# Clinic Appointment Management API

## Yerel çalıştırma

Java 17 ve PostgreSQL gereklidir. Varsayılan bağlantı
`jdbc:postgresql://localhost:5432/school_db`, kullanıcı adı `postgres` olarak ayarlıdır.

Bu bilgisayarda şifre, Git tarafından dışlanan kök dizindeki
`application-local.properties` dosyasından yüklenir. Proje kökünden çalıştırın:

```bash
./mvnw spring-boot:run
```

Başka bir bilgisayarda veya sunucuda şifreyi ortam değişkeniyle sağlayın:

```bash
read -rsp 'Veritabanı şifresi: ' DB_PASSWORD
echo
export DB_PASSWORD
./mvnw spring-boot:run
```

`DB_URL`, `DB_USERNAME` ve `DB_PASSWORD` ortam değişkenleriyle bağlantıyı
değiştirebilirsiniz. Uygulama Docker ağı içindeyse
`DB_URL=jdbc:postgresql://postgres:5432/school_db` kullanın.
IDE ile çalıştırırken çalışma dizinini proje kökü olarak ayarlayın.

Gerçek şifreleri veya yerel yapılandırma dosyasını GitHub’a yüklemeyin.
Spring Boot `.env` dosyasını kendiliğinden yüklemez.
