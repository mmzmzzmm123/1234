package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysVideoComment;

import java.util.List;

public interface SysVideoCommentMapper {
    List<SysVideoComment> selectVideoCommentByTopicId(SysVideoComment videoComment);

    int insertVideoComment(SysVideoComment videoComment);

    int insertVideoCommentReply(SysVideoComment videoComment);

    int deleteVideoCommentById(String id);

    int deleteVideoCommentReplyById(String id);
}
