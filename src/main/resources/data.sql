-- =========================================================================
-- HERSON'S BACKPACK v4.0 - SCRIPT DE BASE DE DATOS RELACIONAL (PostgreSQL 3FN)
-- Incluye creación de tablas, migración segura de columnas y datos semilla
-- con contraseñas cifradas mediante SHA-256 (sin texto plano).
-- =========================================================================

-- 0. TABLA USUARIOS
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    rol VARCHAR(30) DEFAULT 'ESTUDIANTE',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Asegurar longitud de columna password para hashes SHA-256 (64 hex chars)
ALTER TABLE usuarios ALTER COLUMN password TYPE VARCHAR(128);

-- 1. TABLA SUPERUSUARIOS (Puede editar y eliminar cualquier publicación)
CREATE TABLE IF NOT EXISTS superusuarios (
    id SERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    nivel_acceso VARCHAR(40) DEFAULT 'SUPER_ADMIN',
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_superusuario_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- 2. TABLA PROYECTOS (Con 2 descripciones: corta para buscador, larga para detalle, y marcador de visitas)
CREATE TABLE IF NOT EXISTS proyectos (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    tipo VARCHAR(60) NOT NULL,
    descripcion_corta VARCHAR(280),
    descripcion TEXT NOT NULL,
    repositorio_url VARCHAR(255),
    tecnologias VARCHAR(180),
    visitas BIGINT NOT NULL DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_proyecto_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Migración segura por si la tabla 'proyectos' ya existía de una versión anterior
ALTER TABLE proyectos ADD COLUMN IF NOT EXISTS descripcion_corta VARCHAR(280);
ALTER TABLE proyectos ADD COLUMN IF NOT EXISTS visitas BIGINT NOT NULL DEFAULT 0;
UPDATE proyectos SET descripcion_corta = LEFT(descripcion, 200) WHERE descripcion_corta IS NULL;

-- 3. TABLA ENLACES (Múltiples enlaces/repositorios por publicación)
CREATE TABLE IF NOT EXISTS enlaces_proyecto (
    id SERIAL PRIMARY KEY,
    proyecto_id BIGINT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    url VARCHAR(500) NOT NULL,
    CONSTRAINT fk_enlace_proyecto FOREIGN KEY (proyecto_id)
        REFERENCES proyectos (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- 4. TABLA COMENTARIOS (Cada publicación puede tener comentarios de distintas personas)
CREATE TABLE IF NOT EXISTS comentarios (
    id SERIAL PRIMARY KEY,
    proyecto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    contenido TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comentario_proyecto FOREIGN KEY (proyecto_id)
        REFERENCES proyectos (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_comentario_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- 5. TABLA COLABORADORES (El colaborador ve el proyecto como suyo y puede modificarlo, pero NO eliminarlo)
CREATE TABLE IF NOT EXISTS proyecto_colaboradores (
    proyecto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha_ingreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (proyecto_id, usuario_id),
    CONSTRAINT fk_colab_proyecto FOREIGN KEY (proyecto_id)
        REFERENCES proyectos (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_colab_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- 6. TABLA MENSAJES PRIVADOS (Mensajes directos por username o invitaciones a colaborar en un proyecto)
CREATE TABLE IF NOT EXISTS mensajes_privados (
    id SERIAL PRIMARY KEY,
    remitente_id BIGINT NOT NULL,
    destinatario_id BIGINT NOT NULL,
    asunto VARCHAR(150),
    contenido TEXT NOT NULL,
    leido BOOLEAN DEFAULT FALSE,
    es_invitacion BOOLEAN DEFAULT FALSE,
    proyecto_invitado_id BIGINT NULL,
    estado_invitacion VARCHAR(20) DEFAULT 'NO_APLICA',
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_msg_remitente FOREIGN KEY (remitente_id)
        REFERENCES usuarios (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_msg_destinatario FOREIGN KEY (destinatario_id)
        REFERENCES usuarios (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_msg_proyecto FOREIGN KEY (proyecto_invitado_id)
        REFERENCES proyectos (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

-- =========================================================================
-- POBLADO DE DATOS SEMILLA (TODO EN SQL, SIN CÓDIGO EN LA APLICACIÓN)
-- Contraseñas cifradas con SHA-256:
--   '123456'   -> 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
--   'admin123' -> 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
-- =========================================================================

INSERT INTO usuarios (id, username, email, password, rol) VALUES
(1, 'data_ninja', 'ninja@herson.sh', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'ESTUDIANTE'),
(2, 'java_dev99', 'javadev@herson.sh', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'ESTUDIANTE'),
(3, 'admin_db', 'dba@herson.sh', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SUPERUSUARIO'),
(4, 'hardware_hacker', 'hw@herson.sh', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'ESTUDIANTE'),
(5, 'herson_admin', 'imhex@bk.ru', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'SUPERUSUARIO')
ON CONFLICT (username) DO UPDATE SET
    password = EXCLUDED.password,
    rol = EXCLUDED.rol;

INSERT INTO superusuarios (id, usuario_id, nivel_acceso) VALUES
(1, 3, 'DBA_SUPERUSER'),
(2, 5, 'ROOT_SUPERUSER')
ON CONFLICT (usuario_id) DO NOTHING;

INSERT INTO proyectos (id, titulo, tipo, descripcion_corta, descripcion, repositorio_url, tecnologias, visitas, usuario_id) VALUES
(1, 'DataProcessor_Pandas', 'Programming',
 'Transformación de archivos CSV pesados a formato columnar Apache Parquet con gráficas en Plotly.',
 'Scripts en Python desarrollados con Pandas y Plotly para transformar de manera eficiente archivos CSV pesados al formato Apache Parquet y generar visualizaciones de datos comparativas de uso de memoria y tiempos de lectura.',
 'https://github.com/herson-backpack/data-processor-parquet', 'Python, Pandas, Plotly, Apache Parquet', 24, 1),
(2, 'SpringBoot_Backend_Arch', 'Programming',
 'Arquitectura de software multicapa en Java 17 y Spring Boot 3 (Controller, Service, Repository, DTO, Model).',
 'Configuración de arquitectura de software para backend en Java utilizando el framework Spring Boot. Estructurado rigurosamente en capas MVC incluyendo paquetes para Controller, DTO, Service, Repository y Model con persistencia JPA.',
 'https://github.com/herson-backpack/springboot-layered-arch', 'Java 17, Spring Boot 3, JPA, REST API', 42, 2),
(3, 'LabManager_Postgres', 'Database',
 'Base de datos relacional en 3FN en PostgreSQL para gestión de laboratorios de cómputo y mantenimiento.',
 'Diseño e implementación de una base de datos relacional en PostgreSQL (gestionada vía pgAdmin 4) para administrar un laboratorio de cómputo, manteniendo registros detallados del hardware, responsables y bitácoras de mantenimiento mediante llaves primarias y foráneas.',
 'https://github.com/herson-backpack/lab-manager-postgres', 'PostgreSQL, pgAdmin 4, SQL, Álgebra Relacional', 31, 3),
(4, 'HC_SR04_Proximity_Sensor', 'Hardware & IoT',
 'Sistema de detección ultrasónica de proximidad con Arduino IDE, sensor HC-SR04 y alertas LED.',
 'Código en Arduino IDE y diagramas de protoboard para integrar y controlar un sensor de proximidad ultrasónico HC-SR04, junto con una secuencia de LEDs alternantes para alertas visuales de seguridad.',
 'https://github.com/herson-backpack/arduino-hcsr04-security', 'Arduino IDE, C++, HC-SR04, Protoboard', 19, 4),
(5, 'DataCenter_Env_Monitor', 'Networks & Sec',
 'Monitoreo ambiental y seguridad física para Data Center universitario con Spring Boot, Postgres y sensores.',
 'Sistema integral de monitoreo ambiental y de seguridad para centros de datos universitarios utilizando microcontroladores, sensores de temperatura/proximidad, PostgreSQL y backend en Java Spring Boot.',
 'https://github.com/herson-backpack/datacenter-env-monitor', 'Spring Boot, PostgreSQL, Microcontroladores, IEEE', 58, 5),
(6, 'Becas_Estudiantiles_DB', 'Database',
 'Esquema relacional y portal para gestión de solicitudes de becas estudiantiles y estudios socioeconómicos.',
 'Arquitectura de base de datos relacional y diagrama interactivo para la gestión de solicitudes de becas estudiantiles, evaluaciones socioeconómicas y seguimiento de beneficiarios.',
 'https://github.com/herson-backpack/becas-db-schema', 'PostgreSQL, HTML5, Diseño Relacional', 15, 3),
(7, 'Network_Audit_Compliance', 'Networks & Sec',
 'Auditoría técnica de redes y telecomunicaciones bajo estándares IEEE y marcos de gobernanza TI.',
 'Auditoría técnica de infraestructura de telecomunicaciones, matriz de no conformidades y planes de acción correctiva bajo estándares IEEE y marcos de gobernanza TI.',
 'https://github.com/herson-backpack/network-audit-ieee', 'Redes, IEEE 802, Gobernanza TI, Ciberseguridad', 27, 5),
(8, 'ETL_Benchmark_Visualizer', 'Programming',
 'Benchmark interactivo de lectura/escritura entre CSV y Apache Parquet en entornos Linux ligeros.',
 'Dashboard comparativo de rendimiento de lectura/escritura entre archivos planos CSV y almacenamiento columnar Parquet en entornos Linux ligeros.',
 'https://github.com/herson-backpack/etl-benchmark-linux', 'Python, Plotly, Debian LXQt, Parquet', 12, 1)
ON CONFLICT (id) DO UPDATE SET
    descripcion_corta = EXCLUDED.descripcion_corta,
    visitas = COALESCE(proyectos.visitas, EXCLUDED.visitas);

INSERT INTO enlaces_proyecto (id, proyecto_id, titulo, url) VALUES
(1, 1, 'Repositorio Principal (Parquet ETL)', 'https://github.com/herson-backpack/data-processor-parquet'),
(2, 1, 'Dataset de Prueba y Benchmarks', 'https://github.com/herson-backpack/data-processor-parquet/tree/main/datasets'),
(3, 2, 'Repositorio Backend Spring Boot', 'https://github.com/herson-backpack/springboot-layered-arch'),
(4, 2, 'Documentación OpenAPI / Swagger', 'https://github.com/herson-backpack/springboot-layered-arch/wiki'),
(5, 3, 'Esquema SQL 3FN', 'https://github.com/herson-backpack/lab-manager-postgres'),
(6, 4, 'Código Arduino & Diagramas', 'https://github.com/herson-backpack/arduino-hcsr04-security'),
(7, 5, 'Backend & Esquema SQL DataCenter', 'https://github.com/herson-backpack/datacenter-env-monitor'),
(8, 5, 'Firmware Microcontroladores (Arduino)', 'https://github.com/herson-backpack/datacenter-env-monitor-firmware')
ON CONFLICT (id) DO NOTHING;

INSERT INTO comentarios (id, proyecto_id, usuario_id, contenido) VALUES
(1, 1, 2, 'Excelente comparativa con Apache Parquet, redujo muchísimo el tamaño en disco.'),
(2, 2, 5, 'Muy limpia la separación de responsabilidades entre DTOs y Entidades JPA.'),
(3, 5, 1, 'Me encantó la integración entre los sensores del Data Center y PostgreSQL.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO proyecto_colaboradores (proyecto_id, usuario_id) VALUES
(5, 4)
ON CONFLICT DO NOTHING;

INSERT INTO mensajes_privados (id, remitente_id, destinatario_id, asunto, contenido, leido, es_invitacion, proyecto_invitado_id, estado_invitacion) VALUES
(1, 5, 1, 'Invitación a colaborar en DataCenter_Env_Monitor', '¡Hola data_ninja! Te invito a ser colaborador en el proyecto DataCenter_Env_Monitor para integrar métricas con Python.', FALSE, TRUE, 5, 'PENDIENTE'),
(2, 2, 1, 'Duda sobre compresión Snappy en Parquet', 'Hola, vi tu proyecto DataProcessor_Pandas y quería preguntarte qué compresión usaste en Pandas.', FALSE, FALSE, NULL, 'NO_APLICA')
ON CONFLICT (id) DO NOTHING;

-- Sincronizar secuencias de PostgreSQL para que nuevos registros no colisionen con IDs semilla
SELECT setval(pg_get_serial_sequence('usuarios', 'id'), COALESCE((SELECT MAX(id) FROM usuarios), 1), true);
SELECT setval(pg_get_serial_sequence('superusuarios', 'id'), COALESCE((SELECT MAX(id) FROM superusuarios), 1), true);
SELECT setval(pg_get_serial_sequence('proyectos', 'id'), COALESCE((SELECT MAX(id) FROM proyectos), 1), true);
SELECT setval(pg_get_serial_sequence('enlaces_proyecto', 'id'), COALESCE((SELECT MAX(id) FROM enlaces_proyecto), 1), true);
SELECT setval(pg_get_serial_sequence('comentarios', 'id'), COALESCE((SELECT MAX(id) FROM comentarios), 1), true);
SELECT setval(pg_get_serial_sequence('mensajes_privados', 'id'), COALESCE((SELECT MAX(id) FROM mensajes_privados), 1), true);
