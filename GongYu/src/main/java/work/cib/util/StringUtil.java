package work.cib.util;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {
    /**
     * 判断是否为（null或“”）
     */
    public static boolean isNullValue(String value) {
        if (value != null && !"".equals(value.trim()))
            return false;
        else
            return true;
    }

    /**
     * 查找【正则表达式】描述的内容。
     */
    public static List<String> search(String regex, String string) {
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(string);
        while (m.find()) {
            String g = m.group();
            list.add(g);
        }
        return list;
    }

    /**
     * 区别tomcat路径和 weblogic路径
     *
     * @param dirs 根目录下面的路径 前面跟 "/"符
     */
    public static String getUrl(HttpServletRequest request, String dirs) {
        // 得到了web应用中的 Servletcontext ，从而使用 Servletcontext 接口的一些方法
        // 获取“绝对路径”
        String root = request.getSession().getServletContext().getRealPath("");
        String path = "";
        if (isNullValue(root)) {
            try {
                path = request.getSession().getServletContext().getResource("/").getFile() + dirs;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            path = request.getSession().getServletContext().getRealPath("") + dirs;
        }
        return path;
    }

    /**
     * 将字符串拼接为 'String1','String2' 形式
     */
    public static String parseStr(List<String> lists) {
        String resultStr = "";
        for (int i = 0; i < lists.size(); i++) {
            if (i == lists.size() - 1) {
                resultStr += "'" + lists.get(i) + "'";
            } else {
                resultStr += "'" + lists.get(i) + "',";
            }
        }
        return resultStr;
    }

    /**
     * 去除空格,换行,制表符
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * context 字符串，按map key值替换内容
     */
    public static String replaceMapKeys(Map<String, Object> map, String context) {
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            context = context.replace(key, map.get(key).toString());
        }
        return context;
    }

    /**
     * list 转换为  '1','2','3'
     */
    public static String listToInSQL(List<String> data) {
        StringBuilder sd = new StringBuilder("");
        data.forEach(str -> sd.append("'" + str + "',"));
        return sd.substring(0, sd.length() - 1);
    }

    public static void main(String[] args) {
        String[] arr = {"1", "2", "3", "4"};
        List<String> data = Arrays.asList(arr);
        System.out.println(listToInSQL(data));
    }

}
