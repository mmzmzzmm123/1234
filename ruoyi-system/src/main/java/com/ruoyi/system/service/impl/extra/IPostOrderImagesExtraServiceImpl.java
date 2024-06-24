package com.ruoyi.system.service.impl.extra;

import com.ruoyi.system.domain.PostOrderImages;
import com.ruoyi.system.mapper.extra.PostOrderImagesExtraMapper;
import com.ruoyi.system.service.extra.IPostOrderImagesExtraService;
import com.ruoyi.system.service.impl.PostOrderImagesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-21 11:20:35
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-21     张李鑫                     1.0         1.0 Version
 */
@Service
@Primary
public class IPostOrderImagesExtraServiceImpl extends PostOrderImagesServiceImpl implements IPostOrderImagesExtraService {

    @Autowired
    private PostOrderImagesExtraMapper postOrderImagesExtraMapper;


    @Override
    public int insertPostOrderImageBatch(List<String> imageList, Long orderId) {
        if (imageList == null || imageList.isEmpty()) {
            //没有要上传的图片
            return 1;
        }
        List<PostOrderImages> insertArrayList = imageList.stream()
                .map((imageUrl) -> build(imageUrl, orderId))
                .collect(Collectors.toList());
        return postOrderImagesExtraMapper.insertBatch(insertArrayList);
    }


    private PostOrderImages build(String imageUrl, Long orderId) {
        PostOrderImages postOrderImages = new PostOrderImages();
        postOrderImages.setImageUrl(imageUrl);
        postOrderImages.setOrderId(orderId);
        return postOrderImages;
    }
}
