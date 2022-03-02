package uz.pdp.dao;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.dto.LessonDto;
import uz.pdp.dto.MentorCourseDto;

import uz.pdp.dto.ModuleDto;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.*;
import java.util.UUID;


@Component
public class ModuleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ModuleDto> getAllModules() {
        String sql = "select id,name,is_active,price from modules;";
        List<ModuleDto> moduleDtoList = jdbcTemplate.query(sql, (rs, row) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(2));
            moduleDto.setActive(rs.getBoolean(3));
            moduleDto.setPrice(rs.getDouble(4));
            return moduleDto;
        });
        return moduleDtoList;
    }

    public ModuleDto getModuleById(UUID uuid) {
        String sql = "select * from modules where id='" + uuid + "'";
        return jdbcTemplate.queryForObject(sql, (rs, row) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(3));
            moduleDto.setActive(rs.getBoolean(2));
            moduleDto.setCourseId(UUID.fromString(rs.getString(5)));
            moduleDto.setPrice(rs.getDouble(4));
            return moduleDto;
        });
    }


//    public int editModule(ModuleDto moduleDto) {
//        String sql =
//                "update modules set name='" + moduleDto.getName() + "',is_active=" + moduleDto.isActive() + "," +
//                        "course_id='" + moduleDto.getCourseId() + "'," +
//                        "price=" + moduleDto.getPrice() + " where id='" + moduleDto.getId() + "';";
//        return jdbcTemplate.update(sql);
//    }

    public List<ModuleDto> viewModuleBYPage(int startPage, int totalPage) {
        String sql = "select * from modules limit " + (startPage - 1) + " offset " + totalPage + "";
        return jdbcTemplate.query(sql, (rs, row) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(3));
            moduleDto.setPrice(rs.getDouble(4));
            moduleDto.setActive(rs.getBoolean(2));
            return moduleDto;
        });
    }

    public int getCountPage() {
        int count = 0;
        String sql = "select count (*) from modules";
        count = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                rs.getInt(1));
        if (count % 3 == 0) {
            count = count / 3;
        } else count = count / 3 + 1;
        return count;
    }

    public List<ModuleDto> getModuleFromDb(Integer page, String search) {
        String sql =
                "select id,name,is_active,price from modules where name like '%" + search + "%' " +
                        "limit 3 " +
                        "offset " + page;
        List<ModuleDto> moduleDtoList = jdbcTemplate.query(sql, (rs, row) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(2));
            moduleDto.setActive(rs.getBoolean(3));
            moduleDto.setPrice(rs.getDouble(4));
            return moduleDto;
        });
        return moduleDtoList;
    }

    public List<ModuleDto> getModuleAndLesson(String text, Integer page) {
        String sql = "select * from get_module_lessons('" + text + "'," + page + ") ";
        List<ModuleDto> getModuleAndLessonByCourseId = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(2));
            moduleDto.setPrice(rs.getDouble(3));
            moduleDto.setStatus(rs.getString(4));
            Array lessons = rs.getArray(5);
            Type type = new TypeToken<ArrayList<LessonDto>>() {
            }.getType();
            List<LessonDto> lessonDtoList = new Gson().fromJson(lessons.toString(), type);
            moduleDto.setLessons(lessonDtoList);
            return moduleDto;
        });
        return getModuleAndLessonByCourseId;
    }

    public List<ModuleDto> getModuleByCourseId (UUID uuid){
        String sql="select * from module_by_course_id('" + uuid + "');";
        List<ModuleDto> mentorCourseDto1=jdbcTemplate.query(sql,
                (rs, rowNum) -> {
            ModuleDto mentorCourseDto=new ModuleDto();
            mentorCourseDto.setId(UUID.fromString(rs.getString(1)));
            mentorCourseDto.setName(rs.getString(2));
            mentorCourseDto.setStatus(rs.getString(3));
            mentorCourseDto.setPrice(rs.getDouble(4));
            mentorCourseDto.setActive(rs.getBoolean(5));
            Array lessonArray=rs.getArray(6);
            Type lessonType=new TypeToken<ArrayList<LessonDto>>(){}.getType();
            List<LessonDto> lessonDtoList=new Gson().fromJson(lessonArray.toString(),lessonType);
            mentorCourseDto.setLessons(lessonDtoList);
            return mentorCourseDto;
                });
        return mentorCourseDto1;
    }

    public int addModuleAndLesson(MentorCourseDto moduleLessonDto, UUID uuid2) {

        String addModuleSql = "insert into modules(name,price,course_id) values " +
                "('" + moduleLessonDto.getModuleName() + "'," + moduleLessonDto.getModulePrice() + ",'" + uuid2 +
                "') returning id";
        String moduleId = jdbcTemplate.queryForObject(addModuleSql, (rs, rowNum) -> rs.getString("id"));
        UUID moduleUuid = UUID.fromString(Objects.requireNonNull(moduleId));
        int result = 0;
        for (UUID uuid1 : moduleLessonDto.getAuthorsId()) {
            result =
                    jdbcTemplate.update("insert into authors_modules values ('" + uuid1 + "','" + moduleUuid + "')");
        }
        String addLessonSql = "insert into lessons(title,module_id)values " +
                "('" + moduleLessonDto.getLessonTitle() + "','" + moduleUuid + "') returning id";
        String lessonId = jdbcTemplate.queryForObject(addLessonSql, (rs, rowNum) ->
                rs.getString("id"));

        UUID lessonUUid = UUID.fromString(Objects.requireNonNull(lessonId));

        String attachmentSql = "insert into attachment(video_path,lesson_id,file_type) values " +
                "('" + moduleLessonDto.getLessonVideoPath() + "','" + lessonUUid + "','" + "vide" +
                "/mp4" + "')";
        int check = jdbcTemplate.update(attachmentSql);
        return check;
    }

    public int editModule(MentorCourseDto mentorCourseDto) {
        String moduleSql =
                "update modules set name='" + mentorCourseDto.getModuleName() + "',price="
                        + mentorCourseDto.getModulePrice() + ",course_id='" + mentorCourseDto.getCourseId() + "',";
        String moduleId = jdbcTemplate.queryForObject(moduleSql, (rs, rowNum) ->
                rs.getString("id"));
        UUID moduleUUid = UUID.fromString(Objects.requireNonNull(moduleId));
        int result = 0;
        for (UUID uuid1 : mentorCourseDto.getAuthorsId()) {
            result = jdbcTemplate.update("update authors_modules set author_id='" + uuid1 + "'," +
                    "module_id='" + moduleUUid + "'");
        }
        String editLessonSql =
                "update lessons set title='" + mentorCourseDto.getLessonTitle() + "','" + moduleUUid + "'";
        String lessonId = jdbcTemplate.queryForObject(editLessonSql, (rs, rowNum) -> rs.getString(
                "id"));
        UUID lessonUuid = UUID.fromString(Objects.requireNonNull(lessonId));

        String attachmentSql =
                "update attachment set video_path='" + mentorCourseDto.getLessonVideoPath() +
                        "'," +
                        "lesson_id='" + lessonUuid + "',file_type='" + "video/mp4" + "'";
        int check = jdbcTemplate.update(attachmentSql);
        return check;
    }

    public String deleteModule(UUID uuid) {
        String optionalDeleteSql = "select * from delete_module('" + uuid + "')";
        return jdbcTemplate.queryForObject(optionalDeleteSql, (rs, rowNum) -> rs.getString(1));
    }

    public int sendMessage(UUID uuid, String message, UUID userId,UUID courseId) {
        String sql="insert into admin_mentors_requests_modules(user_id,course_id,module_id," +
                "description) values ('"+userId+"','"+courseId+"','"+uuid+"','"+message+"')";
        return jdbcTemplate.update(sql);


    }
}
