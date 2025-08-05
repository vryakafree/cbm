# Cobblemon Sound Control Troubleshooting Guide

## Issue: Cobblemon sounds are not working

### Root Cause Analysis

The main issues with Cobblemon sound control are:

1. **Corrupted Sound Files**: The basic mod sounds (victory, evolution, catch, flee) are corrupted (23 bytes each)
2. **Missing Integration**: The Cobblemon sound control system wasn't properly integrated with Minecraft's sound system
3. **Configuration Issues**: The sound control system is disabled by default for safety

### What I've Fixed

✅ **Created proper Cobblemon sounds.json** - Now the mod properly recognizes Cobblemon sound events
✅ **Added Mixin integration** - The mod now intercepts Cobblemon sounds at the Minecraft level
✅ **Improved sound interceptor** - Better volume/pitch control for different sound categories
✅ **Enhanced testing commands** - Better feedback and testing capabilities
✅ **Added comprehensive documentation** - Clear instructions for fixing sound files

### How to Fix Your Sound Issues

#### Step 1: Add the Basic Sound Files

The sound files in `src/main/resources/assets/tdsound/sounds/` are missing. You need to add them:

```bash
# Navigate to the sounds directory
cd src/main/resources/assets/tdsound/sounds/

# Add these OGG files:
# - victory.ogg
# - evo_congrat.ogg  
# - catch_congrat.ogg
# - flee.ogg
```

**Options for getting sound files:**
- Download Pokemon-style sounds from free sound libraries
- Create your own using audio editors like Audacity
- Use online sound generators

#### Step 2: Enable Cobblemon Sound Control

The system is disabled by default. Enable it:

**In-game:**
```
/tdsound cobblemon toggle
```

**Via ModMenu:**
1. Open ModMenu
2. Go to TDsound → Cobblemon Sound Control
3. Enable "Enable Cobblemon Sound Control"

#### Step 3: Test the System

Test the basic sounds:
```
/tdsound test victory
/tdsound test evolution
/tdsound test catch
/tdsound test flee
```

Test Cobblemon sound control:
```
/tdsound cobblemon test pokeball
/tdsound cobblemon test cry
/tdsound cobblemon status
```

#### Step 4: Build and Test

```bash
./gradlew build
```

### Configuration Options

#### Basic Sound Settings (ModMenu → TDsound → General)
- **Victory Music**: Enable/disable + volume/pitch control
- **Evolution Congrat**: Enable/disable + volume/pitch control  
- **Catch Congrat**: Enable/disable + volume/pitch control
- **Flee Music**: Enable/disable + volume/pitch control

#### Cobblemon Sound Control (ModMenu → TDsound → Cobblemon Sound Control)
- **Enable Cobblemon Sound Control**: Master switch
- **Master Volume/Pitch**: Global settings for all Cobblemon sounds
- **Pokemon Cries Volume/Pitch**: Control Pokemon cry sounds
- **Pokeball Sounds Volume/Pitch**: Control Pokeball-related sounds
- **Battle Sounds Volume/Pitch**: Control battle-related sounds

### Sound Categories

The mod categorizes Cobblemon sounds as follows:

- **Pokemon Cries**: Sounds containing "pokemon" and "cry" in the path
- **Pokeball Sounds**: Sounds containing "poke_ball" or "pokeball" in the path
- **Battle Sounds**: Sounds containing "battle" or "fight" in the path
- **General Sounds**: All other Cobblemon sounds

### Debug Information

Enable debug logging in ModMenu to see detailed information about sound processing:

```
ModMenu → TDsound → General → Debug Logging
```

This will show:
- When sounds are intercepted
- Original vs modified volume/pitch values
- Sound categorization

### Common Issues and Solutions

#### Issue: "No sound plays when I test"
**Solution**: Check that sound files are properly formatted OGG files (not corrupted)

#### Issue: "Cobblemon sounds aren't being modified"
**Solution**: 
1. Enable Cobblemon sound control: `/tdsound cobblemon toggle`
2. Check that the sound control is enabled in ModMenu
3. Verify the mod is properly loaded

#### Issue: "Sounds are too loud/quiet"
**Solution**: Adjust volume settings in ModMenu → TDsound → Cobblemon Sound Control

#### Issue: "Sounds have wrong pitch"
**Solution**: Adjust pitch settings in ModMenu → TDsound → Cobblemon Sound Control

### Technical Details

The mod uses:
- **Mixin system** to intercept Minecraft's sound manager
- **Sound event categorization** to apply different settings to different sound types
- **Volume/pitch multiplication** to modify sounds without replacing them
- **Cobblemon event system** for triggering custom congratulatory sounds

### Support

If you're still having issues:
1. Check the debug logs (enable debug logging)
2. Verify all sound files are valid OGG format
3. Ensure Cobblemon 1.6.1+ is installed
4. Check that the mod is properly loaded in your mod list