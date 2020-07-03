create table #HousingCaseListAdj
(
    HouseholdsID_SRC nvarchar(64) primary key
  , ProjectID_SRC nvarchar(64) not null
  , RentType tinyint
  , Room tinyint
  , Hall tinyint
  , Bashroom tinyint
  , Area decimal(18, 2)
  , Towards nvarchar(64)
  , UpperFloorSum nvarchar(32)
  , UpperFloorNum nvarchar(32)
  , Elevator tinyint
  , Decoration nvarchar(64)
  , Year int
  , PriceTotal decimal(18, 2) not null
  , PriceUnit decimal(18, 2) not null
  , Visited_Num int
  , First_Visit_Time date
  , Visited_Num_15 int
  , Visited_Num_30 int
  , PriceDateIn date
  , TowardsCoff decimal(7, 4)
  , Origin nvarchar(64)
  , UrlHouseholds nvarchar(max)
  , UrlProjects nvarchar(max)
  , CaseName nvarchar(1024)
);


DECLARE @llid NVARCHAR(64),	@lcid NVARCHAR(64), @name NVARCHAR(1024),	@roomtype nvarchar(50),	@area decimal(18,2)
DECLARE @towards nvarchar(50),	@storey nvarchar(50), @Created DATE, @price decimal(18,2), @UrlHouseholds  NVARCHAR(MAX), @UrlProjects  NVARCHAR(MAX)
DECLARE @RentType TINYINT, @Room TINYINT, @Hall TINYINT, @Bashroom TINYINT, @TowardsCoff  DECIMAL(7,4),@UpperFloorSum NVARCHAR(32), @UpperFloorNum NVARCHAR(32), @priceunit decimal(18,2)
--朝向修正系数(由于单个案例可能有多个朝向, 所以多个朝向的修正系数需要通过游标完成)
select SingleKey, Coefficient INTO #TEMP from DIM_PARAMETERS where ProjectType = '公寓' and CoffType='朝向（租金）'
DECLARE Record2Insert CURSOR FOR
  SELECT llid, lcid, cast([Name] as nvarchar(1024)) as Name, roomtype, area, towards, storey, Created, price, Url, Curl FROM ODS_HOUSINGCASELISTED_RENT_LJ_#yearMonth#_Raw
OPEN Record2Insert
FETCH NEXT FROM Record2Insert INTO @llid ,	@lcid ,	@name, @roomtype ,	@area ,	@towards ,	@storey , @Created, @price, @UrlHouseholds, @UrlProjects
SET NOCOUNT ON  --忽略行数显示
WHILE @@FETCH_STATUS=0
BEGIN
  BEGIN
  --租赁类型
  SET @RentType = CASE WHEN LEFT(@name,2) IN ('整租') THEN 1
                       WHEN LEFT(@name,2) IN ('散租') THEN 2
                       WHEN LEFT(@name,2) IN ('分租') THEN 3
                  ELSE NULL END
  --室厅卫
  SET @Room = CASE WHEN isnumeric(substring(@roomtype, 1, charindex('室', @roomtype, 1)-1))=1 THEN substring(@roomtype, 1, charindex('室', @roomtype, 1)-1) END
  SET @Hall = CASE WHEN isnumeric(substring(@roomtype, charindex('室', @roomtype, 1)+1, charindex('厅', @roomtype, 1)-charindex('室', @roomtype, 1)-1))=1
                THEN substring(@roomtype, charindex('室', @roomtype, 1)+1, charindex('厅', @roomtype, 1)-charindex('室', @roomtype, 1)-1) END
  SET @Bashroom = CASE WHEN isnumeric(substring(@roomtype, charindex('厅', @roomtype, 1)+1, charindex('卫', @roomtype, 1)-charindex('厅', @roomtype, 1)-1))=1
                    THEN substring(@roomtype, charindex('厅', @roomtype, 1)+1, charindex('卫', @roomtype, 1)-charindex('厅', @roomtype, 1)-1) END

  --朝向及朝向修正系数 （#lastYearMonth#10修改：由于链家页面修改，朝向规则修改如下）
  if @towards in ('暂无数据', '--')
    set @towards = NULL
