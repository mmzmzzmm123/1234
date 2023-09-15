package com.ruoyi.office.service.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.WxLoginBody;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.domain.vo.MerchantUserStatisticsVo;
import com.ruoyi.office.domain.vo.MerchantUserVo;
import com.ruoyi.office.service.ITRoomOrderService;
import com.ruoyi.office.service.ITRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserMapper;
import com.ruoyi.office.domain.TWxUser;
import com.ruoyi.office.service.ITWxUserService;

import javax.annotation.Resource;

/**
 * 微信用户信息 t_wx_userService业务层处理
 *
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class TWxUserServiceImpl extends ServiceImpl<TWxUserMapper, TWxUser> implements ITWxUserService {
    @Autowired
    private TWxUserMapper tWxUserMapper;

    /**
     * 查询微信用户信息 t_wx_user
     *
     * @param id 微信用户信息 t_wx_user主键
     * @return 微信用户信息 t_wx_user
     */
    @Override
    public TWxUser selectTWxUserById(Long id) {
        return tWxUserMapper.selectTWxUserById(id);
    }

    /**
     * 查询微信用户信息 t_wx_user列表
     *
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 微信用户信息 t_wx_user
     */
    @Override
    public List<TWxUser> selectTWxUserList(TWxUser tWxUser) {
        return tWxUserMapper.selectTWxUserList(tWxUser);
    }

    /**
     * 新增微信用户信息 t_wx_user
     *
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 结果
     */
    @Override
    public int insertTWxUser(TWxUser tWxUser) {
        tWxUser.setCreateTime(DateUtils.getNowDate());
        return tWxUserMapper.insertTWxUser(tWxUser);
    }

    /**
     * 修改微信用户信息 t_wx_user
     *
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 结果
     */
    @Override
    public int updateTWxUser(TWxUser tWxUser) {
        tWxUser.setUpdateTime(DateUtils.getNowDate());
        return tWxUserMapper.updateTWxUser(tWxUser);
    }

    /**
     * 批量删除微信用户信息 t_wx_user
     *
     * @param ids 需要删除的微信用户信息 t_wx_user主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserByIds(Long[] ids) {
        return tWxUserMapper.deleteTWxUserByIds(ids);
    }

    /**
     * 删除微信用户信息 t_wx_user信息
     *
     * @param id 微信用户信息 t_wx_user主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserById(Long id) {
        return tWxUserMapper.deleteTWxUserById(id);
    }

    @Override
    public List<MerchantUserVo> listStoreWxuser(TWxUser tWxUser) {
        return baseMapper.listStoreWxuser(tWxUser);
    }

    @Override
    public void updateLogInfo(WxUser wxUser) {
        UpdateWrapper<TWxUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(TWxUser::getUnionId, wxUser.getUnionId());
        updateWrapper.lambda().set(TWxUser::getLoginIp, wxUser.getLoginIp()).set(TWxUser::getLoginDate, new Date())
                .set(TWxUser::getUpdateTime, new Date());
        baseMapper.update(null, updateWrapper);
    }

    @Autowired
    private ITRoomService tRoomService;
    @Autowired
    private ITRoomOrderService roomOrderService;

    @Override
    public List<MerchantUserStatisticsVo> statistics(Long userId, String staType) {
        TRoom tRoom = new TRoom();
        tRoom.setCreateBy(SecurityUtils.getUserId() + "");
        List<TRoom> list = tRoomService.selectTRoomList(tRoom);
        List<TRoomOrder> totalList = new ArrayList<>();
        for (TRoom room : list) {
            TRoomOrder roomOrder = new TRoomOrder();
            roomOrder.setRoomId(room.getId());
            if ("reg".equalsIgnoreCase(staType)) {
                totalList.addAll(roomOrderService.selectTRoomOrderList(roomOrder).stream().filter(x -> x.getStatus() == 5).sorted(Comparator.comparing(TRoomOrder::getCreateTime)).collect(Collectors.toList()));
            } else if ("act".equalsIgnoreCase(staType)) {
                totalList.addAll(roomOrderService.selectTRoomOrderList(roomOrder).stream().filter(x -> x.getStatus() == 5).sorted(Comparator.comparing(TRoomOrder::getCreateTime).reversed()).collect(Collectors.toList()));
            }else{
                totalList.addAll(roomOrderService.selectTRoomOrderList(roomOrder).stream().filter(x -> x.getStatus() == 5).collect(Collectors.toList()));
            }
        }

        Map<Long, TWxUser> wxUserList = tWxUserMapper.selectTWxUserList(new TWxUser()).stream().collect(Collectors.toMap(TWxUser::getId, Function.identity()));

        Map<Long, MerchantUserStatisticsVo> userStaMap = new HashMap<>();
        for (TRoomOrder order : totalList) {
            int hour = Math.abs((int) ((order.getEndTime().getTime() - order.getStartTime().getTime()) / (1000 * 3600)));
            if (userStaMap.containsKey(order.getUserId())) {
                MerchantUserStatisticsVo vo = userStaMap.get(order.getUserId());
                vo.setCount(vo.getCount() + 1);
                vo.setAmount(vo.getAmount().add(order.getPayAmount()));
                vo.setHours(vo.getHours() + hour);
                vo.setLastestDate(order.getCreateTime());
            } else {
                MerchantUserStatisticsVo vo = new MerchantUserStatisticsVo();
                vo.setCount(1);
                vo.setHours(hour);
                vo.setAmount(order.getPayAmount());
                vo.setLastestDate(order.getCreateTime());
                vo.setWxUser(wxUserList.get(order.getUserId()));
                userStaMap.put(order.getUserId(), vo);
            }
        }
        final List<MerchantUserStatisticsVo> targetList = new ArrayList<>(userStaMap.values());
        return targetList;
    }
}
