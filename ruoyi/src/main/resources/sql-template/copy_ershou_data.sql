delete from obpm_LianCheng_Data.dbo.TLK_成交案例 where ITEM_YEARMONTH='#yearMonth#';

declare @count int

SELECT @count=max(cast((substring(ITEM_DEALID,2,10) )as int)) FROM dbo.TLK_成交案例 where ITEM_DEALTYPE='二手';
-- 居住类型
insert into obpm_LianCheng_Data.dbo.TLK_成交案例(LASTMODIFIED, FORMNAME, AUTHOR, AUTHOR_DEPT_INDEX, CREATED, FORMID, ISTMP, VERSIONS, APPLICATIONID, STATEINT, LASTMODIFIER, DOMAINID, AUDITORLIST, COAUDITORLIST, ID, ITEM_DEALID, ITEM_SECTOR, ITEM_CIRCLEPOSITION, ITEM_DISTRICT, ITEM_RANAME, ITEM_HOUSEADDRESS, ITEM_AREA, ITEM_SUMPRICE, ITEM_UNITPRICE, ITEM_SIGNINGDATA, ITEM_FLOOR, ITEM_ROOMNATURE, ITEM_APARTMENT, ITEM_DEALTYPE, ITEM_REGISTER, ITEM_INTERMEDIARYCOMPANY, ITEM_INTERMEDIARYTYPE, ITEM_SELLERTYPE, ITEM_BUYERTYPE, ITEM_YEARMONTH, ITEM_AIRAID, ITEM_AIUNITID, ITEM_SINGLEID, ITEM_STANDARDSINGLEADDRESS, ITEM_HOUSETYPE, ITEM_IMPORTDATE,ITEM_PROPERTYTYPE)
select getdate(), N'物业信息管理/成交案例', N'PNDbyK6lSFWFGlLJFXj', 'Os6qe4Pmq5viTO8lTnW_3QBGkL7jqDjm2BRk7uJ', getdate(),
N'gTn1hQuxFRdwLakei6q', 0, 1, N'Ts7TykYmuEzzZgWhXHj', 0, N'PNDbyK6lSFWFGlLJFXj', N'BclzHtmfLQoAA5ICTb5', N'{}',
N'{}', newid(), 'b'+cast((row_number() over(order by case_id) + @count) as nvarchar(20)),case_block_name,
case_loopline_name,case_county_name,case_community_name,case_address,case_area,case_total_price,case_unit_price,
case_signing_date,null,case_house_property,case_apartment_layout,'二手',case_register_date,case_agency_name,
case_agency_type,case_seller_type,case_buyer_type,'#yearMonth#',clean_community_id,NULL,clean_condo_id,
clean_condo_address,case_house_type,getdate(),clean_property_type
from uv_compute.dbo.original_residence_sale_closing_case_#tableRoute#

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
SELECT     ID
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
 , ID
FROM obpm_LianCheng_Data.dbo.TLK_成交案例
where ITEM_YEARMONTH='#yearMonth#' and ITEM_DEALTYPE='二手';