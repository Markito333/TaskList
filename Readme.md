# 🚀 TaskList - Gestión Inteligente de Tareas

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
  [TaskList Frontend](https://markito333.github.io/TaskListFrontend/)

- **Backend/Aplicación Completa (Railway):**  
  [TaskList en Heroku](https://tasklist.app.railway.com)  
 
-----

Estructura del Proyecto
La aplicación TaskList está construida sobre Spring Boot y combina el frontend y el backend en un único paquete ejecutable. A continuación, se muestra cómo se organiza el proyecto:

📄 pom.xml: Archivo de configuración de Maven que define todas las dependencias, plugins y ajustes necesarios para compilar y empaquetar la aplicación.

📁 src/main/java:

📦 demo: Paquete base que contiene el código del backend:

🚀 RunPW.java: Clase principal anotada con @SpringBootApplication que arranca la aplicación.

📁 src/main/resources:

🖥 static: Carpeta con los archivos del frontend (HTML, CSS, y JavaScript).

⚙️ application.properties: Archivo de configuración donde se definen parámetros importantes como la conexión a la base de datos.

📁 src/test/java: Ubicación de todas las pruebas unitarias y de integración para asegurar la calidad del código.



