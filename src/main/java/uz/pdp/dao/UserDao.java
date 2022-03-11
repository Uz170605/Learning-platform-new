package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.LessonDto;
import uz.pdp.dto.ModuleDto;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Attachment;
import uz.pdp.model.Role;
import uz.pdp.model.Task;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Base64;
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
            userDto.setBio(rs.getString(7));
            byte[] encode = Base64.getEncoder().encode(rs.getBytes(8));
            String base64Encode=null;
            try {
                 base64Encode = new String(encode, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            userDto.setImage(base64Encode);
            return userDto;
        });
        return getUserDb;
    }

    public UserDto getMentorById(UUID id) {
        String sqlQuery = "Select * from get_author where id='" + id + "'";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> {
            UserDto userDto = new UserDto();
            userDto.setId(UUID.fromString(rs.getString(1)));
            userDto.setFirstName(rs.getString(2));
            userDto.setLastName(rs.getString(3));
            userDto.setPhoneNumber(rs.getString(4));
            userDto.setEmail(rs.getString(5));
            userDto.setPassword(rs.getString(6));
            userDto.setBio(rs.getString(7));
            byte[] encode = Base64.getEncoder().encode(rs.getBytes(8));
            String base64Encode=null;
            try {
                base64Encode = new String(encode, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            userDto.setImage(base64Encode);
            userDto.setBalance(rs.getDouble(9));
            return userDto;
        });
    }

    public List<UserDto> getAllUsers(int interval, int currentPage, String text) {
        String sqlQuery = "";
        if (text != null) {
//sqlQuery = "select *\n" +
//        "from get_all_users_pageable_search('"+text+"', "+interval+", "+currentPage+");";
        }else {
//            sqlQuery = "select *\n" +
//                    "from get_all_users_pageable(" + interval + ", " + currentPage + ");";
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
        String queryImage="Select content from images where id = 1";
        byte[] bytes = jdbcTemplate.queryForObject(queryImage, (rs, rowNum) -> rs.getBytes(1));
             String query="UPDATE users set image= ? where id='"+userId+"'";
            int update = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        query);
                ps.setBytes(1, bytes);//This is byte[] of type
                return ps;
            });
        int res = 0;
        for (UUID roleId : userDto.getRole()) {
            res = jdbcTemplate.update("Insert into users_roles values('" + userId + "','" + roleId + "')");
        }
        return res-update;
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
//        String sqlQuery;
//        if(text!=null){
//            sqlQuery = "select count (*)\n" +
//                    "from get_all_users_pageable_search_count('"+text+"');";
//        }else {
//            sqlQuery = "select count(*)\n" +
//                    "from get_all_users_pageable_count();";
//        }
//        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
//    }
        return 0;
    }

    public List<UserDto> getCourseAuthors(String id) {
        UUID courseId=UUID.fromString(id);
        String sqlQuery = "Select * from get_course_authors where course_id='"+courseId+"';";
        List<UserDto> getUserDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            UserDto userDto = new UserDto();
            userDto.setId(UUID.fromString(rs.getString(2)));
            userDto.setFirstName(rs.getString(3));
            userDto.setLastName(rs.getString(4));
            userDto.setPhoneNumber(rs.getString(5));
            userDto.setEmail(rs.getString(6));
            userDto.setPassword(rs.getString(7));
            userDto.setBio(rs.getString(8));
            byte[] encode = Base64.getEncoder().encode(rs.getBytes(12));
            String base64Encode=null;
            try {
                base64Encode = new String(encode, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            userDto.setImage(base64Encode);
            return userDto;
        });
        return getUserDb;
    }

    public List<CourseDto> getMyCourse(UUID userId) {
      String sql="Select * from get_user_all_courses where user_id='"+userId+"';";
      return jdbcTemplate.query(sql, (rs, rowNum) -> {
         CourseDto courseDto = new CourseDto();
         courseDto.setName(rs.getString(1));
         courseDto.setDescription(rs.getString(2));
          byte[] encode = Base64.getEncoder().encode(rs.getBytes(3));
          String base64Encode=null;
          try {
              base64Encode = new String(encode, "UTF-8");
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }
          courseDto.setImage(base64Encode);
          Array module = rs.getArray(4);
          Type type = new TypeToken<ArrayList<ModuleDto>>() {
          }.getType();
          List<ModuleDto> moduleList = new Gson().fromJson(module.toString(), type);
          courseDto.setModule(moduleList);
          return courseDto;
      });
    }

    public ModuleDto getModuleAllData(UUID uuid) {
      String sql="SELECT * FROM get_module_by_user where id='"+uuid+"'";
        ModuleDto moduleDto1 = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(2));
            return moduleDto;
        });
        moduleDto1.setLessons(getAllLessonForUser(uuid));
        moduleDto1.setUserDto(getAllMentorForUser(uuid));
        return moduleDto1;
    }

    private List<UserDto> getAllMentorForUser(UUID uuid) {
        String sqlQuery="SELECT * from get_module_author_by_user where id='"+uuid+"'";
        List<UserDto> getUserDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            UserDto userDto = new UserDto();
            userDto.setFirstName(rs.getString(2));
            userDto.setLastName(rs.getString(3));
            byte[] encode = Base64.getEncoder().encode(rs.getBytes(4));
            String base64Encode=null;
            try {
                base64Encode = new String(encode, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            userDto.setImage(base64Encode);
            return userDto;
        });
        return getUserDb;
    }

    private List<LessonDto> getAllLessonForUser(UUID uuid) {
        String sql="SELECT * FROM get_lesson_by_user where module_id='"+uuid+"';";
       return jdbcTemplate.query(sql,(rs, rowNum) -> {
            LessonDto lessonDto= new LessonDto();
            lessonDto.setId(UUID.fromString(rs.getString(1)));
            lessonDto.setTitle(rs.getString(2));
           Array attachment = rs.getArray(4);
           Type type = new TypeToken<ArrayList<Attachment>>() {
           }.getType();
           List<Attachment> attachmentList = new Gson().fromJson(attachment.toString(), type);
           lessonDto.setAttachmentList(attachmentList);
           return lessonDto;
        });
    }

    public String getLessonVideo(UUID id) {
        String sql="SELECT video_path from attachment where id='"+id+"'";
        return jdbcTemplate.queryForObject(sql,(rs, rowNum) -> rs.getString(1));
    }
    public LessonDto getLessonTitle(UUID id) {
        String sql="SELECT id,title from lessons where id= (Select lesson_id from attachment where attachment.id='"+id+"')";
        return jdbcTemplate.queryForObject(sql,(rs, rowNum) ->{
         LessonDto lessonDto = new LessonDto();
         lessonDto.setId(UUID.fromString(rs.getString(1)));
         lessonDto.setTitle(rs.getString(2));
         return lessonDto;
        });
    }

    public List<Task> getLessonTask(UUID lessonId,UUID moduleId) {
        String sql="SELECT * from tasks where lesson_id='"+lessonId+"' ";
        return jdbcTemplate.query(sql,(rs, rowNum) -> {
           Task task = new Task();
           task.setId(UUID.fromString(rs.getString(1)));
           task.setTitle(rs.getString(2));
           task.setDifficulty_degree(rs.getInt(3));
           task.setGrade(rs.getInt(4));
           task.setBody(rs.getString(5));
           task.setLesson_id(UUID.fromString(rs.getString(6)));
           task.setModule_id(moduleId);
           return task;
        });
    }
}
