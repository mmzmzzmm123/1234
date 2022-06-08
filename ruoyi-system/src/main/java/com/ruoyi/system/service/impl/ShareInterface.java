package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.InterfaceLog;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.model.DataGTGSH;
import com.ruoyi.system.utils.ConfigInfo;
import com.ruoyi.system.utils.HttpUtil;
import com.ruoyi.system.utils.MD5Util;
import org.apache.commons.text.StringEscapeUtils;
import org.dom4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author lxc
 * @version 1.0
 * @description
 * @date 2021/7/20 20:50
 */

@Service
public class ShareInterface {

    /**
     * 个人或企业失信记录查询
     * @param xydm 身份证号码或社会信用代码
     * @return  数据格式如下：(无失信记录时，data 节点为空数组)
     * {
     *   code:200,
     *   data[{
     *       {
     *             "ID": "3FF69D0787624F59B1AECC50BBF04D77",
     *             "CASE_CODE": "(2019)闽0502执882号",
     *             "INAME": "泉州市微图五金制品有限公司",
     *             "CARDNUM": "91350500156488989A",
     *             "CARDTYPE": "",
     *             "CARDNUMBER": "91350500156488989A",
     *             "BUESINESSENTITY": "陈士聪",
     *             "PARTY_TYPE_NAME": "",
     *             "IS_PUBLISH": ""
     *         },
     *   }],
     *   message: "调用成功"
     * }
     *
     */
    public JSONObject unTrustWorthyPersonnel(String xydm){
        String result = getInterface(ConfigInfo.UntrustworthyPersonnel, xydm);
        String formatStr = StringEscapeUtils.unescapeJson(result).trim();
        if (StringUtils.isEmpty(result) || StringUtils.isEmpty(formatStr)){
            return null;
        }
        formatStr = formatStr.substring(1, formatStr.length()-1);
        return JSONObject.parseObject(formatStr);
    }

    @InterfaceLog(title = "(全市)根据名称查询失信被执行人",url = ConfigInfo.UntrustworthyPersonnel,operaterName = "#name",parameter = "#xydm")
    public boolean isTrust(String xydm,String name){
        JSONObject jsonObject = unTrustWorthyPersonnel(xydm);
        if (jsonObject == null)return true;
        JSONArray array = jsonObject.getJSONArray("data");
        int size = array.size();
        if (size == 0){
            return true;
        }else{
            JSONObject object = array.getJSONObject(0);
            return object.get("ID") == null;
        }
    }

