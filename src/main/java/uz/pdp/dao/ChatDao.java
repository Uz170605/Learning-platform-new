package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uz.pdp.dto.*;

import java.lang.reflect.Type;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public String studentMentorSendMessage(ChatMessageSumDto dto) {
        String query = "select * from send_message('" + dto.getUserId() + "',\n" +
                "    '" + dto.getTaskId() + "',\n" +
                "    '" + dto.getMentorId() + "',\n" +
                "    '" + dto.getMessage() + "', '" + dto.getRole() + "')";
        try {

            return jdbcTemplate.queryForObject(query, (rs, row) -> {
                return rs.getString(1);
            });
        } catch (Exception e) {
            return null;
        }

    }

    public String studentSendTask(ChatMessageSumDto dto, byte[] file) {
        String query = "insert into performed_tasks(task, user_id, task_id) VALUES (?, '" + dto.getUserId() + "', '" + dto.getTaskId() + "');";

        int check = jdbcTemplate.update(connection -> {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setBytes(1, file);
            return pr;
        });
        if(check>0){
            studentMentorSendMessage(dto);
        }
        return null;
    }

    public ChatDto chat(ChatMessageSumDto message) {
        String query = "select concat(  u.\"firstName\",' ', u.\"lastName\") as user,\n" +
                "       concat(  u2.\"firstName\",' ', u2.\"lastName\") as mentor,\n" +
                "       json_agg(json_build_object('message', cm.message,\n" +
                "           'time', cm.send_time,\n" +
                "           'role', r.name\n " +
                "" +
                "           ))\n" +
                "from chat_messages cm\n" +
                " join roles r on cm.role = r.id\n" +
                "join chat c on c.id = cm.chat_id\n" +
                "join users u on c.user_id = u.id\n" +
                "join users u2 on u2.id = c.mentor_id\n" +
                "where c.user_id = '" + message.getUserId() + "'\n" +
                "and c.mentor_id = '" + message.getMentorId() + "'\n" +
                "group by u.\"firstName\", u.\"lastName\", u2.\"firstName\", u2.\"lastName\";";

        return jdbcTemplate.queryForObject(query, (rs, row) -> {
            ChatDto chat = new ChatDto();
            chat.setUserFullName(rs.getString(1));
            chat.setMentorFullName(rs.getString(2));
            Array chatList = rs.getArray(3);
            Type listType = new TypeToken<ArrayList<ChatMessagesDto>>() {
            }.getType();
            List<ChatMessagesDto> chatList1 = new Gson().fromJson(chatList.toString(), listType);
            chat.setMessages(chatList1);
            return chat;
        });


    }

}
