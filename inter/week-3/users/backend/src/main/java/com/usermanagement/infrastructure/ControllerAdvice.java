package com.usermanagement.infrastructure;

import com.usermanagement.feature.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Tymur Berezhnoi.
 */
@RestControllerAdvice
public class ControllerAdvice {

    private final MessageSource messageSource;

    @Autowired
    public ControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handle java bean validation exception.
     *
     * @param exception an exception of java bean validation
     * @return list of response messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public List<ResponseMessageDTO> handleValidationError(MethodArgumentNotValidException exception) {
        var bindingResult = exception.getBindingResult();
        var fieldErrors = bindingResult.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class, UsernameNotFoundException.class, EntityNotFoundException.class,
                               IllegalStateException.class, EntityExistsException.class})
    @ResponseStatus(BAD_REQUEST)
    public ResponseMessageDTO exceptionProcessor(RuntimeException exception) {
        return new ResponseMessageDTO(exception.getMessage());
    }

    /**
     * Process validation errors.
     *
     * @param fieldErrors the field errors that may occurs during validation.
     * @return a list of error messages or empty list if validation success.
     */
    private List<ResponseMessageDTO> processFieldErrors(List<FieldError> fieldErrors) {
        var result = new ArrayList<ResponseMessageDTO>();

        if(fieldErrors != null) {
            for(var fieldError: fieldErrors) {
                var currentLocale = LocaleContextHolder.getLocale();
                var localizedMessage = messageSource.getMessage(fieldError.getDefaultMessage(), new Object[] {}, currentLocale);
                result.add(new ResponseMessageDTO(localizedMessage));
            }
        }
        return result;
    }
}
