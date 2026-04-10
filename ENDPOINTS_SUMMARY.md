# 📋 Résumé des Endpoints API

## Vue d'ensemble rapide

| N° | Méthode | Endpoint | Description | Auth | Status |
|:--:|:-------:|:--------:|:----------:|:----:|:------:|
| 1 | `POST` | `/api/register` | Créer un nouveau compte utilisateur | ❌ Non | 201 |
| 2 | `POST` | `/api/login` | Se connecter et obtenir un token JWT | ❌ Non | 200 |
| 3 | `POST` | `/api/students` | Ajouter un nouvel étudiant | ✅ Oui | 201 |
| 4 | `GET` | `/api/students` | Consulter la liste de tous les étudiants | ✅ Oui | 200 |
| 5 | `GET` | `/api/students/{id}` | Consulter les détails d'un étudiant | ✅ Oui | 200 |
| 6 | `PUT` | `/api/students/{id}` | Modifier un étudiant | ✅ Oui | 200 |
| 7 | `DELETE` | `/api/students/{id}` | Supprimer un étudiant | ✅ Oui | 204 |

---

## 🔐 Endpoints publics (sans authentification)

### 1️⃣ Enregistrement (`POST /api/register`)
```
POST http://localhost:8080/api/register
Content-Type: application/json

{
  "firstName": "Jean",
  "lastName": "Dupont",
  "login": "jean.dupont",
  "password": "SecurePassword123!"
}

→ 201 Created
```

### 2️⃣ Connexion (`POST /api/login`)
```
POST http://localhost:8080/api/login
Content-Type: application/json

{
  "login": "jean.dupont",
  "password": "SecurePassword123!"
}

→ 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer"
}
```

---

## 🔒 Endpoints protégés (authentification requise)

### 3️⃣ Ajouter un étudiant (`POST /api/students`)
```
POST http://localhost:8080/api/students
Authorization: Bearer <your_token>
Content-Type: application/json

{
  "firstName": "Marie",
  "lastName": "Martin",
  "email": "marie.martin@example.com"
}

→ 201 Created
{
  "id": 1,
  "firstName": "Marie",
  "lastName": "Martin",
  "email": "marie.martin@example.com",
  "created_at": "2026-04-09T10:30:00",
  "updated_at": "2026-04-09T10:30:00"
}
```

### 4️⃣ Consulter la liste des étudiants (`GET /api/students`)
```
GET http://localhost:8080/api/students
Authorization: Bearer <your_token>

→ 200 OK
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

### 5️⃣ Consulter les détails d'un étudiant (`GET /api/students/{id}`)
```
GET http://localhost:8080/api/students/1
Authorization: Bearer <your_token>

→ 200 OK
{
  "id": 1,
  "firstName": "Marie",
  "lastName": "Martin",
  "email": "marie.martin@example.com",
  "created_at": "2026-04-09T10:30:00",
  "updated_at": "2026-04-09T10:30:00"
}
```

### 6️⃣ Modifier un étudiant (`PUT /api/students/{id}`)
```
PUT http://localhost:8080/api/students/1
Authorization: Bearer <your_token>
Content-Type: application/json

{
  "firstName": "Marianne",
  "lastName": "Martins",
  "email": "marianne.martins@example.com"
}

→ 200 OK
{
  "id": 1,
  "firstName": "Marianne",
  "lastName": "Martins",
  "email": "marianne.martins@example.com",
  "created_at": "2026-04-09T10:30:00",
  "updated_at": "2026-04-09T10:50:00"
}
```

### 7️⃣ Supprimer un étudiant (`DELETE /api/students/{id}`)
```
DELETE http://localhost:8080/api/students/1
Authorization: Bearer <your_token>

→ 204 No Content
(Pas de corps de réponse)
```

---

## ✅ Codes de réponse

### Succès
| Code | Sens |
|------|------|
| **200** | OK - Requête réussie |
| **201** | Created - Ressource créée |
| **204** | No Content - Ressource supprimée |

### Erreurs
| Code | Sens | Cause |
|------|------|-------|
| **400** | Bad Request | Données invalides ou manquantes |
| **401** | Unauthorized | Non authentifié ou token invalide |
| **403** | Forbidden | Accès refusé |
| **404** | Not Found | Ressource introuvable |
| **500** | Internal Server Error | Erreur serveur |

---

## 🔑 Authentification

### Header requis pour les endpoints protégés
```
Authorization: Bearer <jwt_token>
```

### Où obtenir le token ?
1. Appelez `POST /api/login` avec vos identifiants
2. Récupérez le token dans la réponse
3. Incluez-le dans le header `Authorization` de toutes les requêtes protégées

### Format du token
Le token est un JWT (JSON Web Token) valide pour une durée limitée.

---

## 🛠 Outils de test

### cURL
```bash
# S'enregistrer
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Jean","lastName":"Dupont","login":"jean.dupont","password":"SecurePassword123!"}'

