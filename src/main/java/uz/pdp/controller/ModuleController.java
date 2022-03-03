package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dao.ModuleDao;
import uz.pdp.dto.MentorCourseDto;
import uz.pdp.dto.UserDto;
import uz.pdp.service.LoginService;
import uz.pdp.service.ModuleService;
import uz.pdp.service.UserService;

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
    LoginService loginService;
    @Autowired
    UserService userService;
    @Autowired
    ModuleDao moduleDao;
    static String role = "MENTOR";

    @GetMapping("/courses_modules")
    public String getAllModules(@RequestParam(required = false, name = "courseId") String courseId,
                                Model model, HttpServletRequest request) {
        UUID userUUID = loginService.sessionGetEmail(request, role);
        if (userUUID == null) {
            return "/login";
        }
        String id = courseId;
        HttpSession session = request.getSession();
        if(courseId!=null){
       session.setAttribute("courseId",courseId);
        }else id = (String) session.getAttribute("courseId");

        List<MentorCourseDto> getAllModules =
                moduleService.getAllModulesFromDb(UUID.fromString(id),userUUID );
        model.addAttribute("moduleList", getAllModules);
        return "view-modules";
    }

    @GetMapping("select_mentor")
    public String getAllMentor(Model model) {
        List<UserDto> allMentors = userService.getAllMentors();
        model.addAttribute("authors", allMentors);
        return "add-module";

    }


    @PostMapping("/addModule")
    public String addModuleAndLesson(@ModelAttribute("modules")
                                             MentorCourseDto mentorCourseDto,
                                     HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            //  String courseId= "63efece5-3fef-433d-a164-d64c7b357135";
            String courseId = (String) session.getAttribute("courseId");
            moduleService.addAndEditMethod(mentorCourseDto, UUID.fromString(courseId));
            return "redirect:/modules/courses_modules";
        }
        return "redirect:/modules/select_mentor";
    }


    @GetMapping("/editModule/{id}")
    public String editModule(@PathVariable("id") UUID id, Model model) {
        MentorCourseDto mentorCourseDto = moduleDao.editModuleAndLesson(id);
        model.addAttribute("module", mentorCourseDto);
        return "edit-modules";
    }


    @PostMapping("/editModule")
    public String editModule(@ModelAttribute("module") MentorCourseDto mentorCourseDto) {
        moduleDao.editModuleMentor(mentorCourseDto);
        return "redirect:/modules/courses_modules";
    }


    @GetMapping("/delete/{id}")
    public String deleteModule(@PathVariable("id") UUID id) {
        moduleDao.deleteMentorModule(id);
        return "redirect:/modules/courses_modules";
    }


    @GetMapping("/module_message/{id}")
    public String sendMessageInModule(@PathVariable UUID id, HttpServletRequest request,
                                      Model model,
                                      @RequestParam(required = false,name = "message")String message) {
        UUID userUUID = loginService.sessionGetEmail(request, role);
        if (userUUID == null) {
            return "/login";
        }
        else {
            if(message==null){
                return "mentor-send-module-message-admin";
            }
            int dsck = moduleDao.sendMessage(userUUID, message, id);
            model.addAttribute("message",dsck);
            return "redirect:/modules/courses_modules";
        }
    }
}
