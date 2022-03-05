package uz.pdp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseAllData {
    private UUID id;
    private String name;
    private Boolean isActive;
    private String created_at;
    private String description;
    private String image;
    private Double price;
    private List<ModuleDto> moduleList;
    private List<UserDto> authorList;
}
