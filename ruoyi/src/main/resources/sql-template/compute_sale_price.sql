truncate table DW_HOUSINGCASE_COMM_#yearMonth#
truncate table DWA_PROJECTBASEPRICE_IMDT_#yearMonth#

insert into DW_HOUSINGCASE_COMM_#yearMonth#
select
	  a.case_id
     , a.HouseholdsID_LJ
     , a.ProjectID_LJ
     , c.ITEM_AIRAID
     , c.ITEM_RANAME
     , c.ITEM_RAADRESS
     , f.ITEM_DICTEXT
     , g.ITEM_DICTEXT
     , h.ITEM_DICTEXT
     , a.Roomtype
     , a.Area
     , a.Towards
     , a.UpperFloorSum
     , a.UpperFloorNum
     , a.Elevator
     , a.Decoration
     , a.Year
     , isnull(a.AreaCoff, 1)
     , isnull(a.TowardsCoff, 1)
     , isnull(a.FloorCoff, 1)
     , isnull(a.DecorationRng, 0)
     , isnull(a.YearCoff, 1)
     , isnull(a.BuildingCoff, 1)
     , d.BasePrice as BasePrice_1
     , a.PriceTotal
     , a.PriceUnit
     , a.PriceUnitAdj
     , a.Visited_Num
     , a.First_Visit_Time
     , a.Visited_Num_15
     , a.Visited_Num_30
     , a.Status
     , a.AdjustedValue
     , a.AdjustedPst
     , a.AdjustedCumValue
     , a.AdjustedCumPst
     , a.AdjustedCumValueAbs
     , a.AdjustedCumPstAbs
     , a.AdjustedCumNum
     , a.PriceTotalIn
     , a.PriceDateIn
	 , null
     , NULL
from ODS_HOUSINGCASELISTED_LJ_#yearMonth#        a
    left join obpm_LianCheng_Data.dbo.DIM_PROJECTID_LJ2AI           b
        on a.ProjectID_LJ = b.ProjectID_LJ
    left join obpm_LianCheng_Data.dbo.TLK_小区信息管理       c
        on b.ProjectID = c.ITEM_AIRAID and c.ITEM_ISBUILDINDEX='1' and c.ITEM_RUNNINGSTATE=1
		and c.ITEM_PROJECTTYPE='1'
	left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       f
        on c.ITEM_DISTRICT1=f.ITEM_DICVALUE and f.ITEM_DICTYPE='区域'
    left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       g
        on c.ITEM_SECTOR121=g.ITEM_DICVALUE and g.ITEM_DICTYPE='板块'
	left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       h
        on c.ITEM_CIRCLEPOSITION=h.ITEM_DICVALUE and h.ITEM_DICTYPE='环线'
	left join ODS_PROJECT_PRICE_INFO_#lastYearMonth# d
        on b.ProjectID = d.ProjectID
           and d.Status = 1
where a.status not in ( 4, 5 );


insert into DW_HOUSINGCASE_COMM_#yearMonth#
select
     a.case_id
     ,null
     ,null
     , a.ProjectID
     , c.ITEM_RANAME
     , c.ITEM_RAADRESS
     , f.ITEM_DICTEXT
     , g.ITEM_DICTEXT
     , h.ITEM_DICTEXT
     , convert(varchar, null)
     , a.Area
     , convert(varchar, null)
     , a.UpperFloorSum
     , a.UpperFloorNum
     , convert(int, null)
     , convert(varchar, null)
     , convert(int, null)
     , isnull(a.AreaCoff, 1)
     , 1
     , isnull(a.FloorCoff, 1)
     , 0
     , 1
     , isnull(a.BuildingCoff, 1)
     , d.BasePrice as BasePrice_1
     , a.PriceTotal
     , a.PriceUnit
     , a.PriceUnitAdj
     , 0
     , convert(date, null)
     , 0
     , 0
     , convert(int, null)
     , 0
     , 0
     , 0
     , 0
     , 0
     , 0
     , 0
     , 0
     , a.DateContract
	 , null
     , NULL
