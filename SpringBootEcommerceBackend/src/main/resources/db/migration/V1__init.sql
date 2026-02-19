-- This file is used to initialize the database schema for the application. It creates the necessary tables and indexes.

CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL is an auto-incrementing integer type suitable for primary keys
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now() -- TIMESTAMPTZ is a timestamp with time zone, which is useful for tracking when records are created
);

CREATE TABLE products(
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(64) NOT NULL UNIQUE, -- SKU (Stock Keeping Unit) is a unique identifier for each product
    name VARCHAR(255) NOT NULL,
    price_cents BIGINT NOT NULL, -- BIGINT is used to store price in cents to avoid floating-point precision issues
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- inventory as seprate table to manage locking / consisteny issues when multiple transactions are trying to update the same product stock
CREATE TABLE inventory(
    product_id BIGINT PRIMARY KEY REFERENCES products(id) ON DELETE CASCADE, -- product_id is a foreign key referencing the products table, with ON DELETE CASCADE to automatically delete inventory records when a product is deleted
    available_qty BIGINT NOT NULL CHECK (available_qty >= 0), -- CHECK constraint to ensure that available quantity cannot be negative
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now() 
);

-- indexing product table for faster lookups by active status, which is a common query filter
CREATE INDEX idx_products_active ON products(active) -- index to quickly find active products