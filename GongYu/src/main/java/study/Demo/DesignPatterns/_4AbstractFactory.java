package study.Demo.DesignPatterns;

class AbstractProductA {
}

class AbstractProductB {
}

class ProductA1 extends AbstractProductA {
}

class ProductA2 extends AbstractProductA {
}

class ProductB1 extends AbstractProductB {
}

class ProductB2 extends AbstractProductB {
}

public abstract class _4AbstractFactory {
    abstract AbstractProductA createProductA();
    abstract AbstractProductB createProductB();
}

class AbstractConcreteFactory1 extends _4AbstractFactory {
    AbstractProductA createProductA() {
        return new ProductA1();
    }
    AbstractProductB createProductB() {
        return new ProductB1();
    }
}

class AbstractConcreteFactory2 extends _4AbstractFactory {
    AbstractProductA createProductA() {
        return new ProductA2();
    }
    AbstractProductB createProductB() {
        return new ProductB2();
    }
}

class AbstractClient {
    public static void main(String[] args) {
        _4AbstractFactory abstractFactory = new AbstractConcreteFactory1();
        AbstractProductA productA = abstractFactory.createProductA();
        AbstractProductB productB = abstractFactory.createProductB();
        // do something with productA and productB
    }
}
