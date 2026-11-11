/**
 * Herson's Backpack v3.0 - Cliente Frontend, Temas (Classic / White / Stelar),
 * Internacionalización (EN, ZH, ES) y Conexión REST API Spring Boot + PostgreSQL 3FN
 * Incluye: Superusuarios, Múltiples Enlaces, Comentarios, Mensajes Privados/Invitaciones,
 * Colaboradores, Marcador de Visitas, Creación de Cuentas Normales y 2 Descripciones.
 */

const API_PROYECTOS = '/api/proyectos';
const API_USUARIOS = '/api/usuarios';
const API_MENSAJES = '/api/mensajes';

const USUARIOS_SEMILLA = [
    { id: 1, username: 'data_ninja', email: 'ninja@herson.sh', rol: 'ESTUDIANTE', esSuperusuario: false, fechaRegistro: '2026-10-01T10:00:00' },
    { id: 2, username: 'java_dev99', email: 'javadev@herson.sh', rol: 'ESTUDIANTE', esSuperusuario: false, fechaRegistro: '2026-10-02T11:30:00' },
    { id: 3, username: 'admin_db', email: 'dba@herson.sh', rol: 'SUPERUSUARIO', esSuperusuario: true, fechaRegistro: '2026-10-03T09:15:00' },
    { id: 4, username: 'hardware_hacker', email: 'hw@herson.sh', rol: 'ESTUDIANTE', esSuperusuario: false, fechaRegistro: '2026-10-04T14:20:00' },
    { id: 5, username: 'herson_admin', email: 'imhex@bk.ru', rol: 'SUPERUSUARIO', esSuperusuario: true, fechaRegistro: '2026-10-05T08:00:00' }
];

const PROYECTOS_SEMILLA = [
    {
        id: 1,
        titulo: 'DataProcessor_Pandas',
        tipo: 'Programming',
        descripcionCorta: 'Transformación de archivos CSV pesados a formato columnar Apache Parquet con gráficas en Plotly.',
        descripcion: 'Scripts en Python desarrollados con Pandas y Plotly para transformar de manera eficiente archivos CSV pesados al formato Apache Parquet y generar visualizaciones de datos comparativas de uso de memoria y tiempos de lectura.',
        repositorioUrl: 'https://github.com/herson-backpack/data-processor-parquet',
        enlaces: [
            { id: 1, titulo: 'Repositorio Principal (Parquet ETL)', url: 'https://github.com/herson-backpack/data-processor-parquet' },
            { id: 2, titulo: 'Dataset de Prueba y Benchmarks', url: 'https://github.com/herson-backpack/data-processor-parquet/tree/main/datasets' }
        ],
        comentarios: [
            { id: 1, proyectoId: 1, usuarioId: 2, autorUsername: 'java_dev99', contenido: 'Excelente comparativa con Apache Parquet, redujo muchísimo el tamaño en disco.', fechaCreacion: '2026-10-16T11:00:00' }
        ],
        colaboradoresIds: [],
        colaboradoresUsernames: [],
        visitas: 24,
        tecnologias: 'Python, Pandas, Plotly, Apache Parquet',
        usuarioId: 1,
        autorUsername: 'data_ninja',
        fechaCreacion: '2026-10-10T12:00:00',
        fechaActualizacion: '2026-10-15T16:30:00'
    },
    {
        id: 2,
        titulo: 'SpringBoot_Backend_Arch',
        tipo: 'Programming',
        descripcionCorta: 'Arquitectura de software multicapa en Java 17 y Spring Boot 3 (Controller, Service, Repository, DTO, Model).',
        descripcion: 'Configuración de arquitectura de software para backend en Java utilizando el framework Spring Boot. Estructurado rigurosamente en capas MVC incluyendo paquetes para Controller, DTO, Service, Repository y Model.',
        repositorioUrl: 'https://github.com/herson-backpack/springboot-layered-arch',
        enlaces: [
            { id: 3, titulo: 'Repositorio Backend Spring Boot', url: 'https://github.com/herson-backpack/springboot-layered-arch' },
            { id: 4, titulo: 'Documentación OpenAPI / Swagger', url: 'https://github.com/herson-backpack/springboot-layered-arch/wiki' }
        ],
        comentarios: [
            { id: 2, proyectoId: 2, usuarioId: 5, autorUsername: 'herson_admin', contenido: 'Muy limpia la separación de responsabilidades entre DTOs y Entidades JPA.', fechaCreacion: '2026-10-17T09:30:00' }
        ],
        colaboradoresIds: [],
        colaboradoresUsernames: [],
        visitas: 42,
        tecnologias: 'Java 17, Spring Boot 3, JPA, REST API',
        usuarioId: 2,
        autorUsername: 'java_dev99',
        fechaCreacion: '2026-10-11T09:00:00',
        fechaActualizacion: '2026-10-16T10:15:00'
    },
    {
        id: 3,
        titulo: 'LabManager_Postgres',
        tipo: 'Database',
        descripcionCorta: 'Base de datos relacional en 3FN en PostgreSQL para gestión de laboratorios de cómputo y mantenimiento.',
        descripcion: 'Diseño e implementación de una base de datos relacional en PostgreSQL (gestionada vía pgAdmin 4) para administrar un laboratorio de cómputo, manteniendo registros detallados del hardware y bitácoras de mantenimiento mediante llaves primarias y foráneas.',
        repositorioUrl: 'https://github.com/herson-backpack/lab-manager-postgres',
        enlaces: [
            { id: 5, titulo: 'Esquema SQL 3FN', url: 'https://github.com/herson-backpack/lab-manager-postgres' }
        ],
        comentarios: [],
        colaboradoresIds: [],
        colaboradoresUsernames: [],
        visitas: 31,
        tecnologias: 'PostgreSQL, pgAdmin 4, SQL, Álgebra Relacional',
        usuarioId: 3,
        autorUsername: 'admin_db',
        fechaCreacion: '2026-10-12T15:20:00',
        fechaActualizacion: '2026-10-17T18:45:00'
    },
    {
        id: 4,
        titulo: 'HC_SR04_Proximity_Sensor',
        tipo: 'Hardware & IoT',
        descripcionCorta: 'Sistema de detección ultrasónica de proximidad con Arduino IDE, sensor HC-SR04 y alertas LED.',
        descripcion: 'Código en Arduino IDE y diagramas de protoboard para integrar y controlar un sensor de proximidad ultrasónico HC-SR04, junto con una secuencia de LEDs alternantes para alertas visuales de seguridad.',
        repositorioUrl: 'https://github.com/herson-backpack/arduino-hcsr04-security',
        enlaces: [
            { id: 6, titulo: 'Código Arduino & Diagramas', url: 'https://github.com/herson-backpack/arduino-hcsr04-security' }
        ],
        comentarios: [],
        colaboradoresIds: [],
        colaboradoresUsernames: [],
        visitas: 19,
        tecnologias: 'Arduino IDE, C++, HC-SR04, Protoboard',
        usuarioId: 4,
        autorUsername: 'hardware_hacker',
        fechaCreacion: '2026-10-13T11:10:00',
        fechaActualizacion: '2026-10-18T14:00:00'
    },
    {
        id: 5,
        titulo: 'DataCenter_Env_Monitor',
        tipo: 'Networks & Sec',
        descripcionCorta: 'Monitoreo ambiental y seguridad física para Data Center universitario con Spring Boot, Postgres y sensores.',
        descripcion: 'Sistema integral de monitoreo ambiental y de seguridad para centros de datos universitarios utilizando microcontroladores, sensores de temperatura/proximidad, PostgreSQL y backend en Java Spring Boot.',
        repositorioUrl: 'https://github.com/herson-backpack/datacenter-env-monitor',
        enlaces: [
            { id: 7, titulo: 'Backend & Esquema SQL DataCenter', url: 'https://github.com/herson-backpack/datacenter-env-monitor' },
            { id: 8, titulo: 'Firmware Microcontroladores (Arduino)', url: 'https://github.com/herson-backpack/datacenter-env-monitor-firmware' }
        ],
        comentarios: [
            { id: 3, proyectoId: 5, usuarioId: 1, autorUsername: 'data_ninja', contenido: 'Me encantó la integración entre los sensores del Data Center y PostgreSQL.', fechaCreacion: '2026-10-20T14:00:00' }
        ],
        colaboradoresIds: [4],
        colaboradoresUsernames: ['hardware_hacker'],
        visitas: 58,
        tecnologias: 'Spring Boot, PostgreSQL, Microcontroladores, IEEE',
        usuarioId: 5,
        autorUsername: 'herson_admin',
        fechaCreacion: '2026-10-14T08:30:00',
        fechaActualizacion: '2026-10-19T20:10:00'
    }
];

