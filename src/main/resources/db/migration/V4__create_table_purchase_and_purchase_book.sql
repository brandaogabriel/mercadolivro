CREATE TABLE tb_purchase
(
    id          SERIAL PRIMARY KEY,
    customer_id SERIAL         NOT NULL,
    nfe         VARCHAR(255),
    price       DECIMAL(15, 2) NOT NULL,
    created_at  TIME           NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES tb_customer (id)
);

CREATE TABLE tb_purchase_book
(
    purchase_id SERIAL,
    book_id     SERIAL,
    FOREIGN KEY (purchase_id) REFERENCES tb_purchase (id),
    FOREIGN KEY (book_id) REFERENCES tb_book (id),
    PRIMARY KEY (purchase_id, book_id)
);