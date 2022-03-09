package uz.pdp.controller;

//import com.sun.deploy.net.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.pdp.dao.CourseDao;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.ModuleDto;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;
import uz.pdp.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;



@Controller
@RequestMapping("/userPanel")
public class UserRoleController {
    @Autowired
    UserDao userDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    LoginService loginService;

    @GetMapping
    public String getUserData(Model model, HttpServletRequest request){
        UUID userId = loginService.sessionGetEmail(request, "USER");
        if (userId == null) return "login";
        List<UserDto> allMentors = userDao.getAllMentors();
        model.addAttribute("mentorList",allMentors);
        List<CourseDto> allCourseForIndex = courseDao.getAllCourseForIndex();
        model.addAttribute("courseList",allCourseForIndex);
        List<Role> userRole = userDao.getUserRole();
        UUID roleId=null;
        for (Role role1 : userRole) {
            if (role1.getName().equals("USER")) {
                roleId=role1.getId();
                break;
            }
        }
        if (roleId != null) {
            model.addAttribute("roleId",roleId);
        }
        return "user";
    }


    @GetMapping("/my-courses")
    public String getMyCourse(Model model, HttpServletRequest request){
        UUID userId = loginService.sessionGetEmail(request, "USER");
        if (userId != null){
            List<Role> userRole = userDao.getUserRole();
            UUID roleId=null;
            for (Role role1 : userRole) {
                if (role1.getName().equals("USER")) {
                    roleId=role1.getId();
                    break;
                }
            }
            if (roleId != null) {
                model.addAttribute("roleId",roleId);
            }
          List<CourseDto> courseList = userDao.getMyCourse(userId);
          model.addAttribute("courseList",courseList);
          return "my-courses";
        } else {
            return "login";
        }

    }
    @GetMapping("/view-modules/{id}")
    public String getModuleLesson(@PathVariable(required = false) UUID id, Model model){
       if (id == null) return "redirect:/userPanel/my-courses";
        List<Role> userRole = userDao.getUserRole();
        UUID roleId=null;
        for (Role role1 : userRole) {
            if (role1.getName().equals("USER")) {
                roleId=role1.getId();
                break;
            }
        }
        if (roleId != null) {
            model.addAttribute("roleId",roleId);
        }
        ModuleDto moduleDto=userDao.getModuleAllData(id);
        model.addAttribute("modules",moduleDto);
        return "my-modules";

    }

    @GetMapping("/selectLessonVideo")
    public String getLessonVideo(@RequestParam("attachmentId") UUID id,@RequestParam("moduleId") UUID moduleId, RedirectAttributes redirectAttributes){
        String lessonVideo = userDao.getLessonVideo(id);
        String lessonTitle = userDao.getLessonTitle(id);
        redirectAttributes.addFlashAttribute("lessonVideo",lessonVideo);
        redirectAttributes.addFlashAttribute("lessonTitle",lessonTitle);
        return "redirect:/userPanel/view-modules/"+moduleId;
    }
}
