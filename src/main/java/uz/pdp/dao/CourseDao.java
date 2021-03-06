package uz.pdp.dao;

import com.google.gson.Gson;


import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uz.pdp.dto.*;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CourseDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<CourseDto> getAllCourses(Integer interval, Integer currentPage, String search) { // TODO: 2/20/2022 add checked and pagabel
        String sqlQuery = "";
        if (search != null) {
            sqlQuery = "select * from get_all_courses_by_pageable_and_search('" + search + "', " + interval + ", " + currentPage + ")";
        } else if (interval == null && currentPage == null) {
            sqlQuery = "select *\n" + "from get_course_by_user_and_module();";
        } else {
            sqlQuery = "select * from get_course_by_user_and_module(" + interval + ", " + currentPage + ")";
        }
        List<CourseDto> courseDtoListFromDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(UUID.fromString(rs.getString(1)));
            courseDto.setName(rs.getString(2));
            courseDto.setDescription(rs.getString(3));
            courseDto.setActive(rs.getBoolean(4));
            Array authors = rs.getArray(5);

            Type listType = new TypeToken<ArrayList<UserDto>>() {
            }.getType();
            List<UserDto> authorList = new Gson().fromJson(authors.toString(), listType);
            courseDto.setAuthors(authorList);
            if (search == null) {
                Array module = rs.getArray(6);

                Type type = new TypeToken<ArrayList<ModuleDto>>() {
                }.getType();
                List<ModuleDto> moduleDtoList = new Gson().fromJson(module.toString(), type);
                courseDto.setModule(moduleDtoList);
            }
            return courseDto;
        });
        return courseDtoListFromDb;
    }

