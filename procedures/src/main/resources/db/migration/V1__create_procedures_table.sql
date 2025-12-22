CREATE TABLE procedures (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    duration_in_minutes INT NOT NULL,
    complexity ENUM('DEFAULT', 'HIGH') NOT NULL DEFAULT 'DEFAULT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_procedures_name ON procedures(name);
CREATE INDEX idx_procedures_complexity ON procedures(complexity);