--  else
--    set @towards = substring(@towards, 2, len(@towards)-1)  --去掉字段中的’朝‘字
  while patindex('% %',@towards)>0
    SET @towards=STUFF(@towards, patindex('% %',@towards),1,'|')  --多个朝向用’|‘隔开
  select @TowardsCoff=case when Coefficient is null then 1 else Coefficient end
  from (select avg(Coefficient) Coefficient from StringSplit(@towards, '|') a inner join #TEMP b on a.StringSub = b.SingleKey)t  --多个朝向的修正系数取均值

  --楼层处理
  set @UpperFloorNum = case when substring(@storey,1,3)='高楼层' then '高区'
                            when substring(@storey,1,3)='中楼层' then '中区'
                            when substring(@storey,1,3)='低楼层' then '低区' end
  set @UpperFloorSum = substring(@storey,charindex('/',@storey,1)+1,charindex('层',@storey,charindex('/',@storey,1))-charindex('/',@storey,1)-1)

  --单价
  set @priceunit = @price*1.0/isnull(@area,1)

  --插入数据
  insert into #HousingCaseListAdj values(@llid, @lcid, @RentType, @Room, @Hall, @Bashroom, @area, @towards, @UpperFloorSum, @UpperFloorNum, NULL, NULL, NULL,
        @price, @priceunit, NULL, NULL, NULL,NULL, @Created, @TowardsCoff, '链家', @UrlHouseholds, @UrlProjects, @name)

  END
  FETCH NEXT FROM Record2Insert INTO @llid ,	@lcid ,	@name, @roomtype ,	@area ,	@towards ,	@storey , @Created, @price, @UrlHouseholds, @UrlProjects
END
CLOSE Record2Insert
DEALLOCATE Record2Insert
SET NOCOUNT OFF

drop table #TEMP

/****个性化：
-----1）由于链家目前已没有`散租`,都已标记为`整租`,而租金计算用到`散租`,因此这里把链家的整租都修改为散租
-----2）装修为空的统一调整为`中装`
****/
update #HousingCaseListAdj
set RentType=2 where RentType=1

update #HousingCaseListAdj
set RentType=2 where RentType is null

update #HousingCaseListAdj
set Decoration='中装' where Decoration is null



/****************插入租赁平台数据*********************/
--来源页房源ID提取
select distinct case_url,
       case when case_source in ('21世纪不动产') then 'century21_'+reverse(substring(reverse(case_url), charindex('.', reverse(case_url))+1, charindex('/', reverse(case_url))- charindex('.', reverse(case_url))-1))
            when case_source in ('蛋壳') then 'danken_'+reverse(substring(reverse(case_url), charindex('.', reverse(case_url))+1, charindex('/', reverse(case_url))- charindex('.', reverse(case_url))-1))
            when case_source in ('我爱我家') then '5i5j_'+reverse(substring(reverse(case_url), charindex('.', reverse(case_url))+1, charindex('/', reverse(case_url))- charindex('.', reverse(case_url))-1))
            when case_source in ('中原地产') then 'centanet_'+reverse(substring(reverse(case_url), charindex('.', reverse(case_url))+1, charindex('/', reverse(case_url))- charindex('.', reverse(case_url))-1))
            when case_source in ('自如') then 'ziroom_'+reverse(substring(reverse(case_url), charindex('.', reverse(case_url))+1, charindex('/', reverse(case_url))- charindex('.', reverse(case_url))-1))
            when case_source in ('平安好房') then 'pinganfang_'+reverse(substring(reverse(case_url), charindex('lmth.', reverse(case_url))+5, charindex('.di', reverse(case_url))- charindex('lmth.', reverse(case_url))-5))
            when case_source in ('青客') then 'qk365_'+reverse(substring(reverse(case_url), 1, charindex('/', reverse(case_url))- 1))
            when case_source in ('太平洋房屋') then 'taiwu_'+reverse(substring(reverse(case_url), 2, charindex('/', reverse(case_url), 2)- 2))
            when case_source in ('信义房屋') then 'sinyi'+reverse(substring(reverse(case_url), 1, charindex('=di', reverse(case_url), 2)- 1)) end as HouseholdsID
into #HouseholdsID
from residence_rent_platform_case_#yearMonth#


