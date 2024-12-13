package net.nemisolv.identity.payload.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class VerificationMfaRequest {
    @Length(min=6,message = "Code must contain only 6 characters")
    private String code;
    @NotBlank(message = "Email can not be empty")
    private String email;

}