from ODS_HOUSINGCASE_DEAL_#lastYearMonth#            a
	left join obpm_LianCheng_Data.dbo.TLK_小区信息管理       c
        on a.ProjectID = c.ITEM_AIRAID and c.ITEM_ISBUILDINDEX='1' and c.ITEM_RUNNINGSTATE=1 and c.ITEM_PROJECTTYPE='1'
	left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       f
        on c.ITEM_DISTRICT1=f.ITEM_DICVALUE and f.ITEM_DICTYPE='区域'
    left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       g
        on c.ITEM_SECTOR121=g.ITEM_DICVALUE and g.ITEM_DICTYPE='板块'
	left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       h
        on c.ITEM_CIRCLEPOSITION=h.ITEM_DICVALUE and h.ITEM_DICTYPE='环线'
    left join ODS_PROJECT_PRICE_INFO_#lastYearMonth# d
        on a.ProjectID = d.ProjectID
           and d.Status = 1;


---成交案例的HouseholdsID_LJ，在上期由0优化为NULL，因此RangeFlag类型需要修改
update DW_HOUSINGCASE_COMM_#yearMonth#
set CaseType = case when HouseholdsID_LJ is null then 0 else 1 end,
RangeFlag = case when BasePrice_1 is null then 19
                 when HouseholdsID_LJ is null and abs(PriceUnitAdj*1.0/BasePrice_1-1)<=0.1 then 10
                 when HouseholdsID_LJ is not null and abs(PriceUnitAdj*1.0/BasePrice_1-1)<=0.06 then 10
                 when abs(PriceUnitAdj*1.0/BasePrice_1-1)>0.5 then 17
                 else 16 end;

/******************************小区基价计算中间表***********************************/
----第一步；根据小区做基础信息汇总汇总；
create table #DWA_PROJECTBASEPRICE_IMDT_STEP_1
(
    ProjectID bigint primary key
  , ProjectName nvarchar(1024)
  , ProjectAddr nvarchar(1024)
  , County nvarchar(512)
  , Block nvarchar(512)
  , Loop nvarchar(512)
  , IsIndxGen tinyint
  , IsPstCalc tinyint
  , StatusRun tinyint
  , BasePrice_1 decimal(18, 2)
  , PriceUnitAdj decimal(18, 2)
  , Visited_Num int
  , First_Visit_Time date
  , Visited_Num_15 int
  , Visited_Num_30 int
  , PriceDealMean_1 decimal(18, 2)
  , PriceDealMax_1 decimal(18, 2)
  , SumDeal_1 int
  , PriceDealMean decimal(18, 2)
  , PriceDealMax decimal(18, 2)
  , SumDeal int
  , PriceListedMin decimal(18, 2)
  , PriceCase1_ToAI_Pst decimal(18, 6)
  , PriceCase2_ToAI_Pst decimal(18, 6)
  , PriceCase1_ToLst_Pst decimal(18, 6)
  , PriceCase2_ToLst_Pst decimal(18, 6)
  , PriceCase1 decimal(18, 2)
  , PriceCase1AdjPst decimal(18, 6)
  , SumCase1 int
  , PriceCase2 decimal(18, 2)
  , PriceCase2AdjPst decimal(18, 6)
  , SumCase2 int
  , PriceCase2_ToAI_0 decimal(18, 6)  --PriceCase2_ToAI_Pst非空的案例数量
  , PriceCase2_ToLst_0 decimal(18, 6) --PriceCase2_ToLst_Pst非空的案例数量
  , PriceCase2Adj_0 decimal(18, 6)  --PriceCase2AdjPst非空的案例数量
);


