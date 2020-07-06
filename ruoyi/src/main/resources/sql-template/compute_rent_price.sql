truncate table DW_HOUSINGCASE_RENT_COMM_#yearMonth#;
truncate table DWA_PROJECTBASEPRICE_RENT_IMDT_#yearMonth#;
/******************************租金案例整合***********************************/
insert into dbo.DW_HOUSINGCASE_RENT_COMM_#yearMonth#
(
    HouseholdsID_SRC
  , ProjectID_SRC
  , ProjectID
  , BuildingID
  , RentType
  , Room
  , Hall
  , Bashroom
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
  , RoomTypeCoff
  , PriceTotal
  , PriceUnit
  , PriceScatterRent
  , PriceEntireRent
  , PriceShareRent0
  , PriceShareRent
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
  , Origin
  , UrlHouseholds
  , UrlProjects
  , CaseName
  , CaseType
)
select HouseholdsID_SRC
     , ProjectID_SRC
     , ProjectID
     , BuildingID
     , RentType
     , Room
     , Hall
     , Bashroom
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
     , RoomTypeCoff
     , PriceTotal
     , PriceUnit
     , PriceScatterRent
     , PriceEntireRent
     , PriceShareRent0
     , PriceShareRent
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
     , Origin
     , UrlHouseholds
     , UrlProjects
     , CaseName
     , cast(1 as tinyint) as CaseType
from ODS_HOUSINGCASELISTED_RENT_#yearMonth#
where Status in ( 1, 2, 3, 4 );


insert into dbo.DW_HOUSINGCASE_RENT_COMM_#yearMonth#
(
    HouseholdsID_SRC
  , ProjectID_SRC
  , ProjectID
  , BuildingID
  , RentType
  , Room
  , Hall
  , Bashroom
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
  , RoomTypeCoff
  , PriceTotal
  , PriceUnit
  , PriceScatterRent
  , PriceEntireRent
  , PriceShareRent0
  , PriceShareRent
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
  , Origin
  , UrlHouseholds
  , UrlProjects
  , CaseName
  , CaseType
)
select HouseholdsID_SRC
     , ProjectID_SRC
     , ProjectID
     , BuildingID
     , RentType
     , Room
     , Hall
     , Bashroom
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
     , RoomTypeCoff
     , PriceTotal
     , PriceUnit
     , PriceScatterRent
     , PriceEntireRent
     , PriceShareRent0
     , PriceShareRent
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
     , Origin
     , UrlHouseholds
     , UrlProjects
     , CaseName
     , cast(0 as tinyint) as CaseType
from ODS_HOUSINGCASEDEAL_RENT_#lastYearMonth#;

--添加案例涨跌幅等信息
update a
set a.RentPrice_1 = b.RentPrice, a.Range = PriceScatterRent*1.0/b.RentPrice-1
from DW_HOUSINGCASE_RENT_COMM_#yearMonth# a
left join ODS_PROJECT_RENT_PRICE_INFO_#lastYearMonth# b
on a.ProjectID = b.ProjectID
where b.Status=1;

--1-整租；2-散租；3-分租； 分租的涨跌幅标签RangeFlag依赖分组比，故先打标签49，后面再修改。
update DW_HOUSINGCASE_RENT_COMM_#yearMonth#
set RangeFlag = case when RentType=2 and abs(Range)<=0.15 then 20
                     when RentType=2 and abs(Range)>0.6   then 27
                     when RentType=2 and abs(Range)>0.3   then 26
                     when RentType=2 and abs(Range)>0.15  then 23
                     when RentType=2 then 29
                     when RentType=1 and Range>=-0.3 and Range<=0.6 then 30
                     when RentType=1 and Range>=-0.6 and Range<-0.3 then 36
                     when RentType=1 and abs(Range)>0.6 then 37
                     when RentType=1 then 39
                     when RentType=3 then 49 end;

/******************************小区基价计算中间表***********************************/
-----20191205:BUG修复：新增CaseType=0 and RangeFlag in (20,23) and Range<-0.1 then -0.1 
----第一步；根据小区做基础信息汇总汇总； 
create table #DWA_PROJECTBASEPRICE_IMDT_STEP_1
(
    ProjectID nvarchar(32) primary key
  , ProjectName nvarchar(1024)
  , ProjectAddr nvarchar(1024)
  , County nvarchar(512)
  , Block nvarchar(512)
  , Loop nvarchar(512)
  , IsIndxGen tinyint
  , IsPstCalc tinyint
  , StatusRun tinyint
  , RentPrice_1 decimal(18, 2)
  , PriceScatterRent decimal(18, 2)
  , PriceDealMean_1 decimal(18, 2)
  , PriceDealMax_1 decimal(18, 2)
  , SumDeal_1 int                         --选20和23
  , PriceDeal_1_ToAI_Pst decimal(18, 6)
  , PriceDealMean decimal(18, 2)          --
  , PriceDealMax decimal(18, 2)
  , SumDeal int                           --
  , PriceDeal_ToAI_Pst decimal(18, 6)     --
  , PriceDeal_ToLst_Pst decimal(18, 6)    --
  , PriceCaseOff decimal(18, 2)
  , PriceCaseOff_ToLst_Pst decimal(18, 6) --下架案例与上期下架案例进行比较，字段稍后更新
  , PriceListedMin decimal(18, 2)         --
  , PriceLstMn_ToAI_Pst decimal(18, 6)    --
  , PriceCase1_ToAI_Pst decimal(18, 6)    --
  , PriceCase2_ToAI_Pst decimal(18, 6)    --
  , PriceCase1_ToLst_Pst decimal(18, 6)   --
  , PriceCase2_ToLst_Pst decimal(18, 6)   --
  , PriceCase1 decimal(18, 2)             --
  , PriceCase1AdjPst decimal(18, 6)       --
  , PriceCase1Adj_0 decimal(18, 6)        --链家1中调价幅度非零的个数
  , SumCase1 int                          --
  , PriceCase2 decimal(18, 2)             --
  , PriceCase2AdjPst decimal(18, 6)       --
  , PriceCase2Adj_0 decimal(18, 6)        --链家2中调价幅度非零的个数
  , SumCase2 int
);


