package com.stdiet.custom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.reflect.ReflectUtils;
import com.stdiet.custom.dto.response.NutritionQuestionResponse;
import com.stdiet.custom.utils.LuceneIndexUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${lucene.index.nutritionQuestion}")
    public String index_path;

    //建立索引的字段名称
    public static final String[] index_field_array = {"id", "title", "content", "key"};
    //查询字段
    public static final String[] index_select_field_array = {"title", "content", "key"};

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
        if(sysNutritionQuestionMapper.updateSysNutritionQuestion(sysNutritionQuestion) > 0){
            return updateNutritionQuestionIndex(sysNutritionQuestion) ? 1 : 0;
        }
        return 0;
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
        if(sysNutritionQuestionMapper.deleteSysNutritionQuestionByIds(ids) > 0){
            //建立索引
            LuceneIndexUtils luceneIndexUtils = LuceneIndexUtils.getLuceneIndexUtils(index_path);
            try {
                return luceneIndexUtils.deleteOneByPrimaryIds(ids) ? 1 : 0;
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
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
        if(sysNutritionQuestionMapper.deleteSysNutritionQuestionById(id) > 0){
            try {
                //建立索引
                LuceneIndexUtils luceneIndexUtils = LuceneIndexUtils.getLuceneIndexUtils(index_path);
                return luceneIndexUtils.deleteOneByPrimaryKey(id) ? 1 : 0;
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    /**
     * 根据关键词搜索对应营养知识问答
     * @return
     */
    @Override
    public Map<String, Object> getNutritionQuestionListByKey(SysNutritionQuestion sysNutritionQuestion, int pageNum, int pageSize){
        //return sysNutritionQuestionMapper.getNutritionQuestionListByKey(sysNutritionQuestion);
        Map<String, Object> result = new HashMap<>();
        int total = 0;
        List<NutritionQuestionResponse> nutritionQuestionResponsesList = new ArrayList<>();
        try{
            //建立索引
            LuceneIndexUtils luceneIndexUtils = LuceneIndexUtils.getLuceneIndexUtils(index_path);
            Map<String, Object> indexMap = luceneIndexUtils.queryByKeyword(sysNutritionQuestion.getKey(), index_select_field_array, pageNum, pageSize);
            total = (int)indexMap.get("total");
            List<Document> documentList = (List<Document>)indexMap.get("data");
            if(documentList != null && documentList.size() > 0){
                for (Document document : documentList) {
                    nutritionQuestionResponsesList.add(documentToNutritionQuestion(document));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("total", total);
        result.put("data", nutritionQuestionResponsesList);
        return result;
    }

    /**
     * 重新生成知识问答索引
     * @return
     */
    public boolean regenerateNutritionQuestionIndex(){
        int pageSize = 1000;
        int total = sysNutritionQuestionMapper.getNutritionQuestionCount();
        try {
            //建立索引
            LuceneIndexUtils luceneIndexUtils = LuceneIndexUtils.getLuceneIndexUtils(index_path);
            if(luceneIndexUtils.deleteAllIndex()){
                if(total > 0){
                    //总页数
                    int totalPage = total/pageSize + (total%pageSize > 0 ? 1 : 0);
                    int start = 0;
                    for(int i = 1; i <= totalPage; i++){
                        start = (i-1) * pageSize;
                        List<SysNutritionQuestion> list = sysNutritionQuestionMapper.getNutritionQuestionListByPage(start,pageSize);
                        luceneIndexUtils.addIndexs(nutritionQuestionToDocument(list));
                    }
                }
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;

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
            Document document = nutritionQuestionToDocument(sysNutritionQuestion);
            return luceneIndexUtils.addIndexOne(document);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新索引
     * @param sysNutritionQuestion
     * @return
     */
    private boolean updateNutritionQuestionIndex(SysNutritionQuestion sysNutritionQuestion){
        try{
            //建立索引
            LuceneIndexUtils luceneIndexUtils = LuceneIndexUtils.getLuceneIndexUtils(index_path);
            Document document = nutritionQuestionToDocument(sysNutritionQuestion);
            return luceneIndexUtils.updateByPrimaryId(sysNutritionQuestion.getId(), document);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * document转为NutritionQuestionResponse
     * @param document
     * @return
     */
    private NutritionQuestionResponse documentToNutritionQuestion(Document document){
        NutritionQuestionResponse nutritionQuestionResponse = new NutritionQuestionResponse();
        for (String fieldName : index_field_array) {
            ReflectUtils.setFieldValue(nutritionQuestionResponse, fieldName, document.get(fieldName));
        }
        return nutritionQuestionResponse;
    }

    private Document nutritionQuestionToDocument(SysNutritionQuestion sysNutritionQuestion){
        Document document = new Document();
        for (String fieldName : index_field_array) {
            document.add(new TextField(fieldName, ReflectUtils.getFieldValue(sysNutritionQuestion, fieldName)+"", Field.Store.YES));
        }
        return document;
    }

    private List<Document> nutritionQuestionToDocument(List<SysNutritionQuestion> sysNutritionQuestionList){
        List<Document> result = new ArrayList<>();
        for (SysNutritionQuestion sysNutritionQuestion : sysNutritionQuestionList) {
            Document document = new Document();
            for (String fieldName : index_field_array) {
                document.add(new TextField(fieldName, ReflectUtils.getFieldValue(sysNutritionQuestion, fieldName)+"", Field.Store.YES));
            }
            result.add(document);
        }
        return result;
    }
}