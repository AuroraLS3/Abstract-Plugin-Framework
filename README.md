# Abstract Plugin Framework

Abstract Plugin Framework is a library for abstracting away server platform specific implementations.
Because of the work required, scope of the abstraction is quite limited.

> **Project discontinued**  
> A complete rewrite of the project is available at https://github.com/plan-player-analytics/Platform-abstraction-layer with different kind of goals, mainly avoiding the need to `extend`, instead relying on composition. 
>
> Some of the features like commands, debugging, errors, benchmarks or static utilities were removed in the process because they were unnecessary features that are not needed in all projects, or lead to bad code patterns. You can use other libraries for those features.
## Supported platforms

Currently all [Spigot](https://www.spigotmc.org/), [Sponge](https://www.spongepowered.org/), [BungeeCord](https://www.spigotmc.org/wiki/bungeecord/) and [Velocity](https://www.velocitypowered.com/) based platforms are supported.

## Available Features

- Plugin Console Logging
- Debug Logging (Console/Memory/File)
- Error Logging (Console/File)
- Benchmarking
- Command Abstraction Layer
- Task Scheduling Abstraction Layer
- Misc. Utilities
  - UUIDFetcher
  - Formatter
  - Check (Class loaded checker)
  - Verify (Condition verification utility)

## Documentation

- [Javadocs](https://rsl1122.github.io/Abstract-Plugin-Framework/)
- [Wiki](https://github.com/Rsl1122/Abstract-Plugin-Framework/wiki)
