package net.xzh.jwt.common.exception;

/**
 * Created 2020/6/23.
 */
public class JwtExpiredException extends RuntimeException{
    public JwtExpiredException(String message) {
        super(message);
    }
}
