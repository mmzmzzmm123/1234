package com.ruoyi.routine;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.microsoft.sqlserver.jdbc.Geometry;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.microsoft.sqlserver.jdbc.spatialdatatypes.Figure;
import com.microsoft.sqlserver.jdbc.spatialdatatypes.Point;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.basis.domain.*;
import com.ruoyi.project.data.basis.mapper.UvCommunityGeoMapper;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private String convertGaoDe2BaiDu(List<Figure> figureList, List<Point> pointList) {

        OkHttpClient client = new OkHttpClient();
        String baseUrl = "http://api.map.baidu.com/geoconv/v1/?ak=GNPGIGijImWZHguFphH6GcsPmAYrEfMX&from=3&to" +
                "=5&coords=";
        StringBuilder baiDuGeoSb = new StringBuilder("POLYGON (");
        List<String> polygonPointList = new LinkedList<>();
        for (int i = 0; i < figureList.size(); i++) {
            List<Point> partialPointList = null;
            List<BaiDuPoint> allBaiDuPointList = new LinkedList<>();
            if (i == figureList.size() - 1) {
                partialPointList = pointList.stream()
                        .skip(figureList.get(i).getPointOffset())
                        .collect(Collectors.toList());
            } else {
                partialPointList = pointList.stream()
                        .skip(figureList.get(i).getPointOffset())
                        .limit(figureList.get(i + 1).getPointOffset() - figureList.get(i).getPointOffset())
                        .collect(Collectors.toList());
            }

            int groupCount = partialPointList.size() / 100;
            if (0 != partialPointList.size() % 100) {
                groupCount += 1;
            }

            for (int j = 0; j < groupCount; j++) {
                StringBuilder sb = new StringBuilder(baseUrl);
                sb.append(String.join(";",
                        partialPointList.stream().skip(j * 100).limit(100).map(p -> p.getX() + "," + p.getY()).collect(Collectors.toList())));
                Request request = new Request.Builder()
                        .url(sb.toString())
                        .build();
                System.out.println(request.url().toString());
                try (Response response = client.newCall(request).execute()) {
                    String content = response.body().string();
                    BaiDuMapResponse priceApiResult = JSON.parseObject(content,
                            BaiDuMapResponse.class);
                    if (Objects.equals(0, priceApiResult.getStatus())) {
                        List<BaiDuPoint> baiDuPointList = priceApiResult.getResult();
                        allBaiDuPointList.addAll(baiDuPointList);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String partialWKT = String.join(", ",
                    allBaiDuPointList.stream().map(BaiDuPoint::toString).collect(Collectors.toList()));
            System.out.println(partialWKT);
            polygonPointList.add(partialWKT);
        }

        baiDuGeoSb.append(String.join(", ",
                polygonPointList.stream().map(s -> "(" + s + ")").collect(Collectors.toList())));
        baiDuGeoSb.append(")");

        return baiDuGeoSb.toString();
    }


    /**
     * 高德坐标转百度
     */
    @Test
    @DS("teemlink")
    public void convertGeoGD2Baidu() {
        String tableName = "上海基准地价2010办公";
        List<UvGeo> uvGeoList = uvCommunityGeoMapper.getSomeGeo(tableName);

        //
        uvGeoList.parallelStream().forEach(gaoDeGeo -> {
            try {
                Geometry geometry = Geometry.parse(gaoDeGeo.getGeoText());
                List<Figure> figureList = geometry.getFigureList();
                List<Point> pointList = geometry.getPointList();

                String baiDuGeoWKT = convertGaoDe2BaiDu(figureList, pointList);
                uvCommunityGeoMapper.updateSomeGeo(tableName, baiDuGeoWKT, gaoDeGeo.getId());
            } catch (SQLServerException e) {
                e.printStackTrace();
            }

//                    StringBuilder baiDuGeoText = new StringBuilder("POLYGON ((");
//                    String geoText = gaoDeGeo.getGeoText().replace("POLYGON ((", "").replace("))", "");
//                    String[] segmentList = geoText.split(",");
//
//
//                    List<String> polygonPointList = new LinkedList<>();
//                    for (String item : segmentList) {
//                        String[] point = item.trim().split(" ");
//                        polygonPointList.add(String.format("%s,%s", point[0], point[1]));
//                    }
//
//                    // 每100个点一组
//                    int groupCount = polygonPointList.size() / 100;
//                    if (0 != polygonPointList.size() % 100) {
//                        groupCount += 1;
//                    }
//                    for (int i = 0; i < groupCount; i++) {
//                        StringBuilder sb = new StringBuilder(baseUrl);
//                        sb.append(String.join(";",
//                                polygonPointList.stream().skip(i * 100).limit(100).collect(Collectors.toList())));
//                        Request request = new Request.Builder()
//                                .url(sb.toString())
//                                .build();
//                        try (Response response = client.newCall(request).execute()) {
//                            String content = response.body().string();
//                            BaiDuMapResponse priceApiResult = JSON.parseObject(content,
//                                    BaiDuMapResponse.class);
//                            if (Objects.equals(0, priceApiResult.getStatus())) {
//                                List<BaiDuPoint> baiDuPointList = priceApiResult.getResult();
//                                allBaiDuPointList.addAll(baiDuPointList);
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    try {
//                        String rawBaiDuPointText = String.join(",",
//                                allBaiDuPointList.stream().map(BaiDuPoint::toString).collect(Collectors.toList()));
//
//                        baiDuGeoText.append(rawBaiDuPointText);
//                        baiDuGeoText.append("))");
//                        uvCommunityGeoMapper.updateSomeGeo(tableName, baiDuGeoText.toString(), gaoDeGeo.getId());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

        });
    }

}
