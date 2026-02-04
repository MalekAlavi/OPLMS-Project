package mk.ukim.finki.wp.db.repository;

import mk.ukim.finki.wp.db.entity.Lesson;
import mk.ukim.finki.wp.db.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findAllByModuleEntity(ModuleEntity moduleEntity);
}
