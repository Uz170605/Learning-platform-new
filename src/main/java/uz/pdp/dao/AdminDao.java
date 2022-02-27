package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.MentorCourseDto;
import uz.pdp.dto.ModuleDto;
import uz.pdp.dto.UserDto;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<UserDto> getAllMessage() {

        String sqlQuery = "select u.id, u.\"firstName\", u.\"lastName\"\n" +
                "from users u\n" +
                "         join admins_mentors_requests_courses c on u.id = c.user_id\n" +
                "group by u.id;";

//        String sqlQuery = "select c.id, c.name, c.status, c.is_active from courses c\n" +
//                " join modules m on c.id = m.course_id group by c.id ";

        List<UserDto> userDtoListFromDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            UserDto userDto = new UserDto();
            userDto.setId(UUID.fromString(rs.getString(1)));
            userDto.setFirstName(rs.getString(2));
            userDto.setLastName(rs.getString(3));




            return userDto;
        });
        return userDtoListFromDb;
    }

    public List<UserDto> getMessageById(UUID messageId) {
        String sqlQuery = "select user_id, description, course_id, u.\"lastName\", u.\"firstName\"\n" +
                "from admins_mentors_requests_courses a\n" +
                "join users u on a.user_id = u.id\n" +
                "where user_id = '"+messageId+"';";

//        String sqlQuery = "select c.id, c.name, c.status, c.is_active from courses c\n" +
//                " join modules m on c.id = m.course_id group by c.id ";

        List<UserDto> userDtoListFromDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            UserDto userDto = new UserDto();
            userDto.setId(UUID.fromString(rs.getString(1)));
            userDto.setMessage(rs.getString(2));
            userDto.setCourseId(UUID.fromString(rs.getString(3)));
            userDto.setLastName(rs.getString(4));
            userDto.setFirstName(rs.getString(5));
            return userDto;
        });
        return userDtoListFromDb;
    }

    public String rejectedCourse(UUID uuid) {
        String query = "select rejectCourses('"+uuid+"');";
        return jdbcTemplate.queryForObject(query, (rs, row) -> {
            String courseDto = rs.getString(1);
            return courseDto;
        });
    }

    public String acceptCourse(UUID uuid) {
        String query = "select acceptCourses('"+uuid+"');";
        return jdbcTemplate.queryForObject(query, (rs, row) -> {
            String courseDto = rs.getString(1);
            return courseDto;
        });
    }

    public MentorCourseDto getCourseById(UUID id) {
        String sqlQuery = "select * from viewCourseAllData('"+id+"')";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> {
            MentorCourseDto courseDto = new MentorCourseDto();
            courseDto.setCourseId(UUID.fromString(rs.getString(1)));
            courseDto.setName(rs.getString(2));
            courseDto.setDescription(rs.getString(3));
            courseDto.setCourseImage(rs.getBytes(4));
            courseDto.setModuleName(rs.getString(6));
            courseDto.setModulePrice(rs.getInt(7));
            courseDto.setLessonTitle(rs.getString(8));
            courseDto.setLessonVideoPath(rs.getString(9));

            Array authors = rs.getArray(5);
            Type listType = new TypeToken<ArrayList<UserDto>>() {
            }.getType();
            List<UserDto> authorList = new Gson().fromJson(authors.toString(), listType);
            courseDto.setUserDtos(authorList);

            return courseDto;
        });
    }
}
