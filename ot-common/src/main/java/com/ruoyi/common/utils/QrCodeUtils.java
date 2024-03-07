package com.ruoyi.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * @Title: QrCodeUtils
 * @Author itmei
 * @Package com.cloud.commons.utils
 * @Date 2023/7/23 22:56
 * @description: 二维码工具类
 */
public class QrCodeUtils {
    private static final String CHARSET = "utf-8";
    public static final String FORMAT = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int LOGO_WIDTH = 60;
    // LOGO高度
    private static final int LOGO_HEIGHT = 60;

    /**
     * 生成二维码
     *
     * @param content  内容
     * @param destPath 存储地址
     * @throws Exception
     */
    public static String encode(String content, String destPath, String fileName) throws Exception {
        System.out.println("保存二维码内容：" + content);
        BufferedImage image = QrCodeUtils.createImage(content, null, false);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param logoPath     logo地址
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception
     */
    public static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入logo图片
        if (!StringUtils.isEmpty(logoPath)) {
            QrCodeUtils.insertImage(image, logoPath, needCompress);
        }
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param logoPath     LOGO图片地址
     * @param needCompress 是否压缩
     * @throws IOException
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = FileUtils.getResourceAsStream(logoPath);
            Image src = ImageIO.read(inputStream);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            if (needCompress) { // 压缩LOGO
                if (width > LOGO_WIDTH) {
                    width = LOGO_WIDTH;
                }
                if (height > LOGO_HEIGHT) {
                    height = LOGO_HEIGHT;
                }
                Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                src = image;
            }
            // 插入LOGO
            Graphics2D graph = source.createGraphics();
            int x = (QRCODE_SIZE - width) / 2;
            int y = (QRCODE_SIZE - height) / 2;
            graph.drawImage(src, x, y, width, height, null);
            Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
            graph.setStroke(new BasicStroke(3f));
            graph.draw(shape);
            graph.dispose();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．
     * (mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }
}
