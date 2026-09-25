package Project_Araba;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ArabaSemaPaneli extends JPanel {

    private final Map<String, ParcaButonu> parcaButonlari = new HashMap<>();

    // Parçaların Piyasa Değer Düşüş Ağırlıkları
    private final Map<String, Double> parcaAgirliklari = Map.ofEntries(
            Map.entry("Tavan", 0.08),           // Tavan boya/değişen en büyük düşüş (%8 taban)
            Map.entry("Kaput", 0.05),           // Kaput (%5 taban)
            Map.entry("Bagaj", 0.04),           // Bagaj (%4 taban)
            Map.entry("Sol Ön Kapı", 0.025),
            Map.entry("Sağ Ön Kapı", 0.025),
            Map.entry("Sol Arka Kapı", 0.025),
            Map.entry("Sağ Arka Kapı", 0.025),
            Map.entry("Sol Ön Çamurluk", 0.02),
            Map.entry("Sağ Ön Çamurluk", 0.02),
            Map.entry("Sol Arka Çamurluk", 0.02),
            Map.entry("Sağ Arka Çamurluk", 0.02),
            Map.entry("Ön Tampon", 0.005),       // Tamponlar değer kaybına çok az etki eder
            Map.entry("Arka Tampon", 0.005)
    );

    public ArabaSemaPaneli() {
        setLayout(new GridBagLayout());
        setBackground(new Color(33, 38, 49));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(164, 176, 190)),
                " Araç Ekspertiz Şeması (Parçalara Tıklayarak Durum Değiştirin) ",
                0, 0, new Font("Segoe UI", Font.BOLD, 13), Color.WHITE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.BOTH;

        // --- Kuş Bakışı Araç Dizilimi ---
        parcaEkle("Ön Tampon", 1, 0, 1, 1, gbc);
        
        parcaEkle("Sol Ön Çamurluk", 0, 1, 1, 1, gbc);
        parcaEkle("Kaput", 1, 1, 1, 1, gbc);
        parcaEkle("Sağ Ön Çamurluk", 2, 1, 1, 1, gbc);

        parcaEkle("Sol Ön Kapı", 0, 2, 1, 1, gbc);
        parcaEkle("Tavan", 1, 2, 1, 2, gbc); // Tavan 2 satır yüksekliğinde
        parcaEkle("Sağ Ön Kapı", 2, 2, 1, 1, gbc);

        parcaEkle("Sol Arka Kapı", 0, 3, 1, 1, gbc);
        parcaEkle("Sağ Arka Kapı", 2, 3, 1, 1, gbc);

        parcaEkle("Sol Arka Çamurluk", 0, 4, 1, 1, gbc);
        parcaEkle("Bagaj", 1, 4, 1, 1, gbc);
        parcaEkle("Sağ Arka Çamurluk", 2, 4, 1, 1, gbc);

        parcaEkle("Arka Tampon", 1, 5, 1, 1, gbc);
    }

    private void parcaEkle(String ad, int gridx, int gridy, int gridwidth, int gridheight, GridBagConstraints gbc) {
        ParcaButonu buton = new ParcaButonu(ad);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        add(buton, gbc);
        parcaButonlari.put(ad, buton);
    }

    // Seçili parçalara göre toplam değer düşüş oranını döndüren metot
    public double toplamDusukOraniHesapla() {
        double toplamDusuk = 0.0;
        for (Map.Entry<String, ParcaButonu> entry : parcaButonlari.entrySet()) {
            String parcaAdi = entry.getKey();
            ParcaDurumu durum = entry.getValue().getDurum();
            double agirlik = parcaAgirliklari.getOrDefault(parcaAdi, 0.02);

            toplamDusuk += (agirlik * durum.getEtkiKatsayisi());
        }
        return toplamDusuk; // Örn: 0.12 (%12 değer kaybı)
    }

    // Tıklanabilir Parça Buton İç Sınıfı
    private static class ParcaButonu extends JButton {
        private ParcaDurumu durum = ParcaDurumu.ORIJINAL;

        public ParcaButonu(String ad) {
            super("<html><center>" + ad + "<br><b>[ O ]</b></center></html>");
            setFont(new Font("Segoe UI", Font.PLAIN, 11));
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            stiliGuncelle();

            addActionListener(e -> {
                durum = durum.sonraki();
                stiliGuncelle();
            });
        }

        public ParcaDurumu getDurum() { return durum; }

        private void stiliGuncelle() {
            setBackground(durum.getRenk());
            setForeground(Color.WHITE);
            String temizAd = getText().replaceAll("<[^>]*>", "").split("\\[")[0].trim();
            setText("<html><center>" + temizAd + "<br><b>[ " + durum.getKod() + " ]</b></center></html>");
        }
    }
}