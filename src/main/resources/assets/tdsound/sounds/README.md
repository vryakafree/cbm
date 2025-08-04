# Sound Files for TDsound

## Required Sound Files

Place the following `.ogg` audio files in this directory:

### Battle Music (Looping)
- **`battle_song.ogg`** - Normal battle background music (loops continuously)
- **`strong_battle_song.ogg`** - Intense battle music for high-level opponents (loops continuously)  
- **`panic_song.ogg`** - Low health panic music when Pokemon < 20% HP (loops continuously)

### Event Music (One-time)
- **`victory.ogg`** - Victory celebration music (plays for 7 seconds, then fades out)
- **`evo.ogg`** - Pokemon evolution music (plays during evolution animation)
- **`evo_congrat.ogg`** - Evolution completion celebration (plays after evolution)
- **`catch_congrat.ogg`** - Pokemon capture success music

## Music Behavior

### Battle Flow:
1. **Battle Start**: `battle_song.ogg` or `strong_battle_song.ogg` (if opponent 15+ levels higher)
2. **Low Health**: Switch to `panic_song.ogg` when Pokemon health ≤ 20%
3. **Health Recovery**: Switch back to battle music when health ≥ 50%
4. **Battle End**: Fade out current music over 2.5 seconds
5. **Victory**: Play `victory.ogg` for 7 seconds, then fade out
6. **Fled/Fainted**: Just fade out, no victory music

### Pokemon Events:
- **Evolution**: Play `evo.ogg` → wait 3 seconds → play `evo_congrat.ogg`
- **Capture**: Play `catch_congrat.ogg` immediately after successful catch

## Technical Requirements

- **Format**: OGG Vorbis (.ogg files only)
- **Quality**: 44.1 kHz, 16-bit recommended
- **Looping**: Battle songs should loop seamlessly
- **Volume**: Pre-balanced in code, but can be adjusted via configuration

## Default Volumes
- Battle Song: 80%
- Panic Song: 90% 
- Strong Battle: 85%
- Victory: 100%
- Evolution: 70%
- Evolution Congrat: 80%
- Catch Congrat: 90%

## Testing

Use `/cobblecongrat` commands to test music:
- `/cobblecongrat test victory`
- `/cobblecongrat test evolution`
- `/cobblecongrat test catch`