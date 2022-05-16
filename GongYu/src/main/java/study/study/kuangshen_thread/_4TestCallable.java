package study.study.kuangshen_thread;

import java.util.concurrent.*;

public class _4TestCallable implements Callable<Boolean> {
    private String url;//网络图片地址
    private String name;//保存的文件名
    @Override
    public Boolean call()
    {
        WebDownLoader webDownLoader=new WebDownLoader();
        webDownLoader.downloader(url,name);
        System.out.println("下载了文件名为"+name);
        return true;
    }

    public _4TestCallable(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        _4TestCallable t1=new _4TestCallable("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%B0%8F%E7%8B%97%E5%9B%BE%E7%89%87&hs=2&pn=2&spn=0&di=68640&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=3788223672%2C2933772794&os=3469738239%2C2819470525&simid=0%2C0&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=&bdtype=0&oriquery=%E5%B0%8F%E7%8B%97%E5%9B%BE%E7%89%87&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20191105%2F15%2F1572937434-vjDJNuBAxl.jpg%26refer%3Dhttp%3A%2F%2Fimage.biaobaiju.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1613979841%26t%3D6b74022d98f53152b4e7143934ed2714&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bktw5kwt37_z%26e3Bv54AzdH3Fi5g2geiwtztAzdH3Fl08nc_z%26e3Bip4s&gsm=3&islist=&querylist=","dog.jpeg");
        _4TestCallable t2=new _4TestCallable("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%B0%8F%E7%8B%97%E5%9B%BE%E7%89%87&hs=2&pn=5&spn=0&di=26840&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=2057173425%2C3288346039&os=3534193242%2C1744020036&simid=4255926711%2C726475710&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=&bdtype=0&oriquery=%E5%B0%8F%E7%8B%97%E5%9B%BE%E7%89%87&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fimg.wohaoyun.com%2Fimg_600%2FM00%2F07%2FB8%2FwKjg2lvW-dGAERdjAAEeYGzmpUA906.jpg%26refer%3Dhttp%3A%2F%2Fimg.wohaoyun.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1613979935%26t%3Dbd8042b4b6488deae4c73854e0c4dd6e&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bo5iw5y7g_z%26e3Bv54AzdH3Ff3AzdH3F15g2o7ktzitAzdH3Fda8bAzdH3F88AzdH3F8988_z%26e3Bip4s&gsm=6&islist=&querylist=","dog2.jpeg");
        _4TestCallable t3=new _4TestCallable("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%B0%8F%E7%8B%97%E5%9B%BE%E7%89%87&hs=2&pn=4&spn=0&di=13750&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=2136279165%2C2845293579&os=1684394320%2C374773828&simid=3423840844%2C312231934&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=&bdtype=0&oriquery=%E5%B0%8F%E7%8B%97%E5%9B%BE%E7%89%87&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fwww.cpnic.com%2FUploadFiles%2Fimg_0_3308088708_3867205912_26.jpg%26refer%3Dhttp%3A%2F%2Fwww.cpnic.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1613980066%26t%3D73944a43d2a1181ed0e4aeb6cd59da1e&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bvrgtv_z%26e3Bv54AzdH3FrtvAzdH3F%25Ec%25Ba%25bF%25Ec%25lE%25bB%25E0%25bB%25l0%25Ec%25ln%25b8%25E0%25A0%25bD%25Ec%25A9%25A0%25Ec%25bc%25Ab%25Ec%25bF%25bA%25Ec%25lB%25BE%25E0%25bl%25b0%25Ec%25bF%25bA%25E9%25BB%25B0%25Em%25Aa%25BCAzdH3F&gsm=5&islist=&querylist=","dog3.jpeg");
        //创建执行服务
        ExecutorService ser= Executors.newFixedThreadPool(3);//线程池，放3个线程
        //提交执行
        Future<Boolean> r1=ser.submit(t1);
        Future<Boolean> r2=ser.submit(t2);
        Future<Boolean> r3=ser.submit(t3);
        //获取结果
        boolean rs1=r1.get();
        boolean rs2=r2.get();
        boolean rs3=r3.get();
        //关闭服务
        ser.shutdownNow();
    }


}

