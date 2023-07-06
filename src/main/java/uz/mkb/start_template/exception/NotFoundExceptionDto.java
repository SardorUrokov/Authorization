package uz.mkb.start_template.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.servlet.NoHandlerFoundException;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class NotFoundExceptionDto {

    String requestURL;
    String httpMethod;
    String localizedMessage;
    String message;
    String cause_message;

    public static NotFoundExceptionDto myBuilder(NoHandlerFoundException ex) {
        return NotFoundExceptionDto.builder()
                .cause_message(ex.getCause().getMessage())
                .httpMethod(ex.getHttpMethod())
                .localizedMessage(ex.getLocalizedMessage())
                .httpMethod(ex.getHttpMethod())
                .requestURL(ex.getRequestURL())
                .build();
    }

}
