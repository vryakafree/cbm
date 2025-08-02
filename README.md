# Custom Cobblemon Music Mod

A lightweight Fabric mod that adds **Victory**, **Evolution**, and **Catch** music to your Cobblemon experience. Battle music is handled by Cobblemon's native system or compatible resource packs.

## ✨ Features

### 🎵 **Music Types**
- **🏆 Victory Music** - Plays immediately after winning battles with smooth fade out
- **🔄 Evolution Music** - Plays alongside Cobblemon's evolution_full.ogg during evolution
- **🎉 Evolution Congratulations** - Plays when evolution completes
- **⭐ Catch Congratulations** - Plays when you successfully catch Pokemon

### ⚙️ **Configuration System**
- **ModMenu Integration**: Full GUI configuration through ModMenu
- **Volume Controls**: Individual volume settings for each music type (0-100%)
- **Feature Toggles**: Enable/disable specific music types
- **Advanced Settings**: Victory duration, fade out duration, debug logging
- **Auto-save Configuration**: Settings automatically save and load

### 🎮 **Commands**
- `/cobblemusic status` - Show mod status and enabled features
- `/cobblemusic test victory` - Test victory music
- `/cobblemusic test evolution` - Test evolution music  
- `/cobblemusic test evo_congrat` - Test evolution congratulations
- `/cobblemusic test catch` - Test catch music
- `/cobblemusic stop` - Stop all custom music
- `/cobblemusic config` - Show current configuration

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

## 🎵 **Adding Music Files**

Create a resource pack with your custom music:

### Resource Pack Structure
```
your_resource_pack/
├── pack.mcmeta
└── assets/
    └── customcobblemonmusicmod/
        └── sounds/
            ├── victory.ogg
            ├── evo.ogg
            ├── evo_congrat.ogg
            └── catch_congrat.ogg
```

### pack.mcmeta
```json
{
  "pack": {
    "pack_format": 34,
    "description": "Custom Cobblemon Music"
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
2. Go to **Mods** → **Custom Cobblemon Music Mod** → **Config**
3. Adjust settings in the GUI
4. Settings save automatically

### Manual Configuration
Edit `config/customcobblemonmusicmod.json`:

```json
{
  "victoryMusicVolume": 1.0,
  "evolutionMusicVolume": 0.7,
  "evolutionCongratMusicVolume": 0.8,
  "catchCongratMusicVolume": 0.9,
  "enableVictoryMusic": true,
  "enableEvolutionMusic": true,
  "enableCatchMusic": true,
  "victoryMusicDuration": 7000,
  "debugLogging": false
}
```

### Configuration Options

| Setting | Default | Description |
|---------|---------|-------------|
| `victoryMusicVolume` | 1.0 | Victory music volume (0.0-1.0) |
| `evolutionMusicVolume` | 0.7 | Evolution music volume (0.0-1.0) |
| `evolutionCongratMusicVolume` | 0.8 | Evolution congratulations volume (0.0-1.0) |
| `catchCongratMusicVolume` | 0.9 | Catch congratulations volume (0.0-1.0) |
| `enableVictoryMusic` | true | Enable victory music |
| `enableEvolutionMusic` | true | Enable evolution music |
| `enableCatchMusic` | true | Enable catch music |
| `victoryMusicDuration` | 7000 | Victory music duration (milliseconds) |
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

### Evolution Music Issues
- Evolution music timing depends on Cobblemon's events
- Only `evo_congrat` plays after evolution completes
- Custom `evo` music requires manual implementation

## 🎯 **Compatibility**

### ✅ Compatible With
- **Resource Packs** - Any battle music resource pack
- **Other Cobblemon Addons** - Non-conflicting music mods
- **Performance Mods** - Sodium, Lithium, etc.

### ❌ May Conflict With
- **Other Music Mods** - If they override same sound events
- **Sound Overhaul Mods** - May interfere with audio system

## 🔄 **Changelog**

### v1.0.2 (Latest)
#### 🔧 **Simplified & Optimized**
- **Removed**: Battle music system (use resource packs instead)
- **Fixed**: Victory music now plays immediately after battle win
- **Fixed**: Evolution music timing improved
- **Optimized**: Cleaner code, better performance
- **Updated**: Configuration GUI simplified

### v1.0.1
- Major overhaul with comprehensive features
- Full battle tracking system
- ModMenu integration

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

**Enjoy your enhanced Cobblemon musical experience!** 🎵✨