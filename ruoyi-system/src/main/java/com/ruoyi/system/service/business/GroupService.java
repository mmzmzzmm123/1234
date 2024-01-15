package com.ruoyi.system.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.GroupPageQueryDTO;
import com.ruoyi.system.domain.dto.GroupQueryDTO;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.GroupPageInfoVO;
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
    public R<List<GroupInfoVO>> queryGroup(GroupQueryDTO dto) {
        return R.ok();
    }

    /**
     * 取消标记群已使用
     *
     * @param groupIds 群id集合
     */
    public void cancelGroup(List<String> groupIds) {

    }


}
