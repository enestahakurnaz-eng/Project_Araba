package Project_Araba;

import java.awt.Color;

public enum ParcaDurumu {
    ORIJINAL("Orijinal", "O", new Color(160, 160, 160), 0.0),
    LOKAL_BOYALI("Lokal Boyalı", "LB", new Color(230, 126, 34), 0.5), // %50 Etki
    BOYALI("Boyalı", "B", new Color(41, 128, 185), 1.0),            // %100 Etki
    DEGISEN("Değişen", "D", new Color(231, 76, 60), 2.2);            // %220 Etki

    private final String etiket;
    private final String kod;
    private final Color renk;
    private final double etkiKatsayisi;

    ParcaDurumu(String etiket, String kod, Color renk, double etkiKatsayisi) {
        this.etiket = etiket;
        this.kod = kod;
        this.renk = renk;
        this.etkiKatsayisi = etkiKatsayisi;
    }

    public String getEtiket() { return etiket; }
    public String getKod() { return kod; }
    public Color getRenk() { return renk; }
    public double getEtkiKatsayisi() { return etkiKatsayisi; }

    // Tıklandıkça bir sonraki duruma geçiren metot
    public ParcaDurumu sonraki() {
        ParcaDurumu[] durumlar = values();
        return durumlar[(this.ordinal() + 1) % durumlar.length];
    }
}