//    public String addCourse(CourseDto courseDto) {
//        String sqlQuery = "Insert into courses(name,is_active,description) values('" + courseDto.getName() + "'," + courseDto.isActive() + ",'" + courseDto.getDescription() + "') returning id";
//        String idStr = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> rs.getString("id"));
//        UUID uuid = UUID.fromString(Objects.requireNonNull(idStr));
//        int res = 0;
//        for (UUID uuid1 : courseDto.getAuthorsId()) {
//            res = jdbcTemplate.update("INSERT INTO authors_courses values ('" + uuid1 + "','" + uuid + "');");
//
//        }
//        return res;
//        return idStr;
//    }

    public int deleteCourse(UUID id) {
        String sqlQuery1 = "Delete from authors_modules where module_id='" + id + "'";
        int res = jdbcTemplate.update(sqlQuery1);
        String sqlQuery = "Delete from courses where id ='" + id + "'";
        int res1 = jdbcTemplate.update(sqlQuery);
        return res - res1;
    }

    public CourseDto getCourseById(UUID id) {
        String sqlQuery = "select * from get_course_by_user_and_module() where id='" + id + "'";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(UUID.fromString(rs.getString(1)));
            courseDto.setName(rs.getString(2));
            courseDto.setDescription(rs.getString(3));
            courseDto.setActive(rs.getBoolean(4));
            Array authors = rs.getArray(5);

            Type listType = new TypeToken<ArrayList<UserDto>>() {
            }.getType();
            List<UserDto> authorList = new Gson().fromJson(authors.toString(), listType);
            courseDto.setAuthors(authorList);
            Array module = rs.getArray(6);
            Type type = new TypeToken<ArrayList<ModuleDto>>() {
            }.getType();
            List<ModuleDto> moduleDtoList = new Gson().fromJson(module.toString(), type);
            courseDto.setModule(moduleDtoList);
            return courseDto;
        });
    }

    public int editCourse(CourseDto courseDto) {
        int res2 = 0;
        int res = 0;
        if (courseDto.getAuthorsId().length != 0) {
            String sqlQuery1 = "Delete from authors_modules where module_id ='" + courseDto.getId() + "'";
            res = jdbcTemplate.update(sqlQuery1);
            for (UUID uuid : courseDto.getAuthorsId()) {
                res2 = jdbcTemplate.update("Insert INTO authors_modules values ('" + uuid + "','" + courseDto.getId() + "')");
            }
        }
        String sqlQuery = "Update courses Set name='" + courseDto.getName() + "',  is_active =" + courseDto.isActive() + ", description='" + courseDto.getDescription() + "', updated_at=now()  where id='" + courseDto.getId() + "'";
        int res1 = jdbcTemplate.update(sqlQuery);
        return res1 + (res - res2);
    }


    public List<CourseDto> getAllCoursesByPage(int interval, int currentPage, String text, String condition) {
        String sqlQuery = "";

        if (text != null) {
            sqlQuery = "select *\n" +
                    "from get_all_courses_by_pageable_and_search('" + text + "'," + interval + ", " + currentPage + ");";
        } else if (condition.equals("true") || condition.equals("false")) {
            boolean type = Boolean.parseBoolean(condition);
            sqlQuery = "select *\n" +
                    "from get_all_courses_by_pageable_and_search_and_status(" + type + ", " + interval + ", " + currentPage + ");";
        } else {
            sqlQuery = "select *\n" +
                    "from get_all_courses_by_pageable(" + interval + "," + currentPage + ");";
        }
        List<CourseDto> courseDtoList = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(UUID.fromString(rs.getString(1)));
            courseDto.setName(rs.getString(2));
            courseDto.setPrice(rs.getDouble(3));
            courseDto.setActive(rs.getBoolean(5));
            courseDto.setDescription(rs.getString(4));
            Array authors = rs.getArray(6);

            Type listType = new TypeToken<ArrayList<UserDto>>() {
            }.getType();
            List<UserDto> authorList = new Gson().fromJson(authors.toString(), listType);
            courseDto.setAuthors(authorList);
            return courseDto;
        });
        return courseDtoList;
    }


    public int getCourseCountByPage() {
        String sqlQuery = "select count (*) from courses";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
    }

    public int getCourseCountBySearch(String text) {
        String sqlQuery = "select count(*)\n" +
                "from get_all_courses_by_cound_and_search('" + text + "');";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
    }

    public int getCourseCountByType(boolean type) {
        String sqlQuery = "select count(*)\n" +
                "from get_all_courses_by_search_and_status_count(" + type + ");";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
    }

    public int getCourseCountByType() {
        String sqlQuery = "select count(*) from get_course_by_user_and_module_count();";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> rs.getInt(1));
    }


    public int addNewCourseModuleLesson(MentorCourseDto courseDto, byte[] fileBytes) {
        String sqlQuery = "Insert into courses(name,description) values('" +
                courseDto.getName() + "', '" +
                courseDto.getDescription() + "') returning id";

        String idStr = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> rs.getString("id"));
        UUID uuid = UUID.fromString(Objects.requireNonNull(idStr));

        String addImage = "update courses set image = ? where id = '" + uuid + "' ";


        int update = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    addImage);
            ps.setBytes(1, fileBytes);//This is byte[] of type
            return ps;
        });


//        String addImage = "update courses SET image = ? where id = '" + uuid + "' ";
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(addImage);
//            ps.setBytes(1, fileBytes);
//            return ps;
//        });
        String addModuleQuery = "insert into modules(name, price, course_id) " +
                "VALUES ('" + courseDto.getModuleName() + "', '" +
                courseDto.getModulePrice() + "', '" +
                uuid + "') returning id";

        String moduleId = jdbcTemplate.queryForObject(addModuleQuery, ((rs, rowNum) -> rs.getString("id")));

        UUID moduleUuid = UUID.fromString(Objects.requireNonNull(moduleId));

        int res = 0;
        for (UUID uuid1 : courseDto.getAuthorsId()) {
            res = jdbcTemplate.update("INSERT INTO authors_modules values (" +
                    "'" + uuid1 + "','" + moduleUuid + "');");
        }


        String addLessonQuery = "insert into lessons (title, module_id) values (" +
                "'" + courseDto.getLessonTitle() + "', '" + moduleUuid + "') returning id;";

        String lessonId = jdbcTemplate.queryForObject(addLessonQuery, (((rs, rowNum) -> rs.getString("id"))));

        UUID lessonUuid = UUID.fromString(Objects.requireNonNull(lessonId));

        String attachmentQuery = "insert into attachment(video_path, lesson_id)" +
                " values ('" + courseDto.getLessonVideoPath() + "', '" + lessonUuid + "')";

        int check = jdbcTemplate.update(attachmentQuery);

