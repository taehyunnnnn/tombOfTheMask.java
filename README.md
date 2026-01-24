# Tomb of the Mask (Final Project)

A Java-based arcade maze game inspired by *Tomb of the Mask*.  
Built as a final project by **Tei** and **Nihaal** (Jan 19, 2024).

Navigate through grid-based levels, collect items, avoid traps, and reach the exit ✅

---

## Features

- Grid-based movement (WASD / arrow keys)
- Multiple levels + level select screen
- Collectibles: dots, coins, stars (score/balance system)
- Hazards: spikes + spike traps
- Rising lava level (challenge mode on specific maps)
- Buyable shield (extra life) using coins
- Music + sound effects
- Pause menu + restart flow

---

## Controls

### Movement
- **W / A / S / D** or **Arrow Keys** → move

### Gameplay
- **Enter** → buy shield (when available)
- **Click pause icon** (top-right) → pause menu
- pause menu buttons: resume / return to level select

---

## Tech Stack

- **Language:** Java
- **Graphics:** `hsa2.GraphicsConsole`
- **Audio:** `javax.sound.sampled.Clip`
- **Assets:** images + sound effects referenced in `assets` / `stages`

---

## How to Run

1. Open the project in your Java IDE (or your course setup)
2. Make sure required libraries are included (ex: `hsa2.GraphicsConsole`)
3. Ensure asset files (images/sounds) are in the expected folders
4. Run:

```java
public static void main(String[] args) throws InterruptedException
