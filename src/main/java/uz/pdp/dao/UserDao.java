package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<UserDto> getAllMentors() {
        String sqlQuery = "Select * from get_author;";
        List<UserDto> getUserDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            UserDto userDto = new UserDto();
            userDto.setId(UUID.fromString(rs.getString(1)));
            userDto.setFirstName(rs.getString(2));
            userDto.setLastName(rs.getString(3));
            userDto.setPhoneNumber(rs.getString(4));
            userDto.setEmail(rs.getString(5));
            userDto.setPassword(rs.getString(6));
            return userDto;
        });
        return getUserDb;
    }

    public UserDto getMentorById(UUID id) {
        String sqlQuery = "Select * from get_all_mentors where id='" + id + "'";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> {
            UserDto userDto = new UserDto();
            userDto.setId(UUID.fromString(rs.getString(1)));
            userDto.setFirstName(rs.getString(2));
            userDto.setLastName(rs.getString(3));
            userDto.setPhoneNumber(rs.getString(4));
            userDto.setEmail(rs.getString(5));
            userDto.setPassword(rs.getString(6));
            userDto.setBio(rs.getString(7));
            Array course = rs.getArray(8);

            Type type = new TypeToken<ArrayList<CourseDto>>() {
            }.getType();
            List<CourseDto> courses = new Gson().fromJson(course.toString(), type);
            userDto.setCourses(courses);
            return userDto;
        });
    }

    public List<UserDto> getAllUsers(int interval, int currentPage, String text) {
        String sqlQuery = "";
        if (text != null) {
sqlQuery = "select *\n" +
        "from get_all_users_pageable_search('"+text+"', "+interval+", "+currentPage+");";
        }else {
            sqlQuery = "select *\n" +
                    "from get_all_users_pageable(" + interval + ", " + currentPage + ");";
        }
        List<UserDto> userDtoList = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            UserDto userDto = new UserDto();
            userDto.setId(UUID.fromString(rs.getString(1)));
            userDto.setFirstName(rs.getString(2));
            userDto.setLastName(rs.getString(3));
            userDto.setPhoneNumber(rs.getString(4));
            userDto.setEmail(rs.getString(5));
            userDto.setPassword(rs.getString(6));
            Array array = rs.getArray(7);
            Type type = new TypeToken<ArrayList<Role>>() {
            }.getType();
            List<Role> roles = new Gson().fromJson(array.toString(), type);
            userDto.setRoles(roles);
            return userDto;
        });

        return userDtoList;
    }

    public List<Role> getUserRole() {
        String sqlQuery = "Select * from roles";
        List<Role> roles = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            Role role = new Role();
            role.setId(UUID.fromString(rs.getString(1)));
            role.setName(rs.getString(2));
            return role;
        });
        return roles;
    }

    public UserDto getUserById(UUID id) {
        String sqlQuery = "select * from get_all_users where id='" + id + "';";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> {
            UserDto userDto = new UserDto();
            userDto.setId(UUID.fromString(rs.getString(1)));
            userDto.setFirstName(rs.getString(2));
            userDto.setLastName(rs.getString(3));
            userDto.setPhoneNumber(rs.getString(4));
            userDto.setEmail(rs.getString(5));
            userDto.setPassword(rs.getString(6));
            userDto.setBio(rs.getString(7));
            Array array = rs.getArray(8);
            Type type = new TypeToken<ArrayList<Role>>() {
            }.getType();
            List<Role> roles = new Gson().fromJson(array.toString(), type);
            userDto.setRoles(roles);
            return userDto;
        });
    }


    public int addUser(UserDto userDto) {
        String sqlQuery = "Insert into users(\"firstName\", \"lastName\", \"phoneNumber\", email, password, bio)" +
                " values('" + userDto.getFirstName() + "','" + userDto.getLastName() + "','" + userDto.getPhoneNumber() + "','" + userDto.getEmail() + "'," +
                "'" + userDto.getPassword() + "','" + userDto.getBio() + "') returning id";
        String id = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) ->
                rs.getString("id")
        );
        UUID userId = UUID.fromString(id);
        int res = 0;
        for (UUID roleId : userDto.getRole()) {
            res = jdbcTemplate.update("Insert into users_roles values('" + userId + "','" + roleId + "')");
        }
        return res;
    }

    public int editUser(UserDto userDto) {
        int res1 = 0;
        int res2 = 0;
        int res3 = 0;
        if (userDto.getRole().length != 0) {
            res1 = jdbcTemplate.update("delete from users_roles where user_id='" + userDto.getId() + "'");
            for (UUID roleId : userDto.getRole()) {
                res2 = jdbcTemplate.update("Insert into users_roles values('" + userDto.getId() + "','" + roleId + "')");
            }
        }

        res3 = jdbcTemplate.update("UPDATE users SET \"firstName\"='" + userDto.getFirstName() + "'," +
                " \"lastName\"='" + userDto.getLastName() + "', \"phoneNumber\"='" + userDto.getPhoneNumber() + "'," +
                " email='" + userDto.getEmail() + "', password='" + userDto.getPassword() + "', bio='" + userDto.getBio() + "', updated_at=now() where id='" + userDto.getId() + "'");

        return res3 + (res1 - res2);
    }

    public int deleteUser(UUID userId) {
        int res1 = jdbcTemplate.update("DELETE FROM users_roles where user_id='" + userId + "'");
        int res2 = jdbcTemplate.update("DELETE FROM users where id='" + userId + "'");
        return res1 - res2;
    }

    public int getUserCount(String text) {
        String sqlQuery;
        if(text!=null){
            sqlQuery = "select count (*)\n" +
                    "from get_all_users_pageable_search_count('"+text+"');";
        }else {
            sqlQuery = "select count(*)\n" +
                    "from get_all_users_pageable_count();";
        }
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
    }


}
