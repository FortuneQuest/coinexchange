package cn.oc.controller;

import cn.oc.domain.WebConfig;
import cn.oc.model.R;
import cn.oc.service.WebConfigService;
import cn.oc.utils.ArraysUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : WebConfigController
 * @Author: oc
 * @Date: 2022/11/28/17:14
 * @Description:
 **/
@RestController
@Api(tags = "webConfig的控制器")
@RequestMapping("/webConfigs")
public class WebConfigController {

    @Autowired
    private WebConfigService webConfigService;

    @GetMapping
    @ApiOperation("分页查询webConfig")
    @PreAuthorize("hasAuthority('web_config_query')")
    public R<Page<WebConfig>> findByPage(@ApiIgnore Page page , String name , String type) {
       return R.ok(webConfigService.findByPage(page,name,type));
    }

    @PostMapping
    @ApiOperation("新增一个webConfig")
    @PreAuthorize("hasAuthority('web_config_create')")
    public R add(@RequestBody @Validated WebConfig webConfig) {
        boolean save = webConfigService.save(webConfig);
        if (save) {
            return R.ok("新增成功");
        } else {
            return  R.fail("新增失败");
        }
    }

    @PatchMapping
    @ApiOperation("修改公告")
    @PreAuthorize("hasAuthority('web_config_update')")
    public R update(@RequestBody WebConfig webConfig) {
        boolean update = webConfigService.updateById(webConfig);
        if (update) {
            return R.ok("修改成功");
        } else {
            return  R.fail("修改失败");
        }
    }

    @PostMapping("/delete")
    @ApiOperation("删除公告")
    @PreAuthorize("hasAuthority('web_config_delete')")
    public R delete(@RequestBody Long[] ids ) {
        if (ArraysUtil.ArraysIsEmpty(ids)) {
            return R.fail("删除时需要给予对应的id");
        }
        boolean remove = webConfigService.removeByIds(Arrays.asList(ids));
        if (remove) {
            return R.ok("删除成功");
        } else {
            return  R.fail("删除失败");
        }
    }
}
