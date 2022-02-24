package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.dto.LessonDto;
import uz.pdp.dto.ModuleDto;

import java.lang.reflect.Type;
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

    public int deleteLesson(UUID id) {
        String sqlQuery1 = "Delete from lessons where id='"+id+"'";
       int res = jdbcTemplate.update(sqlQuery1);
        return res;
    }

    public LessonDto getLessonById(UUID id) {
        String sqlQuery = "select * from get_all_lessons where lesson_id ='" + id+"'";
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

    public int editLesson(LessonDto lessonDto) {
        String sqlString =
                "update lessons set title='"+lessonDto.getTitle()+"',module_id='"+ lessonDto.getModuleId()+"' where id='" + lessonDto.getId() +"'";
    return jdbcTemplate.update(sqlString);
    }

    public List<LessonDto> getLessonsByPage(Integer currentPage){
        String sqlQuery = "select *\n" +
                " from get_lessons_by_page("+currentPage+");";
        List<LessonDto> lessonDtoListFromDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setId(UUID.fromString(rs.getString(1)));
            lessonDto.setTitle(rs.getString(2));
            lessonDto.setModuleId(UUID.fromString(rs.getString(3)));
            Object object = rs.getObject(4);
            Type listType = new TypeToken<ModuleDto>(){}.getType();
            ModuleDto moduleDto = new Gson().fromJson( object.toString(), listType);
                  lessonDto.setModuleDto(moduleDto);
            return lessonDto;
        });
        return lessonDtoListFromDb;
    }
    public int  pageButtonCount(){
        int  integer = jdbcTemplate.queryForObject("select count(*) from lessons", (rs, rom) -> {
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
}
