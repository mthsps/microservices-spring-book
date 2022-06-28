package microservices.book.gamification.game;

import microservices.book.gamification.game.domain.LeaderBoardRow;
import microservices.book.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository <ScoreCard, Long> {
    Optional<Integer> getTotalScoreForUser(long userId);

    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(long userId);

    List<LeaderBoardRow> findFirst10();
}
