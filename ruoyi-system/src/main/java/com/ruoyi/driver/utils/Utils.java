package com.ruoyi.driver.utils;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Administrator on 2020/1/3.
 */
@Slf4j
public class Utils {

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if(hex.length() == 1){
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if(hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static double toBtc(Long wbtc) {
        return new BigDecimal(wbtc).divide(new BigDecimal(100000000D)).doubleValue();
    }

    public static Long getTimeMs(int delay) {
        return System.currentTimeMillis() + delay * 1000;
    }

    public static Boolean isDebug(String model) {
        return model.equals("debug");
    }

    public static Boolean isNotOver(int state) {//-2:失败 -1:撤单成功 0:等待成交 1:部分成交 2:完全成交 3:下单中4:撤单中6: 未完成（等待成交+部分成交） 7:已完成（撤单成功+完全成交）
        return state == 0 || state == 1 || state == 3 || state == 4 || state == 6;
    }

    public static String getNotOverCondition(String orderId) {
        return "&state=6&before=" + orderId;
    }

    public static String getOverCondition(String orderId) {
        return "&state=7&before=" + orderId;
    }

    public static String toUnder(String instrumentId) {
        return instrumentId.substring(0, instrumentId.lastIndexOf("-")).toLowerCase();
    }

    public static void main(String[] args) {
//        System.out.println(toUnder("EOS-USD-200327"));

        String time = "2019-03-19T16:00:00.000Z";
        System.out.println(utcToLocal(time));
        System.out.println(localToUTC(1553011200000L));
    }

    public static <T> List<List<T>> splitToBatch(List<T> items) {
        int batchSize = new Double(Math.ceil(items.size() / 10d)).intValue();
        List<List<T>> batchs = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            List<T> batch = new ArrayList<>();
            batch.addAll(items.subList(i * 10, i * 10 + 10 <= items.size() ? i * 10 + 10 : items.size()));
            batchs.add(batch);
        }
        return batchs;
    }

    public static <T> List<List<T>> splitToBatch20(List<T> items) {
        int batchSize = new Double(Math.ceil(items.size() / 20d)).intValue();
        List<List<T>> batchs = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            List<T> batch = new ArrayList<>();
            batch.addAll(items.subList(i * 20, i * 20 + 20 <= items.size() ? i * 20 + 20 : items.size()));
            batchs.add(batch);
        }
        return batchs;
    }

    public static Double toFixed(Double d) {
        return new BigDecimal(d).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double toFixed(Double d, Integer wei) {
        return new BigDecimal(d).setScale(wei, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String extractBase(String instrumentId) {
        return instrumentId.split("-")[0];
    }

    public static String extractQuote(String instrumentId) {
        return instrumentId.split("-")[1];
    }

    public static String getCurrency(String instrument) {
        return instrument.split("-")[0];
    }

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 6; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    public static String localToUTC(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return sdf.format(utcDate);
    }

    public static Long utcToLocal(String utcTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locatlDate.getTime();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
                ipAddress = request.getRemoteAddr();
                if(ipAddress.equals("127.0.0.1")){
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if(ipAddress != null && ipAddress.length() > 15){ // "***.***.***.***".length()
                // = 15
                if(ipAddress.indexOf(",") > 0){
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }


        return ipAddress;
    }

    public static String getDayStr() {
        return getDayStr(null);
    }

    public static String getDayStr(Calendar calendar) {
        if(calendar == null){
            calendar = Calendar.getInstance();
            calendar.setTime(new Date());
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

    }

    public static Integer caclsDayNum(Date start, Date end) {
        return (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
    }


    @Data
    public static class OkError {
        @SerializedName("error_message")
        String errorMessage;
        String code;
        @SerializedName("error_code")
        String errorCode;
        String message;
    }


    public static Long minStepNum(Double mount, Double deltaPrice, Double start, Double minStep) {
        Long sqrtNum = Double.valueOf(Math.floor(Math.sqrt(mount))).longValue();
        Long caclNum = Double.valueOf(Math.floor(deltaPrice / (start * minStep))).longValue();
        log.info("delta {} start {}", deltaPrice, start);
        return Math.min(sqrtNum, caclNum);
    }


}
