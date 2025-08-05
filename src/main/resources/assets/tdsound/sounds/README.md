# Sound Files for Cobblemon Battle Music

## ⚠️ IMPORTANT: Sound Files Need to be Added

The current sound files in this directory are **placeholder files** (only 23 bytes each). This is why Cobblemon sounds are not working properly.

## Required Sound Files

Place the following `.ogg` audio files in this directory:

### Event Music (One-time) - CURRENTLY MISSING
- **`victory.ogg`** - Victory celebration music (plays for 7 seconds, then fades out)
- **`evo_congrat.ogg`** - Evolution completion celebration (plays after evolution)
- **`catch_congrat.ogg`** - Pokemon capture success music
- **`flee.ogg`** - Plays when you flee from battle

### Battle Music (Looping) - OPTIONAL
- **`battle_song.ogg`** - Normal battle background music (loops continuously)
- **`strong_battle_song.ogg`** - Intense battle music for high-level opponents (loops continuously)  
- **`panic_song.ogg`** - Low health panic music when Pokemon < 20% HP (loops continuously)

## How to Fix Missing Sounds

### Option 1: Download Sample Sounds
1. Download Pokemon-style congratulatory sounds (OGG format)
2. Rename them to match the expected filenames above
3. Replace the placeholder files in this directory

### Option 2: Create Your Own Sounds
1. Use an audio editor (Audacity, etc.)
2. Create short congratulatory sounds (2-5 seconds)
3. Export as OGG format
4. Place in this directory with the correct names

### Option 3: Use Online Sound Generators
1. Use online tools to generate simple beep/tone sounds
2. Export as OGG format
3. Place in this directory

## File Requirements
- **Format**: OGG (Vorbis codec)
- **Duration**: 2-10 seconds recommended
- **Sample Rate**: 44.1 kHz recommended
- **Bitrate**: 128-192 kbps recommended

## Music Behavior

### Battle Flow:
1. **Battle Start**: `battle_song.ogg` or `strong_battle_song.ogg` (if opponent 15+ levels higher)
2. **Low Health**: Switch to `panic_song.ogg` when Pokemon health ≤ 20%
3. **Health Recovery**: Switch back to battle music when health ≥ 50%
4. **Battle End**: Fade out current music over 2.5 seconds
5. **Victory**: Play `victory.ogg` for 7 seconds, then fade out
6. **Fled/Fainted**: Just fade out, no victory music

### Pokemon Events:
- **Evolution**: Play `evo_congrat.ogg` after evolution completes
- **Capture**: Play `catch_congrat.ogg` immediately after successful catch
- **Flee**: Play `flee.ogg` when player flees from battle

## Default Volumes
- Battle Song: 80%
- Panic Song: 90% 
- Strong Battle: 85%
- Victory: 100%
- Evolution Congrat: 80%
- Catch Congrat: 90%
- Flee: 85%

## Testing
After adding the sound files:
1. Build the mod: `./gradlew build`
2. Test in-game with commands:
   - `/tdsound test victory`
   - `/tdsound test evolution`
   - `/tdsound test catch`
   - `/tdsound test flee`

## Note
The Cobblemon sound control system (for controlling Pokeball sounds, Pokemon cries, etc.) is now properly integrated and should work once the basic sound files are added.