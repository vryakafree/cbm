# Cobblemon Sound Configuration Troubleshooting Guide

## Issue: "The config for cobblemon sound is not working"

If you're experiencing issues with Cobblemon sound configuration not working, follow this troubleshooting guide.

## Quick Diagnosis Steps

### 1. Enable Debug Logging
First, enable debug logging to see what's happening:

1. Open your Minecraft game
2. Go to Mods → TDsound → Config
3. Set `debugLogging` to `true`
4. Save the config
5. Restart Minecraft

### 2. Check the Logs
After enabling debug logging, check your Minecraft logs for:
- `Sound played: cobblemon:...` - Shows all sounds being played
- `Intercepting Cobblemon sound: ...` - Shows which sounds are being intercepted
- `Creating modified Cobblemon sound: ...` - Shows volume/pitch modifications

## Common Issues and Solutions

### Issue 1: No Cobblemon Sounds Being Intercepted

**Symptoms:**
- Debug logs show no "Intercepting Cobblemon sound" messages
- Cobblemon sounds play normally without modification

**Possible Causes:**
1. **Cobblemon Sound Control Disabled**
   - Check if `enableCobblemonSoundControl` is set to `true` in config
   
2. **Sound Namespace Issues**
   - Ensure Cobblemon sounds use the `cobblemon` namespace
   - Check debug logs for sound namespace information

3. **Mixin Not Loading**
   - Verify the mod is properly installed
   - Check for any crash logs or errors

**Solutions:**
1. Enable `enableCobblemonSoundControl` in config
2. Restart Minecraft after changing config
3. Check mod compatibility with your Cobblemon version

### Issue 2: Sounds Intercepted But No Volume/Pitch Changes

**Symptoms:**
- Debug logs show "Intercepting Cobblemon sound" but no volume/pitch changes
- Sounds still play at original volume/pitch

**Possible Causes:**
1. **Configuration Values Set to Default**
   - All volume/pitch values are set to 1.0 (no change)
   
2. **Wrong Sound Category Mapping**
   - Sound not being categorized correctly
   - Check debug logs for sound category information

**Solutions:**
1. Adjust volume/pitch values in config:
   - Set values below 1.0 to reduce volume/pitch
   - Set values above 1.0 to increase volume/pitch
   
2. Check sound categorization:
   - Pokemon cries: `cobblemonPokemonCriesVolume/Pitch`
   - Move sounds: `cobblemonMoveSoundsVolume/Pitch`
   - Impact sounds: `cobblemonImpactSoundsVolume/Pitch`
   - Evolution sounds: `cobblemonEvolutionSoundsVolume/Pitch`
   - Pokeball sounds: `cobblemonPokeballSoundsVolume/Pitch`
   - Battle sounds: `cobblemonBattleSoundsVolume/Pitch`
   - PC sounds: `cobblemonPCSoundsVolume/Pitch`
   - UI sounds: `cobblemonUISoundsVolume/Pitch`
   - Item sounds: `cobblemonItemSoundsVolume/Pitch`
   - Block sounds: `cobblemonBlockSoundsVolume/Pitch`

### Issue 3: Only Some Cobblemon Sounds Are Affected

**Symptoms:**
- Some Cobblemon sounds are modified, others are not
- Inconsistent behavior across different sound types

**Possible Causes:**
1. **Sound Pattern Matching Issues**
   - Some sound paths don't match the expected patterns
   
2. **Missing Sound Categories**
   - New sound types not covered by existing categories

**Solutions:**
1. Check debug logs to see which sounds are being intercepted
2. Verify sound path patterns in logs
3. Report missing sound categories for future updates

## Sound Categories Reference

Based on Cobblemon's `sounds.json`, the mod categorizes sounds as follows:

### Pokemon Cries
- Pattern: `pokemon.*.cry`
- Examples: `cobblemon:pokemon.bulbasaur.cry`, `cobblemon:pokemon.pikachu.cry`
- Config: `cobblemonPokemonCriesVolume/Pitch`

### Move Sounds
- Pattern: `move.*`
- Examples: `cobblemon:move.tackle`, `cobblemon:move.thunder_shock`
- Config: `cobblemonMoveSoundsVolume/Pitch`

