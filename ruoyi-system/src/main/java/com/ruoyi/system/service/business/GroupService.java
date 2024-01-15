package com.ruoyi.system.service.business;

import com.ruoyi.system.domain.dto.GroupQueryDTO;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/15/015 15:47
 */
@Service
@AllArgsConstructor
public class GroupService {

    /**
     * 选群
     *
     * @param dto 选群条件
     * @return 满足条件群
     */
    public List<GroupInfoVO> queryGroup(GroupQueryDTO dto) {
        return new ArrayList<>();
    }

    /**
     * 取消标记群已使用
     *
     * @param groupIds 群id集合
     */
    public void cancelGroup(List<String> groupIds) {

    }

}
