package uz.pdp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.dao.ModuleDao;
import uz.pdp.dto.MentorCourseDto;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleService {
@Autowired
ModuleDao moduleDao;
    public List<MentorCourseDto> getAllModulesFromDb(UUID courseId, UUID authorId) {
        List<MentorCourseDto> moduleDtoList=moduleDao.getAllModuleFromDb(courseId, authorId);
        return moduleDtoList;
    }

    public String addAndEditMethod(MentorCourseDto mentorCourseDto, UUID courseId) {
        if(mentorCourseDto.getCourseId()!=null){
            if(moduleDao.editModuleAndLesson(courseId)!=null){
                return "Successfully edited ";
            }else return "Unsuccessfully edited";
        }
        if (moduleDao.addModuleAndLesson(mentorCourseDto,courseId)!=0){
            return "Successfully added";
        }else return "Unsuccessfully added";


    }
}
