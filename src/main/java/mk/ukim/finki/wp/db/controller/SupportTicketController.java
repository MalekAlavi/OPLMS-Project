package mk.ukim.finki.wp.db.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.service.SupportTicketService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    @GetMapping("/support-ticket")
    public String getSupportTicketPage(Model model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        model.addAttribute("tickets", supportTicketService.findAll(email));

        boolean loggedIn = authentication != null && authentication.isAuthenticated();
        model.addAttribute("loggedIn", loggedIn);

        String userRole = null;

        if (loggedIn) {
            userRole = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse(null);
        }

        model.addAttribute("userRole", userRole);
        return "support_ticket/support_ticket";
    }

    @GetMapping("/add/support-ticket")
    public String getAddSupportTicketPage() {
        return "support_ticket/support_ticket_form";
    }

    @PostMapping("/add/support-ticket")
    public String addSupportTicket(@RequestParam String subject,
                                   @RequestParam String description,
                                   Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        supportTicketService.addSupportTicket(email, subject, description);
        return "redirect:/support-ticket";
    }

    @PostMapping("/resolve/support-ticket/{id}")
    public String resolveSupportTicket(@PathVariable Integer id) {
        supportTicketService.resolveSupportTicket(id);
        return "redirect:/support-ticket";
    }
}
