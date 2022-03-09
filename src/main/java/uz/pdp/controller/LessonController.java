package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dao.LessonDao;
import uz.pdp.dao.ModuleDao;
import uz.pdp.dto.AttachmentDto;
import uz.pdp.dto.LessonDto;
import uz.pdp.dto.MentorCourseDto;
import uz.pdp.dto.TaskDto;
import uz.pdp.model.Attachment;
import uz.pdp.model.Lesson;
import uz.pdp.model.Task;
import uz.pdp.service.LessonService;
import uz.pdp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @Autowired
    LessonDao lessonDao;

    @Autowired
    UserService userService;

    @Autowired
    ModuleDao moduleDao;
//    @GetMapping
//    public String getAllLessons(Model model){
//             List<LessonDto> moduleDtoList = lessonService.getAllLessons();
//        int buttonCount = lessonDao.pageButtonCount();
//        model.addAttribute("lessonList", moduleDtoList);
//        model.addAttribute("buttonCount",buttonCount);
//        return "view-lessons";
//    }


    @GetMapping("page/{moduleIds}/{currentPage}")
    public String getAllLessons(@PathVariable String moduleIds,
                                @PathVariable Integer currentPage, Model model) {
        UUID moduleId = UUID.fromString(moduleIds);
        List<LessonDto> moduleDtoList = lessonService.getLessonByPage(currentPage, moduleId);
        int buttonCount = lessonDao.pageLessonsByModuleId(moduleId);
        model.addAttribute("buttonCount", buttonCount);
        model.addAttribute("lessonList", moduleDtoList);
        return "view-lessons-by-module-id";
    }
    @GetMapping("/editModuleId/{id}")
    public String editLessonByModuleId(@PathVariable(required = false) String id, Model model) {
        UUID id1 = UUID.fromString(id);
        LessonDto lessonById = lessonService.getLessonById(id1);
        model.addAttribute("selectLesson", lessonById);
        return "lesson-form-by-module-id";
    }
    @GetMapping("/addLesson")
    public String getLesson(Model model) {
//        model.addAttribute("modules",moduleDao.getAllModules());
        return "lesson-form";
    }

    @GetMapping("/addLesson/{moduleId}")
    public String getLessonByModuleId(@PathVariable String moduleId, Model model) {
        UUID modulId = UUID.fromString(moduleId);
        model.addAttribute("moduleId", modulId);
        return "lesson-form-by-module-id";

    }
    @GetMapping("/delete/{id}")
    public String Cascade(@PathVariable String id, Model model) {
        UUID id1 = UUID.fromString(id);
        String str = lessonService.deleteLesson(id1);
        model.addAttribute("message", str);
//        UUID id2 = UUID.fromString(lessonService.getModuleIdByLessonId(id1));
        return "redirect:/lessons/byModuleId";
    }

    @GetMapping("/search")
    public String searchLesson(@RequestParam String word, Model model) {
        List<LessonDto> lessonDtos = lessonService.searchLesson(word);
        return "";
    }

    @GetMapping("/byModuleId/{id}")
    public String getLessonsByModuleId(@PathVariable String id, Model model,
                                       HttpServletRequest request) {
        if (id != null) {
            HttpSession moduleSession = request.getSession();
            moduleSession.setAttribute("moduleId", id);
        }
        UUID moduleId = UUID.fromString(String.valueOf(request.getSession().getAttribute("moduleId")));
        List<LessonDto> moduleDtoList = lessonService.getLessonsByModuleId(moduleId);
        int buttonCount = lessonDao.pageLessonsByModuleId(moduleId);
        model.addAttribute("moduleId", moduleId);
        model.addAttribute("lessonList", moduleDtoList);
        model.addAttribute("buttonCount", buttonCount);
        return "view-lessons-by-module-id";
    }

    @GetMapping("/byModuleId")
    public String getLessonsByModuleId2(Model model, HttpServletRequest request) {
        UUID moduleId = UUID.fromString(String.valueOf(request.getSession().getAttribute("moduleId")));
        List<LessonDto> moduleDtoList = lessonService.getLessonsByModuleId(moduleId);
        int buttonCount = lessonDao.pageLessonsByModuleId(moduleId);
        model.addAttribute("moduleId", moduleId);
        model.addAttribute("lessonList", moduleDtoList);
        model.addAttribute("buttonCount", buttonCount);
        return "view-lessons-by-module-id";
    }

    @GetMapping("/addLessonToModule")
    public String addLessonToSelectedModule(@ModelAttribute("lessons") MentorCourseDto lesson,
                                            Model model) {
        if (lesson.getLessonId() != null) {
            lessonDao.editLesson(lesson);
            model.addAttribute("msg", "Succesfully edited!!!");
        } else {
            if (lessonService.addLessonToModule(lesson) > 0) {
                model.addAttribute("message", "Succesfully added!!!");
            } else {
                model.addAttribute("message", "Could not added!!!");
            }
        }
        return "redirect:/lessons/byModuleId";
    }

    @GetMapping("/viewVideo/{lessonId}")
    public String viewVideo(@PathVariable UUID lessonId, Model model, HttpServletRequest request) {
        if (lessonId == null) {
            lessonId = (UUID) request.getSession().getAttribute("lessonId");
        }
        HttpSession session = request.getSession();
        session.setAttribute("lessonId", lessonId);
        List<Attachment> attachmentList = lessonDao.getAttachmentsByLessonId(lessonId);
        UUID moduleId = UUID.fromString(lessonService.getModuleIdByLessonId(lessonId));
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("moduleId", moduleId);
        model.addAttribute("videoList", attachmentList);
        return "view-videos-by-lesson-id";
    }

    @GetMapping("/viewVideo")
    public String viewVideos(Model model, HttpServletRequest request) {
        UUID lessonId = (UUID) request.getSession().getAttribute("lessonId");
        List<Attachment> attachmentList = lessonDao.getAttachmentsByLessonId(lessonId);
        UUID moduleId = UUID.fromString(lessonService.getModuleIdByLessonId(lessonId));
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("moduleId", moduleId);
        model.addAttribute("videoList", attachmentList);
        return "view-videos-by-lesson-id";
    }

    @GetMapping("/viewTask/{lessonId}")
    public String viewTask(@PathVariable UUID lessonId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("lessonId", lessonId);
        List<Task> taskList = lessonDao.getTaskByLessonId(lessonId);
        UUID moduleId = UUID.fromString(lessonService.getModuleIdByLessonId(lessonId));
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("moduleId", moduleId);
        model.addAttribute("taskList", taskList);
        return "view-tasks-by-lesson-id";
    }

    @GetMapping("/viewTask")
    public String viewTask(Model model, HttpServletRequest request) {
        UUID lessonId = (UUID) request.getSession().getAttribute("lessonId");
        HttpSession session = request.getSession();
        session.setAttribute("lessonId", lessonId);
        List<Task> taskList = lessonDao.getTaskByLessonId(lessonId);
        UUID moduleId = UUID.fromString(lessonService.getModuleIdByLessonId(lessonId));
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("moduleId", moduleId);
        model.addAttribute("taskList", taskList);
        return "view-tasks-by-lesson-id";
    }

    @GetMapping("/addVideo/{lessonId}")
    public String addVideoForm(@PathVariable UUID lessonId, Model model) {
        model.addAttribute("lessonId", lessonId);
        return "add-video";
    }

    @GetMapping("/addTask/{lessonId}")
    public String addTask(@PathVariable UUID lessonId, Model model) {
//        UUID moduleId = UUID.fromString(lessonService.getModuleIdByLessonId(lessonId));
        model.addAttribute("lessonId", lessonId);
        return "add-task";
    }

    @PostMapping("/addTask")
    public String addtasksToDb(@ModelAttribute("lessons") TaskDto task, Model model) {
        if (task.getId() == null) {
            lessonService.addTask(task);
        } else {
            lessonService.editTask(task);
        }
        return "redirect:/lessons/viewTask";
    }

    @PostMapping("/addVideo")
    public String addVideoToDb(@ModelAttribute("lessons") AttachmentDto attachment) {
        if (attachment.getId() != null) {
            lessonService.editAttachment(attachment);
        } else {
            lessonService.saveVideo(attachment);
        }
        return "redirect:/lessons/viewVideo";
    }

    @GetMapping("/editTask/{taskId}")
    public String editTask(@PathVariable UUID taskId, Model model) {
        TaskDto taskById = lessonService.getTaskById(taskId);
        model.addAttribute("selectedTask", taskById);
        return "add-task";
    }

    @GetMapping("/editAttachment/{attachmentId}")
    public String editAttachment(@PathVariable UUID attachmentId, Model model) {
        AttachmentDto attachmentById = lessonService.getAttachmentById(attachmentId);
        model.addAttribute("selectedVideo", attachmentById);
        return "add-video";
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable UUID taskId) {
        lessonService.deleteTaskById(taskId);
        return "redirect:/lessons/viewTask";
    }

    @GetMapping("/deleteAttachment/{attachmentId}")
    public String deleteVideo(@PathVariable UUID attachmentId) {
        lessonService.deleteVideoById(attachmentId);
        return "redirect:/lessons/viewVideo";
    }
}
