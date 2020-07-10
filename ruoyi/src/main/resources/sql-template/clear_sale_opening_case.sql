truncate table Ods_HousingCaseListed_LJ_#yearMonth#

--建临时表#HousingCaseListAdj， 插入挂牌清洗数据
-- 20200312BUG修复：朝向处理规则优化
create table #HousingCaseListAdj
(
	case_id varchar(32) not null
  , HouseholdsID_LJ bigint
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

DECLARE @llid 						bigint,
        @lcid 						bigint,
        @roomtype 				nvarchar(50),
        @area 						decimal(28, 10),
        @towards 					nvarchar(50),
        @storey 					nvarchar(50),
        @condoElev 				nvarchar(50),
        @decoration 			nvarchar(50),
        @yr								int,
        @price 						DECIMAL(18,8),
        @priceunit				DECIMAL(18,8),
        @visited_num 	 	 	int,
        @first_visit_time date,
        @visited_num_15 	int,
        @visited_num_30 	int,
        @toward           nvarchar(50),
        @curr             int,
        @prev             int,
        @trimstr          nvarchar(32),
        @flag             int,
        @UpperFloorSum	  NVARCHAR(32),
        @UpperFloorNum	  NVARCHAR(32),
        @Elevator         tinyint,
		@case_id 			  varchar(32)

DECLARE Record2Insert CURSOR
    FOR select case_id
	 , llid
     , lcid
     , roomtype
     , area
     , towards
     , storey
     , condoElev
     , decoration
     , [year]
     , price
     , visited_num
     , first_visit_time
     , visited_num_15
     , visited_num_30
from ODS_HOUSINGCASELISTED_LJ_#yearMonth#_Raw;
OPEN Record2Insert
FETCH NEXT FROM Record2Insert into @case_id, @llid ,	@lcid ,	@roomtype ,	@area ,	@towards ,	@storey ,	@condoElev ,	@decoration ,	@yr, @price ,	@visited_num ,
  @first_visit_time ,	@visited_num_15 ,	@visited_num_30
SET NOCOUNT ON  --忽略行数显示
WHILE @@FETCH_STATUS=0
  BEGIN
    BEGIN
      --朝向处理（按照"东南,	西南,	西北,	东北,	南,	东,	西,	北"的优先级取朝向的值。注：表的一列赋值给单变量，只有最后赋的值有效）
      set @toward = NULL
			select @toward = StringSub from StringSplit(@towards, ' ')  order by charindex(StringSub, '北, 西, 东, 南, 东北, 西北, 西南, 东南')
      --楼层处理
      set @UpperFloorNum = case when substring(@storey,1,3)='高楼层' then '高区'
                                when substring(@storey,1,3)='中楼层' then '中区'
                                when substring(@storey,1,3)='低楼层' then '低区' end
      set @UpperFloorSum = substring(@storey,charindex('共',@storey,1)+1,charindex('层',@storey,charindex('共',@storey,1))-charindex('共',@storey,1)-1)
      --电梯处理
      set @Elevator = case when @condoElev='有' then 1
                           when @condoElev='无' then 0 end
      --装修处理
      set @decoration = case when @decoration <> '其他' then @decoration end
      --单价
      set @priceunit = @price*1.0/isnull(@area,1)
      --插入数据
      insert into #HousingCaseListAdj values(@case_id, @llid, @lcid, @roomtype, @area, @toward, @UpperFloorSum, @UpperFloorNum, @Elevator, @decoration, @yr, 10000*@price, 10000*@priceunit,
        @visited_num, @first_visit_time, @visited_num_15, @visited_num_30)
    END
    FETCH NEXT FROM Record2Insert INTO @case_id, @llid ,	@lcid ,	@roomtype ,	@area ,	@towards ,	@storey ,	@condoElev ,	@decoration ,	@yr, 	@price ,	@visited_num ,
      @first_visit_time ,	@visited_num_15 ,	@visited_num_30
  END
CLOSE Record2Insert
DEALLOCATE Record2Insert
SET NOCOUNT OFF


