truncate table ODS_PROJECT_PRICE_INFO_#yearMonth#;

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
  , ITEM_STANDARDPRICE
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
     , 1
     , 1
     , BasePriceDft
	 , MainPriceDft
     , '#priceDate#'
     , PriceNote
     , '李贺'
     , getdate()
     , VOPPA
from dbo.DWA_PROJECTBASEPRICE_MANU_#yearMonth#;

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
      and a.ITEM_PRICETYPE = '1';


-- 更新以前的基价
update b
set b.ITEM_STANDARDPRICE=a.BasePrice_1,
    b.ITEM_MAINAREAPRICE=a.BasePrice_1 * 1.0 * isnull(c.ITEM_AREACOFF, 1) * isnull(c.ITEM_YEARCOFF, 1)
from DWA_PROJECTBASEPRICE_MANU_#yearMonth# a
         join obpm_LianCheng_Data.dbo.TLK_基价信息 b on a.ProjectID = b.ITEM_AIRAID
         join obpm_LianCheng_Data.dbo.TLK_小区信息管理 c on b.ITEM_AIRAID = c.ITEM_AIRAID
where b.ITEM_PRICETYPE = '1'
  and b.ITEM_PROJECTTYPE = '1'
  and b.ITEM_VALUEPOINT = '#lastPriceDate#'
  and a.BasePrice_1 <> b.ITEM_MAINAREAPRICE;

-- 插入价格信息
insert into ODS_PROJECT_PRICE_INFO_#yearMonth#(projectid, pricetype, baseprice, mainprice, areacoff, yearcoff, voppat, voppa, pricedate, pricenote, modifydate, status, adjevd)
select projectid, 1, BasePriceDft, MainPriceDft, areacoff, yearcoff, voppat, voppa, '#priceDate#', pricenote, getdate(), 1, NULL from DWA_PROJECTBASEPRICE_MANU_#yearMonth#;
