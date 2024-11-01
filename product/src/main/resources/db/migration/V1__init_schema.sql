CREATE TABLE products.Products
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(255)   NOT NULL,
    description    TEXT,
    price          DECIMAL(10, 2) NOT NULL,
    stock_quantity INT            NOT NULL
);

INSERT INTO products.Products (name, description, price, stock_quantity)
VALUES ('Telewizor', 'Duży telewizor LED HD', 899.99, 10),
       ('Laptop', 'Laptop z procesorem i7 i 16 GB RAM', 1299.99, 5),
       ('Smartfon', 'Nowoczesny smartfon z aparatem 64 MP', 699.99, 20);



CREATE TABLE products.Categories
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);

INSERT INTO products.Categories (name, description)
VALUES ('Telewizory', 'Kategorie zawierające różnego rodzaju telewizory'),
       ('Komputery', 'Kategorie zawierające różnego rodzaju komputery'),
       ('Laptopy', 'Kategorie zawierające różnego rodzaju laptopy'),
       ('Smartfony', 'Kategorie zawierające różnego rodzaju smartfony'),
       ('Tablety', 'Kategorie zawierające różnego rodzaju tablety'),
       ('Akcesoria do elektroniki', 'Kategorie zawierające różne akcesoria do elektroniki'),
       ('Gry komputerowe', 'Kategorie zawierające różne gry komputerowe');

INSERT INTO products.Products (name, description, price, stock_quantity)
VALUES ('Telewizor LED 42 cali', 'Telewizor LED o przekątnej 42 cali', 999.99, 10),
       ('Telewizor OLED 55 cali', 'Telewizor OLED o przekątnej 55 cali', 1499.99, 5),
       ('Laptop Dell Inspiron 15', 'Laptop z procesorem i5 i 8 GB RAM', 899.99, 20),
       ('Laptop HP Pavilion 14', 'Laptop z procesorem i7 i 16 GB RAM', 1199.99, 15),
       ('Smartfon Samsung Galaxy S20', 'Smartfon z ekranem 6.2 cala i aparatem 64 MP', 799.99, 30),
       ('Smartfon iPhone 12', 'Smartfon z ekranem 6.1 cala i systemem iOS', 999.99, 25),
       ('Tablet Samsung Galaxy Tab S7', 'Tablet z ekranem 11 cali i procesorem Snapdragon', 649.99, 15),
       ('Tablet iPad Air', 'Tablet z ekranem 10.9 cala i chipem A14 Bionic', 749.99, 20),
       ('Kabel HDMI', 'Kabel HDMI długości 2 metry', 19.99, 100),
       ('Klawiatura mechaniczna', 'Klawiatura mechaniczna z podświetleniem RGB', 89.99, 50),
       ('Mysz bezprzewodowa', 'Mysz bezprzewodowa z czujnikiem laserowym', 29.99, 75),
       ('Gra komputerowa Cyberpunk 2077', 'Gra RPG osadzona w otwartym świecie cyberpunku', 59.99, 40),
       ('Gra komputerowa The Witcher 3: Wild Hunt', 'Gra RPG osadzona w otwartym świecie fantasy', 39.99, 60),
       ('Ładowarka bezprzewodowa', 'Ładowarka bezprzewodowa do smartfonów', 29.99, 50),
       ('Słuchawki douszne', 'Słuchawki douszne z redukcją szumów', 49.99, 30),
       ('Głośnik Bluetooth', 'Głośnik Bluetooth o mocy 20 W', 79.99, 25),
       ('Monitor komputerowy 27 cali', 'Monitor komputerowy o rozdzielczości 2560x1440', 299.99, 15),
       ('Procesor Intel Core i9-10900K', 'Procesor Intel Core i9 10. generacji', 499.99, 10),
       ('Karta graficzna NVIDIA GeForce RTX 3080', 'Karta graficzna NVIDIA z architekturą Ampere', 699.99, 5),
       ('Dysk SSD NVMe 1TB', 'Dysk SSD NVMe pojemności 1TB', 149.99, 20);

CREATE TABLE products.ProductCategories
(
    product_id  SERIAL,
    category_id SERIAL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES products.Products (id),
    FOREIGN KEY (category_id) REFERENCES products.Categories (id)
);

CREATE TABLE products.Reviews
(
    id         SERIAL PRIMARY KEY,
    product_id INT,
    user_id    INT,
    rating     INT,
    comment    TEXT,
    FOREIGN KEY (product_id) REFERENCES products.Products (id)
    -- Add FOREIGN KEY for user_id if needed for user authentication
);

