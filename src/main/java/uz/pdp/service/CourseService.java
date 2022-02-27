package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.pdp.dao.CourseDao;
import uz.pdp.dto.CourseDto;

import java.util.List;
import java.util.UUID;


@Service
@Component
public class CourseService {

    @Autowired
    CourseDao courseDao;

    public List<CourseDto> getAllCourses(Integer interval, Integer currentPage, String search) {
        List<CourseDto> allCourses = courseDao.getAllCourses(interval, currentPage, search);
        return allCourses;
    }


    public List<CourseDto> getAllCoursesByPage(int interval, int currentPage, String text, String condition) {
        List<CourseDto> allCourses = courseDao.getAllCoursesByPage(interval, currentPage, condition, text);
        return allCourses;
    }

    public int courseSize(String text, String condition) {
        if (text != null) {
            int size = courseDao.getCourseCountBySearch(text);
            if(size==0)
            return 0;
            return size;
        }
        if (condition.equals("main")) {
            return courseDao.getCourseCountByPage();
        } else if (condition.equals("true") || condition.equals("false")) {
            boolean type = Boolean.parseBoolean(condition);
            return courseDao.getCourseCountByType(type);
        }
        return 0;
    }




    public String addCourse(CourseDto courseDto) {
        if (courseDto.getId() != null) {
            if (courseDao.editCourse(courseDto) != 0) {
                return "Successfully edited!";
            } else {
                return "Could not edited!";
            }
        } else {
            if (!courseDao.addCourse(courseDto).equals(null)) {
                return "Successfully added!";
            } else {
                return "Could not added!";
            }
        }
    }

    public String deleteCourse(UUID id) {
        if (courseDao.deleteCourse(id) == 0) {
            return "Successfully deleted!";
        } else {
            return "Could not deleted!";
        }
    }

    public CourseDto getCourseById(UUID id) {
        CourseDto courseDto = courseDao.getCourseById(id);
        return courseDto;
    }

    public int getAllCourseTableCount(String search){
        if(search!=null){
            return  courseDao.getCourseCountBySearch(search);
        }
        return courseDao.getCourseCountByType();
    }


}
