package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDto {
    private UUID messageId;
    private String message;
    private UUID courseId;
    private String time;
}
