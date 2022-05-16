package study.Demo.DesignPatterns;

public abstract  class _5Prototype {
    abstract _5Prototype myClone();
}

class ConcretePrototype extends _5Prototype {
    private String filed;
    public ConcretePrototype(String filed) {
        this.filed = filed;
    }
    @Override
    _5Prototype myClone() {
        return new ConcretePrototype(filed);
    }
    @Override
    public String toString() {
        return filed;
    }
}

class _6PrototypeClient {
    public static void main(String[] args) {
        //ʹ��ԭ��ʵ��ָ��Ҫ�������������
        _5Prototype prototype = new ConcretePrototype("abc");
        //ͨ���������ԭ���������¶���
        _5Prototype clone = prototype.myClone();
        System.out.println(clone.toString());
    }
}
