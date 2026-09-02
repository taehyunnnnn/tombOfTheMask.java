# Tomb of the Mask
Java arcade game recreating the mobile hit with a custom tile renderer, sprite animation, and rising lava mechanic.

![demo](assets/demo.gif)

---

## Overview
A grid-based maze game where the player slides through hand-designed levels collecting coins, dodging spikes, and racing against rising lava. Built as a high school final project in Java, it implements a full game loop from scratch — including a tile engine, frame-by-frame sprite animation, a scrolling camera, and a coin economy with a purchasable shield.

---

## Features
- 6 hand-designed levels encoded as 2D integer tile maps
- Tile-sliding movement — character slides until it hits a wall, matching the mobile game feel
- 6-frame idle animation for the character; animated coins, stars, and exit portals
- Scrolling camera that keeps the player centered with clamped map boundaries
- Coin economy: dots (1 pt), coins (5 pts), stars (10 pts) — spend 100 coins on a shield
- Rising lava on Level 4 that creates real time pressure
- Full audio: background music + per-action sound effects (jump, pickup, death, shield, exit)
- Pause menu, death screen with replay, and a clickable level select grid

---

## Getting Started

**Requirements:** Java 8+

### Option A — Download and run (easiest)
[Download TombOfTheMask.jar from the latest release](https://github.com/taehyunnnnn/tombOfTheMask.java/releases/latest) then:
```bash
java -jar TombOfTheMask.jar
```

### Option B — Build from source
```bash
git clone https://github.com/taehyunnnnn/tombOfTheMask.java
cd tombOfTheMask.java/src
javac files/*.java hsa2/*.java
java files.main
```

---

## Controls
| Input | Action |
|---|---|
| `W A S D` / Arrow Keys | Move |
| `Enter` | Buy shield (costs 100 coins) |
| Click pause icon (top-right) | Pause |
| Click left blue button | Resume |
| Click right blue button | Return to level select |
| Click red button | Replay after death |

---

## What I Learned
- Encoding levels as 2D integer arrays and rendering them with a nested loop tile engine
- Building a game loop with `Thread.sleep` and tick counters to sync movement, animation, and sound
- Computing scroll offsets to keep the player centered on screen, clamped to map edges
- Grid-based collision detection by checking adjacent cells before committing a move
- Cycling sprite frames on a timer to animate the character, coins, stars, and exit portal

---

## Author
**Taehyun Im**
[GitHub](https://github.com/taehyunnnnn) · [Portfolio](https://taehyun.pages.dev) · [LinkedIn](https://linkedin.com/in/taehyunim)

---

## Acknowledgments
- Inspired by [Tomb of the Mask](https://apps.apple.com/us/app/tomb-of-the-mask/id1010213994) by Playgendary
- `hsa2.GraphicsConsole` — teaching graphics library used in Ontario high school CS courses
- Sound effects and sprite assets created for this project
