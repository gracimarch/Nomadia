# Nomadia 🏡🛎️

**Nomadia** es una aplicación de consola en **Java** desarrollada para la gestión integral de alquileres temporarios y alojamientos turísticos (casas, departamentos y hoteles), reservas, usuarios, reseñas y pasarelas de pago con persistencia en **SQLite**.

---

## 📋 Características Principales

- **Gestión de Usuarios:**
  - Registro, modificación y baja de Inquilinos, Inquilinos Premium (con beneficios de descuento) y Anfitriones.
- **Gestión de Propiedades:**
  - Publicación y administración polimórfica de alojamientos: *Casas* (parrilla, patio), *Departamentos* (piso, balcón, zonas comunes) y *Hoteles* (estrellas, check-in/out).
  - Búsqueda y filtrado multicriterio (por anfitrión, tipo, ubicación geográfica, precio máximo, capacidad de huéspedes y comodidades).
- **Gestión de Reservas:**
  - Creación, modificación de fechas o cantidad de personas, y cancelación de reservas con validación de disponibilidad.
- **Procesamiento de Pagos:**
  - Soporte para pagos en Efectivo, Tarjeta de Crédito/Débito (con validación y cálculo de cuotas) y Transferencia Bancaria.
- **Sistema de Reseñas y Calificaciones:**
  - Valoraciones de 1 a 5 estrellas con comentarios de inquilinos sobre propiedades reservadas.
- **Persistencia Robusta:**
  - Base de datos relacional SQLite con activación de claves foráneas e integridad referencial en cascada y restricción.

---

## 🛠️ Tecnologías y Requisitos

- **Lenguaje:** Java 21 LTS (compatible con JDK 21 o superior).
- **Gestor de Construcción:** [Apache Maven](https://maven.apache.org/) 3.8+.
- **Base de Datos:** SQLite 3 (a través de `org.xerial:sqlite-jdbc:3.50.3.0`).

---

## 📂 Estructura del Proyecto

```text
Nomadia/
├── .gitattributes                # Configuración de atributos y finales de línea para Git
├── .gitignore                    # Exclusiones de artefactos de compilación, IDEs y SQLite
├── pom.xml                       # Configuración de compilación Maven y dependencias
├── README.md                     # Documentación general del repositorio
├── nomadia.db                    # Base de datos SQLite preconfigurada con datos semilla
│
├── docs/                         # Documentación técnica
│   ├── architecture.md           # Diseño orientado a objetos, capas y patrones del sistema
│   └── database/
│       └── nomadia_schema.sql    # DDL del esquema relacional y script de datos iniciales
│
└── src/
    └── main/
        └── java/
            └── nomadia/
                ├── Nomadia.java                              # Punto de entrada principal (main)
                ├── ConexionDB.java                           # Conexión JDBC a nomadia.db
                ├── Leer.java                                 # Utilidad de entrada por consola
                ├── AnfitrionConReservasActivasException.java # Excepción de dominio
                │
                ├── Usuario.java                              # Modelo base de usuarios
                ├── Inquilino.java                            # Huésped regular
                ├── InquilinoPremium.java                     # Huésped con descuento preferencial
                ├── Anfitrion.java                            # Propietario de alojamientos
                │
                ├── Propiedad.java                            # Modelo base de propiedades
                ├── Casa.java                                 # Alojamiento tipo Casa
                ├── Departamento.java                         # Alojamiento tipo Departamento
                ├── Hotel.java                                # Alojamiento tipo Hotel
                │
                ├── Reserva.java                              # Modelo de reservas
                ├── Resenia.java                              # Modelo de calificaciones y reseñas
                │
                ├── Pago.java                                 # Interfaz para procesamiento de pagos
                ├── PagoEfectivo.java                         # Pago en efectivo
                ├── PagoTarjeta.java                          # Pago con tarjeta y cuotas
                ├── PagoTransferencia.java                    # Pago vía transferencia bancaria
                │
                ├── GestorUsuario.java                        # Operaciones de datos para usuarios
                ├── GestorPropiedad.java                      # Operaciones de datos para propiedades
                ├── GestorReserva.java                        # Operaciones de datos para reservas
                └── GestorResenia.java                        # Operaciones de datos para reseñas
```

---

## 🚀 Compilación y Ejecución

### Opción 1: Desde Apache NetBeans
1. Abrir **Apache NetBeans**.
2. Ir a `File` -> `Open Project...` y seleccionar la carpeta del repositorio `Nomadia`.
3. NetBeans detectará automáticamente la configuración Maven a través de `pom.xml`.
4. Presionar `F6` o hacer clic derecho en el proyecto y seleccionar **Run**.

---

### Opción 2: Desde Línea de Comandos con Maven

#### 1. Compilar el proyecto
```bash
mvn clean compile
```

#### 2. Ejecutar la aplicación
```bash
mvn exec:java
```

#### 3. Generar el empaquetado JAR ejecutable
```bash
mvn clean package
```
El archivo JAR ejecutable se generará en la carpeta `target/Nomadia-1.0-SNAPSHOT.jar`.

---

### Opción 3: Compilación directa con `javac` y `java`

Si no dispone de Maven en su variable `PATH`:

```powershell
# 1. Compilar clases hacia target/classes
javac -cp "C:\Users\<Usuario>\.m2\repository\org\xerial\sqlite-jdbc\3.50.3.0\sqlite-jdbc-3.50.3.0.jar" -d target/classes (Get-ChildItem -Path src/main/java -Filter *.java -Recurse | Select-Object -ExpandProperty FullName)

# 2. Ejecutar la clase principal
java -cp "target/classes;C:\Users\<Usuario>\.m2\repository\org\xerial\sqlite-jdbc\3.50.3.0\sqlite-jdbc-3.50.3.0.jar" nomadia.Nomadia
```

---

## 🗄️ Base de Datos

La aplicación utiliza SQLite a través del archivo `nomadia.db` ubicado en la raíz del proyecto.
- Si necesita restablecer la base de datos a su estado original o crear una nueva instancia, puede ejecutar el script SQL ubicado en:
  [`docs/database/nomadia_schema.sql`](docs/database/nomadia_schema.sql).
- Para más información sobre el modelo relacional, consulte [`docs/architecture.md`](docs/architecture.md).
