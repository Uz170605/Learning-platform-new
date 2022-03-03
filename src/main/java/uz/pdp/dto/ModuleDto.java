package uz.pdp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModuleDto {
    private UUID id;
    private String name;
    private double price;
    private UUID courseId;
    private boolean isActive;
    private List<LessonDto> lessonList;
}
