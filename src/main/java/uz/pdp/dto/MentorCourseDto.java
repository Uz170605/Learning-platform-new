package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MentorCourseDto {
    private UUID courseId;
    private String name;
    private String description;
    private byte[] courseImage;

    private UUID[] authorsId;
    private List<UserDto> userDtos;

    private String moduleName;
    private double modulePrice;

    private String lessonTitle;
    private String lessonVideoPath;


}
