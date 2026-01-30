# Hytale Plugin Template

A template for developing Hytale server plugins.

## Requirements

- Java 25+
- Hytale installed via official launcher

## Quick Start

1. Clone this template
2. Generate the sources `./gradlew generateSources`
3. Run `./gradlew runServer`
4. Authenticate with `auth login` and `auth persistence Encrypted`
5. Join the server through `localhost:5520`

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
- Asset editor changes for the `Common` directory require server restart. (Edits outside the editor work without a restart)
- **Never commit the `run/` directory** - it contains authentication credentials
- See the [HytaleGradlePlugin repository](https://github.com/MrMineO5/HytaleGradlePlugin/) for full configuration options and documentation
