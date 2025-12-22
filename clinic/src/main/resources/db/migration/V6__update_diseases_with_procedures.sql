-- Associar procedimentos às doenças

-- Doenças de EMERGÊNCIA
UPDATE diseases SET procedure_id = 3 WHERE name = 'Câncer de Pulmão';          -- Tomografia
UPDATE diseases SET procedure_id = 10 WHERE name = 'Ataque Cardíaco'; -- Eletrocardiograma

-- Doenças de severidade HIGH
UPDATE diseases SET procedure_id = 4 WHERE name = 'Pneumonia';                 -- Raio-X
UPDATE diseases SET procedure_id = 6 WHERE name = 'Dengue';                    -- Hemograma Completo
UPDATE diseases SET procedure_id = 1 WHERE name = 'Apendicite';                -- Ultrassom
UPDATE diseases SET procedure_id = 10 WHERE name = 'Insuficiência Cardíaca';   -- Eletrocardiograma
UPDATE diseases SET procedure_id = 2 WHERE name = 'Meningite';                 -- Ressonância Magnética

-- Doenças de severidade DEFAULT
UPDATE diseases SET procedure_id = 7 WHERE name = 'COVID-19';                  -- Teste de COVID
UPDATE diseases SET procedure_id = 6 WHERE name = 'Gripe (Influenza)';         -- Hemograma Completo
UPDATE diseases SET procedure_id = 4 WHERE name = 'Asma';                      -- Raio-X
UPDATE diseases SET procedure_id = 4 WHERE name = 'Bronquite';                 -- Raio-X
UPDATE diseases SET procedure_id = 1 WHERE name = 'Gastroenterite';            -- Ultrassom

-- Doenças de severidade LOW (não necessitam exames)
UPDATE diseases SET procedure_id = NULL WHERE name = 'Resfriado Comum';
UPDATE diseases SET procedure_id = NULL WHERE name = 'Rinite Alérgica';
