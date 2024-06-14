[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/B2OnycBl)
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-718a45dd9cf7e7f842a935f5ebbe5719a5e09af4491e668f4dbf3b35d5cca122.svg)](https://classroom.github.com/online_ide?assignment_repo_id=15135344&assignment_repo_type=AssignmentRepo)
# 6.1 Processing in Java CPT

## Objective
The goal of the game is to reach the highest level possible. There are an unlimited number of levels, each progressively getting more challenging. You start with ten lives, and each collision with an enemy costs you one. Lose all your lives, and the game ends. Use your mouse maneuvering skills to dodge enemies and survive as long as you can!

## Gameplay Mechanics
### Enemies
Each level spawns a random number of enemies, each with a random speed and diameter. The larger the diameter, the more health points the enemy has, with health scaling multiplicatively with each level. All enemies move towards the cursor like homing missiles. They always spawn from the top of the screen at a random X-position. Upon collision or death, enemies explode, causing collateral damage to nearby enemies.

### Inputs
The keys "w, a, s, d" on the keyboard can be inputted, and specific combinations of these keys trigger unique abilities. Invalid input combinations include sequences that do not exist or attempts to activate abilities when the player lacks sufficient mana. Mana regenerates by holding down the mouse button.

1. Input "add" (costs 100 mana):
Entering "add" activates a slot machine at the screen center. Achieving three identical numbers results in dealing immense damage to all enemies on the board.

2. Input "ddd" (costs 150 mana):
Inputting "ddd" freezes all enemies temporarily. Upon recovery from this state, the enemies' speed is permanently reduced by half.

3. Input "sss" (costs 100 mana):
Typing "sss" summons three lightning strikes periodically. Each lightning strike deals immense damage to the enemy with the highest health.

4. Input "wws" (costs 100 mana):
Inputting "wws" summons a rain of arrows that continues for a specified duration. Each arrow inflicts minimal damage upon contact with an enemy.

## Scoring
The current level is displayed at the top left of the screen, along with a record of your inputs, remaining lives, and mana. You start with a total of 10 lives, and losing all lives results in a game over. Mana is capped at 200, and holding down the mouse button regenerates it. The level advances when all enemies in the current level are defeated.

## Limitations
There are a few limitations in this game:
* The enemy AI is straightforward, and there are no variations in enemy types with different abilities. Additionally, only four input combinations are currently implemented in the game.
* The mouse can exit the screen, but enemies can still damage you based on the last recorded position of your mouse.