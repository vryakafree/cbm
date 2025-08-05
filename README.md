# TDsound

A comprehensive Fabric mod that adds **Victory**, **Evolution Congratulations**, **Catch Congratulations**, and **Flee** sounds to your Cobblemon experience with advanced volume/pitch control system. Now includes experimental Cobblemon sound management tools for controlling ALL Cobblemon sounds!

## ✨ Features

### 🎵 **Sound Types**
- **🏆 Victory Music** - Plays immediately after winning battles
- **🎉 Evolution Congratulations** - Plays when evolution completes
- **⭐ Catch Congratulations** - Plays when you successfully catch Pokemon
- **🏃 Flee Music** - Plays when you flee from battle

### 🎛️ **Advanced Sound Control**
- **Separate Volume & Pitch Controls** - Individual volume (0-100%) and pitch (0.5x-2.0x) for each sound
- **Cobblemon Sound Management** - Experimental system to control ALL Cobblemon sounds
- **Sound Categories** - Control Pokemon cries, Pokeball sounds, battle sounds separately
- **Real-time Configuration** - Changes apply immediately without restart

### ⚙️ **Configuration System**
- **ModMenu Integration**: Full GUI configuration with multiple categories
- **Volume & Pitch Controls**: Separate controls for each sound type
- **Feature Toggles**: Enable/disable specific sound types
- **Cobblemon Sound Control**: Experimental controls for all Cobblemon sounds
- **Auto-save Configuration**: Settings automatically save and load

### 🎮 **Commands**
**Main Commands:**
- `/tdsound status` - Show mod status and enabled features
- `/tdsound test victory` - Test victory music
- `/tdsound test evo_congrat` - Test evolution congratulations
- `/tdsound test catch` - Test catch congratulations
- `/tdsound test flee` - Test flee music
- `/tdsound stop` - Stop all custom sounds
- `/tdsound config` - Show current configuration

**Cobblemon Sound Control:**
- `/tdsound cobblemon toggle` - Enable/disable Cobblemon sound control
- `/tdsound cobblemon status` - Show Cobblemon sound control settings
- `/tdsound cobblemon test pokeball` - Test Pokeball sound control
- `/tdsound cobblemon test cry` - Test Pokemon cry sound control

## 🎼 **Battle Music**

**This mod does NOT include battle music.** Battle music is handled by:

