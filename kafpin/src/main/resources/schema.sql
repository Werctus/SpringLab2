CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    postal_index VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    region VARCHAR(255) NOT NULL,
    district VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    home VARCHAR(255) NOT NULL,
    apartment VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS payment_infos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(255) UNIQUE,
    bank_account VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS buyers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    last_name VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    gender VARCHAR(2) NOT NULL,
    nationality VARCHAR(100) NOT NULL,
    height INTEGER NOT NULL,
    weight INTEGER NOT NULL,
    birth_date DATE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address_id BIGINT NOT NULL,
    payment_info_id BIGINT NOT NULL,
    FOREIGN KEY (address_id) REFERENCES addresses(id),
    FOREIGN KEY (payment_info_id) REFERENCES payment_infos(id)
);