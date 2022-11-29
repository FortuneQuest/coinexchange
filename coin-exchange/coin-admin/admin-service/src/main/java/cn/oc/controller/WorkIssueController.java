package cn.oc.controller;

import cn.oc.domain.WorkIssue;
import cn.oc.model.R;
import cn.oc.service.WorkIssueService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : WorkIssueController
 * @Author: oc
 * @Date: 2022/11/28/17:56
 * @Description:
 **/
@RestController
@RequestMapping("/workIssues")
@Api("客服工单的处理的控制器")
public class WorkIssueController {

    @Autowired
    private WorkIssueService workIssueService;


    @GetMapping
    @ApiOperation("客服工单的分页查询")
    @PreAuthorize("hasAuthority('work_issue_query')")
    public R<Page<WorkIssue>> findByPage(@ApiIgnore Page<WorkIssue> page,Integer status
            ,String startTime,String endTime) {
        page.addOrder(OrderItem.desc("last_update_time"));
        return R.ok(workIssueService.findByPage(page,status,startTime,endTime));
    }

    @PatchMapping
    @ApiOperation("回复工单")
    @PreAuthorize("hasAuthority('work_issue_update')")
    public R Record(Long id,String answer) {
        WorkIssue workIssue = new WorkIssue();
        workIssue.setAnswer(answer);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
        String username = userDetails.getUsername();
        workIssue.setAnswerUserId(Long.valueOf(username));
        workIssue.setId(id);
        boolean updateById = workIssueService.updateById(workIssue);
        if (updateById) {
            return R.ok("回复成功");
        } else {
            return  R.fail("没有回复成功");
        }
    }


}
