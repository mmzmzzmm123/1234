package com.ruoyi.routine;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.basis.domain.GDConvertCordinateResult;
import com.ruoyi.project.data.basis.domain.UvCommunityGeo;
import com.ruoyi.project.data.basis.mapper.UvCommunityGeoMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@ActiveProfiles("uat")
public class GeoTests {

    @Autowired
    private UvCommunityGeoMapper uvCommunityGeoMapper;

    /**
     * 小区坐标，百度转高德
     */
    @Test
    @DS("teemlink")
    public void convertBd2Gd() {
        List<UvCommunityGeo> communityGeoList = uvCommunityGeoMapper.all();

        OkHttpClient client = new OkHttpClient();
        String baseUrl = "https://restapi.amap.com/v3/assistant/coordinate/convert?key" +
                "=4b331b88b71252b7db756603a2562737";

        //
        communityGeoList.parallelStream().forEach(communityGeo -> {

            StringBuilder sb = new StringBuilder(baseUrl);
            sb.append(String.format("&locations=%s,%s&coordsys=baidu", communityGeo.getBaiduLng(),
                    communityGeo.getBaiduLat()));

            Request request = new Request.Builder()
                    .url(sb.toString())
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String content = response.body().string();
//                System.out.println(content);
                GDConvertCordinateResult priceApiResult = JSON.parseObject(content,
                        GDConvertCordinateResult.class);
                if (Objects.equals(1, priceApiResult.getStatus())) {
                    String[] coordinates = priceApiResult.getLocations().split(",");
                    communityGeo.setGaodeLng(new BigDecimal(coordinates[0]));
                    communityGeo.setGaodeLat(new BigDecimal(coordinates[1]));
                    uvCommunityGeoMapper.updateGaoDeCoordinate(communityGeo);
                }

//                System.out.println(priceApiResult);
//                if (null != priceApiResult && priceApiResult.getStatus()) {
//                    jdbcTemplate.update("update collateral set total_price=? where id=?",
//                            new Object[]{priceApiResult.getUeasPrice(), x.getId()});
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
