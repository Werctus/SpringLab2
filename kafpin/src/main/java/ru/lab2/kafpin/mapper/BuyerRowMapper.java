package ru.lab2.kafpin.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.lab2.kafpin.model.Address;
import ru.lab2.kafpin.model.Buyer;
import ru.lab2.kafpin.model.PaymentInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyerRowMapper implements RowMapper<Buyer> {

    @Override
    public Buyer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Buyer buyer = new Buyer();
        buyer.setId(rs.getLong("id"));
        buyer.setLastName(rs.getString("last_name"));
        buyer.setFirstName(rs.getString("first_name"));
        buyer.setMiddleName(rs.getString("middle_name"));
        buyer.setGender(rs.getString("gender"));
        buyer.setNationality(rs.getString("nationality"));
        buyer.setHeight(rs.getInt("height"));
        buyer.setWeight(rs.getInt("weight"));

        if (rs.getDate("birth_date") != null) {
            buyer.setBirthDate(rs.getDate("birth_date").toLocalDate());
        }
        buyer.setPhone(rs.getString("phone"));

        Address address = new Address();
        address.setId(rs.getLong("address_id"));
        address.setPostalIndex(rs.getString("postal_index"));
        address.setCountry(rs.getString("country"));
        address.setRegion(rs.getString("region"));
        address.setDistrict(rs.getString("district"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));
        address.setHome(rs.getString("home"));
        address.setApartment(rs.getString("apartment"));
        buyer.setAddress(address);

        PaymentInfo payment = new PaymentInfo();
        payment.setId(rs.getLong("payment_info_id"));
        payment.setCardNumber(rs.getString("card_number"));
        payment.setBankAccount(rs.getString("bank_account"));
        buyer.setPaymentInfo(payment);

        return buyer;
    }
}