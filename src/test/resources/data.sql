INSERT INTO POSTAL_CODE (id, zip_code, city)
VALUES (nextval('postal_code_seq'),
        '1000',
        'TestCity');

INSERT INTO ADDRESS (id, street_name, street_number, fk_postal_code_id)
Values (nextval('address_seq'),
        'Test Street',
        '1',
        1);

INSERT INTO CUSTOMER (id, first_name, last_name, email, fk_address_id, phone_number)
VALUES ('123e4567-e89b-12d3-a456-426614174000',
        'John',
        'McTest',
        'John.McTest@diehard.com',
        1,
        '0800-999');
INSERT INTO CUSTOMER (id, first_name, last_name, email, fk_address_id, phone_number)
VALUES ('123e4567-e89b-12d3-a456-426614174001',
        'Chuck',
        'Testa',
        'Chuck.Testa@diehard.com',
        1,
        '0800-999');

INSERT INTO ITEM (id, name, description, price, amount)
VALUES ('123e4567-e89b-12d3-a456-426614174002',
        'Tomato',
        'A clean, round tomato with lots of vitamins',
        0.125,
        10);
INSERT INTO ITEM (id, name, description, price, amount)
VALUES ('123e4567-e89b-12d3-a456-426614174003',
        'Carrot',
        'A carrot',
        0.14,
        30);

INSERT INTO EURDER (id, fk_customer_id, total_price)
VALUES ('123e4567-e89b-12d3-a456-426614174004',
        '123e4567-e89b-12d3-a456-426614174000',
        1.0);
INSERT INTO ITEM_GROUP (id, fk_item_id, fk_eurder_id, amount, price, shipping_date)
VALUES ('123e4567-e89b-12d3-a456-426614174005',
        '123e4567-e89b-12d3-a456-426614174002',
        '123e4567-e89b-12d3-a456-426614174004',
        8,
        0.125,
        '2022-05-06'
       );