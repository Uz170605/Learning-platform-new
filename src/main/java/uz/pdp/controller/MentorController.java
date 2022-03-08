package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;
import uz.pdp.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/mentors")
public class MentorController {
    @Autowired
    LoginService loginService;
    @Autowired
    UserDao userDao;

    static String role = "MENTOR";

    @GetMapping
    public String test(Model model,HttpServletRequest request) {
        UUID uuid = loginService.sessionGetEmail(request, role);
        if (uuid == null) {
            return "login";
        }
        UserDto userById = userDao.getMentorById(uuid);
        List<Role> userRole = userDao.getUserRole();
        UUID roleId=null;
        for (Role role1 : userRole) {
            if (role1.getName().equals("MENTOR")) {
                roleId=role1.getId();
                break;
            }
        }
        if (roleId != null) {
            model.addAttribute("roleId",roleId);
        }
        return "mentor";
    }
}