--案例标准化
create table #HousingCaseListSTD
(
    HouseholdsID_LJ bigint
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

INSERT INTO #HousingCaseListSTD
SELECT A.HouseholdsID_LJ, A.ProjectID_LJ, B.ProjectID, B.BuildingID,
			 D.CoefficientA*A.Area+D.CoefficientB as AreaCoff, E.Coefficient as TowardsCoff, F.Coefficient as FloorCoff,
			 G.Coefficient as DecorationRng, H.Coefficient as YearCoff, I.Coefficient as BuildingCoff, A.PriceUnit,
			 (A.PriceUnit-isnull(G.Coefficient,0))*1.0/isnull(D.CoefficientA*A.Area+D.CoefficientB,1)/isnull(E.Coefficient,1)/isnull(F.Coefficient,1)/isnull(H.Coefficient,1)/isnull(I.Coefficient,1)
FROM #HousingCaseListAdj A
LEFT JOIN  obpm_LianCheng_Data.dbo.DIM_PROJECTID_LJ2AI B
ON A.ProjectID_LJ = B.ProjectID_LJ
left join (select CommunityId,ProjectLevel from uv_compute.dbo.ResidenceCommunity) C
on B.ProjectID = C.CommunityId
LEFT JOIN (SELECT ProjectLevel,LowerKey,UpperKey,CoefficientA,CoefficientB FROM obpm_LianCheng_Data.dbo.DIM_PARAMETERS WHERE CoffType='面积修正（链家挂牌）' and ProjectType = '公寓') D	--面积修正
on C.ProjectLevel = D.ProjectLevel and ( A.Area > D.LowerKey and A.Area <= D.UpperKey)
LEFT JOIN (SELECT SingleKey,Coefficient FROM obpm_LianCheng_Data.dbo.DIM_PARAMETERS WHERE CoffType='朝向（链家挂牌）' and ProjectType = '公寓') E	--朝向修正
on A.Towards = E.SingleKey
LEFT JOIN (SELECT Elevator,UpperKey,LowerKey,Coefficient FROM obpm_LianCheng_Data.dbo.DIM_PARAMETERS WHERE CoffType='楼层（链家挂牌）' and ProjectType = '公寓') F	--楼层修正
on isnull(A.Elevator,99)=isnull(F.Elevator,99)  and A.UpperFloorSum = F.UpperKey and A.UpperFloorNum = F.LowerKey
LEFT JOIN (SELECT SingleKey,Coefficient FROM obpm_LianCheng_Data.dbo.DIM_PARAMETERS WHERE CoffType='室内装修（链家挂牌）' and ProjectType = '公寓') G --装修修正
on A.Decoration = G.SingleKey
LEFT JOIN (SELECT SingleKey,Coefficient FROM obpm_LianCheng_Data.dbo.DIM_PARAMETERS WHERE CoffType='竣工日期' and ProjectType = '公寓') H   --竣工日期修正
on A.[Year]=H.SingleKey
LEFT JOIN obpm_LianCheng_Data.dbo.DIM_BUILDING_COFF I
on B.BuildingID = I.BuildingID;


--当月拍照表调价信息
-- 20200205BUG修复：AdjustedValue、AdjustedPst 中当Status=1时，值不为0的问题
select
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
on a.HouseholdsID_LJ = b.HouseholdsID_LJ;

--插入当月未下架(活跃)案例数据
insert into Ods_HousingCaseListed_LJ_#yearMonth#
select a.case_id,a.HouseholdsID_LJ, a.ProjectID_LJ, d.ProjectID, a.Roomtype, a.Area, a.Towards, a.UpperFloorSum, a.UpperFloorNum, a.Elevator, a.Decoration, a.[Year],
       b.AreaCoff, b.TowardsCoff, b.FloorCoff, b.DecorationRng, b.YearCoff, b.BuildingCoff, a.PriceTotal, a.PriceUnit, b.PriceUnitAdj,
       a.Visited_Num, a.First_Visit_Time, a.Visited_Num_15, a.Visited_Num_30,
       c.Status,	c.AdjustedValue,	c.AdjustedPst,	c.AdjustedCumValue,	c.AdjustedCumPst,	c.AdjustedCumValueAbs,
       c.AdjustedCumPstAbs,	c.AdjustedCumNum,	c.PriceTotalIn,	c.PriceTotalOut,	c.PriceDateIn,	c.PriceDateOut
from #HousingCaseListAdj a
left join #HousingCaseListSTD b
on a.HouseholdsID_LJ = b.HouseholdsID_LJ
left join #HousingCaseListStatic c
on a.HouseholdsID_LJ = c.HouseholdsID_LJ
left join obpm_LianCheng_Data.dbo.DIM_PROJECTID_LJ2AI d
on a.ProjectID_LJ = d.ProjectID_LJ;


/*插入当月下架记录*/
select a.case_id, a.HouseholdsID_LJ,	a.ProjectID_LJ,	a.ProjectID, a.Roomtype,	a.Area,	a.Towards,	a.UpperFloorSum,	a.UpperFloorNum,	a.Elevator,	a.Decoration,	a.[Year],
    a.AreaCoff, a.TowardsCoff, a.FloorCoff, a.DecorationRng, a.YearCoff, a.BuildingCoff, a.PriceTotal, a.PriceUnit, a.PriceUnitAdj,
    a.Visited_Num, a.First_Visit_Time,	a.Visited_Num_15,	a.Visited_Num_30,
    4 as Status, a.AdjustedValue,	a.AdjustedPst,	a.AdjustedCumValue,	a.AdjustedCumPst,
    a.AdjustedCumValueAbs,	a.AdjustedCumPstAbs, a.AdjustedCumNum, a.PriceTotalIn,	a.PriceTotal as PriceTotalOut,	a.PriceDateIn, getdate() as PriceDateOut
into #TmpRecordAddCurr
from Ods_HousingCaseListed_LJ_#lastYearMonth# a
left join Ods_HousingCaseListed_LJ_#yearMonth# b
on a.HouseholdsID_LJ = b.HouseholdsID_LJ
where b.HouseholdsID_LJ is null and a.Status not in (4,5);

insert into dbo.Ods_HousingCaseListed_LJ_#yearMonth# select * from #TmpRecordAddCurr;


/*插入历史下架记录*/
select a.case_id,a.HouseholdsID_LJ,	a.ProjectID_LJ,	a.ProjectID, a.Roomtype,	a.Area,	a.Towards,	a.UpperFloorSum,	a.UpperFloorNum,	a.Elevator,	a.Decoration,	a.[Year],
    a.AreaCoff, a.TowardsCoff, a.FloorCoff, a.DecorationRng, a.YearCoff, a.BuildingCoff, a.PriceTotal, a.PriceUnit, a.PriceUnitAdj,
    a.Visited_Num, a.First_Visit_Time,	a.Visited_Num_15,	a.Visited_Num_30,	5 as Status,
    a.AdjustedValue,	a.AdjustedPst,	a.AdjustedCumValue,	a.AdjustedCumPst,
    a.AdjustedCumValueAbs,	a.AdjustedCumPstAbs, a.AdjustedCumNum, a.PriceTotalIn,	a.PriceTotal as PriceTotalOut,	a.PriceDateIn,	a.PriceDateOut
into #TmpRecordAddHis
from Ods_HousingCaseListed_LJ_#lastYearMonth# a
left join Ods_HousingCaseListed_LJ_#yearMonth# b
on a.HouseholdsID_LJ = b.HouseholdsID_LJ
where b.HouseholdsID_LJ is null and a.Status in (4,5);

insert into dbo.Ods_HousingCaseListed_LJ_#yearMonth# select * from #TmpRecordAddHis


drop table #HousingCaseListAdj
drop table #HousingCaseListSTD
drop table #HousingCaseListStatic
drop table #TmpRecordAddCurr
drop table #TmpRecordAddHis