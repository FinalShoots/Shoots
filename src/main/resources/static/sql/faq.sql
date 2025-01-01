CREATE TABLE faq (
    faq_idx INT(10) AUTO_INCREMENT PRIMARY KEY,
    writer INT(10),
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    faq_file VARCHAR(50),
    faq_original VARCHAR(50),
    register_date DATETIME DEFAULT CURRENT_TIMESTAMP-- ,
    -- FOREIGN KEY (writer) REFERENCES regular_user(idx) ON DELETE CASCADE
);