# Courier Service

Microservicio REST para la gestión de repartidores (*couriers*) del sistema LogiTrack. Permite registrar repartidores y consultar el listado completo, filtrar por estado y buscar un repartidor por su identificador.

## Tecnologías

- **Java 25**
- **Spring Boot 4.1.2**
- **Spring Web MVC** para la API REST
- **Spring Data JPA** para la persistencia
- **H2 Database** como dependencia de base de datos en tiempo de ejecución
- **Spring Validation** para validar los datos de entrada
- **Lombok** para reducir código repetitivo
- **Maven Wrapper** para ejecutar tareas de Maven sin instalarlo manualmente

## Funcionalidades

- Registrar repartidores.
- Consultar todos los repartidores.
- Filtrar repartidores por estado (`ACTIVO` o `INACTIVO`).
- Consultar un repartidor mediante su UUID.
- Validar los campos obligatorios de las solicitudes.
- Centralizar el tratamiento de errores y devolver respuestas HTTP estructuradas.
- Registrar fechas de creación y última modificación mediante la clase base de auditoría.

## Arquitectura

El proyecto sigue una separación por capas:

| Paquete | Responsabilidad |
|---|---|
| `controller` | Expone los endpoints HTTP y recibe las solicitudes. |
| `service` | Contiene la lógica de aplicación y transforma entidades en DTOs. |
| `repository` | Accede a los datos mediante Spring Data JPA. |
| `model` | Define la entidad persistente `Courier`. |
| `dto` | Define los objetos de entrada y salida de la API. |
| `enums` | Define los estados posibles del repartidor. |
| `exceptions` | Contiene excepciones de negocio y el manejador global de errores. |
| `audit` | Proporciona campos comunes de auditoría a las entidades persistentes. |

### Modelo de datos

La entidad `Courier` representa un repartidor y contiene:

- `id`: identificador UUID generado automáticamente.
- `nombre`: nombre del repartidor.
- `tipoVehiculo`: tipo de vehículo utilizado.
- `estadoCourier`: estado actual (`ACTIVO` o `INACTIVO`).
- `createdDate`: fecha y hora de creación.
- `lastModifiedDate`: fecha y hora de la última modificación.

Al crear un repartidor, el servicio asigna el estado inicial `ACTIVO`.

## Requisitos previos

- **JDK 25** instalado y configurado.
- Acceso a internet la primera vez que se ejecute Maven Wrapper, para descargar la distribución de Maven y resolver dependencias.

No es necesario instalar Maven por separado: el repositorio incluye `mvnw` y `mvnw.cmd`.

## Configuración

La configuración disponible en `src/main/resources/application.yaml` establece:

- Nombre de la aplicación: `courier-service`.
- Puerto HTTP: `8082`.
- Endpoints de Actuator expuestos: `health` e `info`.

La configuración suministrada incluye la dependencia H2, pero no define explícitamente una URL, usuario ni contraseña de base de datos. Spring Boot aplicará la configuración automática correspondiente según las dependencias y el entorno.

> **Nota:** el proyecto también incorpora Spring Security. Si no se configura una política de seguridad propia, los endpoints podrían quedar sujetos a la configuración de seguridad predeterminada de Spring Boot. Si la API debe consumirse sin autenticación, revisá y configurá explícitamente las reglas de seguridad.

## Cómo ejecutar el proyecto

Cloná el repositorio y entrá en el directorio del proyecto:

```bash
git clone <URL_DEL_REPOSITORIO>
cd themiggacool-courier-service
```

### Windows (PowerShell o CMD)

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux o macOS

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

Una vez iniciado, el servicio estará disponible en:

```text
http://localhost:8082
```

## API REST

La API utiliza la ruta base `/couriers`.

| Método | Endpoint | Descripción | Respuesta esperada |
|---|---|---|---|
| `POST` | `/couriers` | Registra un repartidor. | `201 Created` |
| `GET` | `/couriers` | Lista todos los repartidores. | `200 OK` |
| `GET` | `/couriers?estado=ACTIVO` | Filtra por estado. | `200 OK` |
| `GET` | `/couriers/{id}` | Busca por UUID. | `200 OK` |

