package kosign.b2bdocumentv4.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage implements IResponseMessage {
    OK("0000", "OK"),
    INCORRECT_USERNAME_OR_PASSWORD("0001", "Incorrect username or password"),
    FORBIDDEN("403", "Forbidden"),
    UNAUTHORIZED("401", "Unauthorized"),
    INVALID_TOKEN_SIGNATURE("0004", "Invalid token signature"),
    INVALID_TOKEN("0005", "Invalid token"),
    TOKEN_EXPIRED("0006", "Token expired"),
    UNSUPPORTED_TOKEN("0007", "Unsupported token"),
    NOT_HAVE_PERMISSION_ON_COMPANY("0008", "You don't have permission on this company"),
    NOT_HAVE_PERMISSION_ON_PROJECT("0009", "You don't have permission on this project"),
    NOT_HAVE_PERMISSION_ON_POST("0010", "You don't have permission on this post"),
    NOT_FOUND("0011", "Not found"),
    USERNAME_ALREADY_EXISTS("0012", "Username already exists"),
    USER_IS_DISABLED("0013", "User is disabled"),
    YOU_DONT_HAVE_ANY_COMPANY("0014", "You don't have any company"),
    ALREADY_EXIST("0015", "Already exist"),
    COMPANY_NOT_FOUND("0016", "Company not found"),
    USER_NOT_FOUND("0017", "User not found"),
    PROJECT_NOT_FOUND("0018", "Project not found"),
    USER_NOT_COMPANY_ADMIN("0019", "User is not company admin"),
    POST_NOT_FOUND("0020", "Post not found"),
    INTERNAL_SERVER_ERROR("500", "Internal server error"),
    INCORRECT_PASSWORD("0021", "Incorrect password"),

    ;

    private final String code;
    private final String message;
}
