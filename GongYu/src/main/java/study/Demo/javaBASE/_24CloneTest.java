package study.Demo.javaBASE;

import java.util.Arrays;

public class _24CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        CloneObj cloneObj = new CloneObj();
        cloneObj.name = "大冰";
        cloneObj.age = 30;
        cloneObj.sistersAge = new int[]{18, 19};

        CloneObj cloneObj2 = (CloneObj) cloneObj.clone();
        cloneObj2.name = "小冰";
        cloneObj2.age = 33;
        cloneObj2.sistersAge[0] = 20;

        System.out.println(cloneObj.name + "|" + cloneObj2.name);
        System.out.println(cloneObj.age + "|" + cloneObj2.age);
        System.out.println(Arrays.toString(cloneObj.sistersAge) + "|" + Arrays.toString(cloneObj2.sistersAge));
    }
}
class CloneObj implements Cloneable {
    public String name;
    public int age;
    public int[] sistersAge;
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
