CREATE TABLE IF NOT EXISTS PART (
                                    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    NAME VARCHAR(255) NOT NULL,
                                    QUANTITY INT NOT NULL,
                                    PRICE DECIMAL(10, 2) NOT NULL
);