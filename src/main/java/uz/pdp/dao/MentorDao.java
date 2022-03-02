package uz.pdp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;



public class MentorDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
}
