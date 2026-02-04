package mk.ukim.finki.wp.db.repository;

import mk.ukim.finki.wp.db.entity.Certificate;
import mk.ukim.finki.wp.db.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    Optional<Certificate> findByEnrollment(Enrollment enrollment);
}
