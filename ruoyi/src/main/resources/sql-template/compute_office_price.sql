 --区县处理
update ODS_OFFICECASELISTED_#yearMonth#_RAW     
set [区域] = left([区域],2);
 
--楼盘名称中括号处理
update ODS_OFFICECASELISTED_#yearMonth#_RAW 
set [楼盘名称_M] = STUFF([楼盘名称], patindex('%(%',[楼盘名称]),patindex('%)%',[楼盘名称])-patindex('%(%',[楼盘名称])+1,'')
 
----索引匹配
--搜房
---(2019.07.09)删除DIM_OFFICE_PROJECTID_SF2AI 中同一BuildingID对应不同UnifiedID的记录
select distinct a.SID,
  case when b.BuildingID_P is not null then b.BuildingID_P
       when c.BuildingID_P is not null then c.BuildingID_P
       else d.BuildingID_P end as BuildingID_P,
  case when b.ProjectID_P is not null then b.ProjectID_P
       when c.ProjectID_P is not null then c.ProjectID_P
       else d.ProjectID_P end as ProjectID_P
into #AddressMatchSF
from ODS_OFFICECASELISTED_#yearMonth#_RAW a
left join DIM_OFFICE_PROJECTID_SF2AI_201909 b
on a.[来源]='房天下' and (a.[楼盘名称] = b.ProjectName_SF or a.[楼盘名称_M] = b.ProjectName_SF) and (a.[地址] = b.Address_SF or a.[地址] = b.AddressF_SF) and a.[区域] = b.Area_SF
left join DIM_OFFICE_PROJECTID_SF2AI_201909 c
on a.[来源]='房天下' and 'http:'+a.[楼盘网址] = c.Curl
left join DIM_OFFICE_PROJECTID_SF2AI_201909 d
on a.[来源]='房天下' and (a.[地址] =d.Address_SF or a.[地址] =d.AddressF_SF) and a.[区域] = d.Area_SF and  a.[板块] = d.Block_SF
where b.ID_SF is not null or c.ID_SF is not null or d.ID_SF is not null

--安居客
select distinct a.SID, b.BuildingID_P, b.ProjectID_P
into #AddressMatchAJK
from ODS_OFFICECASELISTED_#yearMonth#_RAW a
left join DIM_OFFICE_PROJECTID_AJK2AI_201909 b
on a.[来源]='安居客' and (a.[楼盘名称] = b.ProjectName_AJK or a.[楼盘名称_M] = b.ProjectName_AJK or a.[地址] = b.Address_AJK or a.[地址] = b.AddressF_AJK) and (a.[区域] = b.Area_AJK or a.[板块] = b.Block_AJK)
where b.ID_AJK is not null;

----修正系数匹配及修正
----价格（主力基价或租金）偏离幅度q：
----1）有基价的案例：q=修正后案例单价/上期基价-1； 
---	2）无基价的案例：q=修正后案例单价/(AVERAGE(上期修正案例均价,当期修正案例均价))-1
--- //////20190620优化：1） 可用案例偏离幅度修改为6%。 2） 剔除不在DIM_OFFICE_PROJECT_BUILDING_201909 中的楼栋
--- //////20200325优化：案例整合表中新增没有地址匹配到的案例

