
package ru.lab2.kafpin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lab2.kafpin.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
