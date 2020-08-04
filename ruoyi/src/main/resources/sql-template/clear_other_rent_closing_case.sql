delete from ODS_HOUSINGCASEDEAL_RENT_#yearMonth# where Origin='太平洋';

create table #HousingCaseListAdj
(
    case_id varchar(32) not null
  , HouseholdsID_SRC nvarchar(64) primary key
  , ProjectID_SRC nvarchar(64) not null
  , ProjectID nvarchar(64)
  , BuildingID nvarchar(64)
  , RentType tinyint
  , Area decimal(18, 2)
  , Towards nvarchar(64)
  , UpperFloorSum nvarchar(32)
  , UpperFloorNum nvarchar(32)
  , Decoration nvarchar(64)
  , PriceTotal decimal(18, 2) not null
  , PriceUnit decimal(18, 2) not null
  , PriceDateOut date
  , CaseName nvarchar(512)
);

declare @HouseholdsID_SRC nvarchar(64)
      , @CaseName         nvarchar(512)
      , @ProjectID        nvarchar(64)
      , @BuildingID       nvarchar(64)
      , @roomtype         nvarchar(50)
      , @UpperFloorSum    nvarchar(32)
	  , @UpperFloorNum     nvarchar(32)
      , @area             decimal(28, 2)
      , @towards          nvarchar(50)
      , @decora           nvarchar(64)
      , @price            decimal(28, 2)
      , @dueDate          date
      , @case_id          varchar(32)
	  , @RentType    tinyint
      , @priceunit   decimal(28, 2);
declare Record2Insert cursor for
SELECT case_id
	  ,case_id
      ,case_community_name
	  ,case_closing_date
	  ,case_total_price
	  ,case_area
	  ,case_total_floor
      ,case_current_floor
	  ,case_toward
	  ,case_decoration
	  ,uv_community_id
      ,uv_building_id
FROM dbo.TEMP_ODS_HOUSINGCASEDEAL_RENT_#yearMonth#_RAW;
open Record2Insert;
fetch next from Record2Insert
into @case_id
   , @HouseholdsID_SRC
   , @CaseName
   , @dueDate
   , @price
   , @area
   , @UpperFloorSum
   , @UpperFloorNum
   , @towards
   , @decora
   , @ProjectID
   , @BuildingID;
set nocount on; --忽略行数显示
while @@fetch_status = 0
begin
    begin
        --租赁类型
        set @RentType = case
                            when @area < 30 then
                                3
                            else
                                2
                        end; --散租
        --面积
        set @area = case
                        when @area <> 0 then
                            @area
                    end;
        --单价
        set @priceunit = @price * 1.0 / isnull(@area, 1.0);
        --插入数据
        insert into #HousingCaseListAdj(case_id,HouseholdsID_SRC,ProjectID_SRC,ProjectID,BuildingID,RentType,Area,Towards,
        UpperFloorSum,UpperFloorNum,Decoration,PriceTotal,PriceUnit,PriceDateOut,CaseName)
        values
        (@case_id, @HouseholdsID_SRC, '-1', @ProjectID, @BuildingID, @RentType, @area
       , @towards, @UpperFloorSum, @UpperFloorNum, @decora, @price, @priceunit, @dueDate, @CaseName);
    end;
    fetch next from Record2Insert
    into @case_id
       , @HouseholdsID_SRC
       , @CaseName
       , @dueDate
       , @price
       , @area
       , @UpperFloorSum
       , @UpperFloorNum
       , @towards
       , @decora
       , @ProjectID
       , @BuildingID;
end;
close Record2Insert;
deallocate Record2Insert;
set nocount off;

create table #HousingCaseListSTD
(
    case_id varchar(32)
  , HouseholdsID_SRC nvarchar(64)
  , ProjectID_SRC nvarchar(64)
  , ProjectID nvarchar(64)
  , BuildingID nvarchar(64)
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
  , PriceShareRent decimal(18, 2)   --合租折算价
);

