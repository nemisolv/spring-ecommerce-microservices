package net.nemisolv.lib.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.techshop.repository.ConfirmationEmailRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final ConfirmationEmailRepository confirmationEmailRepository;

    // A scheduled task to clean up revoked, expired or confirmed confirmation emails every week (cron expression for every Sunday at midnight)
    @Scheduled(cron = "0 0 0 * * SUN")
    public void cleanUpStaleConfirmationEmails() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Starting cleanup for revoked, expired, or confirmed confirmation emails...");

        int deletedCount = confirmationEmailRepository.deleteByRevokedTrueOrExpiredAtBeforeOrConfirmedAtBefore(now);

        log.info("Cleanup complete. {} confirmation emails deleted.", deletedCount);
    }
}
