package GYProject.gyvue_test_project.controller;

import GYProject.gyvue_test_project.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import GYProject.gyvue_test_project.repository.PaymentRepository;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentHandler {
    @Autowired
    private PaymentRepository paymentRepository;
    @GetMapping("/findAll")
    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }
}