const MENSAJES_SEMILLA = [
    {
        id: 1,
        remitenteId: 5,
        remitenteUsername: 'herson_admin',
        destinatarioId: 1,
        destinatarioUsername: 'data_ninja',
        asunto: 'Invitación a colaborar en DataCenter_Env_Monitor',
        contenido: '¡Hola data_ninja! Te invito a ser colaborador en el proyecto DataCenter_Env_Monitor para integrar métricas con Python.',
        leido: false,
        esInvitacion: true,
        proyectoInvitadoId: 5,
        proyectoInvitadoTitulo: 'DataCenter_Env_Monitor',
        estadoInvitacion: 'PENDIENTE',
        fechaEnvio: '2026-10-21T10:00:00'
    },
    {
        id: 2,
        remitenteId: 2,
        remitenteUsername: 'java_dev99',
        destinatarioId: 1,
        destinatarioUsername: 'data_ninja',
        asunto: 'Duda sobre compresión Snappy en Parquet',
        contenido: 'Hola, vi tu proyecto DataProcessor_Pandas y quería preguntarte qué compresión usaste en Pandas.',
        leido: false,
        esInvitacion: false,
        proyectoInvitadoId: null,
        proyectoInvitadoTitulo: null,
        estadoInvitacion: 'NO_APLICA',
        fechaEnvio: '2026-10-22T15:10:00'
    }
];

function initFallbackStorage() {
    if (!localStorage.getItem('hb_v3_usuarios_fallback')) {
        localStorage.setItem('hb_v3_usuarios_fallback', JSON.stringify(USUARIOS_SEMILLA));
    }
    if (!localStorage.getItem('hb_v3_proyectos_fallback')) {
        localStorage.setItem('hb_v3_proyectos_fallback', JSON.stringify(PROYECTOS_SEMILLA));
    }
    if (!localStorage.getItem('hb_v3_mensajes_fallback')) {
        localStorage.setItem('hb_v3_mensajes_fallback', JSON.stringify(MENSAJES_SEMILLA));
    }
}
initFallbackStorage();

