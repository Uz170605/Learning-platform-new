package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    private UUID id;
    private String title;
    private Integer difficulty_degree;
    private Integer grade;
    private String body;
    private UUID lesson_id;
    private UUID module_id;
}
