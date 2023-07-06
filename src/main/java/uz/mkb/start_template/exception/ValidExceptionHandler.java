package uz.mkb.start_template.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.mkb.start_template.model.response.ResponseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class ValidExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "Path variable [" + ex.getVariableName() + "] is required";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "This request wait [" + ex.getMethod() + "] Method";
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(message);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "Request [" + ex.getParameterName() + "] is required";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorFields = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            errorFields.add("Validation message: [" + error.getDefaultMessage() + "]");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorFields);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ex.getMessage().contains(" body is missing")
                        ? new ResponseObject("Error: Body is missing", "Request Body is Required")
                        : new ResponseObject("Error: Message Not Readable", ex.getMessage())
        );
    }


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(
                        "Error: Not Supported Media Type",
                        "Http media type [" + ex.getContentType().getType() + "] not supported"
                )
        );
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(
                        "Error: Missing Request Part",
                        "Request part: [" + ex.getRequestPartName() + "] missing"
                )
        );
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        NotFoundExceptionDto dto = NotFoundExceptionDto.myBuilder(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(
                        "Some internal Exception",
                        ex.getMessage()
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        String not_found_entity = "";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(
                        "Error: Entity Not Found ",
                        "Element in Table [" + not_found_entity + "] not found"
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlePostgresqlException(PSQLException e) {
        String exception = "";
        if (e.getMessage().contains("foreign key")) {
            exception = "Element has relation";
        } else if (e.getMessage().contains("duplicate")) {
            exception = "Duplicate Key Value";
        } else {
            exception = "Postgresql Exception";
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(exception, e.getMessage())
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNoSuchElement(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        "No Such Element",
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNullPointException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(
                        "Null Point Exception",
                        e.getMessage()
                )
        );
    }
}
