package com.ruoyi.udef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Konst {
    public static final String R_STATUS_INIT = "init";
    public static final String R_STATUS_RUNNING = "running";
    public static final String R_STATUS_STOPPED = "stopped";

    public static final String P_STATUS_RUNNING = "running";
    public static final String P_STATUS_TRIGGERED = "triggered";
    public static final String P_STATUS_CLOSED = "closed";

    public static final String S_PRIORITY_SOLID="solid";
    public static final String S_PRIORITY_WATCHER="watcher";

    public static final String DOMAIN = "divine14";

    public static String padding(Integer scale, Long id) {
        StringBuilder idStr = new StringBuilder(id + "");
        if(idStr.length() > scale){
            return idStr.substring(idStr.length() - scale);
        } else {
            while (idStr.length() < scale) {
                idStr.append("0");
            }
        }
        return idStr.toString();
    }

    public static String padding(Integer scale, Double stop) {
        StringBuilder idStr = new StringBuilder(stop + "");
        if(idStr.length() > scale){
            return idStr.substring(idStr.length() - scale);
        } else {
            while (idStr.length() < scale) {
                idStr.append("0");
            }
        }
        return idStr.toString();
    }

    public static void main(String[] args) {
        System.out.println(padding(5, 1l));
        System.out.println(padding(5, 10000l));
        System.out.println(padding(5, 100000l));

        Map<String,String> tt = new ConcurrentHashMap<>();
        System.out.println(tt.putIfAbsent("1","1"));
    }
}
