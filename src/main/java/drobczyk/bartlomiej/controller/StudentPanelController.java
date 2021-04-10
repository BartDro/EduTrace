package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.dto.addition_form.LessonFormInfo;
import drobczyk.bartlomiej.model.Student.Student;
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
    public String presentPanel(Model model,@RequestParam(required = false) Long studentId){
        if (teacherSession.isTeacherLogged()){
            Student student = studentService.getStudentById(studentId);
            model.addAttribute("lessonInfo", new LessonFormInfo());
            model.addAttribute("chosenStudent", student);
            model.addAttribute("currentLessons", studentService.getCurrentLessons(student));
            model.addAttribute("students", teacherSession.getTeacher().getStudents());
            model.addAttribute("weather",apiService.provideWeather());
            model.addAttribute("quote",apiService.provideQuote());
            teacherSession.refresh();
            return "studentPanel";
        }return "redirect:/";
    }

    @PostMapping("/add-lesson")
    public String addLessonToStudent(@ModelAttribute LessonFormInfo lessonInfo, Model model) {
        Student student = studentService.getStudentById(lessonInfo.getStudentId());
        studentService.saveLessonToStudent(student, lessonInfo);
        return "redirect:/student-panel?studentId="+lessonInfo.getStudentId();
    }

    @PostMapping("/archive-lesson")
    public String archiveLessons(@RequestParam Long studentId, @RequestParam Long positionToArchive) {
        studentService.archiveCurrentLessons(studentId, positionToArchive);
        return "redirect:/student-panel?studentId="+studentId;
    }

    @PostMapping("/delete-student")
    public String deleteStudent(@RequestParam Long studentId ){
        studentService.deleteStudent(studentId);
        return "redirect:/main-panel";
    }

}
