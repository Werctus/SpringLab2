/*
package ru.lab2.kafpin.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lab2.kafpin.model.Buyer;
import ru.lab2.kafpin.repository.BuyerJdbcRepository;

import java.util.List;

@RestController
@RequestMapping("/api/jdbc/buyers")
public class BuyerJdbcController {

    private final BuyerJdbcRepository jdbcRepository;

    public BuyerJdbcController(BuyerJdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @GetMapping
    public List<Buyer> getAll() {
        return jdbcRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buyer> getById(@PathVariable Long id) {
        return jdbcRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Buyer create(@RequestBody Buyer buyer) {
        Long newId = jdbcRepository.save(buyer);
        buyer.setId(newId);
        return buyer;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buyer> update(@PathVariable Long id, @RequestBody Buyer buyer) {
        if (jdbcRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        buyer.setId(id);
        jdbcRepository.update(buyer);
        return ResponseEntity.ok(buyer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        jdbcRepository.deleteById(id);
    }
}*/
