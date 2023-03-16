package cn.oc.service;


import cn.oc.domain.Sms;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SmsService extends IService<Sms>{


    /**
     * 发送一个短信
     * @param sms
     * 短信内容
     * @return
     * 是否发送成功
     */
    boolean sendSms(Sms sms);

    SendSmsResponse sendSmsRequest(SendSmsRequest request);
}
