package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uz.pdp.config.WebMvcConfig;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.ModuleDto;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRoleDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public UserDto getUserById(UUID uuid){
        UserDto userDto = new UserDto();
        String sql = "select * from get_user_by_id('"+uuid+"')";
        jdbcTemplate.queryForObject(sql, (rs,row) -> {
            UUID id = UUID.fromString(rs.getString(1));
            userDto.setId(id);
            String firstName = rs.getString(2);
            userDto.setFirstName(firstName);
            String lastName = rs.getString(3);
            userDto.setLastName(lastName);
            String phoneNumber = rs.getString(4);
            userDto.setPhoneNumber(phoneNumber);
            String email = rs.getString(5);
            userDto.setEmail(email);
            String password = rs.getString(6);
            userDto.setPassword(password);
            String bio = rs.getString(7);
            userDto.setBio(bio);
            Array modules = rs.getArray(8);
            Type listType = new TypeToken<ArrayList<ModuleDto>>() {
            }.getType();
            List<ModuleDto> moduleDtos = new Gson().fromJson(modules.toString(), listType);

            return userDto;
        });
        return userDto;
    }
}
