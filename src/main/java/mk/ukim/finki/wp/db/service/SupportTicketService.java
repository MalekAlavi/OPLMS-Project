package mk.ukim.finki.wp.db.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.SupportTicket;
import mk.ukim.finki.wp.db.entity.user.Administrator;
import mk.ukim.finki.wp.db.entity.user.User;
import mk.ukim.finki.wp.db.entity.user.UserEntity;
import mk.ukim.finki.wp.db.entity.user.enums.Role;
import mk.ukim.finki.wp.db.repository.AdministratorRepository;
import mk.ukim.finki.wp.db.repository.SupportTicketRepository;
import mk.ukim.finki.wp.db.repository.UserEntityRepository;
import mk.ukim.finki.wp.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final UserRepository userRepository;
    private final UserEntityRepository userEntityRepository;
    private final AdministratorRepository administratorRepository;

    public List<SupportTicket> findAll(String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email).get();

        if(userEntity.getRole() == Role.ADMIN) {
            return findAllByAdmin(userEntity);
        } else {
            return findAllByUser(userEntity);
        }
    }

    public List<SupportTicket> findAllByUser(UserEntity userEntity) {
        User user = userRepository.findById(userEntity.getId()).get();
        return supportTicketRepository.findAllByUser(user);
    }

    public List<SupportTicket> findAllByAdmin(UserEntity userEntity) {
        Administrator admin = administratorRepository.findById(userEntity.getId()).get();
        return supportTicketRepository.findAllByAdmin(admin);
    }

    public void addSupportTicket(String email, String subject, String description) {
        UserEntity userEntity = userEntityRepository.findByEmail(email).get();
        User user = userRepository.findById(userEntity.getId()).get();

        List<Administrator> admins = administratorRepository.findAll();

        Random random = new Random();
        Administrator assignedAdmin = admins.get(random.nextInt(admins.size()));

        SupportTicket supportTicket = new SupportTicket();
        supportTicket.setUser(user);
        supportTicket.setAdmin(assignedAdmin);
        supportTicket.setSubject(subject);
        supportTicket.setDescription(description);
        supportTicket.setStatus("UNRESOLVED");
        supportTicket.setCreatedAt(LocalDateTime.now());
        supportTicketRepository.save(supportTicket);
    }

    public void resolveSupportTicket(Integer ticketId) {
        SupportTicket supportTicket = supportTicketRepository.findById(ticketId).get();
        supportTicket.setStatus("RESOLVED");
        supportTicketRepository.save(supportTicket);
    }
}
