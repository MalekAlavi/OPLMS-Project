package mk.ukim.finki.wp.db.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.dto.ModuleWithLessonDto;
import mk.ukim.finki.wp.db.entity.Course;
import mk.ukim.finki.wp.db.entity.ModuleEntity;
import mk.ukim.finki.wp.db.service.CategoryService;
import mk.ukim.finki.wp.db.service.CourseService;
import mk.ukim.finki.wp.db.service.LessonService;
import mk.ukim.finki.wp.db.service.ModuleService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CategoryService categoryService;
    private final ModuleService moduleService;
    private final LessonService lessonService;

    @GetMapping("/course")
    public String getCoursePage(Model model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        model.addAttribute("availableCourses", courseService.findAllWithUserNotEnrolled(email));
        model.addAttribute("unavailableCourses", courseService.findAllWithUserEnrolled(email));
        return "course/course";
    }

    @GetMapping("/instructor/course")
    public String getInstructorCoursePage(Model model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        model.addAttribute("courses", courseService.findAllByInstructor(email));
        return "course/course_instructor";
    }

    @GetMapping("/add/course")
    public String getAddCoursePage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "course/course_form";
    }

    @PostMapping("/add/course")
    public String addCourse(@RequestParam String name,
                            @RequestParam BigDecimal price,
                            @RequestParam Integer categoryId,
                            Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        courseService.addCourse(name, price, categoryId, email);
        return "redirect:/instructor/course";
    }

    @GetMapping("/edit/course/{id}")
    public String getEditCoursePage(@PathVariable Integer id, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findById(id));
        return "course/course_form";
    }

    @PostMapping("/edit/course/{id}")
    public String editCourse(@PathVariable Integer id,
                            @RequestParam String name,
                            @RequestParam BigDecimal price,
                            @RequestParam Integer categoryId,
                            Authentication authentication) {
        courseService.editCourse(id, name, price, categoryId);
        return "redirect:/instructor/course";
    }

    @PostMapping("/delete/course/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return "redirect:/instructor/course";
    }

    @PostMapping("/change-status/course/{id}")
    public String changeCourseStatus(@PathVariable Integer id) {
        courseService.changeCourseStatus(id);
        return "redirect:/instructor/course";
    }

    @GetMapping("/course/enroll/{id}")
    public String enrollUserToCourse(@PathVariable Integer id, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        courseService.enrollUserToCourse(id, email);
        return "redirect:/course";
    }

    @GetMapping("/course/{id}")
    public String goToFullCoursePage(@PathVariable Integer id, Model model, Authentication authentication) {
        Course course = courseService.findById(id);
        List<ModuleEntity> modules = moduleService.findAllByCourse(course);
        List<ModuleWithLessonDto> moduleWithLessonDtos = new ArrayList<>();

        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();

        for(ModuleEntity module : modules) {
            ModuleWithLessonDto moduleWithLessonDto = new  ModuleWithLessonDto();
            moduleWithLessonDto.setModuleId(module.getModuleId());
            moduleWithLessonDto.setTitle(module.getTitle());
            moduleWithLessonDto.setDescription(module.getDescription());
            moduleWithLessonDto.setLessons(lessonService.findAllByModule(module));
            moduleWithLessonDtos.add(moduleWithLessonDto);
        }

        model.addAttribute("modules", moduleWithLessonDtos);
        model.addAttribute("course", course);
        model.addAttribute("certificate", courseService.getCertificate(id, email));

        return "course/course_view";
    }

    @PostMapping("/course/complete/{id}")
    public String completeCourse(@PathVariable Integer id, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        courseService.completeCourse(id, email);
        return "redirect:/course/" + id;
    }
}
