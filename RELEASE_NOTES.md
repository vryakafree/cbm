# Cobblemon Battle Music v1.0.0 - Optimized Release

## 🎵 Tính năng chính

### Hệ thống âm nhạc Pokemon hoàn chỉnh
- **Battle Music**: Tự động phát nhạc khi bắt đầu trận đấu (loops)
- **Strong Battle Music**: Nhạc đặc biệt khi đối thủ cao hơn 15 level
- **Panic Music**: Chuyển sang nhạc căng thẳng khi Pokemon < 20% máu
- **Victory Music**: Nhạc chiến thắng phát 7 giây rồi fade out
- **Evolution Music**: Sequence âm thanh cho tiến hóa Pokemon
- **Catch Music**: Nhạc chúc mừng khi bắt Pokemon thành công

### Tích hợp Cobblemon API
- **Event-driven Architecture**: Dựa trên Cobblemon events
- **Real-time Monitoring**: Theo dõi trạng thái battle và Pokemon
- **Smart Music Switching**: Chuyển nhạc dựa theo tình huống
- **Error Handling**: Graceful fallback khi API thay đổi

### Debug & Testing System
- **Commands**: `/cobblemusic status`, `/cobblemusic test <type>`
- **API Detection**: Tự động phát hiện Cobblemon có sẵn
- **Status Reporting**: Thông tin chi tiết về mod state

## 📋 Yêu cầu

- **Minecraft**: 1.21.1
- **Fabric Loader**: 0.16.9+
- **Fabric API**: 0.110.0+1.21.1
- **Cobblemon**: 1.6.1+ (BẮT BUỘC)
- **Java**: 21+

## 🎛️ Sound Events

| File âm thanh | Mô tả | Trigger |
|---------------|-------|---------|
| `battle_song.ogg` | Nhạc battle thường | Bắt đầu trận đấu |
| `strong_battle_song.ogg` | Nhạc battle mạnh | Opponent +15 levels |
| `panic_song.ogg` | Nhạc căng thẳng | Pokemon < 20% HP |
| `victory.ogg` | Nhạc chiến thắng | Thắng battle |
| `evo.ogg` | Nhạc tiến hóa | Evolution start |
| `evo_congrat.ogg` | Chúc mừng tiến hóa | Evolution complete |
| `catch_congrat.ogg` | Chúc mừng bắt Pokemon | Capture success |

## 🔧 Cài đặt

1. Tải `cobblemonbattlemusic-1.0.0.jar`
2. Đặt vào thư mục `mods`
3. Đảm bảo Cobblemon 1.6.1+ đã cài
4. Thay thế placeholder sounds bằng file .ogg thật (optional)
5. Khởi động game

## 📝 Commands

- `/cobblemusic status` - Kiểm tra trạng thái mod
- `/cobblemusic test battle` - Test battle music
- `/cobblemusic test panic` - Test panic music  
- `/cobblemusic test victory` - Test victory music
- `/cobblemusic test evolution` - Test evolution sequence
- `/cobblemusic test catch` - Test catch music
- `/cobblemusic stop` - Stop music (debug)
- `/cobblemusic version` - Thông tin version

## ⚡ Optimizations từ CBM-CC

- **Event-driven Architecture** thay vì polling
- **Memory Efficient** Pokemon health monitoring
- **Graceful Error Handling** cho API changes
- **Proper Resource Management** với Timers
- **Volume Control** per music type
- **Fade Effects** giữa các loại nhạc

## 🐛 Known Issues

- **Health monitoring** có thể cần API updates trong future Cobblemon versions
- **Level detection** đơn giản hóa để tránh API instability
- **Sound files** hiện tại là placeholders (0 bytes)

## 🔮 Future Plans

- [ ] Advanced health monitoring với stable API
- [ ] Dynamic volume adjustment
- [ ] Custom sound file integration
- [ ] GUI configuration panel
- [ ] More battle music categories
- [ ] Biome-specific music

## 📜 Technical Notes

- **Client-side** music management
- **Server-side** command system  
- **Cross-platform** Fabric support
- **Kotlin compatibility** for Cobblemon
- **Resource streaming** for large audio files

---

**Build Info**: Built with Gradle 8.10.2, Fabric Loom 1.7.4  
**JAR Size**: 20KB (optimized)  
**Repository**: https://github.com/vryakafree/cbm