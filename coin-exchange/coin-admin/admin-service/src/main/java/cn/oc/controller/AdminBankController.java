package cn.oc.controller;

import cn.oc.domain.AdminBank;
import cn.oc.model.R;
import cn.oc.service.AdminBankService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : AdminBankController
 * @Author: oc
 * @Date: 2022/11/29/15:36
 * @Description:
 **/
@RequestMapping("/adminBanks")
@RestController
@Api(tags = "公司银行卡的配置")
public class AdminBankController {

    @Autowired
    private AdminBankService adminBankService;

    @GetMapping
    @ApiOperation("条件查询公司银行卡")
    @PreAuthorize("hasAuthority('admin_bank_query')")
    public R<Page<AdminBank>> findByPage(@ApiIgnore Page<AdminBank> page, String bankCard) {
        return R.ok(adminBankService.findByPage(page,bankCard));
    }

    @PostMapping
    @ApiOperation("新增银行卡")
    @PreAuthorize("hasAuthority('admin_bank_create')")
    public R addAdminBank (@RequestBody  @Validated AdminBank adminBank) {
        boolean save = adminBankService.save(adminBank);
        if (save) {
            return R.ok("添加银行卡成功");
        } else {
            return R.fail("添加失败");
        }
    }

    @PatchMapping
    @ApiOperation("修改银行卡")
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R update(@RequestBody AdminBank adminBank) {
        boolean update = adminBankService.updateById(adminBank);
        if (update) {
            return R.ok("修改成功");
        } else {
            return  R.fail("修改失败");
        }
    }

    @PostMapping("/adminUpdateBankStatus")
    @ApiOperation("更改银行卡状态")
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R changeStatus (Long bankId,Byte status) {
        AdminBank adminBank = new AdminBank();
        adminBank.setId(bankId);
        adminBank.setStatus(status);
        boolean update  = adminBankService.updateById(adminBank);
        if (update) {
            return R.ok("修改状态成功");
        } else {
            return  R.fail("状态修改失败");
        }
    }

}
