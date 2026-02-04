package mk.ukim.finki.wp.db.repository;

import mk.ukim.finki.wp.db.entity.UserSubscription;
import mk.ukim.finki.wp.db.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription,Integer> {
    Optional<UserSubscription> findByUserAndStatus(User user, String status);
    List<UserSubscription> findByEndDateBeforeAndStatusNot(LocalDate date, String status);
}
