package mk.ukim.finki.wp.db.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.Course;
import mk.ukim.finki.wp.db.entity.ModuleEntity;
import mk.ukim.finki.wp.db.service.CourseService;
import mk.ukim.finki.wp.db.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ModuleController {

    private final CourseService courseService;
    private final ModuleService moduleService;

    @GetMapping("/module/{courseId}")
    public String getModulesPage(@PathVariable Integer courseId, Model model) {
        Course course = courseService.findById(courseId);

        model.addAttribute("course", course);
        model.addAttribute("modules", moduleService.findAllByCourse(course));

        return "module/module";
    }

    @GetMapping("/module/{courseId}/add")
    public String getAddModulePage(@PathVariable Integer courseId, Model model) {
        Course course = courseService.findById(courseId);

        model.addAttribute("course", course);

        return "module/module_form";
    }

    @PostMapping("/module/{courseId}/add")
    public String addModule(@PathVariable Integer courseId,
                            @RequestParam String title,
                            @RequestParam String description) {

        Course course = courseService.findById(courseId);
        moduleService.addModule(course, title, description);

        return "redirect:/module/" + courseId;
    }

    @GetMapping("/module/{courseId}/edit/{moduleId}")
    public String getEditModulePage(@PathVariable Integer courseId,
                                    @PathVariable Integer moduleId,
                                    Model model) {

        Course course = courseService.findById(courseId);
        ModuleEntity module = moduleService.findById(moduleId);

        model.addAttribute("course", course);
        model.addAttribute("module", module);

        return "module/module_form";
    }

    @PostMapping("/module/{courseId}/edit/{moduleId}")
    public String editModule(@PathVariable Integer courseId,
                             @PathVariable Integer moduleId,
                             @RequestParam String title,
                             @RequestParam String description) {

        moduleService.editModule(moduleId, title, description);

        return "redirect:/module/" + courseId;
    }

    @PostMapping("/module/{courseId}/delete/{moduleId}")
    public String deleteModule(@PathVariable Integer courseId,
                               @PathVariable Integer moduleId) {

        moduleService.deleteModule(moduleId);

        return "redirect:/module/" + courseId;
    }
}
