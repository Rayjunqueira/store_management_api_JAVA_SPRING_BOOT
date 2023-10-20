CREATE TABLE customers (
    customer_id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    cellphone1 TEXT,
    cellphone2 TEXT,
    email TEXT,
    customer_category_id UUID NOT NULL,
    stats TEXT []
);