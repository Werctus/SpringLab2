package ru.lab2.kafpin;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "buyers")
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotBlank(message = "Телефон обязателен")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Некорректный номер телефона")
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_info_id", nullable = false)
    private PaymentInfo paymentInfo;

    public void setAddress(Address address) { this.address = address; }
    public Address getAddress() { return address; }
    public void setPaymentInfo(PaymentInfo p) { this.paymentInfo = p; }
    public PaymentInfo getPaymentInfo() { return paymentInfo; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}