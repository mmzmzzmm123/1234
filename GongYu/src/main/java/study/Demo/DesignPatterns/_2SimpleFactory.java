package study.Demo.DesignPatterns;

interface Product {
}
class ConcreteProduct implements Product {
}
class ConcreteProduct1 implements Product {
}
class ConcreteProduct2 implements Product {
}

public class _2SimpleFactory {
    public Product createProduct(int type) {
        if (type == 1) {
            return new ConcreteProduct1();
        } else if (type == 2) {
            return new ConcreteProduct2();
        }
        return new ConcreteProduct();
    }
}
class Client {
    public static void main(String[] args) {
        _2SimpleFactory simpleFactory = new _2SimpleFactory();
        Product product = simpleFactory.createProduct(1);
        // do something with the product
    }
}
