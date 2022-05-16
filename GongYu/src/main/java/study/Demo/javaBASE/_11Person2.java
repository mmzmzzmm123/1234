package study.Demo.javaBASE;

class Address2 implements Cloneable {
    public String addr;
    public Address2(String addr) {
        this.addr = addr;
    }
    @Override
    public Object clone() {
        Address2 addr = null;
        try {
            addr = (Address2) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return addr;
    }
}

public class _11Person2 implements Cloneable {
    private String name;
    private int age;
    private Address2 address;
    public Address2 getAddr() {
        return address;
    }
    public void setAddr(Address2 address) {
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
        _11Person2 person = null;
        try {
            person = (_11Person2) super.clone();   //浅复制
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        person.address = (Address2) address.clone();   //深度复制
        return person;
    }

    public static void main(String[] args) {
        Address2 address = new Address2("山东");
        _11Person2 p1 = new _11Person2();
        p1.setName("小王");
        p1.setAge(15);
        p1.setAddr(address);
        //深拷贝
        _11Person2 p3 = (_11Person2) p1.clone();

        p1.setName("小赵");
        p1.setAge(10);
        address.addr = "上海";
        p1.setAddr(address);

        System.out.println(p3.toString());
    }
}
