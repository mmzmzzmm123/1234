package com.ruoyi.project.data.basis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.project.data.basis.domain.UvClosingCase;
import com.ruoyi.project.data.basis.domain.UvTradingCase;
import com.ruoyi.project.data.basis.mapper.UvClosingCaseMapper;
import com.ruoyi.project.data.basis.service.BaseElasticsearchService;
import com.ruoyi.project.data.basis.service.IUvClosingCaseService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 案例查询实现
 *
 * @author lihe
 */
@Service
public class UvClosingCaseServiceImpl extends BaseElasticsearchService implements IUvClosingCaseService {

    @Autowired
    private UvClosingCaseMapper uvClosingCaseMapper;

    @Override
    public List<UvClosingCase> pageList(UvClosingCase queryModel) {
        return uvClosingCaseMapper.selectPageList(queryModel);
    }

    @Override
    public Integer pageCount(UvClosingCase queryModel) {
        return uvClosingCaseMapper.selectPageCount(queryModel);
    }

    @Override
    public List<UvTradingCase> findByText(String text) {
        Map<String, Object> params =  new HashMap<>();
        params.put("cleanAddress",text);
        SearchResponse searchResponse = search("trading",params);
        SearchHit[] hits = searchResponse.getHits().getHits();

        List<UvTradingCase> caseList = new ArrayList<>();
        Arrays.stream(hits).forEach(hit -> {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            UvTradingCase person = BeanUtil.mapToBean(sourceAsMap, UvTradingCase.class, true);
            caseList.add(person);
        });

        return caseList;
    }


}
