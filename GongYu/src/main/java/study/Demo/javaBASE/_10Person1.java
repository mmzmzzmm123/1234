package study.Demo.javaBASE;
//深拷贝与浅拷贝https://blog.csdn.net/QAQ123666/article/details/104940711
class Address {
    public String addr;
    public Address(String addr) {
        this.addr = addr;
    }
}

public class _10Person1 implements Cloneable {
    private String name;
    private int age;
    private Address address;
    public Address getAddr() {
        return address;
    }
    public void setAddr(Address address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return this.name + " " + age + " " + address.addr;
    }

    @Override
    public Object clone() {
        _10Person1 stu = null;
        try {
            stu = (_10Person1) super.clone();   //浅复制
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }

    public static void main(String[] args) {
        Address address = new Address("山东");
        _10Person1 p1 = new _10Person1();
        p1.setName("小王");
        p1.setAge(15);
        p1.setAddr(address);
        //直接等于
        _10Person1 p2 = p1;
        //浅拷贝
        _10Person1 p3 = (_10Person1) p1.clone();

        p1.setName("小赵");
        p1.setAge(10);
        address.addr = "上海";
        p1.setAddr(address);

        System.out.println(p2.toString());
        System.out.println(p3.toString());
    }
}

