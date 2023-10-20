CREATE TABLE orders (
    order_id UUID PRIMARY KEY,
    customer_id UUID,
    sales UUID[],
    orderDate DATE,
    totalAmount DECIMAL(10, 2)
);