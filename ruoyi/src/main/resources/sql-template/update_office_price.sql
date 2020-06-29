select a.BuildingID as BuildingID_P
     , a.ProjectID  as ProjectID_P
     , a.MainPrice
     , a.MainPriceRent
     , a.MainPrice_1
     , a.MainPriceRent_1
     , getdate()    as ModifyDate
     , case
           when b.BuildingID is not null
                and cast(a.MainPrice as decimal(18, 1)) <> cast(isnull(b.MainPrice, 0) as decimal(18, 1)) then
               1
           else
               0
       end          as MainPriceChg
     , case
           when b.BuildingID is not null
                and cast(a.MainPriceRent as decimal(18, 1)) <> cast(isnull(b.MainPriceRent, 0) as decimal(18, 1)) then
               1
           else
               0
       end          as MainPriceRentChg
     , case
           when c.BuildingID is not null
                and cast(a.MainPrice_1 as decimal(18, 1)) <> cast(isnull(c.MainPrice, 0) as decimal(18, 1)) then
               1
           else
               0
       end          as MainPrice_1Chg
     , case
           when c.BuildingID is not null
                and cast(a.MainPriceRent_1 as decimal(18, 1)) <> cast(isnull(c.MainPriceRent, 0) as decimal(18, 1)) then
               1
           else
               0
       end          as MainPriceRent_1Chg
into #InfoChg
from dbo.DWA_PROJECTBASEPRICE_OFFICE_MANU_#yearMonth#    a
    left join ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth# b
        on a.BuildingID = b.BuildingID_P
           and b.Status = 1
           and
           (
               cast(a.MainPrice as decimal(18, 1)) <> cast(isnull(b.MainPrice, 0) as decimal(18, 1))
               or cast(a.MainPriceRent as decimal(18, 1)) <> cast(isnull(b.MainPriceRent, 0) as decimal(18, 1))
           )
    left join ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# c
        on a.BuildingID = c.BuildingID_P
           and c.Status = 1
           and
           (
               cast(a.MainPrice_1 as decimal(18, 1)) <> cast(isnull(c.MainPrice, 0) as decimal(18, 1))
               or cast(a.MainPriceRent_1 as decimal(18, 1)) <> cast(isnull(c.MainPriceRent, 0) as decimal(18, 1))
           )
where b.BuildingID_P is not null
      or c.BuildingID_P is not null;


select a.UnifiedID
     , a.BuildingID_P
     , a.ProjectID_P
     , b.MainPrice_1
     , b.MainPriceRent_1
     , a.MainPricePst
     , a.MainPriceRentPst
     , a.MainPriceType
     , a.MainPriceRentType
     , b.ModifyDate
     , 1         as Status
     , a.BuildingStd
     , case
           when MainPrice_1Chg = 0 then
               ''
           else
               'MainPrice_1Chg|'
       end + case
                 when MainPriceRent_1Chg = 0 then
                     ''
                 else
                     'MainPriceRent_1Chg|'
             end as AdjEvd
into #InfoChgLst
from ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# a
    inner join #InfoChg                    b
        on a.BuildingID_P = b.BuildingID_P
where (
          b.MainPrice_1Chg = 1
          or b.MainPriceRent_1Chg = 1
      )
      and a.Status = 1;

update a
set a.Status = 0
from ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# a
    left join #InfoChgLst                  b
        on a.BuildingID_P = b.BuildingID_P
where b.BuildingID_P is not null
      and a.Status = 1;

insert into dbo.ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth#
(
    ID
  , UnifiedID
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
)
select newid()
     , UnifiedID
     , BuildingID_P
     , ProjectID_P
     , MainPrice_1
     , MainPriceRent_1
     , MainPricePst
     , MainPriceRentPst
     , MainPriceType
     , MainPriceRentType
     , getdate()
     , Status
     , BuildingStd
     , AdjEvd
from #InfoChgLst;

select a.UnifiedID
     , a.BuildingID_P
     , a.ProjectID_P
     , b.MainPrice
     , b.MainPriceRent
     , b.MainPrice * 1.0 / c.MainPrice         as MainPricePst
     , b.MainPriceRent * 1.0 / c.MainPriceRent as MainPriceRentPst
     , a.MainPriceType
     , a.MainPriceRentType
     , b.ModifyDate
     , 1                                       as Status
     , a.BuildingStd
     , case
           when MainPriceChg = 0 then
               ''
           else
               'MainPriceChg|'
       end + case
                 when MainPriceRentChg = 0 then
                     ''
                 else
                     'MainPriceRentChg|'
             end                               as AdjEvd
into #InfoChgCurr
from ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth#          a
    inner join #InfoChg                             b
        on a.BuildingID_P = b.BuildingID_P
    left join ODS_OFFICE_BUILDING_PRICE_INFO_#lastYearMonth# c
        on a.BuildingID = c.BuildingID_P
where (
          b.MainPriceChg = 1
          or b.MainPriceRentChg = 1
      )
      and a.Status = 1
      and c.Status = 1;

update a
set a.Status = 0
from ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth# a
    left join #InfoChgCurr                 b
        on a.BuildingID_P = b.BuildingID_P
where b.BuildingID_P is not null
      and a.Status = 1;

insert into dbo.ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth#
(
    ID
  , UnifiedID
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
)
select newid()
     , UnifiedID
     , BuildingID_P
     , ProjectID_P
     , MainPrice
     , MainPriceRent
     , MainPricePst
     , MainPriceRentPst
     , MainPriceType
     , MainPriceRentType
     , getdate()
     , Status
     , BuildingStd
     , AdjEvd
