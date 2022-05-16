package study.Demo.DesignPatterns;

//Ѽ�ӣ�Duck���ͻ𼦣�Turkey��ӵ�в�ͬ�Ľ�����Duck �Ľ������� A() �������� Turkey ���� B() ������
//
//Ҫ�� Turkey �� B() ��������� Duck �� A() �������Ӷ��û�ð��Ѽ�ӣ�
interface Duck {
    void A();
}
interface Turkey {
    void B();
}

class WildTurkey implements Turkey {
    @Override
    public void B() {
        System.out.println("gobble!");
    }
}

class TurkeyAdapter implements Duck {
    Turkey turkey;
    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }
    @Override
    public void A() {
        turkey.B();
    }
}

public class _6Adapter {
    public static void main(String[] args) {
        Turkey turkey = new WildTurkey();
        //��ΪTurkeyAdapterʵ����Duck
        Duck duck = new TurkeyAdapter(turkey);
        duck.A();
    }
}
