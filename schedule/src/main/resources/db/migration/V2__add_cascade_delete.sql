ALTER TABLE appointments DROP FOREIGN KEY fk_appointments_patient;

ALTER TABLE appointments
ADD CONSTRAINT fk_appointments_patient
FOREIGN KEY (patient_id) REFERENCES patients(id)
ON DELETE CASCADE;