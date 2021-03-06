package com.gox.basic.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.gox.basic.domain.ElectronicAttributes;
import com.gox.basic.domain.Metadata;
import com.gox.common.utils.spring.PropertiesUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.*;

public class ImportUtils {
    private ImportUtils(){}
    public static List<List<Object>> extract(String filepath) throws Throwable {
        if (filepath.endsWith(".xlsx")||filepath.endsWith(".xls")){
            return extractExcel(filepath);
        }
        else if (filepath.endsWith("xml")){
            return extractXml(filepath);
        }
        else if (filepath.endsWith("zip")){
            return extractZip(filepath);
        }
        return new ArrayList<>();
    }
    public static List<List<Object>> extractExcel(String filePath){
        ExcelReader reader = ExcelUtil.getReader(filePath);
        return reader.read(0,11);
    }
    public static List<List<Object>> extractXml(String filePath){
        Document doc = XmlUtil.readXML(filePath);
        NodeList nodeList = doc.getElementsByTagName("元数据");
        int n = nodeList.getLength();
        int l = Math.min(n, 10);
        List<List<Object>> res = new ArrayList<>();
        List<Object> objs ;
        for (int i = 0; i < l; i++) {
            objs = new ArrayList<>();
            Node item = nodeList.item(i);
            NodeList children = item.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node child = nodeList.item(j);
                if (i==0){
                    objs.add(child.getNodeName());
                    continue;
                }
                objs.add(child.getNodeValue());
            }
            res.add(objs);
        }
        return res;
    }
    public static List<List<Object>> extractZip(String filePath) throws Throwable {
        File file = ZipUtil.unzip(filePath, Charset.forName("GBK"));
        File[] files = file.listFiles();
        if (files==null||files.length==0){
            return new ArrayList<>();
        }
        File resource = Arrays.stream(files).filter(f->f.getName().endsWith("xml")).findFirst().orElse(null);
        if (resource == null){
            resource = Arrays.stream(files).filter(f->f.getName().endsWith("xlsx")).findFirst().orElse(null);
            if (resource==null){
                return new ArrayList<>();
            }
            return extractExcel(resource.getCanonicalPath());
        }
        return extractXml(resource.getCanonicalPath());
    }
    public static List<Metadata> importHandler(String filepath, Map<String,String> map1,Map<String,String> map2)
            throws Throwable {
        if (filepath.endsWith(".xlsx")||filepath.endsWith(".xls")){
            return importExcel(filepath,map1,map2);
        }else if(filepath.endsWith("xml")){
            return importXml(filepath, map1, map2);
        }
        else if(filepath.endsWith("zip")){
            return importZip(filepath, map1, map2);
        }
        return new ArrayList<>();
    }
    public static List<Metadata> importXml(String filePath,Map<String,String> map1,Map<String,String> map2)
            throws Throwable{
        Document doc = XmlUtil.readXML(filePath);
        NodeList nodeList = doc.getElementsByTagName("元数据");
        int n = nodeList.getLength();
        List<Metadata> metadatas = new ArrayList<>();
        Metadata  metadata ;
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            metadata = new Metadata();
            Node item = nodeList.item(i);
            NodeList children = item.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node child = nodeList.item(j);
                if (i==0){
                    titles.add(child.getNodeValue());
                    continue;
                }
                String field = map2.get(map1.get(titles.get(j)));
                if (field==null){
                    continue;
                }
                setValue(metadata,field.trim(),child.getNodeValue());
            }
            metadatas.add(metadata);
        }
        return metadatas;
    }
    public static List<Metadata> importExcel(String filePath,Map<String,String> map1,Map<String,String> map2)
            throws Throwable {
        ExcelReader reader = ExcelUtil.getReader(filePath);
        List<List<Object>> data = reader.read();
        if (data == null||data.size()<2){
            return new ArrayList<>();
        }
        List<Object> titles = data.get(0);
        List<Metadata> metadatas = new ArrayList<>();
        Metadata metadata ;
        for (int i=1;i<data.size();i++){
            metadata = new Metadata();
            for (int j = 0; j < titles.size(); j++) {
                String fieldCn = map1.get(titles.get(j));
                String field = map2.get(fieldCn);
                if (field==null){
                    continue;
                }
                setValue(metadata,field.trim(), data.get(i).get(j));
            }
            metadatas.add(metadata);
        }
        return metadatas;
    }
    public static List<Metadata> importZip(String filePath,Map<String,String> map1,Map<String,String> map2)
            throws Throwable {
        String folderPath = filePath.substring(0,filePath.lastIndexOf(".zip"));
        File folder  = new File(folderPath);
        File[] files = folder.listFiles();
        List<Metadata> metadatas ;
        if (files==null||files.length==0){
            return new ArrayList<>();
        }
        File resource = Arrays.stream(files).filter(f->f.getName().endsWith("xml")).findFirst().orElse(null);
        if (resource == null){
            resource = Arrays.stream(files).filter(f->f.getName().endsWith("xlsx")).findFirst().orElse(null);
            if (resource==null){
                return new ArrayList<>();
            }
            metadatas = importExcel(resource.getCanonicalPath(),map1,map2);
        }
        else {
            metadatas = importXml(resource.getCanonicalPath(),map1,map2);
        }
        if (files.length==1){
            return metadatas;
        }
        String year = String.valueOf(DateTime.now().year());
        String month = String.valueOf(DateTime.now().month());
        String day = String.valueOf(DateTime.now().dayOfMonth());
        ElectronicAttributes ele;
        List<ElectronicAttributes> eles = new ArrayList<>();
        for (Metadata metadata : metadatas) {
            String ac = metadata.getArchivalCode();
            metadata.setElectronicAttributes(eles);
            String meFolder ;
            String to;
            if (StrUtil.isBlank(ac)){
                meFolder = filePath+File.separator+metadata.getId()+File.separator;
                to = PropertiesUtils.profile+File.separator+"ele"+year+File.separator+month+File.separator+day+metadata.getId();
            }
            else {
                meFolder = filePath+File.separator+ac+File.separator;
                to = PropertiesUtils.profile+File.separator+"ele"+year+File.separator+month+File.separator+day+ac;
            }
            File toFolder = new File(to);
            toFolder.mkdirs();
            File file = new File(meFolder);
            if (!file.exists()){
                continue;
            }
            File[] meFiles = file.listFiles();
            if (meFiles==null||meFiles.length==0){
                continue;
            }
            for (File meFile : meFiles) {
                ele = new ElectronicAttributes(meFile,metadata.getId());
                eles.add(ele);
                FileUtil.copy(meFile,toFolder,true);
            }
        }
        return metadatas;
    }
    public static void setValue(Object o ,String field,Object v) throws Throwable {
        if (o==null){
            throw new NullPointerException();
        }
        Field f = o.getClass().getDeclaredField(field);
        Class<?> c = f.getType();
        v=Convert.convert(c,v);
        f.setAccessible(true);
        f.set(o,v);
    }
}
