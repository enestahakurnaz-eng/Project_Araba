package Project_Araba;

import java.io.File;

public class CarValuationService {

    public double calculateInstantValue(Car car, double parcaKaybiOrani) {
        // 1. Marka, Model ve Yıla Özel Taban Fiyatı Katalogdan Çek
        double basePrice = AracKatalogu.getTabanFiyat(car.getBrand(), car.getModel(), car.getYear());

        // 2. Kilometre Aşınma Düşüşü (Her 10.000 km için %1.2 değer kaybı)
        double kmLoss = basePrice * ((car.getKilometer() / 10000.0) * 0.012);
        basePrice -= kmLoss;

        // 3. Ekspertiz Şemasından Gelen Parça Kaybı Oranını Uygula
        basePrice -= (basePrice * parcaKaybiOrani);

        return Math.max(basePrice, 100000); // Minimum taban sınır
    }

    public Car analyzeImageWithAI(File imageFile) {
        Car aiResult = new Car();
        aiResult.setImagePath(imageFile.getAbsolutePath());
        
        // Örnek AI çıktısı
        aiResult.setBrand("Peugeot");
        aiResult.setModel("208");
        aiResult.setYear(2021);
        aiResult.setKilometer(65000);

        return aiResult;
    }

	
}