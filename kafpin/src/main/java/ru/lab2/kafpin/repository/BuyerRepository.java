package ru.lab2.kafpin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lab2.kafpin.Buyer;

import java.util.List;

public interface BuyerRepository
    extends JpaRepository<Buyer, Long> {

    // Поиск по фамилии (без учёта регистра, часть строки)
    List<Buyer> findByLastNameContainsIgnoreCase(String lastName);

    // Поиск по городу (через связанную сущность Address)
    List<Buyer> findByAddressCityIgnoreCase(String city);

    // Комбинированный поиск по полу и национальности
    List<Buyer> findByGenderAndNationalityIgnoreCase(String gender, String nationality);

}
