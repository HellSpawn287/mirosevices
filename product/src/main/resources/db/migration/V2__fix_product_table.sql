-- Dodanie nowych kolumn do tabeli
ALTER TABLE products.Products
    ADD COLUMN creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255);

-- Zmiana nazwy kolumny stock_quantity na quantity
ALTER TABLE products.Products
    RENAME COLUMN stock_quantity TO quantity;

-- -- Usunięcie starej kolumny ID
-- ALTER TABLE products.Products DROP COLUMN id;

-- (Opcjonalnie) Aktualizacja istniejących danych w kolumnach audytu
UPDATE products.Products
SET created_by = 'system', last_modified_by = 'system';
