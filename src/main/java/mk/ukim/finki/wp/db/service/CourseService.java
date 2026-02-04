package mk.ukim.finki.wp.db.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.Category;
import mk.ukim.finki.wp.db.entity.Certificate;
import mk.ukim.finki.wp.db.entity.Course;
import mk.ukim.finki.wp.db.entity.Enrollment;
import mk.ukim.finki.wp.db.entity.user.Administrator;
import mk.ukim.finki.wp.db.entity.user.Instructor;
import mk.ukim.finki.wp.db.entity.user.User;
import mk.ukim.finki.wp.db.entity.user.UserEntity;
import mk.ukim.finki.wp.db.entity.user.enums.Role;
import mk.ukim.finki.wp.db.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserEntityRepository userEntityRepository;
    private final InstructorRepository instructorRepository;
    private final CategoryService categoryService;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final UserSubscriptionService userSubscriptionService;
    private final CertificateRepository certificateRepository;

    public List<Course> findAllWithUserEnrolled(String email) {
        List<Course> courses = courseRepository.findAllByStatus("Available");
        List<Course> result = new ArrayList<>();

        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        User user = userRepository.findById(userEntity.getId()).get();

        for (Course course : courses) {
            if(enrollmentRepository.findByCourseAndUser(course, user).isPresent()) {
                result.add(course);
            }
        }

        return result;
    }

    public List<Course> findAllWithUserNotEnrolled(String email) {
        List<Course> courses = courseRepository.findAllByStatus("Available");
        List<Course> result = new ArrayList<>();

        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        User user = userRepository.findById(userEntity.getId()).get();

        for (Course course : courses) {
            if(enrollmentRepository.findByCourseAndUser(course, user).isEmpty()) {
                result.add(course);
            }
        }

        return result;
    }

    public Course findById(Integer courseId) {
        return courseRepository.findById(courseId).get();
    }

    public List<Course> findAllByInstructor(String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        Instructor instructor = instructorRepository.findById(userEntity.getId()).get();
        return courseRepository.findAllByInstructor(instructor);
    }

    public void addCourse(String name, BigDecimal price, Integer categoryId, String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        Instructor instructor = instructorRepository.findById(userEntity.getId()).get();
        Category category = categoryService.findById(categoryId);

        Course course = new Course();
        course.setName(name);
        course.setPrice(price);
        course.setStatus("Available");
        course.setInstructor(instructor);
        course.getCategories().add(category);
        courseRepository.save(course);
    }

    public void editCourse(Integer courseId, String name, BigDecimal price, Integer categoryId) {
        Course course = courseRepository.findById(courseId).get();
        course.setName(name);
        course.setPrice(price);
        course.getCategories().clear();
        course.getCategories().add(categoryService.findById(categoryId));
        courseRepository.save(course);
    }

    public void deleteCourse(Integer courseId) {
        courseRepository.deleteById(courseId);
    }

    public void changeCourseStatus(Integer courseId) {
        Course course = courseRepository.findById(courseId).get();

        if(course.getStatus().equals("Available")) {
            course.setStatus("Unavailable");
        } else {
            course.setStatus("Available");
        }

        courseRepository.save(course);
    }

    public void enrollUserToCourse(Integer courseId, String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        User user = userRepository.findById(userEntity.getId()).get();
        Course course = courseRepository.findById(courseId).get();

        if(userSubscriptionService.hasUserSubscription(email) && enrollmentRepository.findByCourseAndUser(course, user).isEmpty()) {
            Enrollment enrollment = new Enrollment();
            enrollment.setUser(user);
            enrollment.setCourse(course);
            enrollment.setEnrollDate(LocalDate.now());
            enrollment.setCompletionStatus("NOT COMPLETE");
            enrollment.setProgressPercentage(0);

            enrollmentRepository.save(enrollment);
        }
    }

    public void completeCourse(Integer courseId, String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        User user = userRepository.findById(userEntity.getId()).get();
        Course course = courseRepository.findById(courseId).get();

        Random random = new Random();

        Enrollment enrollment = enrollmentRepository.findByCourseAndUser(course, user).get();
        enrollment.setCompletionStatus("COMPLETE");
        enrollment.setProgressPercentage(100);
        enrollmentRepository.save(enrollment);

        Certificate certificate = new Certificate();
        certificate.setEnrollment(enrollment);
        certificate.setIssueDate(LocalDate.now());
        certificate.setStatus("COMPLETE");
        certificate.setCertificateCode("CODE" + random.nextLong());
        certificateRepository.save(certificate);
    }

    public Certificate getCertificate(Integer courseId, String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        User user = userRepository.findById(userEntity.getId()).get();
        Course course = courseRepository.findById(courseId).get();

        Enrollment enrollment = enrollmentRepository.findByCourseAndUser(course, user).get();

        Optional<Certificate> certificate = certificateRepository.findByEnrollment(enrollment);

        if(certificate.isPresent()) {
            return certificate.get();
        } else {
            return null;
        }
    }
}
