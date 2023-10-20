  CREATE TABLE products (
    product_id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    img TEXT,
    brand TEXT,
    stock INTEGER NOT NULL,
    category_id UUID NOT NULL,
    price DECIMAL(10, 2)
  );