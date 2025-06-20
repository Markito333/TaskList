# TaskList - Aplicación Full-Stack de Gestión de Tareas

TaskList es una solución integral para administrar tus tareas diarias. La aplicación fusiona un frontend interactivo y dinámico con un backend robusto, ofreciendo una experiencia de usuario moderna y escalable.

---

## Tabla de Contenidos

- [Descripción](#descripción)
- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Demo en Vivo](#demo-en-vivo)
- [Despliegue](#despliegue)

---

## Descripción

TaskList es una aplicación full-stack para la gestión de tareas que integra un frontend moderno, intuitivo y responsive, con un robusto backend API desarrollado en Java, utilizando Spring Boot y PostgreSQL. La interfaz está orientada a mejorar la productividad a través de una experiencia interactiva, con validaciones y confirmaciones personalizadas.

---

## Características

- **Gestión Completa de Tareas:** Permite crear, editar, eliminar y marcar tareas como completadas.
- **Interfaz Intuitiva y Responsive:** Diseño adaptable a móviles, tabletas y escritorio.
- **Interacciones Dinámicas:** Utiliza animaciones y ventanas modales para confirmaciones y notificaciones.
- **Integración Total:** Comunicación fluida entre frontend y backend mediante APIs RESTful.
- **Pruebas y Calidad:** Pruebas unitarias, de integración y E2E asegurando la estabilidad de la aplicación.

---

## Tecnologías Utilizadas

### Frontend
- **HTML5:** Estructura semántica y accesible.
- **CSS3:** Estilos modernos, responsive y atractivos.
- **JavaScript (ES6):** Interactividad y manipulación dinámica del DOM.
- **Feather Icons:** Íconos vectoriales que realzan el diseño de la interfaz.

### Backend
- **Java:** Lenguaje robusto orientado a objetos.
- **Spring Boot:** Framework para la creación rápida de aplicaciones empresariales.
- **PostgreSQL:** Base de datos relacional para un manejo seguro y escalable de datos.
- **Maven:** Gestión de dependencias y proceso de construcción.
- **JUnit, Mockito, RestAssured y Testcontainers:** Herramientas para pruebas unitarias, de integración y E2E.

### Herramientas Adicionales
- **Git & GitHub:** Control de versiones y colaboración en el desarrollo.
- **GitHub Pages:** Despliegue del frontend.
- **Heroku:** Plataforma para desplegar el backend y la aplicación completa en producción.
- **Heroku CLI:** Para gestionar el despliegue y las configuraciones en la nube.

---

## Demo en Vivo

- **Frontend (GitHub Pages):**  
  [TaskList Frontend](https://markito333.github.io/TaskListFrontend)

- **Backend/Aplicación Completa (Heroku):**  
  [TaskList en Heroku](https://tu-aplicacion.herokuapp.com)  
  *(Actualiza la URL según corresponda)*

## Estructura del proyecto

🗂
La aplicación está organizada bajo una arquitectura típica de Spring Boot, integrando tanto el frontend como el backend en un solo paquete ejecutable:

TaskList/
├── pom.xml                        # Archivo de configuración de Maven
├── Procfile                      # Instrucciones para Heroku (despliegue)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── demo/             # Paquete base con controladores, servicios y entidades
│   │   │       └── Application.java  # Clase principal con @SpringBootApplication
│   │   └── resources/
│   │       ├── static/          # Frontend: HTML, CSS, JS
│   │       └── application.properties # Configuración de la app (incluye datasource)
│   └── test/
│       └── java/                # Pruebas unitarias y de integración
├── target/                       # Archivos generados por Maven (.jar incluido)
└── .vscode/                      # Configuraciones del editor (opcional)



