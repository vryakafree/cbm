# Hướng dẫn kích hoạt Cobblemon Integration

## Hiện tại


Mod đã được cập nhật cho **Minecraft 1.21.1** và **Cobblemon 1.6.1**. Hiện tại mod có thể build thành công với cấu hình cơ bản, tuy nhiên do một số vấn đề repository network, Cobblemon integration tạm thời được comment out. Để mod hoạt động đầy đủ với Cobblemon, bạn cần thực hiện các bước sau:


## Bước 1: Kích hoạt Cobblemon dependency

Trong file `build.gradle`, uncomment dòng Cobblemon dependency:

```gradle
// Tìm dòng này:
// modImplementation "com.cobblemon:fabric:${project.cobblemon_version}"

// Và thay thế bằng:
modImplementation "com.cobblemon:fabric:${project.cobblemon_version}"
```


**Lưu ý về Repository**: Nếu gặp lỗi network với nexus.impactdev.net, hãy thử:
1. Sử dụng VPN hoặc thay đổi DNS
2. Hoặc download Cobblemon JAR trực tiếp và đặt vào local libs folder


## Bước 2: Kích hoạt imports trong CobblemonBattleMusicClient.java

Trong file `src/main/java/com/cbm/cobblemonbattlemusic/CobblemonBattleMusicClient.java`:

### 2.1: Uncomment các imports
```java
// Thay thế:
// import com.cobblemon.mod.common.api.events.CobblemonEvents;
// import com.cobblemon.mod.common.battles.BattleRegistry;
// import com.cobblemon.mod.common.api.Priority;
// import kotlin.Unit;

// Bằng:
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.cobblemon.mod.common.api.Priority;
import kotlin.Unit;
```

### 2.2: Kích hoạt client imports
```java
// Thay thế:
// import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
// import net.minecraft.client.MinecraftClient;
// import net.minecraft.client.sound.PositionedSoundInstance;
// import net.minecraft.sound.SoundCategory;

// Bằng:
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundCategory;
```

**Quan trọng**: Minecraft 1.21.1 đã thay đổi một số API. Nếu gặp lỗi compilation, hãy kiểm tra:
- `Identifier.of()` thay vì `new Identifier()`
- Mappings phù hợp với yarn build.1 hoặc mới hơn

## Bước 3: Kích hoạt event registration

Trong method `registerBattleEvents()`, uncomment tất cả code bên trong:

```java
private void registerBattleEvents() {
    // Listen for battle start events
    CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, battleStartEvent -> {
        onBattleStart();
        return Unit.INSTANCE;
    });
    
    // Listen for battle end events  
    CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
        onBattleVictory();
        return Unit.INSTANCE;
    });
    
    CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, battleFledEvent -> {
        onBattleEnd();
        return Unit.INSTANCE;
    });
    
    CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, battleFaintedEvent -> {
        onBattleEnd();
        return Unit.INSTANCE;
    });
}
```

## Bước 4: Kích hoạt tick events và battle state checking

Trong method `onInitializeClient()`:
```java
// Uncomment:
ClientTickEvents.END_CLIENT_TICK.register(client -> {
    checkBattleState();
});
```

Trong method `checkBattleState()`, uncomment toàn bộ implementation.

## Bước 5: Kích hoạt music playing methods

Uncomment toàn bộ implementation trong các methods:
- `playBattleMusic()`
- `playLowHealthMusic()`  
- `playVictoryMusic()`
- `stopBattleMusic()`

## Bước 6: Thêm file âm thanh thực tế

Thay thế các file âm thanh trống trong `assets/cobblemonbattlemusic/sounds/` bằng các file .ogg thực tế:
- `battle_music.ogg` - Nhạc nền chiến đấu
- `low_health_music.ogg` - Nhạc khi máu thấp
- `victory_music.ogg` - Nhạc chiến thắng

## Bước 7: Build và test

1. Build mod:
   ```bash
   ./gradlew clean build
   ```

2. File mod sẽ được tạo trong `build/libs/cobblemonbattlemusic-1.0.0.jar`

3. Đặt file vào thư mục mods với Cobblemon và test trong game

## Lưu ý

- Đảm bảo version Cobblemon trong `gradle.properties` phù hợp với version bạn đang sử dụng
- Nếu Cobblemon API thay đổi, có thể cần cập nhật event listeners
- Test kỹ trong development environment trước khi sử dụng trong production

## Troubleshooting

- Nếu build fail, kiểm tra version compatibility
- Nếu events không hoạt động, kiểm tra Cobblemon documentation cho API updates
- Nếu âm thanh không phát, kiểm tra file paths và format (.ogg)