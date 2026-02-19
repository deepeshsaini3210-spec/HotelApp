CREATE TABLE customers (
                           customer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(150) UNIQUE NOT NULL,
                           phone VARCHAR(20),
                           address TEXT,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rooms (
                       room_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       room_number VARCHAR(20) NOT NULL,
                       room_type VARCHAR(30) NOT NULL,
                       price_per_night DECIMAL(10,2) NOT NULL,
                       status VARCHAR(20) NOT NULL,
                       hotel_city VARCHAR(100) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       UNIQUE (room_number, hotel_city)
);

CREATE TABLE rooms (
                       room_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       room_number VARCHAR(20) NOT NULL,
                       room_type VARCHAR(30) NOT NULL,
                       price_per_night DECIMAL(10,2) NOT NULL,
                       status VARCHAR(20) NOT NULL,
                       hotel_city VARCHAR(100) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       UNIQUE (room_number, hotel_city)
);
CREATE TABLE reservations (
                              reservation_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              customer_id BIGINT NOT NULL,
                              room_id BIGINT NOT NULL,
                              check_in_date DATE NOT NULL,
                              check_out_date DATE NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                              CONSTRAINT fk_res_customer
                                  FOREIGN KEY (customer_id) REFERENCES customers(customer_id),

                              CONSTRAINT fk_res_room
                                  FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);
CREATE TABLE billings (
                          billing_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          reservation_id BIGINT UNIQUE NOT NULL,
                          room_charges DECIMAL(10,2),
                          extra_charges DECIMAL(10,2),
                          discount DECIMAL(10,2),
                          total_amount DECIMAL(10,2),
                          payment_status VARCHAR(20),

                          CONSTRAINT fk_bill_res
                              FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);
CREATE TABLE housekeeping_tasks (
                                    task_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    room_id BIGINT NOT NULL,
                                    reservation_id BIGINT,
                                    task_type VARCHAR(50),
                                    status VARCHAR(20),
                                    assigned_to VARCHAR(100),

                                    CONSTRAINT fk_task_room
                                        FOREIGN KEY (room_id) REFERENCES rooms(room_id),

                                    CONSTRAINT fk_task_res
                                        FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);