### Impact Sounds
- Pattern: `impact.*`
- Examples: `cobblemon:impact.tackle`, `cobblemon:impact.thunder_shock`
- Config: `cobblemonImpactSoundsVolume/Pitch`

### Evolution Sounds
- Pattern: `evolution.*`
- Examples: `cobblemon:evolution.full`, `cobblemon:evolution.start`
- Config: `cobblemonEvolutionSoundsVolume/Pitch`

### Pokeball Sounds
- Pattern: `poke_ball.*` or `pokeball.*`
- Examples: `cobblemon:poke_ball.capture_succeeded`, `cobblemon:poke_ball.shake`
- Config: `cobblemonPokeballSoundsVolume/Pitch`

### Battle Sounds
- Pattern: `battle.*`
- Examples: `cobblemon:battle.start`, `cobblemon:battle.end`
- Config: `cobblemonBattleSoundsVolume/Pitch`

### PC Sounds
- Pattern: `pc.*`
- Examples: `cobblemon:pc.on`, `cobblemon:pc.off`
- Config: `cobblemonPCSoundsVolume/Pitch`

### UI Sounds
- Pattern: `ui.*`
- Examples: `cobblemon:ui.menu_open`, `cobblemon:ui.button_click`
- Config: `cobblemonUISoundsVolume/Pitch`

### Item Sounds
- Pattern: `item.*`
- Examples: `cobblemon:item.potion`, `cobblemon:item.berry`
- Config: `cobblemonItemSoundsVolume/Pitch`

### Block Sounds
- Pattern: `block.*`
- Examples: `cobblemon:block.healing_machine`
- Config: `cobblemonBlockSoundsVolume/Pitch`

### General Sounds (Fallback)
- Any Cobblemon sound not matching above patterns
- Config: `cobblemonSoundsVolume/Pitch`

## Testing Your Configuration

### Test 1: Pokemon Cries
1. Set `cobblemonPokemonCriesVolume` to `0.5` (50% volume)
2. Set `cobblemonPokemonCriesPitch` to `1.5` (50% higher pitch)
3. Spawn a Pokemon and make it cry
4. Verify the sound is quieter and higher pitched

### Test 2: Move Sounds
1. Set `cobblemonMoveSoundsVolume` to `0.3` (30% volume)
2. Use a Pokemon move in battle
3. Verify the move sound is much quieter

### Test 3: Pokeball Sounds
1. Set `cobblemonPokeballSoundsVolume` to `0.0` (mute)
2. Try to catch a Pokemon
3. Verify no pokeball sounds are heard

## Advanced Debugging

### Enable Verbose Logging
If standard debug logging isn't enough, you can temporarily modify the mod to add more detailed logging:

1. Look for `CustomCobblemonMusicMod.LOGGER.info()` calls in the code
2. Add additional logging statements as needed
3. Rebuild the mod

### Check Sound Registration
Verify that Cobblemon sounds are properly registered:
1. Check if Cobblemon mod is loaded correctly
2. Verify sound files exist in Cobblemon's assets
3. Check for any sound loading errors in logs

## Version Compatibility

- **Cobblemon 1.6.1+**: Fully supported
- **Minecraft 1.21.1+**: Required
- **Fabric Loader 0.16.0+**: Required

## Getting Help

If you're still experiencing issues:

1. **Collect Information:**
   - Minecraft version
   - Cobblemon version
   - TDsound version
   - Debug logs with `debugLogging = true`
   - Any error messages or crash logs

2. **Report the Issue:**
   - Include all collected information
   - Describe the expected vs actual behavior
   - Provide steps to reproduce the issue

3. **Check for Updates:**
   - Ensure you're using the latest version of both Cobblemon and TDsound
   - Check for any known compatibility issues

## Configuration File Location

The configuration file is located at:
- **Windows:** `%APPDATA%/.minecraft/config/tdsound.json`
- **macOS:** `~/Library/Application Support/minecraft/config/tdsound.json`
- **Linux:** `~/.minecraft/config/tdsound.json`

You can edit this file directly if the in-game config doesn't work properly.