package com.ruoyi.web.test.controller.baiduyunpan;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.BackupsYunPan;
import com.ruoyi.baiduyunpan.servise.BaiduWangPanServise;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.web.test.controller.BaseSpringBootTest;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashMap;

/**
 * @Auther: Wang
 * @Date: 2021/07/24 22:37
 * 功能描述:
 *  //教程
 *
 */
public class BaiDuWangPan  extends BaseSpringBootTest {

    @Autowired
    private BaiduWangPanServise baiduWangPanServise;
    @Autowired
    private BackupsYunPan backupsYunPan;

    private static String access_token = Constant.ATOKEN;


    @Test
    public void ss(){
        //百度 备份
        backupsYunPan.BaiDuYunBackups("D:\\data\\ces\\","day4_MySQL性能优化总结.zip",Constant.ATOKEN);
    }

    // 获取用户信息
    @Test
    public void getUser() {
        String result = baiduWangPanServise.getUserInfo(Constant.ATOKEN);
        JSONObject object = JSONObject.parseObject(result);
        System.out.println("result：" + result);
        System.out.println("baidu_name:" + object.get("baidu_name"));
        System.out.println("netdisk_name:" + object.get("netdisk_name"));
    }

    // 获取容量
    @Test
    public void getCapacity() {
        String result = baiduWangPanServise.getCapacity(access_token);
        System.out.println("result：" + result);
    }


    // 上传文件
    @Test
    //预上传 >> 分片上传 >> 创建文件
    // 废弃 FileUtilsBaidu 参考这个 类
    public void addFile() {

        String fileName = "22.txt";
        String userPath = "D:\\Wang\\" + fileName;
        String token = Constant.ATOKEN;


        //第一步:预上传
        //对于大文件 需要吧他分成多份(小于4M)，然后计算每份的MD5弄成数组
        String md5 = "";
        try {
             md5 =  DigestUtils.md5Hex(new FileInputStream(new File(userPath)));


        HashMap<String, Object> postParamMap = new HashMap<>();
            postParamMap.put("access_token", token);
            postParamMap.put("path", "/apps/藏趣云/22.txt");
            postParamMap.put("size", 7);//文件或目录的大小，单位B
            postParamMap.put("isdir", "0");//是否目录，0 文件、1 目录
            postParamMap.put("block_list", "[\"" + md5 + "\"]");
            postParamMap.put("autoinit", 1);

        HashMap<String, String> getParamMap = new HashMap<>();
        getParamMap.put("method","precreate");
        getParamMap.put("access_token",token);

        String precreateUrl = HttpUtils.initUriPathParams(Constant.FILE_MANAGER_URL,getParamMap);
        String result = HttpUtil.post(precreateUrl, postParamMap);
            System.out.println("result：" + result);
        String uploadid = JSONObject.parseObject(result).getString("uploadid");
        System.out.println("uploadid：" + uploadid);

        //第二步:分片上传
        HashMap<String, String> superfile2Map = new HashMap<>();
        superfile2Map.put("method", "upload");
        superfile2Map.put("access_token", token);
        superfile2Map.put("type", "tmpfile");
        superfile2Map.put("partseq", "0");
        superfile2Map.put("path", Constant.APP_PATH +fileName);
        superfile2Map.put("uploadid", uploadid);

        System.out.println("superfile2Map："+superfile2Map.values().toString());

        String url = HttpUtils.initUriPathParams(Constant.SLICING_UPLOAD_FILE_URL,superfile2Map);
        String result2 = FileUtilsBaidu.sendFile(url,new File(userPath));
        System.out.println("result2：" + result2);


        //第三步;创建文件
        HashMap<String, Object> postCreateMap = new HashMap<>();
            postCreateMap.put("path", "/apps/藏趣云/22.txt");
            postCreateMap.put("size", 7);
            postCreateMap.put("isdir", 0);
            postCreateMap.put("rtype", 2);
            postCreateMap.put("uploadid", uploadid);
            postCreateMap.put("block_list", "[\"" + md5 + "\"]");

            HashMap<String, String> getCreateMap = new HashMap<>();
            getParamMap.put("method","create");
            getParamMap.put("access_token",token);

            String CreateUrl = HttpUtils.initUriPathParams(Constant.FILE_MANAGER_URL,getParamMap);

        String result3 = HttpUtil.post(CreateUrl, postCreateMap);
        System.out.println("result3：" + result3);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

















    //将文件转换成char[]数组
    public static char[] ReadFileToCharArray(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            System.out.println(numRead);
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }

        reader.close();

        return  fileData.toString().toCharArray();
    }


}
