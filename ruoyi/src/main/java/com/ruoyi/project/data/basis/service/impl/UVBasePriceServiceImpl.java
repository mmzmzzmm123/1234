package com.ruoyi.project.data.basis.service.impl;

import com.ruoyi.project.data.basis.domain.UVBasePrice;
import com.ruoyi.project.data.basis.domain.UVBasePriceQueryModel;
import com.ruoyi.project.data.basis.mapper.UVBasePriceMapper;
import com.ruoyi.project.data.basis.service.IUVBasePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UVBasePriceServiceImpl implements IUVBasePriceService {

    @Autowired
    private UVBasePriceMapper uvBasePriceMapper;

//    @Override
//    public int insert(LianJiaCommunityDict dict) {
//        return jdbcTemplate.update("insert into [dbo].[lianjia_dictionary]([lianjia_community_id]," +
//                "[lianjia_community_name]," +
//                "[lianjia_community_address],[lianjia_community_url],[lianjia_community_curl],[community_id]," +
//                "[building_id],[clean_community_name],[clean_community_address],[clean_building_address],[enable]," +
//                "[create_time]) value (?,?,?,?,?,?,?,?,?,?,1,getdate());", new Object[]{dict.getLianJiaCommunityId(),
//                dict.getLianJiaCommunityName(), dict.getLianJiaCommunityAddress(), dict.getLianJiaUrl(),
//                dict.getLianJiaCommunityUrl(), dict.getCommunityId(), dict.getBuildingId(), dict
//                .getCleanCommunityName()
//                , dict.getCleanCommunityAddress(), dict.getCleanBuildingAddress()});
//    }
//
//    @Override
//    public int update(LianJiaCommunityDict dict) {
//        return 0;
//    }
//
//    @Override
//    public int delete(Integer id) {
//        return jdbcTemplate.update("delete from lianjia_dictionary where id= ? ;", new Object[]{id});
//    }
//
//    @Override
//    public List<LianJiaCommunityDict> selectPageOfList(LianJiaCommunityDict dict) {
//        return lianJiaCommunityDictMapper.selectPageOfList(dict);
//    }
//
//    @Override
//    public int selectPageOfTotal(LianJiaCommunityDict dict) {
//        return lianJiaCommunityDictMapper.selectPageOfTotal(dict);
//    }
//
//    @Override
//    public LianJiaCommunityDict selectById(Integer id) {
//        return lianJiaCommunityDictMapper.selectById(id);
//    }

    @Override
    public List<UVBasePrice> pageList(UVBasePriceQueryModel queryModel) {
        return uvBasePriceMapper.pageList(queryModel);
    }

    @Override
    public Integer pageCount(UVBasePriceQueryModel queryModel) {
        return uvBasePriceMapper.pageCount(queryModel);
    }
}
