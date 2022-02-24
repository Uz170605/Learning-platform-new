package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminRoleController {
    @Autowired
    LoginService loginService;
    static String role = "ADMIN";

    @GetMapping
    public String test(
            Model model,
            HttpServletRequest request) {
        UUID uuid = loginService.sessionGetEmail(request, role);

        if (uuid == null) {
            model.addAttribute("firstPassword", "Enter the password first");
            return "/login";
        }
        return "redirect:/courses/test";
    }



    @GetMapping("/dasfdsf")
    public String test1(HttpServletRequest request) {
        UUID uuid = loginService.sessionGetEmail(request, role);
        if(uuid!=null){

        }
        return null;
    }
}