# Se connecter
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"login":"jean.dupont","password":"SecurePassword123!"}'

# Créer un étudiant
curl -X POST http://localhost:8080/api/students \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Marie","lastName":"Martin","email":"marie.martin@example.com"}'

# Récupérer tous les étudiants
curl -X GET http://localhost:8080/api/students \
  -H "Authorization: Bearer <token>"

# Récupérer un étudiant
curl -X GET http://localhost:8080/api/students/1 \
  -H "Authorization: Bearer <token>"

# Modifier un étudiant
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Marianne","lastName":"Martins","email":"marianne.martins@example.com"}'

# Supprimer un étudiant
curl -X DELETE http://localhost:8080/api/students/1 \
  -H "Authorization: Bearer <token>"
```

### Postman
Importez le fichier `postman_collection.json` dans Postman pour avoir tous les endpoints préconfigurés.

### Swagger/OpenAPI
Consultez les fichiers :
- `openapi.yaml` (format YAML)
- `openapi.json` (format JSON)

Importez dans [Swagger UI](https://editor.swagger.io/) pour une documentation interactive.

---

## 📊 Structure des données

### Étudiant (StudentDTO)
```json
{
  "id": 1,                              // Identifiant (long)
  "firstName": "Marie",                 // Prénom
  "lastName": "Martin",                 // Nom
  "email": "marie.martin@example.com",  // Email
  "created_at": "2026-04-09T10:30:00",  // Création
  "updated_at": "2026-04-09T10:30:00"   // Modification
}
```

### Utilisateur (pour enregistrement)
```json
{
  "firstName": "Jean",           // Prénom
  "lastName": "Dupont",          // Nom
  "login": "jean.dupont",        // Identifiant connexion
  "password": "Pass123!"         // Mot de passe (min 8 car.)
}
```

### Réponse Authentification
```json
{
  "token": "eyJhbGciOiJIUzI1NiI...",  // JWT Token
  "type": "Bearer"                      // Type
}
```

---

## ⚠️ Gestion des erreurs

Tous les erreurs sont retournées au format standardisé :

```json
{
  "timestamp": "2026-04-09T10:30:00",
  "message": "Descriptif de l'erreur",
  "path": "uri=/api/students/999"
}
```

### Exemples d'erreurs courantes

**Étudiant non trouvé**
```json
{
  "timestamp": "2026-04-09T10:30:00",
  "message": "Student not found",
  "path": "uri=/api/students/999"
}
```

**Non authentifié**
```json
{
  "timestamp": "2026-04-09T10:30:00",
  "message": "Unauthorized",
  "path": "uri=/api/students"
}
```

**Données invalides**
```json
{
  "timestamp": "2026-04-09T10:30:00",
  "message": "Validation error: firstName should not be blank",
  "path": "uri=/api/students"
}
```

---

## 🚀 Démarrage rapide

1. **Enregistrement**
   ```bash
   POST /api/register
   ```
   Créer un compte avec vos identifiants

2. **Connexion**
   ```bash
   POST /api/login
   ```
   Obtenir un token JWT

3. **Créer un étudiant**
   ```bash
   POST /api/students
   Authorization: Bearer <token>
   ```
   Ajouter un nouvel étudiant

4. **Récupérer les étudiants**
   ```bash
   GET /api/students
   Authorization: Bearer <token>
   ```
   Consulter la liste

5. **Modifier/Supprimer**
   ```bash
   PUT /api/students/{id}
   DELETE /api/students/{id}
   Authorization: Bearer <token>
   ```
   Gérer les étudiants

---

**Version API**: 1.0.0  
**Date de création**: 2026-04-09  
**Framework**: Spring Boot 3.5.5  
**Base de données**: MySQL 8.x

