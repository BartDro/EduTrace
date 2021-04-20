package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.dto.addition_form.StudentFormInfo;
import drobczyk.bartlomiej.services.MainPanelService;
import drobczyk.bartlomiej.services.StudentService;
import drobczyk.bartlomiej.services.api.ApiService;
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
    private ApiService apiService;

    @Autowired
    public MainPanelController(MainPanelService panelService, TeacherSession teacherSession,
                               StudentService studentService, ApiService apiService) {
        this.mainPanelService = panelService;
        this.teacherSession = teacherSession;
        this.studentService = studentService;
        this.apiService = apiService;
    }

    @GetMapping("/main-panel")
    public String presentPanel(Model model) {
        model.addAttribute("studentBasicInfo", new StudentFormInfo());
        model.addAttribute("students", studentService.provideStudentsDtosAccordingToTeacher());
        model.addAttribute("weather", apiService.provideWeather(apiService.provideLocationDto()));
        model.addAttribute("quote", apiService.provideRandomQuote());
        if (teacherSession.getTeacher().getStudents().isEmpty()) {
            return "firstContactPanel";
        }
        return "mainPanel";
    }

    @PostMapping("/add-student")
    public String addStudentToTeacher(@ModelAttribute StudentFormInfo studentBasicInfo) {
        mainPanelService.addStudentToTeacher(studentBasicInfo, teacherSession.getTeacher());
        return "redirect:/main-panel";

    }
}