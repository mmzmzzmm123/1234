
WITH STP AS(
  SELECT A.ProjectID, 2 AS PriceType, B.BasePrice AS BasePrice_1, A.RentPriceDft as RentPrice,  A.MainRentPriceDft as MainRentPrice,
         A.SumCase1, A.VOPPAT, A.VOPPA,
         CASE WHEN C.ProjectLevel = '工房1' THEN A.RentPriceDft*(1+24*0.012)*1.0/(1.0*(1+0.011*1.8))
              WHEN C.ProjectLevel = '工房2' THEN A.RentPriceDft*(1+26*0.012)*1.0/(1.0*(1+0.011*1.8))
         END AS GF_1Room,
         CASE WHEN C.ProjectLevel = '工房1' THEN A.RentPriceDft*(1+16*0.012)*1.0/(1.0*(1+0.011*1.8))
              WHEN C.ProjectLevel = '工房2' THEN A.RentPriceDft*(1+16*0.012)*1.0/(1.0*(1+0.011*1.8))
         END AS GF_2Room,
         CASE WHEN C.ProjectLevel = '低端公寓1' THEN A.RentPriceDft*1.0*(1+0.011*3.3)/(1.0*(1+0.011*1.8))
              WHEN C.ProjectLevel IN ('中端公寓','低端公寓2') THEN A.RentPriceDft*1.0*(1+0.011*3.3)/(1.0*(1+0.011*1.8))
              WHEN C.ProjectLevel IN ('高端公寓','超高端公寓') THEN A.RentPriceDft*(1+5*0.012)*(1+0.011*3.3)/((1.0+0.011*1.8))
         END AS GY_2Room,
         CASE WHEN C.ProjectLevel = '低端公寓1' THEN A.RentPriceDft*(1-3*0.012)*(1+0.011*4.25)/(1*(1+0.011*1.8))
              WHEN C.ProjectLevel = '低端公寓2' THEN A.RentPriceDft*(1-4*0.012)*(1+0.011*4.25)/(1*(1+0.011*1.8))
              WHEN C.ProjectLevel = '中端公寓' THEN A.RentPriceDft*(1-3*0.012)*(1+0.011*4.25)/(1*(1+0.011*1.8))
              WHEN C.ProjectLevel IN ('高端公寓','超高端公寓') THEN A.RentPriceDft*(1+4*0.012)*(1+0.011*4.25)/((1+0.011*1.8))
         END AS GY_3Room
  FROM DWA_PROJECTBASEPRICE_RENT_IMDT_#yearMonth# A
  LEFT JOIN ODS_PROJECT_PRICE_INFO_#lastYearMonth# B
  ON A.ProjectID = B.ProjectID AND B.STATUS = 1
  LEFT JOIN obpm_LianCheng_Data.dbo.ResidenceCommunity C
  ON A.ProjectID = C.CommunityId
)
INSERT INTO ODS_PROJECT_RENT_PRICE_INFO_#yearMonth#
SELECT A.ProjectID, A.PriceType, A.BasePrice_1, A.RentPrice, A.SumCase1, A.VOPPAT, A.VOPPA, A.GF_1Room, A.GF_2Room, A.GY_2Room, A.GY_3Room,
       A.GF_1Room*35 AS One_Room,
       CASE WHEN A.GF_2Room IS NOT NULL THEN A.GF_2Room*50 ELSE GY_2Room*90 END AS Two_Room,
       A.GY_3Room*105 AS Three_Three_Room, B.EntireRentRatio, B.EntireRentNum,
       B.ShareRentRatio, B.ShareRentNum, A.RentPrice*12/A.BasePrice_1 AS SaleRentRatio, CONVERT(DATE,NULL) AS ModifyDate,
       1 AS Status, NULL AdjEvd
FROM STP A
LEFT JOIN (
  SELECT ProjectID,
         AVG(CASE WHEN RangeFlag = 30 THEN EntireRentRatio END) AS EntireRentRatio,
         SUM(CASE WHEN RangeFlag = 30 THEN 1 END) AS EntireRentNum,
         AVG(CASE WHEN RangeFlag = 40 THEN ShareRentRatio END) AS ShareRentRatio,
         SUM(CASE WHEN RangeFlag = 40 THEN 1 END) AS ShareRentNum
  FROM DW_HOUSINGCASE_RENT_COMM_#yearMonth#
  GROUP BY ProjectID
) B ON A.ProjectID = B.ProjectID;

