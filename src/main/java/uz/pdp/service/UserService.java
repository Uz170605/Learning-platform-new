package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;

import java.util.List;
import java.util.UUID;


public class UserService {

    @Autowired
    UserDao userDao;


    public List<UserDto> getAllMentors() {
        List<UserDto> allMentors = userDao.getAllMentors();
        return allMentors;
    }

    public UserDto getMentorById(UUID id) {
       return userDao.getMentorById(id);
    }

    public List<UserDto> getAllUsers(int interval, int currentPage, String text) {
      return   userDao.getAllUsers(interval, currentPage, text);
    }

    public List<Role> getUserRole() {
      return   userDao.getUserRole();
    }

    public UserDto getUserById(UUID id) {
        return userDao.getUserById(id);
    }



    public String saveUser(UserDto userDto) {
        if (userDto.getId() != null) {
            if (userDao.editUser(userDto) != 0) {
                return "Successfuly edited!";
            } else {
                return "Could not edited!";
            }
        } else {
            if (userDao.addUser(userDto) !=0) {
                return "Successfuly added!";
            } else {
                return "Could not added!";
            }
        }
    }

    public String deleteUser(UUID userId) {
        if (userDao.deleteUser(userId) == 0) {
            return "Successfuly deleted!";
        } else {
            return "Could not deleted!";
        }
    }

    public int userCountByPageable(String text) {

        return userDao.getUserCount(text);
    }
}
