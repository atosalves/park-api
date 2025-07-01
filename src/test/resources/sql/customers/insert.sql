INSERT INTO users (id, username, password, role) VALUES (100, 'admin', '$2a$12$nvMTZTvWXQN31RHT1H5hNu6C.chnNGV26xgfkSgyFR3JKJ/h300XG', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role) VALUES (101, 'customer', '$2a$12$nvMTZTvWXQN31RHT1H5hNu6C.chnNGV26xgfkSgyFR3JKJ/h300XG', 'ROLE_CUSTOMER');
INSERT INTO users (id, username, password, role) VALUES (102, 'customer2', '$2a$12$nvMTZTvWXQN31RHT1H5hNu6C.chnNGV26xgfkSgyFR3JKJ/h300XG', 'ROLE_CUSTOMER');

INSERT INTO users (id, username, password, role) VALUES (103, 'customer3', '$2a$12$nvMTZTvWXQN31RHT1H5hNu6C.chnNGV26xgfkSgyFR3JKJ/h300XG', 'ROLE_CUSTOMER');

INSERT INTO customers (id, name, cpf, user_id) VALUES (10, 'Atos Alves', '22420534000', 100);
INSERT INTO customers (id, name, cpf, user_id) VALUES (11, 'Gilmar Dias', '77115870071', 101);
INSERT INTO customers (id, name, cpf, user_id) VALUES (12, 'Robson Catatau', '60876876025', 102);
