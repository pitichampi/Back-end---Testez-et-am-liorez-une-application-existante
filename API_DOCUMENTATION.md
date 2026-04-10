T# 📚 API de Gestion des Étudiants - Documentation

## 🎯 Vue d'ensemble

API REST complète pour la gestion des étudiants d'une bibliothèque, construite avec **Spring Boot 3.5.5** et sécurisée via **JWT Bearer Token**.

## 🔐 Authentification

Tous les endpoints **SAUF** `/api/register` et `/api/login` nécessitent un token JWT Bearer.

### Format du header d'authentification
```
Authorization: Bearer <your_jwt_token>
```

### Flux d'authentification
1. **S'enregistrer** : `POST /api/register` (sans authentification)
2. **Se connecter** : `POST /api/login` (sans authentification) → Récupère le token
3. **Utiliser l'API** : Inclure le token dans le header `Authorization` de toutes les autres requêtes

---

## 📋 Endpoints

### 🔐 Authentification & Enregistrement

#### 1. Enregistrement d'un nouvel utilisateur
```
POST /api/register
```
**Authentification requise** : Non

**Body (JSON)**:
```json
{
  "firstName": "Jean",
  "lastName": "Dupont",
  "login": "jean.dupont",
  "password": "SecurePassword123!"
}
```

**Réponse** (201 Created):
```
Créé avec succès (pas de contenu retourné)
```

**Erreurs** :
- `400 Bad Request` : Données invalides ou manquantes
- `500 Internal Server Error` : Erreur serveur

---

#### 2. Connexion et obtention du token
```
POST /api/login
```
**Authentification requise** : Non

**Body (JSON)**:
```json
{
  "login": "jean.dupont",
  "password": "SecurePassword123!"
}
```

