truncate table ODS_HOUSINGCASELISTED_LJ_#yearMonth#;

update a
set a.uv_community_id=b.ITEM_ProjectID,
    a.uv_building_id=b.ITEM_BuildingID,
    a.area_coefficient= D.CoefficientA * A.case_area + D.CoefficientB,
    a.toward_coefficient = e.Coefficient,
    a.floor_coefficient= f.Coefficient,
    a.decoration_coefficient=g.Coefficient,
    a.year_coefficient=h.Coefficient,
    a.building_coefficient=i.Coefficient,
    a.adjust_unit_price = (A.clean_unit_price - isnull(G.Coefficient, 0)) * 1.0 /
                          isnull(D.CoefficientA * A.case_area + D.CoefficientB, 1) / isnull(E.Coefficient, 1) /
                          isnull(F.Coefficient, 1) / isnull(H.Coefficient, 1) / isnull(I.Coefficient, 1),
    a.clean_project_level = C.PROJECTLEVEL,
    a.uv_county=c.countyName,
    a.uv_block=c.blockName,
    a.uv_loop=c.loopName,
    a.uv_community_name=c.ITEM_RANAME,
    a.uv_community_address=c.ITEM_RAADRESS
from original_residence_sale_opening_case_#yearMonth# A
         left join obpm_LianCheng_Data.dbo.TLK_链家小区 B
                   on A.case_lianjia_community_id = B.ITEM_ProjectID_LJ
         left join
     (
         select X.ITEM_AIRAID
              , y.ITEM_DICTEXT  as PROJECTLEVEL
              , y2.ITEM_DICTEXT as countyName
              , y3.ITEM_DICTEXT as blockName
              , y4.ITEM_DICTEXT as loopName
              , x.ITEM_RANAME
              , x.ITEM_RAADRESS
         from obpm_LianCheng_Data.dbo.TLK_小区信息管理 x
                  left join obpm_LianCheng_Data.dbo.TLK_字典数据信息 y
                            on x.ITEM_PROPERTYLEVEL = y.ITEM_DICVALUE
                  left join obpm_LianCheng_Data.dbo.TLK_字典数据信息 y2 -- 区域
                            on x.ITEM_DISTRICT1 = y2.ITEM_DICVALUE
                  left join obpm_LianCheng_Data.dbo.TLK_字典数据信息 y3 -- 板块
                            on x.ITEM_SECTOR121 = y3.ITEM_DICVALUE
                  left join obpm_LianCheng_Data.dbo.TLK_字典数据信息 y4 -- 环线
                            on x.ITEM_CIRCLEPOSITION = y4.ITEM_DICVALUE
         where X.ITEM_PROJECTTYPE = '1'
           and y.ITEM_DICTYPE = '物业档次'
           and y2.ITEM_DICTYPE = '区域'
           and y3.ITEM_DICTYPE = '板块'
           and y4.ITEM_DICTYPE = '环线'
     ) C
     on B.ITEM_ProjectID = C.ITEM_AIRAID
         left join
     (
         select ProjectLevel, LowerKey, UpperKey, Coefficient, CoefficientA, CoefficientB
         from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
         where CoffType = '面积修正（链家挂牌）'
           and ProjectType = '公寓'
     ) D --面积修正
     on C.PROJECTLEVEL = D.ProjectLevel
         and
        (
                A.case_area > D.LowerKey
                and A.case_area <= D.UpperKey
            )
         left join
     (
         select SingleKey, Coefficient
         from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
         where CoffType = '朝向（链家挂牌）'
           and ProjectType = '公寓'
     ) E --朝向修正
     on A.clean_toward = E.SingleKey
         left join
     (
         select SingleKey, Coefficient, Elevator, UpperKey, LowerKey
         from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
         where CoffType = '楼层（链家挂牌）'
           and ProjectType = '公寓'
     ) F --楼层修正
     on isnull(A.clean_Elevator, 99) = isnull(F.Elevator, 99)
         and A.clean_total_floor = F.UpperKey
         and A.clean_current_floor_desc = F.LowerKey
         left join
     (
         select SingleKey, Coefficient
         from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
         where CoffType = '室内装修（链家挂牌）'
           and ProjectType = '公寓'
     ) G --装修修正
     on A.clean_Decoration = G.SingleKey
         left join
     (
         select SingleKey, Coefficient
         from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
         where CoffType = '竣工日期'
           and ProjectType = '公寓'
     ) H --竣工日期修正
     on A.clean_year = H.SingleKey
         left join obpm_LianCheng_Data.dbo.DIM_BUILDING_COFF I
                   on B.ITEM_BuildingID = I.BuildingID;

