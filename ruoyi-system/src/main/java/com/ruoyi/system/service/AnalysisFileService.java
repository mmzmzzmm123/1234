package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.dto.play.WordPageDTO;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.WordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ruoyi.common.constant.RedisKeyConstans.PLAY_FILE_CONTENT;

@Slf4j
@Service
public class AnalysisFileService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 解析剧本文件,返回id
     */
    public WordPageDTO analysisPlayWord(MultipartFile file) {
        WordPageDTO pageDTO = new WordPageDTO();

        List<String> contentList = WordUtil.readWordDocumentByUrl(file);
        if (contentList.isEmpty()) {
            pageDTO.setPage(0);
            pageDTO.setPageSize(0);
            return pageDTO;
        }

        String id = Ids.getId();
        //分页计算, 小于1m
        int page = (int) ((file.getSize() / 1024 / 512) + 1);
        int size = contentList.size() / page;

        try {
            SessionCallback sessionCallback = new SessionCallback() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    //开启事务
                    redisOperations.multi();

                    int i = 0;
                    for (String content : contentList) {
                        redisTemplate.opsForZSet().add(PLAY_FILE_CONTENT + id, content, i++);
                    }

                    return redisOperations.exec();
                }
            };

            redisTemplate.multi();
            redisTemplate.execute(sessionCallback);
        } catch (Exception e) {
            log.error("analysisPlayWord:{}", e);
        }

        pageDTO.setId(id);
        pageDTO.setPage(page);
        pageDTO.setPageSize(size);
        return pageDTO;
    }

    public List<String> playWordContentList(WordPageDTO pageDTO) {
        redisTemplate.delete(PLAY_FILE_CONTENT + "65a686e324f4e107943ddf62");

        List<String> ret = new ArrayList<>();
        if (StringUtils.isEmpty(pageDTO.getId())) {
            return ret;
        }

        long start = (long) (pageDTO.getPage() - 1) * pageDTO.getPageSize();
        long end = (long) pageDTO.getPage() * pageDTO.getPageSize() - 1;
        Set<String> stringSet = redisTemplate.opsForZSet().range(PLAY_FILE_CONTENT + pageDTO.getId(), start, end);
        if (stringSet == null) {
            return ret;
        }
        return new ArrayList<>(stringSet);
    }
}
