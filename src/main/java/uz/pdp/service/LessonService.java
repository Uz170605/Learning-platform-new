package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.dao.LessonDao;
import uz.pdp.dto.AttachmentDto;
import uz.pdp.dto.LessonDto;
import uz.pdp.dto.MentorCourseDto;
import uz.pdp.dto.TaskDto;
import uz.pdp.model.Attachment;
import uz.pdp.model.Lesson;
import uz.pdp.model.Task;

import java.util.List;
import java.util.UUID;


@Service
public class LessonService {

    @Autowired
    LessonDao lessonDao;

    public List<LessonDto> getLessonByPage(Integer currentPage,UUID moduleId) {
        return lessonDao.getLessonsByPage(currentPage,moduleId);
    }

    public List<LessonDto> getAllLessons(){
        List<LessonDto> allLessons = lessonDao.getAllLessons();
        return allLessons;
    }

    public String addLesson(MentorCourseDto lesson) {
        if (lesson.getLessonId() != null) {
            if (lessonDao.editLesson(lesson) != 0) {
                return "Successfuly edited!";
            } else {
                return "Could not edited!";
            }
        } else {
            if (lessonDao.addLessonByModuleId(lesson) != 0) {
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
    public int addLessonToModule(MentorCourseDto lesson){
        int i = lessonDao.addLessonByModuleId(lesson);
        return i;
    }
    public int addTask(TaskDto task){
        return lessonDao.addTask(task);
    }
    public String getModuleIdByLessonId(UUID uuid){
        return lessonDao.getModuleIdByLessonId(uuid);
    }
    public int saveVideo(AttachmentDto attachment){
        return lessonDao.saveVideo(attachment);
    }
    public TaskDto getTaskById (UUID taskId){
        return lessonDao.getTaskById(taskId);
    }
    public int editTask(TaskDto taskDto){
        return lessonDao.editTask(taskDto);
    }
    public int editAttachment(AttachmentDto attachmentDto){
        return lessonDao.editAttachment(attachmentDto);
    }
    public int deleteTaskById(UUID taskId){
        return lessonDao.deleteTaskById(taskId);
    }
    public int deleteVideoById(UUID videoId){
        return lessonDao.deleteVideoById(videoId);
    }
    public AttachmentDto getAttachmentById(UUID id){
        return lessonDao.getAttachmentById(id);
    }
}
