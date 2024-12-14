
# Hotel-BookingSystem

Bu projenin amacı, bir otel yönetim sistemine ait oda, müşteri, rezervasyon ve personel yönetim süreçlerini kolaylaştırmaktır. Sistem; oda listeleme ve yönetimi; rezervasyon oluşturma, rezervasyonları yönetme ve listeleme; personel ekleme, listeleme ve yönetme gibi işlemleri düzenlerken güvenli veri saklama ve kullanıcı rolleri bazında erişim kontrolü gibi özellikleri destekler. 

## Kullanılan Teknolojiler

**Front-End:** React

**Back-End:** Java, Spring Framework, JWT, PostgreSql


## Back-End

Projenin backend kısmı Java Spring Framework kullanılarak geliştirilmiştir. Backend, otel yönetim sisteminin iş mantığını ve veri yönetimini sağlamaktan sorumludur. 

API güvenliğinin sağlanması için JSON Web Token (JWT) kullanılmıştır.Kullanıcı giriş yaptıktan sonra, bir JWT token oluşturulur ve sonraki işlemlerde bu token aracılığıyla yetkilendirme kontrolü yapılır.

Controller-Service-Repository katmanları ile katmanlı bir mimari oluşturulmuştur.

- Controller Katmanı, istemciden gelen istekleri alır ve ilgili işlevleri çağırır.

- Service Katmanı, iş kurallarını uygular ve veritabanı işlemlerini koordine eder.

- Repository Katmanı, Hibernate/JPA kullanarak veritabanı CRUD ve veri tabanı sorgu işlemlerini gerçekleştirir.

Sistemin güvenilirliğini artırmak için JUnit ile birim testler yazılmıştır. Servis katmanındaki metotlar test edilerek, sistemin hata durumlarına karşı nasıl tepki vereceği doğrulanmıştır.

Veri tabanı yönetim sistemi olarak ise PostgreSql kullanılmıştır. Aşağıdaki ER diyagramı, uygulamada kullanılan veri modelini detaylandırmaktadır.

