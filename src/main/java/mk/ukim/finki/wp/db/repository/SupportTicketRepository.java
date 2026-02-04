package mk.ukim.finki.wp.db.repository;

import mk.ukim.finki.wp.db.entity.SupportTicket;
import mk.ukim.finki.wp.db.entity.user.Administrator;
import mk.ukim.finki.wp.db.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket,Integer> {
    List<SupportTicket> findAllByUser(User user);
    List<SupportTicket> findAllByAdmin(Administrator admin);
}
