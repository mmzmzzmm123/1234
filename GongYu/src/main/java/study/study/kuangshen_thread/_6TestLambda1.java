package study.study.kuangshen_thread;

public class _6TestLambda1 {
    //3静态内部类
    static class Like2 implements ILike{

        @Override
        public void lambda() {
            System.out.println("静态内部类+lambda");
        }
    }

    public static void main(String[] args) {
        ILike like=new Like();//用接口创建对象，接口new实现类
        like.lambda();
        like=new Like2();
        like.lambda();

        //4局部内部类
        class Like3 implements ILike{
            @Override
            public void lambda() {
                System.out.println("局部内部类+lambda");
            }
        }
        like=new Like3();
        like.lambda();
        //5匿名内部类,没有类的名称，必须借助接口或者父类
        like=new Like(){
            @Override
            public void lambda() {
                System.out.println("匿名内部类+lambda");
            }
        };//有分号，因为是一个语句了
        like.lambda();

        //6用lambda简化
        like=()->{
            System.out.println("lambda表达式");
        };//有分号，因为是一个语句了
        like.lambda();
    }
}

//定义一个函数式接口
interface  ILike{
    void lambda();
}

//2实现类
class Like implements ILike{
    @Override
    public void lambda() {
        System.out.println("lambda");
    }
}
