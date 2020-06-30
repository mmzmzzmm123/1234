select a.BuildingID_p, a.ProjectID_p, a.MainPrice, a.MainPriceRent, a.MainPrice_1, a.MainPriceRent_1,
        getdate() as ModifyDate,
        case when b.BuildingID is not null and cast(a.MainPrice as decimal(18,1)) <> cast(isnull(b.MainPrice, 0) as decimal(18,1)) then 1 else 0 end as MainPriceChg,                   --指当期基价变化
        case when b.BuildingID is not null and cast(a.MainPriceRent as decimal(18,1)) <> cast(isnull(b.MainPriceRent, 0) as decimal(18,1)) then 1 else 0 end as MainPriceRentChg,       --指当期租金变化
        case when c.BuildingID is not null and cast(a.MainPrice_1 as decimal(18,1)) <> cast(isnull(c.MainPrice, 0) as decimal(18,1)) then 1 else 0 end as MainPrice_1Chg,               --指上期基价变化
        case when c.BuildingID is not null and cast(a.MainPriceRent_1 as decimal(18,1)) <> cast(isnull(c.MainPriceRent, 0) as decimal(18,1)) then 1 else 0 end as MainPriceRent_1Chg    --指上期租金变化
into #InfoChg
from DWA_PROJECTBASEPRICE_OFFICE_MANU_#yearMonth# a
left join ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth# b
on a.BuildingID = b.BuildingID and b.status=1 and (
    cast(a.MainPrice as decimal(18,1)) <> cast(isnull(b.MainPrice, 0) as decimal(18,1)) or
    cast(a.MainPriceRent as decimal(18,1)) <> cast(isnull(b.MainPriceRent, 0) as decimal(18,1)))
left join ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# c
on a.BuildingID = c.BuildingID and c.status=1 and (
    cast(a.MainPrice_1 as decimal(18,1)) <> cast(isnull(c.MainPrice, 0) as decimal(18,1)) or
    cast(a.MainPriceRent_1 as decimal(18,1)) <> cast(isnull(c.MainPriceRent, 0) as decimal(18,1)))
where b.BuildingID is not null or c.BuildingID is not null;

--价格调整
select a.BuildingID, a.UnifiedID, a.ProjectID, a.BuildingID_P, a.ProjectID_P, b.MainPrice_1, b.MainPriceRent_1,
        a.MainPricePst, a.MainPriceRentPst,
        a.MainPriceType, a.MainPriceRentType, b.ModifyDate, 1 as Status, a.BuildingStd,
        case when MainPrice_1Chg=0 then '' else 'MainPrice_1Chg|' end + case when MainPriceRent_1Chg=0 then '' else 'MainPriceRent_1Chg|' end as AdjEvd
into #InfoChgLst
from ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# a
inner join #InfoChg b
on a.BuildingID_p = b.BuildingID_p
where (b.MainPrice_1Chg=1 or b.MainPriceRent_1Chg=1) and a.status=1;

update a
set a.Status = 0
from ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# a
left join #InfoChgLst b
on a.BuildingID_p = b.BuildingID_P
where b.BuildingID_p is not null and a.status=1;

insert into ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth#
(
    id
  , BuildingID
  , UnifiedID
  , ProjectID
  , BuildingID_P
  , ProjectID_P
  , MainPrice
  , MainPriceRent
  , MainPricePst
  , MainPriceRentPst
  , MainPriceType
  , MainPriceRentType
  , ModifyDate
  , Status
  , BuildingStd
  , AdjEvd
  , MainPrice_1
  , MainPriceRent_1
  , AreaCoff
  , YearCoff
  , BuildingCoff
  , ProjectName
  , ProjectAddr
  , BuildingAddr
  , County
  , Loop
  , Block
  , Street
  , Year
  , AvgArea
  , TotalFloorSum
  , UpperFloorSum
  , OfficeClass
  , Grade
)
select
	newid()
  , BuildingID
  , UnifiedID
  , ProjectID
  , BuildingID_P
  , ProjectID_P
  , MainPrice
  , MainPriceRent
  , MainPricePst
  , MainPriceRentPst
  , MainPriceType
  , MainPriceRentType
  , ModifyDate
  , Status
  , BuildingStd
  , AdjEvd
  , MainPrice_1
  , MainPriceRent_1
  , AreaCoff
  , YearCoff
  , BuildingCoff
  , ProjectName
  , ProjectAddr
  , BuildingAddr
  , County
  , Loop
  , Block
  , Street
  , Year
  , AvgArea
  , TotalFloorSum
  , UpperFloorSum
  , OfficeClass
  , Grade
from #InfoChgLst;

--当周期价格调整
select a.BuildingID, a.UnifiedID, a.ProjectID, a.BuildingID_P, a.ProjectID_P, b.MainPrice, b.MainPriceRent,
        b.MainPrice*1.0/c.MainPrice as MainPricePst, b.MainPriceRent*1.0/c.MainPriceRent as MainPriceRentPst,
        a.MainPriceType, a.MainPriceRentType, b.ModifyDate, 1 as Status, a.BuildingStd,
        case when MainPriceChg=0 then '' else 'MainPriceChg|' end + case when MainPriceRentChg=0 then '' else 'MainPriceRentChg|' end as AdjEvd
into #InfoChgCurr
from ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth# a
inner join #InfoChg b
on a.BuildingID_p = b.BuildingID_p
left join ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# c
on a.BuildingID_p = c.BuildingID_p
where (b.MainPriceChg=1 or b.MainPriceRentChg=1) and a.status=1 and c.Status=1;

update a
set a.Status = 0
from ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth# a
left join #InfoChgCurr b
on a.BuildingID_p = b.BuildingID_p
where b.BuildingID_p is not null and a.status=1;

insert into dbo.ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth#
(
    id
  , BuildingID
  , UnifiedID
  , ProjectID
  , BuildingID_P
  , ProjectID_P
  , MainPrice
  , MainPriceRent
  , MainPricePst
  , MainPriceRentPst
  , MainPriceType
  , MainPriceRentType
  , ModifyDate
  , Status
  , BuildingStd
  , AdjEvd
  , MainPrice_1
  , MainPriceRent_1
  , AreaCoff
  , YearCoff
  , BuildingCoff
  , ProjectName
  , ProjectAddr
  , BuildingAddr
  , County
  , Loop
  , Block
  , Street
  , Year
  , AvgArea
  , TotalFloorSum
  , UpperFloorSum
  , OfficeClass
  , Grade
)
select
	newid()
  , BuildingID
  , UnifiedID
  , ProjectID
  , BuildingID_P
  , ProjectID_P
  , MainPrice
  , MainPriceRent
  , MainPricePst
  , MainPriceRentPst
  , MainPriceType
  , MainPriceRentType
  , ModifyDate
  , Status
  , BuildingStd
  , AdjEvd
  , MainPrice_1
  , MainPriceRent_1
  , AreaCoff
  , YearCoff
  , BuildingCoff
  , ProjectName
  , ProjectAddr
  , BuildingAddr
  , County
  , Loop
  , Block
  , Street
  , Year
  , AvgArea
  , TotalFloorSum
  , UpperFloorSum
  , OfficeClass
  , Grade
from #InfoChgCurr

drop table #InfoChg;
drop table #InfoChgCurr;
drop table #InfoChgLst;
