package uz.pdp.service;


import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.dao.ModuleDao;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.MentorCourseDto;
import uz.pdp.dto.ModuleDto;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleService {
    @Autowired
    ModuleDao modelDao;


    public List<ModuleDto> getModuleFromDb(Integer page, String search){
        List<ModuleDto> moduleDtoList=modelDao.getModuleFromDb(page,search);
        return moduleDtoList;
    }
    public List<ModuleDto> getAllModules() {
        List<ModuleDto> moduleDtoList = modelDao.getAllModules();
        return moduleDtoList;
    }

    public String addModulesAndLesson(MentorCourseDto moduleDto, UUID uuid) {
        if (moduleDto.getCourseId()!= null) {
            if (modelDao.editModule(moduleDto) != 0) {
                return "Successfully edited";
            } else {
                return "Could not edited!";
            }
        }
        else {
            if (modelDao.addModuleAndLesson(moduleDto, uuid) != 0) {
                return "Successfully added!";
            } else {
                return "Could not added!";
            }
        }
    }
    public String delete(UUID uuid){
        if(modelDao.deleteModule(uuid ).equals('0')){
            return "Successfully delete";
        }else return "Could not  delete";
    }
    public ModuleDto getAllModules(UUID uuid){
        ModuleDto moduleDto=modelDao.getModuleById(uuid);
        return moduleDto;
    }
    public ModuleDto viewModuleByPage(int startPage,int totalPage){

        return (ModuleDto) modelDao.viewModuleBYPage(startPage,totalPage);
    }


    public int  countPage() {
        int count=0;
        count=modelDao.getCountPage();
        return count;
    }
}
