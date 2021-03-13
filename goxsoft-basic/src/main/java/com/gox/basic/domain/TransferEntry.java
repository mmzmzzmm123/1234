package com.gox.basic.domain;
import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import com.gox.common.plugin.AutoId;

/**
 * 移交表 transfer_entry
 *
 * @author gox
 * @date 2021-03-05
 */
public class TransferEntry extends BaseEntity {


    public static final String STATUS_TRANSFOR = "待移交";
    public static final String STATUS_AUDIT = "待审核";
    public static final String STATUS_MOVE = "已入库";

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @AutoId
    private Long id;

    /**
     * 移交单据ID
     **/
    private Long docid;

    /**
     * 条目ID
     **/
    private Long entryid;

    /**
     * 移交状态
     */
    @Excel(name = "移交状态")
    private String status;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocid() {
        return docid;
    }

    public void setDocid(Long docid) {
        this.docid = docid;
    }

    public Long getEntryid() {
        return entryid;
    }

    public void setEntryid(Long entryid) {
        this.entryid = entryid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
