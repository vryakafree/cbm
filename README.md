# Cobblemon Battle Music

Một mod Fabric thêm nhạc nền chiến đấu tùy chỉnh cho Cobblemon.

## Tính năng

- 🎵 Phát nhạc nền khi bắt đầu chiến đấu Cobblemon
- ⚡ Nhạc nền đặc biệt khi máu thấp (≤ 6 HP)
- 🏆 Nhạc chiến thắng khi thắng battle
- 🔄 Tự động dừng nhạc khi kết thúc chiến đấu

## Yêu cầu

- Minecraft 1.21.1
- Fabric Loader 0.16.9+
- Fabric API 0.110.0+
- Cobblemon 1.6.1+
- Java 21+

## Cài đặt

1. Tải xuống và cài đặt Fabric Loader
2. Tải xuống Fabric API và Cobblemon
3. Đặt file mod vào thư mục `mods` của Minecraft
4. Khởi động game và tận hưởng!

## Build từ mã nguồn

1. Clone repository này:
   ```bash
   git clone https://github.com/your-username/cobblemon-battle-music.git
   cd cobblemon-battle-music
   ```

2. Build mod:
   ```bash
   ./gradlew build
   ```

3. File mod sẽ được tạo trong `build/libs/`

## Cấu hình

Mod tự động hoạt động khi được cài đặt. Các file âm thanh có thể được thay thế trong:
- `assets/cobblemonbattlemusic/sounds/battle_music.ogg` - Nhạc chiến đấu
- `assets/cobblemonbattlemusic/sounds/low_health_music.ogg` - Nhạc máu thấp
- `assets/cobblemonbattlemusic/sounds/victory_music.ogg` - Nhạc chiến thắng

## Phát triển

Để phát triển mod này:

1. Import project vào IDE (IntelliJ IDEA khuyến nghị)
2. Chạy `./gradlew genSources` để tạo mappings
3. Chạy `./gradlew runClient` để test trong môi trường dev

## Giấy phép

MIT License - xem file [LICENSE](LICENSE) để biết thêm chi tiết.

## Đóng góp

Mọi đóng góp đều được chào đón! Hãy tạo issue hoặc pull request.

## Hỗ trợ

Nếu gặp vấn đề, hãy tạo issue trên GitHub repository.