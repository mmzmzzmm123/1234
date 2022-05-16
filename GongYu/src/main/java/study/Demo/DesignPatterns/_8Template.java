package study.Demo.DesignPatterns;

public class _8Template  {
    public static void main(String[] args) {
        CaffeineBeverage caffeineBeverage = new Coffee();
        caffeineBeverage.prepareRecipe();
        System.out.println("-----------");
        //����������¶����㷨��ĳЩ���裬�����øı��㷨�Ľṹ��
        caffeineBeverage = new Tea();
        caffeineBeverage.prepareRecipe();
    }
}
abstract class CaffeineBeverage {
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    abstract void brew();
    abstract void addCondiments();
    void boilWater() {
        System.out.println("boilWater");
    }
    void pourInCup() {
        System.out.println("pourInCup");
    }
}
//����������¶����㷨��ĳЩ���裬�����øı��㷨�Ľṹ��
class Coffee extends CaffeineBeverage {
    @Override
    void brew() {
        System.out.println("Coffee.brew");
    }
    @Override
    void addCondiments() {
        System.out.println("Coffee.addCondiments");
    }
}
class Tea extends CaffeineBeverage {
    @Override
    void brew() {
        System.out.println("Tea.brew");
    }
    @Override
    void addCondiments() {
        System.out.println("Tea.addCondiments");
    }
}
