package br.com.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClassException extends Exception{
    private String msg;
    private HttpStatus status;
    public ClassException(String msg, HttpStatus status){
        this.msg = msg;
        this.status = status;
    }
}
