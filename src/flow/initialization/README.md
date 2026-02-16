# League of Warriors - Java Text & GUI Adventure Game

##  Game Overview
League of Warriors is a complex adventure game where players can create multiple characters to explore a grid-based map (max 10x10). The game features turn-based combat, level progression, and diverse events such as Sanctuaries and Portals.


##  Key Features
- **Authentication System:** Secure login using email and password credentials parsed from a JSON file.
- **Dynamic Map Generation:** Maps are generated with random dimensions and randomized entity placement (Enemies, Sanctuaries, Portals).
- **Turn-based Combat:** Strategic battles where players choose between normal attacks or special abilities (Fire, Ice, Earth) based on character class.
- **Character Evolution:** Three distinct classes (**Warrior, Mage, Rogue**) with unique attributes (Strength, Dexterity, Charisma) that scale with experience.
- **Dual Interface:** Supports both terminal-based execution and a Graphical User Interface (GUI) built with **Java Swing**.

##  Design Patterns Applied
To ensure a scalable and clean architecture, several design patterns were implemented:
- **Singleton:** Restricts the `Game` class to a single global instance with lazy initialization.
- **Factory:** Decouples character instantiation based on class type.
- **Builder:** Efficiently constructs complex `Information` objects for user accounts.
- **Visitor:** Models special ability effects on entities, separating behavioral logic from object structure.

##  Project Structure
- `src/flow`: Core game mechanics and combat logic.
- `src/initialization`: JSON parsing and data loading.
- `src/map`: Grid management and navigation.
- `lib/`: External dependencies (json-simple).

##  Technical Requirements
- Java Development Kit (JDK) 17 or higher.
- `json-simple-1.1.1.jar` library.
