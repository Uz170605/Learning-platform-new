package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dao.LessonDao;
import uz.pdp.dao.ModuleDao;
import uz.pdp.dto.LessonDto;
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
    @GetMapping
    public String getAllLessons(Model model){
             List<LessonDto> moduleDtoList = lessonService.getAllLessons();
        int buttonCount = lessonDao.pageButtonCount();
        model.addAttribute("lessonList", moduleDtoList);
        model.addAttribute("buttonCount",buttonCount);
        return "view-lessons";
    }


    @GetMapping("page/{currentPage}")
    public String getAllLessons(@PathVariable Integer currentPage,Model model){
        List<LessonDto> moduleDtoList = lessonService.getLessonByPage(currentPage);
        int buttonCount = lessonDao.pageButtonCount();
        model.addAttribute("buttonCount",buttonCount);
        model.addAttribute("lessonList", moduleDtoList);
        return "view-lessons";
    }
//    @GetMapping("/lessonAllData/{id}")
//    public String getLessonByIdWithAuthor(@PathVariable(required = false) String id, Model model){
//        UUID id1 =UUID.fromString(id);
//        LessonDto lessonById = lessonService.getLessonById(id1);
//        model.addAttribute("selectLesson",lessonById);
//        return "view-select-lesson";
//    }
    @GetMapping("/{id}")
    public String getLessonById(@PathVariable(required = false) String id, Model model){
        UUID id1 =UUID.fromString(id);
        LessonDto lessonById = lessonService.getLessonById(id1);
        model.addAttribute("authors",userService.getAllMentors());
//        model.addAttribute("modules",moduleDao.getAllModules());
        model.addAttribute("selectLesson",lessonById);
        return "lesson-form";
    }
    @GetMapping("/editModuleId/{id}")
    public String editLessonByModuleId(@PathVariable(required = false) String id, Model model){
        UUID id1 =UUID.fromString(id);
        LessonDto lessonById = lessonService.getLessonById(id1);
        model.addAttribute("selectLesson",lessonById);
        return "lesson-form-by-module-id";
    }
//    @GetMapping("/add/{id}")
//    public String addLessonByModule(@PathVariable String id,Model model){
//        UUID uuid = UUID.fromString(id);
//        List<LessonDto> lessonsByModuleId = lessonService.getLessonsByModuleId(uuid);
//        model.addAttribute("module_lessons",lessonsByModuleId);
//        return "view-modul-lessons";
//    }
    @GetMapping("/addLesson")
    public String getLesson(Model model){
//        model.addAttribute("modules",moduleDao.getAllModules());
        return "lesson-form";
    }
    @GetMapping("/addLesson/{moduleId}")
    public String getLessonByModuleId(@PathVariable String moduleId, Model model) {
        UUID modulId = UUID.fromString(moduleId);
        model.addAttribute("moduleId",modulId);
        return "lesson-form-by-module-id";
    }

    @PostMapping
    public String addLesson(@ModelAttribute("lessons") LessonDto lessonDto, Model model){
        String str = lessonService.addLesson(lessonDto);
        model.addAttribute("message",str);
        return "redirect:/lessons";
    }
    @GetMapping("/delete/{id}")
    public String Cascade(@PathVariable String id, Model model){
        UUID id1=UUID.fromString(id);
        String str = lessonService.deleteLesson(id1);
        model.addAttribute("message",str);
//        UUID id2 = UUID.fromString(lessonService.getModuleIdByLessonId(id1));
        return "redirect:/lessons/byModuleId";
    }

    @GetMapping("/search")
    public String searchLesson(@RequestParam String word,Model model){
        List<LessonDto> lessonDtos = lessonService.searchLesson(word);
        return "";
    }

    @GetMapping("/byModuleId/{id}")
    public String getLessonsByModuleId(@PathVariable String id, Model model,
                                       HttpServletRequest request){
        if (id!= null){
        HttpSession moduleSession = request.getSession();
        moduleSession.setAttribute("moduleId",id);
        }
        UUID moduleId =  UUID.fromString(String.valueOf(request.getSession().getAttribute("moduleId")));
        List<LessonDto> moduleDtoList = lessonService.getLessonsByModuleId(moduleId);
        int buttonCount = lessonDao.pageLessonsByModuleId(moduleId);
        model.addAttribute("moduleId",moduleId);
        model.addAttribute("lessonList", moduleDtoList);
        model.addAttribute("buttonCount",buttonCount);
        return "view-lessons-by-module-id";
    }
    @GetMapping("/byModuleId")
    public String getLessonsByModuleId2( Model model,HttpServletRequest request){
        UUID moduleId = UUID.fromString(String.valueOf(request.getSession().getAttribute("moduleId")));
        List<LessonDto> moduleDtoList = lessonService.getLessonsByModuleId(moduleId);
        int buttonCount = lessonDao.pageLessonsByModuleId(moduleId);
        model.addAttribute("moduleId",moduleId);
        model.addAttribute("lessonList", moduleDtoList);
        model.addAttribute("buttonCount",buttonCount);
        return "view-lessons-by-module-id";
    }
    @GetMapping("/addLessonToModule")
    public String addLessonToSelectedModule(@ModelAttribute("lessons") Lesson lesson, Model model){
        if (lesson.getId() != null){
                lessonDao.editLesson(lesson);
                model.addAttribute("msg","Succesfully edited!!!");
        }else {
            if (lessonService.addLessonToModule(lesson) > 0) {
                model.addAttribute("message", "Succesfully added!!!");
            } else {
                model.addAttribute("message", "Could not added!!!");
            }
        }
            return "redirect:/lessons/byModuleId";
    }

    @GetMapping("/addVideo/{lessonId}")
    public String addVideo(@PathVariable UUID lessonId,Model model){
        UUID moduleId = UUID.fromString(lessonService.getModuleIdByLessonId(lessonId));
        model.addAttribute("moduleId",moduleId);
        return "add-video";
    }

    @GetMapping("/addTask/{lessonId}")
    public String addTask(@PathVariable UUID lessonId,Model model){
        UUID moduleId = UUID.fromString(lessonService.getModuleIdByLessonId(lessonId));
        model.addAttribute("moduleId",moduleId);
        return "add-task";
    }

    @PostMapping("/addTask")
    public String addtasksToDb(@ModelAttribute("lessons")Task task,Model model){
        lessonService.addTask(task);
        return "redirect:/lessons/byModuleId/"+task.getModule_id();
    }
    @PostMapping("/addVideo")
    public String addVideoToDb(@ModelAttribute("lessons")Attachment attachment, Model model){
        lessonService.saveVideo(attachment);
        return "redirect:/lessons/byModuleId/"+attachment.getModule_id();
    }
}
