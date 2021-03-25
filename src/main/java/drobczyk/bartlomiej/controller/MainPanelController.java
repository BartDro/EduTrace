package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.DTO.addition_form.StudentFormInfo;
import drobczyk.bartlomiej.services.MainPanelService;
import drobczyk.bartlomiej.services.StudentService;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainPanelController {
    private MainPanelService mainPanelService;
    private TeacherSession teacherSession;
    private StudentService studentService;

    @Autowired
    public MainPanelController(MainPanelService panelService, TeacherSession teacherSession, StudentService studentService) {
        this.mainPanelService = panelService;
        this.teacherSession = teacherSession;
        this.studentService = studentService;
    }

    @GetMapping("/main-panel")
    public String presentPanel(Model model) {
        if (teacherSession.isTeacherLogged()) {
            model.addAttribute("studentBasicInfo", new StudentFormInfo());
            model.addAttribute("students", teacherSession.getTeacher().getStudents());
            if (teacherSession.getTeacher().getStudents().isEmpty()) {
                return "firstContactPanel";
            }
            return "mainPanel";
        }
        return "redirect:/";
    }

    @PostMapping("/add-student")
    public String addStudentToTeacher(@ModelAttribute StudentFormInfo studentBasicInfo) {
        mainPanelService.addStudentToTeacher(studentBasicInfo, teacherSession.getTeacher());
        return "redirect:/main-panel";
    }

    @GetMapping({"/add-student","/add-lesson","/register"})
    public String redirectToLogView() {
        if (teacherSession.isTeacherLogged()) {
            return "redirect:/main-panel";
        }
        return "redirect:/";
    }


}
