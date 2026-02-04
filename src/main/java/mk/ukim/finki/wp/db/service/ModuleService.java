package mk.ukim.finki.wp.db.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.Course;
import mk.ukim.finki.wp.db.entity.ModuleEntity;
import mk.ukim.finki.wp.db.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public List<ModuleEntity> findAllByCourse(Course course) {
        return moduleRepository.findAllByCourse(course);
    }

    public ModuleEntity findById(Integer id) {
        return moduleRepository.findById(id).get();
    }

    public void addModule(Course course, String title, String description) {
        ModuleEntity moduleEntity = new ModuleEntity();
        moduleEntity.setCourse(course);
        moduleEntity.setTitle(title);
        moduleEntity.setDescription(description);
        moduleRepository.save(moduleEntity);
    }

    public void editModule(Integer id, String title, String description) {
        ModuleEntity moduleEntity = moduleRepository.findById(id).get();
        moduleEntity.setTitle(title);
        moduleEntity.setDescription(description);
        moduleRepository.save(moduleEntity);
    }

    public void deleteModule(Integer id) {
        moduleRepository.deleteById(id);
    }
}
