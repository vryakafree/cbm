# 🎵 Custom Cobblemon Music Mod - Resource Pack

This resource pack allows you to add custom music files to the Custom Cobblemon Music Mod.

## 📁 Structure

```
resourcepack/
├── pack.mcmeta                    # Resource pack manifest
├── README.md                      # This file
└── assets/
    └── customcobblemonmusicmod/
        ├── sounds.json            # Sound definitions
        └── sounds/               # Your custom music files go here
            ├── battle_song.ogg
            ├── strong_battle_song.ogg
            ├── panic_song.ogg
            ├── victory.ogg
            ├── evo.ogg
            ├── evo_congrat.ogg
            └── catch_congrat.ogg
```

## 🎵 Music Files

Replace the placeholder files with your custom music:

| File | Description | Format | Duration |
|------|-------------|--------|----------|
| `battle_song.ogg` | Normal battle music | OGG | Looping |
| `strong_battle_song.ogg` | Intense battle music | OGG | Looping |
| `panic_song.ogg` | Low health music | OGG | Looping |
| `victory.ogg` | Victory celebration | OGG | 7 seconds |
| `evo.ogg` | Evolution music | OGG | One-time |
| `evo_congrat.ogg` | Evolution complete | OGG | One-time |
| `catch_congrat.ogg` | Pokemon caught | OGG | One-time |

## 🎛️ Audio Requirements

- **Format**: OGG Vorbis (.ogg files only)
- **Sample Rate**: 44.1 kHz recommended
- **Bit Depth**: 16-bit recommended
- **Channels**: Stereo or Mono
- **Quality**: Optimize for file size while maintaining quality

## 🚀 Installation

1. **Download** this resource pack
2. **Add your music files** to the `sounds/` directory
3. **Zip the resourcepack folder** (not the contents, the folder itself)
4. **Rename** to `customcobblemonmusicmod-resourcepack.zip`
5. **Place in** your Minecraft `resourcepacks` folder
6. **Enable** in Minecraft resource pack settings

## ⚙️ Configuration

The `sounds.json` file contains volume and playback settings:

- **Volume**: 0.0 to 1.0 (0.8 = 80% volume)
- **Pitch**: 1.0 = normal speed
- **Stream**: `true` for long files, `false` for short sounds
- **Weight**: Sound selection weight (usually 1)

## 🎯 Tips

- **Looping songs**: Make sure battle songs loop seamlessly
- **File size**: Keep files under 10MB for best performance
- **Testing**: Use `/cobblemusic test <type>` to test your sounds
- **Backup**: Keep original files before replacing

## 🔧 Troubleshooting

- **No sound**: Check file format is .ogg
- **Wrong volume**: Adjust volume in sounds.json
- **Not playing**: Ensure mod is installed and enabled
- **File too large**: Compress audio or reduce quality

---

**Note**: This resource pack works with Custom Cobblemon Music Mod v1.0.0+