package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.dao.CourseDao;
import uz.pdp.dao.MentorDao;
import uz.pdp.dao.ModuleDao;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.ModuleDto;

import java.util.List;
import java.util.UUID;

@Service
public class MentorService {
    @Autowired
    MentorDao mentorDao;

}
