package com.ruoyi.common.utils;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.web.method.HandlerMethod;

public class ReflectUtils {

    private ReflectUtils() {
    }

    /**
     * 获取类的泛型参数
     *
     * @param clazz 目标类
     * @return 类的泛型参数
     */
    public static Type[] getGenericParameter(Class clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        }
        return new Type[]{};
    }

    public static String getMethodSignature(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    public static String getMethodSignature(HandlerMethod method) {
        return method.getBeanType().getName() + "." + method.getMethod().getName();
    }
}
