package com.stdiet.custom.utils;

import com.stdiet.common.utils.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lucene的IKAnalyzer工具类
 */
public class LuceneIndexUtils {

    //分词器
    public static IKAnalyzer analyzer = null;
    //分词器工具
    private static IndexWriterConfig config = null;
    //索引库
    private Directory directory = null;
    //流
    private IndexWriter indexWriter= null;
    //获得读取对象
    private IndexSearcher indexSearcher = null;

    public static final String default_primary_key = "id";

    public static final Integer max_select_count = 10000;

    static{
        //分词器
        analyzer = new IKAnalyzer();
        //使用智能分词
        analyzer.setUseSmart(true);
        //工具装配分词器
        config = new IndexWriterConfig(Version.LUCENE_44, analyzer);

    }

    public static LuceneIndexUtils getLuceneIndexUtils(String indexPath){
        LuceneIndexUtils luceneIndexUtils = new LuceneIndexUtils();
        try {
            //索引库
            luceneIndexUtils.directory = FSDirectory.open(new File(indexPath));
            //读索引库流
            IndexReader reader = DirectoryReader.open(luceneIndexUtils.directory);
            //获得读取对象
            luceneIndexUtils.indexSearcher = new IndexSearcher(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return luceneIndexUtils;
    }

    /**
     * 增加所有数据
     * @return
     * @throws IOException
     */
    public int addIndexs(List<Document> documents) throws IOException {

        //流 读取索引库 加装工具
        indexWriter = new IndexWriter(directory,config);

        //数据进流
        indexWriter.addDocuments(documents);

        try {
            indexWriter.commit();
            return documents.size();
        }catch (Exception e){
            indexWriter.rollback();
            return 0;
        }finally {
            indexWriter.close();
            indexWriter= null;
            //config=null;
        }

    }

    /**
     * 增加一条数据
     * @param document
     * @return
     * @throws IOException
     */
    public boolean addIndexOne(Document document) throws IOException {


        //流 读取索引库 加装工具
        indexWriter = new IndexWriter(directory,config);

        //数据进流
        indexWriter.addDocument(document);

        try {
            indexWriter.commit();
            return true;
        }catch (Exception e){
            indexWriter.rollback();
            return false;
        }finally {
            indexWriter.close();
            indexWriter= null;
        }

    }

    /**
     * 删除索引库所有数据
     * @return
     * @throws IOException
     */
    public boolean deleteAllIndex() throws IOException {

        try {

            //流 读取索引库 加装工具
            indexWriter = new IndexWriter(directory,config);

            indexWriter.deleteAll();
            indexWriter.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            indexWriter.rollback();
            return false;
        }finally {
            indexWriter.close();
            indexWriter= null;
        }

    }

    /**
     * 删除索引库中一条数据
     * @param term
     * @return
     * @throws IOException
     */
    public boolean deleteOneByPrimaryKey(Long id) throws IOException {

        //流 读取索引库 加装工具
        indexWriter = new IndexWriter(directory,config);

        TermQuery term = new TermQuery(new Term(default_primary_key, id+""));
        try {
            indexWriter.deleteDocuments(term);
            indexWriter.commit();
            return true;
        }catch (Exception e){
            indexWriter.rollback();
            return false;
        }finally {
            indexWriter.close();
            indexWriter= null;
        }

    }

    /**
     * 删除索引库中一条数据
     * @param term
     * @return
     * @throws IOException
     */
    public boolean deleteOneByPrimaryIds(Long[] ids) throws IOException {

        //流 读取索引库 加装工具
        indexWriter = new IndexWriter(directory,config);

        try {
            for (Long id : ids) {
                TermQuery term = new TermQuery(new Term(default_primary_key, id+""));
                indexWriter.deleteDocuments(term);
                indexWriter.commit();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            indexWriter.rollback();
            return false;
        }finally {
            indexWriter.close();
            indexWriter= null;
        }
    }

    /**
     * 修改索引库中一条数据
     * 注意：此处修改为  根据查找条件修改  如果有则修改 没有则新添 多条则修改一条（索引库的底层其实做法是 先删除，后修改- -）
     * @param term
     * @param document
     * @return
     * @throws IOException
     */
    public boolean updateByPrimaryId(Long id, Document document) throws IOException {

        try {

            //流 读取索引库 加装工具
            indexWriter = new IndexWriter(directory,config);

            indexWriter.updateDocument(new Term(default_primary_key, id+""),document);
            indexWriter.commit();
            return true;
        }catch (Exception e){
            indexWriter.rollback();
            return false;
        }finally {
            indexWriter.close();
            indexWriter= null;
        }

    }

    /**
     * 查询索引库的数据（根据输入关键字）
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryByKeyword(BooleanQuery query, Boolean boostSort, int pageNum, int pageSize) throws Exception {
        Map<String, Object> result = new HashMap<>();

        //排序
        Sort sort = null;
        if(boostSort == null || !boostSort){
            SortField sortField = new SortField(default_primary_key, SortField.Type.INT, true);
            sort = new Sort(sortField);
        }

        //装配解析结果 指定读取量级
        TopDocs search = sort == null ? indexSearcher.search(query, max_select_count) : indexSearcher.search(query, max_select_count, sort);

        //获得数据地址数组
        ScoreDoc[] scoreDocs = search.scoreDocs;

        result.put("total", scoreDocs.length);

        //创建返回集合  --> 方便装配
        ArrayList<Document> list = new ArrayList<>();

        int start = (pageNum - 1) * pageSize;

        if(start < scoreDocs.length){
            int end = pageSize * pageNum;
            if (end > scoreDocs.length) {
                end = scoreDocs.length;
            }
            for (int i = start; i < end; i++) {
                //System.out.println(scoreDocs[i].score);
                //进集合 装配
                list.add(indexSearcher.doc(scoreDocs[i].doc));
            }
        }

        result.put("data", list);
        return result;
    }

    /**
     * 重置索引库 不解释
     * @return
     * @throws IOException
     */
    public boolean resetIndexDB(ArrayList<Document> documents) throws IOException {

        try {
            boolean bool = deleteAllIndex();
            System.out.println(bool);
            int i = addIndexs(documents);
            System.out.println(i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 打印分词结果
     * @param keyWord
     * @throws Exception
     */
    public static void printAnalysisResult(String keyWord) throws Exception {
        StringReader reader = new StringReader(keyWord);
        IKSegmenter ik = new IKSegmenter(reader, true);// 当为true时，分词器进行最大词长切分
        Lexeme lexeme = null;
        while ((lexeme = ik.next()) != null) {
            System.out.println(lexeme.getLexemeText());
        }
    }


}
