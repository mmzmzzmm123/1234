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
    /**
     *  {
     *     "contestId": 1,
     *     "subContestId": 1,
     *     "name": "2023年 中国“互联网+”大学生创新创业大赛-校赛",
     *     "contest": "",
     *     "awardDate": "2023-02-16",
     *     "awardGrade": "1",
     *     "attachmentUrl": "/profile/upload/2023/02/16/个人受理证明-代仕斌_20230216124955A001.pdf",
     *     "remark": "备注1",
     *     "guideTeacherList": [
     *         {
     *             "teacherId": 1,
     *             "name": "张三",
     *             "orderNum": 0,
     *             "conDegree": 100,
     *             "workContent": "",
     *             "gender": "0",
     *             "professional": "0",
     *             "post": "0",
     *             "research": "大数据"
     *         }
     *     ],
     *     "contestStuList": [
     *         {
     *             "id": 17116,
     *             "name": "史启蒙",
     *             "orderNum": 0,
     *             "conDegree": 100,
     *             "workContent": ""
     *         },
     *         {
     *             "id": 369,
     *             "name": "周友平",
     *             "orderNum": 1,
     *             "conDegree": 100,
     *             "workContent": ""
     *         }
     *     ]
     * }
     */
    private Long contestId;

    private Long subContestId;

    private Date awardDate;

    private String awardGrade;

    private String attachmentUrl;

    private String remark;

    private List<AwardPerson> guideTeacherList;

    private List<AwardPerson> contestStuList;

}
