package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessagesDto {
    private UUID id;
    private UUID chatId;
    private String message;
    private String time;
    private String role;
}
