# 🎵 Custom Cobblemon Music Mod

> **Authentic Pokemon-style battle music system for Cobblemon**

Adds dynamic battle music that automatically plays during Cobblemon battles, Pokemon events, evolutions, and captures with intelligent health-based switching - just like in the original Pokemon games!

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-green?style=flat-square&logo=minecraft)](https://minecraft.net)
[![Cobblemon](https://img.shields.io/badge/Cobblemon-1.6.1+-blue?style=flat-square)](https://cobblemon.com)
[![Fabric](https://img.shields.io/badge/Fabric-0.16.9+-orange?style=flat-square&logo=fabric)](https://fabricmc.net)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](LICENSE)

## ✨ Features

### 🎼 Complete Pokemon Music System
- **Battle Music**: Automatically plays during Cobblemon battles (loops continuously)
- **Strong Battle Music**: Special music when facing opponents 15+ levels higher (configurable)
- **Panic Music**: Switches to intense music when Pokemon health ≤ 20% (configurable)
- **Victory Music**: Celebration music for 7 seconds after winning battles (configurable duration)
- **Evolution Music**: Two-part sequence for Pokemon evolution events
- **Catch Music**: Congratulations sound when successfully catching Pokemon

### 🎯 Smart Music Switching
- **Health-based**: Panic music at low health, recovery when health improves
- **Level-based**: Strong battle music for challenging opponents
- **Event-driven**: Real-time response to Cobblemon API events
- **Instant Response**: Music stops immediately when battles end (flee, faint, commands)
- **Smooth Fade-out**: Gradual fade when battles conclude

### ⚙️ Configuration System
- **ModMenu Integration**: Easy-to-use GUI configuration screen
- **Volume Controls**: Adjust volume for each music type independently
- **Feature Toggles**: Enable/disable specific music types
- **Advanced Settings**: Customize health thresholds, level differences, and timing
- **Auto-save**: Configuration automatically saves and loads

### 🛠️ Debug & Testing
- **Commands**: `/cobblemusic status`, `/cobblemusic test <type>`
- **API Detection**: Automatically detects Cobblemon installation
- **Status Reporting**: Comprehensive mod state information
- **Debug Logging**: Optional detailed logging for troubleshooting

## 📋 Requirements

| Component | Version | Required |
|-----------|---------|----------|
| **Minecraft** | 1.21.1 | ✅ Required |
| **Fabric Loader** | 0.16.9+ | ✅ Required |
| **Fabric API** | 0.110.0+1.21.1 | ✅ Required |
| **Cobblemon** | 1.6.1+ | 🔴 **MANDATORY** |
| **Java** | 21+ | ✅ Required |
| **ModMenu** | 11.0.1+ | 🟡 Optional (for config GUI) |
| **Cloth Config** | 15.0.127+ | 🟡 Optional (for config GUI) |

> ⚠️ **Important**: This mod **requires Cobblemon** to function. All events come from Cobblemon API.

## 🚀 Installation

1. **Download Dependencies**:
   - Install [Fabric Loader](https://fabricmc.net/use/installer/)
   - Download [Fabric API](https://modrinth.com/mod/fabric-api)
   - Download [Cobblemon](https://modrinth.com/mod/cobblemon) (1.6.1+)
   - Optional: [ModMenu](https://modrinth.com/mod/modmenu) + [Cloth Config](https://modrinth.com/mod/cloth-config) for configuration GUI

2. **Install Mod**:
   - Download latest release from [Releases](../../releases)
   - Place in `mods` folder
   - Launch Minecraft with Fabric profile

3. **Add Sound Files**:
   - Create Pokemon music files (`.ogg` format recommended)
   - Name them according to the [Sound Files](#sound-files) section
   - Place in mod's sound directory or use a resource pack

## 🎵 Sound Files

The mod expects these sound files in `.ogg` format:

| File Name | Triggers When | Description |
|-----------|---------------|-------------|
| `battle_song.ogg` | Normal battles | Main battle theme (loops) |
| `strong_battle_song.ogg` | Tough opponents | Intense battle theme (loops) |
| `panic_song.ogg` | Low health | Emergency theme (loops) |
| `victory.ogg` | Battle won | Victory fanfare |
| `evo.ogg` | Evolution starts | Evolution beginning |
| `evo_congrat.ogg` | Evolution complete | Evolution celebration |
| `catch_congrat.ogg` | Pokemon caught | Capture celebration |

### 📂 Sound File Locations
```
resourcepacks/YourPack/assets/customcobblemonmusicmod/sounds/
└── battle_song.ogg
└── strong_battle_song.ogg
└── panic_song.ogg
└── victory.ogg
└── evo.ogg
└── evo_congrat.ogg
└── catch_congrat.ogg
```

## ⚙️ Configuration

### Using ModMenu (Recommended)
1. Install [ModMenu](https://modrinth.com/mod/modmenu) and [Cloth Config](https://modrinth.com/mod/cloth-config)
2. Go to **Mods** → **Custom Cobblemon Music Mod** → **Config**
3. Adjust settings in the GUI:
   - **Volume Settings**: Control volume for each music type
   - **Music Features**: Enable/disable specific music types
   - **Advanced Settings**: Fine-tune thresholds and behavior

### Manual Configuration
The config file is located at: `config/customcobblemonmusicmod.json`

```json
{
  "battleMusicVolume": 0.8,
  "strongBattleMusicVolume": 0.85,
  "panicMusicVolume": 0.9,
  "victoryMusicVolume": 1.0,
  "evolutionMusicVolume": 0.7,
  "evolutionCongratMusicVolume": 0.8,
  "catchCongratMusicVolume": 0.9,
  "enableBattleMusic": true,
  "enableStrongBattleMusic": true,
  "enablePanicMusic": true,
  "enableVictoryMusic": true,
  "enableEvolutionMusic": true,
  "enableCatchMusic": true,
  "panicHealthThreshold": 0.2,
  "strongBattleLevelDifference": 15,
  "victoryMusicDuration": 7000,
  "immediateStopOnBattleEnd": true,
  "debugLogging": false
}
```

## 🎮 Usage

### Automatic Operation
The mod works automatically once installed:
- Music plays during any Cobblemon battle
- Switches to panic music when Pokemon health is low
- Plays victory music after winning
- Handles evolution and capture events

### Commands
```
/cobblemusic status          # Show mod status and configuration
/cobblemusic test battle     # Test battle music
/cobblemusic test panic      # Test panic music
/cobblemusic test victory    # Test victory music
/cobblemusic test evolution  # Test evolution music sequence
/cobblemusic test catch      # Test catch music
/cobblemusic stop           # Stop all music
/cobblemusic version        # Show mod version
```

## 🔧 Troubleshooting

### Common Issues

**Music not playing:**
- Check Cobblemon is installed (1.6.1+)
- Verify sound files are in correct location
- Run `/cobblemusic status` to check mod state
- Enable debug logging in config for detailed logs

**Wrong music volume:**
- Use ModMenu config GUI to adjust volumes
- Check Minecraft's music volume slider
- Verify sound file formats (`.ogg` recommended)

**Events not triggering:**
- Ensure Cobblemon API is working
- Check for mod conflicts
- Enable debug logging to see event detection

**Config not working:**
- Make sure ModMenu and Cloth Config are installed
- Check config file permissions
- Reset config by deleting the file (recreates with defaults)

### Debug Mode
Enable debug logging in the configuration for detailed information about:
- Event detection and processing
- Music state changes
- Health/level monitoring
- Error messages and API status

## 🔄 Changelog

### Latest Updates
- ✅ **Fixed music playback speed** - Music now plays at correct speed (was ~0.25x)
- ✅ **Added Pokemon health monitoring** - Panic music triggers when health ≤ configurable threshold
- ✅ **Implemented level detection** - Strong battle music for tough opponents
- ✅ **Fixed immediate battle end response** - Music fades out immediately when battles end
- ✅ **Improved evolution music timing** - Music starts when evolve button pressed
- ✅ **Added comprehensive configuration system** - ModMenu integration with GUI
- ✅ **Enhanced fade-out system** - Smooth transitions when music stops

[View Full Changelog](CHANGELOG.md)

## 🤝 Contributing

Contributions welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Test thoroughly with Cobblemon
4. Submit a pull request

## 📝 License

MIT License - See [LICENSE](LICENSE) for details.

## 🙏 Credits

- **Pokemon Company**: Original music inspiration
- **Cobblemon Team**: Amazing Pokemon mod for Minecraft
- **Fabric Team**: Modding framework
- **Community**: Bug reports and suggestions

---

**Enjoy authentic Pokemon battle music in your Cobblemon adventures! 🎵✨**