----建临时表#HousingCaseListAdj， 插入挂牌清洗数据
------ 20200312BUG修复：朝向处理规则优化
create table #HousingCaseListAdj
(
	case_id varchar(32) not null
  ,  HouseholdsID_LJ bigint
  , ProjectID_LJ bigint
  , Roomtype nvarchar(64)
  , Area decimal(18, 2)
  , Towards nvarchar(64)
  , UpperFloorSum nvarchar(32)
  , UpperFloorNum nvarchar(32)
  , Elevator tinyint
  , Decoration nvarchar(64)
  , [Year] int
  , PriceTotal decimal(18, 2)
  , PriceUnit decimal(18, 2) not null
  , Visited_Num int
  , First_Visit_Time date
  , Visited_Num_15 int
  , Visited_Num_30 int
);

insert into #HousingCaseListAdj
(
    case_id
  , HouseholdsID_LJ
  , ProjectID_LJ
  , Roomtype
  , Area
  , Towards
  , UpperFloorSum
  , UpperFloorNum
  , Elevator
  , Decoration
  , Year
  , PriceTotal
  , PriceUnit
)
select case_id
     , case_lianjia_id
     , case_lianjia_community_id
     , case_apartment_layout
     , case_area
     , clean_toward
     , clean_total_floor
     , clean_current_floor_desc
     , clean_elevator
     , clean_decoration
     , clean_year
     , clean_total_price
     , clean_unit_price
from original_residence_sale_opening_case_#yearMonth#;

----案例标准化
create table #HousingCaseListSTD
(
	case_id varchar(32) not null
  , HouseholdsID_LJ bigint
  , ProjectID_LJ bigint
  , ProjectID bigint
  , BuildingID bigint
  , AreaCoff decimal(7, 4)
  , TowardsCoff decimal(7, 4)
  , FloorCoff decimal(7, 4)
  , DecorationRng int
  , YearCoff decimal(7, 4)
  , BuildingCoff decimal(7, 4)
  , PriceUnit decimal(18, 2) not null
  , PriceUnitAdj decimal(18, 2) not null
);
insert into #HousingCaseListSTD
(
    case_id
  , HouseholdsID_LJ
  , ProjectID_LJ
  , ProjectID
  , BuildingID
  , AreaCoff
  , TowardsCoff
  , FloorCoff
  , DecorationRng
  , YearCoff
  , BuildingCoff
  , PriceUnit
  , PriceUnitAdj
)
select case_id
     , case_lianjia_id
     , case_lianjia_community_id
     , uv_community_id
     , uv_building_id
     , area_coefficient
     , toward_coefficient
     , floor_coefficient
     , decoration_coefficient
     , year_coefficient
     , building_coefficient
     , clean_unit_price
     , adjust_unit_price
from dbo.original_residence_sale_opening_case_#yearMonth#;


--当月拍照表调价信息
--- 20200205BUG修复：AdjustedValue、AdjustedPst 中当Status=1时，值不为0的问题
select
a.case_id,
a.HouseholdsID_LJ,
	case when b.Status is null then 3
        when a.PriceTotal=b.PriceTotal then 1
        else 2 end as Status,
case when a.PriceTotal=b.PriceTotal then 0   -- 20200205修改
        when b.Status is null then 0
        else a.PriceTotal-b.PriceTotal end AdjustedValue,
