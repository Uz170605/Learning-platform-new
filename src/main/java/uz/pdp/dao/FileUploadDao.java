package uz.pdp.dao;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.dto.FileDto;
import uz.pdp.dto.LessonDto;

import java.util.List;
import java.util.UUID;

@Component
public class FileUploadDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer saveFileDb(UUID lessonId, String originalFilename, String contentType) {
        String sql ="INSERT INTO attachment(video_path, file_type, lesson_id) VALUES ('"+originalFilename+"','"+contentType+"','"+lessonId+"')";
        return jdbcTemplate.update(sql);
    }

    public List<FileDto> getFileVideo(){
        String sql = "Select * from get_file where file_type='video/mp4'";
       List<FileDto> fileDtoList= jdbcTemplate.query(sql,(rs, rowNum) -> {
           FileDto fileDto = new FileDto();
           fileDto.setId(UUID.fromString(rs.getString(1)));
           fileDto.setFileName(rs.getString(2));
           fileDto.setFileType(rs.getString(3));
           Object object = rs.getObject(4);
           LessonDto lesson =  new Gson().fromJson(object.toString(),LessonDto.class);
           fileDto.setLessonDto(lesson);
           return  fileDto;
        });

       return fileDtoList;
    }
    public List<FileDto> getFileTask(){
        String sql = "Select * from get_file where file_type='application/msword'";
        List<FileDto> fileDtoList= jdbcTemplate.query(sql,(rs, rowNum) -> {
            FileDto fileDto = new FileDto();
            fileDto.setId(UUID.fromString(rs.getString(1)));
            fileDto.setFileName(rs.getString(2));
            fileDto.setFileType(rs.getString(3));
            Object object = rs.getObject(4);
            LessonDto lesson =  new Gson().fromJson(object.toString(),LessonDto.class);
            fileDto.setLessonDto(lesson);
            return  fileDto;
        });

        return fileDtoList;
    }
    public List<FileDto> getFileManual(){
        String sql = "Select * from get_file where file_type='application/pdf'";
        List<FileDto> fileManualList= jdbcTemplate.query(sql,(rs, rowNum) -> {
            FileDto fileDto = new FileDto();
            fileDto.setId(UUID.fromString(rs.getString(1)));
            fileDto.setFileName(rs.getString(2));
            fileDto.setFileType(rs.getString(3));
            Object object = rs.getObject(4);
            LessonDto lesson =  new Gson().fromJson(object.toString(),LessonDto.class);
            fileDto.setLessonDto(lesson);
            return  fileDto;
        });

        return fileManualList;
    }

    public FileDto getFileById(UUID fileId) {
        String sql = "Select * from get_file where id ='"+fileId+"'";
        return jdbcTemplate.queryForObject(sql,(rs, rowNum) -> {
            FileDto fileDto = new FileDto();
            fileDto.setId(UUID.fromString(rs.getString(1)));
            fileDto.setFileName(rs.getString(2));
            fileDto.setFileType(rs.getString(3));
            Object object = rs.getObject(4);
            LessonDto lesson =  new Gson().fromJson(object.toString(),LessonDto.class);
            fileDto.setLessonDto(lesson);
            return  fileDto;
        });
    }

    public Integer deleteFile(UUID fileId) {
        String sql = "DELETE from attachment where id ='"+fileId+"'";
        return jdbcTemplate.update(sql);
    }
}
