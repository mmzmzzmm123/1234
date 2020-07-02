
truncate table ODS_PROJECT_PRICE_INFO_#yearMonth#;

insert into ODS_PROJECT_PRICE_INFO_#yearMonth#
 select ProjectID, 1 PriceType, BasePriceDft, MainPriceDft, AreaCoff, YearCoff, VOPPAT, VOPPA, '#priceDate#', PriceNote, '#priceDate#', 1, NULL  ---注意：AdjEvd下期调整
 from DWA_PROJECTBASEPRICE_IMDT_#yearMonth# a

--判断有无新增小区（根据当期、上期价格表和当期小区信息表分别判断）
select a.ProjectID,
       case when b.ProjectID is null then 1 else 0 end as PriceTableAdd,
       case when c.ProjectID is null then 1 else 0 end as PriceTableAdd_1,
       case when d.ITEM_AIRAID is null then 1 else 0 end as InfoTableAdd
into #ProjectAdd
from DWA_PROJECTBASEPRICE_MANU_#yearMonth# a
left join ODS_PROJECT_PRICE_INFO_#yearMonth# b
on a.ProjectID = b.ProjectID and b.Status=1
left join ODS_PROJECT_PRICE_INFO_#lastYearMonth# c
on a.ProjectID = c.ProjectID and c.Status=1
left join obpm_LianCheng_Data.dbo.TLK_小区信息管理 d
on a.ProjectID = d.ITEM_AIRAID and d.ITEM_PROJECTTYPE='1' and d.ITEM_ISBUILDINDEX='1'
where b.ProjectID is null or c.ProjectID is null or d.ITEM_AIRAID is null;

-------DWA_PROJECTBASEPRICE_MANU_201902记录有更新，触发下面流程
-------价格表调整
select a.ProjectID, a.IsIndxGen, a.IsPstCalc, a.StatusRun, a.ProjectType, a.ProjectTypeDtl, a.ProjectLevel, a.[Year], a.MainArea, a.AreaCoff, a.YearCoff,
        a.BasePriceDft as BasePrice, a.BasePrice_1, a.PriceNote, a.VOPPAT, a.VOPPA, getdate() as ModifyDate,
        case when b.ProjectID is not null and isnull(a.BasePriceDft, 0) <> isnull(b.BasePrice, 0) then 1 else 0 end as PriceChg,    --指当期基价变化
        case when b.ProjectID is not null and isnull(a.priceNote, '') <> isnull(b.priceNote, '') then 1 else 0 end as PriceNoteChg,  -- 指当期价格说明字段变化
        case when b.ProjectID is not null and isnull(a.AreaCoff,1) <> isnull(b.AreaCoff,1) then 1 else 0 end as AreaCoffChg,  -- 指当期面积修正系数字段变化
        case when b.ProjectID is not null and isnull(a.YearCoff,1) <> isnull(b.YearCoff,1) then 1 else 0 end as YearCoffChg,  -- 指当期年代修正系数字段变化
        case when b.ProjectID is not null and isnull(a.VOPPAT,'') <> isnull(b.VOPPAT,'') then 1 else 0 end as VOPPATChg,  -- 指当期价格涨跌幅类型字段变化
        case when b.ProjectID is not null and isnull(a.VOPPA,0) <> isnull(b.VOPPA,0) then 1 else 0 end as VOPPAChg,  -- 指当期价格涨跌幅字段变化
        case when c.ProjectID is not null then 1 else 0 end as Price_1Chg									-- 指上期基价变化
into #InfoChg
from DWA_PROJECTBASEPRICE_MANU_#yearMonth# a
left join ODS_PROJECT_PRICE_INFO_#yearMonth# b
on a.ProjectID = b.ProjectID and b.status=1 and (
    isnull(a.BasePriceDft, 0) <> isnull(b.BasePrice, 0) or
    isnull(a.priceNote, '') <> isnull(b.priceNote, '') or
    isnull(a.AreaCoff,1) <> isnull(b.AreaCoff,1) or
    isnull(a.YearCoff,1) <> isnull(b.YearCoff,1) or
    isnull(a.VOPPAT,'') <> isnull(b.VOPPAT,'') or
    isnull(a.VOPPA,0) <> isnull(b.VOPPA,0))
left join ODS_PROJECT_PRICE_INFO_#lastYearMonth# c
on a.ProjectID = c.ProjectID and isnull(a.BasePrice_1,0) <> isnull(c.BasePrice,0) and c.status=1
where b.ProjectID is not null or c.ProjectID is not null;

