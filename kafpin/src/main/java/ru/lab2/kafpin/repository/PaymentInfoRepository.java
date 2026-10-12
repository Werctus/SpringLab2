package ru.lab2.kafpin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lab2.kafpin.PaymentInfo;

public interface PaymentInfoRepository extends CrudRepository<PaymentInfo, Long> {
}