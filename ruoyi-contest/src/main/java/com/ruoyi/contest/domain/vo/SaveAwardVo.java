package com.ruoyi.contest.domain.vo;

import com.ruoyi.contest.domain.AwardPerson;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lsyonlygoddes
 * @time 2023/02/16 12:57
 */
@Data
public class SaveAwardVo {
    private Long contestId;

    private Long subContestId;

    private Date awardDate;

    private String awardGrade;

    private String attachmentUrl;

    private String remark;

    private List<AwardPerson> guideTeacherList;

    private List<AwardPerson> contestStuList;

}
