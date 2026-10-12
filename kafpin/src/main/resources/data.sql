INSERT INTO addresses
(postal_index,
country,
region,
district,
city,
street,
home,
apartment)
VALUES
('123456', 'Россия', 'Московская', 'Центральный', 'Москва', 'Тверская', '1', '10');

INSERT INTO addresses
(postal_index,
country,
region,
district,
city,
street,
home,
apartment)
VALUES
('654321', 'Россия', 'Ленинградская', 'Невский', 'Санкт-Петербург', 'Невский', '5', '25');

INSERT INTO addresses
(postal_index,
country,
region,
district,
city,
street,
home,
apartment)
VALUES
('627085', 'Россия', 'Нижегородская', 'Выксунский', 'Выкса', 'Красные Зори', '94', '13');

INSERT INTO payment_info (card_number, bank_account)
VALUES ('1234567812345678', '12345678901234567890');

INSERT INTO payment_info (card_number, bank_account)
VALUES ('8765432187654321', '09876543210987654321');

INSERT INTO payment_info (card_number, bank_account)
VALUES ('0192837465019283', '12345678900987654321');

INSERT INTO buyers
(last_name,
first_name,
middle_name,
gender,
nationality,
height,
weight,
birth_date,
phone,
address_id,
payment_info_id)
VALUES
('Девон',
'Ларрат',
'Ноулимит',
'М',
'канадец',
203,
140,
'1980-05-12',
'+88005553535',
1,
1);

INSERT INTO buyers
(last_name,
first_name,
middle_name,
gender,
nationality,
height,
weight,
birth_date,
phone,
address_id,
payment_info_id)
VALUES
('Чарльз',
'Бронсон',
'Сидящий',
'М',
'британец',
182,
110,
'1960-07-14',
'+88312920845',
2,
2);

INSERT INTO buyers
(last_name,
first_name,
middle_name,
gender,
nationality,
height,
weight,
birth_date,
phone,
address_id,
payment_info_id)
VALUES
('Волков',
'Михаил',
'Сергеевич',
'М',
'русский',
203,
140,
'1980-05-12',
'+89108859229',
3,
3);
