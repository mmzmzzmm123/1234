package com.jjpt.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jjpt.business.domain.ElQuRepo;
import com.jjpt.business.mapper.ElQuRepoMapper;
import com.jjpt.business.service.IElQuRepoService;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 试题题库关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
@Service
public class ElQuRepoServiceImpl implements IElQuRepoService
{
    @Autowired
    private ElQuRepoMapper elQuRepoMapper;

    /**
     * 查询试题题库关联
     * 
     * @param id 试题题库关联主键
     * @return 试题题库关联
     */
    @Override
    public ElQuRepo selectElQuRepoById(String id)
    {
        return elQuRepoMapper.selectElQuRepoById(id);
    }

    /**
     * 查询试题题库关联列表
     * 
     * @param elQuRepo 试题题库关联
     * @return 试题题库关联
     */
    @Override
    public List<ElQuRepo> selectElQuRepoList(ElQuRepo elQuRepo)
    {
        return elQuRepoMapper.selectElQuRepoList(elQuRepo);
    }

    /**
     * 新增试题题库关联
     * 
     * @param elQuRepo 试题题库关联
     * @return 结果
     */
    @Override
    public int insertElQuRepo(ElQuRepo elQuRepo)
    {
        return elQuRepoMapper.insertElQuRepo(elQuRepo);
    }

    /**
     * 修改试题题库关联
     * 
     * @param elQuRepo 试题题库关联
     * @return 结果
     */
    @Override
    public int updateElQuRepo(ElQuRepo elQuRepo)
    {
        return elQuRepoMapper.updateElQuRepo(elQuRepo);
    }

    /**
     * 批量删除试题题库关联
     * 
     * @param ids 需要删除的试题题库关联主键
     * @return 结果
     */
    @Override
    public int deleteElQuRepoByIds(String[] ids)
    {
        return elQuRepoMapper.deleteElQuRepoByIds(ids);
    }

    /**
     * 删除试题题库关联信息
     * 
     * @param id 试题题库关联主键
     * @return 结果
     */
    @Override
    public int deleteElQuRepoById(String id)
    {
        return elQuRepoMapper.deleteElQuRepoById(id);
    }


    @Override
    public void saveAll(String quId, Integer quType, List<String> repoIds) {
        //先删除
        elQuRepoMapper.deleteByQuId(quId);
        //保存全部
        if(!CollectionUtils.isEmpty(repoIds)){
            for(String repoId : repoIds){
                ElQuRepo  elQuRepo = new ElQuRepo();
                elQuRepo.setQuId(quId);
                elQuRepo.setRepoId(repoId);
                elQuRepo.setQuType(quType);
                elQuRepo.setId(IdUtils.fastSimpleUUID());
                elQuRepoMapper.insertElQuRepo(elQuRepo);
            }

        }
    }
}
