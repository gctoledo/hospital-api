CREATE TABLE consultations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_cpf VARCHAR(11) NOT NULL,
    doctor_id BIGINT,
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
    date_time DATETIME NOT NULL,
    status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_consultations_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE SET NULL
);

CREATE INDEX idx_consultations_patient_cpf ON consultations(patient_cpf);
CREATE INDEX idx_consultations_doctor_id ON consultations(doctor_id);
CREATE INDEX idx_consultations_status ON consultations(status);
CREATE INDEX idx_consultations_date_time ON consultations(date_time);