case when a.PriceTotal=b.PriceTotal then 0   -- 20200205修改
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
case when b.Status is null then getdate() else b.PriceDateIn end PriceDateIn,
cast(NULL as date) as PriceDateOut
into #HousingCaseListStatic
from #HousingCaseListAdj a
left join Ods_HousingCaseListed_LJ_#lastYearMonth# b
on a.HouseholdsID_LJ = b.HouseholdsID_LJ


--插入当月未下架(活跃)案例数据
insert into dbo.ODS_HOUSINGCASELISTED_LJ_#yearMonth#
(
    case_id
  , HouseholdsID_LJ
  , ProjectID_LJ
  , ProjectID
  , Roomtype
  , Area
  , Towards
  , UpperFloorSum
  , UpperFloorNum
  , Elevator
  , Decoration
  , Year
  , AreaCoff
  , TowardsCoff
  , FloorCoff
  , DecorationRng
  , YearCoff
  , BuildingCoff
  , PriceTotal
  , PriceUnit
  , PriceUnitAdj
  , Visited_Num
  , First_Visit_Time
  , Visited_Num_15
  , Visited_Num_30
  , Status
  , AdjustedValue
  , AdjustedPst
  , AdjustedCumValue
  , AdjustedCumPst
  , AdjustedCumValueAbs
  , AdjustedCumPstAbs
  , AdjustedCumNum
  , PriceTotalIn
  , PriceTotalOut
  , PriceDateIn
  , PriceDateOut
)
select a.case_id
	 , a.HouseholdsID_LJ
     , a.ProjectID_LJ
     , d.ProjectID
     , a.Roomtype
     , a.Area
     , a.Towards
     , a.UpperFloorSum
     , a.UpperFloorNum
     , a.Elevator
     , a.Decoration
     , a.[Year]
     , b.AreaCoff
     , b.TowardsCoff
     , b.FloorCoff
     , b.DecorationRng
     , b.YearCoff
     , b.BuildingCoff
     , a.PriceTotal
     , a.PriceUnit
     , b.PriceUnitAdj
     , a.Visited_Num
     , a.First_Visit_Time
     , a.Visited_Num_15
     , a.Visited_Num_30
     , c.Status
     , c.AdjustedValue
     , c.AdjustedPst
     , c.AdjustedCumValue
     , c.AdjustedCumPst
     , c.AdjustedCumValueAbs
     , c.AdjustedCumPstAbs
     , c.AdjustedCumNum
     , c.PriceTotalIn
     , c.PriceTotalOut
     , c.PriceDateIn
     , c.PriceDateOut
from #HousingCaseListAdj             a
    left join #HousingCaseListSTD    b
        on a.HouseholdsID_LJ = b.HouseholdsID_LJ
    left join #HousingCaseListStatic c
        on a.HouseholdsID_LJ = c.HouseholdsID_LJ
    left join DIM_PROJECTID_LJ2AI     d
        on a.ProjectID_LJ = d.ProjectID_LJ;


/*插入当月下架记录*/

--当月拍照表比上月汇总表减少的记录，为下架记录，先放入临时表 #TmpRecordAdd，然后插入到当月拍照表，记为当月汇总表
insert into dbo.ODS_HOUSINGCASELISTED_LJ_#yearMonth#
(
    case_id
  , HouseholdsID_LJ
  , ProjectID_LJ
  , ProjectID
  , Roomtype
  , Area
  , Towards
  , UpperFloorSum
  , UpperFloorNum
  , Elevator
  , Decoration
  , Year
  , AreaCoff
  , TowardsCoff
  , FloorCoff
  , DecorationRng
  , YearCoff
  , BuildingCoff
  , PriceTotal
  , PriceUnit
  , PriceUnitAdj
  , Visited_Num
  , First_Visit_Time
  , Visited_Num_15
  , Visited_Num_30
  , Status
  , AdjustedValue
  , AdjustedPst
  , AdjustedCumValue
  , AdjustedCumPst
  , AdjustedCumValueAbs
  , AdjustedCumPstAbs
  , AdjustedCumNum
  , PriceTotalIn
  , PriceTotalOut
  , PriceDateIn
  , PriceDateOut
)
select a.case_id
	 , a.HouseholdsID_LJ
     , a.ProjectID_LJ
     , a.ProjectID
     , a.Roomtype
     , a.Area
     , a.Towards
     , a.UpperFloorSum
     , a.UpperFloorNum
     , a.Elevator
     , a.Decoration
     , a.[Year]
     , a.AreaCoff
     , a.TowardsCoff
     , a.FloorCoff
     , a.DecorationRng
     , a.YearCoff
     , a.BuildingCoff
     , a.PriceTotal
     , a.PriceUnit
     , a.PriceUnitAdj
     , a.Visited_Num
     , a.First_Visit_Time
     , a.Visited_Num_15
     , a.Visited_Num_30
     , 4            as Status
     , a.AdjustedValue
     , a.AdjustedPst
     , a.AdjustedCumValue
     , a.AdjustedCumPst
     , a.AdjustedCumValueAbs
     , a.AdjustedCumPstAbs
     , a.AdjustedCumNum
     , a.PriceTotalIn
     , a.PriceTotal as PriceTotalOut
     , a.PriceDateIn
     , getdate()    as PriceDateOut