with DW_HOUSINGCASE_RENT_COMM_M as (
  SELECT ProjectID, PriceUnit, PriceScatterRent, Status, AdjustedValue, 
         CASE WHEN CaseType=1 and Status in (1,2) and RangeFlag in (20,23) and AdjustedPst>0.06 then 0.06
              WHEN CaseType=1 and Status in (1,2) and RangeFlag in (20,23) and AdjustedPst<-0.06 then -0.06
              ELSE AdjustedPst END AS AdjustedPst,
         CaseType, RentPrice_1, 
         CASE WHEN CaseType=1 and Status<>4 and RangeFlag in (20,23) and Range>0.1 then 0.1       --涨跌幅在±10%以外的，直接置为边界±10%
              WHEN CaseType=1 and Status<>4 and RangeFlag in (20,23) and Range<-0.1 then -0.1 
              WHEN CaseType=0 and RangeFlag in (20,23) and Range>0.1 then 0.1 
              WHEN CaseType=0 and RangeFlag in (20,23) and Range<-0.1 then -0.1 
              ELSE Range END AS Range, RangeFlag
  from DW_HOUSINGCASE_RENT_COMM_#yearMonth#
),
DW_HOUSINGCASE_RENT_COMM_M_1 as (
  SELECT ProjectID, PriceUnit, PriceScatterRent, Status, AdjustedValue, 
         CASE WHEN CaseType=1 and Status in (1,2) and RangeFlag in (20,23) and AdjustedPst>0.06 then 0.06
              WHEN CaseType=1 and Status in (1,2) and RangeFlag in (20,23) and AdjustedPst<-0.06 then -0.06
              ELSE AdjustedPst END AS AdjustedPst,
         CaseType, RentPrice_1, 
         CASE WHEN CaseType=1 and Status<>4 and RangeFlag in (20,23) and Range>0.1 then 0.1 
              WHEN CaseType=1 and Status<>4 and RangeFlag in (20,23) and Range<-0.1 then -0.1 
              WHEN CaseType=0 and RangeFlag in (20,23) and Range>0.1 then 0.1 
              WHEN CaseType=0 and RangeFlag in (20,23) and Range<-0.1 then -0.1 
              ELSE Range END AS Range, RangeFlag
  from DW_HOUSINGCASE_RENT_COMM_#lastYearMonth#
)
insert into #DWA_PROJECTBASEPRICE_IMDT_STEP_1
select a.ProjectID, a.ProjectName, a.ProjectAddr, a.County, a.Block, a.Loop, a.IsIndxGen, a.IsPstCalc, a.StatusRun,
       d.RentPrice as RentPrice_1, b.PriceScatterRent, 
       c.PriceDealMean_1, c.PriceDealMax_1, c.SumDeal_1, c.PriceDeal_1_ToAI_Pst,
			 b.PriceDealMean, b.PriceDealMax, b.SumDeal, b.PriceDeal_ToAI_Pst, (b.PriceDealMean - c.PriceDealMean_1)*1.0/c.PriceDealMean_1 as PriceDeal_ToLst_Pst,
       b.PriceCaseOff, case when e.PriceCaseOff<>0 then b.PriceCaseOff*1.0/e.PriceCaseOff - 1 end as PriceCaseOff_ToLst_Pst,
       b.PriceListedMin, b.PriceLstMn_ToAI_Pst, b.PriceCase1_ToAI_Pst, b.PriceCase2_ToAI_Pst, 
			 (b.PriceCase1 - c.PriceCase1_1)*1.0/c.PriceCase1_1 as PriceCase1_ToLst_Pst,
			 (b.PriceCase2 - c.PriceCase2_1)*1.0/c.PriceCase2_1 as PriceCase2_ToLst_Pst,
			 b.PriceCase1, b.PriceCase1AdjPst, b.PriceCase1Adj_0, b.SumCase1, b.PriceCase2, b.PriceCase2AdjPst, b.PriceCase2Adj_0, b.SumCase2
