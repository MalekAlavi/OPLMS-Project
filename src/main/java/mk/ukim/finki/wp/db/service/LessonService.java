package mk.ukim.finki.wp.db.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.Lesson;
import mk.ukim.finki.wp.db.entity.ModuleEntity;
import mk.ukim.finki.wp.db.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public List<Lesson> findAllByModule(ModuleEntity moduleEntity) {
        return lessonRepository.findAllByModuleEntity(moduleEntity);
    }

    public Lesson findById(Integer id) {
        return lessonRepository.findById(id).get();
    }

    public void addLesson(ModuleEntity moduleEntity, String title, String material) {
        Lesson lesson = new Lesson();
        lesson.setModuleEntity(moduleEntity);
        lesson.setTitle(title);
        lesson.setMaterial(material);
        lessonRepository.save(lesson);
    }

    public void editLesson(Integer id, String title, String material) {
        Lesson lesson = lessonRepository.findById(id).get();
        lesson.setTitle(title);
        lesson.setMaterial(material);
        lessonRepository.save(lesson);
    }

    public void deleteLesson(Integer id) {
        lessonRepository.deleteById(id);
    }
}
