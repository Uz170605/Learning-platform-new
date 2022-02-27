
package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.dao.AdminDao;
import uz.pdp.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminRoleController {
    @Autowired
    LoginService loginService;

    @Autowired
    AdminDao adminDao;
    static String role = "ADMIN";

    @GetMapping
    public String mainPage(
            Model model,
            HttpServletRequest request) {
        UUID uuid = loginService.sessionGetEmail(request, role);

        if (uuid == null) {
            model.addAttribute("firstPassword", "Enter the password first");
            return "/login";
        }
        return "admin-panel";
    }

    @GetMapping("/messages")
    public String messages(HttpServletRequest request, Model model) {
        UUID uuid = loginService.sessionGetEmail(request, role);

        if (uuid == null) {
            model.addAttribute("firstPassword", "Enter the password first");
            return "/login";
        }
        model.addAttribute("all", "all");
        model.addAttribute("messages", adminDao.getAllMessage());
        return "admin-answer-mentor";
    }

    @GetMapping( "/answer")
    public String answer(
            @RequestParam(required = false, name = "messageId") String messageId,
                         Model model
    ){
        model.addAttribute("all", "select");
        model.addAttribute("messages", adminDao.getMessageById(UUID.fromString(messageId)));
        return "admin-answer-mentor";
    }

    @GetMapping("/accept")
    public String accept( @RequestParam(required = false, name = "messageId") String messageId){
        UUID uuid = UUID.fromString(messageId);
        adminDao.acceptCourse(uuid);
        return "admin-panel";
    }

    @GetMapping("/reject")
    public String reject( @RequestParam(required = false, name = "messageId") String messageId){
        UUID uuid = UUID.fromString(messageId);
        adminDao.rejectedCourse(uuid);
        return "admin-panel";
    }

    @GetMapping("/view")
    public String view( @RequestParam(required = false, name = "messageId") String messageId,
                        Model model){
        UUID uuid = UUID.fromString(messageId);
        model.addAttribute("course", adminDao.getCourseById(uuid));

        return "view-selected-course";
    }

}