with ListedPriceAvg_1 as(
  select BuildingID_P, ProjectID_P, avg(ListedPrice) as ListedPrice_1, avg(ListedPriceRent) as ListedPriceRent_1 
  from DW_OFFICECASE_COMM_#lastYearMonth#
  group by BuildingID_P, ProjectID_P
),
coff as(
  select a.*, b.BuildingID_P, b.ProjectID_P, c.TotalFloorSum ,
         cast(case when a.[楼层]='低区' then 0.96
                   when c.TotalFloorSum>=30 then 1.04 
                   else 1 end as DECIMAL(7,4)) as FloorCoff,
         cast(case when a.[装修]='精装修' then 1.04
                   else 1 end as DECIMAL(7,4)) as DecorationCoff      
  from ODS_OFFICECASELISTED_#yearMonth#_RAW a
  left join (select * from #AddressMatchSF union select * from #AddressMatchAJK) b
  on a.SID = b.SID
  left join DIM_OFFICE_PROJECT_BUILDING_201909 c
  on b.BuildingID_P = c.BuildingID_P and c.EffDate <= getdate() AND c.ExpirDate > getdate()
),
ListedPriceAvg as(
  select BuildingID_P, ProjectID_P, 
         avg(case when [分类]='售' then [单价] end) as ListedPrice,
         avg(case when [分类]='租' then [单价] end) as ListedPriceRent
  from coff group by BuildingID_P, ProjectID_P
),
ListedPriceAvg_0_1 as(
  select BuildingID_P, ProjectID_P, avg(Price) as ListedPrice_0_1, avg(PriceRent) as ListedPriceRent_0_1
  from (
    select BuildingID_P, ProjectID_P, ListedPrice_1 as Price, ListedPriceRent_1 as PriceRent from ListedPriceAvg_1
    union
    select BuildingID_P, ProjectID_P, ListedPrice as Price, ListedPriceRent as PriceRent from ListedPriceAvg
  ) a group by BuildingID_P, ProjectID_P
)
select a.*, isnull(a.[单价],0)*1.0/FloorCoff/DecorationCoff as PriceAmend,
       b.MainPrice MainPrice_1, b.MainPriceRent MainPriceRent_1,
       e.ListedPrice_1, e.ListedPriceRent_1, d.ListedPrice, d.ListedPriceRent,
       case when a.[分类]='售' and isnull(b.MainPrice,0)<>0 then isnull(a.[单价],0)*1.0/FloorCoff/DecorationCoff/b.MainPrice-1
            when a.[分类]='售' and isnull(b.MainPrice,0)= 0 then isnull(a.[单价],0)*1.0/FloorCoff/DecorationCoff/isnull(c.ListedPrice_0_1, 1)-1
            when a.[分类]='租' and isnull(b.MainPriceRent,0)<>0 then isnull(a.[单价],0)*1.0/FloorCoff/DecorationCoff/b.MainPriceRent-1
            when a.[分类]='租' and isnull(b.MainPriceRent,0)= 0 then isnull(a.[单价],0)*1.0/FloorCoff/DecorationCoff/isnull(c.ListedPriceRent_0_1, 1)-1
            end as VOPP
into DW_OFFICECASE_COMM_#yearMonth# 
from coff a
left join (select BuildingID_P, ProjectID_P, MainPrice, MainPriceRent from ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# where Status = 1 and BuildingStd = 1) b  ---BuildingStd=1：取5000左右的活跃的楼栋(标准楼栋); Status=1当前有效
on a.BuildingID_P = b.BuildingID_P
left join ListedPriceAvg_0_1 c on a.BuildingID_P = c.BuildingID_P
left join ListedPriceAvg d on a.BuildingID_P = d.BuildingID_P
left join ListedPriceAvg_1 e on a.BuildingID_P = e.BuildingID_P

alter table DW_OFFICECASE_COMM_#yearMonth# add VOPPT nvarchar(32);

update DW_OFFICECASE_COMM_#yearMonth# 
 set VOPPT = 
         case when [分类]='售' and abs(VOPP)>0.3 then 's:gt:30%'
              when [分类]='售' and abs(VOPP)>0.2 then 's:gt:20%'
              when [分类]='售' and abs(VOPP)>0.1 then 's:gt:10%'
              when [分类]='售' and abs(VOPP)>0.06 then 's:gt:6%'
              when [分类]='售' then 's:lte:6%'
              when [分类]='租' and abs(VOPP)>0.3 then 'r:gt:30%'
              when [分类]='租' and abs(VOPP)>0.2 then 'r:gt:20%'
              when [分类]='租' and abs(VOPP)>0.1 then 'r:gt:10%'
              when [分类]='租' and abs(VOPP)>0.06 then 'r:gt:6%'
              when [分类]='租' then 'r:lte:6%' end;

---根据楼栋汇总
select BuildingID_P, ProjectID_P, 
       MainPrice_1,     --上期基价
       MainPriceRent_1, --上期租金
       cast(avg(case when a.VOPPT='s:lte:6%' and a.PriceAmend<>0 then a.PriceAmend end) as decimal(18,2)) as MainPrice,   --剔除案例修正价格为0的和空的（avg函数自动剔除空值不参与运算）
       cast(avg(case when a.VOPPT='r:lte:6%' and a.PriceAmend<>0 then a.PriceAmend end) as decimal(18,2)) as MainPriceRent,
       cast(avg(case when a.VOPPT='s:lte:6%' then a.VOPP end) as DECIMAL(18,6)) as MainPricePst,
       cast(avg(case when a.VOPPT='r:lte:6%' then a.VOPP end) as DECIMAL(18,6)) as MainPriceRentPst
Into #BuildingPriceWithCase
from DW_OFFICECASE_COMM_#yearMonth# a
where VOPPT in ('s:lte:6%','r:lte:6%')
group by BuildingID_P, ProjectID_P, MainPrice_1, MainPriceRent_1

---绑定涨跌幅
--绑定同一办公项目
select a.BuildingID_P, a.ProjectID_P, a.ProjectName, a.ProjectAddr, a.BuildingAddr, a.County, a.Loop, a.Block, a.Street, 
       a.[Year], a.AvgArea, a.TotalFloorSum, a.UpperFloorSum, a.OfficeClass, a.Grade, 
       case when c.MainPricePst is not null then c.MainPricePst 
            when b.MainPricePst is not null then b.MainPricePst end as MainPricePst,
       case when c.MainPriceRentPst is not null then c.MainPriceRentPst 
            when b.MainPriceRentPst is not null then b.MainPriceRentPst end as MainPriceRentPst,
       case when c.MainPricePst is not null then 't' 
            when b.MainPricePst is not null then 't0' end as MainPriceType,
       case when c.MainPriceRentPst is not null then 't'
            when b.MainPriceRentPst is not null then 't0' end as MainPriceRentType 
into #TempBindUnifiedID
from DIM_OFFICE_PROJECT_BUILDING_201909 a
left join (
  select ProjectID_P, avg(MainPricePst) MainPricePst, avg(MainPriceRentPst) MainPriceRentPst
  from #BuildingPriceWithCase group by ProjectID_P
)b on a.ProjectID_P = b.ProjectID_P
left join #BuildingPriceWithCase c
on a.BuildingID_P= c.BuildingID_P
where (b.ProjectID_P is not null or c.BuildingID_P is not null) and a.EffDate <= getdate() AND a.ExpirDate > getdate()
go

--竣工日期<=5年+同一办公分类+ 同一街道
with BindStreet as(
  select a.*, b.BuildingID_P BuildingID_B, b.ProjectID_P ProjectID_B, b.MainPricePst, b.MainPriceRentPst, b.MainPriceType, b.MainPriceRentType
  from DIM_OFFICE_PROJECT_BUILDING_201909 a
  inner join #TempBindUnifiedID b
  on abs(a.[Year]-b.[Year])<=5 and a.OfficeClass = b.OfficeClass and (a.OfficeClass is not null and a.OfficeClass<>'未确定') and a.Street = b.Street
)
select a.BuildingID_P, a.ProjectID_P, a.ProjectName, a.ProjectAddr, a.BuildingAddr, a.County, a.Loop, a.Block, a.Street, 
       a.[Year], a.AvgArea, a.TotalFloorSum, a.UpperFloorSum, a.OfficeClass, a.Grade, 
       case when c.MainPricePst is not null then c.MainPricePst 
            when b.MainPricePst is not null then b.MainPricePst end as MainPricePst,
       case when c.MainPriceRentPst is not null then c.MainPriceRentPst 
            when b.MainPriceRentPst is not null then b.MainPriceRentPst end as MainPriceRentPst,
       case when c.MainPricePst is not null then c.MainPriceType
            when b.MainPricePst is not null then 't1' end as MainPriceType,
       case when c.MainPriceRentPst is not null then c.MainPriceRentType
            when b.MainPriceRentPst is not null then 't1' end as MainPriceRentType  
into #TempBindStreet
from DIM_OFFICE_PROJECT_BUILDING_201909 a
left join (
  select BuildingID_P, ProjectID_P, avg(MainPricePst) MainPricePst, avg(MainPriceRentPst) MainPriceRentPst 
  from BindStreet group by BuildingID_P, ProjectID_P
) b on a.BuildingID_P = b.BuildingID_P
left join #TempBindUnifiedID c
on a.BuildingID_P= c.BuildingID_P
where (b.ProjectID_P is not null or c.BuildingID_P is not null) and a.EffDate <= getdate() AND a.ExpirDate > getdate()
go

--竣工日期<=5年+同一办公分类+ 同一板块
with BindBlock as(
  select a.*, b.BuildingID_P BuildingID_B, b.ProjectID_P ProjectID_B, b.MainPricePst, b.MainPriceRentPst, b.MainPriceType, b.MainPriceRentType
  from DIM_OFFICE_PROJECT_BUILDING_201909 a
  inner join #TempBindStreet b
  on abs(a.[Year]-b.[Year])<=5 and a.OfficeClass = b.OfficeClass and (a.OfficeClass is not null and a.OfficeClass<>'未确定') and a.Block = b.Block
)
select a.BuildingID_P, a.ProjectID_P, a.ProjectName, a.ProjectAddr, a.BuildingAddr, a.County, a.Loop, a.Block, a.Street, 
       a.[Year], a.AvgArea, a.TotalFloorSum, a.UpperFloorSum, a.OfficeClass, a.Grade, 
       case when c.MainPricePst is not null then c.MainPricePst 
            when b.MainPricePst is not null then b.MainPricePst end as MainPricePst,
       case when c.MainPriceRentPst is not null then c.MainPriceRentPst 
            when b.MainPriceRentPst is not null then b.MainPriceRentPst end as MainPriceRentPst,
       case when c.MainPricePst is not null then c.MainPriceType
            when b.MainPricePst is not null then 't2' end as MainPriceType,
       case when c.MainPriceRentPst is not null then c.MainPriceRentType
            when b.MainPriceRentPst is not null then 't2' end as MainPriceRentType  
into #TempBindBlock
from DIM_OFFICE_PROJECT_BUILDING_201909 a
left join (
  select BuildingID_P, ProjectID_P, avg(MainPricePst) MainPricePst, avg(MainPriceRentPst) MainPriceRentPst 
  from BindBlock group by BuildingID_P, ProjectID_P
) b on a.BuildingID_P = b.BuildingID_P
left join #TempBindStreet c
on a.BuildingID_P= c.BuildingID_P
where(b.ProjectID_P is not null or c.BuildingID_P is not null) and a.EffDate <= getdate() AND a.ExpirDate > getdate()
go

--竣工日期<=5年+同一办公分类+ 同一区域
with BindCounty as(
  select a.*, b.BuildingID_P BuildingID_B, b.ProjectID_P ProjectID_B, b.MainPricePst, b.MainPriceRentPst, b.MainPriceType, b.MainPriceRentType
  from DIM_OFFICE_PROJECT_BUILDING_201909 a
  inner join #TempBindBlock b
  on abs(a.[Year]-b.[Year])<=5 and a.OfficeClass = b.OfficeClass and (a.OfficeClass is not null and a.OfficeClass<>'未确定') and a.County = b.County
)
select a.BuildingID_P, a.ProjectID_P, a.ProjectName, a.ProjectAddr, a.BuildingAddr, a.County, a.Loop, a.Block, a.Street, 
       a.[Year], a.AvgArea, a.TotalFloorSum, a.UpperFloorSum, a.OfficeClass, a.Grade, 
       case when c.MainPricePst is not null then c.MainPricePst 
            when b.MainPricePst is not null then b.MainPricePst end as MainPricePst,
       case when c.MainPriceRentPst is not null then c.MainPriceRentPst 
            when b.MainPriceRentPst is not null then b.MainPriceRentPst end as MainPriceRentPst,
       case when c.MainPricePst is not null then c.MainPriceType
            when b.MainPricePst is not null then 't3' end as MainPriceType,
       case when c.MainPriceRentPst is not null then c.MainPriceRentType
            when b.MainPriceRentPst is not null then 't3' end as MainPriceRentType  
into #TempBindCounty
from DIM_OFFICE_PROJECT_BUILDING_201909 a
left join (
  select BuildingID_P, ProjectID_P, avg(MainPricePst) MainPricePst, avg(MainPriceRentPst) MainPriceRentPst 
  from BindCounty group by BuildingID_P, ProjectID_P
) b on a.BuildingID_P = b.BuildingID_P
left join #TempBindBlock c
on a.BuildingID_P= c.BuildingID_P
where (b.ProjectID_P is not null or c.BuildingID_P is not null) and a.EffDate <= getdate() AND a.ExpirDate > getdate()
go

--竣工日期<=5年+同一办公分类+ 同一环线
with BindLoop as(
  select a.*, b.BuildingID_P BuildingID_B, b.ProjectID_P ProjectID_B, b.MainPricePst, b.MainPriceRentPst, b.MainPriceType, b.MainPriceRentType
  from DIM_OFFICE_PROJECT_BUILDING_201909 a
  inner join #TempBindCounty b
  on abs(a.[Year]-b.[Year])<=5 and a.OfficeClass = b.OfficeClass and (a.OfficeClass is not null and a.OfficeClass<>'未确定') and a.Loop = b.Loop
)
select a.BuildingID_P, a.ProjectID_P, a.ProjectName, a.ProjectAddr, a.BuildingAddr, a.County, a.Loop, a.Block, a.Street, 
       a.[Year], a.AvgArea, a.TotalFloorSum, a.UpperFloorSum, a.OfficeClass, a.Grade, 
       case when c.MainPricePst is not null then c.MainPricePst 
            when b.MainPricePst is not null then b.MainPricePst end as MainPricePst,
       case when c.MainPriceRentPst is not null then c.MainPriceRentPst 
            when b.MainPriceRentPst is not null then b.MainPriceRentPst end as MainPriceRentPst,
       case when c.MainPricePst is not null then c.MainPriceType
            when b.MainPricePst is not null then 't4' end as MainPriceType,
       case when c.MainPriceRentPst is not null then c.MainPriceRentType
            when b.MainPriceRentPst is not null then 't4' end as MainPriceRentType  
into #TempBindLoop
from DIM_OFFICE_PROJECT_BUILDING_201909 a
left join (
  select BuildingID_P, ProjectID_P, avg(MainPricePst) MainPricePst, avg(MainPriceRentPst) MainPriceRentPst 
  from BindLoop group by BuildingID_P, ProjectID_P
) b on a.BuildingID_P = b.BuildingID_P
left join #TempBindCounty c
on a.BuildingID_P= c.BuildingID_P
where (b.ProjectID_P is not null or c.BuildingID_P is not null) and a.EffDate <= getdate() AND a.ExpirDate > getdate()
go

---添加上期基价
---///非t~t4，且上期价格非空，且上期价格类型非t5，则另当期基价类型为t5
select newid() as id
     , a.BuildingID
     , a.UnifiedID
     , a.ProjectID
     , a.BuildingID_P
     , a.ProjectID_P
     , c.MainPrice * (1 + isnull(b.MainPricePst, 0))         as MainPrice
     , c.MainPriceRent * (1 + isnull(b.MainPriceRentPst, 0)) as MainPriceRent
     , case
           when b.MainPricePst is not null then
               b.MainPricePst
           when c.MainPrice is not null then
               0
       end                                                   MainPricePst
     , case
           when b.MainPriceRentPst is not null then
               b.MainPriceRentPst
           when c.MainPriceRent is not null then
               0
       end                                                   MainPriceRentPst
     , case
           when b.MainPriceType is not null then
               b.MainPriceType
           when c.MainPrice is not null then
               't5'
       end                                                   as MainPriceType
     , case
           when b.MainPriceRentType is not null then
               b.MainPriceRentType
           when c.MainPriceRent is not null then
               't5'
       end                                                   as MainPriceRentType
     , cast(getdate() as date)                               ModifyDate
     , cast(1 as nvarchar)                                   Status
     , a.BuildingStd
     , cast(null as nvarchar(1000))                          as AdjEvd
	 , c.MainPrice as MainPrice_1
	 , c.MainPriceRent  as MainPriceRent_1
	 , a.AreaCoff as AreaCoff
	 , a.YearCoff as YearCoff
	 , a.BuildingCoff as BuildingCoff
	, a.ProjectName as ProjectName
	, a.ProjectAddr as ProjectAddr
	,a.BuildingAddr as BuildingAddr
	,a.County as County
	,a.Loop  as Loop
	,a.Block as Block
	,a.Street as Street
	,a.Year as Year
	,a.AvgArea as AvgArea
	,a.TotalFloorSum as TotalFloorSum
	,a.UpperFloorSum as UpperFloorSum
	,a.OfficeClass as OfficeClass
	,a.Grade as Grade
into ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth#
from DIM_OFFICE_PROJECT_BUILDING_201909             a
    left join #TempBindLoop                         b
        on a.BuildingID_P = b.BuildingID_P
    left join ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# c
        on a.BuildingID_P = c.BuildingID_P
where a.EffDate <= getdate()
      and a.ExpirDate > getdate()
      and c.Status = 1;

drop table #AddressMatchSF
drop table #AddressMatchAJK
drop table #BuildingPriceWithCase
drop table #TempBindUnifiedID
drop table #TempBindStreet
drop table #TempBindBlock
drop table #TempBindCounty
drop table #TempBindLoop