create type [dbo].[DWA_PROJECTBASEPRICE_RENT_MANU_Table] as table(
	 ID int not null
    , ProjectID nvarchar(32) primary key
    , MainCoff_Rent decimal(7, 4)
    , RentPrice decimal(18, 2)
    , MainRentPrice decimal(18, 2)
    , RentPrice_1 decimal(18, 2)
    , VOPPAT varchar(64)
    , VOPPA decimal(18, 6)
    , ModifyDate date
)
go