# Gremlin

Gremlinure is a fun and challenging 2D game where you play as a wizard navigating through various levels filled with brick walls, stone walls, and moving gremlins. Your goal is to reach the gate at the end of each level while avoiding gremlins and using fireballs to clear your path through destructible brick walls.

## Table of Contents

- [Gameplay](#gameplay)
- [Controls](#controls)
- [Installation](#installation)
- [Running the Game](#running-the-game)
- [Building the Game](#building-the-game)
- [Requirements](#requirements)
- [Contributing](#contributing)
- [License](#license)

## Gameplay

In Gremlinure, you control a wizard who can move in four directions and shoot fireballs. The game environment consists of brick walls, stone walls, and gremlins:

- **Brick Walls**: These can be destroyed by shooting fireballs at them.
- **Stone Walls**: These are indestructible and cannot be destroyed by fireballs.
- **Gremlins**: These enemies move around the level. If the wizard touches a gremlin, they lose a life and must restart the level.

### Objective

Reach the gate at the end of each level to progress to the next one. Avoid gremlins and use your fireballs wisely to clear brick walls and create a path to the gate.

## Controls

- **Arrow Keys**: Move the wizard up, down, left, or right.
- **Spacebar**: Shoot a fireball in the direction the wizard is facing.

## Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/wizard-adventure.git
    cd wizard-adventure
    ```

2. **Ensure you have Java 8 installed**. You can download it from [here](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html).

3. **Install Gradle**. You can download it from [here](https://gradle.org/install/).

## Running the Game

To run the game, use the following command in your terminal:
```bash
gradle run
```

## Building the Game

To build the game, use the following command in your terminal:
```bash
gradle build
```

This will compile the game and package it into a JAR file located in the `build/libs` directory.

## Requirements

- Java 8
- Gradle

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any changes you would like to make.

1. **Fork the repository**
2. **Create a new branch** (`git checkout -b feature-branch`)
3. **Commit your changes** (`git commit -am 'Add new feature'`)
4. **Push to the branch** (`git push origin feature-branch`)
5. **Create a new Pull Request**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Enjoy playing Gremlinure and may your wizarding skills lead you to victory!

---