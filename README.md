# Herson's Backpack v4.0 (`hersons-backpack`)

University Project Repository, Collaboration Hub & Search Engine built with **Java 17, Spring Boot 3 (Model, DTO, Repository, Service, Controller)** and **PostgreSQL (3FN)**.

## Novedades de la Versión 4.0
1. **`HersonsBackpackApplication.java` 100% limpio**: Se eliminó por completo el `CommandLineRunner` que insertaba datos desde código Java. Ahora la clase principal solo arranca Spring Boot.
2. **Todo el DDL y poblado de datos en `database/init_postgres.sql` (y sincronizado en `src/main/resources/data.sql`)**:
   - Creación de las 7 tablas (`usuarios`, `superusuarios`, `proyectos`, `enlaces_proyecto`, `comentarios`, `proyecto_colaboradores`, `mensajes_privados`).
   - Migración automática segura (`ALTER TABLE ... ADD COLUMN IF NOT EXISTS`) por si ya tenías las tablas creadas de antes en PostgreSQL.
3. **Cifrado de contraseñas con SHA-256 (Cero texto plano)**:
   - Tanto en `init_postgres.sql` como al registrarse en `UsuarioService.java`, las contraseñas se cifran con **SHA-256** (64 caracteres hexadecimales).
   - Si tu base de datos tenía usuarios de una versión anterior con contraseña en texto plano, al iniciar sesión el servicio lo detecta y actualiza automáticamente su hash a SHA-256 en PostgreSQL.
4. **Corrección del Inicio de Sesión (`login.html`)**:
   - Solucionado el conflicto de esquema (`descripcion_corta` nula en bases de datos previas) y sincronización de secuencias en PostgreSQL que impedían autenticar.

## Cuentas Semilla en `init_postgres.sql`
*(En la base de datos están guardadas con su hash SHA-256, pero puedes iniciar sesión escribiendo la contraseña normal en `login.html`)*:

| Usuario | Contraseña para Login | Hash SHA-256 guardado en PostgreSQL | Rol |
| :--- | :--- | :--- | :--- |
| `data_ninja` | `123456` | `8d969eef6ecad3c29a3a6292...` | ESTUDIANTE |
| `java_dev99` | `123456` | `8d969eef6ecad3c29a3a6292...` | ESTUDIANTE |
| `hardware_hacker` | `123456` | `8d969eef6ecad3c29a3a6292...` | ESTUDIANTE (Colab) |
| `admin_db` | `123456` | `8d969eef6ecad3c29a3a6292...` | SUPERUSUARIO |
| `herson_admin` | `admin123` | `240be518fabd2724ddb6f04e...` | SUPERUSUARIO |
