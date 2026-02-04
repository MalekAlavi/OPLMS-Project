package mk.ukim.finki.wp.db.repository;

import mk.ukim.finki.wp.db.entity.Course;
import mk.ukim.finki.wp.db.entity.user.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByInstructor(Instructor instructor);
    List<Course> findAllByStatus(String status);
}
