# Gremlin

Gremlin is a 2D game where you play as a wizard navigating through various levels filled with obstacles, entities and power ups. Your goal is to reach the gate at the end of each level while avoiding the green gremlins. Fireballs can be used to eliminate the gremlins and destroy brick walls.

## Table of Contents

- [Gameplay](#gameplay)
- [Controls](#controls)
- [Installation](#installation)
- [Running the Game](#running-the-game)
- [Building the Game](#building-the-game)
- [Requirements](#requirements)

## Gameplay

In Gremlin, you control a wizard who can move in four directions and shoot fireballs. The game environment consists of brick walls, stone walls, gremlins and snowballs:

- **Brick Walls**: These can be destroyed by shooting fireballs at them.
- **Stone Walls**: These are indestructible and cannot be destroyed by fireballs.
- **Gremlins**: These enemies move around the level. If the wizard touches a gremlin, they lose a life and must restart the level.
- **Snowballs**: These power ups randomly spawn throughout the level. If the wizard touches them, all gremlins will be frozen for a short amount of time.

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
    cd gremlin-game
    ```

2. **Ensure you have Java 8 installed**. You can download it from [here](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html).

3. **Install Gradle**. You can download it from [here](https://gradle.org/install/).

## Running the Game

To run the game, use the following command in your terminal:
```bash
gradle build
gradle run
```

---