from obpm_LianCheng_Data.dbo.V_RESIDENCE_COMMUNITY a
left join (
	select ProjectID, avg(RentPrice_1) as RentPrice_1, 
         avg(case when ((CaseType=1 and Status<>4) or CaseType=0) and RangeFlag in (20,23) then PriceScatterRent end) as PriceScatterRent,
				 avg(case when CaseType=0 and RangeFlag in (20,23) then PriceScatterRent end) as PriceDealMean,
				 max(case when CaseType=0 and RangeFlag in (20,23) then PriceScatterRent end) as PriceDealMax, 
				 count(case when CaseType=0 and RangeFlag in (20,23) then 1 end) as SumDeal,
         avg(case when CaseType=0 and RangeFlag in (20,23) then Range end) as PriceDeal_ToAI_Pst,
               
         avg(case when CaseType=1 and RangeFlag in (20,23) and Status=4 then PriceScatterRent end) as PriceCaseOff,
         
				 min(case when CaseType=1 and Status<>4 and RangeFlag in (20,23) then PriceScatterRent end) as PriceListedMin,
         min(case when CaseType=1 and Status<>4 and RangeFlag in (20,23) then Range end) as PriceLstMn_ToAI_Pst,
				 avg(case when CaseType=1 and Status<>4 and RangeFlag in (20,23) then PriceScatterRent end) as PriceCase1,
				 count(case when CaseType=1 and Status<>4 and RangeFlag in (20,23) then 1 end) as SumCase1,
				 avg(case when CaseType=1 and Status<>4 and RangeFlag in (20,23) then Range end) as PriceCase1_ToAI_Pst,
				 avg(case when CaseType=1 and Status in (1,2) and RangeFlag in (20,23) then AdjustedPst end) as PriceCase1AdjPst,
         count(case when CaseType=1 and Status in (1,2) and RangeFlag in (20,23) and AdjustedPst<>0 then 1 end) as PriceCase1Adj_0,
				 
				 avg(case when CaseType=1 and Status<>4 and RangeFlag in (20) then PriceScatterRent end) as PriceCase2, 
				 count(case when CaseType=1 and Status<>4 and RangeFlag in (20) then 1 end) as SumCase2, 
				 avg(case when CaseType=1 and Status<>4 and RangeFlag in (20) then Range end) as PriceCase2_ToAI_Pst,
				 avg(case when CaseType=1 and Status in (1,2) and RangeFlag in (20) then AdjustedPst end) as PriceCase2AdjPst ,
         count(case when CaseType=1 and Status in (1,2) and RangeFlag in (20) and AdjustedPst<>0 then 1 end) as PriceCase2Adj_0
	from DW_HOUSINGCASE_RENT_COMM_M group by ProjectID
) b on a.ProjectID = b.ProjectID
left join (
	select ProjectID, 
         avg(case when CaseType=0 and RangeFlag in (20,23) then PriceScatterRent end) as PriceDealMean_1,
				 max(case when CaseType=0 and RangeFlag in (20,23) then PriceScatterRent end) as PriceDealMax_1, 
				 count(case when CaseType=0 and RangeFlag in (20,23) then 1 end) as SumDeal_1,
         avg(case when CaseType=0 and RangeFlag in (20,23) then Range end) as PriceDeal_1_ToAI_Pst,
				 avg(case when CaseType=1 and Status<>4 and RangeFlag in (20,23) then PriceScatterRent end) as PriceCase1_1,
				 avg(case when CaseType=1 and Status<>4 and RangeFlag in (20) then PriceScatterRent end) as PriceCase2_1
	from DW_HOUSINGCASE_RENT_COMM_M_1 group by ProjectID
) c on a.ProjectID = c.ProjectID 
left join ODS_PROJECT_RENT_PRICE_INFO_#lastYearMonth# d
on a.ProjectID = d.ProjectID and d.Status=1
left join DWA_PROJECTBASEPRICE_RENT_IMDT_#lastYearMonth# e
on a.ProjectID = e.ProjectID;


----第二步；根据案例自动计算的小区涨跌幅
create table #DWA_PROJECTBASEPRICE_IMDT_STEP_2
(
    ProjectID nvarchar(32) primary key
  , VOPPBT nvarchar(16) --价格涨跌幅类型-调整前
  , VOPPB decimal(18, 6) --价格涨跌幅-调整前
);

