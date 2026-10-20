# Proyecto Clínica Dental

Aplicación web para gestionar pacientes y citas de una clínica dental. Está construida
con **Servlets, JSP, JSTL y JDBC** sobre MySQL siguiendo una separación MVC sencilla:
los servlets controlan las peticiones, `ClinicDao` accede a los datos y los JSP muestran
la información.

## Estructura

```
src/main/
├── java/es/dwes/dentista/
│   ├── Appointment.java       <- modelo de cita
│   ├── AppointmentServlet.java
│   ├── ClinicDao.java         <- consultas y operaciones CRUD
│   ├── Database.java          <- conexión configurable a MySQL
│   ├── Dentist.java           <- modelo de odontólogo
│   ├── Patient.java           <- modelo de paciente
│   └── PatientServlet.java
├── resources/
│   └── dentista.sql           <- creación y datos iniciales
└── webapp/
    ├── index.jsp              <- menú
    ├── estilos.css
    └── WEB-INF/views/         <- vistas JSP no accesibles directamente
        ├── formularioCita.jsp
        ├── formularioPaciente.jsp
        ├── listadoCitas.jsp
        └── listadoPacientes.jsp
```

Las vistas usan JSTL y Expression Language; no contienen scriptlets ni código JDBC.
Los formularios envían altas y bajas por `POST`; tras una operación correcta, el servlet
redirige al listado correspondiente.

## Rutas

| Ruta | Método | Función |
|---|---|---|
| `/pacientes` | GET | Listar pacientes |
| `/pacientes/nuevo` | GET | Mostrar el alta de paciente |
| `/pacientes` | POST | Guardar paciente |
| `/pacientes/eliminar` | POST | Eliminar paciente y sus citas asociadas |
| `/citas` | GET | Mostrar próximas citas |
| `/citas?todas=1` | GET | Mostrar el histórico completo |
| `/citas/nueva` | GET | Mostrar el alta de cita |
| `/citas` | POST | Guardar cita |
| `/citas/eliminar` | POST | Anular cita |

Desde el listado de pacientes, `/citas/nueva?paciente=<id>` preselecciona el paciente.

## Base de datos

La base `dentista` contiene las tablas `paciente`, `odontologos` y `cita`. La tabla `cita`
referencia a paciente y odontólogo; al eliminar un paciente, sus citas se eliminan por
la restricción `ON DELETE CASCADE`.

Importa el script desde la raíz del proyecto:

```bash
mysql -u user -puser < src/main/resources/dentista.sql
```

Por defecto se conecta a `localhost:3306`, base `dentista`, usuario `user` y contraseña
`user`. La conexión se puede configurar sin modificar el código mediante estas variables:

| Variable | Valor predeterminado |
|---|---|
| `DENTISTA_DB_URL` | `jdbc:mysql://localhost:3306/dentista?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8` |
| `DENTISTA_DB_USER` | `user` |
| `DENTISTA_DB_PASSWORD` | `user` |

## Compilar y desplegar

Requisitos: Java 21, Maven 3.8 o superior, MySQL y Tomcat 11.0.x.

La aplicación usa Jakarta Servlet 6.1 (`jakarta.*`), compatible con Tomcat 11. No se debe
desplegar en Tomcat 9, que requiere el espacio de nombres anterior `javax.*`.

```bash
mvn clean package
```

Maven genera `target/scriplet_dentista.war`. Despliega ese archivo en Tomcat 11; el
contexto predeterminado será `/scriplet_dentista`.

Para entregar el proyecto fuente, excluye las carpetas generadas `target` y `build` del
archivo ZIP.
