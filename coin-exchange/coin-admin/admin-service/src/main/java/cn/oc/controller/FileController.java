package cn.oc.controller;

import cn.hutool.core.date.DateUtil;
import cn.oc.model.R;
import com.aliyun.oss.OSS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : FileController
 * @Author: oc
 * @Date: 2022/11/27/21:01
 * @Description:  完成文件上传的功能
 **/
@RestController
@Api(tags = "文件上传控制器")
public class FileController {

    @Autowired
    /**
     * spring-cloud-alibaba会自动注入该对象
     */
    private OSS ossClient;

    @Value("${oss.bucket.name:coin-oc}")
    private String bucketName;

    @Value("@{spring.cloud.alicloud.oss.endpoint}")
    private String endPoint;

    @ApiOperation("文件上传")
    @PostMapping("/image/AliYunImgUpload")
    public R<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        /**
         * bucketName
         * 文件名  日期+原始文件名称   http://xxx.com/路径  or uuid+文件名
         * 输入流
         */
        //生成的日期格式为 : yyyy-MM-dd---->转换yyyy-MM-dd
        String fileName = DateUtil.today().replace("-","/")+"/"+file.getOriginalFilename();
        ossClient.putObject(bucketName,fileName,file.getInputStream());
        return  R.ok("https://"+bucketName+"."+endPoint+"/"+fileName);
    }
}
