package uz.pdp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import uz.pdp.dao.CourseDao;
import uz.pdp.dao.FileDownloadDao;
import uz.pdp.dao.LoginDao;
import uz.pdp.dao.UserDao;
import uz.pdp.service.CourseService;
import uz.pdp.service.UserService;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "uz.pdp")
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void
    addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

    }
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    @Bean
    public CourseDao courseDao(){
        return  new CourseDao();
    }

    @Bean
    public LoginDao loginDao(){return new LoginDao();}
    @Bean
    public FileDownloadDao fileDownloadDao(){
        return  new FileDownloadDao();
    }

    @Bean
    public CourseService courseService(){
        return  new CourseService();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/learning_platform");
        dataSource.setUsername("postgres");

        dataSource.setPassword("akbarali");


        return new JdbcTemplate(dataSource);
    }

    @Bean
    public UserDao userDao(){
        return new UserDao();
    }

    @Bean
    public UserService userService(){
        return new UserService();
    }

    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver();

        return cmr;
    }
}
