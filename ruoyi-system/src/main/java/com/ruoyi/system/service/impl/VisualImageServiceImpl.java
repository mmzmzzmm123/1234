package com.ruoyi.system.service.impl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VisualImageMapper;
import com.ruoyi.system.domain.VisualImage;
import com.ruoyi.system.service.IVisualImageService;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

/**
 * 图片管理。管理上传的图片Service业务层处理
 *
 * @author yangsj
 * @date 2021-10-28
 */
@Service
public class VisualImageServiceImpl implements IVisualImageService {
    @Autowired
    private VisualImageMapper visualImageMapper;

    /**
     * 查询图片管理。管理上传的图片
     *
     * @param id 图片管理。管理上传的图片主键
     * @return 图片管理。管理上传的图片
     */
    @Override
    public VisualImage selectVisualImageById(Long id) {
        return visualImageMapper.selectVisualImageById(id);
    }

    /**
     * 查询图片管理。管理上传的图片列表
     *
     * @param visualImage 图片管理。管理上传的图片
     * @return 图片管理。管理上传的图片
     */
    @Override
    public List<VisualImage> selectVisualImageList(VisualImage visualImage) {
        return visualImageMapper.selectVisualImageList(visualImage);
    }

    /**
     * 新增图片管理。管理上传的图片
     *
     * @param visualImage 图片管理。管理上传的图片
     * @return 结果
     */
    @Override
    public int insertVisualImage(VisualImage visualImage) {
        visualImage.setCreateTime(DateUtils.getNowDate());
        return visualImageMapper.insertVisualImage(visualImage);
    }

    /**
     * 修改图片管理。管理上传的图片
     *
     * @param visualImage 图片管理。管理上传的图片
     * @return 结果
     */
    @Override
    public int updateVisualImage(VisualImage visualImage) {
        visualImage.setUpdateTime(DateUtils.getNowDate());
        return visualImageMapper.updateVisualImage(visualImage);
    }

    /**
     * 批量删除图片管理。管理上传的图片
     *
     * @param ids 需要删除的图片管理。管理上传的图片主键
     * @return 结果
     */
    @Override
    public int deleteVisualImageByIds(Long[] ids) {
        return visualImageMapper.deleteVisualImageByIds(ids);
    }

    /**
     * 删除图片管理。管理上传的图片信息
     *
     * @param id 图片管理。管理上传的图片主键
     * @return 结果
     */
    @Override
    public int deleteVisualImageById(Long id) {
        return visualImageMapper.deleteVisualImageById(id);
    }


    @Value("${imageDir.visualImage}")
    private String imageDir;

    @Override
    public int addImage(MultipartFile file, Integer heigth, Integer width) {
        // 1.保存到图片本地
        saveImageToLocal(file);

        // 2.插入记录到数据库

        return 0;
    }

    @Override
    public String saveImageToLocal(MultipartFile file) {
        String filePath = RuoYiConfig.getProfile() + imageDir;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String[] fileNameSplit = file.getOriginalFilename().split("\\.");
        String fileType = fileNameSplit[fileNameSplit.length - 1];

        String newFileName = UUID.fastUUID().toString(true) + "." + fileType;
        String fullFileName = filePath + "/" + newFileName;
        try {
            FileOutputStream out = new FileOutputStream(fullFileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFileName;
    }

    @Override
    @Async
    public void divideImage(VisualImage visualImage) {
        String rootPath = RuoYiConfig.getProfile() + imageDir;
        String newFileName = rootPath + "/" + visualImage.getNewName();
        // 读入大图
        File file = new File(newFileName);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fis);

            // 分割成4*4(16)个小图
            int rows = visualImage.getHeight();
            int cols = visualImage.getWidth();
            int chunks = rows * cols;

            // 计算每个小图的宽度和高度
            int chunkWidth = image.getWidth() / cols;
            int chunkHeight = image.getHeight() / rows;

            // 新建文件夹存放子图
            String[] split = visualImage.getNewName().split("\\.");
            String outputDir = rootPath + "/" + split[0];
            File targetFile = new File(outputDir);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            int count = 0;
            BufferedImage imgs[] = new BufferedImage[chunks];
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    //设置小图的大小和类型
                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                    //写入图像内容
                    Graphics2D gr = imgs[count].createGraphics();
                    gr.drawImage(image, 0, 0,
                            chunkWidth, chunkHeight,
                            chunkWidth * y, chunkHeight * x,
                            chunkWidth * y + chunkWidth,
                            chunkHeight * x + chunkHeight, null);
                    gr.dispose();

                    // 输出小图
                    String outFileName = outputDir + "/" + x + "_" + y + "." + split[split.length - 1];
                    ImageIO.write(imgs[count], split[split.length - 1], new File(outFileName));
                    count++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Value("${imageDir.width}")
    private Integer thumbWidth;

    @Value("${imageDir.height}")
    private Integer thumbHeigth;

    @Async
    @Override
    public void saveThumb(VisualImage visualImage) {
        String rootPath = RuoYiConfig.getProfile() + imageDir;
        String newFileName = rootPath + "/" + visualImage.getNewName();
        // 读入大图
        File file = new File(newFileName);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fis);

            // 新建文件夹存放子图
            String[] split = visualImage.getNewName().split("\\.");
            String outputDir = rootPath + "/" + split[0];
            File targetFile = new File(outputDir);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            //设置小图的大小和类型
            BufferedImage imgs = new BufferedImage(thumbWidth, thumbHeigth, image.getType());

            //写入图像内容
            Graphics2D gr = imgs.createGraphics();
            gr.drawImage(image, 0, 0, thumbWidth, thumbHeigth, null);
            gr.dispose();

            // 输出小图
            String outFileName = outputDir +"/thumb."+ split[split.length - 1];
            ImageIO.write(imgs, split[split.length - 1], new File(outFileName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value("${server.port}")
    private int serverPort;

    @Value("${imageDir.visualImage}")
    private String imagePrefix;

    public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch ( UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://"+address.getHostAddress() +":"+this.serverPort;
    }

    @Override
    public Map<String, String> getLink(String fileName) {
        // todo 这里应该查找数据库或本地资源，判断图片是否存在

        HashMap<String, String> map = new HashMap<>();
        String[] split = fileName.split("\\.");
        if (split.length != 2) {
            return null;
        }
        String rootDir = this.getUrl() + Constants.RESOURCE_PREFIX +imagePrefix +"/";
        map.put("subRoot",rootDir + split[0] + "/");
        map.put("original",rootDir+fileName);
        map.put("thumb",rootDir+"thumb."+ split[1]);

        return map;
    }
}
