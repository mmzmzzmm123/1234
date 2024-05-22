package com.jjpt.business.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import com.jjpt.business.domain.ElQu;
import com.jjpt.business.domain.ElQuAnswer;
import com.jjpt.business.domain.ElQuRepo;
import com.jjpt.business.domain.dto.ElQuDto;
import com.jjpt.business.mapper.ElQuMapper;
import com.jjpt.business.service.IElQuAnswerService;
import com.jjpt.business.service.IElQuRepoService;
import com.jjpt.business.service.IElQuService;
import com.jjpt.business.utils.BeanMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 试题管理Service业务层处理
 * 
 * @author 卢亚峰
 * @date 2024-05-17
 */
@Service
public class ElQuServiceImpl implements IElQuService
{
    @Autowired
    private ElQuMapper elQuMapper;
    @Autowired
    private IElQuAnswerService elQuAnswerService;
    @Autowired
    private IElQuRepoService elQuRepoService;

    /**
     * 查询试题管理
     * 
     * @param id 试题管理主键
     * @return 试题管理
     */
    @Override
    public ElQuDto selectElQuById(String id)
    {

        ElQuDto elQuDto = new ElQuDto();
        //1.查询题目
        ElQu elQu = elQuMapper.selectElQuById(id);
        BeanMapper.copy(elQu, elQuDto);

        //2.查询答案
        ElQuAnswer elQuAnswer = new ElQuAnswer();
        elQuAnswer.setQuId(id);
        List<ElQuAnswer> elQuAnswers = elQuAnswerService.selectElQuAnswerList(elQuAnswer);
        elQuDto.setAnswerList(elQuAnswers);

        //3.查询题库
        ElQuRepo elQuRepo = new ElQuRepo();
        elQuRepo.setQuId(id);
        List<ElQuRepo> elQuRepos = elQuRepoService.selectElQuRepoList(elQuRepo);
        List<String> collect = elQuRepos.stream().map(ElQuRepo::getRepoId).collect(Collectors.toList());
        elQuDto.setRepoIds(collect);
        return elQuDto;
    }

    /**
     * 查询试题管理列表
     * 
     * @param elQu 试题管理
     * @return 试题管理
     */
    @Override
    public List<ElQu> selectElQuList(ElQu elQu)
    {
        return elQuMapper.selectElQuList(elQu);
    }

    /**
     * 新增试题管理
     * 
     * @param elQu 试题管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertElQu(ElQuDto elQu)
    {

        Long userId = SecurityUtils.getUserId();
        Long deptId = SecurityUtils.getDeptId();
        //新增问题主表
        ElQu qu = new ElQu();
        String quId = elQu.getId();
        BeanMapper.copy(elQu, qu);
        qu.setCreateTime(DateUtils.getNowDate());
        qu.setDeptId(deptId);
        qu.setUserId(userId);
        qu.setId(quId);
        qu.setCreateTime(DateUtils.getNowDate());
        if(StrUtil.isBlank(quId)){
            quId = IdUtils.fastSimpleUUID();
            qu.setId(quId);
            elQuMapper.insertElQu(qu);
        }else{
            elQuMapper.updateElQu(qu);
        }

         //新增问题选项表
        List<ElQuAnswer> answerList = elQu.getAnswerList();
        elQuAnswerService.saveAll(quId,answerList);
        //保存到题库
        elQuRepoService.saveAll(quId,elQu.getQuType(),elQu.getRepoIds());


        return 1;
    }

    /**
     * 修改试题管理
     * 
     * @param elQu 试题管理
     * @return 结果
     */
    @Override
    public int updateElQu(ElQu elQu)
    {
        elQu.setUpdateTime(DateUtils.getNowDate());
        return elQuMapper.updateElQu(elQu);
    }

    /**
     * 批量删除试题管理
     * 
     * @param ids 需要删除的试题管理主键
     * @return 结果
     */
    @Override
    public int deleteElQuByIds(String[] ids)
    {
        return elQuMapper.deleteElQuByIds(ids);
    }

    /**
     * 删除试题管理信息
     * 
     * @param id 试题管理主键
     * @return 结果
     */
    @Override
    public int deleteElQuById(String id)
    {
        return elQuMapper.deleteElQuById(id);
    }

    @Override
    public List<ElQu> listByRamdom(String repoId, Integer quType, List<String> excludes, Integer size) {
        return  elQuMapper.listByRandom(repoId, quType, excludes, size);
    }
}
