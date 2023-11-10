-- Database: cars_rest_service

DROP DATABASE IF EXISTS cars_rest_service;

CREATE DATABASE cars_rest_service
    WITH
    OWNER = task4
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;