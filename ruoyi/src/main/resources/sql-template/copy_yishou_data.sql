delete from obpm_LianCheng_Data.dbo.TLK_成交案例 where ITEM_YEARMONTH='#yearMonth#' and ITEM_DEALTYPE='一手';

declare @count int

SELECT @count=count(1) from TLK_成交案例;
-- 居住类型
insert into obpm_LianCheng_Data.dbo.TLK_成交案例(LASTMODIFIED, FORMNAME, AUTHOR, AUTHOR_DEPT_INDEX, CREATED, FORMID, ISTMP, VERSIONS, APPLICATIONID, STATEINT, LASTMODIFIER, DOMAINID, AUDITORLIST, COAUDITORLIST,
ID,ITEM_DEALID,ITEM_SECTOR,ITEM_CIRCLEPOSITION,ITEM_DISTRICT,ITEM_RANAME,ITEM_HOUSEADDRESS,ITEM_AREA,ITEM_SUMPRICE,ITEM_UNITPRICE,ITEM_SIGNINGDATA,ITEM_FLOOR,ITEM_ROOMNATURE,ITEM_APARTMENT,ITEM_DEALTYPE,ITEM_CALCULATIONUNITPRICE,ITEM_CONSULTUNITPRICE,ITEM_CONSULTTOTALPRICE,ITEM_YEARMONTH,ITEM_CALCULATIONTOTALPRICE,ITEM_HOUSETYPE,case_id)
select getdate(), N'物业信息管理/成交案例', N'PNDbyK6lSFWFGlLJFXj', 'Os6qe4Pmq5viTO8lTnW_3QBGkL7jqDjm2BRk7uJ', getdate(),
N'gTn1hQuxFRdwLakei6q', 0, 1, N'Ts7TykYmuEzzZgWhXHj', 0, N'PNDbyK6lSFWFGlLJFXj', N'BclzHtmfLQoAA5ICTb5', N'{}',
N'{}', case_id,'a'+cast( (ROW_NUMBER() over(order by case_id) + @count) as nvarchar(20)),case_block,case_loop,case_county,
case_community_name,case_address,case_area,case_total_price,case_unit_price,case_signing_date,case_floor,case_house_property,case_apartment_layout,'一手',compute_unit_price,reference_unit_price,reference_total_price,'#yearMonth#',compute_total_price,case_house_type,case_id
from uv_compute.dbo.original_new_house_case_#tableRoute#

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
where ITEM_YEARMONTH='#yearMonth#' and ITEM_DEALTYPE='一手';