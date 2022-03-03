package uz.pdp.controller;

import com.sun.deploy.net.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.dao.CourseDao;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;
import uz.pdp.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("userPanel")
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
    public String getModuleLesson(@PathVariable String id, Model model){


        return "my-modules";
    }
}
