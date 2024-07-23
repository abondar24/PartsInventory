CREATE TABLE IF NOT EXISTS PART_DETAILS (
                                    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    DESCRIPTION VARCHAR(255) NOT NULL,
                                    PART_ID INT,
                                    FOREIGN KEY (PART_ID) REFERENCES PART(ID) ON DELETE CASCADE
);