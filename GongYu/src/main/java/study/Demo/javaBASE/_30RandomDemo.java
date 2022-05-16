package study.Demo.javaBASE;
//随机生成30-100之间的随机数。
public class _30RandomDemo {
    public Integer random() {
        int result = (int)(Math.random()*(100-30))+30;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new _30RandomDemo().random());
    }
}
