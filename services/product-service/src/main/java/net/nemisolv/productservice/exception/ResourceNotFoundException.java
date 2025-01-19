package net.nemisolv.productservice.exception;

import lombok.Getter;
import lombok.Setter;
import net.nemisolv.lib.util.ResultCode;

@Getter
@Setter

public class ResourceNotFoundException extends RuntimeException {
    private ResultCode resultCode;

    public ResourceNotFoundException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }
    public ResourceNotFoundException(ResultCode resultCode,String customMessage) {
        super(customMessage);
        this.resultCode = resultCode;
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.resultCode = ResultCode.RESOURCE_NOT_FOUND;
    }
}
