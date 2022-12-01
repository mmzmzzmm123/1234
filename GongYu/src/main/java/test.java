import java.util.Arrays;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String a="190837,1*9";
        List<String> list= Arrays.asList(a.split(","));
        list.forEach(System.out::println);
    }
}
