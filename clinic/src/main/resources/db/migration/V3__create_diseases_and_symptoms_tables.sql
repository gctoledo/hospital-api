CREATE TABLE diseases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    severity ENUM('LOW', 'DEFAULT', 'HIGH', 'EMERGENCY') NOT NULL DEFAULT 'DEFAULT'
);

CREATE TABLE symptoms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE disease_symptoms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    disease_id BIGINT NOT NULL,
    symptom_id BIGINT NOT NULL,
    severity ENUM('LOW', 'DEFAULT', 'HIGH', 'EMERGENCY') NOT NULL DEFAULT 'DEFAULT',
    specificity DOUBLE NOT NULL DEFAULT 0.5,
    CONSTRAINT fk_ds_disease FOREIGN KEY (disease_id) REFERENCES diseases(id),
    CONSTRAINT fk_ds_symptom FOREIGN KEY (symptom_id) REFERENCES symptoms(id)
);

CREATE INDEX idx_diseases_name ON diseases(name);
CREATE INDEX idx_symptoms_name ON symptoms(name);
CREATE INDEX idx_ds_disease_id ON disease_symptoms(disease_id);
CREATE INDEX idx_ds_symptom_id ON disease_symptoms(symptom_id);