-----先考虑情况8：链家1和上月基价、链家1涨跌幅、1调价幅度的三者比较
WITH DataUnPivot AS(
  select projectid, tp, CAST(vl AS DECIMAL(18,6)) vl
  from (
    select projectid, PriceCase1_ToAI_Pst, PriceCase1_ToLst_Pst, PriceCase1AdjPst
    from  #DWA_PROJECTBASEPRICE_IMDT_STEP_1 where PriceScatterRent is not null
  )t unpivot (vl for tp in (PriceCase1_ToAI_Pst, PriceCase1_ToLst_Pst, PriceCase1AdjPst)) up
) 
SELECT A.ProjectID,
   CASE WHEN (PriceCase1_ToAI_Pst>0 AND PriceCase1_ToLst_Pst>0 AND PriceCase1AdjPst>0) THEN MIN_LISTED 
        WHEN (PriceCase1_ToAI_Pst>0 AND PriceCase1_ToLst_Pst>0 AND PriceCase1AdjPst=0) THEN MDN_LISTED*1.0/4 
        WHEN (PriceCase1_ToAI_Pst>0 AND PriceCase1_ToLst_Pst>0 AND PriceCase1AdjPst<0) THEN MDN_LISTED*1.0/4 
        WHEN (PriceCase1_ToAI_Pst>0 AND PriceCase1_ToLst_Pst=0 AND PriceCase1AdjPst>0) THEN MDN_LISTED*1.0/4 
        WHEN (PriceCase1_ToAI_Pst>0 AND PriceCase1_ToLst_Pst=0 AND PriceCase1AdjPst=0) THEN NULL 
        WHEN (PriceCase1_ToAI_Pst>0 AND PriceCase1_ToLst_Pst=0 AND PriceCase1AdjPst<0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst>0 AND PriceCase1_ToLst_Pst<0 AND PriceCase1AdjPst>0) THEN MDN_LISTED*1.0/4
        WHEN (PriceCase1_ToAI_Pst>0 AND PriceCase1_ToLst_Pst<0 AND PriceCase1AdjPst=0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst>0 AND PriceCase1_ToLst_Pst<0 AND PriceCase1AdjPst<0) THEN MDN_LISTED
        WHEN (PriceCase1_ToAI_Pst=0 AND PriceCase1_ToLst_Pst>0 AND PriceCase1AdjPst>0) THEN MDN_LISTED*1.0/4 
        WHEN (PriceCase1_ToAI_Pst=0 AND PriceCase1_ToLst_Pst>0 AND PriceCase1AdjPst=0) THEN MDN_LISTED*1.0/4
        WHEN (PriceCase1_ToAI_Pst=0 AND PriceCase1_ToLst_Pst>0 AND PriceCase1AdjPst<0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst=0 AND PriceCase1_ToLst_Pst=0 AND PriceCase1AdjPst>0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst=0 AND PriceCase1_ToLst_Pst=0 AND PriceCase1AdjPst=0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst=0 AND PriceCase1_ToLst_Pst=0 AND PriceCase1AdjPst<0) THEN MIN_LISTED*1.0/3
        WHEN (PriceCase1_ToAI_Pst=0 AND PriceCase1_ToLst_Pst<0 AND PriceCase1AdjPst>0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst=0 AND PriceCase1_ToLst_Pst<0 AND PriceCase1AdjPst=0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst=0 AND PriceCase1_ToLst_Pst<0 AND PriceCase1AdjPst<0) THEN MDN_LISTED
        WHEN (PriceCase1_ToAI_Pst<0 AND PriceCase1_ToLst_Pst>0 AND PriceCase1AdjPst>0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst<0 AND PriceCase1_ToLst_Pst>0 AND PriceCase1AdjPst=0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst<0 AND PriceCase1_ToLst_Pst>0 AND PriceCase1AdjPst<0) THEN MDN_LISTED
        WHEN (PriceCase1_ToAI_Pst<0 AND PriceCase1_ToLst_Pst=0 AND PriceCase1AdjPst>0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst<0 AND PriceCase1_ToLst_Pst=0 AND PriceCase1AdjPst=0) THEN NULL
        WHEN (PriceCase1_ToAI_Pst<0 AND PriceCase1_ToLst_Pst=0 AND PriceCase1AdjPst<0) THEN MDN_LISTED 
        WHEN (PriceCase1_ToAI_Pst<0 AND PriceCase1_ToLst_Pst<0 AND PriceCase1AdjPst>0) THEN MDN_LISTED
        WHEN (PriceCase1_ToAI_Pst<0 AND PriceCase1_ToLst_Pst<0 AND PriceCase1AdjPst=0) THEN MDN_LISTED 
        WHEN (PriceCase1_ToAI_Pst<0 AND PriceCase1_ToLst_Pst<0 AND PriceCase1AdjPst<0) THEN MDN_LISTED
      END AS VOPP_8
INTO #Price_VOPP_8
FROM #DWA_PROJECTBASEPRICE_IMDT_STEP_1 A
LEFT JOIN (
  select projectid, min(vl) as MIN_LISTED, avg(MDN) as MDN_LISTED
  from (
    select a.*, PERCENTILE_CONT(0.5) within group(order by vl)over(partition by projectid) as MDN --取中位数
    from DataUnPivot a
  )t
  group by projectid
)B ON A.ProjectID = B.ProjectID;

