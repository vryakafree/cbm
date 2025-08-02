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
- **Strong Battle Music**: Special music when facing opponents 15+ levels higher
- **Panic Music**: Switches to intense music when Pokemon health ≤ 20%
- **Victory Music**: Celebration music for 7 seconds after winning battles
- **Evolution Music**: Two-part sequence for Pokemon evolution events
- **Catch Music**: Congratulations sound when successfully catching Pokemon

### 🎯 Smart Music Switching
- **Health-based**: Panic music at low health, recovery when health improves
- **Level-based**: Strong battle music for challenging opponents
- **Event-driven**: Real-time response to Cobblemon API events
- **Fade Effects**: Smooth transitions between different music types

### 🛠️ Debug & Testing
- **Commands**: `/cobblemusic status`, `/cobblemusic test <type>`
- **API Detection**: Automatically detects Cobblemon installation
- **Status Reporting**: Comprehensive mod state information
- **Error Handling**: Graceful fallback for API changes

## 📋 Requirements

| Component | Version | Required |
|-----------|---------|----------|
| **Minecraft** | 1.21.1 | ✅ Required |
| **Fabric Loader** | 0.16.9+ | ✅ Required |
| **Fabric API** | 0.110.0+1.21.1 | ✅ Required |
| **Cobblemon** | 1.6.1+ | 🔴 **MANDATORY** |
| **Java** | 21+ | ✅ Required |

> ⚠️ **Important**: This mod **requires Cobblemon** to function. All events come from Cobblemon API.

## 🚀 Installation

1. **Download Dependencies**:
   - Install [Fabric Loader](https://fabricmc.net/use/installer/)
   - Download [Fabric API](https://modrinth.com/mod/fabric-api)
   - Download [Cobblemon 1.6.1+](https://modrinth.com/mod/cobblemon)

2. **Install Mod**:
   - Download `customcobblemonmusicmod-1.0.0.jar` from [Releases](../../releases)
   - Place in your `mods` folder

3. **Optional**: Replace placeholder sounds with custom `.ogg` files

4. **Launch** and enjoy authentic Pokemon battle music!

## 🎛️ Sound Events

| Music Type | File | Trigger | Duration |
|------------|------|---------|----------|
| **Battle** | `battle_song.ogg` | Battle starts | Loops |
| **Strong Battle** | `strong_battle_song.ogg` | Opponent 15+ levels higher | Loops |
| **Panic** | `panic_song.ogg` | Pokemon health ≤ 20% | Loops |
| **Victory** | `victory.ogg` | Win battle | 7 seconds |
| **Evolution** | `evo.ogg` | Evolution starts | One-time |
| **Evolution Complete** | `evo_congrat.ogg` | Evolution finishes | One-time |
| **Catch Success** | `catch_congrat.ogg` | Pokemon caught | One-time |

## 📝 Commands

Test and debug the mod with these commands:

```
/cobblemusic status          # Check mod status and Cobblemon integration
/cobblemusic test battle     # Test battle music
/cobblemusic test panic      # Test panic music
/cobblemusic test victory    # Test victory music
/cobblemusic test evolution  # Test evolution sequence
/cobblemusic test catch      # Test catch congratulations
/cobblemusic stop            # Stop all music (debug)
/cobblemusic version         # Show mod version info
```

## 🔧 Development

### Building from Source

```bash
git clone https://github.com/vryakafree/cbm.git
cd cbm
./gradlew clean build
```

The built mod will be in `build/libs/customcobblemonmusicmod-1.0.0.jar`

### Development Environment

```bash
./gradlew genSources    # Generate mappings
./gradlew runClient     # Test in development environment
```

### Adding Custom Sounds

Replace placeholder files in `src/main/resources/assets/customcobblemonmusicmod/sounds/`:

- **Format**: OGG Vorbis (.ogg files only)
- **Quality**: 44.1 kHz, 16-bit recommended  
- **Looping**: Battle songs should loop seamlessly
- **Size**: Optimize for mod distribution

## ⚡ Architecture

- **Event-driven**: Responds to Cobblemon API events in real-time
- **Client-side**: Music management handled on client
- **Server-side**: Commands and debug system
- **Memory efficient**: Proper resource cleanup and management
- **Error resilient**: Graceful handling of API changes

## 🎨 Customization

### Icon
- **Current**: Placeholder ready for custom design
- **Specs**: 128x128 PNG with transparency
- **Theme**: Music + Pokemon elements recommended
- **Guide**: See `ICON_README.md` for design guidelines

### Volume Control
Default volumes can be adjusted in code:
- Battle: 80%, Panic: 90%, Victory: 100%
- Evolution: 70%, Catch: 90%

## 🐛 Known Issues

- Health monitoring may need updates for future Cobblemon versions
- Level detection simplified for API stability
- Sound files are currently placeholders (0 bytes)
- Icon awaiting custom design

## 🔮 Roadmap

- [ ] Advanced health monitoring with stable API
- [ ] GUI configuration panel
- [ ] Custom sound file integration
- [ ] Dynamic volume adjustment
- [ ] More battle music categories
- [ ] Biome-specific music themes

## 📜 Technical Details

- **Build**: Gradle 8.10.2 + Fabric Loom 1.7.4
- **Size**: 20KB (optimized)
- **Platform**: Fabric (Minecraft 1.21.1)
- **Language**: Java 21 + Kotlin compatibility
- **API**: Cobblemon Events API

## 🤝 Contributing

Contributions welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test with Cobblemon
5. Submit a pull request

## 📄 License

MIT License - see [LICENSE](LICENSE) for details.

## 🆘 Support

- **Issues**: [GitHub Issues](../../issues)
- **Discussions**: [GitHub Discussions](../../discussions)  
- **Testing**: Use `/cobblemusic status` for diagnostics

---

**🎯 Goal**: Bring authentic Pokemon battle music experience to Cobblemon!

*Built with ❤️ for the Cobblemon community*