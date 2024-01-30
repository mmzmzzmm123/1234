package com.ruoyi.system.components;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.ScreeningTaskBatch;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.extend.UtTouchJoinRoomClient;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.*;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskDetailOutput.ChatRoomJoinList;
import com.ruoyi.system.mapper.TaskMapper;
import com.ruoyi.system.service.ScreeningTaskBatchService;
import com.ruoyi.system.service.ScreeningTaskService;
import com.ruoyi.system.service.ScreeningTaskTargetService;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class TaskQuery {

    static Map<Integer, TaskQuery> INSTANCE_CACHE = new ConcurrentHashMap<>();

    static {
        INSTANCE_CACHE.put(0, new PullInGroupTaskQuery());
        INSTANCE_CACHE.put(2, new ScreeningTaskQuery());
    }

    public static TaskQuery newQuery(int orderType) {
        return INSTANCE_CACHE.get(orderType);
    }

    /**
     * 获取 任务 详情成功的 个数
     *
     * @param taskId
     * @return
     */
    public abstract int getSuccessCountOfTaskDetail(String taskId);

    /**
     * 通过 订单id查询 任务
     *
     * @param orderIds
     * @return
     */
    public abstract List<TaskAdapter> listByOrder(List<String> orderIds);

    /**
     * -1待执行 0-进行中 1-已完成 2-已取消
     *
     * @param taskId
     * @return
     */
    public abstract int getStatus(String taskId);

    @Slf4j
    public static class PullInGroupTaskQuery extends TaskQuery {
        @Override
        public int getStatus(String taskId) {
            GetChatRoomJoinTaskPageInput input = new GetChatRoomJoinTaskPageInput();
            input.setId(taskId);
            input.setName("");
            UtTouchResult<UtTouchPage<GetChatRoomJoinTaskPageOutput>> taskRet = UtTouchJoinRoomClient
                    .getChatRoomJoinTaskPage(input);
            log.info("UtTouchJoinRoomClient.getChatRoomJoinTaskPage {} {}", input, taskRet);
            if (taskRet == null || taskRet.getData() == null
                    || CollectionUtils.isEmpty(taskRet.getData().getDataList())) {
                throw new TaskQueryCallException("返回数据为空， taskId:" + taskId);
            }
            GetChatRoomJoinTaskPageOutput output = taskRet.getData().getDataList().get(0);
            // 0待执行、1执行中、2执行完成,-1已取消、-2已暂停
            int source = output.getExecuteStatus();
            if (source == 1) {
                return 0;
            }
            if (source == 2) {
                return 1;
            }
            if (source == 0) {
                return -1;
            }
            if (source == -1) {
                return 2;
            }
            return 0;
        }

        @Override
        public List<TaskAdapter> listByOrder(List<String> orderIds) {
            if (CollectionUtils.isEmpty(orderIds)) {
                return Collections.emptyList();
            }
            final TaskMapper taskMapper = SpringUtils.getBean(TaskMapper.class);
            List<Task> datas = taskMapper.selectList(new QueryWrapper<Task>().lambda().in(Task::getOrderId, orderIds));
            final List<TaskAdapter> adapters = new ArrayList<TaskQuery.TaskAdapter>();
            for (Task task : datas) {
                adapters.add(new TaskAdapter(task.getTaskId(), task.getTaskName(), task.getOrderId()));
            }
            return adapters;
        }

        @Override
        public int getSuccessCountOfTaskDetail(String taskId) {
            GetChatRoomJoinTaskDetailInput input = new GetChatRoomJoinTaskDetailInput();
            input.setTaskId(Long.parseLong(taskId));
            UtTouchResult<GetChatRoomJoinTaskDetailOutput> details = UtTouchJoinRoomClient
                    .getChatRoomJoinTaskDetail(input);
            log.info("getChatRoomJoinTaskDetail {} {}", input, details);
            int successTotal = 0;
            if (details.getData() != null
                    && !CollectionUtils.isEmpty(details.getData().getChatRoomJoinAllList())) {
                for (ChatRoomJoinList chatRoomJoinList : details.getData().getChatRoomJoinAllList()) {
                    // 进群成功数
                    successTotal += chatRoomJoinList.getJoinSuccessCount();
                }
            }
            return successTotal;
        }
    }

    public static class ScreeningTaskQuery extends TaskQuery {

        @Override
        public int getSuccessCountOfTaskDetail(String taskId) {
            return (int) SpringUtils.getBean(ScreeningTaskTargetService.class).countCheck(taskId);
        }

        @Override
        public List<TaskAdapter> listByOrder(List<String> orderIds) {
            return SpringUtils.getBean(ScreeningTaskService.class).getByOrderIds(orderIds)
                    .stream().map(task -> new TaskAdapter(task.getTaskId(), task.getTaskName(), task.getOrderId())).collect(Collectors.toList());
        }

        @Override
        public int getStatus(String taskId) {
            ScreeningTaskBatch screeningTaskBatch = SpringUtils.getBean(ScreeningTaskBatchService.class).getById(taskId);

            if (screeningTaskBatch == null) {
                return -99;
            }
            return ObjectUtil.equal(screeningTaskBatch.getBatchState(), 3) ? 2 :
                    ObjectUtil.equal(screeningTaskBatch.getBatchState(), 4) ? 1 :
                            ObjectUtil.equal(screeningTaskBatch.getBatchState(), 0) ? -1 :
                                    0;
        }
    }


    @SuppressWarnings("serial")
    public static class TaskQueryCallException extends RuntimeException {

        public TaskQueryCallException(String msg) {
            super(msg);
        }

        public TaskQueryCallException(String msg, Throwable e) {
            super(msg, e);
        }
    }

    @Data
    @Accessors(chain = true)
    public static class TaskAdapter {
        private final String taskId;
        private final String taskName;
        private final String orderId;
    }

}
