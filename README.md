# Hytale Plugin Template

A template for developing Hytale server plugins.

## Requirements

- Java 25+
- Hytale installed via official launcher

## Quick Start

1. Clone this template
2. Edit `gradle.properties` with your mod name/group
3. Run `./gradlew runServer`
4. Authenticate with `auth login` and `auth persistence encrypted`

## Gradle Tasks

| Task | Description |
|------|-------------|
| `./gradlew runServer` | Build and run the server |
| `./gradlew build` | Build the plugin shadowJar |
| `./gradlew clean` | Clean build files and run directory |

## Output

Built plugin: `build/libs/<mod_name>-<version>.jar`

## Tips

- Run `./gradlew clean` after changing mod name/group
- Set `hytale_home` in `gradle.properties` if auto-detection fails
- **Never commit the `run/` directory** - it contains authentication credentials