select t.*
into #HouseingCaseZFZL
from (
  select row_number()over(partition by b.HouseholdsID order by newid()) sq, a.*, b.HouseholdsID
  from (select * from residence_rent_platform_case_#yearMonth# where  case_source<>'链家') a
  inner join #HouseholdsID b
  on a.case_url = b.case_url
)t  where sq=1;


INSERT INTO #HousingCaseListAdj
SELECT HouseholdsID AS HouseholdsID_SRC, '-1' AS ProjectID_SRC,
       CASE WHEN case_rent_type='整租' then 1
            WHEN case_rent_type='散租' then 2
            WHEN case_rent_type='分租' then 3
            WHEN case_rent_type='按间' then 3 END AS RentType,
       case_room_num AS Room, case_hall_num AS Hall, NULL AS Bashroom, case_area AS Area, NULL AS Towards, NULL AS UpperFloorSum,
       CASE WHEN case_floor LIKE '%低%' THEN '低区'
            WHEN case_floor LIKE '%中%' THEN '中区'
            WHEN case_floor LIKE '%高%' THEN '高区' END AS UpperFloorNum, NULL AS Elevator, case_decoration AS Decoration, NULL AS Year, ISNULL(case_price, 1) PriceTotal,
       ISNULL(CONVERT(DECIMAL(18,2),case_price), 1)*1.0/ISNULL(CONVERT(DECIMAL(18,2),case_area),1) AS PriceUnit, NULL Visited_Num, NULL First_Visit_Time, NULL Visited_Num_15, NULL Visited_Num_30, savetime AS PriceDateIn,
       NULL AS TowardsCoff, case_source AS Origin, case_url as  UrlHouseholds, NULL as UrlProjects, case_community_name + '|' + case_address as CaseName
FROM #HouseingCaseZFZL A

/****************地址匹配*********************/
--select top 3000* from BAK_HousingCaseListAdj_20190725 order by newid()
create table #HousingCaseListAdj2ID
(
    HouseholdsID_SRC nvarchar(64) primary key
  , ProjectID_SRC nvarchar(64) not null
  , ProjectID bigint
  , BuildingID bigint
);

--链家案例地址匹配
INSERT INTO #HousingCaseListAdj2ID
SELECT A.HouseholdsID_SRC, A.ProjectID_SRC, B.ProjectID, B.BuildingID
FROM #HousingCaseListAdj A
LEFT JOIN DIM_PROJECTID_LJ2AI B
ON A.ProjectID_SRC = cast(B.ProjectID_LJ as varchar)
WHERE A.Origin = '链家'

--其他房源的地址匹配

select
CommunityId as ProjectID,
ITEM_RANAME as ProjectName,
ITEM_RAADRESS as  ProjectAddr,
County,
Block,
Loop,
item_UpperFloorSum as UpperFloorSum
into #TEMP_ODS_PROJECT_INFO
from obpm_LianCheng_Data.dbo.V_ResidenceCommunity;

with HouseingCaseZFZL_STD as(
  select a.case_county , HouseholdsID,
         case when [地址_1] is not null then [地址_1] else case_address end as [地址_1],
         case when [小区名_1] is not null then [小区名_1] else case_community_name end as [小区名_1]
  from (
      SELECT A.*,
         STUFF(case_address, patindex('%(%',case_address),patindex('%)%',case_address)-patindex('%(%',case_address)+1,'') as [地址_1],
         STUFF(case_community_name, patindex('%(%',case_community_name),patindex('%)%',case_community_name)-patindex('%(%',case_community_name)+1,'') as [小区名_1]
      FROM #HouseingCaseZFZL A
  )a
)
select t.HouseHoldsID as HouseHoldsID_SRC, '-1' as ProjectID_SRC,
       case when a.[小区ID] is not null then a.[小区ID]
            when c.[小区ID] is not null then c.[小区ID]
            else d.ProjectID end as "ProjectID",
       a.[楼栋ID] as "BuildingID"
INTO #HousingCaseListAdj2ID_ZFZL
from HouseingCaseZFZL_STD t
left join (select * from [AI楼栋地址20190729] where [项目类型] = '居住') a
on LEFT(a.[新区域],2)=LEFT(t.case_county,2) and (t.[地址_1] = a.[地址栋号] or t.[地址_1] = a.[区址])
left join (select * from [AI小区地址20190729] where [项目类型] = '居住') c
on LEFT(c.[新区域],2)=LEFT(t.case_county,2) and (t.[小区名_1] = c.[小区名址] or t.[地址_1] = c.[区地址] or t.[小区名_1] = c.[区地址] or t.[地址_1] = c.[小区名址])
left join #TEMP_ODS_PROJECT_INFO d
on LEFT(d.County,2)=LEFT(t.case_county,2) and (t.[小区名_1] = d.ProjectName or t.[地址_1] = d.ProjectAddr);

INSERT INTO #HousingCaseListAdj2ID
SELECT HouseHoldsID_SRC, ProjectID_SRC, ProjectID, BuildingID
FROM (
  SELECT ROW_NUMBER()OVER(PARTITION BY HouseHoldsID_SRC ORDER BY BuildingID DESC) SQ, A.*
  FROM #HousingCaseListAdj2ID_ZFZL A
)T WHERE SQ=1;


DROP TABLE #HousingCaseListAdj2ID_ZFZL
DROP TABLE #HouseingCaseZFZL;

/****个性化：
***** 总楼层为NULL，
***** 1.用楼栋或小区主力楼栋的总层数补充
***** 2.总楼层置为7，后面楼层系数修正时统一
****/
UPDATE A
SET A.UpperFloorSum =
  CASE WHEN A.UpperFloorSum<>0 AND A.UpperFloorSum IS NOT NULL THEN A.UpperFloorSum
       WHEN C.[AI小区ID] IS NOT NULL THEN C.[地上层数]
       ELSE D.UpperFloorSum END
FROM #HousingCaseListAdj A
LEFT JOIN #HousingCaseListAdj2ID B
ON A.HouseHoldsID_SRC = B.HouseHoldsID_SRC
LEFT JOIN [AI楼栋基价201709] C
ON B.ProjectID=C.[AI小区ID] AND B.BuildingID=C.[AI楼栋ID]
LEFT JOIN #TEMP_ODS_PROJECT_INFO D
ON B.ProjectID=D.ProjectID;

UPDATE #HousingCaseListAdj
SET UpperFloorSum = 7
WHERE UpperFloorSum IS NULL;



/***************************案例标准化*******************************/
create table #HousingCaseListSTD
(
    HouseholdsID_SRC nvarchar(64)
  , ProjectID_SRC nvarchar(64)
  , ProjectID bigint
  , BuildingID bigint
  , AreaCoff decimal(7, 4)
  , TowardsCoff decimal(7, 4)
  , FloorCoff decimal(7, 4)
  , DecorationRng int
  , YearCoff decimal(7, 4)
  , BuildingCoff decimal(7, 4)
  , RoomTypeCoff decimal(7, 4)
  , PriceUnit decimal(18, 2) not null
  , PriceScatterRent decimal(18, 2) --散租单价
  , PriceEntireRent decimal(18, 2)  --整租单价
  , PriceShareRent0 decimal(18, 2)  --合租单间价
  , PriceShareRent decimal(18, 2) --合租折算价
);


INSERT INTO #HousingCaseListSTD
SELECT A.HouseholdsID_SRC, A.ProjectID_SRC, B.ProjectID, B.BuildingID,
			 D.CoefficientA*A.Area+D.CoefficientB as AreaCoff, a.TowardsCoff as TowardsCoff, F.Coefficient as FloorCoff,
			 G.Coefficient as DecorationRng, 1 as YearCoff, I.Coefficient as BuildingCoff, J.Coefficient AS RoomTypeCoff, A.PriceUnit,
			 (A.PriceUnit-isnull(G.Coefficient,0))*1.0/isnull(D.CoefficientA*A.Area+D.CoefficientB,1)/isnull(a.TowardsCoff,1)/isnull(F.Coefficient,1)/isnull(I.Coefficient,1)/isnull(J.Coefficient,1) PriceScatterRent,
       (A.PriceUnit-isnull(G.Coefficient,0))*1.0/isnull(D.CoefficientA*A.Area+D.CoefficientB,1)/isnull(a.TowardsCoff,1)/isnull(F.Coefficient,1)/isnull(I.Coefficient,1)/isnull(J.Coefficient,1)+isnull(G.Coefficient,0) PriceEntireRent,
       A.PriceTotal*1.0/isnull(F.Coefficient,1)/isnull(a.TowardsCoff,1) as PriceShareRent0,
       CASE WHEN A.Hall=0 THEN A.PriceTotal*A.Room*1.0/isnull(F.Coefficient,1)/isnull(a.TowardsCoff,1)
            ELSE A.PriceTotal*(A.Room+A.Hall-1)*1.0/isnull(F.Coefficient,1)/isnull(a.TowardsCoff,1) END AS PriceShareRent
FROM #HousingCaseListAdj A
LEFT JOIN #HousingCaseListAdj2ID B
ON A.HouseholdsID_SRC = B.HouseholdsID_SRC
left join (select CommunityId,ProjectLevel from obpm_LianCheng_Data.dbo.V_ResidenceCommunity ) C
on B.ProjectID = C.CommunityId
LEFT JOIN (SELECT * FROM DIM_PARAMETERS WHERE CoffType='面积修正（租金）' and ProjectType = '公寓') D	--面积修正
on C.ProjectLevel = D.ProjectLevel and ( A.Area > D.LowerKey and A.Area <= D.UpperKey)
LEFT JOIN (SELECT * FROM DIM_PARAMETERS WHERE CoffType='楼层（租金）' and ProjectType = '公寓') F	--楼层修正
on isnull(A.Elevator,99)=isnull(F.Elevator,99)  and A.UpperFloorSum = F.UpperKey and A.UpperFloorNum = F.LowerKey
LEFT JOIN (SELECT * FROM DIM_PARAMETERS WHERE CoffType='室内装修（租金）' and ProjectType = '公寓') G --装修修正
on  A.Decoration = G.SingleKey
LEFT JOIN DIM_BUILDING_COFF I
on B.BuildingID = I.BuildingID
LEFT JOIN (SELECT * FROM DIM_PARAMETERS WHERE CoffType='室加厅修正（租金）' and ProjectType = '公寓') J   --室加厅修正
on A.Room = J.UpperKey and A.Hall = J.LowerKey;

--当月拍照表调价信息（非首期）
  select
  	a.HouseholdsID_SRC,
		case when b.Status is null then 3
         when a.PriceTotal=b.PriceTotal then 1
         else 2 end as Status,
    case when a.PriceTotal=b.PriceTotal then b.AdjustedValue
         when b.Status is null then 0
         else a.PriceTotal-b.PriceTotal end AdjustedValue,
    case when a.PriceTotal=b.PriceTotal then b.AdjustedPst
         when b.Status is null then 0
         else (a.PriceTotal-b.PriceTotal)*1.0/b.PriceTotal end AdjustedPst,
    case when a.PriceTotal=b.PriceTotal then b.AdjustedCumValue
         when b.Status is null then 0
         else b.AdjustedCumValue + a.PriceTotal-b.PriceTotal end AdjustedCumValue,
    case when a.PriceTotal=b.PriceTotal then b.AdjustedCumPst
         when b.Status is null then 0
         else (b.AdjustedCumValue + a.PriceTotal-b.PriceTotal)*1.0/b.PriceTotalIn end AdjustedCumPst,
    case when a.PriceTotal=b.PriceTotal then b.AdjustedCumValueAbs
         when b.Status is null then 0
         else b.AdjustedCumValueAbs + abs(a.PriceTotal-b.PriceTotal) end AdjustedCumValueAbs,
    case when a.PriceTotal=b.PriceTotal then b.AdjustedCumPstAbs
         when b.Status is null then 0
         else (b.AdjustedCumValueAbs+ abs(a.PriceTotal-b.PriceTotal))*1.0/b.PriceTotalIn end AdjustedCumPstAbs,
    case when a.PriceTotal=b.PriceTotal then b.AdjustedCumPstAbs
         when b.Status is null then 0
         else b.AdjustedCumNum+1 end AdjustedCumNum,
    case when b.Status is null then a.PriceTotal else b.PriceTotalIn end PriceTotalIn,
    NULL as PriceTotalOut,
    case when b.Status is not null then b.PriceDateIn
         when a.PriceDateIn is not null then a.PriceDateIn
         else getdate() end as PriceDateIn,
    cast(NULL as date) as PriceDateOut,
    case when b.Status is not null then b.Origin
         else a.Origin end as Origin
    into #HousingCaseListStatic
    from #HousingCaseListAdj a
    left join dbo.Ods_HousingCaseListed_RENT_#lastYearMonth# b
    on a.HouseholdsID_SRC = b.HouseholdsID_SRC;

/**************************插入案例到案例清洗表(非首期)***********************************/
--新建当期案例清洗表


--插入当月活跃案例数据
insert into ODS_HOUSINGCASELISTED_RENT_#yearMonth#
select a.HouseholdsID_SRC, a.ProjectID_SRC, b.ProjectID, b.BuildingID, a.RentType, a.Room, a.Hall, a.Bashroom,
       a.Area, a.Towards, a.UpperFloorSum, a.UpperFloorNum, a.Elevator, a.Decoration, a.[Year],
       b.AreaCoff, b.TowardsCoff, b.FloorCoff, b.DecorationRng, b.YearCoff, b.BuildingCoff, b.RoomTypeCoff,
       a.PriceTotal, a.PriceUnit, b.PriceScatterRent, b.PriceEntireRent, b.PriceShareRent0, b.PriceShareRent,
       a.Visited_Num, a.First_Visit_Time, a.Visited_Num_15, a.Visited_Num_30,
       c.Status,	c.AdjustedValue,	c.AdjustedPst,	c.AdjustedCumValue,	c.AdjustedCumPst,	c.AdjustedCumValueAbs,
       c.AdjustedCumPstAbs,	c.AdjustedCumNum,	c.PriceTotalIn,	c.PriceTotalOut,	c.PriceDateIn,	c.PriceDateOut,
       c.Origin, a.UrlHouseholds, a.UrlProjects, a.CaseName
from #HousingCaseListAdj a
left join #HousingCaseListSTD b
on a.HouseholdsID_SRC = b.HouseholdsID_SRC
left join #HousingCaseListStatic c
on a.HouseholdsID_SRC = c.HouseholdsID_SRC;

--插入当月下架记录
select a.HouseholdsID_SRC, a.ProjectID_SRC, a.ProjectID, a.BuildingID, a.RentType, a.Room, a.Hall, a.Bashroom,
       a.Area, a.Towards, a.UpperFloorSum, a.UpperFloorNum, a.Elevator, a.Decoration, a.[Year],
       a.AreaCoff, a.TowardsCoff, a.FloorCoff, a.DecorationRng, a.YearCoff, a.BuildingCoff, a.RoomTypeCoff,
       a.PriceTotal, a.PriceUnit, a.PriceScatterRent, a.PriceEntireRent, a.PriceShareRent0, a.PriceShareRent,
       a.Visited_Num, a.First_Visit_Time, a.Visited_Num_15, a.Visited_Num_30,
       4 as Status,	a.AdjustedValue,	a.AdjustedPst,	a.AdjustedCumValue,	a.AdjustedCumPst,	a.AdjustedCumValueAbs,
       a.AdjustedCumPstAbs,	a.AdjustedCumNum,	a.PriceTotalIn,	a.PriceTotal as PriceTotalOut,	a.PriceDateIn,	getdate() as PriceDateOut,
       a.Origin, a.UrlHouseholds, a.UrlProjects, a.CaseName
into #TmpRecordAddCurr
from ODS_HOUSINGCASELISTED_RENT_#lastYearMonth# a
left join ODS_HOUSINGCASELISTED_RENT_#yearMonth# b
on a.HouseholdsID_SRC = b.HouseholdsID_SRC
where b.HouseholdsID_SRC is null and a.Status not in (4,5);

insert into ODS_HOUSINGCASELISTED_RENT_#yearMonth# select * from #TmpRecordAddCurr;


--插入历史下架记录
select a.HouseholdsID_SRC, a.ProjectID_SRC, a.ProjectID, a.BuildingID, a.RentType, a.Room, a.Hall, a.Bashroom,
       a.Area, a.Towards, a.UpperFloorSum, a.UpperFloorNum, a.Elevator, a.Decoration, a.[Year],
       a.AreaCoff, a.TowardsCoff, a.FloorCoff, a.DecorationRng, a.YearCoff, a.BuildingCoff, a.RoomTypeCoff,
       a.PriceTotal, a.PriceUnit, a.PriceScatterRent, a.PriceEntireRent, a.PriceShareRent0, a.PriceShareRent,
       a.Visited_Num, a.First_Visit_Time, a.Visited_Num_15, a.Visited_Num_30,
       5 as Status,	a.AdjustedValue,	a.AdjustedPst,	a.AdjustedCumValue,	a.AdjustedCumPst,	a.AdjustedCumValueAbs,
       a.AdjustedCumPstAbs,	a.AdjustedCumNum,	a.PriceTotalIn,	a.PriceTotal as PriceTotalOut,	a.PriceDateIn,	a.PriceDateOut,
       a.Origin, a.UrlHouseholds, a.UrlProjects, a.CaseName
into #TmpRecordAddHis
from ODS_HOUSINGCASELISTED_RENT_#lastYearMonth# a
left join ODS_HOUSINGCASELISTED_RENT_#yearMonth# b
on a.HouseholdsID_SRC = b.HouseholdsID_SRC
where b.HouseholdsID_SRC is null and a.Status in (4,5);

insert into ODS_HOUSINGCASELISTED_RENT_#yearMonth# select * from #TmpRecordAddHis;


drop table #HousingCaseListAdj
drop table #HousingCaseListAdj2ID
drop table #HousingCaseListSTD
drop table #HousingCaseListStatic
drop table #TmpRecordAddCurr
drop table #TmpRecordAddHis
drop table #HouseholdsID
DROP TABLE #TEMP_ODS_PROJECT_INFO