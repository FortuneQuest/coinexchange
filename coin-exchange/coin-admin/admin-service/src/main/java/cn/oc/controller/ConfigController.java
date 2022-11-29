package cn.oc.controller;

import cn.oc.domain.Config;
import cn.oc.model.R;
import cn.oc.service.ConfigService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : ConfigController
 * @Author: oc
 * @Date: 2022/11/29/16:42
 * @Description:
 **/
@RestController
@RequestMapping("configs")
@Api(tags = "后台参数配置")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @ApiOperation("分页查询参数")
    @GetMapping
    @PreAuthorize("hasAuthority('config_query')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size",value = "分页的大小",paramType = "query"),
            @ApiImplicitParam(name = "current",value = "当前页",paramType = "query")

    })
    @ApiParam()
    public R<Page<Config>> findByPage(@ApiIgnore Page<Config> page, String type,
                                      String name, String code) {
        return  R.ok(configService.findByPage(page,type,code,name));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('config_create')")
    @ApiOperation("新增一个参数")
    public R add(@RequestBody @Validated Config config) {
        boolean save = configService.save(config);
        if (save) {
            return R.ok("新增成功");
        } else {
            return  R.fail("新增失败");
        }
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('config_update')")
    @ApiOperation("修改参数")
    public R update(@RequestBody Config config) {
        boolean update = configService.updateById(config);
        if (update) {
            return R.ok("修改成功");
        } else {
            return  R.fail("修改失败");
        }
    }
}