insert into #HousingCaseListSTD
select a.case_id
	 , A.HouseholdsID_SRC
     , A.ProjectID_SRC
     , A.ProjectID
     , null
     , D.CoefficientA * A.Area + D.CoefficientB                                                                    as AreaCoff
     , E.Coefficient                                                                                               as TowardsCoff
     , F.Coefficient                                                                                               as FloorCoff
     , G.Coefficient                                                                                               as DecorationRng
     , null                                                                                                        as YearCoff
     , null                                                                                                        as BuildingCoff
     , NULL                                                                                              as RoomTypeCoff
     , A.PriceUnit
     , (A.PriceUnit - isnull(G.Coefficient, 0)) * 1.0 / isnull(D.CoefficientA * A.Area + D.CoefficientB, 1)
       / isnull(E.Coefficient, 1) / isnull(F.Coefficient, 1)                             PriceScatterRent
     , (A.PriceUnit - isnull(G.Coefficient, 0)) * 1.0 / isnull(D.CoefficientA * A.Area + D.CoefficientB, 1)
       / isnull(E.Coefficient, 1) / isnull(F.Coefficient, 1)  + isnull(G.Coefficient, 0) PriceEntireRent
     , A.PriceTotal * 1.0 / isnull(F.Coefficient, 1) / isnull(E.Coefficient, 1)                                    as PriceShareRent0
     , null  as PriceShareRent
from #HousingCaseListAdj                                          A
    left join
    (select projectid,ProjectLevel from obpm_LianCheng_Data.dbo.V_RESIDENCE_COMMUNITY) C
        on A.ProjectID = C.projectid
    left join
    (
        select ProjectLevel
             , UpperKey
             , LowerKey
             , CoefficientA
             , CoefficientB
        from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
        where CoffType = '面积修正（租金）'
              and ProjectType = '公寓'
    )                                                             D --面积修正
        on C.ProjectLevel = D.ProjectLevel
           and
           (
               A.Area > D.LowerKey
               and A.Area <= D.UpperKey
           )
    left join
    (
        select SingleKey
             , Coefficient
        from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
        where CoffType = '朝向（租金）'
              and ProjectType = '公寓'
    )                                                             E --朝向修正
        on A.Towards = E.SingleKey
    left join
    (
        select UpperKey
             , LowerKey
             , Elevator
             , Coefficient
        from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
        where CoffType = '楼层（租金）'
              and ProjectType = '公寓'
    )                                                             F --楼层修正
        on F.Elevator is null
           and A.UpperFloorSum = F.UpperKey
           and A.UpperFloorNum = F.LowerKey
    left join
    (
        select SingleKey
             , Coefficient
        from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
        where CoffType = '室内装修（租金）'
              and ProjectType = '公寓'
    )                                                             G --装修修正
        on A.Decoration = G.SingleKey;


insert into ODS_HOUSINGCASEDEAL_RENT_#yearMonth#
select a.case_id
	 , A.HouseholdsID_SRC
     , A.ProjectID_SRC
     , A.ProjectID
     , A.BuildingID
     , A.RentType
     , NULL
     , NULL
     , NULL
     , A.Area
     , A.Towards
     , A.UpperFloorSum
     , A.UpperFloorNum
     , null         Elevator
     , A.Decoration
     , null         as Year
     , B.AreaCoff
     , B.TowardsCoff
     , B.FloorCoff
     , B.DecorationRng
     , B.YearCoff
     , B.BuildingCoff
     , B.RoomTypeCoff
     , A.PriceTotal
     , A.PriceUnit
     , B.PriceScatterRent
     , B.PriceEntireRent
     , B.PriceShareRent0
     , B.PriceShareRent
     , null
     , null
     , null
     , null
     , 4            as Status
     , null
     , null
     , null
     , null
     , null
     , null
     , null
     , A.PriceTotal PriceTotalIn
     , A.PriceTotal PriceTotalOut
     , NULL
     , A.PriceDateOut
     , '太平洋'
     , null
     , null
     , CaseName
from #HousingCaseListAdj          A
    left join #HousingCaseListSTD B
        on A.HouseholdsID_SRC = B.HouseholdsID_SRC;

drop table #HousingCaseListAdj
         , #HousingCaseListSTD;