package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.dto.TeacherDto;
import drobczyk.bartlomiej.model.teacher.Teacher;
import drobczyk.bartlomiej.services.TeacherService;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class LoginController {
    private TeacherSession teacherSession;
    private TeacherService teacherService;


    @Autowired
    public LoginController(TeacherSession teacherSession, TeacherService teacherService) {
        this.teacherSession = teacherSession;
        this.teacherService = teacherService;
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
        Teacher loggedTeacher = teacherService.getTeacherByLogin(teacherLogin);
        teacherSession.setTeacher(loggedTeacher);
        return "redirect:/";
    }

    @GetMapping("/fail")
    public String failLogin(Model model){
        model.addAttribute("logFail",true);
        model.addAttribute("teacher",new TeacherDto());
        return "login";
    }
    @GetMapping("/loginform")
    public String loginForm(Model model){
        model.addAttribute("teacher",new TeacherDto());
        return "login";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute TeacherDto dto, BindingResult result, Model model){
        model.addAttribute("teacher",new TeacherDto());
        if (result.hasErrors()) {
            result.getAllErrors().stream()
                    .forEach(x-> System.err.println(x.getDefaultMessage()));
            model.addAttribute("errors",result.getAllErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.toList()));
            model.addAttribute("registrationFail",true);
            return "login";
        }
        boolean isRegistratrionSuccessful =  teacherService.registerTeacher(dto);
        model.addAttribute("registrationOutcome",isRegistratrionSuccessful);
        return "login";
    }

    @GetMapping("/log-out")
    public String logOut (Model model){
      //  teacherSession.setTeacher(null);
        model.addAttribute("teacher",new TeacherDto());
        return "login";
    }
}