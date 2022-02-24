package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uz.pdp.dto.UserDto;
import uz.pdp.dto.ModuleDto;
import uz.pdp.dto.UserDto;
import uz.pdp.model.Role;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public UserDto getUser(String emailOrTelephoneNumber, String password) {
        String sqlQuery = "select u.email, json_agg(json_build_object('name', r.name, 'id', r.id))\n" +
                "from users u\n" +
                "join users_roles ur on u.id = ur.user_id\n" +
                "join roles r on ur.role_id = r.id\n" +
                "where (email = '"+emailOrTelephoneNumber+"' or" +
                " \"phoneNumber\" = '"+emailOrTelephoneNumber+"')\n" +
                "  and password = '"+password+"'\n" +
                "group by u.email;";

        try {
            return

                    jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> {
                        UserDto userDto = new UserDto();
                        userDto.setEmail(rs.getString(1));
                        Array authors = rs.getArray(2);

                        Type listType = new TypeToken<ArrayList<Role>>() {
                        }.getType();
                        List<Role> roles = new Gson().fromJson(authors.toString(), listType);
                        userDto.setRoles(roles);
                        return userDto;
                    });
        }catch (Exception e){
            return null;
        }



    }

    public String getUserId(String emailOrPhoneNumber, String role) {
        String sqlQuery = "select u.id\n" +
                "from users u\n" +
                "         join users_roles ur on u.id = ur.user_id\n" +
                "         join roles r on ur.role_id = r.id\n" +
                "where (u.email = '"+emailOrPhoneNumber+"'\n" +
                "    or u.\"phoneNumber\" = '"+emailOrPhoneNumber+"')\n" +
                "  AND r.name = '"+role+"';\n";
        try {

        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getString(1));
        }catch (Exception e){
            return null;
        }
    }


//    public int registerUser(UserDto userDto){
//        String query1 = "insert into users(\"firstName\", \"lastName\",\n" +
//                "                  \"phoneNumber\", email,\n" +
//                "                  password, bio)\n" +
//                "VALUES (\n" +
//                "        't', 't',\n" +
//                "        't', 't',\n" +
//                "        't', 't'\n" +
//                "       );";
//        int check = jdbcTemplate.update(query1);
////        if(check>)
//    }

    public String role(UUID uuid) {
        String sqlQuery = "select *\n" +
                "from roles where id = '"+uuid+"';";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getString(2));
    }

    public String roleId(){
        String query = "select *\n" +
                "from roles where name = 'USER';";
        return jdbcTemplate.queryForObject(query, ((rs, rowNum) -> rs.getString((1))));
    }



}
