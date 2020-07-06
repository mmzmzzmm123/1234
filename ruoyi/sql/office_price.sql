create type dbo.DWA_PROJECTBASEPRICE_OFFICE_MANU_Table as table
(
    BuildingID nvarchar(20) null
  , UnifiedID nvarchar(20) null
  , ProjectID nvarchar(20) null
  , BuildingID_P nvarchar(20) not null
  , ProjectID_P nvarchar(20) null
  , MainPrice decimal(38, 6) null
  , MainPriceRent decimal(38, 6) null
  , MainPricePst decimal(38, 6) null
  , MainPriceRentPst decimal(38, 6) null
  , MainPriceType varchar(2) null
  , MainPriceRentType varchar(2) null
  , ModifyDate date not null
  , Status bit not null
  , BuildingStd bit null
  , AdjEvd nvarchar(1000) null
  , MainPrice_1 decimal(18, 6) null
  , MainPriceRent_1 decimal(18, 6) null
  , AreaCoff decimal(18, 6) null
  , YearCoff decimal(18, 6) null
  , BuildingCoff decimal(18, 6) null
  , ProjectName nvarchar(200) null
  , ProjectAddr nvarchar(200) null
  , BuildingAddr nvarchar(200) null
  , County nvarchar(200) null
  , Loop nvarchar(200) null
  , Block nvarchar(200) null
  , Street nvarchar(200) null
  , Year int null
  , AvgArea decimal(10, 2) null
  , TotalFloorSum nvarchar(50) null
  , UpperFloorSum nvarchar(50) null
  , OfficeClass nvarchar(50) null
  , Grade nvarchar(50) null
)
go