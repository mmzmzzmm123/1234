package study.leecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
//        String str=sc.nextLine();
        String str="baaaAbb";//aaabbbA
        Map<Character,Integer>map=new HashMap<>();
        for(int i=0;i<str.length();i++){
            char now=str.charAt(i);
            map.put(now,map.getOrDefault(now,0)+1);
        }
        List<Character>list=new ArrayList<>(map.keySet());
        Collections.sort(list, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                if(map.get(o1)!=map.get(o2)){
                    return map.get(o2)-map.get(o1);
                }else if(map.get(o1)==map.get(o2)){
                    return o1-o2;
                }
                return o1-o2;
            }
        });
        StringBuffer sb=new StringBuffer();
        int size=list.size();
//        for(Map.Entry<Character,Integer>temp:map.entrySet()){
//            System.out.println(temp.getValue());
//        }
        for(int i=0;i<size;i++){
            char now=list.get(i);
            int pinlv=map.get(now);
            for(int j=0;j<pinlv;j++){
                sb.append(now);
            }
        }
        System.out.println(sb);
    }
}