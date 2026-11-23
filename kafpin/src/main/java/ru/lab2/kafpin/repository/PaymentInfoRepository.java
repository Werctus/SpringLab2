
package ru.lab2.kafpin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lab2.kafpin.model.PaymentInfo;

public interface PaymentInfoRepository extends CrudRepository<PaymentInfo, Long> {
}
