package drobczyk.bartlomiej.security;

import drobczyk.bartlomiej.model.roles.UserRole;
import drobczyk.bartlomiej.model.teacher.Teacher;
import drobczyk.bartlomiej.services.LogService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TeacherAuthentication implements UserDetailsService {

    private LogService logService;

    public TeacherAuthentication( LogService logService) {
        this.logService = logService;
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Teacher teacher = logService.getTeacherByLogin(login);
        return new User(teacher.getLogin(), teacher.getPassword(), prepareAuthorities(teacher.getRoles()));
    }

    private Set<GrantedAuthority> prepareAuthorities(Set<UserRole> userRoles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.stream()
                .map(x -> new SimpleGrantedAuthority(x.getRole()))
                .forEach(authorities::add);
        return authorities;
    }
}
