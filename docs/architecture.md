# Arquitectura del Sistema Nomadia

Este documento describe la arquitectura, diseño orientado a objetos, modelo de datos y componentes técnicos del proyecto **Nomadia**.

---

## 1. Visión General

**Nomadia** es una aplicación de consola en Java diseñada para la gestión integral de reservas de alojamientos turísticos y temporales. El sistema permite:

- **Administrar Usuarios:** Registro y gestión de Inquilinos, Inquilinos Premium (con descuento preferencial) y Anfitriones.
- **Gestionar Propiedades:** Catálogo polimórfico de alojamientos (Casas, Departamentos, Hoteles) con características específicas y filtrado avanzado (ubicación, precio, capacidad, comodidades).
- **Procesar Reservas:** Alta, modificación de fechas o huéspedes, validación de disponibilidad y control de pagos.
- **Procesamiento de Pagos:** Soporte para múltiples medios de pago (Efectivo, Tarjeta con cálculo de cuotas e interés, y Transferencia con simulación de comprobante).
- **Reseñas y Calificaciones:** Registro de valoraciones (1 a 5 estrellas) de inquilinos sobre las propiedades visitadas.

---

## 2. Diagrama de Capas del Sistema

```
+-------------------------------------------------------------+
|                     Capa de Presentación                    |
|        Nomadia.java (Menú interactivo y orquestación)       |
|        Leer.java (Validación robusta de entrada consola)    |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|              Capa de Negocio y Controladores                |
|   GestorUsuario       GestorPropiedad                       |
|   GestorReserva       GestorResenia                         |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                       Capa de Dominio                       |
|   - Usuario (Inquilino, InquilinoPremium, Anfitrion)        |
|   - Propiedad (Casa, Departamento, Hotel)                   |
|   - Reserva, Resenia                                        |
|   - Pago (PagoEfectivo, PagoTarjeta, PagoTransferencia)     |
|   - AnfitrionConReservasActivasException                    |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                   Capa de Datos (Persistencia)              |
|        ConexionDB.java (Driver org.sqlite.JDBC)             |
|        nomadia.db (Base de datos SQLite local)              |
+-------------------------------------------------------------+
```

---

## 3. Principios de Diseño Orientado a Objetos

### Herencia y Polimorfismo
- **Usuarios (`Usuario`):** Clase base abstracta o modelo que especializa a:
  - `Inquilino`: Huésped estándar.
  - `InquilinoPremium`: Huésped que cuenta con un beneficio de descuento (`10%`) sobre el monto final de las reservas (`obtenerDescuento()`).
  - `Anfitrion`: Propietario que publica alojamientos.
- **Propiedades (`Propiedad`):** Especialización de alojamientos:
  - `Casa`: Incorpora atributos de patio y parrilla.
  - `Departamento`: Incorpora piso, balcón y zonas comunes.
  - `Hotel`: Incorpora rango de estrellas, horarios de Check-In/Check-Out.
- **Estrategia de Pagos (`Pago`):** Interfaz que define el contrato `procesarPago(double monto, Scanner read)`. Implementada por:
  - `PagoEfectivo`: Cobro en efectivo con cálculo de vuelto.
  - `PagoTarjeta`: Validación de tarjeta (16 dígitos), selección de cuotas (1, 3, 6) y aplicación de recargos.
  - `PagoTransferencia`: Solicitud de CBU/Alias y emisión de código de transacción simulado.

### Manejo de Excepciones de Dominio
- `AnfitrionConReservasActivasException`: Excepción personalizada que previene la eliminación de anfitriones cuyas propiedades cuentan con reservas activas pendientes, preservando la consistencia del negocio.

---

## 4. Persistencia y Base de Datos

- **Motor:** SQLite 3 mediante `org.xerial:sqlite-jdbc`.
- **Integridad Referencial:** Se habilita explícitamente `PRAGMA foreign_keys = ON` en cada conexión abierta por `ConexionDB.java`.
- **Relaciones Clave:**
  - `Propiedades.anfitrionId` -> `Usuarios.id` (`ON DELETE CASCADE`)
  - `Reservas.propiedadId` -> `Propiedades.id` (`ON DELETE RESTRICT`)
  - `Reservas.inquilinoId` -> `Usuarios.id` (`ON DELETE CASCADE`)
  - `Resenias.propiedadId` -> `Propiedades.id` (`ON DELETE CASCADE`)
  - `Resenias.inquilinoId` -> `Usuarios.id` (`ON DELETE CASCADE`)
  - `Pagos.reservaId` -> `Reservas.id` (`ON DELETE CASCADE`)

Para ver la definición DDL completa y datos semilla, consultar [`docs/database/nomadia_schema.sql`](./database/nomadia_schema.sql).

---

## 5. Convenciones y Mantenimiento

- **Paquete Base:** Todo el código fuente del dominio reside en el paquete `nomadia` en `src/main/java/nomadia/`, eliminando prefijos artificiales de plantillas de IDEs (`com.mycompany`) y manteniendo una estructura limpia, intuitiva y profesional.
- **Ubicación de Base de Datos:** `nomadia.db` reside en la raíz del proyecto para asegurar que las rutas relativas JDBC sean consistentes tanto al ejecutar desde IDEs (NetBeans, IntelliJ, VS Code) como desde Maven CLI (`mvn exec:java`).
