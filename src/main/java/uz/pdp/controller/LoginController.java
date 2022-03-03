package uz.pdp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import uz.pdp.dao.CourseDao;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;
import uz.pdp.service.LoginService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    UserDao userDao;
    @Autowired
     CourseDao courseDao;

    @GetMapping
    public String login(
            HttpServletRequest request,
            Model model,
            @RequestParam(required = false, name = "emailOrPhoneNumber") String emailOrPhoneNumber,
            @RequestParam(required = false, name = "password") String password
    ) {

        UserDto userDto = loginService.user(emailOrPhoneNumber, password);
        if (userDto != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", userDto.getEmail());
            session.getMaxInactiveInterval();
            List<Role> roles = userDto.getRoles();
            if (roles.size() > 1) {
                model.addAttribute("roles", roles);
                return "selectRole";
            }else{
                return "redirect:/userPanel";
            }
        }
        model.addAttribute("error", "password or email invalid");
        return "login";
    }

    @PostMapping
    public String saveUser(UserDto userDto, Model model,  HttpServletRequest request){
        int check = loginService.userRegister(userDto);
        if(check==0){
            HttpSession session = request.getSession();
            session.setAttribute("username", userDto.getEmail());
            session.getMaxInactiveInterval();
            return "redirect:/userPanel";
        }
        model.addAttribute("error","There is a problem with the registration. Please try again");
        return "register";
    }

    @GetMapping("/roleController/{roleId}")
    public String role(@PathVariable(required = false) UUID roleId,Model model) {
        String role = loginService.role(roleId);
        if (role.equals("MENTOR")) {
            return "redirect:/mentors";
        } else if (role.equals("ADMIN")) {
            return "redirect:/admin";
        } else if (role.equals("SUPERADMIN")) {

        }
        return "redirect:/userPanel";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/openHomePage";
    }
}