/*******************租金价格修改数据***********************/
--调整过程
---下期小区基础信息调整（如：租金主力面积系数调整）
select a.ProjectID, a.RentPrice, a.RentPrice_1, a.VOPPAT, a.VOPPA, GETDATE() AS ModifyDate,
        case when b.ProjectID is not null and isnull(a.RentPrice,0) <> isnull(b.RentPrice,0) then 1 else 0 end as RentPriceChg,
        case when b.ProjectID is not null and isnull(a.VOPPAT,'') <> isnull(b.VOPPAT,'') then 1 else 0 end as VOPPATChg,  -- 指当期价格涨跌幅类型字段变化
        case when b.ProjectID is not null and isnull(a.VOPPA,0) <> isnull(b.VOPPA,0) then 1 else 0 end as VOPPAChg,  -- 指当期价格涨跌幅字段变化
        case when c.ProjectID is not null then 1 else 0 end as RentPrice_1Chg
into #InfoChg
from DWA_PROJECTBASEPRICE_RENT_MANU_#yearMonth# a
left join ODS_PROJECT_RENT_PRICE_INFO_#yearMonth# b
on a.ProjectID = b.ProjectID and b.status=1 and (
    isnull(a.RentPrice,0) <> isnull(b.RentPrice,0) or
    isnull(a.VOPPAT,'') <> isnull(b.VOPPAT,'') or
    isnull(a.VOPPA,0) <> isnull(b.VOPPA,0))
left join ODS_PROJECT_RENT_PRICE_INFO_#lastYearMonth# c
on a.ProjectID = c.ProjectID and c.status=1 and isnull(a.RentPrice_1,0) <> isnull(c.RentPrice,0)
where b.ProjectID is not null or c.ProjectID is not null;

select a.ProjectID, a.PriceType, a.BasePrice_1, b.RentPrice, a.SumCase1, b.VOPPAT, b.VOPPA,
        a.GF_1Room*b.RentPrice/a.RentPrice as GF_1Room,
        a.GF_2Room*b.RentPrice/a.RentPrice as GF_2Room,
        a.GY_2Room*b.RentPrice/a.RentPrice as GY_2Room,
        a.GY_3Room*b.RentPrice/a.RentPrice as GY_3Room,
        a.One_Room*b.RentPrice/a.RentPrice as One_Room,
        a.Two_Room*b.RentPrice/a.RentPrice as Two_Room,
        a.Three_Room*b.RentPrice/a.RentPrice as Three_Room,
        a.EntireRentRatio*b.RentPrice/a.RentPrice as EntireRentRatio,
        a.EntireRentNum,
        a.ShareRentRatio*b.RentPrice/a.RentPrice as ShareRentRatio,
        a.ShareRentNum,
        a.SaleRentRatio*b.RentPrice/a.RentPrice as SaleRentRatio,
        b.ModifyDate, 1 as Status,
        case when RentPriceChg=0 then '' else 'RentPriceChg|' end +
        case when VOPPATChg=0 then '' else 'VOPPATChg|' end +
        case when VOPPAChg=0 then '' else 'VOPPAChg|' end as AdjEvd
into #InfoChgCurr
from ODS_PROJECT_RENT_PRICE_INFO_#yearMonth# a
inner join #InfoChg b
on a.ProjectID = b.ProjectID
where (b.RentPriceChg=1 or b.VOPPATChg=1 or b.VOPPAChg=1) and a.status=1;

update a
set a.Status = 0
from ODS_PROJECT_RENT_PRICE_INFO_#yearMonth# a
left join #InfoChgCurr b
on a.ProjectID = b.ProjectID
where b.ProjectID is not null and a.status=1;

insert into ODS_PROJECT_RENT_PRICE_INFO_#yearMonth#
select * from #InfoChgCurr;

----上期价格调整
select a.ProjectID, a.PriceType, a.BasePrice_1, b.RentPrice_1, a.SumCase1, a.VOPPAT, a.VOPPA,
        a.GF_1Room*b.RentPrice_1/a.RentPrice as GF_1Room,
        a.GF_2Room*b.RentPrice_1/a.RentPrice as GF_2Room,
        a.GY_2Room*b.RentPrice_1/a.RentPrice as GY_2Room,
        a.GY_3Room*b.RentPrice_1/a.RentPrice as GY_3Room,
        a.One_Room*b.RentPrice_1/a.RentPrice as One_Room,
        a.Two_Room*b.RentPrice_1/a.RentPrice as Two_Room,
        a.Three_Room*b.RentPrice_1/a.RentPrice as Three_Room,
        a.EntireRentRatio*b.RentPrice_1/a.RentPrice as EntireRentRatio,
        a.EntireRentNum,
        a.ShareRentRatio*b.RentPrice_1/a.RentPrice as ShareRentRatio,
        a.ShareRentNum,
        a.SaleRentRatio*b.RentPrice_1/a.RentPrice as SaleRentRatio,
        b.ModifyDate, 1 as Status,
        'RentPrice_1Chg|'  as AdjEvd
