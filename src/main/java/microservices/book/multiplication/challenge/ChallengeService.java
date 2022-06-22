package microservices.book.multiplication.challenge;

import org.springframework.stereotype.Service;


public interface ChallengeService {
    /**
     * Verifies if an attempt coming from the presentation layer is correct or not.
     *
     * @return the resulting ChallengeAttempt object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);
}
