package GYProject.gyvue_test_project.repository;

import GYProject.gyvue_test_project.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment,Integer> {
}
