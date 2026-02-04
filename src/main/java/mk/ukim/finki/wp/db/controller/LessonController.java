package mk.ukim.finki.wp.db.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.Lesson;
import mk.ukim.finki.wp.db.entity.ModuleEntity;
import mk.ukim.finki.wp.db.service.LessonService;
import mk.ukim.finki.wp.db.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LessonController {

    private final ModuleService moduleService;
    private final LessonService lessonService;

    @GetMapping("/lesson/{moduleId}")
    public String getLessonsPage(@PathVariable Integer moduleId, Model model) {
        ModuleEntity moduleEntity = moduleService.findById(moduleId);

        model.addAttribute("module", moduleEntity);
        model.addAttribute("lessons", lessonService.findAllByModule(moduleEntity));

        return "lesson/lesson";
    }

    @GetMapping("/lesson/{moduleId}/add")
    public String getAddLessonPage(@PathVariable Integer moduleId, Model model) {
        ModuleEntity moduleEntity = moduleService.findById(moduleId);

        model.addAttribute("module", moduleEntity);

        return "lesson/lesson_form";
    }

    @PostMapping("/lesson/{moduleId}/add")
    public String addLesson(@PathVariable Integer moduleId,
                            @RequestParam String title,
                            @RequestParam String material) {

        ModuleEntity moduleEntity = moduleService.findById(moduleId);
        lessonService.addLesson(moduleEntity, title, material);

        return "redirect:/lesson/" + moduleId;
    }

    @GetMapping("/lesson/{moduleId}/edit/{lessonId}")
    public String getEditLessonPage(@PathVariable Integer moduleId,
                                    @PathVariable Integer lessonId,
                                    Model model) {

        ModuleEntity moduleEntity = moduleService.findById(moduleId);
        Lesson lesson = lessonService.findById(lessonId);

        model.addAttribute("module", moduleEntity);
        model.addAttribute("lesson", lesson);

        return "lesson/lesson_form";
    }

    @PostMapping("/lesson/{moduleId}/edit/{lessonId}")
    public String editLesson(@PathVariable Integer moduleId,
                             @PathVariable Integer lessonId,
                             @RequestParam String title,
                             @RequestParam String material) {

        lessonService.editLesson(lessonId, title, material);

        return "redirect:/lesson/" + moduleId;
    }

    @PostMapping("/lesson/{moduleId}/delete/{lessonId}")
    public String deleteLesson(@PathVariable Integer moduleId,
                               @PathVariable Integer lessonId) {

        lessonService.deleteLesson(lessonId);

        return "redirect:/lesson/" + moduleId;
    }
}
