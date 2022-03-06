package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attachment {
    private UUID id;
    private String file_path;
    private UUID lesson_id;
    private UUID module_id;
}
