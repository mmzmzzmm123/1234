package com.ruoyi.system.service.extra;

import com.ruoyi.system.service.IPostOrderImagesService;

import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-21 11:20:26
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-21     张李鑫                     1.0         1.0 Version
 */
public interface IPostOrderImagesExtraService extends IPostOrderImagesService {
    int insertPostOrderImageBatch(List<String> imageList, Long orderId);
}
