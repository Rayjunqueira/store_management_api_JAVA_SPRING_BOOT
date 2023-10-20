CREATE TABLE categories (
    category_id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    products TEXT[]
);