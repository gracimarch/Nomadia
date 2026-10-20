PRAGMA foreign_keys = ON;

------------------------------------------------------------------------------
-- 1. Tabla: Usuarios
------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS "Usuarios" (
    "id"        INTEGER PRIMARY KEY AUTOINCREMENT,
    "nombre"    TEXT NOT NULL,
    "email"     TEXT NOT NULL UNIQUE,
    "telefono"  TEXT UNIQUE,
    "tipo"      TEXT NOT NULL,
    "descuento" REAL
);

------------------------------------------------------------------------------
-- 2. Tabla: Propiedades
------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS "Propiedades" (
    "id"            INTEGER PRIMARY KEY AUTOINCREMENT,
    "tipo"          TEXT NOT NULL,
    "ubicacion"     TEXT NOT NULL,
    "precioNoche"   REAL NOT NULL,
    "anfitrionId"   INTEGER NOT NULL,
    "maxPersonas"   INTEGER NOT NULL,
    "habitaciones"  INTEGER,
    "banios"        INTEGER,
    "parking"       BOOLEAN,
    "petFriendly"   BOOLEAN,
    "parrilla"      BOOLEAN,
    "patio"         BOOLEAN,
    "piso"          INTEGER,
    "balcon"        BOOLEAN,
    "zonaComun"     BOOLEAN,
    "checkIn"       TEXT,
    "checkOut"      TEXT,
    "estrellas"     INTEGER,
    "piscina"       BOOLEAN,
    FOREIGN KEY ("anfitrionId") REFERENCES "Usuarios" ("id") ON DELETE CASCADE
);

------------------------------------------------------------------------------
-- 3. Tabla: Reservas
------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS "Reservas" (
    "id"                INTEGER PRIMARY KEY AUTOINCREMENT,
    "propiedadId"       INTEGER NOT NULL,
    "inquilinoId"       INTEGER NOT NULL,
    "fechaInicio"       DATE NOT NULL,
    "fechaFin"          DATE NOT NULL,
    "precioFinal"       REAL NOT NULL,
    "cantidadPersonas"  INTEGER NOT NULL,
    "pagado"            BOOLEAN NOT NULL DEFAULT 0,
    FOREIGN KEY ("inquilinoId") REFERENCES "Usuarios" ("id") ON DELETE CASCADE,
    FOREIGN KEY ("propiedadId") REFERENCES "Propiedades" ("id") ON DELETE RESTRICT
);

------------------------------------------------------------------------------
-- 4. Tabla: Resenias
------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS "Resenias" (
    "id"            INTEGER PRIMARY KEY AUTOINCREMENT,
    "propiedadId"   INTEGER NOT NULL,
    "inquilinoId"   INTEGER NOT NULL,
    "comentario"    TEXT,
    "puntaje"       INTEGER NOT NULL CHECK ("puntaje" BETWEEN 1 AND 5),
    FOREIGN KEY ("inquilinoId") REFERENCES "Usuarios" ("id") ON DELETE CASCADE,
    FOREIGN KEY ("propiedadId") REFERENCES "Propiedades" ("id") ON DELETE CASCADE
);

------------------------------------------------------------------------------
-- 5. Tabla: Pagos
------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS "Pagos" (
    "id"        INTEGER PRIMARY KEY AUTOINCREMENT,
    "reservaId" INTEGER NOT NULL,
    "metodo"    TEXT NOT NULL,
    "monto"     REAL NOT NULL,
    "estado"    TEXT,
    FOREIGN KEY ("reservaId") REFERENCES "Reservas" ("id") ON DELETE CASCADE
);

------------------------------------------------------------------------------
-- Datos Semilla (Seed Data)
------------------------------------------------------------------------------

INSERT INTO Usuarios (id, nombre, email, telefono, tipo, descuento) 
VALUES (1, 'Cristian Fernandez', 'crisfernandez@gmail.com', '2613457788', 'Inquilino', NULL);

INSERT INTO Usuarios (id, nombre, email, telefono, tipo, descuento) 
VALUES (2, 'Graciana March', 'gracimarch@gmail.com', '2613456776', 'Anfitrion', NULL);

INSERT INTO Propiedades (id, tipo, ubicacion, precioNoche, anfitrionId, maxPersonas, habitaciones, banios, parking, petFriendly, parrilla, patio, piso, balcon, zonaComun, checkIn, checkOut, estrellas, piscina) 
VALUES (1, 'Casa', 'Mar del Plata', 200.0, 2, 6, 3, 2, 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO Propiedades (id, tipo, ubicacion, precioNoche, anfitrionId, maxPersonas, habitaciones, banios, parking, petFriendly, parrilla, patio, piso, balcon, zonaComun, checkIn, checkOut, estrellas, piscina) 
VALUES (2, 'Departamento', 'Papudo, Chile', 100000.0, 2, 4, 2, 1, 0, 1, NULL, NULL, 12, 0, NULL, NULL, NULL, NULL, NULL);

INSERT INTO Propiedades (id, tipo, ubicacion, precioNoche, anfitrionId, maxPersonas, habitaciones, banios, parking, petFriendly, parrilla, patio, piso, balcon, zonaComun, checkIn, checkOut, estrellas, piscina) 
VALUES (3, 'Casa', 'pruebaa', 300.0, 2, 3, 2, 2, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO Reservas (id, propiedadId, inquilinoId, fechaInicio, fechaFin, precioFinal, cantidadPersonas, pagado) 
VALUES (2, 3, 1, '2025-01-20', '2025-02-20', 9300.0, 2, 0);

INSERT INTO Resenias (id, propiedadId, inquilinoId, comentario, puntaje) 
VALUES (1, 1, 1, 'La mejor casa en la q me hospede', 5);
