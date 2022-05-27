package com.ruoyi.web.controller.service;

import com.alibaba.fastjson.JSON;
import com.ruoyi.system.domain.HeFeiCompanyAdvice;
import com.ruoyi.web.PageResult;
import com.ruoyi.web.RequestParams;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zdw
 * @Date: 2022/5/27 10:40
 * @Description:
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private RestHighLevelClient client;
    //ES索引名称
    private final static String INDEX_NAME = "company_advice";

    @Override
    public PageResult listCompany(RequestParams params) {
        SearchRequest request = new SearchRequest(INDEX_NAME);
        BoolQueryBuilder boolQuery = new BoolQueryBuilder();
        if (params.getTitle() != null) {
            boolQuery.must(QueryBuilders.matchPhraseQuery("title", params.getTitle()));
        }
        int pageNum = params.getPageNum();
        int pageSize = params.getPageSize();

        request.source().from((pageNum - 1) * pageSize).size(pageSize);
        request.source().sort("createTime", SortOrder.DESC);
        request.source().sort("commentCount", SortOrder.DESC);
        request.source().query(boolQuery);
        SearchResponse search = null;
        try {
            search = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<HeFeiCompanyAdvice> list = new ArrayList<>();
        SearchHits hits = search.getHits();
        long total = hits.getTotalHits().value;
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            HeFeiCompanyAdvice company = JSON.parseObject(json, HeFeiCompanyAdvice.class);
            list.add(company);
        }
        return new PageResult(total, list,list);
    }
}
