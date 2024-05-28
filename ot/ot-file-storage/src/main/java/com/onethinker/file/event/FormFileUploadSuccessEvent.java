package com.onethinker.file.event;

import com.onethinker.file.domain.FileInfo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.nio.file.Path;

/**
 * @author yangyouqi
 */
@Getter
public class FormFileUploadSuccessEvent extends ApplicationEvent {
    private final String id;
    private final String userId;
    private final String mimeType;

    public FormFileUploadSuccessEvent(Path dataFile, String id, String userId, String mimeType) {
        super(dataFile);
        this.id = id;
        this.userId = userId;
        this.mimeType = mimeType;
    }

    public FormFileUploadSuccessEvent(FileInfo fileInfo, String id, String userId, String mimeType) {
        super(fileInfo);
        this.id = id;
        this.userId = userId;
        this.mimeType = mimeType;
    }
}
