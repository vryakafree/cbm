# Changelog

All notable changes to TDsound will be documented in this file.

## [1.0.3] - 2025-08-02

### 🎉 Major Rename & Enhanced Fade Out

#### Changed
- **Mod Name**: Renamed from "Custom Congrat Sound For Cobblemon" to "TDsound"
- **Mod ID**: Changed from `customcongratsoundforcobblemon` to `tdsound`
- **Focus**: Enhanced focus on smooth fade out effects

#### Enhanced
- **Victory Music Fade Out**: Implemented smooth volume fade out instead of abrupt stop
- **Fade Out System**: Added sophisticated fade out with 20-step volume reduction
- **Volume Control**: Dynamic volume adjustment during fade out process
- **Timer Management**: Improved timer handling for fade out and stop operations

#### Updated
- **Documentation**: Complete README update with new mod name and fade out features
- **Language Files**: Updated all translation keys to new mod ID
- **Assets**: Renamed assets directory to match new mod ID
- **Configuration**: Updated config file path to new mod ID

### 🎵 Sound System
- **Victory Music**: Plays with smooth fade out after battle victory
- **Evolution Congratulations**: Plays after evolution completes
- **Catch Congratulations**: Plays when Pokemon is caught

### 🎮 Commands
- `/cobblecongrat status` - Show mod status and enabled features
- `/cobblecongrat test victory` - Test victory music (plays with fade out info)
- `/cobblecongrat test evo_congrat` - Test evolution congratulations (plays sound)
- `/cobblecongrat test catch` - Test catch congratulations (plays sound)
- `/cobblecongrat stop` - Stop all custom sounds
- `/cobblecongrat config` - Show current configuration

## [1.0.2] - 2025-08-02

### 🎉 Major Rename & Refinement

#### Changed
- **Mod Name**: Renamed from "Custom Cobblemon Music Mod" to "Custom Congrat Sound For Cobblemon"
- **Mod ID**: Changed from `customcobblemonmusicmod` to `customcongratsoundforcobblemon`
- **Command**: Updated from `/cobblemusic` to `/cobblecongrat`
- **Focus**: Simplified to focus on congratulatory sounds only

#### Removed
- **Evolution Music**: Removed `evo.ogg` sound and related functionality
- **Evolution Volume Setting**: Removed `evolutionMusicVolume` from configuration
- **Evolution Test Command**: Removed `/cobblecongrat test evolution` command

#### Enhanced
- **Victory Music**: Improved fade out functionality with configurable duration
- **Test Commands**: All test commands now actually play sounds instead of just showing info
- **Sound Playback**: Added actual sound playing to `test evo_congrat` and `test catch` commands
- **Configuration**: Added `victoryMusicFadeOutDuration` setting (default: 2000ms)

#### Updated
- **Documentation**: Complete README update with new mod name and features
- **Language Files**: Updated all translation keys to new mod ID
- **Assets**: Renamed assets directory to match new mod ID
- **Configuration**: Updated config file path to new mod ID

### 🎵 Sound System
- **Victory Music**: Plays with fade out after battle victory
- **Evolution Congratulations**: Plays after evolution completes
- **Catch Congratulations**: Plays when Pokemon is caught

### 🎮 Commands
- `/cobblecongrat status` - Show mod status and enabled features
- `/cobblecongrat test victory` - Test victory music (plays with fade out info)
- `/cobblecongrat test evo_congrat` - Test evolution congratulations (plays sound)
- `/cobblecongrat test catch` - Test catch congratulations (plays sound)
- `/cobblecongrat stop` - Stop all custom sounds
- `/cobblecongrat config` - Show current configuration

## [1.0.1] - 2025-08-02

### 🎵 Victory Music Fade Out Implementation

#### Added
- **Victory Music Fade Out**: Victory music now fades out gradually instead of cutting off
- **Fade Out Duration**: Configurable fade out duration (default: 2 seconds)
- **Enhanced Test Command**: Victory test command now shows fade out information

#### Improved
- **Stability**: Simplified architecture for better stability
- **User Experience**: Better feedback in test commands

## [1.0.0] - 2025-08-01

### Initial Release
- **Victory Music**: Celebration sound after winning battles  
- **Evolution Music**: Two-part evolution music sequence
- **Catch Music**: Congratulations when catching Pokemon
- **Commands**: Debug and testing commands
- **Sound Events**: Support for multiple music types

### Dependencies
- Minecraft 1.21.1
- Fabric Loader 0.16.9+
- Fabric API 0.110.0+1.21.1
- Cobblemon 1.6.1+
- Java 21+

---

## Planned Features
- **Enhanced Fade System**: More sophisticated fade out with volume ramping
- **Sound Event Hooking**: Direct integration with Cobblemon's sound events
- **Biome-Specific Sounds**: Different congratulatory sounds based on biome
- **Trainer Battle Sounds**: Special sounds for trainer battles vs wild Pokemon

## Notes
- Mod focuses on congratulatory sounds only
- Battle music is handled by Cobblemon's native system or resource packs
- All sounds are configurable through ModMenu or manual config file