# Ghi chú cập nhật Minecraft 1.21.1

## ✅ Những gì đã cập nhật

### Versions
- **Minecraft**: 1.20.1 → 1.21.1
- **Cobblemon**: 1.4.1 → 1.6.1
- **Fabric Loader**: 0.15.11 → 0.16.9
- **Fabric API**: 0.92.2 → 0.110.0
- **Java**: 17 → 21
- **Yarn Mappings**: 1.20.1+build.9 → 1.21.1+build.1
- **Fabric Loom**: 1.5-SNAPSHOT → 1.7-SNAPSHOT
- **Gradle**: 8.4 → 8.10.2

### API Changes
1. **Identifier Constructor**: 
   - Cũ: `new Identifier(namespace, path)`
   - Mới: `Identifier.of(namespace, path)`

2. **Dependencies trong fabric.mod.json**:
   - `"fabric": "*"` → `"fabric-api": "*"`
   - Java requirement: `">=17"` → `">=21"`

### Repository Configuration
- Đã sắp xếp lại thứ tự repositories để ưu tiên Fabric Maven
- Thêm mavenCentral() cho better resolution

## 🔄 Trạng thái hiện tại

### ✅ Đã hoạt động
- Build cơ bản thành công với Minecraft 1.21.1
- Sound event registration
- Fabric mod infrastructure
- Main mod class

### ⏳ Tạm thời bị comment out
- Cobblemon dependency (do network issues với nexus.impactdev.net)
- Client-side imports và functionality
- Battle event listeners

## 🛠 Cần làm để hoàn thiện

1. **Giải quyết Cobblemon dependency**:
   - Fix network connectivity với ImpactDev repositories
   - Hoặc sử dụng local JAR files

2. **Kích hoạt client functionality**:
   - Uncomment client imports
   - Test client-side sound playing
   - Verify event listeners

3. **Test với Cobblemon thực tế**:
   - Battle start/end events
   - Sound playback
   - Low health detection

## 🚨 Lưu ý quan trọng

- **Java 21**: Minecraft 1.21.1 yêu cầu Java 21, không còn hỗ trợ Java 17
- **API Changes**: Một số Minecraft/Fabric APIs đã thay đổi, cần update code accordingly
- **Repository Issues**: ImpactDev repositories có thể không stable, cần backup plan
- **Cobblemon 1.6.1**: API có thể đã thay đổi từ 1.4.1, cần kiểm tra compatibility

## 📦 Build Results

Hiện tại có thể build thành công:
```bash
./gradlew clean build --no-daemon
# → cobblemonbattlemusic-1.0.0.jar
```

File JAR được tạo nhưng chưa có đầy đủ functionality cho đến khi Cobblemon integration được kích hoạt.