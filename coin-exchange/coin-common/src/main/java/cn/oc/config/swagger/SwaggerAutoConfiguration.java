package cn.oc.config.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : SwaggerAutoConfiguration
 * @Author: oc
 * @Date: 2022/11/09/14:39
 * @Description:
 **/
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)  //spring读取
public class SwaggerAutoConfiguration {


        private SwaggerProperties swaggerProperties;
            //构造器注入
        public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties){
            this.swaggerProperties =swaggerProperties;
        }

    /**
     * 构建器
     * @return
     */
    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                //介绍接口信息
                .apiInfo(apiInfo())
                //选择生成器
                .select()
                //选择为哪些包哪些注解生成api文档
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                //对apis选中的哪些路径生成
                .paths(PathSelectors.any())
                .build();

        //安全的配置
        docket
                //安全的规则
                .securitySchemes(securitySchemes())
                //安全配置的上下文  内容
                .securityContexts(securityContexts());
        return docket;
    }


    /**
     * 安全规则的配置
     * @return
     */
    private List<SecurityScheme> securitySchemes() {
        return Arrays.asList(new ApiKey("Authorization","Authorization","Header"));
    }


    /**
     * 安全的上下文
     * @return
     */
    private List<SecurityContext> securityContexts() {
        return Arrays.asList(new SecurityContext(
                Arrays.asList(new SecurityReference("Authorization",
                        new AuthorizationScope[]{new AuthorizationScope("global", "accessResource")})),
                PathSelectors.any())
        );
    }


    /**
     * 对api信息的简介
     * @return
     */
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                //更新此 API 负责人的联系信息
                .contact(new Contact(swaggerProperties.getName(),swaggerProperties.getUrl(),swaggerProperties.getEmail()))
                //标题
                .title(swaggerProperties.getTitle())
                //描述信息
                .description(swaggerProperties.getDescription())
                //版本号
                .version(swaggerProperties.getVersion())
                //团队服务地址
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .build();
    }
}
