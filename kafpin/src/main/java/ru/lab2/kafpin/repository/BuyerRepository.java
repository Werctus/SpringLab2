package ru.lab2.kafpin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lab2.kafpin.model.Buyer;

import java.util.List;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    @Query("SELECT b FROM Buyer b WHERE " +
            "(:lastName IS NULL OR LOWER(b.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
            "(:city IS NULL OR LOWER(b.address.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND " +
            "(:gender IS NULL OR b.gender = :gender) AND " +
            "(:nationality IS NULL OR LOWER(b.nationality) LIKE LOWER(CONCAT('%', :nationality, '%')))")
    List<Buyer> findWithFilters(
            @Param("lastName") String lastName,
            @Param("city") String city,
            @Param("gender") String gender,
            @Param("nationality") String nationality
    );
}