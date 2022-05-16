package work.cib.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//本代码地址:https://blog.csdn.net/qq_37284798/article/details/81983756
public class test2 {
    // 匹配中文字符
    // \\u4e00和\\u9fa5是中文字符的范围
    @Test
    public void test1() {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher matcher = pattern.matcher("2322购购购购232323");
        String dateStr = null;
        if (matcher.find()) {
            dateStr = matcher.group(0);
            System.out.println(dateStr);
        }
    }

    // 匹配从规定字符开始所有非换行字符
    @Test
    public void test2() {
        Pattern pattern = Pattern.compile("1.*");
        Matcher matcher = pattern.matcher("2312购购购购232323");
        String dateStr = null;
        if (matcher.find()) {
            dateStr = matcher.group(0);
            System.out.println(dateStr);
        }
    }

    @Test
    public void test3() {
        String EXAMPLE_TEST = "This is my small example string ";
        Pattern pattern = Pattern.compile("\\w+");
        // 如果您想忽略大小写敏感度, 你可以使用这个语句:
//         Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(EXAMPLE_TEST);
        // 检查所有的结果
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        System.out.println("-------------------");
        // 现在创建一个新的pattern和Matcher以用选项卡替换空格
        Pattern replace = Pattern.compile("\\s+");
        Matcher matcher2 = replace.matcher(EXAMPLE_TEST);
        System.out.println(matcher2.replaceAll("\t"));
    }

    // m.group(0)为全体匹配得到的值
    @Test
    public void test4() {
        String line = "My name is GongYu, I am a Ciber. I am 21 years old 12";
        Pattern r = Pattern.compile("(\\D*)(\\d+)(.*)");
        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
        } else {
            System.out.println("NO MATCH");
        }

        String pattern = "Ciber";
        boolean isMatch = Pattern.matches(pattern, line);
        System.out.println("字符串中是否包含了 'Ciber' 子字符串? " + isMatch);
    }

    //单词边界就是单词和符号之间的边界
    //单词与数字之间没有边界
    @Test
    public void test5() {
        String str = "(中文问号？asdf123???英文)问号?我是华丽[的制表符\t]我是华丽{的空格符 我是华丽}的换行符\n";
        String rex = "\\b";
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(str);
        String[] result = pattern.split(str);
        for (String string : result) {
            System.out.println("分割的字符串:" + string);
        }
    }

    @Test
    public void test6() {
        String REGEX = "dog";
        String INPUT = "The dog says meow. All dogs say meow.";
        String REPLACE = "cat";

        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);
        INPUT = m.replaceAll(REPLACE);
        System.out.println(INPUT);
    }

    @Test
    public void test7() {
        String REGEX = "a*b";
        String INPUT = "aabfooaabfooabfoobkkk";
        String REPLACE = "-";
        Pattern p = Pattern.compile(REGEX);
        // 获取 matcher 对象
        Matcher m = p.matcher(INPUT);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, REPLACE);
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
    }

    @Test
    public void test8() {
        String str = " 2 ";
        String rex = "\\b2\\b";
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配不成功");
        }
    }

}

//多重条件匹配|
// 对于字符串匹配查找多个符合条件的字符字串，我们用到group（），
// group(num=0)： 匹配的整个表达式的字符串，group() 可以一次输入多个组号，在这种情况下它将返回一个包含那些组所对应值的元组。
// groups() ： 返回一个包含所有小组字符串的元组，从 1 到 所含的小组号。
