package study.Demo.javaBASE;

class BirthDate {
    private int day;
    private int month;
    private int year;
    // 形参d、m、y存在栈中
    public BirthDate(int d, int m, int y) {
        // 实例变量day、month、year存在堆中
        day = d;
        month = m;
        year = y;
    }
    // 省略get,set方法………
}

class _3DuiAndZhan{
    public static void main(String args[]){
        // 局部变量保存在栈中
        int date = 9;
        _3DuiAndZhan test = new _3DuiAndZhan();
        test.change(date);
        BirthDate d1= new BirthDate(7,7,1970);
    }
    //局部变量i保存在栈
    public void change(int i){
        i = 1234;
    }
}