select a.ProjectID, 1 PriceType, b.BasePrice,
        b.BasePrice*1.0*isnull(b.AreaCoff,1)*isnull(b.YearCoff,1) as MainPrice,
        isnull(b.AreaCoff,1) as AreaCoff, isnull(b.YearCoff, 1) as YearCoff,
        b.VOPPAT, b.VOPPA, a.PriceDate, b.PriceNote, b.ModifyDate, 1 as Status,
        case when PriceChg=0 then '' else 'PriceChg|' end + case when PriceNoteChg=0 then '' else 'PriceNoteChg|' end +
        case when AreaCoffChg=0 then '' else 'AreaCoffChg|' end + case when YearCoffChg=0 then '' else 'YearCoffChg|' end +
        case when VOPPATChg=0 then '' else 'VOPPATChg|' end + case when VOPPAChg=0 then '' else 'VOPPAChg|' end as AdjEvd
into #InfoChgCurr
from ODS_PROJECT_PRICE_INFO_#yearMonth# a
inner join #InfoChg b
on a.ProjectID = b.ProjectID
where (b.PriceChg=1 or b.PriceNoteChg=1 or b.AreaCoffChg=1 or b.YearCoffChg=1 or b.VOPPATChg=1 or b.VOPPAChg=1) and a.status=1;

update a
set a.Status = 0
from ODS_PROJECT_PRICE_INFO_#yearMonth# a
left join #InfoChgCurr b
on a.ProjectID = b.ProjectID
where b.ProjectID is not null and a.status=1;

insert into ODS_PROJECT_PRICE_INFO_#yearMonth#
select * from #InfoChgCurr;

select a.ProjectID, 1 PriceType, b.BasePrice_1, b.BasePrice_1*1.0*isnull(a.AreaCoff,1)*isnull(a.YearCoff,1) as MainPrice, a.AreaCoff, a.YearCoff,
        a.VOPPAT, a.VOPPA, a.PriceDate, b.PriceNote, b.ModifyDate, 1 as Status,'Price_1Chg|' as AdjEvd
into #InfoChgLst
from ODS_PROJECT_PRICE_INFO_#lastYearMonth# a
inner join #InfoChg b
on a.ProjectID = b.ProjectID
where b.Price_1Chg=1 and a.status=1;

update a
set Status = 0
from ODS_PROJECT_PRICE_INFO_#lastYearMonth# a
left join #InfoChgLst b
on a.ProjectID = b.ProjectID
where b.ProjectID is not null and a.status=1;

insert into ODS_PROJECT_PRICE_INFO_#lastYearMonth#
select * from #InfoChgLst;

--插入新增小区价格信息
INSERT INTO ODS_PROJECT_PRICE_INFO_#yearMonth#
SELECT A.ProjectID, 1 AS PriceType, A.BasePriceDft, A.MainPriceDft,	AreaCoff,	YearCoff,	VOPPAT,	VOPPA, '#priceDate#' AS PriceDate, PriceNote, '#priceDate#' AS ModifyDate, 1 Status, 'NewAdded' AdjEvd
FROM DWA_PROJECTBASEPRICE_MANU_#yearMonth# A
INNER JOIN #ProjectAdd B
ON A.ProjectID = B.ProjectID AND B.PriceTableAdd = 1;


INSERT INTO ODS_PROJECT_PRICE_INFO_#lastYearMonth#
SELECT A.ProjectID, 1 AS PriceType, BasePrice_1, BasePrice_1*1.0*ISNULL(AreaCoff,1)*ISNULL(YearCoff,1) MainPrice,
       AreaCoff,	YearCoff,	NULL VOPPAT,	NULL VOPPA, '#lastPriceDate#' AS PriceDate, PriceNote, '#priceDate#' AS ModifyDate, 1 Status, 'NewAdded_1' AdjEvd
FROM DWA_PROJECTBASEPRICE_MANU_#yearMonth# A
INNER JOIN #ProjectAdd B
ON A.ProjectID = B.ProjectID AND B.PriceTableAdd_1 = 1;

drop table #InfoChg;
drop table #InfoChgCurr;
drop table #InfoChgLst;
drop table #ProjectAdd;

-- 上月
update a
set a.ITEM_STANDARDPRICE = b.BasePrice,a.ITEM_MAINAREAPRICE=b.MainPrice
from obpm_LianCheng_Data.dbo.TLK_基价信息       a
    join dbo.ODS_PROJECT_PRICE_INFO_#lastYearMonth# b
        on a.ITEM_AIRAID = b.ProjectID
where b.Status = 1
      and a.ITEM_VALUEPOINT = '#lastPriceDate#'
      and a.ITEM_PRICETYPE = '1'
      and a.ITEM_PROJECTTYPE = '1'
      and (
		a.ITEM_STANDARDPRICE <> b.BasePrice or
		a.ITEM_MAINAREAPRICE <> b.MainPrice
	  );
--
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
     , BasePrice
	 , MainPrice
     , '#priceDate#'
     , PriceNote
     , '李贺'
     , getdate()
     , VOPPA
from dbo.ODS_PROJECT_PRICE_INFO_#yearMonth#
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
      and a.ITEM_PRICETYPE = '1';