package study.Demo.javaBASE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _29quchong {
    public  static  void  main(String[]  args)  {
        List<_28People> listPeople  =  new ArrayList<_28People>();
        listPeople.add(new  _28People("张三",  "11111"));
        listPeople.add(new  _28People("张三",    "22222"));
        listPeople.add(new  _28People("李四",    "33333"));
        listPeople.add(new  _28People("张三",    "22222"));
 
        Set<_28People> setData  =  new HashSet<_28People>();
        setData.addAll(listPeople);
        System.out.println("list："  +  listPeople.toString());
        System.out.println("set："  +  setData.toString());

    }
}
