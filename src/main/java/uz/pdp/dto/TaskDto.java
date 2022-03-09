package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {
    private UUID id;
    private String title;
    private Integer difficultyDegree;
    private Integer grade;
    private String body;
    private UUID lessonId;
}
