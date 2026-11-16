# TP 11 : Composants Personnalisés sur Android (Custom Views)

Ce projet Android implémente le **TP 11** portant sur la création et la manipulation de **Composants Personnalisés (Custom Views)** en Kotlin.

## 📱 Fonctionnalités et Composants Développés

1. **`CircularScoreView`** (Jauge Circulaire de Score) :
   - Un composant graphique circulaire interactif héritant de `View`.
   - Dessin personnalisé via `Canvas` et `Paint` (`onDraw`, `onMeasure`).
   - Support des attributs personnalisés XML (`attrs.xml`) : score, maxScore, ringColor, progressColor, textColor, textSize, strokeWidth.
   - Sauvegarde et restauration automatique de l'état de la vue (`onSaveInstanceState` / `onRestoreInstanceState`).

2. **`RatingBarView`** (Barre de Notation en Étoiles) :
   - Un composant interactif permettant de noter de 1 à 5 étoiles par simple toucher (`onTouchEvent`).
   - Rendu vectoriel dynamique par chemins (`Path`).
   - Callback de notification lors du changement de note (`onRatingChangedListener`).

3. **`MainActivity`** (Tableau de bord interactif) :
   - Intégration des deux composants personnalisés dans un layout `LinearLayout` responsive.
   - Bouton interactif pour incrémenter dynamiquement le score de la jauge circulaire.
   - Mise à jour en temps réel du texte de notation lors de l'interaction avec la barre d'étoiles.

---

## 🏗️ Architecture du Projet

```text
com.example.tp_11___composant/
├── MainActivity.kt        # Activité principale interactive
├── CircularScoreView.kt   # Composant personnalisé de jauge circulaire
└── RatingBarView.kt       # Composant personnalisé de notation par étoiles

res/
├── layout/
│   └── activity_main.xml  # Layout principal combinant les Custom Views
└── values/
    ├── attrs.xml          # Déclaration des attributs personnalisés XML
    └── strings.xml        # Ressources textuelles internationalisées
```

---

## 🚀 Guide d'Utilisation

1. Ouvrez le projet dans **Android Studio**.
2. Synchronisez Gradle si nécessaire.
3. Lancez l'application sur un émulateur ou un appareil physique (API 29+).
4. Testez l'incrémentation du score circulaire et la notation interactive par étoiles.

---

## 🛠️ Vérification et Tests

- **Compilation** : Validée avec succès (`app:assembleDebug`).
- **Edge-to-Edge** : Intégration native avec gestion des insets système (`enableEdgeToEdge`).
