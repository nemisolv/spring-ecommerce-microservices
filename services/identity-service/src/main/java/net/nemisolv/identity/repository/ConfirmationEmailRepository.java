package net.nemisolv.identity.repository;


import net.nemisolv.identity.entity.ConfirmationEmail;
import net.nemisolv.lib.core._enum.MailType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConfirmationEmailRepository extends CrudRepository<ConfirmationEmail, Long> {
    List<ConfirmationEmail> findByUserId(Long userId);

    Optional<ConfirmationEmail> findByUserIdAndToken(Long userId, String token);

    List<ConfirmationEmail> findByTypeAndUserId(MailType type, Long userId);

    Optional<ConfirmationEmail> findByToken(String token);

    // Custom query to delete revoked, expired, or confirmed confirmation emails
    @Modifying
    @Query("DELETE FROM ConfirmationEmail ce WHERE ce.revoked = true OR ce.expiredAt < :now OR ce.confirmedAt < :now")
    int deleteByRevokedTrueOrExpiredAtBeforeOrConfirmedAtBefore(LocalDateTime now);
}
