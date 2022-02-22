package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseModuleLessonDto {
    private UUID id;
    private ModuleDto moduleDto;
    private CourseDto courseDto;
}
