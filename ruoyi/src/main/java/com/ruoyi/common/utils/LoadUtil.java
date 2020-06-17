package com.ruoyi.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoadUtil {
    private static final int SIZE = 1024;
    private static final String CHARSET_NAME = "UTF-8";

    public static String loadContent(String name) {

        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, CHARSET_NAME), SIZE);
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                stringBuffer.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * @param name
     * @return
     */
    public static List<String> loadList(String name) {
        List<String> result = new ArrayList<>();
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET_NAME), SIZE);
            String theWord = null;
            do {
                try {
                    theWord = br.readLine();
                    if (null != theWord)
                        result.add(theWord);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } while (theWord != null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result;
    }
//  这样写有什么问题吗？
//    private static BufferedReader readLine(String name) {
//        InputStream is = null;
//        try {
//            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
//            BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET_NAME), SIZE);
//            return br;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (is != null) {
//                    is.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return null;
//    }

    /**
     * @param name
     * @return
     */
    public static Map<String, String> loadDict(String name) {
        Map<String, String> result = new LinkedHashMap<>();
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET_NAME), SIZE);
            String theWord = null;
            do {
                try {
                    theWord = br.readLine();
                    if (null != theWord) {
                        String[] pair = theWord.split(" ");
                        result.put(pair[0], pair[1]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } while (theWord != null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result;
    }
}
