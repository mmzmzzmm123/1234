package study.Demo.DesignPatterns;

public abstract class _3Factory {
    abstract public Product factoryMethod();
    public void doSomething() {
        Product product = factoryMethod();
        // do something with the product
    }
}
class ConcreteFactory extends _3Factory {
    public Product factoryMethod() {
        return new ConcreteProduct();
    }
}
 class ConcreteFactory1 extends _3Factory {
    public Product factoryMethod() {
        return new ConcreteProduct1();
    }
}
 class ConcreteFactory2 extends _3Factory {
    public Product factoryMethod() {
        return new ConcreteProduct2();
    }
}
