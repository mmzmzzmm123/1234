package com.ruoyi.system.mapper.extra;

import com.ruoyi.system.domain.PostOrderImages;
import com.ruoyi.system.mapper.PostOrderImagesMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 发单图片Mapper接口
 *
 * @author ruoyi
 * @date 2024-06-21
 */
public interface PostOrderImagesExtraMapper extends PostOrderImagesMapper {

    int insertBatch(@Param("images") List<PostOrderImages> images);
}