---规则修改：链家1和链家2调价幅度，status in (1,2) 都纳入计算范围
insert into #DWA_PROJECTBASEPRICE_IMDT_STEP_1
select a.ITEM_AIRAID, a.ITEM_RANAME, a.ITEM_RAADRESS,f.ITEM_DICTEXT , g.ITEM_DICTEXT, h.ITEM_DICTEXT, a.ITEM_ISBUILDINDEX, a.ITEM_GAINCALCULATION, a.ITEM_RUNNINGSTATE,
       d.BasePrice, b.PriceUnitAdj, b.Visited_Num,
			 b.First_Visit_Time, b.Visited_Num_15, b.Visited_Num_30, c.PriceDealMean_1, c.PriceDealMax_1, c.SumDeal_1,
			 b.PriceDealMean, b.PriceDealMax, b.SumDeal, b.PriceListedMin, b.PriceCase1_ToAI_Pst, b.PriceCase2_ToAI_Pst,
			 (PriceCase1 - PriceCase1_1)*1.0/PriceCase1_1 as PriceCase1_ToLst_Pst,
			 (PriceCase2 - PriceCase2_1)*1.0/PriceCase2_1 as PriceCase2_ToLst_Pst,
			 b.PriceCase1, b.PriceCase1AdjPst, b.SumCase1, b.PriceCase2, b.PriceCase2AdjPst, b.SumCase2,
       b.PriceCase2_ToAI_0,
       case when b.PriceCase2_ToAI_0<>0 and c.PriceCase2_1 is not null then PriceCase2_ToAI_0
            else 0 end as PriceCase2_ToLst_0,
       b.PriceCase2Adj_0
from obpm_LianCheng_Data.dbo.TLK_小区信息管理 a
left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       f
    on a.ITEM_DISTRICT1=f.ITEM_DICVALUE and f.ITEM_DICTYPE='区域'
left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       g
    on a.ITEM_SECTOR121=g.ITEM_DICVALUE and g.ITEM_DICTYPE='板块'
left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       h
    on a.ITEM_CIRCLEPOSITION=h.ITEM_DICVALUE and h.ITEM_DICTYPE='环线'
left join (
	select ProjectID, avg(BasePrice_1) as BasePrice_1, avg(PriceUnitAdj) as PriceUnitAdj, avg(Visited_Num) as Visited_Num,
				 min(First_Visit_Time) as First_Visit_Time,  avg(Visited_Num_15) Visited_Num_15, avg(Visited_Num_30) as Visited_Num_30,
				 avg(case when CaseType=0 then PriceUnitAdj end) as PriceDealMean,
				 max(case when CaseType=0 then PriceUnitAdj end) as PriceDealMax,
				 count(case when CaseType=0 then 1 end) as SumDeal,
				 min(case when CaseType=1 then PriceUnitAdj end) as PriceListedMin,

				 avg(case when RangeFlag<>19 then PriceUnitAdj end) as PriceCase1,
				 count(case when RangeFlag<>19 then 1 end) as SumCase1,
				 avg(case when RangeFlag<>19 then PriceUnitAdj end)*1.0/avg(BasePrice_1)-1 as PriceCase1_ToAI_Pst,
				 avg(case when RangeFlag<>19 and status in (1,2) then AdjustedPst end) as PriceCase1AdjPst,
				 avg(case when RangeFlag = 10 then PriceUnitAdj end) as PriceCase2,
				 count(case when RangeFlag = 10 then 1 end) as SumCase2,
				 avg(case when RangeFlag = 10 then PriceUnitAdj end)*1.0/avg(BasePrice_1)-1 as PriceCase2_ToAI_Pst,
				 avg(case when RangeFlag = 10 and status in (1,2) then AdjustedPst end) as PriceCase2AdjPst,
         count(case when RangeFlag = 10 then 1 end) as PriceCase2_ToAI_0,
         count(case when RangeFlag = 10 and status in (1,2) then 1 end) as PriceCase2Adj_0
	from DW_HOUSINGCASE_COMM_#yearMonth# group by ProjectID
) b on a.ITEM_AIRAID = b.ProjectID
left join (
	select ProjectID, avg(case when CaseType=0 then PriceUnitAdj end) as PriceDealMean_1,
				 max(case when CaseType=0 then PriceUnitAdj end) as PriceDealMax_1,
				 count(case when CaseType=0 then 1 end) as SumDeal_1,
				 avg(case when RangeFlag<>19 then PriceUnitAdj end) as PriceCase1_1,
				 avg(case when RangeFlag = 10 then PriceUnitAdj end) as PriceCase2_1
	from DW_HOUSINGCASE_COMM_#lastYearMonth# group by ProjectID
) c on a.ITEM_AIRAID = c.ProjectID
left join ODS_PROJECT_PRICE_INFO_#lastYearMonth# d on a.ITEM_AIRAID = d.ProjectID
where a.ITEM_RUNNINGSTATE=1 and a.ITEM_ISBUILDINDEX='1' and a.ITEM_PROJECTTYPE='1' and d.Status=1;

