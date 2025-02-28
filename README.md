# Gremlin

Gremlin is a 2D game where you play as a wizard navigating through various levels filled with obstacles, entities and power ups. Your goal is to reach the gate at the end of each level while avoiding the green gremlins. Fireballs can be used to eliminate the gremlins and destroy brick walls.

<img width="720" alt="Screenshot 2024-07-03 at 11 54 20 am" src="https://github.com/jayden-pham/gremlin-game/assets/101793495/6c89f8c0-ee37-4838-9f91-f59a1a2c780c">

## Table of Contents

- [Gameplay](#gameplay)
- [Controls](#controls)
- [Installation](#installation)
- [Running the Game](#running-the-game)

## Gameplay

In Gremlin, you control a wizard who can move in four directions and shoot fireballs. The game environment consists of brick walls, stone walls, gremlins, snowballs and portals:

- **Brick Walls**: These can be destroyed by shooting fireballs at them.
- **Stone Walls**: These are indestructible and cannot be destroyed by fireballs.
- **Gremlins**: These enemies move around the level. If the wizard touches a gremlin, they lose a life and must restart the level.
- **Snowballs**: These power ups randomly spawn throughout the level. If the wizard touches them, all gremlins will be frozen for a short amount of time.
- **Portals**: Portals may randomly spawn in pairs. Interacting with a portal will instantly teleports the player to the other end of the portal.

### Objective

Reach the gate at the end of each level to progress to the next one. Avoid gremlins and use your fireballs wisely to eliminate gremlins and destroy brickwalls.

## Controls

- **Arrow Keys**: Move the wizard up, down, left, or right.
- **Spacebar**: Shoot a fireball in the direction the wizard is facing.

## Installation
Java 8 and Gradle are required to run the game.

1. **Clone the repository**:
    ```bash
    git clone https://github.com/jayden-pham/gremlin-game.git
    ```

2. **Ensure you have Java 8 installed**. You can download it from [here](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html).

3. **Install Gradle**. You can download it from [here](https://gradle.org/install/).

## Running the Game

To run the game, navigate to the ```gremlin-game``` directory and use the following command in your terminal:
```bash
gradle build
gradle run
```

---
