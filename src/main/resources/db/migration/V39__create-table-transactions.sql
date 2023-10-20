CREATE TABLE transactions (
    transaction_id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    totalValue DECIMAL(10, 2)
);