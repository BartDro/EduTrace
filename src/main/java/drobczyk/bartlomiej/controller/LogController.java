package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.services.LogService;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String log(Model model){
       if (teacherSession.isTeacherLogged()){
           return "redirect:/main-panel";
       }
       return "login";
    }

    @PostMapping("/register")
    public String registration(@RequestParam String login,@RequestParam String mail,
                               @RequestParam String password,@RequestParam String passwordConfirm,Model model){

        boolean isRegistratrionSuccessful =  logService.registerTeacher(login, mail, password, passwordConfirm);
        model.addAttribute("registrationOutcome",isRegistratrionSuccessful);
        return "login";
    }

    @PostMapping("/log-in")
    public String log(@RequestParam String login, @RequestParam String password,Model model){
        boolean isLogProcessSuccessful = logService.logTeacher(login,password);
        model.addAttribute("logOutcome",isLogProcessSuccessful);
        if (isLogProcessSuccessful){
            teacherSession.setTeacher(logService.getTeacherByLogin(login));
            return "redirect:main-panel";}
        return "login";
    }
}
