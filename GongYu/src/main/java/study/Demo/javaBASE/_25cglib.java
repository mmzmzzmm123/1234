package study.Demo.javaBASE;
//https://huanglei.blog.csdn.net/article/details/101012177

import java.lang.reflect.Method;

//public class _25cglib implements MethodInterceptor{
//    private Object target; // 代理对象
//    public Object getInstance(Object target) {
//        this.target = target;
//        Enhancer enhancer = new Enhancer();
//        // 设置父类为实例类
//        enhancer.setSuperclass(this.target.getClass());
//        // 回调方法
//        enhancer.setCallback(this);
//        // 创建代理对象
//        return enhancer.create();
//    }
//    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        System.out.println("调用前");
//        Object result = methodProxy.invokeSuper(o, objects); // 执行方法调用
//        System.out.println("调用后");
//        return result;
//    }
//
//    public static void main(String[] args) {
//        // cglib 动态代理调用
//        _25cglib proxy = new _25cglib();
//        Panda panda = (Panda)proxy.getInstance(new Panda());
//        panda.eat();
//    }
//}
//
//
//class Panda {
//    public void eat() {
//        System.out.println("The panda is eating");
//    }
//}