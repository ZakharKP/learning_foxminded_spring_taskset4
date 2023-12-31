-- Drop tables if they already exist
DROP TABLE IF EXISTS cars;

-- Create Enums

CREATE TYPE PRODUCENT AS ENUM (
'LEXUS',
'SUBARU',
'CHEVROLET',
'CHRYSLER',
'MASERATI',
'FIAT',
'FREIGHTLINER',
'SUZUKI',
'MAZDA',
'FISKER',
'RIVIAN',
'GMC',
'LINCOLN',
'HYUNDAI',
'EAGLE',
'ASTON MARTIN',
'ACURA',
'KIA',
'PONTIAC',
'PLYMOUTH',
'SAAB',
'GEO',
'MAYBACH',
'TOYOTA',
'JAGUAR',
'TESLA',
'MERCEDES-BENZ',
'PANOZ',
'DAEWOO',
'VOLKSWAGEN',
'VOLVO',
'SATURN',
'DODGE',
'AUDI',
'ALFA ROMEO',
'HONDA',
'SMART',
'FERRARI',
'SRT',
'PORSCHE',
'SCION',
'OLDSMOBILE',
'ISUZU',
'INFINITI',
'ROLLS-ROYCE',
'JEEP',
'BUICK',
'POLESTAR',
'MITSUBISHI',
'LOTUS',
'BMW',
'BENTLEY',
'CADILLAC',
'LAND ROVER',
'LAMBORGHINI',
'DAIHATSU',
'MINI',
'HUMMER',
'GENESIS',
'FORD',
'MCLAREN',
'MERCURY',
'NISSAN',
'RAM'
);

CREATE TYPE CATEGORY AS ENUM (
'SUV1992',
'CONVERTIBLE',
'VAN/MINIVAN',
'SUV2020',
'SUV',
'PICKUP',
'COUPE',
'WAGON',
'HATCHBACK',
'SEDAN'
);

-- Create tables
CREATE TABLE cars (
    car_id UUID PRIMARY KEY,
    car_model VARCHAR(255),
    car_year DATE,
    car_manufacturer PRODUCENT,
);

-- Create the car_categories table
CREATE TABLE car_categories (
    car_id UUID,
    category CATEGORY,
    PRIMARY KEY (car_id, category),
    FOREIGN KEY (car_id) REFERENCES cars(car_id) ON DELETE CASCADE
);