package mk.ukim.finki.wp.db.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.service.SubscriptionPlanService;
import mk.ukim.finki.wp.db.service.UserSubscriptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;
    private final SubscriptionPlanService subscriptionPlanService;

    @GetMapping("/user-subscription")
    public String getUserSubscriptionPage(Model model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        model.addAttribute("plans", subscriptionPlanService.findAll());
        model.addAttribute("alreadySubscribed", userSubscriptionService.hasUserSubscription(email));
        return "user_subscription/user_subscription";
    }

    @GetMapping("/user-subscription-payment/{id}")
    public String getPaymentPage(@PathVariable Integer id, Model model) {
        model.addAttribute("plan", subscriptionPlanService.findById(id));
        return "user_subscription/user_subscription_payment";
    }

    @PostMapping("/user-subscription-payment/{id}")
    public String subscribe(@PathVariable Integer id, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        userSubscriptionService.addUserSubscription(email, id);
        return "redirect:/user-subscription";
    }
}
