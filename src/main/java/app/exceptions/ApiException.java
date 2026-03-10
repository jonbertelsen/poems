package app.exceptions;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class ApiException extends RuntimeException {
    private final int code;
    private static final Logger logger = LoggerFactory.getLogger(ApiException.class);

    public ApiException(int code, String msg){
        super(msg);
        this.code = code;
        logger.error("ApiException (code={}): {}", code, msg);
    }
}