package com.stdiet.common.utils.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 对象相关工具类
 * </p>
 *
 * @author xzj
 * @since 2020-11-27
 */
public class ObjectUtils {

    /**
     * @Author xzj
     * @Description 利用反射机制将一个对象中的所有属性值赋值给另一个对象
     * @Date 2020/12/3 18:17
     * @Param object: 原对象
     * @Param clazz: 目标对象对应Class
     * @Return T 目标对象
     **/
    public static <T> T getObjectByObject(Object object, Class<T> clazz) throws Exception {
        return getObjectByObject(object, clazz, new ArrayList<String>(1));
    }

    /**
     * @Author xzj
     * @Description 利用反射机制将一个对象中的所有属性值赋值给另一个对象
     * @Date 2020/12/3 18:17
     * @Param object: 原对象
     * @Param clazz: 目标对象对应Class
     * @Param filterField: 需要过滤掉的属性名称集合
     * @Return T 目标对象
     **/
    public static <T> T getObjectByObject(Object object, Class<T> clazz, List<String> filterField) throws Exception {
        T t = null;
        if (clazz != null && object != null) {
            t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //属性名
                String key = field.getName();
                //以整数形式返回由此 Field 对象表示的字段的 Java 语言修饰符
                int modifierValue = field.getModifiers();
                //PUBLIC: 1 PRIVATE: 2  PROTECTED: 4 只设置4以下修饰词属性，过滤掉filterField中的属性
                if(modifierValue > 4 || (filterField != null && filterField.contains(key)) ){
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Field objectField = object.getClass().getDeclaredField(key);
                    objectField.setAccessible(true);
                    //返回指定对象obj上此 Field 表示的字段的值
                    Object val = objectField.get(object);
                    //将指定对象变量上此 Field 对象表示的字段设置为指定的新值
                    field.set(t, val);
                }catch (Exception e){
                    //System.out.println(object.getClass().getName() + "没有该属性: " + field.getName());
                }
            }
        }
        return t;
    }
}
