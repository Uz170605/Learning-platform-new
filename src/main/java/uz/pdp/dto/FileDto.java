package uz.pdp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileDto {
    private UUID id;
    private String fileName;
    private String fileType;
    private LessonDto lessonDto;
}
