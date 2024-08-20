package com.application.handler.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidEmailException extends RuntimeException  {

    public InvalidEmailException(String message)
    {
        super(message);
    }


}
