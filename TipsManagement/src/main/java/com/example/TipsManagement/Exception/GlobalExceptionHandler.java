package com.example.TipsManagement.Exception;

import com.example.TipsManagement.model.dto.Response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Esse método será executado quando ocorrer BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(
            //exceção capturada(mensagem e dados do erro)
            BusinessException e,
            //Objeto da requisição HTTP atual (URL, headers etc.)
            HttpServletRequest request) {
        //Cria objeto padrão de resposta de erro da API
        ErrorResponse error = new ErrorResponse(
                //Código HTTP que queremos retornar
                409,
                //Texto descritivo do status HTTP
                "Conflict",
                // pega a mensagem da exceção
                e.getMessage(),
                // URI do endpoint que gerou o erro
                request.getRequestURI()
        );

        // Retorna a resposta HTTP com status 409 e o JSON contendo o erro
        return ResponseEntity
                // Define explicitamente o status HTTP da resposta
                .status(HttpStatus.CONFLICT)

                // Define o corpo da resposta (objeto será serializado para JSON)
                .body(error);

    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException e,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                400,
                "Bad Request",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NotFoundException e,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                404,
                "Not Found",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}

