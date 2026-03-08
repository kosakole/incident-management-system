**Incident Management System (IMS) - Microservices**
This project is a distributed system for reporting and managing public incidents. It is built using Spring Boot microservices architecture, containerized with Docker, and automated through GitHub Actions CI/CD.

🚀**Key Features**

📍 Incident Reporting & Maps
Location Picker: Users can select incident locations manually on a map or use automatic geolocation.
Rich Reports: Support for various incident types and subtypes, including text descriptions and image uploads.
Map Visualization: Anonymous users can view a public map with active incidents.
Advanced Filtering: Filter incidents by category, location, and time (last 24h, 7 days, 31 days, or all time).

🛡️**Moderation & Security**

Approval Workflow: All reports are stored in a pending state and must be reviewed (approved/rejected) by a Moderator before appearing on the public map.
RBAC (Role-Based Access Control): Specific roles for Admin, Moderator, and User, with access rights managed by user-service.
Authentication: Exclusively implemented via Google OAuth2 (restricted to *.etf.unibl.org domain).
📊 Analytics & Intelligent Alerts
Analytics Service: Provides data visualization and analysis of incidents based on time, location, and type.
Alert Service: Detects clusters of reports (multiple incidents in a small radius within a short timeframe).
Dynamic Thresholds: Moderators can configure alert sensitivity (radius and time window) via their profile.

🏗️ **System Architecture**

The system follows a modern microservices pattern:
API Gateway: The single entry point for the frontend. Prevents direct communication with individual microservices for enhanced security.
Service Discovery (Eureka): Dynamically tracks and manages service instances.
Config Server: Centralized configuration management for all microservices.
List of Services:
auth-service - Google OAuth2 Authentication
user-service - User roles & Access management
gateway-service - Routing & Security
config-server - Centralized config
discovery-server - Service registration
incident-service - Core incident logic & storage
moderation-service - Approval workflows
analytics-service - Reporting & Data viz
alert-service - Proximity detection & notifications

🛠️ **Tech Stack**

Backend: Java 17, Spring Boot, Spring Cloud, Spring Security
Database: MySQL, Redis
DevOps: Docker, Docker Compose, GitHub Actions
Auth: JWT, Google OAuth2

📦 **Deployment & CI/CD**

Dockerization
Every microservice contains its own Dockerfile. The entire ecosystem can be launched using:
bash
docker-compose up -d

**GitHub Actions Workflow**

Automated CI/CD pipeline:
Build: Compiles each service using Maven.
Docker Push: Builds and pushes images to DockerHub (lowercase naming convention).

🔧 **Setup**

Clone the repository.
Provide necessary environment variables in .env (Google Client ID/Secret, DB credentials).
Run docker-compose up.

📝 **License**

Developed as part of the coursework for the Faculty of Electrical Engineering (ETF UNIBL).


