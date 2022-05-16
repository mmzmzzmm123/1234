package invest.lixinger.utils;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.Serializable;


public class fastjson2 {

    // Java对象转化为JSON对象
    @Test
    public void javaobj2jsonObj() {
        Student stu = new Student("龚宇", "m", 2);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(stu);
        System.out.println("Java对象转化为JSON对象\n" + jsonObject);

    }

    // JSON对象转换成Java对象
    @Test
    public void jsonObj2javaObj() {
        Student stu = new Student("公众号编程大道", "m", 2);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(stu);
        Student student = JSONObject.toJavaObject(jsonObject, Student.class);
        System.out.println("JSON对象转换成Java对象\n" + student);
    }

    // JSON字符串转换成Java对象
    @Test
    public void jsonString2javaObj() {
        String stuString = "{\"age\":2,\"name\":\"公众号编程大道\",\"sex\":\"m\"}";
        Student student1 = JSONObject.parseObject(stuString, Student.class);
        System.out.println("JSON字符串转换成Java对象\n" + student1);
    }

    // Java对象转换成JSON字符串
    @Test
    public void javaobj2jsonString() {
        Student stu = new Student("龚宇", "m", 2);
        String stuString = JSONObject.toJSONString(stu);
        System.out.println("Java对象转换成JSON字符串\n" + stuString);

    }

    @Test
    public void jsonObj2jsonString() {
        Student stu = new Student("公众号编程大道", "m", 2);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(stu);
        String jsonString = jsonObject.toJSONString();
        System.out.println("JSON对象转换为JSON字符串\n" + jsonString);

    }

}

class Student implements Serializable {
    Student() {
    }

    String name;
    String sex;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    Student(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
}