package cn.element.config;

import cn.element.handler.CustomizedAuthFilter;
import cn.element.handler.JwtAuthenticationEntryPoint;
import cn.element.handler.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler successHandler;//成功登录的处理器

    @Autowired
    private AuthenticationFailureHandler failureHandler;//登录失败的处理器

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;//权限不足处理器

    @Autowired
    private DataSource dataSource;  //数据源

    @Autowired
    private UserDetailsService userDetailsService;  //自定义的用户细节service

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //白名单
    public static final String[] URL_WHITE_LIST = {"/","/userLogin","logout"};

    @Bean
    public JdbcTokenRepositoryImpl getJdbcTokenRepositoryImpl(){

        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        return jdbcTokenRepository;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception{

        return new JwtAuthenticationFilter(this.authenticationManager());
    }

    /**
     * anonymous 仅允许匿名用户访问,如果登录了访问 反而没权限
     * permitAll 登录能访问,不登录也能访问,一般用于静态资源js等
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(URL_WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilter(this.jwtAuthenticationFilter());

        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/userLogin")
                .successHandler(successHandler)
                .failureHandler(failureHandler);

        http.logout().logoutSuccessUrl("/");

        http.csrf().disable();
        http.cors();

        http.exceptionHandling()            //自定义权限不足处理器
                .accessDeniedHandler(accessDeniedHandler);

        http.addFilterAt(customizedAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
        //从内存中读取
//        auth.inMemoryAuthentication()
//                .passwordEncoder(encoder)
//                .withUser("洛必达").password("{noop}123456")
//                .roles("管理员").authorities("user:select");

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Bean
    protected CustomizedAuthFilter customizedAuthFilter() throws Exception {

        CustomizedAuthFilter filter = new CustomizedAuthFilter();

        filter.setAuthenticationManager(authenticationManagerBean());

        return filter;
    }

}
