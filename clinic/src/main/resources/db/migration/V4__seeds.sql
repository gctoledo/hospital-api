-- Inserir doctors
INSERT INTO doctors (name, crm, specialty) VALUES
('Michel Pinto', 'CRM/SP-123456', 'GENERAL_PRACTICE'),
('Henrique Paoletti', 'CRM/RJ-789012', 'CARDIOLOGY'),
('Clecio Rocha', 'CRM/MG-345678', 'PULMONOLOGY');

-- Inserir sintomas
INSERT INTO symptoms (name) VALUES
('Febre'),
('Tosse'),
('Dor no peito'),
('Falta de ar'),
('Dor de cabeça'),
('Náusea'),
('Vômito'),
('Dor abdominal'),
('Fadiga'),
('Coriza'),
('Espirros'),
('Dor muscular'),
('Dor de garganta'),
('Confusão mental'),
('Tontura'),
('Sudorese'),
('Calafrios'),
('Perda de peso'),
('Perda de apetite'),
('Sangramento'),
('Diarreia'),
('Cólica abdominal');

-- Inserir doenças
INSERT INTO diseases (name, severity) VALUES
('Câncer de Pulmão', 'EMERGENCY'),
('Infarto Agudo do Miocárdio', 'EMERGENCY');

INSERT INTO diseases (name, severity) VALUES
('Pneumonia', 'HIGH'),
('Dengue', 'HIGH'),
('Apendicite', 'HIGH'),
('Insuficiência Cardíaca', 'HIGH'),
('Meningite', 'HIGH');

INSERT INTO diseases (name, severity) VALUES
('COVID-19', 'DEFAULT'),
('Gripe (Influenza)', 'DEFAULT'),
('Asma', 'DEFAULT'),
('Bronquite', 'DEFAULT'),
('Gastroenterite', 'DEFAULT');

INSERT INTO diseases (name, severity) VALUES
('Resfriado Comum', 'LOW'),
('Rinite Alérgica', 'LOW');

-- CÂNCER DE PULMÃO (1)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(1, 2, 'EMERGENCY', 0.8),     -- Tosse (muito específico quando persistente)
(1, 3, 'HIGH', 0.6),          -- Dor no peito (característico)
(1, 4, 'HIGH', 0.7),          -- Falta de ar (característico)
(1, 18, 'DEFAULT', 0.7),      -- Perda de peso (característico)
(1, 9, 'DEFAULT', 0.4);       -- Fadiga (moderado)

-- INFARTO AGUDO DO MIOCÁRDIO (2)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(2, 3, 'EMERGENCY', 0.95),
(2, 4, 'HIGH', 0.85),
(2, 16, 'HIGH', 0.80),
(2, 6, 'DEFAULT', 0.60),
(2, 15, 'DEFAULT', 0.55);

-- PNEUMONIA (3)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(3, 1, 'HIGH', 0.85),
(3, 2, 'HIGH', 0.80),
(3, 4, 'HIGH', 0.75),
(3, 3, 'DEFAULT', 0.60),
(3, 9, 'DEFAULT', 0.55);

-- DENGUE (4)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(4, 1, 'HIGH', 0.90),
(4, 12, 'HIGH', 0.85),
(4, 5, 'DEFAULT', 0.75),
(4, 20, 'EMERGENCY', 0.80),
(4, 6, 'DEFAULT', 0.60);

-- APENDICITE (5)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(5, 8, 'EMERGENCY', 0.90),
(5, 6, 'DEFAULT', 0.70),
(5, 7, 'DEFAULT', 0.65),
(5, 1, 'DEFAULT', 0.60),
(5, 22, 'DEFAULT', 0.75);

-- INSUFICIÊNCIA CARDÍACA (6)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(6, 4, 'HIGH', 0.85),
(6, 9, 'DEFAULT', 0.75),
(6, 3, 'DEFAULT', 0.60),
(6, 16, 'DEFAULT', 0.55),
(6, 18, 'DEFAULT', 0.50);

-- MENINGITE (7)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(7, 1, 'HIGH', 0.90),
(7, 5, 'HIGH', 0.85),
(7, 14, 'EMERGENCY', 0.80),
(7, 7, 'DEFAULT', 0.60),
(7, 15, 'DEFAULT', 0.55);

-- COVID-19 (8)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(8, 10, 'LOW', 0.80),
(8, 1, 'DEFAULT', 0.80),
(8, 2, 'DEFAULT', 0.80),
(8, 4, 'HIGH', 0.70),
(8, 9, 'DEFAULT', 0.65),
(8, 13, 'DEFAULT', 0.60);

-- GRIPE (9)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(9, 1, 'DEFAULT', 0.85),
(9, 12, 'DEFAULT', 0.80),
(9, 5, 'DEFAULT', 0.70),
(9, 10, 'DEFAULT', 0.65),
(9, 9, 'DEFAULT', 0.60);

-- ASMA (10)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(10, 4, 'HIGH', 0.90),
(10, 2, 'DEFAULT', 0.70),
(10, 3, 'DEFAULT', 0.55),
(10, 15, 'DEFAULT', 0.50),
(10, 16, 'DEFAULT', 0.45);

-- BRONQUITE (11)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(11, 2, 'DEFAULT', 0.85),
(11, 4, 'DEFAULT', 0.65),
(11, 1, 'DEFAULT', 0.60),
(11, 9, 'DEFAULT', 0.55),
(11, 3, 'DEFAULT', 0.50);

-- GASTROENTERITE (12)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(12, 21, 'DEFAULT', 0.85),
(12, 7, 'DEFAULT', 0.80),
(12, 6, 'DEFAULT', 0.75),
(12, 8, 'DEFAULT', 0.70),
(12, 22, 'DEFAULT', 0.65);

-- RESFRIADO COMUM (13)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(13, 10, 'LOW', 0.85),
(13, 11, 'LOW', 0.80),
(13, 13, 'LOW', 0.60),
(13, 2, 'LOW', 0.50),
(13, 9, 'LOW', 0.45);

-- RINITE ALÉRGICA (14)
INSERT INTO disease_symptoms (disease_id, symptom_id, severity, specificity) VALUES
(14, 11, 'LOW', 0.90),
(14, 10, 'LOW', 0.85),
(14, 13, 'LOW', 0.60),
(14, 5, 'LOW', 0.45),
(14, 9, 'LOW', 0.40);