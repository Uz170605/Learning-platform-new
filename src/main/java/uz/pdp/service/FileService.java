package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.dao.FileDownloadDao;
import uz.pdp.dto.CourseModuleLessonDto;
import uz.pdp.dto.FileDto;
import uz.pdp.dto.LessonDto;

import java.util.List;
import java.util.UUID;

@Service

public class FileService {
    @Autowired
    FileDownloadDao fileDownloadDao;

    public String pdfDownload(UUID uuid) {
        List<FileDto> file = fileDownloadDao.getAllLessons(uuid);
        String path = "";
        for (FileDto fileDto : file) {
            if (fileDto.getFileType().equals("application/pdf")) {
                path = fileDto.getFileName();
                break;
            }
        }
        return path;
    }

    public LessonDto lessonDto(UUID uuid){
        return fileDownloadDao.getLesson(uuid);
    }

    public CourseModuleLessonDto getAllData(UUID uuid){
        return fileDownloadDao.getCourseModule(uuid);
    }


    public String docDownload(UUID uuid) {
        List<FileDto> file = fileDownloadDao.getAllLessons(uuid);
        String path = "";
        for (FileDto fileDto : file) {
            if (fileDto.getFileType().equals("application/doc")) {
                path = fileDto.getFileName();
                break;
            }
        }
        return path;
    }

    public String viewVideo(UUID uuid) {
        List<FileDto> file = fileDownloadDao.getAllLessons(uuid);
        String path = "";
        for (FileDto fileDto : file) {
            if (fileDto.getFileType().equals("video/mp4")) {
                path = fileDto.getFileName();
                break;
            }
        }
        return path;
    }

}
