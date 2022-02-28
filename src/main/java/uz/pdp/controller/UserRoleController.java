package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.service.LoginService;
import uz.pdp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("/userPanel")
public class UserRoleController {
    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;

    @GetMapping
    public String loginUser (HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        UUID userId = loginService.sessionGetEmail(request, "USER");
        if (userId != null){
            model.addAttribute("userId",userId);
            return "user-cabinet";
        }else{
            model.addAttribute("firstPassword","Please login first");
            return "login";
        }
    }

    @GetMapping("/myCourses/{id}")
    public String getUserCourses(@PathVariable String id, Model model){
        UUID userId = UUID.fromString(id);

        return "user-panel";
    }
}
