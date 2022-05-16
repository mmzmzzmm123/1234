package study.Demo.javaBASE;

class Person {
    private Integer id;
    private String personName;

    public Person(String personName) {
        this.personName = personName;
    }
    public String getPersonName(){
        return this.personName;
    }
    public void setPersonName(String personName){
        this.personName=personName;
    }
}
public class _13TransferValueDemo {

    public void changeValue1(int age) {
        age = 30;
    }

    public void changeValue2(Person person) {
        person.setPersonName("XXXX");
    }
    public void changeValue3(String str) {
        str = "XXX";
    }

    public static void main(String[] args) {
        _13TransferValueDemo test = new _13TransferValueDemo();

        // 定义基本数据类型
        int age = 20;
        test.changeValue1(age);
        System.out.println("age ----" + age);

        // 实例化person类
        Person person = new Person("abc");
        test.changeValue2(person);
        System.out.println("personName-----" + person.getPersonName());

        // String
        String str = "abc";
        test.changeValue3(str);
        System.out.println("string-----" + str);

    }
}
