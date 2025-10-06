# AntiSeedCracker

A comprehensive Minecraft plugin that prevents players from obtaining the world seed through various methods.

## Features

- **Command Blocking**: Blocks `/seed`, `/locatebiome`, `/locate`, and other seed-revealing commands
- **Execute Protection**: Prevents players from using `/execute` to run seed commands
- **Customizable Protection**: Configure which features to enable/disable
- **World-Specific Protection**: Protect all worlds or specific worlds only
- **Bypass Permission**: Allow trusted players to access seed information
- **Debug Mode**: Verbose logging for troubleshooting
- **Configurable Messages**: Customize all plugin messages

## Commands

- `/antiseedcracker reload` or `/asc reload` - Reload configuration
- `/antiseedcracker info` or `/asc info` - Show plugin information
- `/antiseedcracker help` or `/asc help` - Show help menu

## Permissions

- `antiseedcracker.admin` - Access to admin commands (default: op)
- `antiseedcracker.bypass` - Bypass seed protection (default: false)

## Configuration

### config.yml
Configure protection features, blocked commands, and protected worlds.

### messages.yml
Customize all plugin messages and notifications.

## Compatibility

- Minecraft versions: 1.13 - 1.21.4
- Server software: Paper, Spigot
- Java version: 21

## Installation

1. Download the plugin JAR file
2. Place it in your server's `plugins` folder
3. Restart your server
4. Configure the plugin in `plugins/AntiSeedCracker/config.yml`
5. Reload with `/asc reload`

## Protection Methods

- Blocks direct seed commands
- Blocks location-based commands that aid seed cracking
- Prevents command execution workarounds
- Customizable command blacklist

## Author
Created by Dutchwilco

## Support

For issues or questions, please contact me on Discord: Dutchwilco
