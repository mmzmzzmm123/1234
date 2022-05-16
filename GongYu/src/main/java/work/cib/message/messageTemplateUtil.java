package work.cib.message;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.regex.Pattern;


public class messageTemplateUtil {
    private static Pattern pattern;
    static {
        pattern = Pattern.compile("((?<=\\{)([a-zA-Z_]{1,})(?=\\}))");
    }

    @Test
    public void testReplace1() {
        String text = "hello {user}, welcome to {place}!";
        String user = "Lucy";
        String place = "China";
        String res = text.replace("{user}", user).replace("{place}", place);
        System.out.println(res);  // 输出   hello Lucy, welcome to China!
    }

    //--------------------------------------------------------------------------
    public String replace(String text, Object... args) {
        System.out.println(args.length);

        for(Object arg:args){

        }
        return MessageFormat.format(text, args);
    }
    @Test
    public void testReplace2() {
        String text = "hello {0}, welcome to {1}!, telephone is {2}";
        String user = "Lucy";
        String place = "China";
        String telephone = "15528606339";
        String test="asdf";
        String ans = replace(text, user, place, telephone,test);
        System.out.println(MessageFormat.format(text, user, place, telephone));
        System.out.println(ans); // 输出   hello Lucy, welcome to China!
    }

    // --------------------------------------------------------------------------------
        public  void m1(String s, String... ss) {
            for (int i = 0; i < ss.length; i++) {
                System.out.println(ss[i]);
            }
        }
        @Test
        public void testReplace3() {
            m1("aaa");
            m1("aaa", "bbb");
        }
}
