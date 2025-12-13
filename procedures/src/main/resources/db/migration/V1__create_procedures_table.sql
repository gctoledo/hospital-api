CREATE TABLE procedures (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_cpf VARCHAR(11) NOT NULL,
    doctor_crm VARCHAR(20),
    exam_type ENUM(
        'BLOOD_COUNT',
        'BLOOD_GLUCOSE',
        'CHOLESTEROL',
        'THYROID',
        'LIVER_FUNCTION',
        'KIDNEY_FUNCTION',
        'URINALYSIS',
        'URINE_CULTURE',
        'X_RAY',
        'ULTRASOUND',
        'ELECTROCARDIOGRAM',
        'ALLERGY_TEST',
        'STOOL_TEST',
        'PREGNANCY_TEST'
    ) NOT NULL,
    date_time DATETIME,
    status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'SCHEDULED',
    priority ENUM('LOW', 'DEFAULT', 'HIGH') NOT NULL DEFAULT 'DEFAULT'
);

CREATE INDEX idx_procedures_patient_cpf ON procedures(patient_cpf);
CREATE INDEX idx_procedures_doctor_crm ON procedures(doctor_crm);
CREATE INDEX idx_procedures_status ON procedures(status);
CREATE INDEX idx_procedures_date_time ON procedures(date_time);
