package study.study.thread_master.juc2;

public class TestTransferValue {
    public void changeValue1(int age){
        age = 30;
    }
//    public void changeValue2(Person person){
//        person.setPersonName("xxx");
//    }
    public void changeValue3(String str){
        str = "xxx";
    }

//    public static void main(String[] args) {
//        TestTransferValue lixinger.mybatis.test = new TestTransferValue();
//        int age = 20;
//        lixinger.mybatis.test.changeValue1(age);
//        System.out.println("age----"+age);
//
//        Person person = new Person("abc");
//        lixinger.mybatis.test.changeValue2(person);
//        System.out.println("personName-------"+person.getPersonName());
//
//        String str = "abc";
//        lixinger.mybatis.test.changeValue3(str);
//        System.out.println("String-----"+str);
//    }
}
