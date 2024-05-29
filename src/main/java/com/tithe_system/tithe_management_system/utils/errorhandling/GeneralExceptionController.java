package com.tithe_system.tithe_management_system.utils.errorhandling;

import com.tithe_system.tithe_management_system.utils.responses.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionController extends ResponseEntityExceptionHandler {

   private Logger logger = LoggerFactory.getLogger(this.getClass());

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(value = Exception.class)
   public CommonResponse handleException2(Exception ex) {

      ex.printStackTrace();

      logger.error("Error while processing your API request : {}", ex.getMessage());

      if (ex.getMessage().contains("Access is denied")) {
         return buildCommonResponseForAccessDenied();
      }

      CommonResponse commonResponse = new CommonResponse();
      commonResponse.setMessage("Sorry, We encountered an error while processing your request");
      commonResponse.setStatusCode(501);
      commonResponse.setSuccess(false);
      return commonResponse;
   }

   private CommonResponse buildCommonResponseForAccessDenied() {
      CommonResponse commonResponse = new CommonResponse();
      commonResponse.setMessage("You do not have rights to access the requested resource");
      commonResponse.setStatusCode(403);
      commonResponse.setSuccess(false);
      return commonResponse;
   }
   
}