//        String adminMentor = "insert into admins_mentors_requests_courses(user_id, course_id) values ('""')";
//        jdbcTemplate.update(adminMentor);


        return check;
    }

    public MentorCourseDto getCourseByIdNew(UUID uuid) {
        String sqlQuery = "select id, name, description, image from courses where id='" + uuid + "'";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> {
            MentorCourseDto courseDto = new MentorCourseDto();
            courseDto.setCourseId(UUID.fromString(rs.getString(1)));
            courseDto.setName(rs.getString(2));
            courseDto.setDescription(rs.getString(3));
            courseDto.setCourseImage(rs.getBytes(4));


            return courseDto;
        });
    }

    public List<CourseDto> getAllCourse(UUID authorId) {

        String sqlQuery = "select c.id, c.name, c.status, c.is_active\n" +
                "from courses c\n" +
                "         join modules m on c.id = m.course_id\n" +
                "join authors_modules am on m.id = am.module_id\n" +
                "where am.author_id = '" + authorId + "'\n" +
                "group by c.id;";

        List<CourseDto> courseDtoListFromDb = jdbcTemplate.query(sqlQuery, (rs, row) -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(UUID.fromString(rs.getString(1)));
            courseDto.setName(rs.getString(2));
            courseDto.setStatus(rs.getString(3));

            courseDto.setActive(rs.getBoolean(4));

            return courseDto;
        });
        return courseDtoListFromDb;
    }

