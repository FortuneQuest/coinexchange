package cn.oc.controller;

import cn.oc.domain.Notice;
import cn.oc.model.R;
import cn.oc.service.NoticeService;
import cn.oc.utils.ArraysUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : NoticeController
 * @Author: oc
 * @Date: 2022/11/28/12:55
 * @Description:
 **/
@RestController
@RequestMapping("/notices")
@Api(tags = "公告管理")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询公告")
    @GetMapping
    @PreAuthorize("hasAuthority('notice_query')")
    public R<Page<Notice>> findByPage(@ApiIgnore Page<Notice> page,String tile ,String startTime,
                                      String endTime,Integer status) {
        page.addOrder(OrderItem.desc("last_update_time"));
        return  R.ok(noticeService.findByPage(page,tile,startTime,endTime,status));
    }


    @ApiOperation("删除公告")
    @PreAuthorize("hasAuthority('notice_delete')")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        if(ArraysUtil.ArraysIsEmpty(ids)){
            return R.fail("删除时，需要给予id的值");
        }
        boolean removeByIds = noticeService.removeByIds(Arrays.asList(ids));
        if (removeByIds) {
            return R.ok("删除成功");
        } else {
            return  R.fail("删除失败");
        }
    }

    @PostMapping("/updateStatus")
    @ApiOperation("启用或禁用公告")
    @PreAuthorize("hasAuthority('notice_update')")
    public R updateStatus(Long id,Integer status) {
        Notice notice = new Notice();
        notice.setStatus(status);
        notice.setId(id);
        boolean updateById = noticeService.updateById(notice);
        if (updateById) {
            return R.ok("更新成功");
        } else {
            return  R.fail("更新失败");
        }
    }

    @PostMapping
    @ApiOperation("新增一个公告")
    @PreAuthorize("hasAuthority('notice_create')")
    public R add(@RequestBody @Validated  Notice notice) {
        notice.setStatus(1);
        boolean save = noticeService.save(notice);
        if (save) {
            return R.ok("新增公告成功");
        } else {
            return  R.fail("新增公告失败");
        }
    }

    @PatchMapping
    @ApiOperation("修改一个公告")
    @PreAuthorize("hasAuthority('notice_update')")
    public R update(@RequestBody Notice notice) {
        boolean update = noticeService.updateById(notice);
        if (update) {
            return R.ok("修改公告成功");
        } else {
            return  R.fail("修改公告失败");
        }
    }
}
