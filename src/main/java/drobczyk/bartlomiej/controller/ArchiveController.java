package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.dto.StudentDto;
import drobczyk.bartlomiej.services.StudentService;
import drobczyk.bartlomiej.services.api.ApiService;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ArchiveController {
    private StudentService studentService;
    private TeacherSession teacherSession;
    private ApiService apiService;

    @Autowired
    public ArchiveController(StudentService studentService, TeacherSession teacherSession, ApiService apiService) {
        this.studentService = studentService;
        this.teacherSession = teacherSession;
        this.apiService = apiService;
    }

    @GetMapping("/archive")
    public String findStudentInArchive(@RequestParam(required = false) String student, @RequestParam(required = false) Long studentId, Model model) {
        if (teacherSession.isTeacherLogged()) {
            List<StudentDto> matchedStudents = new ArrayList<>();
            if (student != null) {
                matchedStudents = studentService.findStudentsInArchive(student);
            }
            model.addAttribute("archivedStudents", matchedStudents);
            model.addAttribute("students", studentService.provideStudentsDtosAccordingToTeacher());
            model.addAttribute("weather",apiService.provideWeather(apiService.provideLocationDto()));
            model.addAttribute("quote",apiService.provideRandomQuote());
            if (studentId != null){
                model.addAttribute("chosenStudent", studentService.provideStudentDto(studentId));
                model.addAttribute("currentLessons", studentService.getCurrentLessonsDto(studentId));
                return "studentArchivePanel";
            }
                return "archivePanel";
        }
        return "redirect:/";
    }
}
