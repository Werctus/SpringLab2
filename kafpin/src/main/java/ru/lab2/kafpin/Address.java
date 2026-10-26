package ru.lab2.kafpin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Почтовый индекс обязателен!")
    @Pattern(regexp = "\\d{6}", message = "Индекс должен содержать 6 цифр")
    @Column(name = "postal_index", nullable = false)
    private String postalIndex;

    @NotBlank(message = "Страна обязательна!")
    private String country;

    @NotBlank(message = "Область обязательна!")
    private String region;

    @NotBlank(message = "Район обязателен")
    private String district;

    @NotBlank(message = "Город обязателен")
    private String city;

    @NotBlank(message = "Улица обязателен")
    private String street;

    @NotBlank(message = "Дом обязателен")
    private String home;

    @NotBlank(message = "Квартира обязателен")
    private String apartment;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Buyer> buyers = new ArrayList<>();

    public void addBuyer(Buyer buyer) {
        buyers.add(buyer);
        buyer.setAddress(this);
    }

    public void removeBuyer(Buyer buyer) {
        buyers.remove(buyer);
        buyer.setAddress(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostalIndex() {
        return postalIndex;
    }

    public void setPostalIndex(String postalIndex) {
        this.postalIndex = postalIndex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public List<Buyer> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<Buyer> buyers) {
        this.buyers = buyers;
    }
}
