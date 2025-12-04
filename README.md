## GitHub: https://github.com/shusenjiang506-oss/CW2025

## Compilation Instructions:
### Prerequisites
- Java JDK 11 or higher
- JavaFX SDK
- Maven (recommended)

### Build and Run
```bash
# Clone repository
git clone + github link
cd CW2025

# Build with Maven
mvn clean compile

# Run the game
mvn exec:java

# Run tests
mvn test
```

### Game Controls
- **Arrow Keys / WASD**: Move and rotate bricks
- **Space**: Hard drop (instant drop)
- **P**: Pause/Resume
- **1**: Select Classic Mode
- **2**: Select Timed Mode (2 minutes)
- **N**: Start new game

## Implemented and Working Properly:

### Bug Fixes (3)
1. **Fixed premature game over** (SimpleBoard.java)
    - Brick spawn position corrected from Y=10 to Y=0
    - Game now ends only when blocks reach actual top

2. **Fixed boundary checking** (MatrixOperations.java)
    - Added null validation to prevent ArrayIndexOutOfBoundsException
    - Reordered bounds checking for safety

3. **Fixed null pointer crashes** (BrickRotator.java)
    - Added null checks before brick operations
    - Validates brick is set before rotation

### Refactoring (3)
4. **Eliminated code duplication** (All Brick classes)
    - Created AbstractBrick base class
    - Applied Template Method design pattern
    - Reduced code by ~40 lines across 7 brick classes

5. **Improved code maintainability** (GameController.java, GuiController.java)
    - Extracted magic numbers to named constants
    - Replaced switch statements with arrays
    - Enhanced method naming and readability

6. **Added comprehensive documentation**
    - Javadoc comments for all public methods
    - Inline comments for complex logic


### New Features (5)
7. **Pause/Resume** (Press P)
    - Pauses game and timer
    - Shows "PAUSED" message

8. **Progressive Difficulty**
    - Speed increases every 10 lines cleared
    - 10 levels (500ms → 100ms)
    - Visual level-up notifications

9. **Timed Game Mode**
    - 2-minute countdown timer
    - Mode selection at start
    - Timer display turns red at 30s

10. **Hard Drop** (Press Space)
    - Instantly drops brick to bottom
    - Awards 2 points per cell dropped

11. **Real-time UI Display**
    - Game mode indicator
    - Current level (gold)
    - Score counter (green)
    - Timer (red when low)

## Implemented but Not Working Properly:
None. All features function as expected.

## Features Not Implemented:
- Next brick preview
- High score persistence
- Sound effects
- Ghost piece (shadow)
- Hold piece feature


## New Java Classes:
| Class | Purpose |
|-------|---------|
| AbstractBrick.java | Base class for all bricks (Template Method pattern) |
| PausePanel.java | UI panel for pause screen |
| GameMode.java | Enum for game modes (CLASSIC, TIMED) |
| ModeHintPanel.java | Mode selection screen |

## Modified Java Classes:

| Class | Changes |
|-------|---------|
| **SimpleBoard.java** | Fixed spawn position (Y=10→Y=0), added hardDrop() |
| **GameController.java** | Added level tracking, constants, hard drop handler |
| **MatrixOperations.java** | Fixed boundary checking, extracted constants |
| **BrickRotator.java** | Added null safety checks |
| **GuiController.java** | Added mode selection, timer, UI display, hard drop |
| **IBrick.java - ZBrick.java** | Refactored to extend AbstractBrick (all 7 bricks) |


## Unexpected Problems:
### 1. Premature Game Over
**Symptom**: Game ended after first brick despite board being mostly empty

**Cause**: Bricks spawned at Y=10 instead of Y=0, causing collision when stack reached middle

**Fix**: Changed spawn position to Y=0 in SimpleBoard.java


### 2. Array Out of Bounds Exception
**Symptom**: Crashes when checking collision near board edges

**Cause**: Boundary check accessed array before validating indices

**Fix**: Reordered validation logic in MatrixOperations.java



### 3. UI Label Positioning Issues
**Symptom**: Game info labels appeared in wrong location or off-screen

**Cause**: Container positioning differed from expected coordinates

**Fix**: Used negative Y offsets (-180, -155, -130, -105) for proper placement


### 4. Code Duplication Across Brick Classes
**Symptom**: Identical code in all 7 brick classes

**Cause**: No inheritance structure in original design

**Fix**: Created AbstractBrick with Template Method pattern

## Testing

### Unit Tests (5 test classes, all passing)

RandomBrickGeneratorTest - 4 tests passed
BrickRotatorTest - 4 tests passed  
MatrixOperationsTest - 5 tests passed
ScoreTest - 4 tests passed
SimpleBoardTest - 5 tests passed

Total: 22 tests, 0 failures, 0 errors

