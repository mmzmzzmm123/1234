package com.jjpt.business.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.jjpt.business.domain.dto.ElRepoDto;
import com.jjpt.business.utils.BeanMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.ElRepoMapper;
import com.jjpt.business.domain.ElRepo;
import com.jjpt.business.service.IElRepoService;

/**
 * 题库管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-16
 */
@Service
public class ElRepoServiceImpl implements IElRepoService 
{
    @Autowired
    private ElRepoMapper elRepoMapper;

    /**
     * 查询题库管理
     * 
     * @param id 题库管理主键
     * @return 题库管理
     */
    @Override
    public ElRepo selectElRepoById(String id)
    {
        return elRepoMapper.selectElRepoById(id);
    }

    /**
     * 查询题库管理列表
     * 
     * @param elRepo 题库管理
     * @return 题库管理
     */
    @Override
    public List<ElRepoDto> selectElRepoList(ElRepo elRepo)
    {
        List<ElRepoDto> res = new ArrayList<>();
        List<ElRepo> elRepos = elRepoMapper.selectElRepoList(elRepo);
        if(!CollUtil.isEmpty(elRepos)){
            for(ElRepo val: elRepos){
                ElRepoDto elRepoDto = new ElRepoDto();
                BeanMapper.copy(val,elRepoDto);
                Map<String,Object>  map =  elRepoMapper.selectTypeNumbyRepoId(val.getId());
                elRepoDto.setRadioCount(Integer.valueOf(map.get("radioCount").toString()));
                elRepoDto.setMultiCount(Integer.valueOf(map.get("multiCount").toString()));
                elRepoDto.setJudgeCount(Integer.valueOf(map.get("judgeCount").toString()));
                res.add(elRepoDto);
            }
        }

        return res;
    }

    /**
     * 新增题库管理
     * 
     * @param elRepo 题库管理
     * @return 结果
     */
    @Override
    public int insertElRepo(ElRepo elRepo)
    {
        Long userId = SecurityUtils.getUserId();
        Long deptId = SecurityUtils.getDeptId();
        elRepo.setCreateTime(DateUtils.getNowDate());
        elRepo.setDeptId(deptId);
        elRepo.setUserId(userId);
        elRepo.setId(IdUtils.fastSimpleUUID());
        elRepo.setCode(IdUtils.fastSimpleUUID());
        return elRepoMapper.insertElRepo(elRepo);
    }

    /**
     * 修改题库管理
     * 
     * @param elRepo 题库管理
     * @return 结果
     */
    @Override
    public int updateElRepo(ElRepo elRepo)
    {
        elRepo.setUpdateTime(DateUtils.getNowDate());
        return elRepoMapper.updateElRepo(elRepo);
    }

    /**
     * 批量删除题库管理
     * 
     * @param ids 需要删除的题库管理主键
     * @return 结果
     */
    @Override
    public int deleteElRepoByIds(String[] ids)
    {
        return elRepoMapper.deleteElRepoByIds(ids);
    }

    /**
     * 删除题库管理信息
     * 
     * @param id 题库管理主键
     * @return 结果
     */
    @Override
    public int deleteElRepoById(String id)
    {
        return elRepoMapper.deleteElRepoById(id);
    }
}