//    public int deleteCourseMentor(UUID uuid) {
//
//        String deleteCourse = "select delete_course('"+uuid+"')";
//
//        return jdbcTemplate.update(deleteCourse);
//    }


    public int editCourseMentor(MentorCourseDto courseDto, byte[] file) {
        String sql = "update courses set name = '" + courseDto.getName() + "', " +
                "description = '" + courseDto.getDescription() + "', image = ? where id = '" + courseDto.getCourseId() + "'";
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql);
            ps.setBytes(1, file);//This is byte[] of type
            return ps;
        });
    }


    public String deleteCourseMentor(UUID id) {
        try {
            String sqlQuery = "select delete_course_all('" + id + "')";
            return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> {
                String text = rs.getString(1);
                return text;
            });
        } catch (Exception e) {
            String sqlQuery = "select delete_course('" + id + "')";
            return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> {
                String text = rs.getString(1);
                return text;
            });
        }
    }


    public int courseSendMessage(UUID courseId, String message, UUID userId) {
        String query = "insert into" +
                " admins_mentors_requests_courses" +
                "(" +
                "user_id, course_id, description" +
                ") values ('" + userId + "', '" + courseId + "', '" + message + "') ";
        return jdbcTemplate.update(query);
    }


    public List<CourseDto> getAllCourseForIndex() {
        String sql="SELECT c.name, c.is_active, c.created_at, c.updated_at, c.id, c.description, c.image, c.status, json_agg(row_to_json(m)) as module FROM courses c " +
                "join modules m on c.id = m.course_id where c.is_active=true group by c.name, c.is_active, c.created_at, c.id";
        List<CourseDto> courseDtoList = jdbcTemplate.query(sql,(rs, rowNum) -> {
           CourseDto courseDto = new CourseDto();
           courseDto.setId(UUID.fromString(rs.getString(5)));
           courseDto.setName(rs.getString(1));
           courseDto.setDescription(rs.getString(6));
            byte[] encode = Base64.getEncoder().encode(rs.getBytes(7));
            String base64Encode=null;
            try {
                base64Encode = new String(encode, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            courseDto.setImage(base64Encode);
            Array module = rs.getArray(9);
           Type type = new TypeToken<ArrayList<ModuleDto>>() {
             }.getType();
             List<ModuleDto> moduleDtoList = new Gson().fromJson(module.toString(), type);
            Double coursePrice = Double.valueOf(0);
            for (ModuleDto moduleDto : moduleDtoList) {
                coursePrice+=moduleDto.getPrice();
            }
            courseDto.setPrice(coursePrice);
            return courseDto;
        });
        return courseDtoList;
    }

    public CourseAllData getCourseAllData(UUID id){
        String sql="SELECT * FROM get_course_all_data where id='"+id+"';";
        return jdbcTemplate.queryForObject(sql,(rs, rowNum) -> {
           CourseAllData courseAllData = new CourseAllData();
           courseAllData.setId(UUID.fromString(rs.getString(1)));
           courseAllData.setName(rs.getString(2));
           courseAllData.setIsActive(rs.getBoolean(3));
            String created_at = rs.getTimestamp(4).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            courseAllData.setCreated_at(created_at);
           courseAllData.setDescription(rs.getString(5));
            byte[] encode = Base64.getEncoder().encode(rs.getBytes(6));
            String base64Encode=null;
            try {
                base64Encode = new String(encode, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

           courseAllData.setImage(base64Encode);
            Array module = rs.getArray(7);
            Type type = new TypeToken<ArrayList<ModuleDto>>() {
            }.getType();
            List<ModuleDto> moduleDtoList = new Gson().fromJson(module.toString(), type);
           return courseAllData;
        });
    }

    public  List<ModuleDto> getModule(UUID id){
        String sql="SELECT * FROM get_course_module where course_id='"+id+"';";
        return jdbcTemplate.query(sql,(rs, rowNum) -> {
           ModuleDto moduleDto= new ModuleDto();
           moduleDto.setId(UUID.fromString(rs.getString(1)));
           moduleDto.setActive(rs.getBoolean(2));
           moduleDto.setName(rs.getString(3));
           moduleDto.setPrice(rs.getDouble(4));
           moduleDto.setCourseId(UUID.fromString(rs.getString(5)));
            Array lesson = rs.getArray(6);
            Type lessonType = new TypeToken<ArrayList<LessonDto>>() {
            }.getType();
            List<LessonDto> lessonDtoList = new Gson().fromJson(lesson.toString(), lessonType);
            moduleDto.setLessons(lessonDtoList);
           return moduleDto;
        });
    }


    //todo by module


    public String byModule(UUID moduleId, UUID userId) {
        String sqlQuery = "select *\n" +
                "from by_modules('" + userId + "', '" + moduleId + "');";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, row) -> {
            return rs.getString(1);
        });
    }

    public int selectCourse(UUID moduleId, UUID userId) {
        String sqlQuery = "";

        String priceQuery = "select sum(m.price)\n" +
                "from modules m\n" +
                "         join courses c on c.id = m.course_id\n" +
                "where m.id not in (select modules_id\n" +
                "                   from users_modules\n" +
                "                   where user_id = '" + userId + "')\n" +
                "  and c.id = '" + moduleId + "';";
        try {

            String priceStr = jdbcTemplate.queryForObject(priceQuery, (rs, row) -> {
                return rs.getString(1);
            });
            double price = Double.parseDouble(priceStr);

            String balanceQuery = "select balance from users where id = '" + userId + "';";

            String balanceStr = jdbcTemplate.queryForObject(balanceQuery, (rs, row) -> {
                return rs.getString(1);
            });
            double balance = Double.parseDouble(balanceStr);

            if (balance < price) {
                return 0;
            }

            sqlQuery = "select m.id\n" +
                    "from modules m\n" +
                    "         join courses c on c.id = m.course_id\n" +
                    "where m.id not in (select modules_id\n" +
                    "                   from users_modules\n" +
                    "                   where user_id = '" + userId + "')\n" +
                    "  and c.id = '" + moduleId + "';";


            List<UUID> courseDtoList = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
                UUID courseDto = UUID.fromString(rs.getString(1));
                return courseDto;
            });

            for (UUID uuid : courseDtoList) {
                String byCourses = "insert into users_modules(user_id, modules_id) VALUES ('" + userId + "', '" + uuid + "')";
                jdbcTemplate.update(byCourses);
            }

            String checkout = "update users set balance = balance- '" + price + "' where id = '" + userId + "'";
            return jdbcTemplate.update(checkout);

        } catch (Exception e) {
            return 0;
        }


    }


}

