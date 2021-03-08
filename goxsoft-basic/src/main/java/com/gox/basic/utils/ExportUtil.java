package com.gox.basic.utils;


import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.gox.basic.domain.Metadata;
import com.gox.basic.domain.TemplatesPreserve;
import com.gox.basic.domain.vo.TableFieldVo;
import com.gox.common.utils.spring.PropertiesUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ExportUtil {
    private ExportUtil(){}
    private static List<Map<String, String>> extract(Collection<Metadata> collection, List<TemplatesPreserve> fieldVos) throws Throwable{
        Iterator<Metadata> it = collection.iterator();
        Metadata m;
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map ;
        while (it.hasNext()){
            map = new LinkedHashMap<>();
            m = it.next();
            map.put("id",getValue("id",m));
            for (TemplatesPreserve fieldVo : fieldVos) {
                map.put(fieldVo.getTableFieldName(),getValue(fieldVo.getvModel(),m));
            }
            list.add(map);
        }
        return list;
    }
    private static String getValue(String fieldName,Metadata m) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method =m.getClass().getDeclaredMethod("get"+ StrUtil.upperFirst(fieldName));
        Object o = method.invoke(m);
        if (o==null){
            return "";
        }
        else {
            return String.valueOf(o);
        }
    }
    public static void exportExcel(Collection<Metadata> collection, List<TemplatesPreserve> fieldVos, String fileName) throws Throwable {
        List<Map<String, String>> list = extract(collection, fieldVos);
        BigExcelWriter writer = ExcelUtil.getBigWriter(PropertiesUtils.profile+File.separator+"download"+File.separator+fileName);
        // 一次性写出内容，使用默认样式
        writer.write(list);
        // 关闭writer，释放内存
        writer.close();
    }
    public static void exportXml(Collection<Metadata> collection,List<TemplatesPreserve> fieldVos,String fileName) throws Throwable {
        List<Map<String, String>> list = extract(collection, fieldVos);
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("root");
        for (Map<String, String> map : list) {
            Element m = root.addElement("元数据");
            for (TemplatesPreserve fieldVo : fieldVos) {
                String n = map.get(fieldVo.getTableFieldName());
                if (n!=null){
                    Element child = m.addElement(fieldVo.getTableFieldName());
                    child.setText(n);
                }
            }
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setIndentSize(2);  // 行缩进
        format.setNewlines(true); // 一个结点为一行
        format.setTrimText(true); // 去重空格
        format.setPadText(true);
        format.setEncoding("UTF-8");
        format.setNewLineAfterDeclaration(false);
        XMLWriter writer = new XMLWriter(new FileOutputStream(PropertiesUtils.profile+File.separator+"download"+File.separator+fileName), format);
        writer.write(doc);
        writer.close();
    }

}
