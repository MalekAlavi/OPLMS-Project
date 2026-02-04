package mk.ukim.finki.wp.db.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.Payment;
import mk.ukim.finki.wp.db.entity.SubscriptionPlan;
import mk.ukim.finki.wp.db.entity.UserSubscription;
import mk.ukim.finki.wp.db.entity.user.User;
import mk.ukim.finki.wp.db.entity.user.UserEntity;
import mk.ukim.finki.wp.db.repository.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final PaymentRepository paymentRepository;

    public boolean hasUserSubscription(String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        User user = userRepository.findById(userEntity.getId()).get();
        Optional<UserSubscription> optionalUserSubscription = userSubscriptionRepository.findByUserAndStatus(user, "ACTIVE");

        return optionalUserSubscription.isPresent();
    }

    public void addUserSubscription(String email, Integer subscriptionPlanId) {
        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        User user = userRepository.findById(userEntity.getId()).get();
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(subscriptionPlanId).get();

        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUser(user);
        userSubscription.setPlan(subscriptionPlan);
        userSubscription.setStartDate(LocalDate.now());
        userSubscription.setEndDate(LocalDate.now().plusMonths(subscriptionPlan.getDurationMonths()));
        userSubscription.setStatus("ACTIVE");

        userSubscriptionRepository.save(userSubscription);

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setSubscription(userSubscription);
        payment.setAmount(subscriptionPlan.getPrice());

        paymentRepository.save(payment);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void checkExpiredSubscriptions() {
        LocalDate today = LocalDate.now();
        List<UserSubscription> expiredSubscriptions = userSubscriptionRepository
                .findByEndDateBeforeAndStatusNot(today, "EXPIRED");

        for (UserSubscription subscription : expiredSubscriptions) {
            subscription.setStatus("EXPIRED");
            userSubscriptionRepository.save(subscription);
            System.out.println("Subscription " + subscription.getSubscriptionId() + " expired.");
        }

        System.out.println("Checked subscriptions on: " + today);
    }
}