**Réponse** (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer"
}
```

**Erreurs** :
- `400 Bad Request` : Identifiants invalides
- `401 Unauthorized` : Authentification échouée

---

### 👥 Gestion des Étudiants (CRUD)

#### 3. Créer un nouvel étudiant
```
POST /api/students
```
**Authentification requise** : ✅ Oui (Bearer Token)

**Body (JSON)**:
```json
{
  "firstName": "Marie",
  "lastName": "Martin",
  "email": "marie.martin@example.com"
}
```

**Réponse** (201 Created):
```json
{
  "id": 1,
  "firstName": "Marie",
  "lastName": "Martin",
  "email": "marie.martin@example.com",
  "created_at": "2026-04-09T10:30:00",
  "updated_at": "2026-04-09T10:30:00"
}
```

**Erreurs** :
- `400 Bad Request` : Données invalides
- `401 Unauthorized` : Non authentifié
- `500 Internal Server Error` : Erreur serveur

---

#### 4. Récupérer la liste de tous les étudiants
```
GET /api/students
```
**Authentification requise** : ✅ Oui (Bearer Token)

**Réponse** (200 OK):
```json
[
  {
    "id": 1,
    "firstName": "Marie",
    "lastName": "Martin",
    "email": "marie.martin@example.com",
    "created_at": "2026-04-09T10:30:00",
    "updated_at": "2026-04-09T10:30:00"
  },
  {
    "id": 2,
    "firstName": "Jean",
    "lastName": "Dupont",
    "email": "jean.dupont@example.com",
    "created_at": "2026-04-09T10:35:00",
    "updated_at": "2026-04-09T10:35:00"
  }
]
```

**Erreurs** :
- `401 Unauthorized` : Non authentifié
- `500 Internal Server Error` : Erreur serveur

---

#### 5. Récupérer un étudiant spécifique
```
GET /api/students/{id}
```
**Authentification requise** : ✅ Oui (Bearer Token)

**Paramètres** :
- `id` (path, obligatoire) : Identifiant unique de l'étudiant (type: integer)

**Exemple de requête** :
```
GET /api/students/1
```

**Réponse** (200 OK):
```json
{
  "id": 1,
  "firstName": "Marie",
  "lastName": "Martin",
  "email": "marie.martin@example.com",
  "created_at": "2026-04-09T10:30:00",
  "updated_at": "2026-04-09T10:30:00"
}
```

**Erreurs** :
- `401 Unauthorized` : Non authentifié
- `404 Not Found` : Étudiant non trouvé
- `500 Internal Server Error` : Erreur serveur

---

#### 6. Modifier un étudiant
```
PUT /api/students/{id}
```
**Authentification requise** : ✅ Oui (Bearer Token)

**Paramètres** :
- `id` (path, obligatoire) : Identifiant unique de l'étudiant (type: integer)

**Body (JSON)**:
```json
{
  "firstName": "Marianne",
  "lastName": "Martins",
  "email": "marianne.martins@example.com"
}
```

**Exemple de requête** :
```
PUT /api/students/1
```

**Réponse** (200 OK):
```json
{
  "id": 1,
  "firstName": "Marianne",
  "lastName": "Martins",
  "email": "marianne.martins@example.com",
  "created_at": "2026-04-09T10:30:00",
  "updated_at": "2026-04-09T10:50:00"
}
```

**Erreurs** :
- `400 Bad Request` : Données invalides
- `401 Unauthorized` : Non authentifié
- `404 Not Found` : Étudiant non trouvé
- `500 Internal Server Error` : Erreur serveur

---

#### 7. Supprimer un étudiant
```
DELETE /api/students/{id}
```
**Authentification requise** : ✅ Oui (Bearer Token)

**Paramètres** :
- `id` (path, obligatoire) : Identifiant unique de l'étudiant (type: integer)

**Exemple de requête** :
```
DELETE /api/students/1
```

**Réponse** (204 No Content):
```
Aucun contenu retourné
```

**Erreurs** :
- `401 Unauthorized` : Non authentifié
- `404 Not Found` : Étudiant non trouvé
- `500 Internal Server Error` : Erreur serveur

---

## 📊 Formats des données

### StudentDTO (Response)
```json
{
  "id": 1,                    // Identifiant unique (long)
  "firstName": "Marie",       // Prénom (string, 1-100 caractères)
  "lastName": "Martin",       // Nom (string, 1-100 caractères)
  "email": "marie@ex.com",   // Email (string, format email valide)
  "created_at": "2026-04-09T10:30:00",  // Date création (ISO 8601)
  "updated_at": "2026-04-09T10:30:00"   // Date modification (ISO 8601)
}
```

### StudentCreateUpdateDTO (Request)
```json
{
  "firstName": "Marie",       // Requis, non vide
  "lastName": "Martin",       // Requis, non vide
  "email": "marie@ex.com"    // Requis, format email valide
}
```

### RegisterDTO (Request)
```json
{
  "firstName": "Jean",        // Requis, non vide
  "lastName": "Dupont",       // Requis, non vide
  "login": "jean.dupont",     // Requis, non vide (username unique)
  "password": "Pass123!"      // Requis, min 8 caractères
}
```

### LoginRequestDTO (Request)
```json
{
  "login": "jean.dupont",     // Requis
  "password": "Pass123!"      // Requis
}
```

### AuthResponse (Response)
```json
{
  "token": "eyJhbGciOiJIUzI1NiI...",  // JWT Bearer Token
  "type": "Bearer"                      // Type (toujours "Bearer")
}
```

### ErrorDetails (Response)
```json
{
  "timestamp": "2026-04-09T10:30:00",  // Date/heure de l'erreur (ISO 8601)
  "message": "Student not found",       // Message d'erreur explicite
  "path": "uri=/api/students/999"       // Chemin de la requête
}
```

---

## 🚀 Codes de statut HTTP

| Code | Signification | Utilisation |
|------|---------------|-------------|
| **200** | OK | Requête réussie (GET, PUT) |
| **201** | Created | Ressource créée (POST) |
| **204** | No Content | Suppression réussie (DELETE) |
| **400** | Bad Request | Données invalides ou manquantes |
| **401** | Unauthorized | Authentification requise ou échouée |
| **403** | Forbidden | Accès refusé |
| **404** | Not Found | Ressource non trouvée |
| **500** | Internal Server Error | Erreur serveur interne |

---

## 📝 Exemples d'utilisation

### Avec cURL

**1. S'enregistrer**
```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jean",
    "lastName": "Dupont",
    "login": "jean.dupont",
    "password": "SecurePassword123!"
  }'
```

**2. Se connecter**
```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "login": "jean.dupont",
    "password": "SecurePassword123!"
  }'
```

**3. Créer un étudiant**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_token>" \
  -d '{
    "firstName": "Marie",
    "lastName": "Martin",
    "email": "marie.martin@example.com"
  }'
```

**4. Récupérer tous les étudiants**
```bash
curl -X GET http://localhost:8080/api/students \
  -H "Authorization: Bearer <your_token>"
```

**5. Récupérer un étudiant spécifique**
```bash
curl -X GET http://localhost:8080/api/students/1 \
  -H "Authorization: Bearer <your_token>"
```