into #InfoChgLst
from ODS_PROJECT_RENT_PRICE_INFO_#lastYearMonth# a
inner join #InfoChg b
on a.ProjectID = b.ProjectID
where b.RentPrice_1Chg=1 and a.status=1;

update a
set a.Status = 0
from ODS_PROJECT_RENT_PRICE_INFO_#lastYearMonth# a
left join #InfoChgLst b
on a.ProjectID = b.ProjectID
where b.ProjectID is not null and a.status=1;

insert into ODS_PROJECT_RENT_PRICE_INFO_#lastYearMonth#
select * from #InfoChgLst;

drop table #InfoChg, #InfoChgCurr, #InfoChgLst

更新上月价格
update a
set a.ITEM_MAINAREAPRICE = b.RentPrice
from obpm_LianCheng_Data.dbo.TLK_基价信息       a
    join dbo.ODS_PROJECT_RENT_PRICE_INFO_#lastYearMonth# b
        on a.ITEM_AIRAID = b.ProjectID
where b.Status = 1
      and a.ITEM_VALUEPOINT = '#lastPriceDate#'
      and a.ITEM_PRICETYPE = '2'
      and a.ITEM_PROJECTTYPE = '1'
      and a.ITEM_MAINAREAPRICE <> b.RentPrice;

-- 插入新的价格
insert into obpm_LianCheng_Data.dbo.TLK_基价信息
(
    ID
  , LASTMODIFIED
  , FORMNAME
  , AUTHOR
  , AUTHOR_DEPT_INDEX
  , CREATED
  , FORMID
  , ISTMP
  , VERSIONS
  , APPLICATIONID
  , LASTMODIFIER
  , DOMAINID
  , ITEM_PriceID
  , ITEM_AIRAID
  , ITEM_PROJECTTYPE
  , ITEM_PRICETYPE
  , ITEM_MAINAREAPRICE
  , ITEM_VALUEPOINT
  , ITEM_PRICEEXPLAIN
  , ITEM_UPDATEPERSON
  , ITEM_UPDATEDATE
  , ITEM_PRICECHG
)
select newid()
     , getdate()
     , '物业信息管理/基价信息'
     , 'amQRUkvYQAsAec1JGLp'
     , 'amQRUkvYQAsAec1JGLp'
     , getdate()
     , 'ybte0OakLV17UzAyoVU'
     , 0
     , 1
     , 'Ts7TykYmuEzzZgWhXHj'
     , 'amQRUkvYQAsAec1JGLp'
     , 'BclzHtmfLQoAA5ICTb5'
     , newid()
     , ProjectID
     , 2
     , 2
     , RentPrice
     , '#priceDate#'
     , AdjEvd
     , '李贺'
     , getdate()
     , VOPPA
from dbo.ODS_PROJECT_RENT_PRICE_INFO_#yearMonth#
where Status = 1;

insert into obpm_LianCheng_Data.dbo.T_DOCUMENT
(
    ID
  , LASTMODIFIED
  , FORMNAME
  , AUTHOR
  , AUTHOR_DEPT_INDEX
  , CREATED
  , FORMID
  , ISTMP
  , VERSIONS
  , APPLICATIONID
  , LASTMODIFIER
  , DOMAINID
  , AUDITORLIST
  , COAUDITORLIST
  , MAPPINGID
)
select a.ID
     , a.LASTMODIFIED
     , a.FORMNAME
     , a.AUTHOR
     , a.AUTHOR_DEPT_INDEX
     , a.CREATED
     , a.FORMID
     , a.ISTMP
     , a.VERSIONS
     , a.APPLICATIONID
     , a.LASTMODIFIER
     , a.DOMAINID
     , a.AUDITORLIST
     , a.COAUDITORLIST
     , a.id
from obpm_LianCheng_Data.dbo.TLK_基价信息            a
    left join obpm_LianCheng_Data.dbo.T_DOCUMENT b
        on a.ID = b.ID
where a.ITEM_VALUEPOINT = '#priceDate#'
      and a.ITEM_PROJECTTYPE = '1'
      and a.ITEM_PRICETYPE = '2';