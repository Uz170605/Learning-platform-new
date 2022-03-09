package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.dto.*;
import uz.pdp.model.Attachment;
import uz.pdp.model.Task;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Component
public class LessonDao {

    @Autowired
    JdbcTemplate jdbcTemplate;



    public List<LessonDto> getAllLessons() {
        String sqlQuery = "select * " +
                "from get_all_lessons";
        List<LessonDto> lessonDtoListFromDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setId(UUID.fromString(rs.getString(1)));
            lessonDto.setTitle(rs.getString(2));
            Object modules =  rs.getObject(3);
            Type listType = new TypeToken<ModuleDto>(){}.getType();
            ModuleDto moduleDto = new Gson().fromJson( modules.toString(), listType);
            lessonDto.setModuleDto(moduleDto);
            return lessonDto;
        });
        return lessonDtoListFromDb;
    }

    public int addLesson(LessonDto lessonDto) {
        String sqlQuery ="Insert into lessons(title, module_id) values('" + lessonDto.getTitle() +
                "','" + lessonDto.getModuleId()+ "')";
//        String idStr = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> rs.getString("id"));
//        UUID uuid = UUID.fromString(Objects.requireNonNull(idStr));
        return jdbcTemplate.update(sqlQuery);
    }
    public int addLessonByModuleId(MentorCourseDto lesson) {
        String lessonId = "";
        String sqlQuery ="Insert into lessons(title, module_id) values('" + lesson.getLessonTitle() +
                "','" + lesson.getModuleId()+ "') returning id";
//        String idStr = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> rs.getString("id"));
//        UUID uuid = UUID.fromString(Objects.requireNonNull(idStr));
         lessonId = jdbcTemplate.queryForObject(sqlQuery, (rs, row) ->
                rs.getString(1));
         lesson.setLessonId(UUID.fromString(lessonId));
         addVideoToLesson(lesson);
         addTaskToLesson(lesson);
        return 1;
    }
    public int addVideoToLesson(MentorCourseDto lesson){
        String sql = "insert into attachment (video_path,lesson_id) values ('"+lesson.getLessonVideoPath()+
                "','"+lesson.getLessonId()+"')";
        return jdbcTemplate.update(sql);
    }
    public int addTaskToLesson(MentorCourseDto lesson){
        String sql ="insert into tasks (title, difficulty_degree, grade, body, lesson_id) values" +
                " ('"+lesson.getLessonTitle()+"',"+lesson.getTaskDegree()+","+lesson.getTaskGrade()+"," +
                "'"+lesson.getTaskBody()+"'," +
                "'"+lesson.getLessonId()+"')";
        return jdbcTemplate.update(sql);
    }

    public int deleteLesson(UUID id) {
        deleteTask(id);
        deleteAttachment(id);
        try {
            String sqlQuery1 = "Delete from" +
                    " lessons where id='" + id + "'";
            int res = jdbcTemplate.update(sqlQuery1);
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public int deleteAttachment(UUID lesson__id){
        try {
            String sql = "delete from attachment where lesson_id='" + lesson__id +"'";
            return jdbcTemplate.update(sql);
        }catch (Exception e){
            return 0;
        }
    }
    public int deleteTask(UUID lesson__id){
        try {
            String sql = "delete from tasks where lesson_id='" + lesson__id +"'";
            return jdbcTemplate.update(sql);
        }catch (Exception e){
            return 0;
        }
    }
    public LessonDto getLessonById(UUID id) {
        String sqlQuery = "select * from get_lesson_by_id('"+id+"')";
        return  jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setId(UUID.fromString(rs.getString(1)));
            lessonDto.setTitle(rs.getString(2));
            Object module = rs.getObject(3);

            Type listType = new TypeToken<ModuleDto>(){}.getType();
            ModuleDto moduleDto = new Gson().fromJson(module.toString(), listType);

            lessonDto.setModuleDto(moduleDto);
            return lessonDto;
        });
    }

    public int editLesson(MentorCourseDto lesson) {
        String sqlString =
                "update lessons set title='"+lesson.getLessonTitle()+"' where id='" + lesson.getLessonId() +"'";
        return jdbcTemplate.update(sqlString);
    }
    public int editTask(TaskDto task){
        String sql =
                "update tasks set title='"+task.getTitle()+"',difficulty_degree="+task.getDifficultyDegree()+
                ",grade="+task.getGrade()+"," +
                "body='"+task.getBody()+"' where id = '" +task.getId() + "'";
        return jdbcTemplate.update(sql);
    }
    public int editAttachment(AttachmentDto attachmentDto){
        String sql = "update attachment set video_path = '"+attachmentDto.getVideo_path()+"' " +
                "where id='" + attachmentDto.getId() + "'";
        return jdbcTemplate.update(sql);
    }

    public List<LessonDto> getLessonsByPage(Integer currentPage,UUID moduleId){
        String sqlQuery = "select *\n" +
                " from get_lessons_by_page("+currentPage+",'"+moduleId+"');";
        List<LessonDto> lessonDtoListFromDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setId(UUID.fromString(rs.getString(1)));
            lessonDto.setTitle(rs.getString(2));
            Object object = rs.getObject(3);
            Type listType = new TypeToken<ModuleDto>(){}.getType();
            ModuleDto moduleDto = new Gson().fromJson( object.toString(), listType);
            lessonDto.setModuleDto(moduleDto);
            return lessonDto;
        });
        return lessonDtoListFromDb;
    }
