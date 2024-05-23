package com.onethinker.file.dto;

import com.onethinker.file.domain.FileInfo;
import lombok.Data;

import java.util.List;

/**
 * @author yangyouqi
 * @date 2024/5/19
 */
@Data
public class FileInfoDTO extends FileInfo {
    private String domain;

    private List<String> ids;
}
