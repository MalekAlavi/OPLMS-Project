package mk.ukim.finki.wp.db.repository;

import mk.ukim.finki.wp.db.entity.user.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Integer> {
}
