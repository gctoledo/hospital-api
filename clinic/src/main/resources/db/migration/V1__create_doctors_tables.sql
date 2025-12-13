CREATE TABLE doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    crm VARCHAR(20) NOT NULL UNIQUE,
    specialty ENUM(
        'GENERAL_PRACTICE',
        'CARDIOLOGY',
        'DERMATOLOGY',
        'ORTHOPEDICS',
        'NEUROLOGY',
        'PEDIATRICS',
        'GYNECOLOGY',
        'OPHTHALMOLOGY',
        'PSYCHIATRY',
        'UROLOGY',
        'PULMONOLOGY',
        'ENDOCRINOLOGY',
        'ONCOLOGY',
        'RHEUMATOLOGY'
    ) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_doctors_crm ON doctors(crm);
CREATE INDEX idx_doctors_specialty ON doctors(specialty);