----第二步；根据案例自动计算的小区涨跌幅
create table #DWA_PROJECTBASEPRICE_IMDT_STEP_2
(
    ProjectID bigint primary key
  , VOPPBT int --价格涨跌幅类型-调整前
  , VOPPB decimal(18, 6)  --价格涨跌幅-调整前
);

INSERT INTO #DWA_PROJECTBASEPRICE_IMDT_STEP_2
SELECT a.ProjectID,
       CASE WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst>0) THEN 11
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst=0) THEN 12
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst<0) THEN 13
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst>0) THEN 14
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst=0) THEN 15
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst<0) THEN 16
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst>0) THEN 17
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst=0) THEN 18
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst<0) THEN 19
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst>0) THEN 21
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst=0) THEN 22
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst<0) THEN 23
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst>0) THEN 24
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst=0
                  AND (PriceCase2_ToAI_0<>0 OR PriceCase2_ToLst_0<>0 OR PriceCase2Adj_0<>0)) THEN 25
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst<0) THEN 26
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst>0) THEN 27
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst=0) THEN 28
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst<0) THEN 29
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst>0) THEN 0
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst=0) THEN 32
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst<0) THEN 33
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst>0) THEN 34
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst=0) THEN 35
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst<0) THEN 36
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst>0) THEN 37
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst=0) THEN 38
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst<0) THEN 39
            END AS VOPPBT,
       CASE WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst>0) THEN MIN    --11
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst=0) THEN MDN*1.0/4    --12
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst<0) THEN MDN*1.0/4     --13
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst>0) THEN MDN*1.0/4     --14
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst=0) THEN 0      --15
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst<0) THEN 0      --16
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst>0) THEN MDN*1.0/4     --17
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst=0) THEN 0      --18
            WHEN (PriceCase2_ToAI_Pst>0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst<0) THEN MDN    --19
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst>0) THEN MDN*1.0/4     --21
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst=0) THEN MDN*1.0/4     --22
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst<0) THEN 0      --23
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst>0) THEN 0      --24
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst=0
                  AND (PriceCase2_ToAI_0<>0 OR PriceCase2_ToLst_0<>0 OR PriceCase2Adj_0<>0)) THEN 0      --25
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst<0) THEN MIN*1.0/3     --26
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst>0) THEN 0      --27
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst=0) THEN 0      --28
            WHEN (PriceCase2_ToAI_Pst=0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst<0) THEN MDN    --29
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst>0) THEN 0      --31
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst=0) THEN 0      --32
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst>0 AND PriceCase2AdjPst<0) THEN MDN    --33
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst>0) THEN 0      --34
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst=0) THEN 0      --35
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst=0 AND PriceCase2AdjPst<0) THEN MDN    --36
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst>0) THEN MDN    --37
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst=0) THEN MDN    --38
            WHEN (PriceCase2_ToAI_Pst<0 AND PriceCase2_ToLst_Pst<0 AND PriceCase2AdjPst<0) THEN MDN    --39
            END AS VOPPB
