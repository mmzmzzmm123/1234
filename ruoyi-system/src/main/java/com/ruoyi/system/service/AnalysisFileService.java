package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.dto.play.WordPageDTO;
import com.ruoyi.common.core.domain.entity.play.PlayFile;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.WordUtil;
import com.ruoyi.system.domain.vo.AnalysisPlayFileVO;
import com.ruoyi.system.mapper.PlayFileMapper;
import com.ruoyi.system.service.impl.PlayFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AnalysisFileService {
    //@Autowired
    //private RedisTeplate redisTemplate;

    @Autowired
    private PlayFileService playFileService;

    @Autowired
    private PlayFileMapper playFileMapper;

    /**
     * 解析剧本文件,返回id
     */
    public WordPageDTO analysisPlayWord(MultipartFile file) {
        WordPageDTO pageDTO = new WordPageDTO();
        List<String> tmpData = WordUtil.readWordDocument(file);
        if (tmpData.isEmpty()) {
            pageDTO.setPage(0);
            pageDTO.setPageSize(0);
            return pageDTO;
        }

        //合并发言
//        List<List<String>> playFileContent = new ArrayList<>();
//        int index = 0;
//        for (String item : tmpData) {
//            List<String> tmp;
//            if (item.startsWith(WordUtil.spokesman_regex)) {
//                tmp = new ArrayList<>();
//                tmp.add(item);
//                playFileContent.add(index++, tmp);
//            } else {
//                if (playFileContent.isEmpty()) {
//                    tmp = new ArrayList<>();
//                    tmp.add(item);
//                    playFileContent.add(index++, tmp);
//                    continue;
//                }
//                //上一个发言人的数据
//                index--;
//                tmp = playFileContent.get(index);
//                tmp.add(item);
//                playFileContent.set(index++, tmp);
//            }
//        }

        String fileId = Ids.getId();
        List<PlayFile> playFileList = new ArrayList<>();
        long time = System.currentTimeMillis();
        try {
            int no = 0;
            for (String item : tmpData) {
                item = item.trim();
                if (item.isEmpty()) {
                    continue;
                }

                // todo 判断是否另一个发言人
                if (item.startsWith(WordUtil.spokesman_regex)) {
                    no++;
                }

                PlayFile playFile = new PlayFile();
                playFile.setFileId(fileId);
                playFile.setContent(item);
                playFile.setContentNo(no);
                playFile.setType(item.startsWith(WordUtil.img_pre) ? 2 : 1);
                playFileList.add(playFile);
            }

            playFileService.saveBatch(playFileList);
        } catch (Exception e) {
            log.error("analysisPlayWord");
        }

        pageDTO.setSaveFileTime(System.currentTimeMillis() - time);

        //分页计算, 每页小于1m
        int page = (int) ((file.getSize() / 1024 / 512) + 1);
        int size = tmpData.size() / page;

        pageDTO.setFileId(fileId);
        pageDTO.setStartIndex(0);
        pageDTO.setPage(page);
        pageDTO.setPageSize(size);
        return pageDTO;
    }

    private void saveAnalysisPlayFile(String id, List<List<String>> playFileContent) {
//        try {
//            int no = 0;
//            for (List<String> items : playFileContent) {
//                for (String item : items) {
//                    item = item.trim();
//                    if (item.isEmpty()) {
//                        continue;
//                    }
//                    Boolean r = redisTemplate.opsForZSet().add(PLAY_FILE_CONTENT + id, item, no);
//                    if (r == false) {
//                        System.out.println(r);
//                    }
//                }
//                no++;
//            }
//            redisTemplate.multi();
//            Object obj = redisTemplate.execute(new SessionCallback() {
//                @Override
//                public Object execute(RedisOperations redisOperations) throws DataAccessException {
//                    redisOperations.multi();
//
//                    int no = 0;
//                    for (List<String> items : playFileContent) {
//                        for (String item : items) {
//                            redisTemplate.opsForZSet().add(PLAY_FILE_CONTENT + id, item, no);
//                        }
//                        no++;
//                    }
//
//                    return redisOperations.exec();
//                }
//            });
//            System.out.println(obj);
//        } catch (Exception e) {
//            log.error("analysisPlayWord:{}", e.getMessage());
//        }
    }

    public AnalysisPlayFileVO playWordContentList(WordPageDTO pageDTO) {
        AnalysisPlayFileVO ret = new AnalysisPlayFileVO();

        ret.setFileId(pageDTO.getFileId());
        ret.setPageSize(pageDTO.getPageSize());
        ret.setStartIndex(-1);
        if (StringUtils.isEmpty(pageDTO.getFileId())) {
            return ret;
        }

        List<AnalysisPlayFileVO.ContentInfo> contentInfoList = new ArrayList<>();

//        long start = (long) (pageDTO.getPage() - 1) * pageDTO.getPageSize();
//        long end = (long) pageDTO.getPage() * pageDTO.getPageSize() - 1;
//        Set<ZSetOperations.TypedTuple<Object>> stringSet = redisTemplate.opsForZSet().rangeWithScores(PLAY_FILE_CONTENT + pageDTO.getFileId(), start, end);
//        if (null == stringSet) {
//            ret.setContentInfoList(new ArrayList<>());
//            return ret;
//        }
//
//
//        for (ZSetOperations.TypedTuple<Object> item : stringSet) {
//            AnalysisPlayFileVO.ContentInfo contentInfo = new AnalysisPlayFileVO.ContentInfo();
//            contentInfo.setNo(Objects.requireNonNull(item.getScore()).intValue());
//            contentInfo.setContent(Objects.requireNonNull(item.getValue()).toString());
//            contentInfoList.add(contentInfo);
//        }

        List<PlayFile> playFileList = playFileMapper.selectList(new QueryWrapper<PlayFile>().lambda()
                .eq(PlayFile::getFileId, pageDTO.getFileId())
                .gt(PlayFile::getId, pageDTO.getStartIndex())
                .last(" limit " + pageDTO.getPageSize()));
        if (null == playFileList || playFileList.isEmpty()) {
            return ret;
        }

        ret.setStartIndex(playFileList.get(playFileList.size()-1).getId());

        int imgNum = 0;
        for (PlayFile playFile : playFileList) {
            AnalysisPlayFileVO.ContentInfo contentInfo = new AnalysisPlayFileVO.ContentInfo();
            contentInfo.setContent(playFile.getContent());
            contentInfo.setNo(playFile.getContentNo());
            contentInfoList.add(contentInfo);
            if (playFile.getType() == 2) {
                imgNum++;
                //图片数据太大, 限制返回
                if (imgNum > 2) {
                    ret.setStartIndex(playFile.getId());
                    break;
                }
            }
        }

        ret.setContentInfoList(contentInfoList);
        return ret;
    }
}
