package com.ruoyi.web.test.controller;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.baidu.servise.BaiduWangPanServise;
import com.ruoyi.common.constant.BaiduUrl;
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
 *  //https://blog.csdn.net/admans/article/details/80653490
 */
public class BaiDuWangPan  extends BaseSpringBootTest{

    @Autowired
    private BaiduWangPanServise baiduWangPanServise;

    private static String access_token = "123.5f2c72c8c2106a3baa177de77331b1d1.YnelNhviWQotgsooAt63iCRbaqZrjEP0Qgj3nIx.jMfNkQ";


    // 获取用户信息
    @Test
    public void getUser() {
        String result = baiduWangPanServise.getUserInfo(access_token);
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
    public void addFile() {

        //预上传、分片上传、创建文件
        String md5 = "";
        try {
             md5 =  DigestUtils.md5Hex(new FileInputStream(new File("D:\\Wang\\22.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //预上传 获取ID

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", access_token);
        paramMap.put("path", "/apps/藏趣云/22.txt");
        paramMap.put("size", "1");//文件或目录的大小，单位B
        paramMap.put("isdir", "0");//是否目录，0 文件、1 目录
        paramMap.put("block_list", "[\"" + md5 + "\"]");
        paramMap.put("autoinit", 1);
        String result = HttpUtil.post("https://pan.baidu.com/rest/2.0/xpan/file?method=precreate&access_token" + access_token, paramMap);
        System.out.println("result：" + result);

        JSONObject object = JSONObject.parseObject(result);
        String uploadid = object.getString("uploadid");
        System.out.println("uploadid：" + uploadid);


        //上传文件
        HashMap<String, Object> paramMap1 = new HashMap<>();
//      paramMap1.put("file", ReadFileToCharArray("D:\\Wang\\22.txt"));
        paramMap1.put("file", new File("D:\\Wang\\22.txt"));

        System.out.println("file：" + paramMap1.get("file").toString());

//        String result2 = HttpUtil.post("https://d.pcs.baidu.com/rest/2.0/pcs/superfile2?access_token=" + access_token + "&method=upload&type=tmpfile&path=%2Fapps%2F%E8%97%8F%E8%B6%A3%E4%BA%91%2F22.txt&partseq=0&uploadid=" + uploadid, paramMap1);
//        System.out.println("result2：" + result2);
        String url = Constant.SLICING_UPLOAD_FILE_URL + "?method=upload" +
                "&access_token=" + Constant.ATOKEN +
                "&type=tmpfile&partseq=0" +
                "&path=" + Constant.APP_PATH + "22.txt" +
                "&uploadid=" + uploadid;
        String result2 = FileUtilsBaidu.sendFile(url,new File("D:\\Wang\\22.txt"));
        System.out.println("result2：" + result2);

        //创建文件
        HashMap<String, Object> paramMap3 = new HashMap<>();
//      paramMap1.put("file", ReadFileToCharArray("D:\\Wang\\22.txt"));
        paramMap3.put("path", "/apps/藏趣云/22.txt");
        paramMap3.put("size", 6);
        paramMap3.put("isdir", 0);


        String result3 = HttpUtil.post("https://pan.baidu.com/rest/2.0/xpan/file?method=create&access_token=" + access_token, paramMap3);
        System.out.println("result3：" + result3);


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