//    public int  pageButtonCount(){
//        int  integer = jdbcTemplate.queryForObject("select count(*) from lessons", (rs, rom) -> {
//            int max_lessons = rs.getInt(1);
//            return max_lessons;
//        });
//        if (integer%5!=0) {
//            return integer/5+1;
//        }
//        return integer/5;
//    }
    public int pageLessonsByModuleId(UUID id){
        int  integer = jdbcTemplate.queryForObject("select count(*) from " +
                "get_all_lessons_by_module_id('"+id+"')", (rs, rom) -> {
            int max_lessons = rs.getInt(1);
            return max_lessons;
        });
        if (integer%5!=0) {
            return integer/5+1;
        }
        return integer/5;
    }

    public List<LessonDto> searchLesson(String word) {
        String sqlQuery = "select * from lesson_search('"+word+"')";
        List<LessonDto> lessonDtoList = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setId(UUID.fromString(rs.getString(1)));
            lessonDto.setTitle(rs.getString(2));
            Object module = rs.getObject(3);

            Type listType = new TypeToken<ModuleDto>() {
            }.getType();
            ModuleDto moduleDto = new Gson().fromJson(module.toString(), listType);

            lessonDto.setModuleDto(moduleDto);
            return lessonDto;
        });
        return lessonDtoList;
    }
    public List<LessonDto> getLessonsByModuleId(UUID id){
        String sqlString = "select * from get_all_lessons_by_module_id('"+id+"')" +
                " limit 5";
        List<LessonDto> lessonDtoListFromDb = jdbcTemplate.query(sqlString, (rs, row) -> {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setId(UUID.fromString(rs.getString(1)));
            lessonDto.setTitle(rs.getString(2));
            Object modules =  rs.getObject(3);
            Type listType = new TypeToken<ModuleDto>(){}.getType();
            ModuleDto moduleDto = new Gson().fromJson( modules.toString(), listType);
            lessonDto.setModuleDto(moduleDto);
            return lessonDto;
        });
        return lessonDtoListFromDb;
    }
    public int addTask(TaskDto task){
        String sql =
                "insert into tasks ( title, difficulty_degree, grade, body, lesson_id) " +
                        "VALUES ('"+task.getTitle()+"',"+task.getDifficultyDegree()+","+task.getGrade()+
                        "," +
                        "'"+task.getBody()+"','"+task.getLessonId()+"')";
        return jdbcTemplate.update(sql);
    }
    public String getModuleIdByLessonId(UUID lessonId){
        String sql = "select m.id from modules m\n" +
                "join lessons l on m.id = l.module_id\n" +
                "where l.id='"+lessonId+"'\n";
        String uuid = jdbcTemplate.queryForObject(sql, (rs, row) -> rs.getString(1));
        return uuid;
    }
 public int saveVideo(AttachmentDto attachment){
        String sql ="insert into attachment (video_path,lesson_id) values ('"+attachment.getVideo_path()
                +"','"+attachment.getLesson_id()+"')";
        return   jdbcTemplate.update(sql);
    }
    public List<Attachment> getAttachmentsByLessonId(UUID lessonID){
        String sql ="select json_agg(row_to_json(a.*))\n" +
                "from attachment a  where lesson_id='"+lessonID+"';";
        List<Attachment> attachmentList= jdbcTemplate.queryForObject(sql, (rs, row) -> {
            List<Attachment> attachmentList1 = new ArrayList<>();
            Array array = rs.getArray(1);
            Type listType = new TypeToken<List<Attachment>>(){}.getType();
            if (array != null){
            attachmentList1 = new Gson().fromJson(array.toString(), listType);
            }
            return attachmentList1;
        });
        return attachmentList;
    }

    public List<Task> getTaskByLessonId(UUID lessonID){
        String sql ="select json_agg(row_to_json(t.*))\n" +
                "from tasks t  where lesson_id='"+lessonID+"';";
        List<Task> taskList = jdbcTemplate.queryForObject(sql, (rs, row) -> {
            List<Task> attachmentList1 = new ArrayList<>();
            Array array = rs.getArray(1);
            Type listType = new TypeToken<List<Task>>(){}.getType();
            if (array!=null) {
                attachmentList1 = new Gson().fromJson(array.toString(), listType);
            }
            return attachmentList1;
        });
        return taskList;
    }
    public TaskDto getTaskById(UUID taskId){
        String sql = "select * from tasks where id = '" + taskId + "'";
        return jdbcTemplate.queryForObject(sql,(rs,row) ->{
            TaskDto taskDto = new TaskDto();
            taskDto.setId(UUID.fromString(rs.getString(1)));
            taskDto.setTitle(rs.getString(2));
            taskDto.setDifficultyDegree(rs.getInt(3));
            taskDto.setGrade(rs.getInt(4));
            taskDto.setBody(rs.getString(5));
            taskDto.setLessonId(UUID.fromString(rs.getString(6)));
           return taskDto;
        });
    }
    public AttachmentDto getAttachmentById(UUID attachmentId){
        String sql = "select * from attachment where id = '" + attachmentId + "'";
        return jdbcTemplate.queryForObject(sql,(rs,row) ->{
            AttachmentDto attachmentDto = new AttachmentDto();
            attachmentDto.setId(UUID.fromString(rs.getString(1)));
            attachmentDto.setVideo_path(rs.getString(2));
            attachmentDto.setLesson_id(UUID.fromString(rs.getString(3)));
            return attachmentDto;
        });
    }
    public int deleteTaskById(UUID taskId){
        String sql ="delete from tasks where id = '" + taskId + "'";
        return jdbcTemplate.update(sql);
    }
    public int deleteVideoById(UUID videoId){
        String sql = "delete from attachment where id = '" + videoId + "'";
        return jdbcTemplate.update(sql);
    }
}