### 1. Crear un repartidor

**Solicitud**

```http
POST /couriers
Content-Type: application/json
```

```json
{
  "nombre": "Juan Pérez",
  "tipoVehiculo": "Motocicleta"
}
```

El campo `nombre` y el campo `tipoVehiculo` son obligatorios y no pueden estar en blanco. Si la solicitud es válida, el servicio crea el repartidor con estado inicial `ACTIVO`.

**Ejemplo de respuesta — `201 Created`**

```json
{
  "id": "a5cdb2d5-5a83-4a45-a1c3-51f0c43f60a9",
  "nombre": "Juan Pérez",
  "tipoVehiculo": "Motocicleta",
  "estadoCourier": "ACTIVO"
}
```

El UUID es ilustrativo; el identificador real lo genera la aplicación.

### 2. Listar todos los repartidores

```http
GET /couriers
```

Devuelve la lista de repartidores registrados. Si no existen registros, la lista estará vacía.

### 3. Filtrar por estado

```http
GET /couriers?estado=ACTIVO
```

Valores permitidos para `estado`:

- `ACTIVO`
- `INACTIVO`

El valor debe coincidir con uno de los nombres del enumerado. Un valor inválido genera una respuesta `400 Bad Request`.

### 4. Consultar un repartidor por ID

```http
GET /couriers/a5cdb2d5-5a83-4a45-a1c3-51f0c43f60a9
```

Si el UUID corresponde a un repartidor registrado, devuelve sus datos. Si no existe, responde con `404 Not Found`.

## Manejo de errores

`GlobalExceptionHandler` centraliza los errores de la API. Las respuestas utilizan la estructura `ErrorResponse`:

```json
{
  "status": 404,
  "error": "Not Found",
  "mensaje": "Courier no encontrado: <uuid>",
  "timestamp": "2026-10-25T10:30:00"
}
```

El contenido es ilustrativo; el valor de `timestamp` y el mensaje dependen del error concreto.

| Código HTTP | Situación |
|---|---|
| `400 Bad Request` | Datos inválidos, JSON mal formado o parámetros con formato incorrecto. |
| `404 Not Found` | No se encontró el repartidor solicitado. |
| `409 Conflict` | Violación de integridad o conflicto representado por las excepciones de negocio contempladas. |
| `500 Internal Server Error` | Error inesperado; no se exponen detalles internos en la respuesta. |

## Auditoría

La clase `AuditableEntity` proporciona los campos `createdDate` y `lastModifiedDate` a las entidades que hereden de ella. Spring Data JPA puede completar esos campos automáticamente mediante `AuditingEntityListener`.

**Importante:** para que la auditoría automática funcione, la aplicación debe habilitar JPA Auditing mediante `@EnableJpaAuditing` en una clase de configuración. Verificá que esa configuración esté presente antes de ejecutar la aplicación, ya que no aparece en la estructura de archivos proporcionada.

## Ejecutar las pruebas

### Windows

```powershell
.\mvnw.cmd test
```

### Linux o macOS

```bash
./mvnw test
```

El proyecto incluye una prueba de contexto (`CourierServiceApplicationTests`) que verifica que el contexto de Spring Boot pueda iniciarse correctamente.

## Estructura del proyecto

```text
src/
├── main/
│   ├── java/com/courier/service/
│   │   ├── audit/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── enums/
│   │   ├── exceptions/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── CourierServiceApplication.java
│   └── resources/
│       └── application.yaml
└── test/
    └── java/com/courier/service/
        └── CourierServiceApplicationTests.java
```

## Estado del alcance

La implementación actual cubre el alta y las consultas de repartidores. Aunque el enumerado contempla los estados `ACTIVO` e `INACTIVO`, en los endpoints mostrados todavía no se incluye una operación para cambiar el estado de un repartidor.

---

**Proyecto:** `themiggacool-courier-service`  
**Dominio:** gestión de repartidores de LogiTrack
