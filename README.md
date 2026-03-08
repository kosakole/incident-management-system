# Incident Management System (IMS) - Microservices

Academic project developed at the **Faculty of Electrical Engineering – University of Banja Luka (ETF UNIBL)**.

This project was developed as part of university coursework and demonstrates the design and implementation of a distributed **microservices-based system** for reporting and managing public incidents.

---

**Incident Management System (IMS) - Microservices**

This project is a distributed system for reporting and managing public incidents. It is built using Spring Boot microservices architecture, containerized with Docker, and automated through GitHub Actions CI.

🚀 **Key Features**

📍 **Incident Reporting & Maps**

Location Picker: Users can select incident locations manually on a map or use automatic geolocation.
Rich Reports: Support for various incident types, including text descriptions and image uploads.
Map Visualization: Anonymous users can view a public map with active incidents.
Advanced Filtering: Filter incidents by category, location, and time (last 24h, 7 days, 31 days, or all time).

---

🛡️ **Moderation & Security**

Approval Workflow: All reports are stored in a pending state and must be reviewed (approved/rejected) by a Moderator before appearing on the public map.

RBAC (Role-Based Access Control): Specific roles for **Admin, Moderator, and User**, with access rights managed by **user-service**.

Authentication: Exclusively implemented via **Google OAuth2** (restricted to *.etf.unibl.org domain).

---

📊 **Analytics & Intelligent Alerts**

Analytics Service: Provides data visualization and analysis of incidents based on time, location, and type.

Alert Service: Detects clusters of reports (multiple incidents in a small radius within a short timeframe).

Dynamic Thresholds: Moderators can configure alert sensitivity (radius and time window) via their profile.

---

🏗️ **System Architecture**

The system follows a modern microservices pattern:

**API Gateway**
The single entry point for the frontend. Prevents direct communication with individual microservices for enhanced security.

**Service Discovery (Eureka)**
Dynamically tracks and manages service instances.

**Config Server**
Centralized configuration management for all microservices.

**List of Services**

* auth-service – Google OAuth2 Authentication
* user-service – User roles & access management
* gateway-service – Routing & security
* config-server – Centralized configuration
* discovery-server – Service registration
* incident-service – Core incident logic & storage
* moderation-service – Approval workflows
* analytics-service – Reporting & data visualization
* alert-service – Proximity detection & notifications

---

🛠️ **Tech Stack**

Backend:

* Java 17
* Spring Boot
* Spring Cloud
* Spring Security

Database:

* MySQL
* Redis

DevOps:

* Docker
* Docker Compose
* GitHub Actions

Authentication:

* JWT
* Google OAuth2

---

📦 **Deployment & CI**

**Dockerization**

Every microservice contains its own Dockerfile. The entire ecosystem can be launched using:

```bash
docker-compose up -d
```

**GitHub Actions Workflow**

Automated CI pipeline:

Build: Compiles each service using Maven.
Docker Push: Builds and pushes images to DockerHub.

---

🔧 **Setup**

1. Clone the repository.
2. Provide necessary environment variables in `.env` (Google Client ID/Secret, DB credentials).
3. Run `docker-compose up`.

---

📝 **License**

Developed as part of coursework at the **Faculty of Electrical Engineering – University of Banja Luka (ETF UNIBL)**.

🔗 **Additional Repositories**

Frontend GitHub link: https://github.com/kosakole/incident-management-system-frontend

Config GitHub link: https://github.com/kosakole/config-repo
