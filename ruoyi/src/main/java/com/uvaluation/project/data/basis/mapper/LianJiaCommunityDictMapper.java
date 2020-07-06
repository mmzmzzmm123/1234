package com.uvaluation.project.data.basis.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.data.basis.domain.LianJiaCommunityDict;

import java.util.List;

@DS("clean")
public interface LianJiaCommunityDictMapper {

    /**
     * 新增
     *
     * @param dict
     * @return
     */
    int insert(LianJiaCommunityDict dict);

    /**
     * 更新
     *
     * @param dict
     * @return
     */
    int update(LianJiaCommunityDict dict);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Integer id);

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
