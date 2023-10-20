CREATE TABLE customer_categories (
    customer_category_id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    customers TEXT []
);