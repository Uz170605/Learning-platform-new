package uz.pdp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.dao.CourseDao;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.UserDto;

import java.util.List;

@Controller
@RequestMapping("/openHomePage")
public class IndexController {

    @Autowired
    UserDao userDao;
    @Autowired
    CourseDao courseDao;


    @GetMapping
    public String getAllData(Model model){
        List<UserDto> allMentors = userDao.getAllMentors();
        model.addAttribute("mentorList",allMentors);
        return "index";
    }


}