----考虑所有情况
WITH DWA_PROJECTBASEPRICE_IMDT_STEP_1_T AS(
  select a.*, cast(PriceDealMean_1*1.0/RentPrice_1-1 as decimal(18,6)) as PriceDeal_1_ToAI_0_Pst,
         cast(case when PriceDeal_ToLst_Pst>=0.06 then 0.06 
                   when PriceDeal_ToLst_Pst<=-0.06 then -0.06
              else PriceDeal_ToLst_Pst end as decimal(18,6)) as PriceDeal_ToLst_Pst_
  from #DWA_PROJECTBASEPRICE_IMDT_STEP_1 a
),
DataUnPivot(projectid, tp, vl) AS(
  select projectid, tp, CAST(vl AS DECIMAL(18,6)) vl
  from (
    select projectid, PriceCase1AdjPst, PriceCase2AdjPst, PriceCase1_ToAI_Pst, PriceCase2_ToAI_Pst, PriceLstMn_ToAI_Pst, PriceCaseOff_ToLst_Pst,
           PriceDeal_ToAI_Pst, PriceCase1_ToLst_Pst, PriceCase2_ToLst_Pst, PriceDeal_ToLst_Pst_, PriceDeal_1_ToAI_0_Pst, PriceCase1Adj_0, PriceCase2Adj_0
    from  DWA_PROJECTBASEPRICE_IMDT_STEP_1_T
  )t unpivot (vl for tp in (PriceCase1AdjPst, PriceCase2AdjPst, PriceCase1_ToAI_Pst, PriceCase2_ToAI_Pst, PriceLstMn_ToAI_Pst, PriceCaseOff_ToLst_Pst,
                PriceDeal_ToAI_Pst, PriceCase1_ToLst_Pst, PriceCase2_ToLst_Pst, PriceDeal_ToLst_Pst_, PriceDeal_1_ToAI_0_Pst, PriceCase1Adj_0, PriceCase2Adj_0))up
)
INSERT INTO #DWA_PROJECTBASEPRICE_IMDT_STEP_2
SELECT A.ProjectID,
	 CASE WHEN B.VOPP_1 IS NOT NULL AND (B.Adj1<>0 OR B.Adj2 <> 0) THEN '1'
        WHEN D.VOPP_3 IS NOT NULL THEN '3'
	 			WHEN D.VOPP_4 IS NOT NULL THEN '4'
	 			WHEN B.VOPP_2 IS NOT NULL THEN '2'
	 			WHEN B.VOPP_5 IS NOT NULL THEN '5'
	 			WHEN B.VOPP_6 IS NOT NULL THEN '6'
	 			WHEN C.VOPP_8 IS NOT NULL THEN '8'
        WHEN ABS(PriceDeal_ToLst_Pst)<=0.06 THEN '9'
				ELSE NULL END AS VOPPBT,
	 CASE WHEN B.VOPP_1 IS NOT NULL AND (B.Adj1<>0 OR B.Adj2 <> 0) THEN B.VOPP_1
        WHEN D.VOPP_3 IS NOT NULL THEN D.VOPP_3
	 			WHEN D.VOPP_4 IS NOT NULL THEN D.VOPP_4
	 			WHEN B.VOPP_2 IS NOT NULL THEN B.VOPP_2
	 			WHEN B.VOPP_5 IS NOT NULL THEN B.VOPP_5
	 			WHEN B.VOPP_6 >=0.06 THEN 0.06
	 			WHEN B.VOPP_6 IS NOT NULL THEN B.VOPP_6
	 			WHEN C.VOPP_8 IS NOT NULL THEN C.VOPP_8
        WHEN ABS(PriceDeal_ToLst_Pst)<=0.06 THEN E.MDN_DUAL
				ELSE NULL END AS VOPPB
FROM #DWA_PROJECTBASEPRICE_IMDT_STEP_1 A
LEFT JOIN (
  select projectid, 
         avg(case when tp in ('PriceCase1AdjPst', 'PriceCase2AdjPst') then vl end) as VOPP_1,
         avg(case when tp in ('PriceCase1_ToLst_Pst', 'PriceCase2_ToLst_Pst') and abs(vl)<=0.06 then vl end) as VOPP_2,
         avg(case when tp in ('PriceLstMn_ToAI_Pst', 'PriceDeal_ToAI_Pst') then vl end) as VOPP_5,
         avg(case when tp in ('PriceCaseOff_ToLst_Pst', 'PriceLstMn_ToAI_Pst') and vl>0 then vl end) as VOPP_6,
         sum(case when tp in ('PriceCase1Adj_0') then vl end) as Adj1,  --判断当调价均值为0时，是因为所有调价都是0还是有非0值但被平均到0
         sum(case when tp in ('PriceCase2Adj_0') then vl end) as Adj2
  from DataUnPivot group by projectid
) B
ON A.ProjectID = B.ProjectID
LEFT JOIN #Price_VOPP_8 C 
ON A.ProjectID = C.ProjectID
LEFT JOIN (
  select ProjectID, 
         case when abs(PriceCase2_ToLst_Pst)<=0.06 and PriceCase1_ToLst_Pst>0.06 then (0.06+PriceCase2_ToLst_Pst)*1.0/2
              when abs(PriceCase2_ToLst_Pst)<=0.06 and PriceCase1_ToLst_Pst<-0.06 then (-0.06+PriceCase2_ToLst_Pst)*1.0/2 end as VOPP_3,
         case when abs(PriceCase1_ToLst_Pst)<=0.06 and PriceCase2_ToLst_Pst>0.06 then (0.06+PriceCase1_ToLst_Pst)*1.0/2
              when abs(PriceCase1_ToLst_Pst)<=0.06 and PriceCase2_ToLst_Pst<-0.06 then (-0.06+PriceCase1_ToLst_Pst)*1.0/2 end as VOPP_4
  from #DWA_PROJECTBASEPRICE_IMDT_STEP_1
)D ON A.ProjectID = D.ProjectID
LEFT JOIN (
  select projectid, min(vl) as MIN_DUAL, avg(MDN) as MDN_DUAL
  from (
    select a.*, PERCENTILE_CONT(0.5) within group(order by vl)over(partition by projectid) as MDN --取中位数
    from DataUnPivot a where TP IN ('PriceDeal_ToLst_Pst_', 'PriceDeal_ToAI_Pst', 'PriceDeal_1_ToAI_0_Pst')
  )t
  group by projectid
)E ON A.ProjectID = E.ProjectID;

----剔除没有计算出涨跌幅的小区
DELETE FROM #DWA_PROJECTBASEPRICE_IMDT_STEP_2
WHERE VOPPBT IS NULL;

