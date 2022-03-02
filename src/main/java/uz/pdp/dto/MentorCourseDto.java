package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MentorCourseDto {
    private UUID courseId;
    private String name;
    private String description;
    private byte[] courseImage;

    private UUID[] authorsId;

    private UUID moduleId;
    private String moduleName;
    private double modulePrice;
    private String status;
    private boolean is_active;
    private String moduleStatus;

    private UUID lessonId;
    private String lessonTitle;
    private String lessonVideoPath;

    private String taskTitle;
    private Integer taskDegree;
    private Integer taskGrade;
    private String taskBody;


}
