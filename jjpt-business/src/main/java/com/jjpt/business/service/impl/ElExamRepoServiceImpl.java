package com.jjpt.business.service.impl;

import java.util.List;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jjpt.business.domain.dto.ElExamRepoExtDto;
import com.jjpt.business.utils.BeanMapper;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.ElExamRepoMapper;
import com.jjpt.business.domain.ElExamRepo;
import com.jjpt.business.service.IElExamRepoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 考试题库Service业务层处理
 *
 * @author 卢亚峰
 * @date 2024-05-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ElExamRepoServiceImpl implements IElExamRepoService {
    @Autowired
    private ElExamRepoMapper elExamRepoMapper;

    /**
     * 查询考试题库
     *
     * @param id 考试题库主键
     * @return 考试题库
     */
    @Override
    public ElExamRepo selectElExamRepoById(String id) {
        return elExamRepoMapper.selectElExamRepoById(id);
    }

    /**
     * 查询考试题库列表
     *
     * @param elExamRepo 考试题库
     * @return 考试题库
     */
    @Override
    public List<ElExamRepo> selectElExamRepoList(ElExamRepo elExamRepo) {
        return elExamRepoMapper.selectElExamRepoList(elExamRepo);
    }

    /**
     * 新增考试题库
     *
     * @param elExamRepo 考试题库
     * @return 结果
     */
    @Override
    public int insertElExamRepo(ElExamRepo elExamRepo) {
        return elExamRepoMapper.insertElExamRepo(elExamRepo);
    }

    /**
     * 修改考试题库
     *
     * @param elExamRepo 考试题库
     * @return 结果
     */
    @Override
    public int updateElExamRepo(ElExamRepo elExamRepo) {
        return elExamRepoMapper.updateElExamRepo(elExamRepo);
    }

    /**
     * 批量删除考试题库
     *
     * @param ids 需要删除的考试题库主键
     * @return 结果
     */
    @Override
    public int deleteElExamRepoByIds(String[] ids) {
        return elExamRepoMapper.deleteElExamRepoByIds(ids);
    }

    /**
     * 删除考试题库信息
     *
     * @param id 考试题库主键
     * @return 结果
     */
    @Override
    public int deleteElExamRepoById(String id) {
        return elExamRepoMapper.deleteElExamRepoById(id);
    }

    @Override
    public void saveAll(String examId, List<ElExamRepoExtDto> repoList) {
        // 先删除
        elExamRepoMapper.deleteElExamRepoByExamId(examId);
        //再增加
        if(CollUtil.isEmpty(repoList)){
            throw new ServiceException("必须选择题库");
        }
        List<ElExamRepo> repos = BeanMapper.mapList(repoList, ElExamRepo.class);
        for(ElExamRepo item : repos){
            item.setExamId(examId);
            item.setId(IdWorker.getIdStr());
            elExamRepoMapper.insertElExamRepo(item);
        }


    }
}
