package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessageSumDto {
    private UUID userId;
    private UUID mentorId;
    private String message;
    private UUID taskId;
    private String role;
}