![Personel-Management](https://github.com/user-attachments/assets/9a02f814-b5f4-4f89-b581-4ffbb4467d38)

## DTO yapıları

#### DtoRoom Yapısı

| Alan            | Tip        | Açıklama                             |
| :-------------- | :--------- | :----------------------------------- |
| `roomNumber`    | `string`   | Oda numarası            |
| `roomType`      | `RoomType` | Oda tipi                |
| `capacity`      | `int`      | Odanın kapasitesi       |
| `bedType`       | `BedType`  | Yatak tipi              |
| `pricePerNight` | `double`   | Gecelik fiyat           |
| `isAvailable`   | `boolean`  | Odanın müsaitlik durumu |
| `hasView`       | `boolean`  | Odanın manzarası var mı? |

#### DtoCustomerIU Yapısı

| Alan            | Tip        | Açıklama                             |
| :-------------- | :--------- | :----------------------------------- |
| `firstName`     | `string`   | Müşterinin adı          |
| `lastName`      | `string`   |  Müşterinin soyadı       |
| `email`         | `string`   | Müşterinin e-posta adresi |
| `phoneNumber`   | `string`   | Müşterinin telefon numarası |
| `password`      | `string`   | Müşterinin şifresi      |

#### DtoCustomer Yapısı

| Alan            | Tip        | Açıklama                             |
| :-------------- | :--------- | :----------------------------------- |
| `firstName`     | `string`   | **Zorunlu**. Müşterinin adı          |
| `lastName`      | `string`   | **Zorunlu**. Müşterinin soyadı       |

#### DtoBooking Yapısı

| Alan             | Tip           | Açıklama                          |
| :--------------- | :------------ | :-------------------------------- |
| `id`             | `Long`        | Rezervasyon ID'si                 |
| `customer`       | `Customer`    | Rezervasyonu yapan müşteri bilgisi|
| `room`           | `Room`        | Rezervasyon yapılan oda bilgisi   |
| `checkInDate`    | `LocalDate`   | Giriş tarihi                      |
| `checkOutDate`   | `LocalDate`   | Çıkış tarihi                      |
| `status`         | `BookingStatus`| Rezervasyon durumu                |

#### DtoBookingIU Yapısı

| Alan             | Tip           | Açıklama                          |
| :--------------- | :------------ | :-------------------------------- |
| `email`          | `String`      | Rezervasyonu yapan müşteri e-postası |
| `roomNumber`     | `String`      | Rezervasyon yapılan oda numarası  |
| `checkInDate`    | `LocalDate`   | Giriş tarihi                      |
| `checkOutDate`   | `LocalDate`   | Çıkış tarihi                      |
| `bookingStatus`  | `BookingStatus`| Rezervasyon durumu                |


## Customer API Referance
Bu kısım, Customer API'sine ait endpointleri ve her birinin nasıl kullanılacağını açıklar. Aşağıdaki detaylar, ilgili API metodlarının istek yollarını, parametrelerini ve yanıtlarını açıklar.

### Kullanıcı Kaydı

```http
POST /rest/api/customer/register
```

### İstek Gövdesi

| Parametre       | Tip            | Açıklama                          |
| :-------------- | :------------- | :-------------------------------- |
| `customer`      | `DtoCustomerIU`| **Zorunlu**. Yeni müşteri bilgileri |

### Kullanıcı Girişi

```http
POST /rest/api/customer/login
```

#### Sorgu Parametreleri

| Parametre       | Tip      | Açıklama                              |
| :-------------- | :------- | :------------------------------------ |
| `email`         | `string` | **Zorunlu**. Müşterinin e-posta adresi |
| `password`      | `string` | **Zorunlu**. Müşterinin şifresi       |

### Tüm Kullanıcıları Listeleme

```http
GET /rest/api/customer/all
```
Tüm müşterileri döner.


### Kullanıcı Bilgisi

```http
GET /rest/api/customer/{email}
```

#### Yol Parametreleri

| Parametre       | Tip      | Açıklama                              |
| :-------------- | :------- | :------------------------------------ |
| `email`         | `string` | **Zorunlu**. Müşterinin e-posta adresi |

### Kullanıcı Güncelleme

```http
PUT /rest/api/customer/update/{email}
```

#### Yol Parametreleri

| Parametre       | Tip      | Açıklama                              |
| :-------------- | :------- | :------------------------------------ |
| `email`         | `string` | **Zorunlu**. Güncellenecek müşteri e-posta adresi |

#### İstek Gövdesi

| Parametre       | Tip      | Açıklama                              |
| :-------------- | :------- | :------------------------------------ |
| `updatedCustomer`| `DtoCustomer`| **Zorunlu**. Güncel müşteri bilgileri |

### Müşteri Silme  

```http
DELETE /rest/api/customer/delete/{email}
```

| Parametre | Tip      | Açıklama                              |  
| :-------- | :------- | :------------------------------------ |  
| `email`   | `string` | **Zorunlu**. Müşterinin e-posta adresi |  


### Şifre Değiştirme  

```http
PATCH /rest/api/customer/{email}/password
```

#### Yol Parametreleri  

| Parametre | Tip      | Açıklama                              |  
| :-------- | :------- | :------------------------------------ |  
| `email`   | `string` | **Zorunlu**. Müşterinin e-posta adresi |  

#### Sorgu Parametreleri  

| Parametre       | Tip      | Açıklama                              |  
| :-------------- | :------- | :------------------------------------ |  
| `oldPassword`   | `string` | **Zorunlu**. Müşterinin eski şifresi  |  
| `newPassword`   | `string` | **Zorunlu**. Müşterinin yeni şifresi  |  



## Room API Reference

Bu kısım, Room API'sine ait endpointleri ve her birinin nasıl kullanılacağını açıklar. Aşağıdaki detaylar, ilgili API metodlarının istek yollarını, parametrelerini ve yanıtlarını açıklar.

### Odaların Listesi

```http
GET /rest/api/room/list
```

Tüm odaların listesini döner.

### Oda Bilgisi

```http
GET /rest/api/room/{roomNumber}
```

#### Yol Parametreleri

| Parametre      | Tip      | Açıklama                          |
| :------------- | :------- | :-------------------------------- |
| `roomNumber`   | `string` | **Zorunlu**. Oda numarası         |


### Oda Oluşturma

```http
POST /rest/api/room/create
```

#### İstek Gövdesi

| Parametre       | Tip      | Açıklama                              |
| :-------------- | :------- | :------------------------------------ |
| `newRoom`       | `DtoRoom`| **Zorunlu**. Yeni oda bilgileri       |


### Oda Güncelleme

```http
PUT /rest/api/room/{roomNumber}
```

#### Yol Parametreleri

| Parametre      | Tip      | Açıklama                          |
| :------------- | :------- | :-------------------------------- |
| `roomNumber`   | `string` | **Zorunlu**. Güncellenecek oda numarası |

#### İstek Gövdesi

| Parametre       | Tip      | Açıklama                              |
| :-------------- | :------- | :------------------------------------ |
| `updatedRoom`   | `DtoRoom`| **Zorunlu**. Güncel oda bilgileri     |


### Oda Silme

```http
DELETE /rest/api/room/{roomNumber}
```

#### Yol Parametreleri

| Parametre      | Tip      | Açıklama                          |
| :------------- | :------- | :-------------------------------- |
| `roomNumber`   | `string` | **Zorunlu**. Silinecek oda numarası |

### Uygun Odaları Filtreleme

```http
GET /rest/api/room/filter
```

#### Sorgu Parametreleri

| Parametre       | Tip           | Açıklama                              |
| :-------------- | :------------ | :------------------------------------ |
| `roomType`      | `RoomType`    | İsteğe bağlı. Oda tipi               |
| `bedType`       | `string`      | İsteğe bağlı. Yatak tipi             |
| `hasView`       | `boolean`     | İsteğe bağlı. Manzarası var mı       |
| `checkInDate`   | `LocalDate`   | **Zorunlu**. Giriş tarihi            |
| `checkOutDate`  | `LocalDate`   | **Zorunlu**. Çıkış tarihi            |

### Oda Türlerini Listeleme

```http
GET /rest/api/room/room-types
```
Desteklenen tüm oda türlerini döner.


### Yatak Türlerini Listeleme

```http
GET /rest/api/room/bed-types
```
Desteklenen tüm yatak türlerini döner.


## Booking API Reference

Bu kısım, Room API'sine ait endpointleri ve her birinin nasıl kullanılacağını açıklar. Aşağıdaki detaylar, ilgili API metodlarının istek yollarını, parametrelerini ve yanıtlarını açıklar.

### Tüm Rezervasyonları Listeleme

```http
GET /rest/api/booking/list
```
Sistemdeki tüm rezervasyonları döner.


### Rezervasyon Bilgisi

```http
GET /rest/api/booking/{id}
```

#### Yol Parametreleri

| Parametre      | Tip      | Açıklama                              |
| :------------- | :------- | :------------------------------------ |
| `id`           | `Long`   | **Zorunlu**. Rezervasyon ID'si        |

### Rezervasyon Oluşturma

```http
POST /rest/api/booking/create
```

#### İstek Gövdesi

| Parametre       | Tip           | Açıklama                              |
| :-------------- | :------------ | :------------------------------------ |
| `dtoBookingIU`  | `DtoBookingIU`| **Zorunlu**. Yeni rezervasyon bilgileri |

### Rezervasyon Güncelleme

```http
PUT /rest/api/booking/{id}
```

#### Yol Parametreleri

| Parametre      | Tip      | Açıklama                              |
| :------------- | :------- | :------------------------------------ |
| `id`           | `Long`   | **Zorunlu**. Güncellenecek rezervasyon ID'si |

#### İstek Gövdesi

| Parametre       | Tip          | Açıklama                              |
| :-------------- | :----------- | :------------------------------------ |
| `updatedBooking`| `DtoBooking` | **Zorunlu**. Güncel rezervasyon bilgileri |

### Rezervasyon Silme

```http
DELETE /rest/api/booking/{id}
```

#### Yol Parametreleri

| Parametre      | Tip      | Açıklama                              |
| :------------- | :------- | :------------------------------------ |
| `id`           | `Long`   | **Zorunlu**. Silinecek rezervasyon ID'si |

### Müşteriye Göre Rezervasyonlar

```http
GET /rest/api/booking/customer/{email}
```

#### Yol Parametreleri

| Parametre      | Tip      | Açıklama                              |
| :------------- | :------- | :------------------------------------ |
| `email`        | `string` | **Zorunlu**. Müşterinin e-posta adresi |

### Rezervasyon Onaylama

```http
PATCH /rest/api/booking/accept/{id}
```

#### Yol Parametreleri

| Parametre      | Tip      | Açıklama                              |
| :------------- | :------- | :------------------------------------ |
| `id`           | `Long`   | **Zorunlu**. Onaylanacak rezervasyon ID'si |

### Rezervasyon Reddetme

```http
PATCH /rest/api/booking/reject/{id}
```

#### Yol Parametreleri

| Parametre      | Tip      | Açıklama                              |
| :------------- | :------- | :------------------------------------ |
| `id`           | `Long`   | **Zorunlu**. Reddedilecek rezervasyon ID'si |


## Front-End

Frontend kısmı, React.js kullanılarak geliştirilmiştir. Uygulama, kullanıcıların otel odalarını görüntülemesi, odaları filtrelemesi, rezervasyon yapması ve kullanıcı bilgilerini yönetmesi gibi işlemleri gerçekleştirmelerini; personellerin ise odaları, rezervasyonları düzenlemesini sağlar. Kullanıcı rollerine göre erişim yetkisi bulunmayan alanlar render edilmemektedir. Aşağıda, React ile geliştirilen frontend’in temel yapı taşları ve işleyişi açıklanmıştır.

- Ana uygulama bileşeninde, `react-router-dom` ve `react-dom` kütüphanesi kullanılarak sayfalar arası yönlendirme sağlanmaktadır.

- Backend ile iletişim, `axios` kütüphanesi kullanılarak yapılmaktadır. API çağrıları, odaların listelenmesi, filtrelenmesi, rezervasyon yapılması ve müşteri bilgileri gibi işlemleri içerir.

## Screenshots

![Login-Page](https://github.com/user-attachments/assets/3088a813-461b-4b40-9ee4-fd5523c0ae19)
---
![Room-Filter](https://github.com/user-attachments/assets/a965630d-cd6e-4f7d-8d17-c0be7cc13f9c)
---
![Room-Filter-List](https://github.com/user-attachments/assets/f11324d8-f9af-4264-b87f-dd7c6148b487)
---
![Password-Change](https://github.com/user-attachments/assets/7da0e025-5631-4a3e-90bf-7432966448ca)
---
![Admin-Panel](https://github.com/user-attachments/assets/6d661e86-00d1-43f6-ac63-7425639277ec)
---
![Room-Management](https://github.com/user-attachments/assets/b0c8a95f-6f96-4ada-bc61-deeadc8440dd)
---
![Booking-Management](https://github.com/user-attachments/assets/32bdd114-ac1d-49f3-aab2-1106b020a3ab)
---
![Room-Edit](https://github.com/user-attachments/assets/6081ddbc-e805-4f8a-9cc9-17a7dde2be61)
---
![Personel-Management](https://github.com/user-attachments/assets/e0c21fcc-6866-43df-8a39-fee90bec17b1)
---
## Run Locally

Clone the project

```bash
  git clone https://github.com/hegekara/hotel-booking-system.git
```

Go to the project directory

```bash
  cd hotel-booking-system
```

Start the Back-End

```bash
  cd hotel-booking-project
```
( `application.properties` dosyası içerisinden kendi PostgreSql veritabanınıza bağlantılarınız yapılmalıdır. `spring.jpa.hibernate.ddl-auto = update` komutu ilk çalıştırmada `create` olarak seçilmelidir. )
```bash
  ./mvnw spring-boot:run
```

Start the Front-End

```bash
  cd hotel-booking-project-frontend
  npm run dev
```

Test the Service Layer

```bash
  cd hotel-booking-project
  mvn test
```