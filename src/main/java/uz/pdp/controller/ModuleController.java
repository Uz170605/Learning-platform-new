package uz.pdp.controller;

import org.apache.catalina.Session;
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
import javax.servlet.http.HttpSession;
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
            defaultValue = "0") Integer page, Model model, HttpServletRequest request) {
        UUID uuid = loginService.sessionGetEmail(request, role);
        if (uuid == null) return "login";
        int countPage = moduleService.countPage();
        model.addAttribute("page", countPage);
        List<ModuleDto> allModules = moduleDao.getModuleAndLesson(search, page);
        model.addAttribute("moduleList", allModules);
        return "view-modules";
    }

    @GetMapping("courses_modules")
    public String getAllModulesByCourses(@RequestParam(required = false, name = "courseId") String courseId,
                                         Model model, HttpServletRequest request) {

        UUID uuid = loginService.sessionGetEmail(request, role);
        if (uuid == null) return "login";

        HttpSession session = request.getSession();
        session.setAttribute("courseId", courseId);
        session.getMaxInactiveInterval();

        List<ModuleDto> allModulesByCourses =
                moduleDao.getModuleByCourseId(UUID.fromString(courseId));
        UUID courseIdd = UUID.fromString(courseId);
        CourseDto courseById = courseService.getCourseById(courseIdd);
        model.addAttribute("courseList", courseById);
        model.addAttribute("courseAndModule", allModulesByCourses);
        return "view-modules";
    }


    @GetMapping("{id}")
    public String getModuleById(@PathVariable(required = false) String id, Model model) {
        UUID uuid = UUID.fromString(id);
        ModuleDto moduleDto = moduleService.getAllModules(uuid);
        List<CourseDto> allCourse = courseService.getAllCourses(null, null, null);
          LessonDto allLessons = lessonService.getLessonById(uuid);
        model.addAttribute("authors", userService.getAllMentors());
        model.addAttribute("courseList", allCourse);
        model.addAttribute("selectModule", moduleDto);
        model.addAttribute("selectLesson",allLessons);
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
                            HttpServletRequest request ) {
        UUID uuid = loginService.sessionGetEmail(request, role);
        if (uuid == null) return "login";
        HttpSession session1 = request.getSession(false);
        if (session1 != null) {
            String courseId = (String) session1.getAttribute("courseId");
        String modules = moduleService.addModulesAndLesson(moduleDto,
                UUID.fromString(courseId));
        model.addAttribute("message", modules);
        }
        List<LessonDto> lessons = lessonService.getAllLessons();
        model.addAttribute("messageLessons", lessons);
        model.addAttribute("messageUser", userService.getAllMentors());
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

    @GetMapping("module_message")
    public String sendModuleByAdmin(@PathVariable UUID moduleUuid, Model model,
                                    @RequestParam(required = false, name = "message") String message,
                                    HttpServletRequest request, @RequestParam UUID courseId) {
        UUID userId = loginService.sessionGetEmail(request, role);
        if (userId == null) {
            return "login";
        }
        if (message == null) {
            model.addAttribute("moduleId", moduleUuid);
            return "mentor-send-message-admin";
        } else {
            moduleDao.sendMessage(moduleUuid, message, userId, courseId);
            return "redirect:/modules/courses_modules";
        }
    }
}
