package com.jjpt.business.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jjpt.business.utils.BeanMapper;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.ElQuAnswerMapper;
import com.jjpt.business.domain.ElQuAnswer;
import com.jjpt.business.service.IElQuAnswerService;

/**
 * 试题答案选项Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
@Service
public class ElQuAnswerServiceImpl implements IElQuAnswerService 
{
    @Autowired
    private ElQuAnswerMapper elQuAnswerMapper;

    /**
     * 查询试题答案选项
     * 
     * @param id 试题答案选项主键
     * @return 试题答案选项
     */
    @Override
    public ElQuAnswer selectElQuAnswerById(String id)
    {
        return elQuAnswerMapper.selectElQuAnswerById(id);
    }

    /**
     * 查询试题答案选项列表
     * 
     * @param elQuAnswer 试题答案选项
     * @return 试题答案选项
     */
    @Override
    public List<ElQuAnswer> selectElQuAnswerList(ElQuAnswer elQuAnswer)
    {
        return elQuAnswerMapper.selectElQuAnswerList(elQuAnswer);
    }

    /**
     * 新增试题答案选项
     * 
     * @param elQuAnswer 试题答案选项
     * @return 结果
     */
    @Override
    public int insertElQuAnswer(ElQuAnswer elQuAnswer)
    {
        return elQuAnswerMapper.insertElQuAnswer(elQuAnswer);
    }

    /**
     * 修改试题答案选项
     * 
     * @param elQuAnswer 试题答案选项
     * @return 结果
     */
    @Override
    public int updateElQuAnswer(ElQuAnswer elQuAnswer)
    {
        return elQuAnswerMapper.updateElQuAnswer(elQuAnswer);
    }

    /**
     * 批量删除试题答案选项
     * 
     * @param ids 需要删除的试题答案选项主键
     * @return 结果
     */
    @Override
    public int deleteElQuAnswerByIds(String[] ids)
    {
        return elQuAnswerMapper.deleteElQuAnswerByIds(ids);
    }

    /**
     * 删除试题答案选项信息
     * 
     * @param id 试题答案选项主键
     * @return 结果
     */
    @Override
    public int deleteElQuAnswerById(String id)
    {
        return elQuAnswerMapper.deleteElQuAnswerById(id);
    }

    @Override
    public void saveAll(String quId, List<ElQuAnswer> answerList) {
        //最终要保存的列表
        List<ElQuAnswer> saveList = new ArrayList<>();
        //已存在的标签列表
        List<String> ids = this.findExistsList(quId);

        if(!CollectionUtils.isEmpty(answerList)){
            for(ElQuAnswer answer : answerList){
                String id = answer.getId();
                //标签ID
                answer.setQuId(quId);

                //补全ID避免新增
                if(ids.contains(id)){
                    ids.remove(id);
                }

                saveList.add(answer);
                //补全ID避免新增
                if(ids.contains(id)){
                    ids.remove(id);
                }
            }
            //保存标签列表
            if(!CollectionUtils.isEmpty(saveList)) {
                List<ElQuAnswer> updateList = saveList.stream().filter(val -> StrUtil.isNotBlank(val.getId())).collect(Collectors.toList());
                List<ElQuAnswer> insertList = saveList.stream().filter(val -> StrUtil.isBlank(val.getId())).collect(Collectors.toList());
                if(!CollUtil.isEmpty(updateList)){
                    for(ElQuAnswer elQuAnswer : updateList){
                        elQuAnswerMapper.updateElQuAnswer(elQuAnswer);
                    }

                }
               if(!CollUtil.isEmpty(insertList)){
                   for(ElQuAnswer elQuAnswer : insertList){
                       elQuAnswer.setId(IdUtils.fastSimpleUUID());
                       elQuAnswerMapper.insertElQuAnswer(elQuAnswer);
                   }
               }
            }

            if(!ids.isEmpty()){
                elQuAnswerMapper.deleteElQuAnswerByIds(ids.stream().toArray(String[]::new));
            }
        }else{
            elQuAnswerMapper.deleteElQuAnswerByQuId(quId);
        }

    }

    /**
     * 查找已存在的列表
     * @param quId
     * @return
     */
    public List<String> findExistsList(String quId) {
        //返回结果
        List<String> ids = new ArrayList<>();
        ElQuAnswer e = new ElQuAnswer();
        e.setQuId(quId);
        List<ElQuAnswer> elQuAnswers = elQuAnswerMapper.selectElQuAnswerList(e);
        if(!CollectionUtils.isEmpty(elQuAnswers)){
            for(ElQuAnswer elQuAnswer : elQuAnswers){
                ids.add(elQuAnswer.getId());
            }
        }
        return ids;

    }



}