**6. Modifier un étudiant**
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_token>" \
  -d '{
    "firstName": "Marianne",
    "lastName": "Martins",
    "email": "marianne.martins@example.com"
  }'
```

**7. Supprimer un étudiant**
```bash
curl -X DELETE http://localhost:8080/api/students/1 \
  -H "Authorization: Bearer <your_token>"
```

---

### Avec JavaScript (Fetch API)

```javascript
// S'enregistrer
async function register() {
  const response = await fetch('http://localhost:8080/api/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      firstName: 'Jean',
      lastName: 'Dupont',
      login: 'jean.dupont',
      password: 'SecurePassword123!'
    })
  });
  console.log('Status:', response.status);
}

// Se connecter
async function login() {
  const response = await fetch('http://localhost:8080/api/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      login: 'jean.dupont',
      password: 'SecurePassword123!'
    })
  });
  const data = await response.json();
  return data.token; // Récupérer le token
}

// Créer un étudiant (avec authentification)
async function createStudent(token) {
  const response = await fetch('http://localhost:8080/api/students', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({
      firstName: 'Marie',
      lastName: 'Martin',
      email: 'marie.martin@example.com'
    })
  });
  return await response.json();
}

// Récupérer tous les étudiants
async function getAllStudents(token) {
  const response = await fetch('http://localhost:8080/api/students', {
    method: 'GET',
    headers: { 'Authorization': `Bearer ${token}` }
  });
  return await response.json();
}

// Récupérer un étudiant spécifique
async function getStudentById(id, token) {
  const response = await fetch(`http://localhost:8080/api/students/${id}`, {
    method: 'GET',
    headers: { 'Authorization': `Bearer ${token}` }
  });
  return await response.json();
}

// Modifier un étudiant
async function updateStudent(id, token) {
  const response = await fetch(`http://localhost:8080/api/students/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({
      firstName: 'Marianne',
      lastName: 'Martins',
      email: 'marianne.martins@example.com'
    })
  });
  return await response.json();
}

// Supprimer un étudiant
async function deleteStudent(id, token) {
  const response = await fetch(`http://localhost:8080/api/students/${id}`, {
    method: 'DELETE',
    headers: { 'Authorization': `Bearer ${token}` }
  });
  return response.status === 204; // Retourne true si succès
}
```

---

## 📖 Documentation OpenAPI/Swagger

Les fichiers de documentation OpenAPI sont disponibles :
- **YAML**: `openapi.yaml`
- **JSON**: `openapi.json`

### Importer dans Swagger UI

1. Allez sur [Swagger UI](https://editor.swagger.io/)
2. Cliquez sur "File" → "Import URL"
3. Collez l'URL de votre API OpenAPI (ou importez le fichier `openapi.yaml`/`openapi.json`)

### Utiliser avec Postman

1. Ouvrez Postman
2. Cliquez sur "Import"
3. Sélectionnez "Link" ou "File"
4. Collez l'URL ou sélectionnez `openapi.json`/`openapi.yaml`
5. Les endpoints seront automatiquement importés

---

## 🛠 Architecture

### Modèle en couches

```
Controller (HTTP)
    ↓
Service (Logique métier)
    ↓
Repository (Accès aux données)
    ↓
Database (MySQL)
```

### Couches

- **Controller** (`StudentController`, `UserController`) : Gère les requêtes HTTP et les réponses
- **Service** (`StudentService`, `UserService`) : Contient la logique métier
- **Repository** (`StudentRepository`, `UserRepository`) : Accès à la base de données
- **DTO** (Data Transfer Objects) : Transfert de données entre couches
- **Mapper** : Conversion entre entités et DTO
- **Handler** : Gestion centralisée des erreurs

---

## 🔒 Sécurité

- **JWT Bearer Token** : Authentification basée sur les tokens JWT
- **Spring Security** : Configuration de sécurité Spring
- **Validation** : Validation des données d'entrée avec Jakarta Validation
- **CORS** : À configurer selon les besoins
- **Hash de mot de passe** : Les mots de passe sont hashés avant stockage

---

## 📊 Base de données

**Système** : MySQL 8.x

**Entités** :

### Student
```sql
CREATE TABLE student (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### User
```sql
CREATE TABLE user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  login VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);
```

---

## 📄 Licence

MIT

---

## 📞 Support

Pour toute question ou problème, veuillez consulter les sections appropriées de cette documentation ou contacter le support.

