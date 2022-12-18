package invest.lixinger.company.calculateBestCompany;

import java.util.*;

public class test {
    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("KFC", "kfc");
        map.put("WNBA", "wnba");
        map.put("NBA", "nba");
        map.put("CBA", "cba");
        List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());

        List<Map.Entry<String, String>> listMap = (List<Map.Entry<String, String>>) sortMapByKey(map);    //按Key进行排序
        System.out.println("listMap = " + listMap);
    }





    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {

        Map<String, String> sortMap = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }
}
