package uz.pdp.dao;


import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.dto.CourseDto;
import uz.pdp.dto.ModuleDto;

import java.lang.reflect.Type;
import java.util.*;

@Component
public class ModuleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ModuleDto> getAllModules() {
        String sql = "select id,name,is_active,price from modules;";
        List<ModuleDto> moduleDtoList = jdbcTemplate.query(sql, (rs, row) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(2));
            moduleDto.setActive(rs.getBoolean(3));
            moduleDto.setPrice(rs.getDouble(4));
            return moduleDto;
        });
        return moduleDtoList;
    }

    public ModuleDto getModuleById(UUID uuid) {
        String sql = "select * from modules where id='" + uuid + "'";
        return jdbcTemplate.queryForObject(sql, (rs, row) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(3));
            moduleDto.setActive(rs.getBoolean(2));
            moduleDto.setCourseId(UUID.fromString(rs.getString(5)));
            moduleDto.setPrice(rs.getDouble(4));
            return moduleDto;
        });
    }

    public int addModule(ModuleDto moduleDto) {
        String sql = "insert into modules(name, is_active, course_id, price) VALUES (" +
                "        '" + moduleDto.getName() + "'," + moduleDto.isActive() + ",'" + moduleDto.getCourseId() + "'," + moduleDto.getPrice() +
                " )";
        return jdbcTemplate.update(sql);
    }

    public int deleteModule(UUID uuid) {
        String sql = "delete from modules where id='" + uuid + "'";

        return jdbcTemplate.update(sql);
    }

    public int editModule(ModuleDto moduleDto) {
        String sql =
                "update modules set name='" + moduleDto.getName() + "',is_active=" + moduleDto.isActive() + "," +
                        "course_id='" + moduleDto.getCourseId() + "'," +
                        "price=" + moduleDto.getPrice() + " where id='" + moduleDto.getId() + "';";
        return jdbcTemplate.update(sql);
    }

    public List<ModuleDto> viewModuleBYPage(int startPage, int totalPage) {
        String sql = "select * from modules limit " + (startPage - 1) + " offset " + totalPage + "";
        return jdbcTemplate.query(sql, (rs, row) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(3));
            moduleDto.setPrice(rs.getDouble(4));
            moduleDto.setActive(rs.getBoolean(2));
            return moduleDto;
        });
    }

    public int getCountPage() {
        int count = 0;
        String sql = "select count (*) from modules";
        count = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                rs.getInt(1));
        if (count % 3 == 0) {
            count = count / 3;
        } else count = count / 3 + 1;
        return count;
    }

    public List<ModuleDto> getModuleFromDb(Integer page, String search) {
        String sql =
                "select id,name,is_active,price from modules where name like '%"+search+"%' " +
                        "limit 3 " +
                        "offset " + page;
        List<ModuleDto> moduleDtoList = jdbcTemplate.query(sql, (rs, row) -> {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(UUID.fromString(rs.getString(1)));
            moduleDto.setName(rs.getString(2));
            moduleDto.setActive(rs.getBoolean(3));
            moduleDto.setPrice(rs.getDouble(4));
            return moduleDto;
        });
        return moduleDtoList;
    }
}
