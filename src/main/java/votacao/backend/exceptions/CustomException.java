package votacao.backend.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class CustomException extends RuntimeException{

    private HttpStatus statusCode;

    public CustomException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public Object getMessageError(){
        return Map.of("mensagem", getMessage());
    }

    public HttpStatus getStatusCode(){
        return this.statusCode;
    }
}
