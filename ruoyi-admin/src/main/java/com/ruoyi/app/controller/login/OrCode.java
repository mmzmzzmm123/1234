package com.ruoyi.app.controller.login;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.nio.file.Paths;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;


@RestController
@RequestMapping("/app/login")
@Api(value = "登录模块", tags = {"二维码工具"})
public class OrCode {

    public String imageMake(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println("-------------------------------------");
        System.out.println(uuid);
//        String qrCodePath = "D:\\qrcode"+uuid+".png";
        String qrCodePath = "/profile/upload"+uuid+".png";
        File file  = new File(qrCodePath);
        if (file.exists()){
            System.out.println(qrCodePath + "已存在");
        }else {
            try {
                //创建文件
                file.createNewFile();
                //创建文件夹
                //file.mkdirs();
                System.out.println(qrCodePath + "已创建");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return qrCodePath;
    }

    // 生成二维码
    @RequestMapping("/getCode")
    public  Map<String , String> getCode( @RequestParam(value = "codeparam") String qrCodeParam , HttpServletResponse response){
        Map<String , String> map = new HashMap<>();
        // 二维码内容  contents  二维码连接
        String qrCodePath = imageMake();
        // 表示是二维码
        BarcodeFormat qrCode = BarcodeFormat.QR_CODE;
        // 二维码宽度
        int width = 300;
        // 二维码高度
        int height = 300;
        // 返回的图片格式
        String format = "png";
        response.setContentType("image/png");
        try {
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            // 设置二维码编字符集
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 设置二维码的外间距
            hints.put(EncodeHintType.MARGIN, 10);
            // 设置二维码容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 创建二维码对象
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeParam , qrCode, width, height, hints);
            // 输出二维码
//            MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
            MatrixToImageWriter.writeToPath(bitMatrix, format, Paths.get(qrCodePath));

        } catch (WriterException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        map.put("images_url", qrCodePath);
        return map;
    }



}
