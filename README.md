# Hytale Plugin Template

A template for developing Hytale server plugins.

## Requirements

- Java 25+
- Hytale installed via official launcher

## Quick Start

1. Clone this template
3. Generate the sources `./gradlew generateSources`
4. Run `./gradlew runServer`
5. Authenticate with `auth login` and `auth persistence Encrypted`

## Gradle Tasks

| Task | Description |
|------|-------------|
| `./gradlew generateSources` | Generate the sources from HytaleServer.jar |
| `./gradlew runServer` | Build and run the server |
| `./gradlew build` | Build the plugin shadowJar |
| `./gradlew clean` | Clean build files and run directory |

## Output

Built plugin: `build/libs/<mod_name>-<version>.jar`

## Tips

- Run `./gradlew clean` after changing mod name/group
- **Never commit the `run/` directory** - it contains authentication credentials
