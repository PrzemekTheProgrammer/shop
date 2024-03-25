package pl.com.coders.shop2.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotEnoughProductsException extends RuntimeException{

    public NotEnoughProductsException() {
        super();
    }

    public NotEnoughProductsException(String message) {
        super(message);
    }

    public NotEnoughProductsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughProductsException(Throwable cause) {
        super(cause);
    }
}
