CREATE TABLE exams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    procedure_id BIGINT NOT NULL,
    patient_cpf VARCHAR(11) NOT NULL,
    start_datetime DATETIME,
    end_datetime DATETIME,
    status ENUM('CREATED', 'SCHEDULED', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'CREATED',
    priority ENUM('DEFAULT', 'EMERGENCY') NOT NULL DEFAULT 'DEFAULT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (procedure_id) REFERENCES procedures(id)
);

CREATE INDEX idx_exams_procedure_id ON exams(procedure_id);
CREATE INDEX idx_exams_patient_cpf ON exams(patient_cpf);
CREATE INDEX idx_exams_status ON exams(status);
CREATE INDEX idx_exams_start_datetime ON exams(start_datetime);
CREATE INDEX idx_exams_end_datetime ON exams(end_datetime);
