package com.ruoyi.system.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.model.DataGTGSH;
import org.apache.commons.text.StringEscapeUtils;
import org.dom4j.*;

import java.util.*;

/**
 *
 * @author lxc
 * @version 1.0
 * @description
 * @date 2021/7/20 20:50
 */
public class ShareInterface {

    public static void main(String[] args) {
//        List<String> list = queryCompanyName("南1234威");
//        System.out.println(list.toString());
//        String  result = getInterface("http://222.77.0.158:18081/api-gateway/gateway/u70is68s/queryAllByEnterp", "南威软件股份有限公司");
////        System.out.println(companyNameStr);
////        Object obj = JSONObject.parseObject(companyNameStr);
////        System.out.println(companyNameStr);
////        JSONArray jsonArr = null;
//        result = result.replace("\\" , "");
//        result = removeCharAt(result,0);
//        result = removeCharAt(result, result.length()-3);
//        result = result.replaceAll("\\{\"code\":\"01\",\"data\":\"", "");
//        result = result.replaceAll("\",\"message\":\"调用成功\"}", "");
//        result = result.replaceAll(">n", ">");
//        System.out.println(result);
//        List<Map> list = new ArrayList<>();
//        try {
//            Document document = DocumentHelper.parseText(result);
//            // 通过document对象获取根节点bookstore
//            Element bookStore = document.getRootElement();
//            // 通过element对象的elementIterator方法获取迭代器
//            Iterator it = bookStore.elementIterator();
//            // 遍历迭代器，获取根节点中的信息（书籍）
//            while (it.hasNext()) {
//                System.out.println("=====开始遍历=====");
//                Element book = (Element) it.next();
//                // 获取book的属性名以及 属性值
//                List<Attribute> bookAttrs = book.attributes();
//                for (Attribute attr : bookAttrs) {
//                    System.out.println("属性名：" + attr.getName() + "--属性值：" + attr.getValue());
//                }
//                System.out.println("=====结束遍历=====");
//                System.out.println("======遍历子节点======");
//                Iterator iterator1 = book.elementIterator();
//                while (iterator1.hasNext()){
//                    Element stuChild = (Element) iterator1.next();
//                    System.out.println("节点名："+stuChild.getName()+"---节点值："+stuChild.getStringValue());
//                    if(StringUtils.isNotEmpty(stuChild.getStringValue())){
//                        Map map = new HashMap();
//                        map.put(stuChild.getName(), stuChild.getStringValue());
//                    }
//                }
//            }
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }

//        List<String> result =  queryCompanyName("泉州大数据");
//        System.out.println(result);

//        Map map = queryCompanyInfo("泉州大数据运营服务有限公司");
//        if(map.isEmpty()){
//            System.out.println("空");
//        }else {
//            Iterator<String> iterator = map.keySet().iterator();
//            while(iterator.hasNext()){
//                String key = iterator.next();
//                String value = (String) map.get(key);
//                if("tyshxydm".equals(key)){
//                    System.out.println(value);
//                }
////                System.out.println(key + "  " + value);
//            }
//        }
//        System.out.println(map);

//        // 失信企业
//        String xydm1 = "91350500156488989A";
//        // 无失信企业
//        String xydm2 = "91350502MA2Y3AN1X7";
//        JSONObject json1 = unTrustWorthyPersonnel(xydm1);
//        JSONObject json2 = unTrustWorthyPersonnel(xydm2);
//        System.out.println(json1.getJSONArray("data").size());
//        System.out.println(json2.getJSONArray("data").size());
        List<DataGTGSH> list = queryGTGSHByName("恒言");
        System.out.println(list);


//        Map map = queryCompanyInfo("泉州大数据运营服务有限公司");
//        queryGTGSHByXYDM("92350521MA2Y11HY6E");
    }

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
    public static JSONObject unTrustWorthyPersonnel(String xydm){
        String result = getInterface("http://222.77.0.158:18081/api-gateway/gateway/xozfst1w/api/HousingTransaction/UntrustworthyPersonnel", xydm);
        String formatStr = StringEscapeUtils.unescapeJson(result).trim();
        if (StringUtils.isEmpty(result) || StringUtils.isEmpty(formatStr)){
            return null;
        }
        formatStr = formatStr.substring(1, formatStr.length()-1);
        return JSONObject.parseObject(formatStr);
    }

    public static boolean isTrust(String xydm){
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
    public static Map<String,String> queryCompanyInfo(String companyName){
        String  result = getInterface("http://222.77.0.158:18081/api-gateway/gateway/u70is68s/queryAllByEnterp", companyName);
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

    /**
     *
     * @param companyName
     * @return
     */
    public static List<String> queryCompanyName(String companyName){
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
    public static List<DataGTGSH> queryGTGSHByName(String name){

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

        String xydmMatch = "<NodeRemark=\"tyshxydm\">";
        String companyNameMatch = "<NodeRemark=\"traname\">";
        String jyfwMatch = "<NodeRemark=\"jyfw\">";
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

    /**
     * 由于接口返回的 XML 格式有误，暂时硬编码处理。考虑方法不可靠，因此用异常处理，一旦解析出错，返回空
     * @param xml
     * @param begineMatch
     * @param endMacth
     * @return
     */
    private static String getXmlValue(String xml,String begineMatch,String endMacth){
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
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    /**
     * 获取共享云 接口
     *
     * @param companyName
     * @return
     */
    public static String getInterface(String url, String companyName) {

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


}
