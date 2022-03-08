package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatDto {
    private UUID id;
    private UUID userId;
    private String userFullName;
    private UUID mentorId;
    private String mentorFullName;
    private UUID taskId;
    private List<ChatMessagesDto> messages;
}
