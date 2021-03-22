package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.DTO.LessonFormInfo;
import drobczyk.bartlomiej.model.Student.Student;
import drobczyk.bartlomiej.services.StudentService;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

@Controller
public class StudentPanelController {
    private TeacherSession teacherSession;
    private StudentService studentService;

    @Autowired
    public StudentPanelController(TeacherSession teacherSession, StudentService studentService) {
        this.teacherSession = teacherSession;
        this.studentService = studentService;
    }

    @PostMapping("/student-panel")
        public String presentPanel(@RequestParam Long studentId, Model model){
        if (teacherSession.isTeacherLogged()){
            try{
                Student student = studentService.getStudentById(studentId);
                model.addAttribute("lessonInfo",new LessonFormInfo());
                model.addAttribute("chosenStudent",student);
                model.addAttribute("currentLessons",studentService.getCurrentLessons(student));
                return "studentPanel";
            }catch (NoSuchElementException e){
                System.err.println("Such student does not exist");
            }
        }
    return "redirect: /";
    }

    @PostMapping("/add-lesson")
    public String addLessonToStudent(@ModelAttribute LessonFormInfo lessonInfo, Model model){
        Student student = studentService.getStudentById(lessonInfo.getStudentId());
        studentService.saveLessonToStudent(student,lessonInfo);
        model.addAttribute("chosenStudent",student);
        model.addAttribute("currentLessons",studentService.getCurrentLessons(student));
        return "studentPanel";
    }

    @PostMapping("/archive-lesson") //WYDZIELIC DO SERWISU
    public String archiveLessons(@RequestParam Long studentId, @RequestParam Long positionToArchive, Model model){
        Student student = studentService.archiveCurrentLessons(studentId,positionToArchive);
        model.addAttribute("chosenStudent",student);
        model.addAttribute("currentLessons",studentService.getCurrentLessons(student));
        return "studentPanel";
    }

}
