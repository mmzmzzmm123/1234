package com.stdiet.custom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.reflect.ReflectUtils;
import com.stdiet.custom.utils.LuceneIndexUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysNutritionQuestionMapper;
import com.stdiet.custom.domain.SysNutritionQuestion;
import com.stdiet.custom.service.ISysNutritionQuestionService;

/**
 * 营养知识小问答Service业务层处理
 *
 * @author xzj
 * @date 2021-04-13
 */
@Service
public class SysNutritionQuestionServiceImpl implements ISysNutritionQuestionService
{
    @Autowired
    private SysNutritionQuestionMapper sysNutritionQuestionMapper;

    public static final String index_path = "D:\\develop\\LuceneIndex\\nutritionQuestion";
    //private static final String index_path = "";

    //建立索引的字段名称
    public static final String[] index_field_array = {"id", "title", "content", "key"};

    /**
     * 查询营养知识小问答
     *
     * @param id 营养知识小问答ID
     * @return 营养知识小问答
     */
    @Override
    public SysNutritionQuestion selectSysNutritionQuestionById(Long id)
    {
        return sysNutritionQuestionMapper.selectSysNutritionQuestionById(id);
    }

    /**
     * 查询营养知识小问答列表
     *
     * @param sysNutritionQuestion 营养知识小问答
     * @return 营养知识小问答
     */
    @Override
    public List<SysNutritionQuestion> selectSysNutritionQuestionList(SysNutritionQuestion sysNutritionQuestion)
    {
        return sysNutritionQuestionMapper.selectSysNutritionQuestionList(sysNutritionQuestion);
    }

    /**
     * 新增营养知识小问答
     *
     * @param sysNutritionQuestion 营养知识小问答
     * @return 结果
     */
    @Override
    public int insertSysNutritionQuestion(SysNutritionQuestion sysNutritionQuestion)
    {
        sysNutritionQuestion.setCreateTime(DateUtils.getNowDate());
        if(sysNutritionQuestionMapper.insertSysNutritionQuestion(sysNutritionQuestion) > 0){
            return createNutritionQuestionIndex(sysNutritionQuestion) ? 1 : 0;
        }
        return 0;
    }

    /**
     * 修改营养知识小问答
     *
     * @param sysNutritionQuestion 营养知识小问答
     * @return 结果
     */
    @Override
    public int updateSysNutritionQuestion(SysNutritionQuestion sysNutritionQuestion)
    {
        sysNutritionQuestion.setUpdateTime(DateUtils.getNowDate());
        return sysNutritionQuestionMapper.updateSysNutritionQuestion(sysNutritionQuestion);
    }

    /**
     * 批量删除营养知识小问答
     *
     * @param ids 需要删除的营养知识小问答ID
     * @return 结果
     */
    @Override
    public int deleteSysNutritionQuestionByIds(Long[] ids)
    {
        return sysNutritionQuestionMapper.deleteSysNutritionQuestionByIds(ids);
    }

    /**
     * 删除营养知识小问答信息
     *
     * @param id 营养知识小问答ID
     * @return 结果
     */
    @Override
    public int deleteSysNutritionQuestionById(Long id)
    {
        return sysNutritionQuestionMapper.deleteSysNutritionQuestionById(id);
    }

    /**
     * 根据关键词搜索对应营养知识问答
     * @return
     */
    @Override
    public Map<String, Object> getNutritionQuestionListByKey(SysNutritionQuestion sysNutritionQuestion, int pageNum, int pageSize){
        //return sysNutritionQuestionMapper.getNutritionQuestionListByKey(sysNutritionQuestion);
        try{
            //建立索引
            LuceneIndexUtils luceneIndexUtils = LuceneIndexUtils.getLuceneIndexUtils(index_path);
            return luceneIndexUtils.queryByKeyword(sysNutritionQuestion.getKey(), index_field_array, pageNum, pageSize);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("total", 0);
        result.put("data", new ArrayList<>());
        return result;
    }

    /**
     * 建立索引
     * @param sysNutritionQuestion
     * @return
     */
    private boolean createNutritionQuestionIndex(SysNutritionQuestion sysNutritionQuestion){
        try{
            //建立索引
            LuceneIndexUtils luceneIndexUtils = LuceneIndexUtils.getLuceneIndexUtils(index_path);
            Document document = new Document();
            for (String fieldName : index_field_array) {
                document.add(new TextField(fieldName, ReflectUtils.getFieldValue(sysNutritionQuestion, fieldName)+"", Field.Store.YES));
            }
            return luceneIndexUtils.addIndexOne(document);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}