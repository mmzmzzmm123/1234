package com.ruoyi.system.service.file;


import com.ruoyi.system.domain.BackChunk;
import com.ruoyi.system.domain.BackFilelist;
import com.ruoyi.system.domain.vo.CheckChunkVO;

import javax.servlet.http.HttpServletResponse;

public interface IBackFileService {

    int postFileUpload(BackChunk chunk, HttpServletResponse response);

    CheckChunkVO getFileUpload(BackChunk chunk, HttpServletResponse response);

    int deleteBackFileByIds(Long id);

    int mergeFile(BackFilelist fileInfo);
}
