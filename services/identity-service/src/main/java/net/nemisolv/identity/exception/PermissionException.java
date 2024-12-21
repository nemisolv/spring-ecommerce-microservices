package net.nemisolv.lib.core.exception;

import lombok.Getter;
import lombok.Setter;
import net.nemisolv.lib.util.ResultCode;

@Getter
@Setter

public class PermissionException extends RuntimeException {
    private ResultCode resultCode;
    public PermissionException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public PermissionException(ResultCode resultCode, String customMessage) {
        super(customMessage);
        this.resultCode = resultCode;
    }

    public PermissionException(String message) {
        super(message);
        this.resultCode = ResultCode.USER_PERMISSION_ERROR;
    }
}