from #InfoChgCurr;


drop table #InfoChg;
drop table #InfoChgLst;
drop table #InfoChgCurr;


-- 更新上月基价；
update a
set a.ITEM_MAINAREAPRICE = b.MainPrice_1
from obpm_LianCheng_Data.dbo.TLK_基价信息              a
    join dbo.ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth# b
        on a.ITEM_AIRAID = b.ProjectID_P
where a.ITEM_PROJECTTYPE = 2
      and a.ITEM_PRICETYPE = 1
      and a.ITEM_VALUEPOINT = '#lastMonth#'
      and b.Status = 1
      and b.BuildingStd = 1;

update a
set a.ITEM_MAINAREAPRICE = b.MainPriceRent_1
from obpm_LianCheng_Data.dbo.TLK_基价信息              a
    join dbo.ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth# b
        on a.ITEM_AIRAID = b.ProjectID_P
where a.ITEM_PROJECTTYPE = 2
      and a.ITEM_PRICETYPE = 2
      and a.ITEM_VALUEPOINT = '#lastMonth#'
      and b.Status = 1
      and b.BuildingStd = 1

-- 插入本月新增信息；
insert into obpm_LianCheng_Data.dbo.TLK_基价信息
(LASTMODIFIED,FORMNAME,AUTHOR,AUTHOR_DEPT_INDEX,CREATED,FORMID,ISTMP,VERSIONS,APPLICATIONID,STATEINT,LASTMODIFIER,DOMAINID,AUDITORLIST,COAUDITORLIST,ID,ITEM_PriceID,ITEM_AIRAID,ITEM_PROJECTTYPE,ITEM_PRICETYPE,ITEM_STANDARDPRICE,ITEM_MAINAREAPRICE,ITEM_VALUEPOINT,ITEM_PRICEEXPLAIN,ITEM_UPDATEPERSON,ITEM_UPDATEDATE,ITEM_PRICECHG)
select getdate(),'物业信息管理/基价信息','amQRUkvYQAsAec1JGLp','Os6qe4Pmq5viTO8lTnW',getdate(),'ybte0OakLV17UzAyoVU',0,1,'Ts7TykYmuEzzZgWhXHj',0,'amQRUkvYQAsAec1JGLp','BclzHtmfLQoAA5ICTb5','{}','{}',newid(),newid(),ProjectID_P,2,1,null,MainPrice,'#today#',AdjEvd,'李贺',getdate(),MainPricePst
from dbo.ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth# a
join obpm_LianCheng_Data.dbo.TLK_基价信息 b
    on a.ProjectID_P=b.ITEM_AIRAID and b.ITEM_VALUEPOINT='#today#' and b.ITEM_PRICETYPE=1
where Status=1 and BuildingStd=1 and b.ID is null;

insert into obpm_LianCheng_Data.dbo.TLK_基价信息
(LASTMODIFIED,FORMNAME,AUTHOR,AUTHOR_DEPT_INDEX,CREATED,FORMID,ISTMP,VERSIONS,APPLICATIONID,STATEINT,LASTMODIFIER,DOMAINID,AUDITORLIST,COAUDITORLIST,ID,ITEM_PriceID,ITEM_AIRAID,ITEM_PROJECTTYPE,ITEM_PRICETYPE,ITEM_STANDARDPRICE,ITEM_MAINAREAPRICE,ITEM_VALUEPOINT,ITEM_PRICEEXPLAIN,ITEM_UPDATEPERSON,ITEM_UPDATEDATE,ITEM_PRICECHG)
select getdate(),'物业信息管理/基价信息','amQRUkvYQAsAec1JGLp','Os6qe4Pmq5viTO8lTnW',getdate(),'ybte0OakLV17UzAyoVU',0,1,'Ts7TykYmuEzzZgWhXHj',0,'amQRUkvYQAsAec1JGLp','BclzHtmfLQoAA5ICTb5','{}','{}',newid(),newid(),ProjectID_P,2,2,null,MainPriceRent,'#today#',AdjEvd,'李贺',getdate(),MainPriceRentPst
from dbo.ODS_OFFICE_BUILDING_PRICE_INFO_#yearMonth# a
join obpm_LianCheng_Data.dbo.TLK_基价信息 b
    on a.ProjectID_P=b.ITEM_AIRAID and b.ITEM_VALUEPOINT='#today#' and b.ITEM_PRICETYPE=2
where Status=1 and BuildingStd=1 and b.ID is null;

-- 插入t_document
insert into obpm_LianCheng_Data.dbo.T_DOCUMENT
(ID,LASTMODIFIED,FORMNAME,AUTHOR,AUTHOR_DEPT_INDEX,CREATED,FORMID,ISTMP,VERSIONS,APPLICATIONID,STATEINT,LASTMODIFIER,DOMAINID,AUDITORLIST,COAUDITORLIST,MAPPINGID)
 SELECT a.ID,a.LASTMODIFIED,a.FORMNAME,a.AUTHOR,a.AUTHOR_DEPT_INDEX,a.CREATED,a.FORMID,a.ISTMP,a.VERSIONS,a.APPLICATIONID,a.STATEINT,a.LASTMODIFIER,a.DOMAINID,a.AUDITORLIST,a.COAUDITORLIST,a.ID
from obpm_LianCheng_Data.dbo.TLK_基价信息            a
    left join obpm_LianCheng_Data.dbo.T_DOCUMENT b
        on a.ID = b.ID
where a.ITEM_VALUEPOINT='#today#' and a.ITEM_PROJECTTYPE=2 and b.id is null;

-- 计算之前，更新上个月的价格；
