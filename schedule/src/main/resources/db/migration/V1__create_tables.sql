CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    birth_date DATE NOT NULL,
    gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    type ENUM('CONSULTATION', 'EXAM') NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'REJECTED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    external_code VARCHAR(50),
    date_time DATETIME NOT NULL,
    rejection_reason VARCHAR(255),

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
        'GASTROENTEROLOGY',
        'PULMONOLOGY',
        'ENDOCRINOLOGY',
        'ONCOLOGY',
        'RHEUMATOLOGY'
    ),

    exam_type ENUM(
        'BLOOD_COUNT',
        'BLOOD_GLUCOSE',
        'CHOLESTEROL',
        'URINALYSIS',
        'X_RAY',
        'ULTRASOUND',
        'ELECTROCARDIOGRAM',
        'ALLERGY_TEST',
        'STOOL_TEST',
        'PREGNANCY_TEST'
    ),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_appointments_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE INDEX idx_patients_cpf ON patients(cpf);
CREATE INDEX idx_appointments_patient ON appointments(patient_id);
CREATE INDEX idx_appointments_status ON appointments(status);
CREATE INDEX idx_appointments_type ON appointments(type);
CREATE INDEX idx_appointments_external_code ON appointments(external_code);
CREATE INDEX idx_appointments_datetime ON appointments(date_time);