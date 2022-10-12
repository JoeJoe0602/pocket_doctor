package com.jolin.common.exception;

import com.jolin.common.dto.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Framework unity exception
 * <p>
 * Error status in the servlet
 * javax.servlet.error.status_code             Type is Integer        Error status code
 * javax.servlet.error.exception_type          Type is Class          Type of exception
 * javax.servlet.error.message                 Type is String         Information of exception
 * javax.servlet.error.exception               Type is Throwable      Exception class
 * javax.servlet.error.request_uri             Type is String         Page where an exception occurs
 * javax.servlet.error.servlet_name            Type is String         The name of servlet where an exception occurs
 * <p>
 */
public abstract class BaseExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    private static final String ERROR_MES_SEPARATOR = "####";
    private final String message = "The server is out of order";


    /**
     * Base exceptions, which are caught and handled by this handler if the actual item is not overridden
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<ResultDTO> baseExceptionHandler(HttpServletRequest request, BaseException ex) {

        // Framework custom exceptions are service exceptions and return status 200
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ResultDTO<>(ex.getCode(), ex.getMessage(), ""), HttpStatus.OK);
    }

    /**
     * Unique index exception catching
     * @param ex
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResultDTO> dataIntegrityViolationExceptionExceptionHandler(DataIntegrityViolationException  ex) {
        // Framework custom exceptions are service exceptions and return status 200
        logger.error(ex.getMessage(),ex);
        Throwable cause = ex.getCause();
        String errorMessage = "";
        if (cause instanceof SQLIntegrityConstraintViolationException) {
            errorMessage = cause.getMessage();
        }
        if (!(cause instanceof SQLIntegrityConstraintViolationException)) {
            errorMessage = cause.getCause().getMessage();
        }

        // Match the Chinese character re
        String reg = "[u4E00-u9FA5]";
        // Matches letters and single quotes re
        String rega = "[a-zA-Z_\']";

        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(errorMessage);
        String msg = "";
        // If Chinese is included
        if (matcher.find()) {
            msg = errorMessage;
            // Replace non-Chinese characters with empty ones
            msg = msg.replaceAll(rega,"").trim()+ "repeat";
            return new ResponseEntity<>(new ResultDTO<>(40001,msg , ""), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResultDTO<>(40001,message , ""), HttpStatus.OK);
        }
    }

    /**
     * General method parameter check exception catch
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage(), ex);
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        return new ResponseEntity<>(new ResultDTO<>(400, allErrors.get(0).getDefaultMessage(), ""), HttpStatus.OK);
    }

    /**
     * General method parameter check exception catch
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResultDTO> handleValidationExceptions(ValidationException ex) {
        logger.error(ex.getMessage(), ex);
        String message = ex.getMessage();
        //Matching Chinese character Re
        String reg = "[u4E00-u9FA5]";


        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(message);
        String rega = "[a-zA-Z_\'_\\[_\\]_[0-8]_._:]";
        if (matcher.find()) {
            message = message.replaceAll(rega,"").trim();
            return new ResponseEntity<>(new ResultDTO<>(400, message, ""), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResultDTO<>(400, this.message, ""), HttpStatus.OK);
    }

    /**
     * The most basic abnormal encapsulation, play the role of "bottom"
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResultDTO> runtimeExceptionHandler(HttpServletRequest request, Exception ex) {
        HttpStatus status = getStatus(request);
        String messageDetail = ex.getMessage();

        String msg = message;

        //TODO determines whether a friendly description is included. Check whether it contains Chinese, and if it does, concatenate it into message
        Matcher m = Pattern.compile("[\u4e00-\u9fa5]").matcher(messageDetail);
        if (m.find()) {
            msg = messageDetail;
        }
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ResultDTO<>(status.value(), msg , ""), status);
    }

    public HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}


