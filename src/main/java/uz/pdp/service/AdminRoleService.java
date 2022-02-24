package uz.pdp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.UserDto;

import java.util.UUID;

@Service
@Component
public class AdminRoleService {
    @Autowired
    UserDao userDao;

    public UserDto getUserById(UUID uuid){
        return userDao.getUserById(uuid);
    }
}