from ODS_HOUSINGCASELISTED_LJ_#lastYearMonth#          a
    left join ODS_HOUSINGCASELISTED_LJ_#yearMonth# b
        on a.HouseholdsID_LJ = b.HouseholdsID_LJ
where b.HouseholdsID_LJ is null
      and a.Status not in ( 4, 5 );


/*插入历史下架记录*/
insert into dbo.ODS_HOUSINGCASELISTED_LJ_#yearMonth#
(
    case_id
  , HouseholdsID_LJ
  , ProjectID_LJ
  , ProjectID
  , Roomtype
  , Area
  , Towards
  , UpperFloorSum
  , UpperFloorNum
  , Elevator
  , Decoration
  , Year
  , AreaCoff
  , TowardsCoff
  , FloorCoff
  , DecorationRng
  , YearCoff
  , BuildingCoff
  , PriceTotal
  , PriceUnit
  , PriceUnitAdj
  , Visited_Num
  , First_Visit_Time
  , Visited_Num_15
  , Visited_Num_30
  , Status
  , AdjustedValue
  , AdjustedPst
  , AdjustedCumValue
  , AdjustedCumPst
  , AdjustedCumValueAbs
  , AdjustedCumPstAbs
  , AdjustedCumNum
  , PriceTotalIn
  , PriceTotalOut
  , PriceDateIn
  , PriceDateOut
)
select a.case_id
	 , a.HouseholdsID_LJ
     , a.ProjectID_LJ
     , a.ProjectID
     , a.Roomtype
     , a.Area
     , a.Towards
     , a.UpperFloorSum
     , a.UpperFloorNum
     , a.Elevator
     , a.Decoration
     , a.[Year]
     , a.AreaCoff
     , a.TowardsCoff
     , a.FloorCoff
     , a.DecorationRng
     , a.YearCoff
     , a.BuildingCoff
     , a.PriceTotal
     , a.PriceUnit
     , a.PriceUnitAdj
     , a.Visited_Num
     , a.First_Visit_Time
     , a.Visited_Num_15
     , a.Visited_Num_30
     , 5            as Status
     , a.AdjustedValue
     , a.AdjustedPst
     , a.AdjustedCumValue
     , a.AdjustedCumPst
     , a.AdjustedCumValueAbs
     , a.AdjustedCumPstAbs
     , a.AdjustedCumNum
     , a.PriceTotalIn
     , a.PriceTotal as PriceTotalOut
     , a.PriceDateIn
     , a.PriceDateOut
from ODS_HOUSINGCASELISTED_LJ_#lastYearMonth#          a
    left join ODS_HOUSINGCASELISTED_LJ_#yearMonth# b
        on a.HouseholdsID_LJ = b.HouseholdsID_LJ
where b.HouseholdsID_LJ is null
      and a.Status in ( 4, 5 );

drop table #HousingCaseListAdj
drop table #HousingCaseListSTD
drop table #HousingCaseListStatic