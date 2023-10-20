CREATE TABLE sales (
    sale_id UUID PRIMARY KEY,
    customer_id UUID NOT NULL,
    product_id UUID NOT NULL,
    stock INTEGER NOT NULL,
    price DECIMAL(10, 2)
);