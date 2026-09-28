package ru.lab2.kafpin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lab2.kafpin.Buyer;

public interface BuyerRepository
    extends CrudRepository<Buyer, Long> {}
