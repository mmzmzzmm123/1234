truncate table ODS_HOUSINGCASEDEAL_RENT_#yearMonth#;
---中文数字转阿拉伯数字
select StringSub
     , cast(row_number() over (order by charindex(StringSub, '零|一|二|三|四|五|六|七|八|九|十|')) - 1 as varchar) as SID
into #tmp
from StringSplit('零|一|二|三|四|五|六|七|八|九|十|', '|');

create table #HousingCaseListAdj
(
    case_id varchar(32) not null
  , HouseholdsID_SRC nvarchar(64) primary key
  , ProjectID_SRC nvarchar(64) not null
  , ProjectID nvarchar(64)
  , BuildingID nvarchar(64)
  , RentType tinyint
  , Room tinyint
  , Hall tinyint
  , Bashroom tinyint
  , Area decimal(18, 2)
  , Towards nvarchar(64)
  , UpperFloorSum nvarchar(32)
  , UpperFloorNum nvarchar(32)
  , Decoration nvarchar(64)
  , PriceTotal decimal(18, 2) not null
  , PriceUnit decimal(18, 2) not null
  , PriceDateIn date
  , PriceDateOut date
  , CaseName nvarchar(512)
);


declare @HouseholdsID_SRC nvarchar(64)
      , @CaseName         nvarchar(512)
      , @ProjectID        nvarchar(64)
      , @BuildingID       nvarchar(64)
      , @roomtype         nvarchar(50)
      , @UpperFloorSum    nvarchar(32)
      , @area             decimal(28, 2)
      , @towards          nvarchar(50)
      , @decora           nvarchar(64)
      , @price            decimal(28, 2)
      , @listDate         date
      , @dueDate          date
      , @case_id          varchar(32);
declare @RentType    tinyint
      , @RoomOri     nvarchar(32)
      , @HallOri     nvarchar(32)
      , @BashroomOri nvarchar(32)
      , @Room        tinyint
      , @Hall        tinyint
      , @Bashroom    tinyint
      , @priceunit   decimal(28, 2);
declare Record2Insert cursor for
select case_id
     , case_contract_no
     , case_community_name
     , case_opening_date
     , case_closing_date
     , case_rent_price
     , case_area
     , case_total_floor
     , case_toward
     , case_apartment_layout
     , case_decoration
     , clean_community_id
     , clean_building_id
from ODS_HOUSINGCASEDEAL_RENT_#yearMonth#_RAW;
open Record2Insert;
fetch next from Record2Insert
into @case_id
   , @HouseholdsID_SRC
   , @CaseName
   , @listDate
   , @dueDate
   , @price
   , @area
   , @UpperFloorSum
   , @towards
   , @roomtype
   , @decora
   , @ProjectID
   , @BuildingID;
set nocount on; --忽略行数显示
while @@fetch_status = 0
begin
    begin
        --室厅卫
        set @RoomOri = case
                           when charindex('室', @roomtype, 1) <> 0 then
                               substring(@roomtype, charindex('室', @roomtype, 1) - 1, 1)
                       end;
        set @HallOri = case
                           when charindex('厅', @roomtype, 1) <> 0 then
                               substring(@roomtype, charindex('厅', @roomtype, 1) - 1, 1)
                       end;
        set @BashroomOri = case
                               when charindex('卫', @roomtype, 1) <> 0 then
                                   substring(@roomtype, charindex('卫', @roomtype, 1) - 1, 1)
                           end;
        select @Room = SID
        from #tmp
        where (StringSub = @RoomOri)
              or (SID = @RoomOri);
        select @Hall = SID
        from #tmp
        where (StringSub = @HallOri)
              or (SID = @HallOri);
        select @Bashroom = SID
        from #tmp
        where (StringSub = @BashroomOri)
              or (SID = @BashroomOri);

        --租赁类型
        set @RentType = case
                            when @area <= 20 then
                                3 --分租
                            when @area <= 30
                                 and @Room >= 2 then
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
        insert into #HousingCaseListAdj
        values
        (@case_id, @HouseholdsID_SRC, '-1', @ProjectID, @BuildingID, @RentType, @Room, @Hall, @Bashroom, @area
       , @towards, @UpperFloorSum, null, @decora, @price, @priceunit, @listDate, @dueDate, @CaseName);
    end;
    fetch next from Record2Insert
    into @case_id
       , @HouseholdsID_SRC
       , @CaseName
       , @listDate
       , @dueDate
       , @price
       , @area
       , @UpperFloorSum
       , @towards
       , @roomtype
       , @decora
       , @ProjectID
       , @BuildingID;
end;
close Record2Insert;
deallocate Record2Insert;
set nocount off;
drop table #tmp;


/*************案例标准化*************/
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
     , null                                                                                                        BuildingCoff
     , J.Coefficient                                                                                               as RoomTypeCoff
     , A.PriceUnit
     , (A.PriceUnit - isnull(G.Coefficient, 0)) * 1.0 / isnull(D.CoefficientA * A.Area + D.CoefficientB, 1)
       / isnull(E.Coefficient, 1) / isnull(F.Coefficient, 1) / isnull(J.Coefficient, 1)                            PriceScatterRent
     , (A.PriceUnit - isnull(G.Coefficient, 0)) * 1.0 / isnull(D.CoefficientA * A.Area + D.CoefficientB, 1)
       / isnull(E.Coefficient, 1) / isnull(F.Coefficient, 1) / isnull(J.Coefficient, 1) + isnull(G.Coefficient, 0) PriceEntireRent
     , A.PriceTotal * 1.0 / isnull(F.Coefficient, 1) / isnull(E.Coefficient, 1)                                    as PriceShareRent0
     , case
           when A.Hall = 0 then
               A.PriceTotal * A.Room * 1.0 / isnull(F.Coefficient, 1) / isnull(E.Coefficient, 1)
           else
               A.PriceTotal * (A.Room + A.Hall - 1) * 1.0 / isnull(F.Coefficient, 1) / isnull(E.Coefficient, 1)
       end                                                                                                         as PriceShareRent
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
        from DIM_PARAMETERS
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
        from DIM_PARAMETERS
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
        from DIM_PARAMETERS
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
        from DIM_PARAMETERS
        where CoffType = '室内装修（租金）'
              and ProjectType = '公寓'
    )                                                             G --装修修正
        on A.Decoration = G.SingleKey
    left join
    (
        select Coefficient
             , UpperKey
             , LowerKey
        from DIM_PARAMETERS
        where CoffType = '室加厅修正（租金）'
              and ProjectType = '公寓'
    )                                                             J --室加厅修正
        on A.Room = J.UpperKey
           and A.Hall = J.LowerKey;


/**新建成交案例表**/
-- 20200330表结构修改：ODS_HOUSINGCASEDEAL_RENT_202003 新增字段：BuildingID, UrlHouseholds, UrlProjects, CaseName
insert into ODS_HOUSINGCASEDEAL_RENT_#yearMonth#
select a.case_id
	 , A.HouseholdsID_SRC
     , A.ProjectID_SRC
     , A.ProjectID
     , A.BuildingID
     , A.RentType
     , A.Room
     , A.Hall
     , A.Bashroom
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
     , A.PriceDateIn
     , A.PriceDateOut
     , '链家'
     , null
     , null
     , CaseName
from #HousingCaseListAdj          A
    left join #HousingCaseListSTD B
        on A.HouseholdsID_SRC = B.HouseholdsID_SRC;

drop table #HousingCaseListAdj
         , #HousingCaseListSTD;