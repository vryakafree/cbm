# Changelog

All notable changes to Custom Cobblemon Music Mod will be documented in this file.

## [1.0.2] - 2025-08-03

### 🎵 Enhanced Music Integration & Fade System

#### Fixed
- **Victory Music Fade Out**: Fixed abrupt music cutoff - now fades out smoothly over 2 seconds
- **Evolution Music Integration**: evo.ogg now plays alongside Cobblemon's evolution_full.ogg during evolution
- **Music Timing**: Improved synchronization with Cobblemon's native sound events

#### Added
- **Client-Side Music Handler**: Dedicated client-side system for better music control
- **Network Packet System**: Server-client communication for music commands
- **Mixin Integration**: Direct hook into PokemonEntity for evolution start detection
- **Configurable Fade Duration**: Victory music fade out duration is now configurable (0.5-5 seconds)
- **Enhanced Evolution Detection**: Uses mixin to detect evolution start immediately

#### Improved
- **Music Playback**: Better volume control and sound management
- **Event Handling**: More reliable Cobblemon event integration using Kotlin
- **Performance**: Optimized network communication and resource management
- **Error Handling**: Better fallback mechanisms and error recovery

### 🎛️ Configuration Enhancements

#### Added
- **Victory Fade Out Duration**: Configurable fade out timing (default: 2 seconds)
- **Enhanced Volume Controls**: Better precision for all music types
- **Network Settings**: Internal optimizations for client-server communication

### 🛠️ Technical Improvements

#### Added
- **Kotlin Integration**: Better compatibility with Cobblemon's Kotlin codebase
- **Mixin System**: Direct integration with Cobblemon's internal systems
- **Network Protocol**: Efficient server-client communication for music commands
- **Client-Side Processing**: Dedicated client handlers for better performance

#### Dependencies
- **Enhanced**: Kotlin stdlib 1.9.0+ for better Cobblemon integration
- **Added**: Mixin support for direct API access

### 🎮 Commands
- **Updated**: `/cobblemusic status` now shows fade out information
- **Enhanced**: All test commands reflect new fade out system

### 📝 Documentation
- **Updated**: README with new fade out and evolution integration features
- **Added**: Technical documentation for mixin and network systems

## [1.0.1] - 2025-08-02

### 🎵 Major Music System Overhaul

#### Fixed
- **Music Playback Speed**: Fixed slow music playback (was ~0.25x speed, now normal speed)
- **Battle Music Persistence**: Music now stops immediately when battles end (flee, faint, /stopbattle commands)
- **Evolution Music Timing**: Evolution music now plays when evolution starts, stops when congratulations play
- **Music Overlap Prevention**: Fixed victory music overlapping with battle music

#### Added
- **Enhanced Battle Tracking**: Comprehensive battle ID tracking system for better state management
- **Immediate Battle End Detection**: Real-time monitoring for external battle termination
- **Evolution Sound Handler**: Dedicated system for proper evolution music timing
- **Battle State Monitoring**: Continuous monitoring for responsive music changes
- **Enhanced Fade System**: Improved fade-out with immediate response capability

#### Improved
- **Event Handling**: More robust Cobblemon event integration
- **Error Handling**: Better fallback mechanisms for API changes
- **Performance**: Optimized timer management and resource cleanup
- **Debug Logging**: Enhanced logging for troubleshooting

### 🎛️ Configuration System

#### Added
- **ModMenu Integration**: Full GUI configuration through ModMenu
- **Volume Controls**: Individual volume settings for each music type
- **Feature Toggles**: Enable/disable specific music types
- **Advanced Settings**: Configurable thresholds and timing
- **Auto-save Configuration**: Settings automatically save and load

#### Features
- **Battle Music Volume** (0.0 - 1.0, default: 0.8)
- **Strong Battle Music Volume** (0.0 - 1.0, default: 0.85)
- **Panic Music Volume** (0.0 - 1.0, default: 0.9)
- **Victory Music Volume** (0.0 - 1.0, default: 1.0)
- **Evolution Music Volume** (0.0 - 1.0, default: 0.7)
- **Evolution Congratulations Volume** (0.0 - 1.0, default: 0.8)
- **Catch Congratulations Volume** (0.0 - 1.0, default: 0.9)
- **Panic Health Threshold** (configurable, default: 20%)
- **Strong Battle Level Difference** (configurable, default: 15 levels)
- **Victory Music Duration** (configurable, default: 7 seconds)
- **Debug Logging** (optional detailed logging)

### 🛠️ Dependencies
- **Added**: ModMenu 11.0.1+ (optional, for configuration GUI)
- **Added**: Cloth Config 15.0.127+ (optional, for configuration GUI)

### 🎮 Commands
- **Added**: `/cobblemusic test evo` - Test evolution music sequence
- **Improved**: All test commands now reflect actual system behavior

### 📝 Documentation
- **Updated**: Comprehensive README with configuration instructions
- **Added**: English language file for ModMenu integration
- **Cleaned**: Removed unnecessary documentation files

## [1.0.0] - 2025-08-01

### Initial Release
- **Battle Music**: Automatic music during Cobblemon battles
- **Victory Music**: Celebration sound after winning battles  
- **Evolution Music**: Two-part evolution music sequence
- **Catch Music**: Congratulations when catching Pokemon
- **Commands**: Debug and testing commands
- **Sound Events**: Support for 7 different music types

### Dependencies
- Minecraft 1.21.1
- Fabric Loader 0.16.9+
- Fabric API 0.110.0+1.21.1
- Cobblemon 1.6.1+
- Java 21+

---

## Planned Features
- **Health Monitoring**: Real-time Pokemon health tracking for panic music (when API stabilizes)
- **Level Detection**: Automatic strong battle music based on level differences (when API stabilizes)
- **Sound Event Hooking**: Direct integration with Cobblemon's evolution_full sound event
- **Biome-Specific Music**: Different battle themes based on biome
- **Trainer Battle Music**: Special music for trainer battles vs wild Pokemon

## Notes
- Some advanced features are simplified pending Cobblemon API stabilization
- Health and level monitoring use fallback detection methods
- Evolution music timing uses event-based triggers for better synchronization