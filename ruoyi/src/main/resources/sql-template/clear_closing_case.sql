--二手房成交记录处理
select t.*, left(addr_floornum,charindex('|', addr_floornum, 1)-1)  as 楼栋地址,
       substring(addr_floornum, charindex('|', addr_floornum, 1)+1, len(addr_floornum)-charindex('|', addr_floornum, 1)) as "所在层"	 
into #temp
from (
  select a.*, dbo.remove_last_numbers(a.case_address) as addr_floornum
  from dbo.original_residence_sale_closing_case_#yearMonth# a
) t;

update #temp set case_county_name=case_county_name+'区';
update #temp set case_county_name='浦东新区' where case_county_name='浦东区';


--成交插入到临时表 #result
select a.*, b.楼栋ID, b.小区ID,cast(left(case_signing_date,7) as varchar(16)) as 年月
into #result
from #temp a
left join (
	select row_number()over(partition by 区县, 地址 order by newid()) sq, 楼栋ID, 小区ID, 区县, 地址 from obpm_LianCheng_Data.dbo.AI单套地址20190508
) b
on a.case_county_name = b.区县 and a.楼栋地址 = b.地址 and b.sq=1;


update b set b.clean_building_address=a.楼栋地址,
b.clean_current_floor=a.所在层,
b.clean_building_id=a.楼栋ID,
b.clean_community_id=a.小区ID,
b.case_county_name=left(a.case_county_name, 2)
from #result a join dbo.original_residence_sale_closing_case_#yearMonth# b on a.id=b.id


insert into dbo.ODS_HOUSINGCASE_DEAL_#yearMonth#
(
	case_id
  , ProjectID
  , BuildingID
  , Area
  , UpperFloorSum
  , UpperFloorNum
  , AreaCoff
  , FloorCoff
  , BuildingCoff
  , PriceTotal
  , PriceUnit
  , PriceUnitAdj
  , DateContract
)
select a.case_id
	 , a.clean_community_id
     , a.clean_building_id
     , a.case_area
     , b.地上层数
     , a.clean_current_floor
     , d.CoefficientA * a.case_area + d.CoefficientB as AreaCoff
     , 1                                             as FloorCoff
     , e.Coefficient                                 as BuildingCoff
     , a.case_total_price
     , a.case_total_price * 1.0 / a.case_area        as PriceUnit
     , a.case_total_price * 1.0 / a.case_area / isnull(d.CoefficientA * a.case_area + d.CoefficientB, 1)
       / isnull(e.Coefficient, 1)                    as PriceUnitAdj
     , case_signing_date
from dbo.original_residence_sale_closing_case_#yearMonth#    a
    left join obpm_LianCheng_Data.dbo.AI楼栋数据_610        b
        on a.clean_community_id = b.AI小区ID
           and a.clean_building_id = b.AI楼栋ID
    left join obpm_LianCheng_Data.dbo.TLK_小区信息管理        c
        on a.clean_community_id = c.ITEM_AIRAID and c.ITEM_PROJECTTYPE = '1'
    left join obpm_LianCheng_Data.dbo.TLK_字典数据信息        f
        on f.ITEM_DICVALUE = c.ITEM_PROPERTYLEVEL and f.ITEM_DICTYPE='物业档次'
    left join
    (
        select ProjectLevel
             , LowerKey
             , UpperKey
             , CoefficientA
             , CoefficientB
        from obpm_LianCheng_Data.dbo.DIM_PARAMETERS
        where CoffType = '面积修正（链家挂牌）'
              and ProjectType = '公寓'
    )                                                   d --面积修正
        on f.ITEM_DICTEXT = d.ProjectLevel
           and
           (
               a.case_area > d.LowerKey
               and a.case_area <= d.UpperKey
           )
    left join obpm_LianCheng_Data.dbo.DIM_BUILDING_COFF e
        on a.clean_building_id = e.BuildingID
where a.case_area > 0 ;