----幅度大于0.06，置为±0.06
UPDATE #DWA_PROJECTBASEPRICE_IMDT_STEP_2
SET VOPPB=0.06 WHERE VOPPB>0.06;
UPDATE #DWA_PROJECTBASEPRICE_IMDT_STEP_2
SET VOPPB=-0.06 WHERE VOPPB<-0.06;

DROP TABLE #Price_VOPP_8

----第三步；计算绑定涨跌幅
create table #DWA_PROJECTBASEPRICE_IMDT_STEP_3
(
    ProjectID nvarchar(32) primary key
  , BindProjID int
  , Bind_Proj_Pst decimal(18, 6)
  , Bind_Block_Class nvarchar(128)
  , Bind_Block_Class_Pst decimal(18, 6)
  , Bind_Block_Plevel nvarchar(128)
  , Bind_Block_Plevel_Pst decimal(18, 6)
  , Bind_Block_PType nvarchar(128)
  , Bind_Block_PType_Pst decimal(18, 6)
  , Bind_County_PType nvarchar(128)
  , Bind_County_PType_Pst decimal(18, 6)
  , Bind_MixProject_PType nvarchar(128)
  , Bind_MixProject_Pst decimal(18, 6)
  , VOPPAT int
  , VOPPA decimal(18, 6)
);

SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.MixProjMainID, A.BindClassID, A.BindProjID, B.VOPPB AS Bind_Proj_Pst, 
			 CASE WHEN C.ProjectID IS NOT NULL THEN C.VOPPBT 
            WHEN B.ProjectID IS NOT NULL THEN 8505 END AS VOPPAT,
			 CASE WHEN C.ProjectID IS NOT NULL THEN C.VOPPB 
            WHEN B.ProjectID IS NOT NULL THEN B.VOPPB END AS VOPPA
INTO #TempBindProjID
FROM obpm_LianCheng_Data.dbo.V_RESIDENCE_COMMUNITY A
LEFT JOIN #DWA_PROJECTBASEPRICE_IMDT_STEP_2 B
ON A.BindProjID = B.ProjectID
LEFT JOIN #DWA_PROJECTBASEPRICE_IMDT_STEP_2 C
ON A.ProjectID = C.ProjectID;


-- 2.板块+绑定
SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.MixProjMainID, A.BindClassID, A.BindProjID, A.Bind_Proj_Pst,
			 A.Block +A.BindClassID as Bind_Block_Class, B.Bind_Block_Class_Pst, 
			 CASE WHEN A.VOPPAT IS NOT NULL THEN A.VOPPAT 
            WHEN B.Bind_Block_Class IS NOT NULL THEN 8501 END AS VOPPAT,
			 CASE WHEN A.VOPPA IS NOT NULL THEN A.VOPPA 
            WHEN B.Bind_Block_Class IS NOT NULL THEN B.Bind_Block_Class_Pst END AS VOPPA
INTO #Temp_Bind_Block_Class
FROM #TempBindProjID A
LEFT JOIN (
	SELECT Block+BindClassID AS Bind_Block_Class, AVG(VOPPA) AS Bind_Block_Class_Pst
	FROM #TempBindProjID GROUP BY Block+BindClassID
) B
ON A.Block +A.BindClassID = B.Bind_Block_Class AND B.Bind_Block_Class_Pst IS NOT null;


-- 3.板块+物业档次
SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.MixProjMainID, A.BindClassID, A.BindProjID, A.Bind_Proj_Pst,
			 A.Bind_Block_Class, A.Bind_Block_Class_Pst, A.Block +A.ProjectLevel as Bind_Block_Plevel, B.Bind_Block_Plevel_Pst,
			 CASE WHEN A.VOPPAT IS NOT NULL THEN A.VOPPAT 
            WHEN B.Bind_Block_Plevel IS NOT NULL THEN 8502 END AS VOPPAT,
			 CASE WHEN A.VOPPA IS NOT NULL THEN A.VOPPA 
            WHEN B.Bind_Block_Plevel IS NOT NULL THEN B.Bind_Block_Plevel_Pst END AS VOPPA
INTO #Temp_Bind_Block_Plevel
FROM #Temp_Bind_Block_Class A
LEFT JOIN (
	SELECT Block+ProjectLevel AS Bind_Block_Plevel, AVG(VOPPA) AS Bind_Block_Plevel_Pst
	FROM #Temp_Bind_Block_Class GROUP BY Block+ProjectLevel
) B
ON A.Block +A.ProjectLevel = B.Bind_Block_Plevel AND B.Bind_Block_Plevel_Pst IS NOT NULL

-- 4.板块+物业类型
SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.MixProjMainID, A.BindClassID, A.BindProjID, A.Bind_Proj_Pst,
			 A.Bind_Block_Class, A.Bind_Block_Class_Pst, A.Bind_Block_Plevel, A.Bind_Block_Plevel_Pst,
			 A.Block+A.ProjectTypeDtl as Bind_Block_PType, B.Bind_Block_PType_Pst,
			 CASE WHEN A.VOPPAT IS NOT NULL THEN A.VOPPAT 
            WHEN B.Bind_Block_PType IS NOT NULL THEN 8503 END AS VOPPAT,
			 CASE WHEN A.VOPPA IS NOT NULL THEN A.VOPPA 
            WHEN B.Bind_Block_PType IS NOT NULL THEN B.Bind_Block_PType_Pst END AS VOPPA
