package mk.ukim.finki.wp.db.dto;

import lombok.Data;
import mk.ukim.finki.wp.db.entity.Lesson;

import java.util.ArrayList;
import java.util.List;

@Data
public class ModuleWithLessonDto {
    private Integer moduleId;
    private String title;
    private String description;
    private List<Lesson> lessons = new ArrayList<>();
}
