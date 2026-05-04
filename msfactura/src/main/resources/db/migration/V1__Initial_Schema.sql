CREATE TABLE factura(
    id BIGSERIAL,
    descripcion VARCHAR(200)
    );
INSERT INTO factura(descripcion) VALUES ('a');
COMMIT;