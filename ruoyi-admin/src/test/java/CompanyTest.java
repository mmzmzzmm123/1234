import com.alibaba.fastjson.JSON;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.system.domain.HeFeiCompanyAdvice;
import com.ruoyi.web.PageResult;
import com.ruoyi.web.RequestParams;
import com.ruoyi.web.controller.service.CompanyService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author zdw
 * @Date: 2022/5/27 09:27
 * @Description:
 */
@SpringBootTest(classes = RuoYiApplication.class)

public class CompanyTest {

    private RestHighLevelClient esClient;

    //ES索引名称
    private final static String INDEX_NAME = "company_advice";


    @Autowired
    private CompanyService companyService;


    @Test
    public void testCompanyList() {

        RequestParams params = new RequestParams();
        params.setPageNum(1);
        params.setPageSize(10);
        PageResult result = companyService.listCompany(params);
    }


    @Test
    public void testESquery() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        request.source().query(queryBuilder);

        request.source().from(0).size(10);
        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            HeFeiCompanyAdvice company = JSON.parseObject(json, HeFeiCompanyAdvice.class);
            System.out.println(company);
        }

    }


    @BeforeEach
    void setUp() {
        esClient = new RestHighLevelClient(RestClient.builder(
                //本地
                HttpHost.create("http://127.0.0.1:9200")

                //腾讯
                //HttpHost.create("http://121.5.139.66:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        esClient.close();
    }
}
