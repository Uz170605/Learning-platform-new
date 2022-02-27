package uz.pdp.dao;

import com.google.gson.Gson;


import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.ModuleDto;
import uz.pdp.dto.UserDto;


import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CourseDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<CourseDto> getAllCourses(Integer interval, Integer currentPage, String search) { // TODO: 2/20/2022 add checked and pagabel
        String sqlQuery = "";
        if (search != null) {
            sqlQuery = "select * from get_all_courses_by_pageable_and_search('"+search+"', "+interval+", "+currentPage+")";
        } else if (interval == null && currentPage == null) {
            sqlQuery = "select *\n" +
                        "from get_course_by_user_and_module()";
        } else {
            sqlQuery = "select * from get_course_by_user_and_module(" + interval + ", " + currentPage + ")";
        }
            List<CourseDto> courseDtoListFromDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(UUID.fromString(rs.getString(1)));
            courseDto.setName(rs.getString(2));
            courseDto.setDescription(rs.getString(3));
            Array authors = rs.getArray(4);

            Type listType = new TypeToken<ArrayList<UserDto>>() {
            }.getType();
            List<UserDto> authorList = new Gson().fromJson(authors.toString(), listType);
            courseDto.setAuthors(authorList);
            if(search==null) {
                Array module = rs.getArray(5);
                Type type = new TypeToken<ArrayList<ModuleDto>>() {
                }.getType();
                List<ModuleDto> moduleDtoList = new Gson().fromJson(module.toString(), type);
                courseDto.setModule(moduleDtoList);
            }
            return courseDto;
        });
        return courseDtoListFromDb;
    }

    public String addCourse(CourseDto courseDto) {
        String sqlQuery = "Insert into courses(name,is_active,description) values('" + courseDto.getName() + "'," + courseDto.isActive() + ",'" + courseDto.getDescription() + "') returning id";
        String idStr = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> rs.getString("id"));
//        UUID uuid = UUID.fromString(Objects.requireNonNull(idStr));
//        int res = 0;
//        for (UUID uuid1 : courseDto.getAuthorsId()) {
//            res = jdbcTemplate.update("INSERT INTO authors_courses values ('" + uuid1 + "','" + uuid + "');");
//
//        }
//        return res;
        return idStr;
    }

    public int deleteCourse(UUID id) {
        String sqlQuery1 = "Delete from authors_courses where course_id='" + id + "'";
        int res = jdbcTemplate.update(sqlQuery1);
        String sqlQuery = "Delete from courses where id ='" + id + "'";
        int res1 = jdbcTemplate.update(sqlQuery);
        return res - res1;
    }

    public CourseDto getCourseById(UUID id) {
        String sqlQuery = "select * from get_course_by_user_and_module() where id='" + id + "'";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(UUID.fromString(rs.getString(1)));
            courseDto.setName(rs.getString(2));
            courseDto.setPrice(rs.getDouble(3));
            courseDto.setActive(rs.getBoolean(5));
            courseDto.setDescription(rs.getString(4));
            Array authors = rs.getArray(6);

            Type listType = new TypeToken<ArrayList<UserDto>>() {
            }.getType();
            List<UserDto> authorList = new Gson().fromJson(authors.toString(), listType);
            courseDto.setAuthors(authorList);
            Array module = rs.getArray(7);
            Type type = new TypeToken<ArrayList<ModuleDto>>() {
            }.getType();
            List<ModuleDto> moduleDtoList = new Gson().fromJson(module.toString(), type);
            courseDto.setModule(moduleDtoList);
            return courseDto;
        });
    }

    public int editCourse(CourseDto courseDto) {
        int res2 = 0;
        int res = 0;
        if (courseDto.getAuthorsId().length != 0) {
            String sqlQuery1 = "Delete from authors_courses where course_id='" + courseDto.getId() + "'";
            res = jdbcTemplate.update(sqlQuery1);
            for (UUID uuid : courseDto.getAuthorsId()) {
                res2 = jdbcTemplate.update("Insert INTO authors_courses values ('" + uuid + "','" + courseDto.getId() + "')");
            }
        }
        String sqlQuery = "Update courses Set name='" + courseDto.getName() + "', price=" + courseDto.getPrice() + ", is_active =" + courseDto.isActive() + ", description='" + courseDto.getDescription() + "', updated_at=now()  where id='" + courseDto.getId() + "'";
        int res1 = jdbcTemplate.update(sqlQuery);
        return res1 + (res - res2);
    }


    public List<CourseDto> getAllCoursesByPage(int interval, int currentPage, String text, String condition) {
        String sqlQuery = "";

        if (text != null) {
            sqlQuery = "select *\n" +
                    "from get_all_courses_by_pageable_and_search('" + text + "'," + interval + ", " + currentPage + ");";
        } else if (condition.equals("true") || condition.equals("false")) {
            boolean type = Boolean.parseBoolean(condition);
            sqlQuery = "select *\n" +
                    "from get_all_courses_by_pageable_and_search_and_status(" + type + ", " + interval + ", " + currentPage + ");";
        } else {
            sqlQuery = "select *\n" +
                    "from get_all_courses_by_pageable(" + interval + "," + currentPage + ");";
        }
        List<CourseDto> courseDtoList = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(UUID.fromString(rs.getString(1)));
            courseDto.setName(rs.getString(2));
            courseDto.setPrice(rs.getDouble(3));
            courseDto.setActive(rs.getBoolean(5));
            courseDto.setDescription(rs.getString(4));
            Array authors = rs.getArray(6);

            Type listType = new TypeToken<ArrayList<UserDto>>() {
            }.getType();
            List<UserDto> authorList = new Gson().fromJson(authors.toString(), listType);
            courseDto.setAuthors(authorList);
            return courseDto;
        });
        return courseDtoList;
    }


    public int getCourseCountByPage() {
        String sqlQuery = "select count (*) from courses";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
    }

    public int getCourseCountBySearch(String text) {
        String sqlQuery = "select count(*)\n" +
                "from get_all_courses_by_cound_and_search('" + text + "');";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
    }

    public int getCourseCountByType(boolean type) {
        String sqlQuery = "select count(*)\n" +
                "from get_all_courses_by_search_and_status_count(" + type + ");";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
    }

    public int getCourseCountByType() {
        String sqlQuery = "select count(*) from get_course_by_user_and_module_count();";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
    }

}