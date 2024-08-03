package hoangquan.vn.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User existed!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002 , "User not found!", HttpStatus.NOT_FOUND),
    PASSWORD_INVALID(1003,"Password must be at least 8 charaters!", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1004,"Uncategorized exception!", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized exception!", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_EXISTED(1005, "User not existed!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission!", HttpStatus.UNAUTHORIZED),
    USERNAME_INVALID(1008, "Invalid username!", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND(1009,"Book not found!", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1010,"Role not found!", HttpStatus.NOT_FOUND)
            ;
    private  int code;
    private String message;
    private HttpStatusCode statusCode;


    ErrorCode(int code, String message, HttpStatusCode statusCode ) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