    /**
     * 根据企业名称获取企业基本信息
     * 主要是：经验范围（managerange）、统一社会信用代码（tyshxydm）、公司类型（companytype）
     * 生产经营地址(manageaddress) 经营范围(managerange) 登记机关代码(proveinscode)、登记机关(proveins)、单位性质(companyproperty)
     * 经济类型(economictype)、注册（机构/住所/生产经营地址）地址(regaddress)、行业名称(indurstryname)、行业编码(indurstrycode)
     * 例如：{tyshxydm=91350502MA2Y3AN1X7}, {gszch=350502100181468}, {cnname=泉州大数据运营服务有限公司}, {companytype=有限责任公司(国有控股)}
     * @param companyName
     * @return
     */
    @InterfaceLog(title = "（全省）根据企业全称查询福建省内注册企业完整信息",url = ConfigInfo.queryAllByEnterp,operaterName = "#companyName",parameter = "#companyName")
    public Map<String,String> queryCompanyInfo(String companyName){
        String  result = getInterface(ConfigInfo.queryAllByEnterp, companyName);
//        System.out.println(companyNameStr);
//        Object obj = JSONObject.parseObject(companyNameStr);
//        System.out.println(companyNameStr);
//        JSONArray jsonArr = null;
        System.out.println(result);
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isEmpty(result)){
            return map;
        }
        result = result.replace("\\" , "");
        result = removeCharAt(result,0);
        result = removeCharAt(result, result.length()-3);
        result = result.replaceAll("\\{\"code\":\"01\",\"data\":\"", "");
        result = result.replaceAll("\",\"message\":\"调用成功\"}", "");
        result = result.replaceAll(">n", ">");
        System.out.println(result);
        try {
            Document document = DocumentHelper.parseText(result);
            // 通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator<Element> it = bookStore.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                System.out.println("=====开始遍历=====");
                Element book =  it.next();
                // 获取book的属性名以及 属性值
                List<Attribute> bookAttrs = book.attributes();
                for (Attribute attr : bookAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值：" + attr.getValue());
                }
                System.out.println("=====结束遍历=====");
                System.out.println("======遍历子节点======");
                Iterator<Element> iterator1 = book.elementIterator();
                while (iterator1.hasNext()){
                    Element stuChild =  iterator1.next();
                    System.out.println("节点名："+stuChild.getName()+"---节点值："+stuChild.getStringValue());
                    if(StringUtils.isNotEmpty(stuChild.getStringValue())){

                        map.put(stuChild.getName(), stuChild.getStringValue());
//                        list.add(map);
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    @InterfaceLog(title = "（全省）根据企业模糊名称（不拆词匹配）查询企业名称",url = ConfigInfo.QUERY_COMPANY_NAME,operaterName = "#companyName",parameter = "#companyName")
    public List<String> queryCompanyName(String companyName){
        List<String> companyList = new ArrayList<>();
        String  companyNameStr = getInterface(ConfigInfo.QUERY_COMPANY_NAME, companyName);
        System.out.println(companyNameStr);
        if(!companyNameStr.contains("xml")){
            return companyList;
        }
        companyNameStr = companyNameStr.replace("\\" , "");
        companyNameStr = companyNameStr.replace("?>n<Root>", "?><Root>");
        companyNameStr = companyNameStr.substring(1);
        companyNameStr = removeCharAt(companyNameStr, companyNameStr.length()-3);
        System.out.println(companyNameStr);

        try {
            Document document = DocumentHelper.parseText(companyNameStr);
            // 通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                System.out.println("=====开始遍历=====");
                Element book = (Element) it.next();
                // 获取book的属性名以及 属性值
                List<Attribute> bookAttrs = book.attributes();
                for (Attribute attr : bookAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值：" + attr.getValue());
                    companyList.add(attr.getValue());
                }
                System.out.println("=====结束遍历=====");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return companyList;
    }

    /**
     * 根据名称模糊匹配个体工商户
     * @param name
     * @return
     */
    @InterfaceLog(title = "（全市）根据店铺名字(模糊)查询个体工商户基本信息",url = ConfigInfo.QUERY_GTGSH_NAME,operaterName = "#name",parameter = "#name")
    public List<DataGTGSH> queryGTGSHByName(String name){

        List<DataGTGSH> list = new ArrayList<>();

        String result = getInterface(ConfigInfo.QUERY_GTGSH_NAME, name);
        String formatStr = StringEscapeUtils.unescapeJson(result).trim();
        if (StringUtils.isEmpty(result) || StringUtils.isEmpty(formatStr)){
            return list;
        }

        if (formatStr.contains("\"message：\":\"未找到匹配的信息\"")){
            return list;
        }
        formatStr = formatStr.substring(1, formatStr.length()-1);

        formatStr = formatStr.replaceAll("\\{\"code：\":\"200\",\"data：\":\\[\"", "");
        formatStr = formatStr.replaceAll("\"],\"message：\":\"调用成功\"}", "");

        String xydmMatch = "<Node Remark=\"tyshxydm\">";
        String companyNameMatch = "<Node Remark=\"traname\">";
        String jyfwMatch = "<Node Remark=\"jyfw\">";

        int count = StringUtils.countMatches(formatStr, xydmMatch);

        for (int i=0;i<count;i++){
            String xydm = getXmlValue(formatStr,xydmMatch,"</Node>");
            String companyName = getXmlValue(formatStr,companyNameMatch,"</Node>");
            String jyfw = getXmlValue(formatStr,jyfwMatch,"</Node>");

            DataGTGSH gtgsh = new DataGTGSH();
            gtgsh.setXydm(xydm);
            gtgsh.setName(companyName);
            gtgsh.setJyfw(jyfw);
            list.add(gtgsh);

            formatStr = formatStr.replaceFirst(xydmMatch,"");
            formatStr = formatStr.replaceFirst(companyNameMatch,"");
            formatStr = formatStr.replaceFirst(jyfwMatch,"");
        }
        return list;

    }

    @InterfaceLog(title = "（全省）根据统一社会信用代码查询个体工商户基本信息",url = ConfigInfo.QUERY_GTGSH_XYDM,operaterName = "#name",parameter = "#xydm")
    public JSONObject queryGTGSHByXydm(String xydm,String name){
        String result = getInterface(ConfigInfo.QUERY_GTGSH_XYDM, xydm);

        String formatStr = StringEscapeUtils.unescapeJson(result).trim();
        if (StringUtils.isEmpty(result) || StringUtils.isEmpty(formatStr)){
            return null;
        }
        formatStr = formatStr.substring(1, formatStr.length()-1);
        JSONObject jsonObject = JSONObject.parseObject(formatStr);

        JSONArray jsonArray = jsonObject.getJSONArray("data");

        if (jsonArray.size() > 0){
            return jsonArray.getJSONObject(0);
        }
        return null;
    }

    /**
     * 由于接口返回的 XML 格式有误，暂时硬编码处理。考虑方法不可靠，因此用异常处理，一旦解析出错，返回空
     * @param xml
     * @param begineMatch
     * @param endMacth
     * @return
     */
    private String getXmlValue(String xml,String begineMatch,String endMacth){
        try {
            String xydm = xml.substring(xml.indexOf(begineMatch)+begineMatch.length());
            return xydm.substring(0, xydm.indexOf(endMacth));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 截取字符串
     *
     * @param s
     * @param pos
     * @return
     */
    public String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    /**
     * 获取共享云 接口
     *
     * @param companyName
     * @return
     */

    public String getInterface(String url, String companyName) {

        //请求返回结果数据
        String result = "";
        //获取令牌token地址
        String tokenUrl = "";
        //公钥
        String app_key = "";
        //私钥
        String pri_key = "";

        String token = "";
        String authorization = "";
        JSONArray jsonArr = null;

        tokenUrl = ConfigInfo.EXTRANET_TOKEN_URL;
        app_key = ConfigInfo.EXTRANET_APP_KEY;
        pri_key = ConfigInfo.EXTRANET_PRI_KEY;

        //获取时间戮
        Long timestamp = System.currentTimeMillis();
        //根据私钥+时间戮MD5加密获取签名
        String sign = MD5Util.encrypt(pri_key + timestamp.toString());
        try {
            //get方式调用获取令牌信息
            tokenUrl += "?app_key=" + app_key + "&sign=" + sign + "&timestamp=" + timestamp;
            token = HttpUtil.sendGet(tokenUrl, authorization);

            //从获得的信息中获取令牌
            JSONObject jsonObject = JSONObject.parseObject(token);
            authorization = "Basic " + jsonObject.getString("access_token");

            //拼接调用地址参数
            url += "/" + companyName;
            System.out.println(url);
            //调用共享云接口
            result = HttpUtil.sendGet(url, authorization);
            if (StringUtils.isNotEmpty(result)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args){
        new ShareInterface().queryCompanyInfo("泉州大数据运营服务有限公司");
    }

}
