package com.stdiet.custom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SecurityUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.reflect.ReflectUtils;
import com.stdiet.custom.dto.response.NutritionQuestionResponse;
import com.stdiet.custom.utils.LuceneIndexUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;
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
    public static final String[] index_field_array = {"id", "title", "content", "key", "showFlag", "createTime", "createByName"};
    //查询字段
    public static final String[] index_select_field_array = {"title", "content", "key"};

    public static Map<String, Integer> nutritionQuestionBoostMap = null;

    static{
        nutritionQuestionBoostMap = new HashMap<>();
        nutritionQuestionBoostMap.put("key", 100);
        nutritionQuestionBoostMap.put("title", 80);
        nutritionQuestionBoostMap.put("content", 60);
    }

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
        sysNutritionQuestion.setCreateBy(String.valueOf(SecurityUtils.getLoginUser().getUser().getUserId()));
        if(sysNutritionQuestionMapper.insertSysNutritionQuestion(sysNutritionQuestion) > 0){
            return createNutritionQuestionIndex(sysNutritionQuestion.getId()) ? 1 : 0;
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
            return updateNutritionQuestionIndex(sysNutritionQuestion.getId()) ? 1 : 0;
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

            //建立查询语句
            BooleanQuery booleanQuery = new BooleanQuery();

            //装配
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_44, index_select_field_array, luceneIndexUtils.analyzer);

            //解析输入关键字
            Query keywordQuery = StringUtils.isEmpty(sysNutritionQuestion.getKey()) ? new WildcardQuery(new Term(LuceneIndexUtils.default_primary_key, "*")) : queryParser.parse(sysNutritionQuestion.getKey());

            booleanQuery.add(keywordQuery, BooleanClause.Occur.MUST);

            if(sysNutritionQuestion.getShowFlag() != null){
                Query showFlagQuery = new TermQuery(new Term("showFlag", sysNutritionQuestion.getShowFlag().intValue()+""));
                booleanQuery.add(showFlagQuery, BooleanClause.Occur.MUST);
            }

            Map<String, Object> indexMap = luceneIndexUtils.queryByKeyword(booleanQuery, StringUtils.isNotEmpty(sysNutritionQuestion.getKey()), pageNum, pageSize);
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
    private boolean createNutritionQuestionIndex(Long id){
        SysNutritionQuestion sysNutritionQuestion = selectSysNutritionQuestionById(id);
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
    private boolean updateNutritionQuestionIndex(Long id){
        try{
            SysNutritionQuestion sysNutritionQuestion = selectSysNutritionQuestionById(id);
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
            String value = document.get(fieldName);
            ReflectUtils.setFieldValue(nutritionQuestionResponse, fieldName, StringUtils.isEmpty(value) ? "" : value);
        }
        return nutritionQuestionResponse;
    }

    /**
     *
     * @param sysNutritionQuestion
     * @return
     */
    private Document nutritionQuestionToDocument(SysNutritionQuestion sysNutritionQuestion){
        Document document = new Document();
        for (String fieldName : index_field_array) {
            TextField field = null;
            if("createTime".equals(fieldName)){
                field = new TextField(fieldName, sysNutritionQuestion.getCreateTime() == null ? "" : DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,sysNutritionQuestion.getCreateTime()), Field.Store.YES);
            } else{
                Object v = ReflectUtils.getFieldValue(sysNutritionQuestion, fieldName);
                field = new TextField(fieldName, v == null ? "" : String.valueOf(v) , Field.Store.YES);
            }
            if(nutritionQuestionBoostMap.containsKey(fieldName)){
                field.setBoost(nutritionQuestionBoostMap.get(fieldName).floatValue());
            }
            document.add(field);
        }
        return document;
    }

    private List<Document> nutritionQuestionToDocument(List<SysNutritionQuestion> sysNutritionQuestionList){
        List<Document> result = new ArrayList<>();
        for (SysNutritionQuestion sysNutritionQuestion : sysNutritionQuestionList) {
            result.add(nutritionQuestionToDocument(sysNutritionQuestion));
        }
        return result;
    }

    /**
     * 更新微信展示状态
     * @param wxShow 是否展示  0不展示 1展示
     * @param ids id数组
     * @return
     */
    public int updateWxShowByIds(Integer wxShow,  Long[] ids){
        if(sysNutritionQuestionMapper.updateWxShowByIds(wxShow, ids) > 0){
            for (Long id : ids) {
                updateNutritionQuestionIndex(id);
            }
            return 1;
        }
        return 0;
    }

    /**
     * 随机获取指定数量的营养小知识
     * @param sysNutritionQuestion
     * @param pageSize
     * @return
     */
    public List<NutritionQuestionResponse> getNutritionQuestionByRandom(SysNutritionQuestion sysNutritionQuestion, Integer pageSize){
        return sysNutritionQuestionMapper.getNutritionQuestionByRandom(sysNutritionQuestion, pageSize);
    }
}