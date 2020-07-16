package com.ruoyi.erp.shop.task;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.platform.WishApi;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.CSVFileUtil;
import com.ruoyi.common.utils.poi.CsvUtil;
import com.ruoyi.erp.shop.domain.Shop;
import com.ruoyi.erp.shop.service.IShopService;
import com.ruoyi.framework.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 定时任务调度测试
 * 
 * @author wky
 */
@Component("orderTask")
public class OrderTask
{
    @Autowired
    private IShopService shopService;

    @Autowired
    private RedisCache redisCache;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    public void ryWishOrder(){
        System.out.println("开始执行wish订单获取方法");
        Shop shopSelect = new Shop();
        List<Shop> list = shopService.selectShopList(shopSelect);
        HashMap<String,String> map = new HashMap<>();
        for (Shop shop : list) {
            String shopData = shop.getShopData();
            if(StringUtils.isNotEmpty(shopData)) {
                JSONObject obj = JSONObject.parseObject(shopData);
                int code = obj.getInteger("code");
                if(code == 0){
                    String accessToken = obj.getJSONObject("data").getString("access_token");
                    JSONObject result = JSONObject.parseObject(WishApi.createDownloadJob(accessToken,"",""));
                    int result_code = result.getInteger("code");
                    if(result_code == 0){
                        String job_id = result.getJSONObject("data").getString("job_id");
                        System.out.println("用户：" + shop.getShopName() + ",jobId:" + job_id);
                        map.put(shop.getId().toString(),"{\"accessToken\":\"" + accessToken + "\",\"shopType\":\"" + shop.getShopType().getShortName() + "\",\"jobId\":\"" + job_id + "\"}");
                        redisCache.setCacheMap("down_order_job_" + shop.getId(),map);}
                }
            }
        }
        System.out.println("结束wish订单获取方法");
    }

    public void ryDownSaveWishOrder(){
    	System.out.println("开始执行wish订单Excel获取方法");
        Collection<String> coll = redisCache.keys("down_order_job_*");
        for(String str : coll) {
            Map<String,Object> map = redisCache.getCacheMap(str);
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                int shopId = Integer.parseInt(entry.getKey());
                JSONObject obj = JSONObject.parseObject(entry.getValue().toString());
                String shopType = obj.getString("shopType");
                String accessToken = obj.getString("accessToken");
                String jobId = obj.getString("jobId");
                if(shopType.equals("wish")){
                    JSONObject result = JSONObject.parseObject(WishApi.downloadOrderJob(accessToken,jobId));
                    int result_code = result.getInteger("code");
                    if(result_code == 0){
                        String download_link = result.getJSONObject("data").get("download_link").toString();
                        if(StringUtils.isNotEmpty(download_link)) {
                            try {
                                URL url = new URL(download_link);
                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                /*InputStreamReader read = new InputStreamReader(conn.getInputStream(),"UTF-8");
                                BufferedReader bfr=new BufferedReader(read);
                                String line=null;
                                while((line=bfr.readLine())!=null){
                                    String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                                    for(String strs : item)
                                        if(null != strs && !strs.equals(""))
                                            System.out.print(strs + " , ");
                                    System.out.println("\n---------------");
                                }
                                ExcelUtil<Object> util = new ExcelUtil<Object>(Object.class);
                                List<Object> orderList = util.importExcel(conn.getInputStream());*/
                                //EasyExcel.read(conn.getInputStream(),new NoModelDataListener()).sheet().doRead();
                                List<String[]> orderList = CSVFileUtil.readCsv(conn.getInputStream());

                                //CsvUtil csvutil = new CsvUtil(conn.getInputStream());
                                //System.out.println("行："+csvutil.readLine());
                                System.out.println("wish订单总：" + orderList.size());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

}
