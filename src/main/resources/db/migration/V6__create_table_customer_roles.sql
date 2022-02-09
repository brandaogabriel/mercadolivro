CREATE TABLE tb_customer_roles
(
    customer_id SERIAL       NOT NULL,
    role        VARCHAR(255) NOT NULL,
    FOREIGN KEY (customer_id) references tb_customer (id)
);