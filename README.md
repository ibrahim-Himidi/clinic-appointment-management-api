# Klinik Randevu Yönetim API'si

Java ve Spring Boot ile geliştirilmiş, kliniklerde **hasta, doktor ve randevu süreçlerini** yönetmek için tasarlanmış RESTful backend uygulamasıdır.

Proje; katmanlı mimari, veri doğrulama, merkezi hata yönetimi ve ilişkisel veritabanı kullanımı gibi backend geliştirme pratiklerini gerçek bir randevu senaryosu üzerinde uygular.

## İçindekiler

- [Proje Hakkında](#proje-hakkında)
- [Teknolojiler](#teknolojiler)
- [Mimari](#mimari)
- [Özellikler ve İş Kuralları](#özellikler-ve-i̇ş-kuralları)
- [API Uç Noktaları](#api-uç-noktaları)
- [Kurulum ve Çalıştırma](#kurulum-ve-çalıştırma)
- [Örnek İstek](#örnek-i̇stek)
- [HTTP Durum Kodları](#http-durum-kodları)
- [Geliştirme Notları](#geliştirme-notları)

## Proje Hakkında

Bu API üzerinden:

- Hasta kayıtları oluşturulabilir, listelenebilir, güncellenebilir ve silinebilir.
- Doktor bilgileri yönetilebilir.
- Hastalar için doktor ve tarih/saat seçilerek randevu oluşturulabilir.
- Randevular iptal edilebilir veya tamamlandı olarak işaretlenebilir.
- Doktorun aynı tarih ve saatte birden fazla aktif randevu alması engellenir.

Uygulama, istemciden gelen veriler ile veritabanı modellerini birbirinden ayırmak için **DTO (Data Transfer Object)** yapısını kullanır.

## Teknolojiler

| Teknoloji | Kullanım amacı |
| --- | --- |
| Java | Uygulama dili |
| Spring Boot 4.1.1 | REST API ve uygulama altyapısı |
| Spring Web MVC | HTTP isteklerinin işlenmesi |
| Spring Data JPA / Hibernate | Veritabanı erişimi ve ORM |
| PostgreSQL | İlişkisel veritabanı |
| Jakarta Bean Validation | İstek verilerinin doğrulanması |
| Maven | Bağımlılık ve proje yönetimi |
| springdoc-openapi / Swagger UI | Etkileşimli API dokümantasyonu |
| Docker | Yerel PostgreSQL geliştirme ortamı |

## Mimari

Proje, sorumlulukların ayrılmasına dayalı katmanlı bir yapı kullanır:

```text
İstemci (Postman / Swagger UI)
             |
             v
        Controller
             |
             v
          Service
             |
             v
         Repository
             |
             v
         PostgreSQL
```

- **Controller:** HTTP isteklerini karşılar ve uygun yanıtları döndürür.
- **Service:** İş kurallarını ve uygulama akışını yönetir.
- **Repository:** Spring Data JPA aracılığıyla veritabanına erişir.
- **Entity:** Veritabanı tablolarını temsil eder.
- **DTO:** API üzerinden alınan ve döndürülen veri modellerini tanımlar.
- **Mapper:** Entity ve DTO dönüşümlerini gerçekleştirir.
- **Exception:** Hataları merkezi biçimde ele alır.

## Özellikler ve İş Kuralları

### Hasta yönetimi

- Hasta oluşturma, listeleme, detay görüntüleme, tam ve kısmi güncelleme, silme.
- Ad, soyad, e-posta, telefon ve doğum tarihi alanları için uygun doğrulamalar.
- Bulunamayan hasta kayıtlarında `404 Not Found` yanıtı.

### Doktor yönetimi

- Doktor oluşturma, listeleme, detay görüntüleme, kısmi güncelleme ve silme.
- Doktor bilgileri için doğrulama kuralları.
- Randevusu bulunan doktorun silinmesini engelleyen kontrol. İptal edilmiş randevular da geçmiş kayıt olarak dikkate alınır.

### Randevu yönetimi

- Hasta ve doktor kimlikleri ile randevu oluşturma.
- Randevu listeleme ve detay görüntüleme.
- Randevu durumları: `SCHEDULED`, `COMPLETED`, `CANCELLED`.
- Randevu iptali ve tamamlanması için ayrı endpoint'ler.
- İptal edilmiş randevuların aynı zaman dilimi için yeni rezervasyona engel olmaması.
- Tamamlanmış randevuların iptal edilmesinin, iptal edilmiş randevuların tamamlanmasının engellenmesi.

**Randevu saat kuralları**

| Kural | Değer |
| --- | --- |
| Çalışma günleri | Pazartesi–Cuma |
| Sabah saatleri | 09.00–11.30 |
| Öğleden sonra | 13.30–16.30 |
| Randevu aralığı | 30 dakika |
| Geçmiş tarihe randevu | Kabul edilmez |
| Aynı doktor / aynı saat | Aktif randevu varsa kabul edilmez |

> Not: Saat aralıkları, randevu başlangıç zamanlarının kabul edildiği aralıklardır; son randevunun bitiş zamanı uygulama kuralına göre ayrıca değerlendirilebilir.

## API Uç Noktaları

### Hastalar — `/api/patients`

| Metot | Endpoint | Açıklama |
| --- | --- | --- |
| `POST` | `/api/patients` | Yeni hasta oluşturur |
| `GET` | `/api/patients` | Tüm hastaları listeler |
| `GET` | `/api/patients/{id}` | Hasta detayını getirir |
| `PUT` | `/api/patients/{id}` | Hasta bilgilerini günceller |
| `PATCH` | `/api/patients/{id}` | Hasta bilgilerini kısmi günceller |
| `DELETE` | `/api/patients/{id}` | Hastayı siler |

### Doktorlar — `/api/doctors`

| Metot | Endpoint | Açıklama |
| --- | --- | --- |
| `POST` | `/api/doctors` | Yeni doktor oluşturur |
| `GET` | `/api/doctors` | Tüm doktorları listeler |
| `GET` | `/api/doctors/{id}` | Doktor detayını getirir |
| `PATCH` | `/api/doctors/{id}` | Doktor bilgilerini kısmi günceller |
| `DELETE` | `/api/doctors/{id}` | Randevusu yoksa doktoru siler |

### Randevular — `/api/appointments`

| Metot | Endpoint | Açıklama |
| --- | --- | --- |
| `POST` | `/api/appointments` | Yeni randevu oluşturur |
| `GET` | `/api/appointments` | Tüm randevuları listeler |
| `GET` | `/api/appointments/{id}` | Randevu detayını getirir |
| `PATCH` | `/api/appointments/{id}/cancel` | Randevuyu iptal eder |
| `PATCH` | `/api/appointments/{id}/complete` | Randevuyu tamamlandı olarak işaretler |

API dokümantasyonu uygulama çalışırken şu adresten görüntülenebilir:

```text
http://localhost:8080/swagger-ui/index.html
```

## Kurulum ve Çalıştırma

**Gereksinimler:** Projeyle uyumlu JDK, Maven Wrapper ve çalışan bir PostgreSQL sunucusu.

1. Depoyu klonlayın:

   ```bash
   git clone https://github.com/ibrahim-Himidi/clinic-appointment-management-api.git
   cd clinic-appointment-management-api
   ```

2. PostgreSQL üzerinde `clinic_db` adlı veritabanını oluşturun (veya kendi veritabanı adınızı kullanın).

3. `src/main/resources/application.properties` içindeki bağlantı ayarlarını kendi ortamınıza göre yapılandırın. Örnek:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/clinic_db
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}
   ```

   > Bu örnek, yapılandırmanın ortam değişkenlerinden okunmasını gösterir. Projenizdeki diğer JPA ayarlarını mevcut yapılandırmanızla uyumlu tutun. Gerçek parolaları GitHub'a göndermeyin.

4. Uygulamayı başlatın:

   ```bash
   ./mvnw spring-boot:run
   ```

5. Swagger UI üzerinden endpoint'leri inceleyin:

   ```text
   http://localhost:8080/swagger-ui/index.html
   ```

## Örnek İstek

**Randevu oluşturma**

```http
POST /api/appointments
Content-Type: application/json
```

```json
{
  "patientId": 1,
  "doctorId": 2,
  "appointmentDate": "2026-10-26T10:00:00"
}
```

Başarılı bir istekte API, oluşturulan randevuya ait kimlik, hasta ve doktor kimlikleri, tarih/saat ve durum bilgisini döndürür. Başlangıç durumu `SCHEDULED` olur.

## HTTP Durum Kodları

| Kod | Anlamı | Örnek |
| --- | --- | --- |
| `200 OK` | İşlem başarılı | Hasta veya randevu görüntüleme |
| `201 Created` | Kayıt oluşturuldu | Yeni hasta, doktor veya randevu |
| `204 No Content` | İşlem başarılı, yanıt gövdesi yok | Kayıt silme |
| `400 Bad Request` | İstek veya alan doğrulaması başarısız | Geçersiz veri |
| `404 Not Found` | İstenen kayıt bulunamadı | Olmayan doktor kimliği |
| `409 Conflict` | İş kuralı çakışması | Dolu randevu saati veya randevusu olan doktoru silme |

## Geliştirme Notları

Bu proje, backend geliştirme becerilerini göstermek amacıyla geliştirilmiştir. Üretim ortamına alınmadan önce özellikle şu alanların güçlendirilmesi planlanmaktadır:

- JUnit / Mockito ile birim ve entegrasyon testleri.
- Eşzamanlı randevu taleplerine karşı veritabanı seviyesinde benzersizlik güvencesi.
- Ortam değişkenleri ve örnek yapılandırma dosyalarının düzenlenmesi.
- Docker Compose yapılandırmasının uygulama deposuna taşınması.
- Swagger endpoint açıklamalarının genişletilmesi.

---

**Proje türü:** RESTful Backend API  
**Geliştirme amacı:** Java, Spring Boot, JPA, PostgreSQL ve REST API tasarımını gerçek bir senaryoda uygulamak.
