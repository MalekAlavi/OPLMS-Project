package mk.ukim.finki.wp.db.repository;

import mk.ukim.finki.wp.db.entity.Course;
import mk.ukim.finki.wp.db.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Integer> {
    List<ModuleEntity> findAllByCourse(Course course);
}
