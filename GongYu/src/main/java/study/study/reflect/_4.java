package study.study.reflect;

import java.lang.annotation.ElementType;

public class _4{
    public static void main(String[] args) {
        Class c1= Object.class;//类
        Class c2= Runnable.class;//接口
        Class c3= String[].class;//一维二维数组
        Class c4= int[][].class;
        Class c5=Override.class;//注解
        Class c6= ElementType.class;//枚举
        Class c7= Integer.class;//基本数据类型
        Class c8= void.class;//void
        Class c9= Class.class;//Class
    }
}
