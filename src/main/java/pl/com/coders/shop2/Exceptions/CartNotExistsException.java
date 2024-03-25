package pl.com.coders.shop2.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CartNotExistsException extends RuntimeException{

    public CartNotExistsException() {
        super();
    }

    public CartNotExistsException(String message) {
        super(message);
    }

    public CartNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartNotExistsException(Throwable cause) {
        super(cause);
    }
}
