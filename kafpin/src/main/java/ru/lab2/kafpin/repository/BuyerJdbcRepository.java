package ru.lab2.kafpin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.lab2.kafpin.model.Buyer;
import ru.lab2.kafpin.mapper.BuyerRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class BuyerJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BuyerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL запрос с JOIN для получения всех данных
    private final String SELECT_ALL_SQL = """
        SELECT b.*, 
               a.postal_index, a.country, a.region, a.district, a.city, a.street, a.home, a.apartment,
               p.card_number, p.bank_account
        FROM buyers b
        LEFT JOIN addresses a ON b.address_id = a.id
        LEFT JOIN payment_infos p ON b.payment_info_id = p.id
        """;

    public List<Buyer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL + " ORDER BY b.id", new BuyerRowMapper());
    }

    public Optional<Buyer> findById(Long id) {
        List<Buyer> list = jdbcTemplate.query(SELECT_ALL_SQL + " WHERE b.id = ?", new BuyerRowMapper(), id);
        return list.stream().findFirst();
    }

    public int deleteById(Long id) {
        // Сначала удаляем покупателя, затем платежку (чтобы не нарушить FK)
        Buyer buyer = findById(id).orElse(null);
        if (buyer == null) return 0;

        jdbcTemplate.update("DELETE FROM buyers WHERE id = ?", id);
        if (buyer.getPaymentInfo() != null) {
            jdbcTemplate.update("DELETE FROM payment_infos WHERE id = ?", buyer.getPaymentInfo().getId());
        }
        return 1;
    }

    public Long save(Buyer buyer) {
        // Сначала сохраняем платежную информацию
        String insertPaymentSql = "INSERT INTO payment_infos (card_number, bank_account) VALUES (?, ?)";
        KeyHolder paymentKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertPaymentSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, buyer.getPaymentInfo().getCardNumber());
            ps.setString(2, buyer.getPaymentInfo().getBankAccount());
            return ps;
        }, paymentKeyHolder);

        Long paymentId = paymentKeyHolder.getKey().longValue();

        // Затем сохраняем самого покупателя
        String insertBuyerSql = """
                INSERT INTO buyers (last_name, first_name, middle_name, gender, nationality, 
                                    height, weight, birth_date, phone, address_id, payment_info_id) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        KeyHolder buyerKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertBuyerSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, buyer.getLastName());
            ps.setString(2, buyer.getFirstName());
            ps.setString(3, buyer.getMiddleName());
            ps.setString(4, buyer.getGender());
            ps.setString(5, buyer.getNationality());
            ps.setInt(6, buyer.getHeight());
            ps.setInt(7, buyer.getWeight());
            ps.setDate(8, java.sql.Date.valueOf(buyer.getBirthDate()));
            ps.setString(9, buyer.getPhone());
            ps.setLong(10, buyer.getAddress().getId());
            ps.setLong(11, paymentId);
            return ps;
        }, buyerKeyHolder);

        return buyerKeyHolder.getKey().longValue();
    }

    public int update(Buyer buyer) {
        // Обновляем платежку
        jdbcTemplate.update("UPDATE payment_infos SET card_number = ?, bank_account = ? WHERE id = ?",
                buyer.getPaymentInfo().getCardNumber(),
                buyer.getPaymentInfo().getBankAccount(),
                buyer.getPaymentInfo().getId());

        // Обновляем покупателя
        String updateBuyerSql = """
                UPDATE buyers 
                SET last_name = ?, first_name = ?, middle_name = ?, gender = ?, nationality = ?, 
                    height = ?, weight = ?, birth_date = ?, phone = ?, address_id = ?
                WHERE id = ?
                """;
        return jdbcTemplate.update(updateBuyerSql,
                buyer.getLastName(),
                buyer.getFirstName(),
                buyer.getMiddleName(),
                buyer.getGender(),
                buyer.getNationality(),
                buyer.getHeight(),
                buyer.getWeight(),
                java.sql.Date.valueOf(buyer.getBirthDate()),
                buyer.getPhone(),
                buyer.getAddress().getId(),
                buyer.getId()
        );
    }
}