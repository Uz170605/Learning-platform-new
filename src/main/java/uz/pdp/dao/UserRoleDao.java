package uz.pdp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uz.pdp.config.WebMvcConfig;
import uz.pdp.dto.CourseDto;

import java.util.List;

public class UserRoleDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

//    public List<CourseDto> get(){
//
//    }
}
