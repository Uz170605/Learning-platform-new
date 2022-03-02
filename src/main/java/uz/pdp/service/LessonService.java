package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.dao.LessonDao;
import uz.pdp.dto.LessonDto;
import uz.pdp.model.Attachment;
import uz.pdp.model.Lesson;
import uz.pdp.model.Task;

import java.util.List;
import java.util.UUID;


@Service
public class LessonService {

    @Autowired
    LessonDao lessonDao;

    public List<LessonDto> getLessonByPage(Integer currentPage) {
        return lessonDao.getLessonsByPage(currentPage);
    }

    public List<LessonDto> getAllLessons(){
        List<LessonDto> allLessons = lessonDao.getAllLessons();
        return allLessons;
    }

    public String addLesson(LessonDto lessonDto) {
        if (lessonDto.getId() != null) {
            if (lessonDao.editLesson(lessonDto) != 0) {
                return "Successfuly edited!";
            } else {
                return "Could not edited!";
            }
        } else {
            if (lessonDao.addLesson(lessonDto) != 0) {
                return "Successfuly added!";
            } else {
                return "Could not added!";
            }
        }
  }

    public String deleteLesson(UUID id) {
        if (lessonDao.deleteLesson(id) != 0) {
            return "Successfuly deleted!";
        } else {
        return "Could not deleted!";
        }
    }

    public LessonDto getLessonById(UUID id) {
        LessonDto lessonDto = lessonDao.getLessonById(id);
        return lessonDto;
    }
    public List<LessonDto> searchLesson(String word){
       return lessonDao.searchLesson(word);
    }
    public List<LessonDto> getLessonsByModuleId(UUID id){
        List<LessonDto> lessonsByModuleId = lessonDao.getLessonsByModuleId(id);
        return lessonsByModuleId;
    }
    public int addLessonToModule(Lesson lesson){
        int i = lessonDao.addLessonByModuleId(lesson);
        return i;
    }
    public int addTask(Task task){
        return lessonDao.addTask(task);
    }
    public String getModuleIdByLessonId(UUID uuid){
        return lessonDao.getModuleIdByLessonId(uuid);
    }
    public int saveVideo(Attachment attachment){
        return lessonDao.saveVideo(attachment);
    }
}
