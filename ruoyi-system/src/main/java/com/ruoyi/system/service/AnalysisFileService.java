package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.dto.play.WordPageDTO;
import com.ruoyi.common.core.domain.entity.play.PlayFile;
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
    /**
     * 发言人标识前缀
     */
    public static final String SPOKESMAN_REGEX = "${";

    @Autowired
    private PlayFileService playFileService;

    @Autowired
    private PlayFileMapper playFileMapper;

    /**
     * 解析剧本文件,返回id
     */
    public WordPageDTO analysisPlayWord(MultipartFile file, Long userId) {
        WordPageDTO pageDTO = new WordPageDTO();
        List<String> tmpData = WordUtil.readWordDocument(file);
        if (tmpData.isEmpty()) {
            pageDTO.setPage(0);
            pageDTO.setPageSize(0);
            return pageDTO;
        }

        //一个用户只能上传一份文件
        playFileMapper.delete(new LambdaQueryWrapper<PlayFile>().eq(PlayFile::getFileId, userId));

        String fileId = userId.toString();
        List<PlayFile> playFileList = new ArrayList<>();
        long time = System.currentTimeMillis();
        int no = 0;
        for (String item : tmpData) {
            item = item.trim();
            if (item.isEmpty()) {
                continue;
            }

            //判断是否另一个发言人
            if (item.startsWith(SPOKESMAN_REGEX)) {
                no++;
            }

            PlayFile playFile = new PlayFile();
            playFile.setFileId(fileId);
            playFile.setContent(item);
            playFile.setContentNo(no);
            playFile.setType(item.startsWith(WordUtil.IMG_PRE) ? 2 : 1);
            playFileList.add(playFile);
        }
        playFileService.saveBatch(playFileList);

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

    public AnalysisPlayFileVO playWordContentList(WordPageDTO pageDTO) {
        AnalysisPlayFileVO ret = new AnalysisPlayFileVO();

        ret.setFileId(pageDTO.getFileId());
        ret.setPageSize(pageDTO.getPageSize());
        ret.setStartIndex(-1);
        if (StringUtils.isEmpty(pageDTO.getFileId())) {
            return ret;
        }

        List<PlayFile> playFileList = playFileMapper.selectList(new QueryWrapper<PlayFile>().lambda()
                .eq(PlayFile::getFileId, pageDTO.getFileId())
                .gt(PlayFile::getId, pageDTO.getStartIndex())
                .last(" limit " + pageDTO.getPageSize()));
        if (null == playFileList || playFileList.isEmpty()) {
            return ret;
        }

        ret.setStartIndex(playFileList.get(playFileList.size()-1).getId());

        List<AnalysisPlayFileVO.ContentInfo> contentInfoList = new ArrayList<>();
        int imgNum = 0;
        for (PlayFile playFile : playFileList) {
            AnalysisPlayFileVO.ContentInfo contentInfo = new AnalysisPlayFileVO.ContentInfo();
            contentInfo.setContent(playFile.getContent());
            contentInfo.setNo(playFile.getContentNo());
            contentInfoList.add(contentInfo);
            if (playFile.getType() == 2) {
                imgNum++;
                //图片格式数据太大, 限制返回数据
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
