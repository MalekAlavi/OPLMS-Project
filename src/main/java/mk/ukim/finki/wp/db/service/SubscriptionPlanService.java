package mk.ukim.finki.wp.db.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.SubscriptionPlan;
import mk.ukim.finki.wp.db.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public List<SubscriptionPlan> findAll() {
        return subscriptionPlanRepository.findAll();
    }

    public SubscriptionPlan findById(Integer id) {
        return subscriptionPlanRepository.findById(id).get();
    }

    public void addSubscriptionPlan(String name, BigDecimal price, Integer durationMonths, String description) {
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setName(name);
        subscriptionPlan.setPrice(price);
        subscriptionPlan.setDurationMonths(durationMonths);
        subscriptionPlan.setDescription(description);
        subscriptionPlanRepository.save(subscriptionPlan);
    }

    public void editSubscriptionPlan(Integer id, String name, BigDecimal price, Integer durationMonths, String description, String accessType) {
        SubscriptionPlan subscriptionPlan = findById(id);
        subscriptionPlan.setName(name);
        subscriptionPlan.setPrice(price);
        subscriptionPlan.setDurationMonths(durationMonths);
        subscriptionPlan.setDescription(description);
        subscriptionPlanRepository.save(subscriptionPlan);
    }

    public void deleteSubscriptionPlan(Integer id) {
        subscriptionPlanRepository.deleteById(id);
    }

}
