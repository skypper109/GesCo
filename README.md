# DEJ

**DEJ** est une application Java en ligne de commande conçue pour automatiser la rotation du petit-déjeuner en entreprise. Elle permet de planifier équitablement les tours des agents tout en tenant compte des jours fériés et des indisponibilités.

## 🎯 Objectifs

- Assurer une rotation fluide et équitable.
- Gérer les absences d’agents et les jours fériés.
- Automatiser les rappels et l’historique des tours.

## 🔧 Fonctionnalités

- Ajout / suppression d’agents
- Définition d’un jour de rotation (par défaut : vendredi)
- Saut des jours fériés ou week-ends automatiquement
- Remplacement en cas d’indisponibilité
- Suivi et affichage de l’historique des rotations
- Interface différenciée : Administrateur / Agent

## 📁 Structure du projet

- `Main.java` : point d'entrée du programme
- `GestionnaireRotation.java` : logique de planification
- `Agent.java` : modèle représentant un agent
- `Historique.java` : journal des rotations
- `GestionAgent.java` : gestion des agents (ajout, liste, suppression)
- `GestionRotationJour.java` : configuration des jours et relancement de rotation

## ▶️ Lancer l'application

1. Compiler les fichiers Java :
```bash
javac Main.java
```

2. Lancer le programme :
```bash
java Main
```

## 👤 Accès

- **Admin**
  - Username : `Admin`
  - Password : `12345678`

- **Agent**
  - Email : [votre email]
  - Password : `1234`

## 🔮 Améliorations futures

- Ajout d’une interface graphique
- Sauvegarde des données dans un fichier ou une base de données
- Notifications par email ou SMS

---

Développé avec ❤️ par les éléments du Groupe 3 ODK P5 pour organiser la vie d'équipe.
