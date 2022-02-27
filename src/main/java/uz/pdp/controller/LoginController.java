package uz.pdp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;
import uz.pdp.service.LoginService;

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
                List<UserDto> allMentors = userDao.getAllMentors();
                model.addAttribute("mentorList",allMentors);
                return "user";
            }
        }
        model.addAttribute("error", "password or email invalid");
        return "login";
    }

    @PostMapping
    public String saveUser(UserDto userDto,Model model){
        int check = loginService.userRegister(userDto);
        if(check==0){
            List<UserDto> allMentors = userDao.getAllMentors();
            model.addAttribute("mentorList",allMentors);
            return "user";
        }

        model.addAttribute("error","There is a problem with the registration. Please try again");
        return "register";
    }

    @GetMapping("/roleController/{roleId}")
    public String role(@PathVariable(required = false) UUID roleId,Model model) {
        String role = loginService.role(roleId);
        if (role.equals("MENTOR")) {

        } else if (role.equals("ADMIN")) {
            return "redirect:/admin";
        } else if (role.equals("SUPERADMIN")) {

        }
        List<UserDto> allMentors = userDao.getAllMentors();
        model.addAttribute("mentorList",allMentors);
        return "user";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/openHomePage";
    }
}