1. **Cobblemon's Native System** - Built-in battle music events
2. **Resource Packs** - Use packs like [Original Pokemon Battle Music](https://modrinth.com/resourcepack/cobblemon-original-pokemon-battle-music)

This design prevents conflicts and allows you to choose your preferred battle music solution.

## 📦 **Installation**

### Requirements
- **Minecraft**: 1.21.1
- **Fabric Loader**: 0.16.9+
- **Fabric API**: 0.110.0+1.21.1
- **Cobblemon**: 1.6.1+
- **Java**: 21+

### Optional Dependencies (for GUI config)
- **ModMenu**: 11.0.1+ - Adds mod to mods list
- **Cloth Config**: 15.0.127+ - Enables configuration GUI

### Steps
1. Download the latest JAR from [Releases](releases/)
2. Place in your `mods/` folder
3. Install optional dependencies for GUI configuration
4. Launch Minecraft with Fabric

## 🎵 **Adding Sound Files**

Create a resource pack with your custom sounds:

### Resource Pack Structure
```
your_resource_pack/
├── pack.mcmeta
└── assets/
    └── tdsound/
        └── sounds/
            ├── victory.ogg
            ├── evo_congrat.ogg
            ├── catch_congrat.ogg
            └── flee.ogg
```

### pack.mcmeta
```json
{
  "pack": {
    "pack_format": 34,
    "description": "Custom Congrat Sound For Cobblemon"
  }
}
```

### Audio Format Requirements
- **Format**: OGG Vorbis
- **Quality**: 44.1kHz, 16-bit recommended
- **Size**: Keep files under 10MB each for performance

## ⚙️ **Configuration**

### ModMenu GUI (Recommended)
1. Install ModMenu + Cloth Config
2. Go to **Mods** → **Custom Congrat Sound For Cobblemon** → **Config**
3. Adjust settings in the GUI
4. Settings save automatically

### Manual Configuration
Edit `config/tdsound.json`:

```json
{
  "victoryMusicVolume": 0.3,
  "evolutionCongratMusicVolume": 0.3,
  "catchCongratMusicVolume": 0.3,
  "fleeMusicVolume": 0.3,
  "victoryMusicPitch": 1.0,
  "evolutionCongratMusicPitch": 1.0,
  "catchCongratMusicPitch": 1.0,
  "fleeMusicPitch": 1.0,
  "enableVictoryMusic": true,
  "enableEvolutionMusic": true,
  "enableCatchMusic": true,
  "enableFleeMusic": true,
  "enableCobblemonSoundControl": false,
  "cobblemonSoundsVolume": 1.0,
  "cobblemonSoundsPitch": 1.0,
  "cobblemonPokemonCriesVolume": 1.0,
  "cobblemonPokemonCriesPitch": 1.0,
  "cobblemonPokeballSoundsVolume": 1.0,
  "cobblemonPokeballSoundsPitch": 1.0,
  "cobblemonBattleSoundsVolume": 1.0,
  "cobblemonBattleSoundsPitch": 1.0,
  "debugLogging": false
}
```

### Configuration Options

#### **Custom Sound Settings**
| Setting | Default | Description |
|---------|---------|-------------|
| `victoryMusicVolume` | 0.3 | Victory music volume (0.0-1.0) |
| `evolutionCongratMusicVolume` | 0.3 | Evolution congratulations volume (0.0-1.0) |
| `catchCongratMusicVolume` | 0.3 | Catch congratulations volume (0.0-1.0) |
| `fleeMusicVolume` | 0.3 | Flee music volume (0.0-1.0) |
| `victoryMusicPitch` | 1.0 | Victory music pitch (0.5-2.0) |
| `evolutionCongratMusicPitch` | 1.0 | Evolution congratulations pitch (0.5-2.0) |
| `catchCongratMusicPitch` | 1.0 | Catch congratulations pitch (0.5-2.0) |
| `fleeMusicPitch` | 1.0 | Flee music pitch (0.5-2.0) |
| `enableVictoryMusic` | true | Enable victory music |
| `enableEvolutionMusic` | true | Enable evolution congratulations |
| `enableCatchMusic` | true | Enable catch congratulations |
| `enableFleeMusic` | true | Enable flee music |

#### **Cobblemon Sound Control (Experimental)**
| Setting | Default | Description |
|---------|---------|-------------|
| `enableCobblemonSoundControl` | false | Enable control over all Cobblemon sounds |
| `cobblemonSoundsVolume` | 1.0 | Master volume for all Cobblemon sounds (0.0-1.0) |
| `cobblemonSoundsPitch` | 1.0 | Master pitch for all Cobblemon sounds (0.5-2.0) |
| `cobblemonPokemonCriesVolume` | 1.0 | Pokemon cry sounds volume (0.0-1.0) |
| `cobblemonPokemonCriesPitch` | 1.0 | Pokemon cry sounds pitch (0.5-2.0) |
| `cobblemonPokeballSoundsVolume` | 1.0 | Pokeball sounds volume (0.0-1.0) |
| `cobblemonPokeballSoundsPitch` | 1.0 | Pokeball sounds pitch (0.5-2.0) |
| `cobblemonBattleSoundsVolume` | 1.0 | Battle sounds volume (0.0-1.0) |
| `cobblemonBattleSoundsPitch` | 1.0 | Battle sounds pitch (0.5-2.0) |

#### **Advanced Settings**
| Setting | Default | Description |
|---------|---------|-------------|
| `debugLogging` | false | Enable debug logging |

## 🔧 **Troubleshooting**

### Music Not Playing
1. Check if the feature is enabled in config
2. Verify volume is not set to 0
3. Ensure sound files exist in resource pack
4. Check Minecraft's music volume settings
5. Enable debug logging and check console

### No Config GUI
- Install ModMenu and Cloth Config mods
- Check mods are compatible with your Minecraft version

### Evolution Sound Issues
- Evolution congratulations only play after evolution completes
- Uses Cobblemon's `EVOLUTION_COMPLETE` event

## 🎯 **Compatibility**

### ✅ Compatible With
- **Resource Packs** - Any battle music resource pack
- **Other Cobblemon Addons** - Non-conflicting music mods
- **Performance Mods** - Sodium, Lithium, etc.

### ❌ May Conflict With
- **Other Music Mods** - If they override same sound events
- **Sound Overhaul Mods** - May interfere with audio system

## 🔄 **Changelog**

### v1.1.1 (Latest)
#### 🎵 **Cobblemon Sound Control System**
- **NEW**: Experimental Cobblemon sound control system
- **NEW**: Volume and pitch control for ALL Cobblemon sounds
- **NEW**: Separate controls for Pokemon cries, Pokeball sounds, battle sounds
- **NEW**: Flee music support with volume/pitch controls
- **NEW**: Advanced ModMenu configuration with multiple categories
- **FIXED**: Removed problematic mixin system that caused crashes
- **IMPROVED**: Better command structure with `/tdsound` prefix
- **ENHANCED**: Comprehensive sound management framework

### v1.0.9
#### 🎮 **Command System Update**
- **CHANGED**: Commands from `/cobblecongrat` to `/tdsound`
- **UPDATED**: All help text and status messages
- **IMPROVED**: More intuitive command naming

### v1.0.1
- Victory music fade out implementation
- Simplified architecture for stability

### v1.0.0
- Initial release with all music types

## 🤝 **Contributing**

Issues and suggestions welcome! Visit our [GitHub repository](https://github.com/vryakafree/cbm).

## 📄 **License**

MIT License - See LICENSE file for details.

## 🙏 **Credits**

- **Cobblemon Team** - For the amazing Pokemon mod
- **Fabric Community** - For the modding framework
- **ModMenu & Cloth Config** - For configuration systems

---

**Enjoy your enhanced Cobblemon congratulatory experience!** 🎵✨