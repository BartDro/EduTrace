package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.teacher.Teacher;
import drobczyk.bartlomiej.services.LogService;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LogController {
    private LogService logService;
    private TeacherSession teacherSession;

    @Autowired
    public LogController(LogService logService, TeacherSession teacherSession) {
        this.logService = logService;
        this.teacherSession = teacherSession;
    }

    @GetMapping("/")
    public String log(){
        if (teacherSession.isTeacherLogged()){
            return "redirect:/main-panel";
        }
        return "login";
    }

    @GetMapping("/success")
    public String logIn(Principal principal){
        String teacherLogin = principal.getName();
        Teacher loggedTeacher = logService.getTeacherByLogin(teacherLogin);
        teacherSession.setTeacher(loggedTeacher);
        return "redirect:/";
    }

    @GetMapping("/fail")
    public String fail(Model model){
        model.addAttribute("logFail",true);
        return "login";
    }

    @GetMapping("/loginform")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/processlogin")
    public String procces(){
        return "login";
    }

    @PostMapping("/register")
    public String registration(@RequestParam String login,@RequestParam String mail,
                               @RequestParam String password,@RequestParam String passwordConfirm,Model model){

        boolean isRegistratrionSuccessful =  logService.registerTeacher(login, mail, password, passwordConfirm);
        model.addAttribute("registrationOutcome",isRegistratrionSuccessful);
        return "login";
    }

    @GetMapping("/log-out")
    public String logOut (){
        teacherSession.setTeacher(null);
        return "redirect:/";
    }
}