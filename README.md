<div align="center">

<img src="https://img.shields.io/badge/Minecraft-1.21.x-brightgreen?style=for-the-badge&logo=minecraft" alt="Minecraft 1.21.x"/>
<img src="https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk" alt="Java 17+"/>
<img src="https://img.shields.io/badge/Spigot-1.21.1-yellow?style=for-the-badge" alt="Spigot"/>
<img src="https://img.shields.io/badge/License-MIT-lightgrey?style=for-the-badge" alt="License"/>

# 🛡️ StaffMode Plugin

**Plugin Bukkit/Spigot per la gestione rapida della modalità staff**  
Sviluppato per **Gravity** da [ckanto](https://github.com/ckanto)

[📦 Download](#-installazione) · [📖 Wiki](#-comandi) · [🐛 Bug Report](https://github.com/ToxitAnto/StaffMode-Plugin/issues)

</div>

---

## 📋 Indice

- [Panoramica](#-panoramica)
- [Funzionalità](#-funzionalità)
- [Requisiti](#-requisiti)
- [Installazione](#-installazione)
- [Configurazione](#%EF%B8%8F-configurazione)
- [Comandi](#-comandi)
- [Permessi](#-permessi)
- [Strumenti Hotbar](#-strumenti-hotbar)
- [Struttura Progetto](#-struttura-progetto)
- [Build dal Sorgente](#-build-dal-sorgente)

---

## 🌟 Panoramica

StaffMode è un plugin leggero e modulare per Spigot/Bukkit che permette ai membri dello staff di attivare una modalità dedicata con strumenti rapidi direttamente nell'hotbar. All'attivazione, l'hotbar originale del giocatore viene salvata e sostituita con item configurabili per gestire vanish, volo, invsee, teleport e lista giocatori. Disattivando la modalità, l'inventario viene ripristinato automaticamente.

---

## ✨ Funzionalità

### 🎒 Hotbar Staff
- Sostituzione istantanea dell'hotbar con strumenti dedicati allo staff
- Salvataggio e ripristino automatico dell'hotbar originale
- Gli item non possono essere droppati né spostati durante la staff mode
- Ripristino automatico alla disconnessione del giocatore

### 🔧 Strumenti
- **Lista giocatori** — mostra tutti i giocatori online in chat
- **Vanish** — toggle attiva/disattiva tramite comando del server
- **Volo** — toggle attiva/disattiva tramite comando del server
- **Teleport casuale** — teletrasporto istantaneo su un giocatore random
- **InvSee** — apre l'inventario del giocatore guardato

### ⚙️ Configurabilità
- Slot, materiale e nome di ogni item completamente personalizzabili
- Comandi del server per vanish, fly e invsee configurabili da `config.yml`
- Tutti i messaggi personalizzabili con supporto codici colore

---

## 📦 Requisiti

| Dipendenza | Versione | Tipo |
|---|---|---|
| Java | 17+ | Obbligatoria |
| Spigot / Paper | 1.21.1 | Obbligatoria |
| Plugin Vanish | qualsiasi con `/vanish` | Obbligatoria |
| Plugin Fly | qualsiasi con `/fly` | Obbligatoria |
| Plugin InvSee | qualsiasi con `/invsee` | Obbligatoria |

---

## 🚀 Installazione

1. **Scarica** il file `StaffMode.jar` dalla sezione [Releases](https://github.com/ToxitAnto/StaffMode-Plugin/releases).
2. **Copia** il JAR nella cartella `plugins/` del tuo server.
3. **Avvia** il server per generare i file di configurazione.
4. **Modifica** `plugins/StaffMode/config.yml` con i comandi del tuo server.
5. **Riavvia** il server per applicare la configurazione.

> **Nota:** I comandi per vanish, fly e invsee devono corrispondere a quelli esposti dai plugin già installati sul server.

---

## ⚙️ Configurazione

Il file `config.yml` viene generato automaticamente in `plugins/StaffMode/`. Di seguito le sezioni principali:

```yaml
permission: staffmode.use
item-tag: "&8StaffMode"
invsee-range: 6

commands:
  vanish:
    primary: "vanish"
  flight:
    primary: "fly"
  invsee:
    primary: "invsee"

items:
  player-list:
    slot: 3
    material: COMPASS
    name: "&b&lCompass &7- Player List"
  vanish:
    slot: 4
    material: EMERALD
    name: "&a&lSmeraldo &7- Vanish"
  flight:
    slot: 5
    material: FEATHER
    name: "&e&lPiuma &7- Flight"
  random-teleport:
    slot: 6
    material: BLAZE_ROD
    name: "&9&lBlaze Rod &7- Random TP"
  invsee:
    slot: 7
    material: CHEST
    name: "&6&lCassa &7- InvSee"

messages:
  staff-enabled: "&a&lModalita Staff attivata! &7Usa gli oggetti nell'hotbar."
  staff-disabled: "&aModalita Staff disattivata."
  vanish-enabled: "&aSei ora in vanish."
  vanish-disabled: "&cVanish disattivato."
  flight-enabled: "&aVolo attivato."
  flight-disabled: "&cVolo disattivato."
```

---

## 📜 Comandi

| Comando | Descrizione | Permesso |
|---|---|---|
| `/staff` | Attiva la modalità staff | `staffmode.use` |
| `/staff off` | Disattiva la modalità staff | `staffmode.use` |
| `/staff leave` | Disattiva la modalità staff | `staffmode.use` |
| `/leavestaff` | Disattiva la modalità staff | `staffmode.use` |

---

## 🔒 Permessi

| Permesso | Descrizione | Default |
|---|---|---|
| `staffmode.use` | Permette di usare la modalità staff | `op` |

---

## 🧰 Strumenti Hotbar

All'attivazione della staff mode l'hotbar originale viene salvata e sostituita con i seguenti item:

| Slot | Item | Azione | Comportamento |
|---|---|---|---|
| 3 | 🧭 Compass | Click sinistro/destro | Mostra lista giocatori online |
| 4 | 💚 Smeraldo | Click sinistro/destro | Toggle vanish (attiva/disattiva) |
| 5 | 🪶 Piuma | Click sinistro/destro | Toggle volo (attiva/disattiva) |
| 6 | 🔥 Blaze Rod | Click sinistro/destro | Teletrasporto su giocatore casuale |
| 7 | 📦 Cassa | Click destro | InvSee sul giocatore guardato (range: 6 blocchi) |

> Gli item non possono essere droppati né spostati nell'inventario durante la staff mode.  
> L'hotbar originale viene ripristinata automaticamente all'uscita dalla modalità o alla disconnessione.

---

## 📁 Struttura Progetto

```
StaffMode/
├── src/main/
│   ├── java/com/ckanto/staffmode/
│   │   ├── StaffModePlugin.java             # Entry point del plugin
│   │   ├── command/
│   │   │   └── StaffCommand.java            # Handler comandi /staff e /leavestaff
│   │   ├── config/
│   │   │   └── StaffConfig.java             # Wrapper config.yml
│   │   ├── listener/
│   │   │   └── StaffItemListener.java       # Gestione interazioni item hotbar
│   │   ├── manager/
│   │   │   └── StaffModeManager.java        # Business logic staff mode
│   │   ├── model/
│   │   │   └── StaffToolType.java           # Enum strumenti hotbar
│   │   └── util/
│   │       ├── ItemFactory.java             # Creazione item hotbar
│   │       └── PlayerTargetFinder.java      # Ricerca giocatore guardato
│   └── resources/
│       ├── config.yml
│       └── plugin.yml
└── pom.xml
```

---

## 🔨 Build dal Sorgente

Assicurati di avere **Java 17+** e **Maven 3.8+** installati.

```bash
git clone https://github.com/ToxitAnto/StaffMode-Plugin.git
cd StaffMode-Plugin
mvn clean package -DskipTests
```

Il JAR compilato si troverà in `target/StaffMode.jar`.

---

## 📝 Changelog

### v1.0.0
- Release iniziale
- Hotbar staff con 5 strumenti configurabili
- Toggle vanish e volo con click ripetuto
- InvSee con ray-cast sul giocatore guardato
- Teleport casuale su giocatore online
- Ripristino hotbar automatico alla disconnessione
- Tutti i messaggi e item configurabili da `config.yml`

---

## 👤 Autore

Sviluppato con ❤️ da **ckanto** per **Gravity**

- GitHub: [@ToxitAnto](https://github.com/ToxitAnto)

---

<div align="center">
<sub>StaffMode © 2024 ckanto — Gravity. Released under the MIT License.</sub>
</div>