INTO #Temp_Bind_Block_PType
FROM #Temp_Bind_Block_Plevel A
LEFT JOIN (
	SELECT Block+ProjectTypeDtl AS Bind_Block_PType, AVG(VOPPA) AS Bind_Block_PType_Pst
	FROM #Temp_Bind_Block_Plevel GROUP BY Block+ProjectTypeDtl
) B
ON A.Block+A.ProjectTypeDtl = B.Bind_Block_PType AND B.Bind_Block_PType_Pst IS NOT NULL
-- 5.区域+物业类型
SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.MixProjMainID, A.BindClassID, A.BindProjID, A.Bind_Proj_Pst,
			 A.Bind_Block_Class, A.Bind_Block_Class_Pst, A.Bind_Block_Plevel, A.Bind_Block_Plevel_Pst,
			 A.Bind_Block_PType, A.Bind_Block_PType_Pst, A.County +A.ProjectTypeDtl as Bind_County_PType, B.Bind_County_PType_Pst,      
			 CASE WHEN A.VOPPAT IS NOT NULL THEN A.VOPPAT 
            WHEN B.Bind_County_PType IS NOT NULL THEN 8504 END AS VOPPAT,
			 CASE WHEN A.VOPPA IS NOT NULL THEN A.VOPPA 
            WHEN B.Bind_County_PType IS NOT NULL THEN B.Bind_County_PType_Pst END AS VOPPA
INTO #Temp_Bind_County_PType
FROM #Temp_Bind_Block_PType A
LEFT JOIN (
	SELECT County+ProjectTypeDtl AS Bind_County_PType, AVG(VOPPA) AS Bind_County_PType_Pst
	FROM #Temp_Bind_Block_PType GROUP BY County+ProjectTypeDtl
) B
ON A.County +A.ProjectTypeDtl = B.Bind_County_PType AND B.Bind_County_PType_Pst IS NOT null;


-- 6.混合小区处理
SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.MixProjMainID, A.BindClassID, A.BindProjID, A.Bind_Proj_Pst,
			 A.Bind_Block_Class, A.Bind_Block_Class_Pst, A.Bind_Block_Plevel, A.Bind_Block_Plevel_Pst,
			 A.Bind_Block_PType, A.Bind_Block_PType_Pst, A.Bind_County_PType, A.Bind_County_PType_Pst,  
       CASE WHEN A.MixProjMainID IS NOT NULL AND ISNULL(A.VOPPA,0) <> ISNULL(B.VOPPA,0) THEN B.ProjectID END AS Bind_MixProject_PType,
       CASE WHEN A.MixProjMainID IS NOT NULL AND ISNULL(A.VOPPA,0) <> ISNULL(B.VOPPA,0) THEN B.VOPPA END AS Bind_MixProject_Pst,
       CASE WHEN A.MixProjMainID IS NOT NULL AND ISNULL(A.VOPPA,0) <> ISNULL(B.VOPPA,0) THEN 8505 ELSE A.VOPPAT END AS VOPPAT,
       CASE WHEN A.MixProjMainID IS NOT NULL AND ISNULL(A.VOPPA,0) <> ISNULL(B.VOPPA,0) THEN B.VOPPA ELSE A.VOPPA END AS VOPPA
INTO #Temp_Bind_MixProject
FROM #Temp_Bind_County_PType A
LEFT JOIN #Temp_Bind_County_PType B
ON A.MixProjMainID = B.ProjectID;



--插入结果 (11-39 自动计算; 8505 绑定相同ID平均值; 8506混合小区ID; 8501板块+绑定; 8502板块+物业档次; 8503板块+类型; 8504区域+类型; 8505绑定混合小区)
INSERT INTO #DWA_PROJECTBASEPRICE_IMDT_STEP_3
SELECT ProjectID, BindProjID, Bind_Proj_Pst,
			 Bind_Block_Class, Bind_Block_Class_Pst, Bind_Block_Plevel, Bind_Block_Plevel_Pst,
			 Bind_Block_PType, Bind_Block_PType_Pst, Bind_County_PType, Bind_County_PType_Pst, Bind_MixProject_PType, Bind_MixProject_Pst, VOPPAT, VOPPA
FROM #Temp_Bind_MixProject

DROP TABLE #TempBindProjID
DROP TABLE #Temp_Bind_Block_Class
DROP TABLE #Temp_Bind_Block_Plevel
DROP TABLE #Temp_Bind_Block_PType
DROP TABLE #Temp_Bind_County_PType
DROP TABLE #Temp_Bind_MixProject


