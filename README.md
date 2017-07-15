# Abstract Plugin Framework

Abstract Plugin Framework (APF) is a framework for creating Bukkit & Bungee plugins faster removing some Abstraction work.
APF also adds Utilities for multiple different things.

## Utilities

- Benchmarking
- Logging (Abstracted)
  - Error file logging so users don't need to look at stacktraces.
  - Debug logging to console/file/both
- Abstracted Runnable creation
- Abstracted Command registration & senders
- Plugin Message Subchannels
- Compatibility utility for checking if Bukkit, Bungee or Spigot methods are available.
- Enum utility for getting supported Enum variables from a list of enum name Strings.
- Timings for average benchmarks
- Version checking
- Verification
  - NPE free verification methods
    - File | exists
    - Array, Collection, Map | isEmpty, contains
    - Object | equals
  - Verification methods for parameters that throw IllegalArgumentException
- Static instance methods

More information can be read from the wiki.
  
## Documentation

- Javadocs (Coming soon)
- [Wiki](https://github.com/Rsl1122/Abstract-Plugin-Framework/wiki)

## Contributing to the project

- Open issue tickets
- Make a pull request
- Create a plugin using the framework
  - Framework classes should be included in the plugin jar.
