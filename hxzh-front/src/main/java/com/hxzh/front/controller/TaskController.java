package com.hxzh.front.controller;



import com.hxzh.front.common.AjaxResult;
import com.hxzh.front.moudle.vo.TaskInfoVO;
import com.hxzh.front.security.TaskAuthService;
import com.hxzh.front.moudle.dto.TaskAuthDTO;
import com.hxzh.front.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 任务模块
 *
 * @author JasonYou
 * @date 2024/01/10
 */
@RestController
@RequestMapping("/api/front/task")
public class TaskController {

    @Autowired
    TaskAuthService taskAuthService;

    @Autowired
    TaskService taskService;

    @Value("${token.header}")
    private String header;

    /**
     * 任务验证
     *
     * @param taskAuthDTO 任务授权
     * @return 令牌token
     */
    @PostMapping("/auth")
    public AjaxResult auth(@RequestBody TaskAuthDTO taskAuthDTO){

        String token= taskAuthService.auth(taskAuthDTO);
        AjaxResult result= AjaxResult.success("验证成功",token);
        return result;
    }

    /**
     * 获取任务信息
     *
     * @return {@link AjaxResult}
     */
    @GetMapping("/getInfor")
    public AjaxResult getInfor(){
         TaskInfoVO taskInfoVO = taskService.getTaskInfo();
        return AjaxResult.success("获取成功",taskInfoVO);
    }

}
