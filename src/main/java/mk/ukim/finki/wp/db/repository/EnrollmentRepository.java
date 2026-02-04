package mk.ukim.finki.wp.db.repository;

import mk.ukim.finki.wp.db.entity.Course;
import mk.ukim.finki.wp.db.entity.Enrollment;
import mk.ukim.finki.wp.db.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    Optional<Enrollment> findByCourseAndUser(Course course, User user);
}
