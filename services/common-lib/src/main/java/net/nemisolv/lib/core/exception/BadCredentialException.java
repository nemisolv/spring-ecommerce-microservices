package net.nemisolv.lib.core.exception;

import lombok.Getter;
import lombok.Setter;
import net.nemisolv.lib.util.ResultCode;

@Getter @Setter
public class BadCredentialException extends RuntimeException {
    private ResultCode resultCode;
    public BadCredentialException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public BadCredentialException(ResultCode resultCode,String customMessage) {
        super(customMessage);
        this.resultCode = resultCode;
    }
}
