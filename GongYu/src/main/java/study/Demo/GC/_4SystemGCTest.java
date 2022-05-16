package study.Demo.GC;


public class _4SystemGCTest {
    public static void main(String[] args) {
        new _4SystemGCTest();
        System.gc();
        //System.runFinalization();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("SystemGCTest finalize");
    }
}
