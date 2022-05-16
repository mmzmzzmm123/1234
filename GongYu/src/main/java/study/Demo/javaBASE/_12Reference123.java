package study.Demo.javaBASE;

class Student46546{
    public String Name;

    public Student46546(String Name) {
        this.Name=Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
    public String getName(){
        return Name;
    }
}
//证明java是按照值传递的方式
class Reference123 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Student46546 s1 = new Student46546("小张");
        Student46546 s2 = new Student46546("小李");
        Reference123.swap(s1, s2);
        System.out.println("s1:" + s1.getName());
        System.out.println("s2:" + s2.getName());
    }

    private static void swap(Student46546 x, Student46546 y) {
        Student46546 temp = x;
        x = y;
        y = temp;
        System.out.println("x:" + x.getName());
        System.out.println("y:" + y.getName());
    }
}