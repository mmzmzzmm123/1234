package study.Demo.javaBASE;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
//https://huanglei.blog.csdn.net/article/details/101012177

public class _21JDKProxy implements InvocationHandler {
    private Object target; // 代理对象

    public Object getInstance(Object target) {
        this.target = target;
        // 取得代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用前");
        Object result = method.invoke(target, args); // 方法调用
        System.out.println("调用后");
        return result;
    }

    public static void main(String[] args) {
        // JDK 动态代理调用
        _21JDKProxy proxy = new _21JDKProxy();
        Animal654654 dogProxy = (Animal654654) proxy.getInstance(new Cat5465498());
        //代理对象.方法→自动调用invoke里面的内容
        dogProxy.eat();
    }
}


interface Animal654654 {
    void eat();
}

class Dog65464 implements Animal654654 {
    @Override
    public void eat() {
        System.out.println("The dog is eating");
    }
}

class Cat5465498 implements Animal654654 {
    @Override
    public void eat() {
        System.out.println("The cat is eating");
    }
}