FROM (SELECT projectid, ISNULL(PriceCase2_ToAI_Pst,0) PriceCase2_ToAI_Pst,
        ISNULL(PriceCase2_ToLst_Pst,0) PriceCase2_ToLst_Pst, ISNULL(PriceCase2AdjPst,0) PriceCase2AdjPst,
        PriceCase2_ToAI_0, PriceCase2_ToLst_0, PriceCase2Adj_0
      FROM #DWA_PROJECTBASEPRICE_IMDT_STEP_1 where PriceUnitAdj is not null) a
LEFT JOIN (
  select projectid, min(vl) as MIN, avg(MDN) as MDN
  from (
    select projectid, tp, vl, PERCENTILE_CONT(0.5) within group(order by vl)over(partition by projectid) as MDN --取中位数
    from (
      select projectid, PriceCase2_ToAI_Pst, PriceCase2_ToLst_Pst, PriceCase2AdjPst
      from #DWA_PROJECTBASEPRICE_IMDT_STEP_1 where PriceUnitAdj is not null
    ) as t unpivot(vl for tp in (PriceCase2_ToAI_Pst, PriceCase2_ToLst_Pst, PriceCase2AdjPst)) as up
  )tt group by projectid
)b ON a.projectid = b.projectid;

----第三步；计算绑定涨跌幅
-----2019.11.12规则新增：对于绑定没有推导出、且上周期有价格的小区，沿用上周期价格
create table #DWA_PROJECTBASEPRICE_IMDT_STEP_3
(
    ProjectID bigint primary key
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
  , Bind_Price_1_PType nvarchar(128)
  , Bind_Price_1_PType_Pst decimal(18, 6)
  , VOPPAT varchar(64)
  , VOPPA decimal(18, 6)
);


SELECT A.ITEM_AIRAID as ProjectID, g.ITEM_DICTEXT as Block, f.ITEM_DICTEXT as County, h.ITEM_DICTEXT as ProjectLevel, A.item_ProjectTypeDtl as ProjectTypeDtl, A.item_BindClassID as BindClassID, B.ProjectID AS BindProjID, B.VOPPB AS Bind_Proj_Pst,
			 CASE WHEN C.ProjectID IS NOT NULL THEN C.VOPPBT
            WHEN B.ProjectID IS NOT NULL AND D.BasePrice IS NOT NULL THEN '8505' END AS VOPPAT,
			 CASE WHEN C.ProjectID IS NOT NULL THEN C.VOPPB
            WHEN B.ProjectID IS NOT NULL AND D.BasePrice IS NOT NULL THEN B.VOPPB END AS VOPPA,
       D.BasePrice
INTO #TempBindProjID
FROM obpm_LianCheng_Data.dbo.TLK_小区信息管理 a
left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       f
    on a.ITEM_DISTRICT1=f.ITEM_DICVALUE and f.ITEM_DICTYPE='区域'
left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       g
    on a.ITEM_SECTOR121=g.ITEM_DICVALUE and g.ITEM_DICTYPE='板块'
left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       h
    on a.ITEM_PROPERTYLEVEL=h.ITEM_DICVALUE and h.ITEM_DICTYPE='物业档次'
LEFT JOIN (SELECT ProjectID, VOPPBT, VOPPB FROM #DWA_PROJECTBASEPRICE_IMDT_STEP_2 WHERE ABS(VOPPB) < 0.1) B
ON A.item_BindProjID = B.ProjectID
LEFT JOIN (SELECT ProjectID, VOPPBT, VOPPB FROM #DWA_PROJECTBASEPRICE_IMDT_STEP_2 WHERE ABS(VOPPB) < 0.1) C
ON A.ITEM_AIRAID = C.ProjectID
LEFT JOIN ODS_PROJECT_PRICE_INFO_#lastYearMonth# D
ON A.ITEM_AIRAID = D.ProjectID AND D.Status=1
where a.ITEM_ISBUILDINDEX='1' and a.ITEM_RUNNINGSTATE='1' and a.ITEM_PROJECTTYPE='1';

-- 2.板块+绑定
SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.BindClassID, A.BindProjID, A.Bind_Proj_Pst,
			 B.Bind_Block_Class, B.Bind_Block_Class_Pst,
			 CASE WHEN A.VOPPAT IS NOT NULL THEN A.VOPPAT
            WHEN B.Bind_Block_Class IS NOT NULL AND A.BasePrice IS NOT NULL THEN '8501' END AS VOPPAT,
			 CASE WHEN A.VOPPA IS NOT NULL THEN A.VOPPA
            WHEN B.Bind_Block_Class IS NOT NULL AND A.BasePrice IS NOT NULL THEN B.Bind_Block_Class_Pst END AS VOPPA,
       A.BasePrice
INTO #Temp_Bind_Block_Class
FROM #TempBindProjID A
LEFT JOIN (
	SELECT Block+BindClassID AS Bind_Block_Class, AVG(VOPPA) AS Bind_Block_Class_Pst
	FROM #TempBindProjID GROUP BY Block+BindClassID
) B
ON A.Block +A.BindClassID = B.Bind_Block_Class AND B.Bind_Block_Class_Pst IS NOT NULL


-- 3.板块+物业档次
SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.BindClassID, A.BindProjID, A.Bind_Proj_Pst,
			 A.Bind_Block_Class, A.Bind_Block_Class_Pst, B.Bind_Block_Plevel, B.Bind_Block_Plevel_Pst,
			 CASE WHEN A.VOPPAT IS NOT NULL THEN A.VOPPAT
            WHEN B.Bind_Block_Plevel IS NOT NULL AND A.BasePrice IS NOT NULL THEN '8502' END AS VOPPAT,
			 CASE WHEN A.VOPPA IS NOT NULL THEN A.VOPPA
            WHEN B.Bind_Block_Plevel IS NOT NULL AND A.BasePrice IS NOT NULL THEN B.Bind_Block_Plevel_Pst END AS VOPPA,
       A.BasePrice
INTO #Temp_Bind_Block_Plevel
FROM #Temp_Bind_Block_Class A
LEFT JOIN (
	SELECT Block+ProjectLevel AS Bind_Block_Plevel, AVG(VOPPA) AS Bind_Block_Plevel_Pst
	FROM #Temp_Bind_Block_Class GROUP BY Block+ProjectLevel
) B
ON A.Block +A.ProjectLevel = B.Bind_Block_Plevel AND B.Bind_Block_Plevel_Pst IS NOT NULL
-- 4.板块+物业类型
SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.BindClassID, A.BindProjID, A.Bind_Proj_Pst,
			 A.Bind_Block_Class, A.Bind_Block_Class_Pst, A.Bind_Block_Plevel, A.Bind_Block_Plevel_Pst,
			 B.Bind_Block_PType, B.Bind_Block_PType_Pst,
			 CASE WHEN A.VOPPAT IS NOT NULL THEN A.VOPPAT
            WHEN B.Bind_Block_PType IS NOT NULL AND A.BasePrice IS NOT NULL THEN '8503' END AS VOPPAT,
			 CASE WHEN A.VOPPA IS NOT NULL THEN A.VOPPA
            WHEN B.Bind_Block_PType IS NOT NULL AND A.BasePrice IS NOT NULL THEN B.Bind_Block_PType_Pst END AS VOPPA,
       A.BasePrice
INTO #Temp_Bind_Block_PType
FROM #Temp_Bind_Block_Plevel A
LEFT JOIN (
	SELECT Block+ProjectTypeDtl AS Bind_Block_PType, AVG(VOPPA) AS Bind_Block_PType_Pst
	FROM #Temp_Bind_Block_Plevel GROUP BY Block+ProjectTypeDtl
) B
ON A.Block+A.ProjectTypeDtl = B.Bind_Block_PType AND B.Bind_Block_PType_Pst IS NOT NULL
-- 5.区域+物业类型
SELECT A.ProjectID, A.Block, A.County, A.ProjectLevel, A.ProjectTypeDtl, A.BindClassID, A.BindProjID, A.Bind_Proj_Pst,
			 A.Bind_Block_Class, A.Bind_Block_Class_Pst, A.Bind_Block_Plevel, A.Bind_Block_Plevel_Pst,
			 A.Bind_Block_PType, A.Bind_Block_PType_Pst, B.Bind_County_PType, B.Bind_County_PType_Pst,
			 CASE WHEN A.VOPPAT IS NOT NULL THEN A.VOPPAT
            WHEN B.Bind_County_PType IS NOT NULL AND A.BasePrice IS NOT NULL THEN '8504' END AS VOPPAT,
			 CASE WHEN A.VOPPA IS NOT NULL THEN A.VOPPA
            WHEN B.Bind_County_PType IS NOT NULL AND A.BasePrice IS NOT NULL THEN B.Bind_County_PType_Pst END AS VOPPA,
       A.BasePrice
INTO #Temp_Bind_County_PType
FROM #Temp_Bind_Block_PType A
LEFT JOIN (
	SELECT County+ProjectTypeDtl AS Bind_County_PType, AVG(VOPPA) AS Bind_County_PType_Pst
	FROM #Temp_Bind_Block_PType GROUP BY County+ProjectTypeDtl
) B
ON A.County +A.ProjectTypeDtl = B.Bind_County_PType AND B.Bind_County_PType_Pst IS NOT NULL

--插入结果 (11-39 自动计算; 8505 绑定相同ID平均值; 8506混合小区ID; 8501板块+绑定; 8502板块+物业档次; 8503板块+类型; 8504区域+类型)
--6. 上周期有基价、当周期没有涨跌幅的的小区，沿用上周期基价
INSERT INTO #DWA_PROJECTBASEPRICE_IMDT_STEP_3
SELECT ProjectID, BindProjID, Bind_Proj_Pst,
			 Bind_Block_Class, Bind_Block_Class_Pst, Bind_Block_Plevel, Bind_Block_Plevel_Pst,
			 Bind_Block_PType, Bind_Block_PType_Pst, Bind_County_PType, Bind_County_PType_Pst,
       '沿用上周期基价' as Bind_Price_1_PType, 0 as Bind_Price_1_PType_Pst,
       CASE WHEN A.VOPPAT IS NOT NULL THEN A.VOPPAT
            WHEN A.BasePrice IS NOT NULL THEN '9999' END AS VOPPAT,
			 CASE WHEN A.VOPPA IS NOT NULL THEN A.VOPPA
            WHEN A.BasePrice IS NOT NULL THEN 0 END AS VOPPA
FROM #Temp_Bind_County_PType A


DROP TABLE #TempBindProjID
DROP TABLE #Temp_Bind_Block_Class
DROP TABLE #Temp_Bind_Block_Plevel
DROP TABLE #Temp_Bind_Block_PType
DROP TABLE #Temp_Bind_County_PType



----第四步；基价中间表的其他字段，包括主力基价的修正系数等等
create table #DWA_PROJECTBASEPRICE_IMDT_STEP_4
(
    ProjectID bigint primary key
  , ProjectSPLabel nvarchar(64)
  , PropertyType nvarchar(256)
  , ProjectType nvarchar(256)
  , ProjectTypeDtl nvarchar(256)
  , ProjectLevel nvarchar(64)
  , Year int
  , MainArea decimal(18, 2)
  , AreaCoff decimal(7, 4)
  , YearCoff decimal(7, 4)
  , PriceNote nvarchar(1024)
);


INSERT INTO #DWA_PROJECTBASEPRICE_IMDT_STEP_4
SELECT A.ITEM_AIRAID, A.ITEM_RALABLE, g.ITEM_DICTEXT, i.ITEM_DICTEXT, A.item_ProjectTypeDtl, h.ITEM_DICTEXT, A.ITEM_MAINCOMPLETIONYEAR, A.item_MainArea,
       A.item_AreaCoff, A.item_YearCoff, D.PriceNote
FROM obpm_LianCheng_Data.dbo.TLK_小区信息管理  A
left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       h
    on a.ITEM_PROPERTYLEVEL=h.ITEM_DICVALUE and h.ITEM_DICTYPE='物业档次'
left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       g
    on a.ITEM_RAPropertyType=g.ITEM_DICVALUE and g.ITEM_DICTYPE='居住物业类型'
left join obpm_LianCheng_Data.dbo.TLK_字典数据信息       i
    on a.ITEM_RAType=g.ITEM_DICVALUE and i.ITEM_DICTYPE='小区类型'
LEFT JOIN ODS_PROJECT_PRICE_INFO_#lastYearMonth# D
ON A.ITEM_AIRAID = D.ProjectID
where A.ITEM_PROJECTTYPE='1' and a.ITEM_RUNNINGSTATE='1' and a.ITEM_ISBUILDINDEX='1' and D.Status=1;

----第五步；整合到结果表
INSERT INTO DWA_PROJECTBASEPRICE_IMDT_#yearMonth#
SELECT A.ProjectID, A.ProjectName, A.ProjectAddr, A.County, A.Block, A.Loop, a.IsIndxGen, a.IsPstCalc, a.StatusRun, D.ProjectSPLabel, D.PropertyType, D.ProjectType,
       D.ProjectTypeDtl, D.ProjectLevel, D.[Year], D.MainArea, ISNULL(D.AreaCoff,1), ISNULL(D.YearCoff,1), D.PriceNote,
       A.BasePrice_1*(1+C.VOPPA) AS BasePriceDft,
       A.BasePrice_1*(1+C.VOPPA)*ISNULL(D.AreaCoff,1)*ISNULL(D.YearCoff,1) AS MainPriceDft,
       A.BasePrice_1, A.PriceUnitAdj, A.Visited_Num, A.First_Visit_Time, A.Visited_Num_15, A.Visited_Num_30,
       A.PriceDealMean_1, A.PriceDealMax_1, A.SumDeal_1, A.PriceDealMean, A.PriceDealMax, A.SumDeal,
       A.PriceListedMin, A.PriceCase1_ToAI_Pst, A.PriceCase2_ToAI_Pst, A.PriceCase1_ToLst_Pst, A.PriceCase2_ToLst_Pst,
       A.PriceCase1, A.PriceCase1AdjPst, A.SumCase1, A.PriceCase2, A.PriceCase2AdjPst, A.SumCase2, B.VOPPBT, B.VOPPB,
       C.BindProjID, C.Bind_Proj_Pst, C.Bind_Block_Class, C.Bind_Block_Class_Pst, C.Bind_Block_Plevel, C.Bind_Block_Plevel_Pst,
       C.Bind_Block_PType, C.Bind_Block_PType_Pst, C.Bind_County_PType, C.Bind_County_PType_Pst, C.VOPPAT, C.VOPPA
FROM #DWA_PROJECTBASEPRICE_IMDT_STEP_1 A
LEFT JOIN #DWA_PROJECTBASEPRICE_IMDT_STEP_2 B
ON A.ProjectID = B.ProjectID
LEFT JOIN #DWA_PROJECTBASEPRICE_IMDT_STEP_3 C
ON A.ProjectID = C.ProjectID
LEFT JOIN #DWA_PROJECTBASEPRICE_IMDT_STEP_4 D
ON A.ProjectID = D.ProjectID;


DROP TABLE #DWA_PROJECTBASEPRICE_IMDT_STEP_1
DROP TABLE #DWA_PROJECTBASEPRICE_IMDT_STEP_2
DROP TABLE #DWA_PROJECTBASEPRICE_IMDT_STEP_3
DROP TABLE #DWA_PROJECTBASEPRICE_IMDT_STEP_4
