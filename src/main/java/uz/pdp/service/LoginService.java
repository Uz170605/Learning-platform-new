
package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.pdp.dao.LoginDao;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Component
public class LoginService {
    @Autowired
    LoginDao loginDao;

    @Autowired
    UserDao userDao;

    public UserDto user(String emailOrPhoneNumber, String password) {
        return loginDao.getUser(emailOrPhoneNumber, password);
    }

    public int userRegister(UserDto userDto) {
        if (userDto == null) return 1;
        UUID[]roles = {UUID.fromString(loginDao.roleId())};


        userDto.setRole(roles);
        return userDao.addUser(userDto);
    }

    public UUID sessionGetEmail(HttpServletRequest request, String role) {
        HttpSession session1 = request.getSession();
        if (session1 != null) {
            String username = String.valueOf(session1.getAttribute("username"));
            if(username.equals("null"))
              return null;
            try{

            return UUID.fromString(loginDao.getUserId(username, role));
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }

    public UUID checked(Model model, HttpServletRequest request, String role){
        UUID uuid = sessionGetEmail(request, role);
        if(uuid == null){
            model.addAttribute("error", "password or email invalid");
        }
        return uuid;
    }

    public String role(UUID uuid) {
        return loginDao.role(uuid);
    }
}
