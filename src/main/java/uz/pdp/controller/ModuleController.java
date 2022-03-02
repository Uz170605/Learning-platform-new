package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dao.ModuleDao;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.LessonDto;
import uz.pdp.dto.MentorCourseDto;
import uz.pdp.dto.ModuleDto;
import uz.pdp.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/modules")
public class ModuleController {
    @Autowired
    ModuleService moduleService;
    @Autowired
    ModuleDao moduleDao;
    @Autowired
    CourseService courseService;
    @Autowired
    LessonService lessonService;
    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;

    static String role = "MENTOR";

    @GetMapping
    public String getAllModules(@RequestParam(name = "search", required = false,
            defaultValue = "") String search, @RequestParam(name = "page",
            required = false,
            defaultValue = "0") Integer page, Model model,HttpServletRequest request) {
        UUID uuid = loginService.sessionGetEmail(request, role);
        if (uuid == null) return "login";
        int countPage = moduleService.countPage();
        model.addAttribute("page", countPage);
        List<ModuleDto> allModules = moduleDao.getModuleAndLesson(search, page);
        model.addAttribute("moduleList", allModules);
        return "view-modules";
    }

    @GetMapping("{id}")
    public String getModuleById(@PathVariable(required = false) String id, Model model) {
        UUID uuid = UUID.fromString(id);
        ModuleDto moduleDto = moduleService.getAllModules(uuid);
        List<CourseDto> allCourse = courseService.getAllCourses(null, null, null);
      //  LessonDto allLessons = lessonService.getLessonById(uuid);
        model.addAttribute("authors", userService.getAllMentors());
        model.addAttribute("courseList", allCourse);
        model.addAttribute("selectModule", moduleDto);
        return "module-form";
    }

    @GetMapping("/addModule")
    public String getModule(@ModelAttribute("module") ModuleDto moduleDto, Model model) {
        List<CourseDto> allCourses = courseService.getAllCourses(null, null, null);
        model.addAttribute("authors", userService.getAllMentors());
        model.addAttribute("courseList", allCourses);
        return "module-form";
    }

    @PostMapping
    public String addModule(@ModelAttribute("modules") MentorCourseDto moduleDto, Model model,
                            HttpServletRequest request) {
        UUID uuid = loginService.sessionGetEmail(request, role);


        if (uuid == null) return "login";
        String modules = moduleService.addModulesAndLesson(moduleDto, uuid);
        List<LessonDto> lessons=lessonService.getAllLessons();
        model.addAttribute("message",lessons);
        model.addAttribute("message",userService.getAllMentors());
        model.addAttribute("message", modules);
        return "redirect:/modules";
    }

    @GetMapping("/delete/{id}")
    public String deleteModule(@PathVariable String id, Model model) {
        UUID uuid = UUID.fromString(id);
        String str = moduleService.delete(uuid);
        model.addAttribute("message", str);
        return "redirect:/modules";
    }

    @GetMapping("moduleAllData/{id}")
    public String getModuleBYID(@PathVariable(required = false) String id, Model model) {
        UUID uuid = UUID.fromString(id);
        ModuleDto moduleDto = moduleService.getAllModules(uuid);
        model.addAttribute("selectModule", moduleDto);
        return "view-select-module";
    }
}
