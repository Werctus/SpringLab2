package ru.lab2.kafpin.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class BuyerDTO {

    @NotBlank(message = "Фамилия обязательна")
    @Size(min = 2, max = 50, message = "Фамилия от 2 до 50 символов")
    private String lastName;

    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 50, message = "Имя от 2 до 50 символов")
    private String firstName;

    @NotBlank(message = "Отчество обязательно")
    @Size(min = 2, max = 50, message = "Отчество от 2 до 50 символов")
    private String middleName;

    @NotBlank(message = "Пол обязателен")
    @Pattern(regexp = "М|Ж", message = "Пол должен быть М или Ж")
    private String gender;

    @NotBlank(message = "Национальность обязательна")
    private String nationality;

    @NotNull(message = "Рост обязателен")
    @Min(value = 50, message = "Рост не менее 50 см")
    @Max(value = 250, message = "Рост не более 250 см")
    private Integer height;

    @NotNull(message = "Вес обязателен")
    @Min(value = 3, message = "Вес не менее 3 кг")
    @Max(value = 300, message = "Вес не более 300 кг")
    private Integer weight;

    @NotNull(message = "Дата рождения обязательна")
    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate birthDate;

    @NotBlank(message = "Телефон обязателен")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Некорректный номер телефона")
    private String phone;

    @NotNull(message = "ID адреса обязателен")
    private Long addressId;

    // Данные для платежной информации
    @NotBlank(message = "Номер карты обязателен")
    private String cardNumber;

    @NotBlank(message = "Банковский счет обязателен")
    private String bankAccount;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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
}