package uz.pdp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.dao.CourseDao;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.CourseAllData;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.ModuleDto;
import uz.pdp.dto.UserDto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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
        List<CourseDto> allCourseForIndex = courseDao.getAllCourseForIndex();
        model.addAttribute("courseList",allCourseForIndex);
        model.addAttribute("mentorList",allMentors);
        return "index";
    }

    @GetMapping("/selectCourse/{id}")
    public String getCourseAllData(@PathVariable(required = false) String id,Model model){
        if (id != null && id.length()==36) {
        CourseAllData courseAllData = courseDao.getCourseAllData(UUID.fromString(id));
            List<UserDto> courseAuthors = userDao.getCourseAuthors(id);
            courseAllData.setAuthorList(courseAuthors);
            List<ModuleDto> module = courseDao.getModule(UUID.fromString(id));
            courseAllData.setModuleList(module);
            Double coursePrice = (double) 0;
            for (ModuleDto moduleDto : module) {
                coursePrice+=moduleDto.getPrice();
            }
            courseAllData.setPrice(coursePrice);
            model.addAttribute("course", courseAllData);
        return "select-course";
        }
         return "redirect:/openHomePage";

    }

}
