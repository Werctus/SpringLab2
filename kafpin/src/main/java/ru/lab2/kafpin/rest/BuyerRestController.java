package ru.lab2.kafpin.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.lab2.kafpin.dto.BuyerDTO;
import ru.lab2.kafpin.dto.BuyerResponse;
import ru.lab2.kafpin.Address;
import ru.lab2.kafpin.Buyer;
import ru.lab2.kafpin.PaymentInfo;
import ru.lab2.kafpin.repository.AddressRepository;
import ru.lab2.kafpin.repository.BuyerRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buyers")
public class BuyerRestController {

    private final BuyerRepository buyerRepository;
    private final AddressRepository addressRepository;

    public BuyerRestController(BuyerRepository buyerRepository,
                               AddressRepository addressRepository) {
        this.buyerRepository = buyerRepository;
        this.addressRepository = addressRepository;
    }

    // GET ALL
    @GetMapping
    public List<BuyerResponse> getAllBuyers() {
        return buyerRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public BuyerResponse getBuyerById(@PathVariable Long id) {
        Buyer buyer = buyerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Покупатель не найден"));
        return toResponse(buyer);
    }

    // CREATE (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BuyerResponse createBuyer(@Valid @RequestBody BuyerDTO dto) {
        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Адрес не найден"));

        Buyer buyer = new Buyer();
        // Переносим все поля
        buyer.setLastName(dto.getLastName());
        buyer.setFirstName(dto.getFirstName());
        buyer.setMiddleName(dto.getMiddleName());
        buyer.setGender(dto.getGender());
        buyer.setNationality(dto.getNationality());
        buyer.setHeight(dto.getHeight());
        buyer.setWeight(dto.getWeight());
        buyer.setBirthDate(dto.getBirthDate());
        buyer.setPhone(dto.getPhone());
        buyer.setAddress(address);

        // Создаем платежную информацию
        PaymentInfo payment = new PaymentInfo();
        payment.setCardNumber(dto.getCardNumber());
        payment.setBankAccount(dto.getBankAccount());
        buyer.setPaymentInfo(payment);

        return toResponse(buyerRepository.save(buyer));
    }

    // UPDATE (PUT)
    @PutMapping("/{id}")
    public BuyerResponse updateBuyer(@PathVariable Long id, @Valid @RequestBody BuyerDTO dto) {
        Buyer buyer = buyerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Покупатель не найден"));

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Адрес не найден"));

        buyer.setLastName(dto.getLastName());
        buyer.setFirstName(dto.getFirstName());
        buyer.setMiddleName(dto.getMiddleName());
        buyer.setGender(dto.getGender());
        buyer.setNationality(dto.getNationality());
        buyer.setHeight(dto.getHeight());
        buyer.setWeight(dto.getWeight());
        buyer.setBirthDate(dto.getBirthDate());
        buyer.setPhone(dto.getPhone());
        buyer.setAddress(address);

        PaymentInfo payment = buyer.getPaymentInfo();
        if (payment == null) {
            payment = new PaymentInfo();
        }
        payment.setCardNumber(dto.getCardNumber());
        payment.setBankAccount(dto.getBankAccount());
        buyer.setPaymentInfo(payment);

        return toResponse(buyerRepository.save(buyer));
    }

    // PARTIAL UPDATE (PATCH)
    @PatchMapping("/{id}")
    public BuyerResponse partialUpdateBuyer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Buyer buyer = buyerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Покупатель не найден"));

        // Обновляем только те поля, которые переданы
        if (updates.containsKey("lastName")) buyer.setLastName((String) updates.get("lastName"));
        if (updates.containsKey("firstName")) buyer.setFirstName((String) updates.get("firstName"));
        if (updates.containsKey("middleName")) buyer.setMiddleName((String) updates.get("middleName"));
        if (updates.containsKey("gender")) buyer.setGender((String) updates.get("gender"));
        if (updates.containsKey("nationality")) buyer.setNationality((String) updates.get("nationality"));
        if (updates.containsKey("height")) buyer.setHeight((Integer) updates.get("height"));
        if (updates.containsKey("weight")) buyer.setWeight((Integer) updates.get("weight"));
        if (updates.containsKey("phone")) buyer.setPhone((String) updates.get("phone"));

        if (updates.containsKey("addressId")) {
            Long newAddressId = Long.valueOf(updates.get("addressId").toString());
            Address address = addressRepository.findById(newAddressId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Адрес не найден"));
            buyer.setAddress(address);
        }

        if (buyer.getPaymentInfo() != null) {
            if (updates.containsKey("cardNumber")) buyer.getPaymentInfo().setCardNumber((String) updates.get("cardNumber"));
            if (updates.containsKey("bankAccount")) buyer.getPaymentInfo().setBankAccount((String) updates.get("bankAccount"));
        }

        return toResponse(buyerRepository.save(buyer));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBuyer(@PathVariable Long id) {
        if (!buyerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Покупатель не найден");
        }
        buyerRepository.deleteById(id);
    }

    // STATISTICS (Сложная логика)
    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        List<Buyer> allBuyers = buyerRepository.findAll();

        long total = allBuyers.size();

        // Самый популярный город
        String popularCity = allBuyers.stream()
                .filter(b -> b.getAddress() != null && b.getAddress().getCity() != null)
                .collect(Collectors.groupingBy(b -> b.getAddress().getCity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Нет данных");

        // Средний рост и вес
        double avgHeight = allBuyers.stream()
                .filter(b -> b.getHeight() != null)
                .mapToInt(Buyer::getHeight)
                .average().orElse(0.0);

        double avgWeight = allBuyers.stream()
                .filter(b -> b.getWeight() != null)
                .mapToInt(Buyer::getWeight)
                .average().orElse(0.0);

        return Map.of(
                "totalBuyers", total,
                "mostPopularCity", popularCity,
                "averageHeight", Math.round(avgHeight * 10.0) / 10.0,
                "averageWeight", Math.round(avgWeight * 10.0) / 10.0
        );
    }

    // ВСПОМОГАТЕЛЬНЫЙ МЕТОД МАППИНГА Entity -> Response
    private BuyerResponse toResponse(Buyer buyer) {
        BuyerResponse r = new BuyerResponse();
        r.setId(buyer.getId());
        r.setLastName(buyer.getLastName());
        r.setFirstName(buyer.getFirstName());
        r.setMiddleName(buyer.getMiddleName());
        r.setGender(buyer.getGender());
        r.setNationality(buyer.getNationality());
        r.setHeight(buyer.getHeight());
        r.setWeight(buyer.getWeight());
        r.setBirthDate(buyer.getBirthDate());
        r.setPhone(buyer.getPhone());

        if (buyer.getAddress() != null) {
            r.setAddressId(buyer.getAddress().getId());
            r.setCity(buyer.getAddress().getCity());
            r.setStreet(buyer.getAddress().getStreet());
        }
        if (buyer.getPaymentInfo() != null) {
            r.setPaymentInfoId(buyer.getPaymentInfo().getId());
            // Маскируем номер карты: **** 1234
            String card = buyer.getPaymentInfo().getCardNumber();
            if (card != null && card.length() >= 4) {
                r.setCardNumberMasked("**** " + card.substring(card.length() - 4));
            } else {
                r.setCardNumberMasked(card);
            }
        }
        return r;
    }
}