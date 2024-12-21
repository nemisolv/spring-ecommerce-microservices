package net.nemisolv.identity.exception;

import lombok.Getter;
import lombok.Setter;
import net.nemisolv.lib.util.ResultCode;

@Getter
@Setter

public class BadRequestException extends RuntimeException {
    private ResultCode resultCode;
    public BadRequestException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public BadRequestException(ResultCode resultCode,String customMessage) {
        super(customMessage);
        this.resultCode = resultCode;
    }
}
