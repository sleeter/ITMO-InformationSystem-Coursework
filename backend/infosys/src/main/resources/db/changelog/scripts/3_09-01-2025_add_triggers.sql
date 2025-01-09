CREATE OR REPLACE FUNCTION update_pickup_points_size()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE pickup_points
    SET size = size + NEW.size
    WHERE id = NEW.pickup_points_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_order_insert AFTER INSERT ON orders FOR EACH ROW
EXECUTE FUNCTION update_pickup_points_size();
