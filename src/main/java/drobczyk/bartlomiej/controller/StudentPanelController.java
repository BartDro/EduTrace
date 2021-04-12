package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.dto.addition_form.LessonFormInfo;
import drobczyk.bartlomiej.model.student.Student;
import drobczyk.bartlomiej.services.StudentService;
import drobczyk.bartlomiej.services.api.ApiService;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentPanelController {
    private TeacherSession teacherSession;
    private StudentService studentService;
    private ApiService apiService;

    @Autowired
    public StudentPanelController(TeacherSession teacherSession, StudentService studentService, ApiService apiService) {
        this.teacherSession = teacherSession;
        this.studentService = studentService;
        this.apiService = apiService;
    }

    @GetMapping("/student-panel")
    public String presentPanel(Model model, @RequestParam(required = false) Long studentId) {
        if (teacherSession.isTeacherLogged()) {
            model.addAttribute("lessonInfo", new LessonFormInfo());
            model.addAttribute("chosenStudent", studentService.provideStudentDto(studentId));
            model.addAttribute("currentLessons", studentService.getCurrentLessonsDto(studentId));
            model.addAttribute("students", studentService.provideStudentsDtosAccordingToTeacher());
            model.addAttribute("weather",apiService.provideWeather(apiService.provideLocationDto()));
            model.addAttribute("quote",apiService.provideRandomQuote());
            return "studentPanel";
        }
        return "redirect:/";
    }

    @PostMapping("/add-lesson")
    public String addLessonToStudent(@ModelAttribute LessonFormInfo lessonInfo) {
        Student student = studentService.getStudentById(lessonInfo.getStudentId());
        studentService.saveLessonToStudent(student, lessonInfo);
        return "redirect:/student-panel?studentId=" + lessonInfo.getStudentId();
    }

    @PostMapping("/archive-lesson")
    public String archiveLessons(@RequestParam Long studentId, @RequestParam Long positionToArchive) {
        studentService.archiveCurrentLessons(studentId, positionToArchive);
        return "redirect:/student-panel?studentId=" + studentId;
    }

    @PostMapping("/delete-student")
    public String deleteStudent(@RequestParam Long studentId) {
        studentService.deleteStudent(studentId);
        return "redirect:/main-panel";
    }

}
