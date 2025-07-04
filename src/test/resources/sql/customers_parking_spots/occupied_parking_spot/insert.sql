INSERT INTO users (id, username, password, role) VALUES (100, 'admin', '$2a$12$nvMTZTvWXQN31RHT1H5hNu6C.chnNGV26xgfkSgyFR3JKJ/h300XG', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role) VALUES (101, 'customer', '$2a$12$nvMTZTvWXQN31RHT1H5hNu6C.chnNGV26xgfkSgyFR3JKJ/h300XG', 'ROLE_CUSTOMER');
INSERT INTO users (id, username, password, role) VALUES (102, 'customer2', '$2a$12$nvMTZTvWXQN31RHT1H5hNu6C.chnNGV26xgfkSgyFR3JKJ/h300XG', 'ROLE_CUSTOMER');

INSERT INTO customers (id, name, cpf, user_id) VALUES (10, 'Atos Alves', '22420534000', 100);
INSERT INTO customers (id, name, cpf, user_id) VALUES (11, 'Gilmar Dias', '77115870071', 101);
INSERT INTO customers (id, name, cpf, user_id) VALUES (12, 'Robson Catatau', '60876876025', 102);

INSERT INTO parking_spots (id, code, status) VALUES (10, '1111','OCCUPIED');
INSERT INTO parking_spots (id, code, status) VALUES (11, '2222','OCCUPIED');
INSERT INTO parking_spots (id, code, status) VALUES (12, '3333','OCCUPIED');

INSERT INTO customers_parking_spots (id, receipt, license_plate, brand, model, color, entry_at, customer_id, parking_spot_id)
        VALUES (10, 'AAA-1111-20250101-000000','AAA-1111','Volkswagen','Celta','Prata','2025-01-01 00:10:00', 10, 10);
INSERT INTO customers_parking_spots (id, receipt, license_plate, brand, model, color, entry_at, customer_id, parking_spot_id)
        VALUES (11, 'AAA-1111-20250102-000000','AAA-1111','Volkswagen','Celta','Prata','2025-01-02 00:20:00', 11, 11);
INSERT INTO customers_parking_spots (id, receipt, license_plate, brand, model, color, entry_at, customer_id, parking_spot_id)
        VALUES (12, 'AAA-1111-20250103-000000','AAA-1111','Volkswagen','Celta','Prata','2025-01-03 00:30:00', 12, 12);

