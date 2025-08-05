# Changelog

All notable changes to TDsound will be documented in this file.

## [1.1.1] - 2025-08-05

### 🎵 Major Feature: Cobblemon Sound Control System

#### Added
- **Cobblemon Sound Control**: Experimental system to control volume/pitch of ALL Cobblemon sounds
- **Sound Categories**: Separate controls for Pokemon cries, Pokeball sounds, battle sounds, and general sounds
- **Advanced Configuration**: New ModMenu category "Cobblemon Sound Control" with comprehensive settings
- **Test Commands**: `/tdsound cobblemon test pokeball` and `/tdsound cobblemon test cry` for testing
- **Management Commands**: `/tdsound cobblemon toggle` and `/tdsound cobblemon status`
- **Flee Music**: Added support for flee music with separate volume/pitch controls

#### Enhanced
- **Separate Volume/Pitch**: All custom sounds now have independent volume AND pitch controls
- **ModMenu Integration**: Added "Pitch Settings" category with proper validation (0.5-2.0 range)
- **Language File**: Comprehensive translations for all new features and settings
- **Configuration**: Proper validation for all volume (0.0-1.0) and pitch (0.5-2.0) values

#### Changed
- **Command System**: Changed from `/cobblecongrat` to `/tdsound` for better consistency
- **JAR Naming**: Updated to "Tdsound-1.1.1.jar" format
- **Mod Description**: Updated to reflect new Cobblemon sound management capabilities

#### Fixed
- **Mixin Crashes**: Removed problematic mixin system that caused game crashes
- **Sound Framework**: Replaced with safer, more compatible sound management approach
- **Stability**: Eliminated MixinTransformerError and related crash issues

#### Technical
- **CobblemonSoundInterceptor**: New class for managing Cobblemon sound modifications
- **Sound Categorization**: Intelligent sound type detection based on sound ID paths
- **Debug Logging**: Enhanced logging shows intercepted sounds with original/modified values
- **Safety First**: Cobblemon sound control disabled by default to prevent conflicts

## [1.0.9] - 2025-08-05

### 🎮 Command System Overhaul

#### Changed
- **Command Prefix**: Updated from `/cobblecongrat` to `/tdsound` for consistency with mod name
- **Help Text**: Updated all command references and status messages
- **Logging**: Updated initialization message to reference new command structure

#### Commands
- `/tdsound status` - Show mod status and available commands
- `/tdsound test victory` - Test victory music
- `/tdsound test evo_congrat` - Test evolution congratulations music  
- `/tdsound test catch` - Test catch congratulations music
- `/tdsound test flee` - Test flee music
- `/tdsound stop` - Stop all custom sounds
- `/tdsound config` - Show current configuration settings

## [1.0.8] - 2025-08-05

### 🎛️ Separate Volume and Pitch Controls

#### Added
- **Pitch Configuration**: Separate pitch fields for all sound types (victory, evolution, catch, flee)
- **Pitch Validation**: Proper validation with 0.5-2.0 range for all pitch values
- **ModMenu Pitch Category**: New "Pitch Settings" category in configuration GUI
- **Enhanced Commands**: All test commands now display both volume and pitch information
- **Language Support**: Added pitch-related translations with comprehensive tooltips

#### Enhanced
- **Sound Playing Logic**: Updated to use separate volume and pitch parameters
- **Client Integration**: Modified client-side sound playing to support independent pitch control
- **Configuration Display**: Commands now show both volume percentage and pitch multiplier
- **Debug Logging**: Enhanced to include pitch information in sound playing logs

#### Fixed
- **Configuration Issue**: Previous "volume" setting was actually controlling pitch - now properly separated
- **Sound Parameters**: Corrected sound instance creation to use proper volume/pitch separation

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