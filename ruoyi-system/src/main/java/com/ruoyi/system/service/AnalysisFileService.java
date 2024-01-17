package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.dto.play.WordPageDTO;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.WordUtil;
import com.ruoyi.system.domain.vo.AnalysisPlayFileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        Long time = System.currentTimeMillis();

        WordPageDTO pageDTO = new WordPageDTO();

        List<String> tmpData = WordUtil.readWordDocument(file);
        if (tmpData.isEmpty()) {
            pageDTO.setPage(0);
            pageDTO.setPageSize(0);
            return pageDTO;
        }

        //合并发言
        List<List<String>> playFileContent = new ArrayList<>();
        int index = 0;
        for (String item : tmpData) {
            List<String> tmp;
            if (item.startsWith(WordUtil.spokesman_regex)) {
                tmp = new ArrayList<>();
                tmp.add(item);
                playFileContent.add(index++, tmp);
            } else {
                //上一个发言人的数据
                index--;
                tmp = playFileContent.get(index);
                tmp.add(item);
                playFileContent.set(index++, tmp);
            }
        }

        long time1 = System.currentTimeMillis();
        pageDTO.setRedaFileTime(time1 - time);

        String id = Ids.getId();
        saveAnalysisPlayFile(id, playFileContent);
        pageDTO.setSaveFileTime(System.currentTimeMillis() - time1);

        //分页计算, 每页小于1m
        int page = (int) ((file.getSize() / 1024 / 512) + 1);
        int size = tmpData.size() / page;

        pageDTO.setFileId(id);
        pageDTO.setPage(page);
        pageDTO.setPageSize(size);
        return pageDTO;
    }

    private void saveAnalysisPlayFile(String id, List<List<String>> playFileContent) {
        try {
            SessionCallback sessionCallback = new SessionCallback() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.multi();

                    int no = 0;
                    for (List<String> items : playFileContent) {
                        for (String item : items) {
                            redisTemplate.opsForZSet().add(PLAY_FILE_CONTENT + id, item, no);
                        }
                        no++;
                    }

                    return redisOperations.exec();
                }
            };

            redisTemplate.multi();
            redisTemplate.execute(sessionCallback);
        } catch (Exception e) {
            log.error("analysisPlayWord:{}", e.getMessage());
        }
    }

    public AnalysisPlayFileVO playWordContentList(WordPageDTO pageDTO) {
        AnalysisPlayFileVO ret = new AnalysisPlayFileVO();

        ret.setFileId(pageDTO.getFileId());
        ret.setPageSize(pageDTO.getPageSize());
        if (StringUtils.isEmpty(pageDTO.getFileId())) {
            ret.setContentInfoList(new ArrayList<>());
            return ret;
        }

        long start = (long) (pageDTO.getPage() - 1) * pageDTO.getPageSize();
        long end = (long) pageDTO.getPage() * pageDTO.getPageSize() - 1;
        Set<ZSetOperations.TypedTuple<Object>> stringSet = redisTemplate.opsForZSet().rangeWithScores(PLAY_FILE_CONTENT + pageDTO.getFileId(), start, end);
        if (null == stringSet) {
            ret.setContentInfoList(new ArrayList<>());
            return ret;
        }

        List<AnalysisPlayFileVO.ContentInfo> contentInfoList = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Object> item : stringSet) {
            AnalysisPlayFileVO.ContentInfo contentInfo = new AnalysisPlayFileVO.ContentInfo();
            contentInfo.setNo(Objects.requireNonNull(item.getScore()).intValue());
            contentInfo.setContent(Objects.requireNonNull(item.getValue()).toString());
            contentInfoList.add(contentInfo);
        }

        ret.setContentInfoList(contentInfoList);
        return ret;
    }
}
