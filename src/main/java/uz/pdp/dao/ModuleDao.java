package uz.pdp.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.dto.MentorCourseDto;
//import uz.pdp.dto.MentorCourseDto;
//import uz.pdp.dto.MentorCourseDto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Component
public class ModuleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<MentorCourseDto> getAllModuleFromDb(UUID courseId, UUID authorId) {
        String sql = "select * from get_mentor_courses_modules('" + courseId + "', '"+authorId+"');";
        List<MentorCourseDto> moduleDtoList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            MentorCourseDto moduleDto = new MentorCourseDto();
            moduleDto.setModuleId(UUID.fromString(rs.getString(1)));
            moduleDto.setModuleName(rs.getString(2));
            moduleDto.setModuleStatus(rs.getString(3));
            moduleDto.set_active(rs.getBoolean(4));
            moduleDto.setModulePrice(rs.getDouble(5));
            return moduleDto;
        });
        return moduleDtoList;
    }

    public int addModuleAndLesson(MentorCourseDto mentorCourseDto, UUID courseId) {
        String moduleQuery = "insert into modules(name,price,course_id) values " +
                "('" + mentorCourseDto.getModuleName() + "'," + mentorCourseDto.getModulePrice() + ",'" + courseId + "') returning id";
        String moduleId = jdbcTemplate.queryForObject(moduleQuery, (rs, rowNum) -> rs.getString(
                "id"));
        UUID moduleUUID = UUID.fromString(Objects.requireNonNull(moduleId));
        Integer result = 0;
        for (UUID userUUID : mentorCourseDto.getAuthorsId()) {
            result = jdbcTemplate.update("insert into authors_modules (author_id,module_id)values " +
                    "('" + userUUID + "','" + moduleUUID + "')");
        }
        String lessonSql =
                "insert into lessons(title,module_id) values ('" + mentorCourseDto.getLessonTitle() + "'," +
                        "'" + moduleUUID + "')returning id";
        String lessonId = jdbcTemplate.queryForObject(lessonSql, (rs, rowNum) -> rs.getString(
                "id"));
        UUID lessonUUID = UUID.fromString(Objects.requireNonNull(lessonId));
        String attachmentSql = "insert into attachment(video_path,lesson_id)values " +
                "('" + mentorCourseDto.getLessonVideoPath() + "','" + lessonUUID + "')";
        int check = 0;
        check = jdbcTemplate.update(attachmentSql);
        String taskSql="insert into tasks(title,difficulty_degree,grade,body,lesson_id) values " +
                "('"+mentorCourseDto.getTaskTitle()+"',"+mentorCourseDto.getTaskDegree()+","+mentorCourseDto.getTaskGrade()+
                ",'"+mentorCourseDto.getTaskBody()+"','"+lessonUUID+"')";
        check=jdbcTemplate.update(taskSql);
        return check;
    }

    public MentorCourseDto editModuleAndLesson(UUID uuid) {
        String sqlModule = "select id,is_active,name,price,course_id,status from  modules where " +
                "id='" + uuid + "'";
        return jdbcTemplate.queryForObject(sqlModule,
                (rs, rowNum) -> {
                    MentorCourseDto mentorCourseDto1 = new MentorCourseDto();
                    mentorCourseDto1.setModuleId(UUID.fromString(rs.getString(1)));
                    mentorCourseDto1.set_active(rs.getBoolean(2));
                    mentorCourseDto1.setModuleName(rs.getString(3));
                    mentorCourseDto1.setModulePrice(rs.getDouble(4));
                    mentorCourseDto1.setCourseId(UUID.fromString(rs.getString(5)));
                    mentorCourseDto1.setModuleStatus(rs.getString(6));
                    return mentorCourseDto1;
                });
    }

    public int editModuleMentor(MentorCourseDto mentorCourseDto) {
        String updateSql = "update modules set name = '" + mentorCourseDto.getModuleName() + "',price " +
                "=" + mentorCourseDto.getModulePrice() + "\n" +
                   " where id='" + mentorCourseDto.getModuleId() + "'";
        return jdbcTemplate.update(updateSql);
    }

    public String deleteMentorModule(UUID uuid) {
        String deleteSql = "select  delete_module('" + uuid + "')";
        return jdbcTemplate.queryForObject(deleteSql, (rs, rowNum) -> {
            String deleteText = rs.getString(1);
            return deleteText;
        });
    }

    public int sendMessage(UUID userId,String message, UUID moduleId) {
        String messageSql = "insert into admins_mentors_requests_modules(user_id," +
                "module_id,description)values ('"+userId+"','"+moduleId+"','"+message+
                "')";
        return jdbcTemplate.update(messageSql);

    }
}
