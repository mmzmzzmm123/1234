--二手房成交记录处理
select t.*, left(addr_floornum,charindex('|', addr_floornum, 1)-1)  as 楼栋地址,
       substring(addr_floornum, charindex('|', addr_floornum, 1)+1, len(addr_floornum)-charindex('|', addr_floornum, 1)) as "所在层"	 
into #temp
from (
  select case_id
      ,case_county_name
      ,case_block_name
      ,case_loopline_name
      ,case_community_name
      ,case_address
      ,case_area
      ,case_unit_price
      ,case_total_price
      ,case_house_type
      ,case_signing_date
      ,case_register_date
      ,case_agency_name
      ,case_agency_type
      ,case_seller_type
      ,case_buyer_type
      ,case_birthday
	  , dbo.remove_last_numbers(case_address) as addr_floornum
  from original_residence_sale_closing_case_#yearMonth# 
) t;

update #temp set case_county_name=case_county_name+'区';
update #temp set case_county_name='浦东新区' where case_county_name='浦东区';

--成交插入到临时表 #result
select a.*, b.楼栋ID, b.小区ID
into #result
from #temp a
left join (
	select row_number()over(partition by 区县, 地址 order by newid()) sq, 楼栋ID, 小区ID, 区县, 地址 from obpm_LianCheng_Data.dbo.AI单套地址20190508 
) b
on a.case_county_name = b.区县 and a.楼栋地址 = b.地址 and b.sq=1;

--添加物业类型等字段
alter table #result
add 年月 varchar(16), 物业类型 varchar(16);

update #result
set 年月= left(case_signing_date,7), 
	物业类型 = case when case_house_type not in ('办公楼','商铺','未知','其它','工厂','车库') then '居住'
										when case_house_type in ('办公','办公楼','写字楼') then '办公'
										when case_house_type in ('商铺','商店','商铺') then '商业'
							 else '其他' end;


select 
case_id
,left(case_county_name,2) case_county_name
,case_block_name
,case_loopline_name
,case_community_name
,case_address
,case_area as Area
,case_unit_price
,case_total_price as PriceTotal
,case_house_type as ProjectType
,case_signing_date as DateContract
,case_register_date
,case_agency_name
,case_agency_type
,case_seller_type as IsAgency
,case_buyer_type
,case_birthday
,addr_floornum
,楼栋地址
, 所在层 as UpperFloorNum
, cast(convert(decimal(8,0),楼栋ID) as bigint) as BuildingID
, cast(convert(decimal(8,0),小区ID) as bigint) as ProjectID
, 年月
, 物业类型
into #tmp
from #result
where case_area <> 0;

insert into dbo.ODS_HOUSINGCASE_DEAL_#yearMonth#
           (case_id
           ,ProjectID
           ,BuildingID
           ,Area
           ,UpperFloorSum
           ,UpperFloorNum
           ,AreaCoff
           ,FloorCoff
           ,BuildingCoff
           ,PriceTotal
           ,PriceUnit
           ,PriceUnitAdj
           ,DateContract)
select a.case_id
	 , a.ProjectID
     , a.BuildingID
     , a.Area
     , b.地上层数 as UpperFloorSum
     , a.UpperFloorNum
     , d.CoefficientA * a.Area + d.CoefficientB                                                                     as AreaCoff
     , 1                                                                                                            as FloorCoff
     , e.Coefficient                                                                                                as BuildingCoff
     , a.PriceTotal
     , a.PriceTotal * 1.0 / a.Area                                                                                  as PriceUnit
     , a.PriceTotal * 1.0 / a.Area / isnull(d.CoefficientA * a.Area + d.CoefficientB, 1) / isnull(e.Coefficient, 1) as PriceUnitAdj
     , DateContract
from #tmp                                                        a
    left join obpm_LianCheng_Data.dbo.AI楼栋数据_610                                       b
        on a.ProjectID = b.AI小区ID
           and a.BuildingID = b.AI楼栋ID
    left join obpm_LianCheng_Data.dbo.TLK_小区信息管理                            c
        on a.ProjectID = c.ITEM_AIRAID and c.ITEM_ISBUILDINDEX='1'
	left join obpm_LianCheng_Data.dbo.TLK_字典数据信息 f on c.ITEM_PROPERTYLEVEL = f.ITEM_DICVALUE and f.ITEM_DICTYPE='物业档次'
    left join
    (select Coefficient,CoefficientA,CoefficientB,LowerKey,UpperKey,ProjectLevel from  obpm_LianCheng_Data.dbo.DIM_PARAMETERS where CoffType = '面积修正（链家挂牌）' and ProjectType = '公寓') d --面积修正
        on f.ITEM_DICTEXT = d.ProjectLevel
           and
           (
               a.Area > d.LowerKey
               and a.Area <= d.UpperKey
           )
    left join obpm_LianCheng_Data.dbo.DIM_BUILDING_COFF                                  e
        on a.BuildingID = e.BuildingID;


drop table #temp;
drop table #result;