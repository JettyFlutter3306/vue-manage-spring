package cn.apple.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("cn.apple.mapper")
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){

        return new PaginationInterceptor();
    }

    //SQL执行效率插件
//    @Bean
//    @Profile({"dev","test"}) //设置dev test环境开启,保证我们的效率
//    public PerformanceInterceptor performanceInterceptor(){
//
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//
//        performanceInterceptor.setMaxTime(100); //ms 设置SQL最大执行时间
//        performanceInterceptor.setFormat(true);
//
//        return performanceInterceptor;
//    }


}
