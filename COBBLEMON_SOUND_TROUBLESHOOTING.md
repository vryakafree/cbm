# Cobblemon Sound Configuration Troubleshooting Guide

## Problem Description
The mod's Cobblemon sound configuration is not working as expected. This guide will help you diagnose and fix the issue.

## Quick Diagnosis Steps

### 1. Enable Debug Logging
1. In-game, run the command: `/tdsound debug`
2. This will enable detailed logging to help identify the issue
3. Check the Minecraft logs for detailed information

### 2. Test Sound Interception
1. In-game, run the command: `/tdsound test`
2. This will test various Cobblemon sound IDs and show if they're being recognized
3. Check the logs for test results

### 3. Check Current Configuration
1. In-game, run the command: `/tdsound config`
2. This will display your current configuration settings
3. Verify that `enableCobblemonSoundControl` is set to `true`

## Common Issues and Solutions

### Issue 1: Cobblemon Sounds Not Being Intercepted

**Symptoms:**
- Cobblemon sounds play normally without any volume/pitch modifications
- No debug messages in logs when Cobblemon sounds play

**Possible Causes:**
1. **Configuration Disabled**: `enableCobblemonSoundControl` is set to `false`
2. **Sound Recognition Failure**: The mod doesn't recognize the sound as a Cobblemon sound
3. **Mixin Not Loading**: The sound interception mixin isn't being applied

**Solutions:**
1. Check your config file (`config/tdsound.json`) and ensure `enableCobblemonSoundControl` is `true`
2. Enable debug logging and check if Cobblemon sounds are being recognized
3. Verify the mod is properly installed and loaded

### Issue 2: Sound Modification Not Working

**Symptoms:**
- Cobblemon sounds are being intercepted (visible in debug logs)
- But volume/pitch modifications aren't applied correctly

**Possible Causes:**
1. **Configuration Values**: Volume/pitch multipliers are set to 1.0 (no change)
2. **Sound Category Mismatch**: The sound isn't being categorized correctly
3. **Sound Creation Error**: Error creating the modified sound instance

**Solutions:**
1. Check your configuration values - they should be different from 1.0 to see changes
2. Enable debug logging to see which category the sound is being assigned to
3. Check logs for any error messages during sound creation

### Issue 3: Specific Sound Types Not Working

**Symptoms:**
- Some Cobblemon sounds work (e.g., Pokemon cries) but others don't (e.g., move sounds)

**Possible Causes:**
1. **Category Recognition**: The sound path doesn't match the expected patterns
2. **Configuration Missing**: Specific category settings aren't configured

**Solutions:**
1. Enable debug logging to see how sounds are being categorized
2. Check if the sound path matches the expected patterns in the code
3. Verify that the specific category settings are properly configured

## Configuration Reference

### Main Settings
```json
{
  "enableCobblemonSoundControl": true,
  "debugLogging": true,
  "cobblemonSoundsVolume": 1.0,
  "cobblemonSoundsPitch": 1.0
}
```

### Category-Specific Settings
```json
{
  "cobblemonPokemonCriesVolume": 1.0,
  "cobblemonPokemonCriesPitch": 1.0,
  "cobblemonPokeballSoundsVolume": 1.0,
  "cobblemonPokeballSoundsPitch": 1.0,
  "cobblemonMoveSoundsVolume": 1.0,
  "cobblemonMoveSoundsPitch": 1.0,
  "cobblemonImpactSoundsVolume": 1.0,
  "cobblemonImpactSoundsPitch": 1.0,
  "cobblemonEvolutionSoundsVolume": 1.0,
  "cobblemonEvolutionSoundsPitch": 1.0,
  "cobblemonBattleSoundsVolume": 1.0,
  "cobblemonBattleSoundsPitch": 1.0,
  "cobblemonPCSoundsVolume": 1.0,
  "cobblemonPCSoundsPitch": 1.0,
  "cobblemonUISoundsVolume": 1.0,
  "cobblemonUISoundsPitch": 1.0,
  "cobblemonItemSoundsVolume": 1.0,
  "cobblemonItemSoundsPitch": 1.0,
  "cobblemonBlockSoundsVolume": 1.0,
  "cobblemonBlockSoundsPitch": 1.0
}
```

## Sound Categories

The mod categorizes Cobblemon sounds based on their path:

- **Pokemon Cries**: `pokemon.*.cry` (e.g., `pokemon.bulbasaur.cry`)
- **Pokeball Sounds**: `poke_ball.*` or `pokeball.*` (e.g., `poke_ball.capture_succeeded`)
- **Move Sounds**: `move.*` (e.g., `move.tackle`)
- **Impact Sounds**: `impact.*` (e.g., `impact.tackle`)
- **Evolution Sounds**: `evolution.*` (e.g., `evolution.full`)
- **Battle Sounds**: `battle.*`
- **PC Sounds**: `pc.*` (e.g., `pc.on`, `pc.off`)
- **UI Sounds**: `ui.*` (e.g., `ui.menu`)
- **Item Sounds**: `item.*`
- **Block Sounds**: `block.*`
- **Nurse Sounds**: `entity/villager/work_nurse`
- **General Sounds**: Everything else

## Testing Steps

1. **Enable Debug Logging**: `/tdsound debug`
2. **Test Sound Recognition**: `/tdsound test`
3. **Check Configuration**: `/tdsound config`
4. **Trigger Cobblemon Sounds**: 
   - Spawn a Pokemon to hear cries
   - Use a Pokeball to hear capture sounds
   - Use moves in battle to hear move sounds
   - Check logs for debug messages

## Log Analysis

When debug logging is enabled, look for these messages:

```
=== SOUND INTERCEPTION DEBUG ===
Sound played: cobblemon:pokemon.bulbasaur.cry
Namespace: cobblemon
Path: pokemon.bulbasaur.cry
Cobblemon sound control enabled: true
Is Cobblemon sound: true
================================

*** INTERCEPTING COBBLEMON SOUND ***
Sound ID: cobblemon:pokemon.bulbasaur.cry
Original volume: 1.0, pitch: 1.0
Category: pokemon_cries
*******************************
```

## Common Sound IDs from Cobblemon

Based on the provided `sounds.json`, here are some common Cobblemon sound IDs:

- `cobblemon:pokemon.bulbasaur.cry`
- `cobblemon:pokemon.pikachu.cry`
- `cobblemon:move.tackle`
- `cobblemon:impact.tackle`
- `cobblemon:evolution.full`
- `cobblemon:poke_ball.capture_succeeded`
- `cobblemon:pc.on`
- `cobblemon:ui.menu`

## If Nothing Works

1. **Check Mod Compatibility**: Ensure you're using compatible versions of:
   - Minecraft (1.21.1+)
   - Fabric Loader (0.16.0+)
   - Fabric API
   - Cobblemon (1.6.0+)

2. **Check Installation**: Verify the mod is properly installed in your mods folder

3. **Check for Conflicts**: Other mods might interfere with sound processing

4. **Report Issue**: If the problem persists, report it with:
   - Minecraft version
   - Mod versions
   - Debug logs
   - Configuration file contents