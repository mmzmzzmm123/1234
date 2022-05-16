package work.cib.xml_socket;

import work.cib.xml_socket.TestVo.Req_TestVo_msg;
import work.cib.xml_socket.TestVo.Resp_TestVo_msg;
import work.cib.xml_socket.TestVo.Req_TestVo_msg.Req;
import work.cib.xml_socket.TestVo.Req_TestVo_msg.Req.Body;
import work.cib.xml_socket.TestVo.Req_TestVo_msg.Req.Head;

public class Test {
    public static void main(String[] args) {
        // 获取生成xml的实体类
        Req_TestVo_msg msg = getMsg();
        // 将对象转化为XML
        String xml = XMLUtil.pack(Req_TestVo_msg.class, msg);
//        System.out.println(xml);
        // 调用Socket
        String rtnxml = Test_client.sendMessToClinet(xml);
        System.out.println("rtnxml=========" + rtnxml);
        // 将xml报文转化为对象
        Object obj = XMLUtil.unpack(Resp_TestVo_msg.class, rtnxml);
        Resp_TestVo_msg resp = null;
        if (obj instanceof Resp_TestVo_msg) {
            System.out.println(".......第二次进行unpack.....");
            resp = (Resp_TestVo_msg) XMLUtil.unpack(Resp_TestVo_msg.class,
                    rtnxml);
        } else {
            // 异常处理
        }
        System.out.println("resp.getResp().getHead():=" + resp.getResp().getHead());
        // TODO 返回信息处理
    }

    private static Req_TestVo_msg getMsg() {
        // 创建报文实体类
        Req_TestVo_msg msg = new Req_TestVo_msg();
        Req req = new Req();
        // 报文头
        Head head = new Head();
        // 报文体
        Body body = new Body();
        // 在报文头中设值

        head.setJydm("1103");// 交易代码
        head.setZddh("DXPT");// 终端代号
        head.setJrxtbh("12345");// 前台系统编号
        head.setJrxtmc("ces");// 前台系统名称
        head.setYwlx("89");// 业务类型
        head.setQtrq("20220216");// 前台发起日期
        head.setQtjylsxh("12345689");// 前台流水号
        head.setDqdh("34");// 地区代号
        head.setJygy("9988");// 发送柜员
        head.setJyjg("601");// 交易机构
        head.setTdlx("88");// 通道类型
        head.setScwjbz("0");// 上传文件标志 0-报文 1- 文件
        head.setScwjm("");// 上传文件名
        head.setScwjlj("");// 上传文件路径

        // 在报文体中设值
        body.setSjhm("15923335596");// 手机号
        body.setSjyzbz("0");// 验证手机是否在短信平台有签约 0-不验证(默认) 1-验证
        body.setDxfsnr("测试通讯");// 短信发送内容
        //
        req.setHead(head);
        req.setBody(body);
        msg.setReq(req);

        return msg;
    }

}
