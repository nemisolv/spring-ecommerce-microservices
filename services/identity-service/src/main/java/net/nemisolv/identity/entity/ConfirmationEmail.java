package net.nemisolv.identity.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.nemisolv.lib.core._enum.MailType;

import java.time.LocalDateTime;

@Entity
@Table(name = "confirmation_emails")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ConfirmationEmail extends IdBaseEntity {

    // don't need to reference user object here ->
   private Long userId;

   // token or ( otp + auth_secret hashing with sha 256)
    private String token;

    @Enumerated(EnumType.STRING)
    private MailType type;

    private boolean revoked;



    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
    @Column(name = "expired_at")

    private LocalDateTime expiredAt;



}
