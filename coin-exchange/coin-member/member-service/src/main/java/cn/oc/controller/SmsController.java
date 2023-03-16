package cn.oc.controller;

import cn.oc.domain.Sms;
import cn.oc.model.R;
import cn.oc.service.SmsService;
import cn.oc.service.impl.SmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SmsController
 * @Author: oc
 * @Date: 2023/03/16/19:05
 * @Description:
 **/
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsServiceImpl smsService;


    @PostMapping("/senTO")
    public R sendSms (@RequestBody @Validated Sms sms){
        boolean  isOk = smsService.sendSms(sms);
        if (isOk) {
            return  R.ok();
        }
        return  R.fail("发送失败");
    }
}