----第四步；整合到结果表
insert into dbo.DWA_PROJECTBASEPRICE_RENT_IMDT_#yearMonth#
           (ProjectID
           ,ProjectName
           ,ProjectAddr
           ,County
           ,Block
           ,Loop
           ,IsIndxGen
           ,IsPstCalc
           ,StatusRun
           ,PropertyType
           ,ProjectType
           ,ProjectTypeDtl
           ,ProjectLevel
           ,PropertyDevPeriod
           ,BindClassID
           ,MainCoff_Rent
           ,RentPriceDft
           ,MainRentPriceDft
           ,RentPrice_1
           ,PriceDealMean_1
           ,PriceDealMax_1
           ,SumDeal_1
           ,PriceDeal_1_ToAI_Pst
           ,PriceDealMean
           ,PriceDealMax
           ,SumDeal
           ,PriceDeal_ToAI_Pst
           ,PriceDeal_ToLst_Pst
           ,PriceCaseOff
           ,PriceCaseOff_ToLst_Pst
           ,PriceListedMin
           ,PriceLstMn_ToAI_Pst
           ,PriceCase1_ToAI_Pst
           ,PriceCase2_ToAI_Pst
           ,PriceCase1_ToLst_Pst
           ,PriceCase2_ToLst_Pst
           ,PriceCase1
           ,PriceCase1AdjPst
           ,SumCase1
           ,PriceCase2
           ,PriceCase2AdjPst
           ,SumCase2
           ,VOPPBT
           ,VOPPB
           ,BindProjID
           ,Bind_Proj_Pst
           ,Bind_Block_Class
           ,Bind_Block_Class_Pst
           ,Bind_Block_Plevel
           ,Bind_Block_Plevel_Pst
           ,Bind_Block_PType
           ,Bind_Block_Ptype_Pst
           ,Bind_County_PType
           ,Bind_County_Ptype_Pst
           ,Bind_MixProject_PType
           ,Bind_MixProject_Pst
           ,VOPPAT
           ,VOPPA
		   )
select A.ProjectID
     , A.ProjectName
     , A.ProjectAddr
     , A.County
     , A.Block
     , A.Loop
     , A.IsIndxGen
     , A.IsPstCalc
     , A.StatusRun
     , D.propertyType
     , D.ProjectType
     , D.ProjectTypeDtl
     , D.ProjectLevel
     , D.PropertyDevPeriod
     , D.BindClassID
     , isnull(D.MainCoff_Rent, 1)
     , A.RentPrice_1 * (1 + isnull(C.VOPPA, 0))                              as RentPriceDft
     , A.RentPrice_1 * (1 + isnull(C.VOPPA, 0)) * isnull(D.MainCoff_Rent, 1) as MainRentPriceDft
     , A.RentPrice_1
     , A.PriceDealMean_1
     , A.PriceDealMax_1
     , A.SumDeal_1
     , A.PriceDeal_1_ToAI_Pst
     , A.PriceDealMean
     , A.PriceDealMax
     , A.SumDeal
     , A.PriceDeal_ToAI_Pst
     , A.PriceDeal_ToLst_Pst
     , A.PriceCaseOff
     , A.PriceCaseOff_ToLst_Pst
     , A.PriceListedMin
     , A.PriceLstMn_ToAI_Pst
     , A.PriceCase1_ToAI_Pst
     , A.PriceCase2_ToAI_Pst
     , A.PriceCase1_ToLst_Pst
     , A.PriceCase2_ToLst_Pst
     , A.PriceCase1
     , A.PriceCase1AdjPst
     , A.SumCase1
     , A.PriceCase2
     , A.PriceCase2AdjPst
     , A.SumCase2
     , B.VOPPBT
     , B.VOPPB
     , C.BindProjID
     , C.Bind_Proj_Pst
     , C.Bind_Block_Class
     , C.Bind_Block_Class_Pst
     , C.Bind_Block_Plevel
     , C.Bind_Block_Plevel_Pst
     , C.Bind_Block_PType
     , C.Bind_Block_PType_Pst
     , C.Bind_County_PType
     , C.Bind_County_PType_Pst
     , C.Bind_MixProject_PType
     , C.Bind_MixProject_Pst
     , case
           when C.VOPPAT is null
                and A.RentPrice_1 is not null then
               9999
           else
               C.VOPPAT
       end                                                                   as VOPPAT
     , case
           when C.VOPPA is null
                and A.RentPrice_1 is not null then
               0
           else
               C.VOPPA
       end                                                                   as VOPPA
from #DWA_PROJECTBASEPRICE_IMDT_STEP_1                      A
    left join #DWA_PROJECTBASEPRICE_IMDT_STEP_2             B
        on A.ProjectID = B.ProjectID
    left join #DWA_PROJECTBASEPRICE_IMDT_STEP_3             C
        on A.ProjectID = C.ProjectID
    left join obpm_LianCheng_Data.dbo.V_RESIDENCE_COMMUNITY D
        on A.ProjectID = D.projectid;


---添加整租比、分租比
--案例整合表
UPDATE A
SET A.RentPrice = B.RentPriceDft, A.EntireRentRatio = A.PriceEntireRent*1.0/B.RentPriceDft,
    A.ShareRentRatio = A.PriceShareRent*1.0/B.RentPriceDft/90
FROM DW_HOUSINGCASE_RENT_COMM_#yearMonth# A
LEFT JOIN DWA_PROJECTBASEPRICE_RENT_IMDT_#yearMonth# B
ON A.ProjectID = B.ProjectID;

UPDATE DW_HOUSINGCASE_RENT_COMM_#yearMonth#
SET RangeFlag = CASE WHEN ShareRentRatio>=0.8 AND ShareRentRatio<=1.7 THEN 40
                     WHEN ShareRentRatio<0.8 THEN 46
                     WHEN ShareRentRatio>1.7 THEN 47
                     ELSE 49 END 
WHERE RentType=3;


DROP TABLE #DWA_PROJECTBASEPRICE_IMDT_STEP_1
DROP TABLE #DWA_PROJECTBASEPRICE_IMDT_STEP_2
DROP TABLE #DWA_PROJECTBASEPRICE_IMDT_STEP_3