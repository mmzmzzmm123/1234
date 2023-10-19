package com.xinyu.idol.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * pak资源path和guid的映射表
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */
@TableName("t_pak_path_id_map")
@ApiModel(value = "PakPathIdMapEntity对象", description = "pak资源path和guid的映射表")
public class PakPathIdMapEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("pak资源的路径")
    private String path;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PakPathIdMapEntity{" +
            "id = " + id +
            ", path = " + path +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