// ==========================================
// DICCIONARIO MULTILENGUAJE (EN default, ZH, ES)
// ==========================================
const I18N = {
    en: {
        nav_settings: "Settings",
        nav_about: "About",
        nav_contact: "Contact",
        nav_whatsnew: "whats_new.txt",
        nav_login: "Login",
        nav_signup: "Sign up",
        nav_account_details: "Account Details",
        nav_my_projects: "My Projects & Collabs",
        nav_messages: "Private Messages",
        nav_logout: "Log out",
        search_placeholder: "Backstage (Search university projects...)",
        btn_newbackp: "NewBackP",
        btn_checkbackp: "CheckBackP",
        settings_title: "> system_settings.cfg",
        settings_lang_label: "Interface Language:",
        settings_theme_label: "Visual Theme (Aspect):",
        theme_classic: "Classic (Nord Dark / Green)",
        theme_white: "White (Beige / Monochrome Gray)",
        theme_stelar: "Stelar (Deep Space / Cyan Blue)",
        about_title: "> about_herson_backpack.md",
        about_tab_why: "Why was the page created?",
        about_tab_tech: "What technologies does it have?",
        about_tab_thanks: "Thanks for using",
        about_why_content: "Herson's Backpack was created as a centralized university repository and inspiration engine. Its goal is to allow students to classify, upload, and showcase their academic projects so peers can explore real implementations, collaborate, and find inspiration.",
        about_tech_content: "Built with Java 17 & Spring Boot 3 (Model, DTO, Repository, Service, REST Controller), relational persistence in PostgreSQL (3NF with Superusers, Links, Comments, Collaborators, and Private Messages), and a NeoVim/Telescope-inspired frontend.",
        about_thanks_content: "Thank you for exploring, sharing, and building with Herson's Backpack!",
        contact_title: "> contact_channels.txt",
        contact_desc: "For any problem, bug report, suggestion, or similar inquiry, feel free to send a message to:",
        whatsnew_title: "> whats_new.txt",
        whatsnew_subtitle: "> Herson Project Present v3.0",
        whatsnew_1: "New Tables: Superuser, Multiple Links, Comments, Collaborators & Private Messages",
        whatsnew_2: "Visit Counter (+1 per view) & Dual Descriptions (Short for search, Long for detail)",
        whatsnew_3: "Collaborator Invitations via DM (Collaborators can edit, only Owners/Superusers can delete)",
        account_modal_title: "> account_profile.info",
        account_username: "Username:",
        account_email: "Email:",
        account_role: "Role:",
        account_projects_count: "My Projects & Collabs:",
        login_title: "Authenticate with your account",
        login_btn: "Login",
        login_no_account: "Need a new normal user account?",
        signup_btn: "Create Normal Account",
        signup_has_account: "Already have an account?",
        search_bar_top: "Search by title, short/long description, stack or author...",
        filter_type: "Type:",
        filter_all: "ALL",
        results_loading: "> Loading projects from PostgreSQL...",
        btn_view_details: "[view] View Details",
        btn_edit_project: "[edit] Modify Project",
        btn_delete_project: "[del] Delete",
        badge_yours: "[OWNER]",
        badge_collab: "[COLLABORATOR - EDIT ONLY]",
        badge_superuser: "[SUPERUSER ACCESS]",
        badge_readonly: "[READ-ONLY]",
        ws_title: "My Workspace :: Projects, Collaborations & Messages",
        ws_subtitle: "Upload projects (with short/long descriptions & multiple links), edit collaborations, or send private messages/invitations.",
        ws_new_title: "> Create / Edit University Project",
        ws_my_projects_title: "> My Projects & Collaborations",
        ws_login_required: "Please log in or create an account to manage projects and messages.",
        form_title: "Project Title (Identifier):",
        form_type: "Project Type:",
        form_stack: "Technologies / Stack:",
        form_short_desc: "Short Description (Shown in Search Cards):",
        form_desc: "Long Detailed Description (Shown when clicking the project):",
        form_links: "Project Links / Repositories (Add one or more):",
        btn_add_link: "[+ Add another link]",
        btn_cancel: "[esc] Cancel",
        btn_save: "[save] Publish Project",
        btn_update: "[update] Save Changes",
        detail_modal_title: "> project_inspector.ro",
        detail_author: "Owner:",
        detail_collabs: "Collaborators:",
        detail_type: "Category / Type:",
        detail_views: "Total Visits:",
        detail_stack: "Technologies & Stack:",
        detail_repo: "Project Links & Repositories:",
        detail_short_desc: "Summary (Short Description):",
        detail_desc: "Full Project Description (Long Description):",
        detail_owner_notice: "You have permission to modify this project.",
        detail_visitor_notice: "Read-only mode: You can explore all links, read the full description, and leave comments below."
    },
    zh: {
        nav_settings: "设置",
        nav_about: "关于",
        nav_contact: "联系",
        nav_whatsnew: "whats_new.txt",
        nav_login: "登录",
        nav_signup: "注册新账号",
        nav_account_details: "账户详情",
        nav_my_projects: "我的项目与协作",
        nav_messages: "私信与邀请",
        nav_logout: "退出登录",
        search_placeholder: "搜索大学项目...",
        btn_newbackp: "新建项目 (NewBackP)",
        btn_checkbackp: "工作区与私信 (CheckBackP)",
        settings_title: "> 系统设置",
        settings_lang_label: "界面语言:",
        settings_theme_label: "外观主题:",
        theme_classic: "经典 (Classic)",
        theme_white: "纯白 (White)",
        theme_stelar: "星际 (Stelar)",
        about_title: "> 关于项目",
        about_tab_why: "为什么创建？",
        about_tab_tech: "使用了哪些技术？",
        about_tab_thanks: "感谢使用",
        about_why_content: "Herson's Backpack 是大学项目的集中式数据库与协作平台。",
        about_tech_content: "Java 17、Spring Boot 3 与 PostgreSQL 关系型数据库（含超级用户、多链接、评论、协作者与私信表）。",
        about_thanks_content: "感谢使用 Herson's Backpack！",
        contact_title: "> 联系方式",
        contact_desc: "如有任何问题或建议，请发送邮件至：",
        whatsnew_title: "> whats_new.txt",
        whatsnew_subtitle: "> Herson Project Present v3.0",
        whatsnew_1: "新增数据表：超级用户、多仓库链接、评论、协作者与私信",
        whatsnew_2: "浏览量计数器 (+1) 与双描述（搜索简短描述 + 详情长描述）",
        whatsnew_3: "私信协作邀请（协作者仅可修改，不可删除；普通用户注册）",
        account_modal_title: "> 账户信息",
        account_username: "用户名:",
        account_email: "邮箱:",
        account_role: "角色:",
        account_projects_count: "项目与协作数:",
        login_title: "账户登录",
        login_btn: "登录",
        login_no_account: "还没有账号？",
        signup_btn: "注册普通用户",
        signup_has_account: "已有账户？",
        search_bar_top: "按标题、简短描述、技术栈或作者搜索...",
        filter_type: "类型:",
        filter_all: "全部",
        results_loading: "> 正在从 PostgreSQL 加载...",
        btn_view_details: "[查看] 查看详情",
        btn_edit_project: "[编辑] 修改项目",
        btn_delete_project: "[删除] 删除",
        badge_yours: "[项目所有者]",
        badge_collab: "[协作者 - 仅可编辑]",
        badge_superuser: "[超级用户权限]",
        badge_readonly: "[只读视图]",
        ws_title: "工作区 :: 项目管理、协作与私信",
        ws_subtitle: "创建带简短/详细描述与多链接的项目，管理协作或发送私信邀请。",
        ws_new_title: "> 创建 / 编辑大学项目",
        ws_my_projects_title: "> 我的项目与参与的协作",
        ws_login_required: "请先登录或注册普通账户。",
        form_title: "项目名称:",
        form_type: "项目类型:",
        form_stack: "技术栈:",
        form_short_desc: "简短描述 (显示在搜索列表):",
        form_desc: "详细长描述 (点击进入项目时显示):",
        form_links: "项目链接 / 多个仓库:",
        btn_add_link: "[+ 添加更多链接]",
        btn_cancel: "[esc] 取消",
        btn_save: "[save] 发布项目",
        btn_update: "[update] 保存修改",
        detail_modal_title: "> 项目详情",
        detail_author: "所有者:",
        detail_collabs: "协作者:",
        detail_type: "项目类型:",
        detail_views: "浏览次数:",
        detail_stack: "技术栈:",
        detail_repo: "相关链接与仓库:",
        detail_short_desc: "项目摘要 (简短描述):",
        detail_desc: "完整项目说明 (详细长描述):",
        detail_owner_notice: "您拥有修改此项目的权限。",
        detail_visitor_notice: "只读视图：您可以查看所有链接并在下方发表评论。"
    },
    es: {
        nav_settings: "Configuración",
        nav_about: "Acerca de",
        nav_contact: "Contacto",
        nav_whatsnew: "whats_new.txt",
        nav_login: "Iniciar Sesión",
        nav_signup: "Crear Cuenta",
        nav_account_details: "Detalles de la cuenta",
        nav_my_projects: "Mis Proyectos y Colaboraciones",
        nav_messages: "Mensajes Privados",
        nav_logout: "Salir de la cuenta",
        search_placeholder: "Backstage (Buscar proyectos universitarios...)",
        btn_newbackp: "NewBackP",
        btn_checkbackp: "CheckBackP",
        settings_title: "> configuracion_sistema.cfg",
        settings_lang_label: "Idioma de la aplicación:",
        settings_theme_label: "Aspecto visual (Tema):",
        theme_classic: "Clásico (Oscuro Nord / Verde)",
        theme_white: "White (Fondo Blanco Beige / Letras Grises y Negras)",
        theme_stelar: "Stelar (Tonos Azulados / Letras Azules)",
        about_title: "> acerca_de_herson_backpack.md",
        about_tab_why: "¿Por qué se creó la página?",
        about_tab_tech: "¿Qué tecnologías tiene?",
        about_tab_thanks: "Gracias por usar",
        about_why_content: "Herson's Backpack es un repositorio universitario interactivo donde cada estudiante puede publicar proyectos con múltiples enlaces, recibir comentarios, invitar colaboradores por mensaje privado e inspirarse.",
        about_tech_content: "Arquitectura en capas con Java 17 y Spring Boot 3 + PostgreSQL en 3FN (Tablas: usuarios, superusuarios, proyectos, enlaces_proyecto, comentarios, proyecto_colaboradores y mensajes_privados).",
        about_thanks_content: "¡Muchas gracias por usar Herson's Backpack!",
        contact_title: "> contacto_soporte.txt",
        contact_desc: "Cualquier problema, sugerencia o similar pueden mandar un mensaje a este correo:",
        whatsnew_title: "> whats_new.txt",
        whatsnew_subtitle: "> Herson Project Present v3.0",
        whatsnew_1: "Nuevas Tablas: Superusuario, Enlaces múltiples, Comentarios, Colaboradores y Mensajes Privados",
        whatsnew_2: "Marcador de Visitas (+1 al abrir) y 2 Descripciones (Corta en buscador y Larga en detalle)",
        whatsnew_3: "Invitación de Colaboradores por Mensaje Privado y Registro de Cuentas Normales",
        account_modal_title: "> detalles_de_cuenta.info",
        account_username: "Usuario:",
        account_email: "Correo:",
        account_role: "Rol:",
        account_projects_count: "Proyectos y Colaboraciones:",
        login_title: "Inicia sesión en tu cuenta",
        login_btn: "Iniciar Sesión",
        login_no_account: "¿No tienes cuenta?",
        signup_btn: "Crear Cuenta Normal",
        signup_has_account: "¿Ya tienes cuenta?",
        search_bar_top: "Buscar por título, descripción corta/larga, tecnología o autor...",
        filter_type: "Tipo:",
        filter_all: "TODOS",
        results_loading: "> Cargando proyectos desde PostgreSQL...",
        btn_view_details: "[ver] Ver Detalles",
        btn_edit_project: "[edit] Modificar Proyecto",
        btn_delete_project: "[del] Eliminar",
        badge_yours: "[DUEÑO]",
        badge_collab: "[COLABORADOR - SOLO EDITAR]",
        badge_superuser: "[SUPERUSUARIO]",
        badge_readonly: "[SOLO LECTURA]",
        ws_title: "Mi Espacio :: Proyectos, Colaboraciones y Mensajes Privados",
        ws_subtitle: "Crea proyectos con 2 descripciones y múltiples enlaces, edita colaboraciones o envía mensajes privados e invitaciones.",
        ws_new_title: "> Crear / Editar Proyecto Universitario",
        ws_my_projects_title: "> Mis Proyectos y Colaboraciones",
        ws_login_required: "Inicia sesión o crea una cuenta normal para gestionar proyectos y mensajes privados.",
        form_title: "Título del Proyecto:",
        form_type: "Tipo de Proyecto:",
        form_stack: "Tecnologías / Stack:",
        form_short_desc: "Descripción Pequeña (Para el buscador):",
        form_desc: "Descripción Larga (Se muestra al darle clic al proyecto):",
        form_links: "Enlaces / Repositorios del Proyecto (Puedes añadir varios):",
        btn_add_link: "[+ Añadir otro enlace]",
        btn_cancel: "[esc] Cancelar",
        btn_save: "[save] Publicar Proyecto",
        btn_update: "[update] Guardar Cambios",
        detail_modal_title: "> visualizador_proyecto.ro",
        detail_author: "Dueño del proyecto:",
        detail_collabs: "Colaboradores:",
        detail_type: "Categoría / Tipo:",
        detail_views: "Marcador de Visitas:",
        detail_stack: "Tecnologías utilizadas:",
        detail_repo: "Enlaces y Repositorios:",
        detail_short_desc: "Descripción Pequeña (Resumen):",
        detail_desc: "Descripción Larga (Detalle completo del proyecto):",
        detail_owner_notice: "Tienes permisos de edición sobre este proyecto.",
        detail_visitor_notice: "Modo lectura: explora la descripción larga, todos sus enlaces y deja tus comentarios abajo."
    }
};

