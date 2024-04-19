package kosign.b2bdocumentv4.utils;

import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHelper {
    private static Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static DocumentUsers getUser(){
        return (DocumentUsers) getAuth().getPrincipal();
    }

    public static String getUsername(){
        return getUser().getUsername();
    }

    public static Role getRole(){
        return getUser().getRole();
    }

    public static long getUserId(){
        return getUser().getId();
    }

    public static boolean isAdmin(){
        return getRole().equals(Role.ADMIN);
    }

    public static boolean isUser(){
        return getRole().equals(Role.USER);
    }

    public static void reload(){
        SecurityContext context = SecurityContextHolder.getContext();
        context.getAuthentication().setAuthenticated(false);
    }
}
