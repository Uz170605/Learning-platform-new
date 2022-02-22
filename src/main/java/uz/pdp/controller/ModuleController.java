package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.ModuleDto;
import uz.pdp.service.CourseService;
import uz.pdp.service.ModuleService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/modules")
public class ModuleController {
    @Autowired
    ModuleService moduleService;
    @Autowired
    CourseService courseService;

    @GetMapping
    public String getAllModules(@RequestParam(name = "search", required = false,
    defaultValue = "") String search, @RequestParam(name = "page",
            required = false,
            defaultValue = "0") Integer page,Model model) {
        int countPage = moduleService.countPage();
        model.addAttribute("page", countPage);
        List<ModuleDto> allModules = moduleService.getModuleFromDb(page,search);
        model.addAttribute("moduleList", allModules);
        return "view-modules";
    }

    @GetMapping("{id}")
    public String getModuleById(@PathVariable(required = false) String id, Model model) {
        UUID uuid = UUID.fromString(id);
        ModuleDto module=moduleService.getAllModules(uuid);
        List<CourseDto> allCourses = courseService.getAllCourses(null, null, null);  // TODO: 2/20/2022 add null value
        model.addAttribute("courseList",allCourses);
        model.addAttribute("selectModule",module);
        return "module-form";
    }
    @GetMapping(    "/addModule")
    public String getModule(@ModelAttribute("module" ) ModuleDto moduleDto,Model model){
        List<CourseDto> allCourses = courseService.getAllCourses(null, null, null); // TODO: 2/20/2022 add null value


        model.addAttribute("courseList", allCourses);

        return "module-form";
    }
//
//    @GetMapping("/addModule")
//    public String getModule(@ModelAttribute("module") ModuleDto moduleDto, Model model) {
//        List<CourseDto> allCourses = courseService.getAllCourses();
//>>>>>>> 9639ba46f5b568f6d6504c5014a0c5c0c43e89a4
//
//        model.addAttribute("courseList", allCourses);
//        return "module-form";
//    }

    @PostMapping
    public String addModule(@ModelAttribute("modules") ModuleDto moduleDto, Model model) {
        String s = moduleService.addModules(moduleDto);
        model.addAttribute("message", s);
        return "redirect:/modules";
    }

    @DeleteMapping("/{id}")
    public String deleteModule(@PathVariable String id, Model model) {
        UUID uuid = UUID.fromString(id);
        String str = moduleService.delete(uuid);
        model.addAttribute("message", str);
        return "redirect:/modules";
    }

    @GetMapping("moduleAllData/{id}")
    public String getModuleBYID(@PathVariable(required = false) String id, Model model) {
        UUID uuid = UUID.fromString(id);
        ModuleDto moduleDto = moduleService.getAllModules(uuid);
        model.addAttribute("selectModule", moduleDto);
        return "view-select-module";
    }
}