function injectThemeStyles() {
    if (document.getElementById('hb-theme-styles')) return;
    const style = document.createElement('style');
    style.id = 'hb-theme-styles';
    style.textContent = `
        :root, [data-theme="classic"] {
            --bg-base: #1e222a;
            --bg-panel: #181a20;
            --bg-panel-alpha: rgba(24, 26, 32, 0.85);
            --text-main: #ffffff;
            --text-body: #d8dee9;
            --text-muted: #9ca3af;
            --accent: #4ade80;
            --accent-soft: rgba(74, 222, 128, 0.2);
            --accent-glow: rgba(74, 222, 128, 0.6);
            --border-color: #4b5563;
            --blob-1: #2a5a3b;
            --blob-2: #1a4d33;
            --matrix-color: #22c55e;
            --matrix-fade: rgba(30, 34, 42, 0.12);
        }

        [data-theme="white"] {
            --bg-base: #f5f2eb;
            --bg-panel: #ebe6dc;
            --bg-panel-alpha: rgba(235, 230, 220, 0.92);
            --text-main: #111111;
            --text-body: #1f2937;
            --text-muted: #4b5563;
            --accent: #52525b;
            --accent-soft: rgba(82, 82, 91, 0.15);
            --accent-glow: rgba(82, 82, 91, 0.35);
            --border-color: #a8a29e;
            --blob-1: #d6d3cd;
            --blob-2: #c7c2b8;
            --matrix-color: #71717a;
            --matrix-fade: rgba(245, 242, 235, 0.18);
        }

        [data-theme="stelar"] {
            --bg-base: #0d1526;
            --bg-panel: #131f38;
            --bg-panel-alpha: rgba(19, 31, 56, 0.88);
            --text-main: #f0f7ff;
            --text-body: #cbd5e1;
            --text-muted: #6482ad;
            --accent: #38bdf8;
            --accent-soft: rgba(56, 189, 248, 0.2);
            --accent-glow: rgba(56, 189, 248, 0.6);
            --border-color: #2b4570;
            --blob-1: #1e3a8a;
            --blob-2: #0369a1;
            --matrix-color: #38bdf8;
            --matrix-fade: rgba(13, 21, 38, 0.14);
        }

        body {
            background-color: var(--bg-base) !important;
            color: var(--text-body) !important;
            transition: background-color 0.25s ease, color 0.25s ease;
        }

        .hb-panel { background-color: var(--bg-panel) !important; border-color: var(--border-color) !important; }
        .hb-panel-alpha { background-color: var(--bg-panel-alpha) !important; border-color: var(--border-color) !important; }
        .hb-accent, .text-green-400, .text-green-500 { color: var(--accent) !important; }
        .hb-text-main, .text-white { color: var(--text-main) !important; }
        .hb-text-muted, .text-gray-300, .text-gray-400, .text-gray-500 { color: var(--text-muted) !important; }
        .hb-border, .border-gray-600, .border-gray-700, .border-gray-800 { border-color: var(--border-color) !important; }
        .blob-1 { background: radial-gradient(circle, var(--blob-1) 0%, transparent 70%) !important; }
        .blob-2 { background: radial-gradient(circle, var(--blob-2) 0%, transparent 70%) !important; }
        .dash-btn::before { background: var(--accent) !important; }
        .dash-btn:hover { color: var(--bg-base) !important; border-color: var(--accent) !important; box-shadow: 0 0 10px var(--accent-glow) !important; }
        .cmd-input:focus-within, .search-bar:focus-within { box-shadow: 0 0 15px var(--accent-soft) !important; border-color: var(--accent) !important; }
        .icon-btn:hover { color: var(--accent) !important; filter: drop-shadow(0 0 5px var(--accent)) !important; }
        .result-card:hover { border-left: 2px solid var(--accent) !important; background-color: var(--bg-panel-alpha) !important; }
    `;
    document.head.appendChild(style);
}

