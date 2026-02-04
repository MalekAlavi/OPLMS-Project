package mk.ukim.finki.wp.db.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.SubscriptionPlan;
import mk.ukim.finki.wp.db.service.SubscriptionPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    @GetMapping("/subscription-plans")
    public String getSubscriptionPlansPage(Model model) {
        model.addAttribute("plans", subscriptionPlanService.findAll());
        return "subscription/subscription_plans";
    }

    // Show add subscription plan form
    @GetMapping("/add/subscription-plan")
    public String getAddSubscriptionPlanPage() {
        return "subscription/subscription_plan_form";
    }

    @PostMapping("/add/subscription-plan")
    public String addSubscriptionPlan(@RequestParam String name,
                                      @RequestParam BigDecimal price,
                                      @RequestParam Integer durationMonths,
                                      @RequestParam String description) {
        subscriptionPlanService.addSubscriptionPlan(name, price, durationMonths, description);
        return "redirect:/subscription-plans";
    }

    @GetMapping("/edit/subscription-plan/{id}")
    public String getEditSubscriptionPlanPage(@PathVariable Integer id, Model model) {
        SubscriptionPlan plan = subscriptionPlanService.findById(id);
        model.addAttribute("plan", plan);
        return "subscription/subscription_plan_form";
    }

    @PostMapping("/edit/subscription-plan/{id}")
    public String editSubscriptionPlan(@PathVariable Integer id,
                                       @RequestParam String name,
                                       @RequestParam BigDecimal price,
                                       @RequestParam Integer durationMonths,
                                       @RequestParam String description) {
        subscriptionPlanService.editSubscriptionPlan(id, name, price, durationMonths, description, null); // pass null for accessType if not used
        return "redirect:/subscription-plans";
    }

    @PostMapping("/delete/subscription-plan/{id}")
    public String deleteSubscriptionPlan(@PathVariable Integer id) {
        subscriptionPlanService.deleteSubscriptionPlan(id);
        return "redirect:/subscription-plans";
    }
}
