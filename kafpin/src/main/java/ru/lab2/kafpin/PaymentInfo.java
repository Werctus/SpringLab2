package ru.lab2.kafpin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "payment_info")
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Номер кредитной карточки обязателен")
    @Pattern(regexp = "\\d{16}", message = "Номер карты должен содержать 16 цифр")
    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @NotBlank(message = "Номер банковского счёта обязателен")
    @Pattern(regexp = "\\d{20}", message = "Номер счёта должен содержать 20 цифр")
    @Column(name = "bank_account", nullable = false, unique = true)
    private String bankAccount;

    @OneToOne(mappedBy = "paymentInfo", fetch = FetchType.LAZY)
    @JsonBackReference
    private Buyer buyer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }
}