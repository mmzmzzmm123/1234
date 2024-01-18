package com.ruoyi.web.controller.bk;


import com.onethinker.bk.service.ICommentService;
import com.onethinker.bk.vo.BaseRequestVO;
import com.onethinker.bk.vo.CommentVO;
import com.ruoyi.common.core.domain.AjaxResult;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 文章评论表 前端控制器
 * </p>
 *
 * @author sara
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/bk/comment")
public class CommentController {


    @Autowired
    private ICommentService commentService;


    /**
     * 保存评论
     */
    @PostMapping("/saveComment")
    public AjaxResult saveComment(@Validated @RequestBody CommentVO commentVO) {
        commentService.insertComment(commentVO);
        return AjaxResult.success();
    }


    /**
     * 删除评论
     */
    @GetMapping("/deleteComment")
    public AjaxResult deleteComment(@RequestParam("id") Long id) {
        commentService.deleteCommentById(id);
        return AjaxResult.success();
    }


    /**
     * 查询评论数量
     */
    @GetMapping("/getCommentCount")
    public AjaxResult getCommentCount(@RequestParam("source") Long source, @RequestParam("type") String type) {
        return AjaxResult.success(commentService.getCommentCount(source, type));
    }


    /**
     * 查询评论
     */
    @PostMapping("/listComment")
    public AjaxResult listComment(@RequestBody BaseRequestVO baseRequestVO) {
        BaseRequestVO requestVO =  commentService.listComment(baseRequestVO);
        return AjaxResult.success(requestVO);
    }
}

