package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.Student.Student;
import drobczyk.bartlomiej.services.StudentService;
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

    @Autowired
    public ArchiveController(StudentService studentService, TeacherSession teacherSession) {
        this.studentService = studentService;
        this.teacherSession = teacherSession;
    }

    @GetMapping("/archive")
    public String findStudentInArchive(@RequestParam(required = false) String student, Model model){
        if (teacherSession.isTeacherLogged()){
            List<Student> matchedStudents = new ArrayList<>();
            if (student!=null){
                matchedStudents = studentService.findStudentsInArchive(student);
            }
            model.addAttribute("archivedStudents",matchedStudents);
            return "archivePanel";
        }
        return "redirect:/";
    }

    @GetMapping("/archive/present-archive")
    public String presentArchive(@RequestParam Long studentId, Model model){
        if (teacherSession.isTeacherLogged()){
            Student archivedStudent = studentService.getStudentById(studentId);

            model.addAttribute("chosenStudent",archivedStudent);
            model.addAttribute("currentLessons",studentService.getOrderedLessons(archivedStudent));
            return "studentArchivePanel";
        }
        return "redirect:/";
    }
}
