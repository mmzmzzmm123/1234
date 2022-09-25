package invest.lixinger.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class fastjson1 {
    // 序列化
    @Test
    public void Obj2Json() throws Exception {
        User user1 = new User();
        user1.setName("张三");
        user1.setAge(12);

        // {"age":12,"name":"张三"}
        String json = JSON.toJSONString(user1);
        System.out.println(json);
    }

    @Test
    public void Obj2Array() throws Exception {
        User user1 = new User();
        user1.setName("张三");
        user1.setAge(12);

        User user2 = new User();
        user2.setName("李四");
        user2.setAge(12);

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        UserGroup group = new UserGroup();
        group.setUsers(list);
        group.setName("root");

        // 将List转成JSON
        // [{"age":12,"name":"张三"},{"age":12,"name":"李四"}]
        String json = JSON.toJSONString(list);
        System.out.println(json);

        // 将UserGroup转换成Json
        // {"name":"root","users":[{"age":12,"name":"张三"},{"age":12,"name":"李四"}]}
        String json2 = JSON.toJSONString(group);
        System.out.println(json2);
    }


    // 反序列化为对象
    @Test
    public void Json2Obj() throws Exception {
        String json = "{\"age\":12,\"name\":\"张三\"}";
        // JSON会尽最大可能转换成User对象
        User user = JSON.parseObject(json, User.class);
        System.out.println(user);
    }

    // 反序列化为对象，对象里面包含List集合
    @Test
    public void Json2Obj2() throws Exception {
        String json = "{\"name\":\"root\",\"users\":[{\"age\":12,\"name\":\"张三\"},{\"age\":12,\"name\":\"李四\"}]}";
        UserGroup group = JSON.parseObject(json, UserGroup.class);
        System.out.println(group);

    }

    // 反序列化为集合
    @Test
    public void Json2List() {
        String json = "[{\"age\":12,\"name\":\"张三\"},{\"age\":12,\"name\":\"李四\"}]";
        List<User> list = JSON.parseArray(json, User.class);
        System.out.println(list);
    }


    // 定制序列化
    //如果age属性<18，则在序列化时不显示该属性
    @Test
    public void testPrtopertyJson() throws Exception {
        PropertyFilter filter = new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                // 如果age属性<18，则在序列化时不显示该属性
                if (name.equals("age")) {
                    if ((Integer) value < 18) {
                        return false;
                    }
                }
                return true;
            }
        };

        User user1 = new User();
        user1.setName("张三");
        user1.setAge(18);
        String jsonString = JSON.toJSONString(user1, filter);
        System.out.println(jsonString);
    }
}

class User {
    private String name;
    private int age;

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
        return "User [name=" + name + ", age=" + age + "]";
    }
}

class UserGroup {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    private String name;
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "UserGroup [name=" + name + ", users=" + users + "]";
    }
}
