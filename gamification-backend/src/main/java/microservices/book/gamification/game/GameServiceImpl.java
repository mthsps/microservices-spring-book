package microservices.book.gamification.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.gamification.game.badgeprocessors.BadgeProcessor;
import microservices.book.gamification.game.domain.BadgeCard;
import microservices.book.gamification.game.domain.BadgeType;
import microservices.book.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    @Transactional
    public GameResult newAttemptForUser(final ChallengeSolvedEvent challenge) {
        if (challenge.isCorrect()) {
            ScoreCard scoreCard = new ScoreCard(challenge.getUserId(), challenge.getAttemptId());
            scoreRepository.save(scoreCard);
            log.info("User {} scored {} points for attempt id {}",
                    challenge.getUserAlias(), scoreCard.getScore(),
                    challenge.getAttemptId());
            List<BadgeCard> badgeCards = processForBadges(challenge);
            return new GameResult(scoreCard.getScore(),
                    badgeCards.stream()
                            .map(BadgeCard::getBadgeType)
                            .collect(Collectors.toList()));
        } else {
            log.info("Attempt id {} is not correct. User {} does not get score",
                    challenge.getAttemptId(), challenge.getUserAlias());
            return new GameResult(0, List.of());
        }
    }

    private List<BadgeCard> processForBadges(final ChallengeSolvedEvent solvedChallenge) {
        Optional<Integer> optTotalScore = scoreRepository
                .getTotalScoreForUser(solvedChallenge.getUserId());
        if (optTotalScore.isEmpty()) {
            return Collections.emptyList();
        }
        int totalScore = optTotalScore.get();

        List<ScoreCard> scoreCards = scoreRepository
                .findByUserIdOrderByScoreTimestampDesc(solvedChallenge.getUserId());

        Set<BadgeType> alreadyGotBadges = badgeRepository
                .findByUserIdOrderByBadgeTimestampDesc(solvedChallenge.getUserId())
                .stream()
                .map(BadgeCard::getBadgeType)
                .collect(Collectors.toSet());

        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
                .filter(badgeProcessor -> !alreadyGotBadges.contains(badgeProcessor.badgeType()))
                .map(badgeProcessor -> badgeProcessor.processForOptionalBadge(totalScore, scoreCards, solvedChallenge))
                .flatMap(Optional::stream)
                .map(badgeType -> new BadgeCard(solvedChallenge.getUserId(), badgeType))
                .collect(Collectors.toList());

        badgeRepository.saveAll(newBadgeCards);

        return newBadgeCards;
    }


}
