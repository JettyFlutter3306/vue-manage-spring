package cn.element.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置Swagger的Docket的Bean实例
     */
    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        Contact contact = new Contact("Element", "https://gitee.com/Lobidas_Daddy", "1529146935@qq.com");

        return new ApiInfo(
                "Element's Swagger",
                "Technique life never ends!",
                "v1.0",
                "https://gitee.com/Lobidas_Daddy",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                    new ArrayList<>());
    }






}
