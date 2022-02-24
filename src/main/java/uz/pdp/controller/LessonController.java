package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dao.LessonDao;
import uz.pdp.dao.ModuleDao;
import uz.pdp.dto.LessonDto;
import uz.pdp.service.LessonService;
import uz.pdp.service.UserService;

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
        model.addAttribute("modules",moduleDao.getAllModules());
        model.addAttribute("selectLesson",lessonById);
        return "lesson-form";
    }
    @GetMapping("/addLesson")
    public String getLesson(Model model){
        model.addAttribute("modules",moduleDao.getAllModules());
        return "lesson-form";
    }

    @PostMapping
    public String addLesson(@ModelAttribute("lessons") LessonDto lessonDto, Model model){
        String str = lessonService.addLesson(lessonDto);
        model.addAttribute("message",str);
        return "redirect:/lessons";
    }

    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable String id, Model model){
       UUID id1=UUID.fromString(id);
        String str = lessonService.deleteLesson(id1);
        model.addAttribute("message",str);
        return "redirect:/lessons";
    }

    @GetMapping("/search")
    public String searchLesson(@RequestParam String word,Model model){
        List<LessonDto> lessonDtos = lessonService.searchLesson(word);
        return "";
    }

}
