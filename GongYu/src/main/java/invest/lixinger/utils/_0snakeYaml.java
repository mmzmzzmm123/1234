package invest.lixinger.utils;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.*;
//https://blog.csdn.net/qq_40321119/article/details/108510890
public class _0snakeYaml {
    @Test
    public void test1() throws FileNotFoundException {
        InputStream inputStream = _0snakeYaml.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map conf = new Yaml().load(inputStream);
        System.out.println(conf.getOrDefault("metricsList","unknown"));  //
        List list=(List)conf.getOrDefault("metricsList","unknown");
        for(Object temp:list){
            System.out.println(temp);
        }
        System.out.println("------------");
        System.out.println(list.get(0));
    }
    //
    @Test
    public void test2(){
        Yaml yaml = new Yaml(new Constructor(Person.class));
        InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("person.yml");
        Person ret = yaml.load(inputStream);
        System.out.println(ret);
    }

    @Test
    public void test3(){
        Yaml yaml = new Yaml();
        Person ret = (Person) yaml.loadAs(this.getClass().getClassLoader().getResourceAsStream("person.yml"),Person.class);
        Assert.assertNotNull(ret);
    }

}
