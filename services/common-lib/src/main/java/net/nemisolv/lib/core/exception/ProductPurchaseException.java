package net.nemisolv.lib.core.exception;

import lombok.Getter;
import lombok.Setter;
import net.nemisolv.lib.util.ResultCode;

@Getter @Setter
public class ProductPurchaseException extends RuntimeException {
    private ResultCode resultCode;
    public ProductPurchaseException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public ProductPurchaseException(ResultCode resultCode, String customMessage) {
        super(customMessage);
        this.resultCode = resultCode;
    }
}
