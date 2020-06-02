package com.ruoyi.project.data.basis.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.basis.domain.LianJiaCommunityDict;

import java.util.List;

public interface ILianJiaCommunityDictService {

    int insert(LianJiaCommunityDict dict);

    int update(LianJiaCommunityDict dict);

    int delete(Integer id);
    /**
     * @param dict
     * @return
     */
    List<LianJiaCommunityDict> selectPageOfList(LianJiaCommunityDict dict);

    /**
     *
     * @param id
     * @return
     */
    LianJiaCommunityDict selectById(Integer id);

    /**
     * @param dict
     * @return
     */
    int selectPageOfTotal(LianJiaCommunityDict dict);

}