const HBClient = {
    getLang() {
        return localStorage.getItem('hb_lang') || 'es';
    },
    setLang(lang) {
        if (!I18N[lang]) lang = 'es';
        localStorage.setItem('hb_lang', lang);
        this.applyTranslations();
        window.dispatchEvent(new CustomEvent('hb:langChanged', { detail: { lang } }));
    },
    t(key) {
        const lang = this.getLang();
        return (I18N[lang] && I18N[lang][key]) || I18N.en[key] || key;
    },
    applyTranslations() {
        document.querySelectorAll('[data-i18n]').forEach(el => {
            const key = el.getAttribute('data-i18n');
            el.textContent = this.t(key);
        });
        document.querySelectorAll('[data-i18n-placeholder]').forEach(el => {
            const key = el.getAttribute('data-i18n-placeholder');
            el.setAttribute('placeholder', this.t(key));
        });
    },

    getTheme() {
        return localStorage.getItem('hb_theme') || 'classic';
    },
    setTheme(theme) {
        if (!['classic', 'white', 'stelar'].includes(theme)) theme = 'classic';
        localStorage.setItem('hb_theme', theme);
        document.documentElement.setAttribute('data-theme', theme);
        window.dispatchEvent(new CustomEvent('hb:themeChanged', { detail: { theme } }));
    },
    getMatrixColors() {
        const theme = this.getTheme();
        if (theme === 'white') return { fill: 'rgba(245, 242, 235, 0.18)', text: '#52525b' };
        if (theme === 'stelar') return { fill: 'rgba(13, 21, 38, 0.14)', text: '#38bdf8' };
        return { fill: 'rgba(30, 34, 42, 0.12)', text: '#22c55e' };
    },

    getCurrentUser() {
        const raw = localStorage.getItem('hb_active_user');
        if (!raw) return null;
        try { return JSON.parse(raw); } catch (e) { return null; }
    },
    setCurrentUser(user) {
        if (!user) localStorage.removeItem('hb_active_user');
        else localStorage.setItem('hb_active_user', JSON.stringify(user));
    },
    logout() {
        localStorage.removeItem('hb_active_user');
        window.location.href = 'index.html';
    },

    // Permisos en cliente sincronizados con el backend
    esSuperusuario(user) {
        if (!user) return false;
        return Boolean(user.esSuperusuario) || user.rol === 'SUPERUSUARIO' || user.rol === 'ADMIN';
    },
    esDueno(proyecto, user) {
        if (!proyecto || !user) return false;
        return Number(proyecto.usuarioId) === Number(user.id);
    },
    esColaborador(proyecto, user) {
        if (!proyecto || !user || !Array.isArray(proyecto.colaboradoresIds)) return false;
        return proyecto.colaboradoresIds.some(id => Number(id) === Number(user.id));
    },
    puedeModificar(proyecto, user) {
        if (!proyecto || !user) return false;
        return this.esSuperusuario(user) || this.esDueno(proyecto, user) || this.esColaborador(proyecto, user);
    },
    puedeEliminar(proyecto, user) {
        if (!proyecto || !user) return false;
        // Colaborador NO puede eliminar, solo dueño o superusuario
        return this.esSuperusuario(user) || this.esDueno(proyecto, user);
    },

    async getUsuarios() {
        try {
            const res = await fetch(API_USUARIOS);
            if (!res.ok) throw new Error('Backend offline');
            return await res.json();
        } catch (err) {
            return JSON.parse(localStorage.getItem('hb_v3_usuarios_fallback') || '[]');
        }
    },

    async getProyectos(query = '', tipo = '', usuarioId = '') {
        const params = new URLSearchParams();
        if (query) params.append('q', query);
        if (tipo && tipo !== 'ALL') params.append('tipo', tipo);
        if (usuarioId) params.append('usuarioId', usuarioId);

        try {
            const res = await fetch(`${API_PROYECTOS}?${params.toString()}`);
            if (!res.ok) throw new Error('Backend offline');
            return await res.json();
        } catch (err) {
            let lista = JSON.parse(localStorage.getItem('hb_v3_proyectos_fallback') || '[]');
            if (usuarioId) {
                lista = lista.filter(p =>
                    Number(p.usuarioId) === Number(usuarioId) ||
                    (Array.isArray(p.colaboradoresIds) && p.colaboradoresIds.some(cid => Number(cid) === Number(usuarioId)))
                );
            }
            if (query) {
                const q = query.toLowerCase();
                lista = lista.filter(p =>
                    (p.titulo && p.titulo.toLowerCase().includes(q)) ||
                    (p.tipo && p.tipo.toLowerCase().includes(q)) ||
                    (p.descripcionCorta && p.descripcionCorta.toLowerCase().includes(q)) ||
                    (p.descripcion && p.descripcion.toLowerCase().includes(q)) ||
                    (p.tecnologias && p.tecnologias.toLowerCase().includes(q)) ||
                    (p.autorUsername && p.autorUsername.toLowerCase().includes(q))
                );
            }
            if (tipo && tipo !== 'ALL') {
                const t = tipo.toLowerCase();
                lista = lista.filter(p => p.tipo && p.tipo.toLowerCase().includes(t));
            }
            return lista;
        }
    },

    async getProyectoPorId(id, incrementarVisita = true) {
        try {
            const res = await fetch(`${API_PROYECTOS}/${id}?visitar=${incrementarVisita}`);
            if (!res.ok) throw new Error('No encontrado');
            return await res.json();
        } catch (err) {
            const lista = JSON.parse(localStorage.getItem('hb_v3_proyectos_fallback') || '[]');
            const idx = lista.findIndex(p => Number(p.id) === Number(id));
            if (idx === -1) return null;
            if (incrementarVisita) {
                lista[idx].visitas = (Number(lista[idx].visitas) || 0) + 1;
                localStorage.setItem('hb_v3_proyectos_fallback', JSON.stringify(lista));
            }
            return lista[idx];
        }
    },

    async crearProyecto(dto) {
        try {
            const res = await fetch(API_PROYECTOS, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dto)
            });
            if (!res.ok) throw new Error('Error en API');
            return await res.json();
        } catch (err) {
            const lista = JSON.parse(localStorage.getItem('hb_v3_proyectos_fallback') || '[]');
            const autor = this.getCurrentUser() || USUARIOS_SEMILLA[0];
            const enlaces = Array.isArray(dto.enlaces) && dto.enlaces.length > 0
                ? dto.enlaces
                : (dto.repositorioUrl ? [{ id: Date.now(), titulo: 'Repositorio Principal', url: dto.repositorioUrl }] : []);
            const nuevo = {
                ...dto,
                id: Date.now(),
                descripcionCorta: dto.descripcionCorta || (dto.descripcion ? dto.descripcion.substring(0, 160) : ''),
                enlaces,
                repositorioUrl: enlaces.length > 0 ? enlaces[0].url : '',
                comentarios: [],
                colaboradoresIds: [],
                colaboradoresUsernames: [],
                visitas: 0,
                autorUsername: autor.username,
                fechaCreacion: new Date().toISOString(),
                fechaActualizacion: new Date().toISOString()
            };
            lista.unshift(nuevo);
            localStorage.setItem('hb_v3_proyectos_fallback', JSON.stringify(lista));
            return nuevo;
        }
    },

    async actualizarProyecto(id, dto) {
        const user = this.getCurrentUser();
        const editorId = user ? user.id : dto.usuarioId;
        try {
            const res = await fetch(`${API_PROYECTOS}/${id}?editorUsuarioId=${editorId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dto)
            });
            if (!res.ok) {
                const err = await res.json();
                throw new Error(err.error || 'Error en API');
            }
            return await res.json();
        } catch (err) {
            const lista = JSON.parse(localStorage.getItem('hb_v3_proyectos_fallback') || '[]');
            const idx = lista.findIndex(p => Number(p.id) === Number(id));
            if (idx !== -1) {
                if (!this.puedeModificar(lista[idx], user)) {
                    throw new Error('No tienes permisos para modificar este proyecto.');
                }
                const enlaces = Array.isArray(dto.enlaces) ? dto.enlaces : lista[idx].enlaces;
                lista[idx] = {
                    ...lista[idx],
                    titulo: dto.titulo,
                    tipo: dto.tipo,
                    descripcionCorta: dto.descripcionCorta || lista[idx].descripcionCorta,
                    descripcion: dto.descripcion,
                    tecnologias: dto.tecnologias,
                    enlaces,
                    repositorioUrl: enlaces.length > 0 ? enlaces[0].url : '',
                    fechaActualizacion: new Date().toISOString()
                };
                localStorage.setItem('hb_v3_proyectos_fallback', JSON.stringify(lista));
                return lista[idx];
            }
            throw err;
        }
    },

    async eliminarProyecto(id) {
        const user = this.getCurrentUser();
        const solicitanteId = user ? user.id : '';
        try {
            const res = await fetch(`${API_PROYECTOS}/${id}?solicitanteUsuarioId=${solicitanteId}`, { method: 'DELETE' });
            if (!res.ok) {
                const err = await res.json();
                throw new Error(err.error || 'No autorizado para eliminar');
            }
            return await res.json();
        } catch (err) {
            let lista = JSON.parse(localStorage.getItem('hb_v3_proyectos_fallback') || '[]');
            const target = lista.find(p => Number(p.id) === Number(id));
            if (target && !this.puedeEliminar(target, user)) {
                throw new Error('Los colaboradores no pueden eliminar el proyecto; solo el dueño o un superusuario.');
            }
            lista = lista.filter(p => Number(p.id) !== Number(id));
            localStorage.setItem('hb_v3_proyectos_fallback', JSON.stringify(lista));
            return { mensaje: 'Eliminado' };
        }
    },

    async agregarComentario(proyectoId, contenido) {
        const user = this.getCurrentUser();
        if (!user) throw new Error('Debes iniciar sesión para comentar');
        try {
            const res = await fetch(`${API_PROYECTOS}/${proyectoId}/comentarios`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ usuarioId: user.id, contenido })
            });
            if (!res.ok) throw new Error('Error al comentar');
            return await res.json();
        } catch (err) {
            const lista = JSON.parse(localStorage.getItem('hb_v3_proyectos_fallback') || '[]');
            const idx = lista.findIndex(p => Number(p.id) === Number(proyectoId));
            if (idx !== -1) {
                const nuevoCom = {
                    id: Date.now(),
                    proyectoId: Number(proyectoId),
                    usuarioId: user.id,
                    autorUsername: user.username,
                    contenido,
                    fechaCreacion: new Date().toISOString()
                };
                lista[idx].comentarios = lista[idx].comentarios || [];
                lista[idx].comentarios.unshift(nuevoCom);
                localStorage.setItem('hb_v3_proyectos_fallback', JSON.stringify(lista));
                return nuevoCom;
            }
            throw err;
        }
    },

    async getMensajesRecibidos(usuarioId) {
        try {
            const res = await fetch(`${API_MENSAJES}/recibidos/${usuarioId}`);
            if (!res.ok) throw new Error('Offline');
            return await res.json();
        } catch (err) {
            const msgs = JSON.parse(localStorage.getItem('hb_v3_mensajes_fallback') || '[]');
            return msgs.filter(m => Number(m.destinatarioId) === Number(usuarioId));
        }
    },

    async getMensajesEnviados(usuarioId) {
        try {
            const res = await fetch(`${API_MENSAJES}/enviados/${usuarioId}`);
            if (!res.ok) throw new Error('Offline');
            return await res.json();
        } catch (err) {
            const msgs = JSON.parse(localStorage.getItem('hb_v3_mensajes_fallback') || '[]');
            return msgs.filter(m => Number(m.remitenteId) === Number(usuarioId));
        }
    },

    async enviarMensajePrivado({ remitenteId, destinatarioUsername, asunto, contenido, proyectoInvitadoId }) {
        try {
            const res = await fetch(API_MENSAJES, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ remitenteId, destinatarioUsername, asunto, contenido, proyectoInvitadoId })
            });
            if (!res.ok) {
                const err = await res.json();
                throw new Error(err.error || 'Error enviando mensaje');
            }
            return await res.json();
        } catch (err) {
            if (err.message && !err.message.includes('Failed to fetch') && !err.message.includes('NetworkError')) {
                throw err;
            }
            const usuarios = JSON.parse(localStorage.getItem('hb_v3_usuarios_fallback') || '[]');
            const remitente = usuarios.find(u => Number(u.id) === Number(remitenteId)) || this.getCurrentUser();
            const dest = usuarios.find(u => u.username.toLowerCase() === destinatarioUsername.trim().toLowerCase());
            if (!dest) throw new Error(`El usuario '${destinatarioUsername}' no existe.`);

            const proyectos = JSON.parse(localStorage.getItem('hb_v3_proyectos_fallback') || '[]');
            let proy = null;
            if (proyectoInvitadoId) {
                proy = proyectos.find(p => Number(p.id) === Number(proyectoInvitadoId));
            }

            const msgs = JSON.parse(localStorage.getItem('hb_v3_mensajes_fallback') || '[]');
            const nuevo = {
                id: Date.now(),
                remitenteId: remitente.id,
                remitenteUsername: remitente.username,
                destinatarioId: dest.id,
                destinatarioUsername: dest.username,
                asunto: asunto || (proy ? `Invitación a colaborar en: ${proy.titulo}` : 'Mensaje directo'),
                contenido,
                leido: false,
                esInvitacion: Boolean(proy),
                proyectoInvitadoId: proy ? proy.id : null,
                proyectoInvitadoTitulo: proy ? proy.titulo : null,
                estadoInvitacion: proy ? 'PENDIENTE' : 'NO_APLICA',
                fechaEnvio: new Date().toISOString()
            };
            msgs.unshift(nuevo);
            localStorage.setItem('hb_v3_mensajes_fallback', JSON.stringify(msgs));
            return nuevo;
        }
    },

    async responderInvitacion(mensajeId, aceptar) {
        const user = this.getCurrentUser();
        if (!user) throw new Error('Sesión requerida');
        try {
            const res = await fetch(`${API_MENSAJES}/${mensajeId}/responder-invitacion`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ usuarioId: user.id, aceptar })
            });
            if (!res.ok) throw new Error('Error al responder invitación');
            return await res.json();
        } catch (err) {
            const msgs = JSON.parse(localStorage.getItem('hb_v3_mensajes_fallback') || '[]');
            const mIdx = msgs.findIndex(m => Number(m.id) === Number(mensajeId));
            if (mIdx !== -1) {
                msgs[mIdx].estadoInvitacion = aceptar ? 'ACEPTADA' : 'RECHAZADA';
                msgs[mIdx].leido = true;
                if (aceptar && msgs[mIdx].proyectoInvitadoId) {
                    const proys = JSON.parse(localStorage.getItem('hb_v3_proyectos_fallback') || '[]');
                    const pIdx = proys.findIndex(p => Number(p.id) === Number(msgs[mIdx].proyectoInvitadoId));
                    if (pIdx !== -1) {
                        proys[pIdx].colaboradoresIds = proys[pIdx].colaboradoresIds || [];
                        proys[pIdx].colaboradoresUsernames = proys[pIdx].colaboradoresUsernames || [];
                        if (!proys[pIdx].colaboradoresIds.includes(user.id)) {
                            proys[pIdx].colaboradoresIds.push(user.id);
                            proys[pIdx].colaboradoresUsernames.push(user.username);
                        }
                        localStorage.setItem('hb_v3_proyectos_fallback', JSON.stringify(proys));
                    }
                }
                localStorage.setItem('hb_v3_mensajes_fallback', JSON.stringify(msgs));
                return msgs[mIdx];
            }
            throw err;
        }
    },

    async sha256(str) {
        const buf = new TextEncoder().encode(str);
        const hashBuffer = await crypto.subtle.digest('SHA-256', buf);
        const hashArray = Array.from(new Uint8Array(hashBuffer));
        return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
    },

    getFallbackCredsHashes() {
        const defaultHashes = {
            'data_ninja': '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',
            'java_dev99': '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',
            'admin_db': '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',
            'hardware_hacker': '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',
            'herson_admin': '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9'
        };
        const stored = JSON.parse(localStorage.getItem('hb_v4_creds_hashes') || '{}');
        return { ...defaultHashes, ...stored };
    },

    async login(username, password) {
        const cleanUser = (username || '').trim();
        const cleanPass = (password || '').trim();
        try {
            const res = await fetch(`${API_USUARIOS}/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username: cleanUser, password: cleanPass })
            });
            if (res.ok) {
                const user = await res.json();
                this.setCurrentUser(user);
                return user;
            }
            const errData = await res.json().catch(() => ({}));
            throw new Error(errData.error || 'Usuario o contraseña incorrectos.');
        } catch (err) {
            // Respaldo local con verificación SHA-256 si se abre el HTML directo o si la BD local aún no tenía los datos semilla
            const passHash = await this.sha256(cleanPass);
            const creds = this.getFallbackCredsHashes();
            const uname = cleanUser.toLowerCase();
            const usuarios = JSON.parse(localStorage.getItem('hb_v3_usuarios_fallback') || '[]');
            const encontrado = usuarios.find(u => u.username.toLowerCase() === uname);
            if (encontrado && creds[uname] === passHash) {
                this.setCurrentUser(encontrado);
                return encontrado;
            }
            throw new Error(err.message || 'Usuario o contraseña incorrectos.');
        }
    },

    // Cambio 4: Crear nueva cuenta (solo usuarios normales ESTUDIANTE, nunca superusuarios) con contraseña cifrada en SHA-256
    async registrar(username, email, password) {
        const cleanUser = (username || '').trim();
        const cleanEmail = (email || '').trim();
        const cleanPass = (password || '').trim();
        try {
            const res = await fetch(`${API_USUARIOS}/registro`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username: cleanUser, email: cleanEmail, password: cleanPass })
            });
            if (!res.ok) {
                const err = await res.json().catch(() => ({}));
                throw new Error(err.error || 'No se pudo registrar la cuenta');
            }
            const user = await res.json();
            this.setCurrentUser(user);
            return user;
        } catch (err) {
            if (err.message && (err.message.includes('Failed to fetch') || err.message.includes('NetworkError'))) {
                const usuarios = JSON.parse(localStorage.getItem('hb_v3_usuarios_fallback') || '[]');
                if (usuarios.some(u => u.username.toLowerCase() === cleanUser.toLowerCase())) {
                    throw new Error('El nombre de usuario ya está en uso.');
                }
                if (usuarios.some(u => u.email.toLowerCase() === cleanEmail.toLowerCase())) {
                    throw new Error('El correo ya está registrado.');
                }
                const nuevo = {
                    id: Date.now(),
                    username: cleanUser,
                    email: cleanEmail,
                    rol: 'ESTUDIANTE',
                    esSuperusuario: false,
                    fechaRegistro: new Date().toISOString()
                };
                usuarios.push(nuevo);
                localStorage.setItem('hb_v3_usuarios_fallback', JSON.stringify(usuarios));

                const passHash = await this.sha256(cleanPass);
                const creds = this.getFallbackCredsHashes();
                creds[nuevo.username.toLowerCase()] = passHash;
                localStorage.setItem('hb_v4_creds_hashes', JSON.stringify(creds));

                this.setCurrentUser(nuevo);
                return nuevo;
            }
            throw err;
        }
    }
};

injectThemeStyles();
document.documentElement.setAttribute('data-theme', HBClient.getTheme());
window.addEventListener('DOMContentLoaded', () => {
    HBClient.applyTranslations();
});
