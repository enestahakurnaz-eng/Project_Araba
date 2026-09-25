package Project_Araba;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SonucPenceresi extends JDialog {

    private static final Map<String, String> kronikSorunlar = new HashMap<>();

    static {
        // Fiat
        kronikSorunlar.put("fiat:egea", "Çift kütleli volan ve debriyaj erken aşınması, düşük devirde vuruntu/titreşim şikayetleri.");
        kronikSorunlar.put("fiat:egea cross", "Egea ile aynı gövde; ek olarak arka amortisör/burç gürültüsü.");
        kronikSorunlar.put("fiat:500", "Elektrik aksamı (cam motoru, merkezi kilit) arızaları, küçük hacimli motorlarda yağ tüketimi.");
        kronikSorunlar.put("fiat:panda", "Basit mekanik yapı sağlam ama pas/korozyon (özellikle alt kaporta) yaygın.");
        kronikSorunlar.put("fiat:tipo", "Egea ile ortak sorunlar; multimedya/elektronik donanımda arıza bildirimleri.");
        kronikSorunlar.put("fiat:doblo", "Turbo/EGR kirlenmesi, ticari kullanım nedeniyle şanzıman yıpranması.");
        kronikSorunlar.put("fiat:linea", "Elektrik kontak sorunları, klima kompresörü arızaları.");
        kronikSorunlar.put("fiat:marea", "Kaporta pası, elektrik aksamı yaşlanması (üretimi eski).");
        kronikSorunlar.put("fiat:palio", "Basit ama düşük donanım kalitesi, iç mekan gıcırtıları.");
        kronikSorunlar.put("fiat:albea", "Dayanıklı motor bloğu, ancak süspansiyon burçları erken yıpranır.");

        // Renault
        kronikSorunlar.put("renault:megane", "EDC (çift kavramalı) şanzıman debriyaj sorunları, elektronik park freni arızaları.");
        kronikSorunlar.put("renault:clio", "EDC şanzıman tepki gecikmesi, cam/kilit elektroniği.");
        kronikSorunlar.put("renault:captur", "Megane ile ortak EDC sorunları, çok katmanlı gösterge paneli arızaları.");
        kronikSorunlar.put("renault:kadjar", "EDC şanzıman, turbo dizel motorlarda enjektör sorunları.");
        kronikSorunlar.put("renault:talisman", "Hava süspansiyonu (varsa) ve elektronik donanım karmaşıklığı kaynaklı arıza sıklığı.");
        kronikSorunlar.put("renault:symbol", "Basit ve dayanıklı, klima ve elektrik kontak sorunları.");
        kronikSorunlar.put("renault:taliant", "Yeni model; erken dönemde küçük yazılım/multimedya şikayetleri.");
        kronikSorunlar.put("renault:austral", "Hibrit sistemde erken dönem yazılım güncelleme sorunları.");
        kronikSorunlar.put("renault:19", "Karbüratör/enjeksiyon ayarı, kaporta pası (eski model).");
        kronikSorunlar.put("renault:fluence", "EDC şanzıman sorunları, elektrikli cam/anahtar sistemi arızaları.");
        kronikSorunlar.put("renault:toros", "Basit mekanik yapı, ancak pas ve elektrik tesisatı yaşlanması.");

        // Tofaş
        kronikSorunlar.put("tofaş:şahin", "Karbüratör ayarı, kaporta pası, fren sistemi bakım ihtiyacı yüksek.");
        kronikSorunlar.put("tofaş:doğan", "Şahin ile aynı platform; motor yağ kaçakları yaygın.");
        kronikSorunlar.put("tofaş:kartal", "Ticari kullanım yorgunluğu, süspansiyon ve şanzıman aşınması.");
        kronikSorunlar.put("tofaş:murat 131", "Eski teknoloji, elektrik tesisatı ve karbüratör bakımı sık gerekir.");

        // Volkswagen
        kronikSorunlar.put("volkswagen:golf", "DSG şanzıman mekatronik arızası, TSI motorlarda zincir gerdirici sorunu.");
        kronikSorunlar.put("volkswagen:passat", "DSG ve zincir gerdirici, ayrıca elektronik el freni motoru arızası.");
        kronikSorunlar.put("volkswagen:polo", "Küçük hacimli TSI motorlarda yağ tüketimi, DSG debriyaj.");
        kronikSorunlar.put("volkswagen:tiguan", "DSG şanzıman, dört çeker Haldex kaplin bakım maliyeti.");
        kronikSorunlar.put("volkswagen:t-cross", "Polo ile ortak motor/şanzıman sorunları.");
        kronikSorunlar.put("volkswagen:jetta", "Golf ile paylaşılan DSG ve zincir sorunları.");
        kronikSorunlar.put("volkswagen:arteon", "Karmaşık elektronik donanım, hava süspansiyonu (varsa) maliyetli arızalar.");
        kronikSorunlar.put("volkswagen:t-roc", "Tiguan/Golf ile ortak DSG ve zincir gerdirici sorunları.");
        kronikSorunlar.put("volkswagen:bora", "Eski nesil motor zincir/gergi sorunları, elektrik aksamı yaşlanması.");

        // BMW
        kronikSorunlar.put("bmw:320i", "Zincir gerdirici (N20/B48 motor), yüksek basınç yakıt pompası arızaları.");
        kronikSorunlar.put("bmw:118i", "Aynı motor ailesi zincir sorunları, ön takım burç aşınması.");
        kronikSorunlar.put("bmw:218i gran coupe", "Ön çekiş platformu, DCT şanzıman soğuk havada sertlik şikayeti.");
        kronikSorunlar.put("bmw:520i", "Zincir gerdirici, hava süspansiyon (varsa) kompresör arızası.");
        kronikSorunlar.put("bmw:730i", "Hava süspansiyonu ve elektronik donanım karmaşıklığından kaynaklı yüksek bakım maliyeti.");
        kronikSorunlar.put("bmw:x1", "Zincir gerdirici, dört çeker sistem bakım maliyeti.");
        kronikSorunlar.put("bmw:x3", "Zincir sorunları, elektronik şanzıman kontrol ünitesi arızaları.");
        kronikSorunlar.put("bmw:x5", "Hava süspansiyonu, zincir gerdirici, yüksek bakım gideri.");
        kronikSorunlar.put("bmw:316i", "Daha eski zincirli motorlarda gerdirici/kılavuz aşınması.");

        // Peugeot
        kronikSorunlar.put("peugeot:208", "EAT8/EGC şanzıman adaptasyon sorunları, elektronik el freni.");
        kronikSorunlar.put("peugeot:301", "Basit mekanik yapı, klima ve elektrik kontak sorunları.");
        kronikSorunlar.put("peugeot:2008", "208 ile ortak şanzıman ve turbo sorunları.");
        kronikSorunlar.put("peugeot:3008", "EGC şanzıman, dizel motorlarda DPF (partikül filtresi) tıkanması.");
        kronikSorunlar.put("peugeot:508", "EAT8 şanzıman, hava süspansiyonlu versiyonlarda maliyetli arıza.");
        kronikSorunlar.put("peugeot:306", "Eski model, kaporta pası ve elektrik tesisatı yaşlanması.");
        kronikSorunlar.put("peugeot:405", "Karbüratör/enjeksiyon ayarı, süspansiyon burçları.");

        // Honda
        kronikSorunlar.put("honda:civic", "Genel olarak güvenilir; CVT şanzıman versiyonlarında kayış/kasnak aşınması.");
        kronikSorunlar.put("honda:city", "Dayanıklı motor, klima kompresörü ve elektrik kontak arızaları.");
        kronikSorunlar.put("honda:cr-v", "CVT şanzıman ısınma şikayeti, dört çeker sistem bakımı.");
        kronikSorunlar.put("honda:hr-v", "CVT şanzıman, elektronik park sensörü arızaları.");
        kronikSorunlar.put("honda:accord", "Genel güvenilirlik yüksek, süspansiyon burçlarında erken aşınma bildirimi.");

        // Toyota
        kronikSorunlar.put("toyota:corolla", "Genel olarak çok güvenilir; hibrit versiyonlarda yüksek voltaj batarya yaşlanması.");
        kronikSorunlar.put("toyota:corolla cross", "Corolla ile ortak, hibrit sistem bakım maliyeti.");
        kronikSorunlar.put("toyota:c-hr", "Hibrit sistem güvenilir, multimedya yazılım güncelleme şikayetleri.");
        kronikSorunlar.put("toyota:rav4", "Genel güvenilir, hibrit batarya sağlığı uzun vadede takip gerektirir.");
        kronikSorunlar.put("toyota:camry", "Güvenilir; klima ve elektronik donanımda düşük oranlı arıza.");
        kronikSorunlar.put("toyota:yaris", "Çok az arıza bildirimi; küçük motor yağ tüketimi nadiren.");
        kronikSorunlar.put("toyota:yaris cross", "Yaris ile ortak, hibrit versiyonda batarya soğutma fanı gürültüsü.");
        kronikSorunlar.put("toyota:avensis", "Dayanıklı motor, dizel modellerde DPF tıkanması.");

        // Hyundai
        kronikSorunlar.put("hyundai:i20", "DCT şanzıman debriyaj sorunları (özellikle erken dönem), klima kompresörü.");
        kronikSorunlar.put("hyundai:i10", "Basit ve dayanıklı, elektrik kontak sorunları nadir.");
        kronikSorunlar.put("hyundai:elantra", "DCT şanzıman, turbo motorlarda enjektör arızaları.");
        kronikSorunlar.put("hyundai:tucson", "DCT şanzıman ve dört çeker kaplin bakım maliyeti.");
        kronikSorunlar.put("hyundai:bayon", "i20 ile ortak DCT sorunları.");
        kronikSorunlar.put("hyundai:kona", "DCT şanzıman, elektrikli versiyonlarda batarya soğutma sistemi.");
        kronikSorunlar.put("hyundai:accent blue", "Dayanıklı motor, klima ve elektrik aksamı arızaları.");
        kronikSorunlar.put("hyundai:accent era", "Eski model, kaporta pası ve karbüratör/enjeksiyon bakımı.");

        // Kia
        kronikSorunlar.put("kia:rio", "Hyundai ile ortak platform, DCT şanzıman sorunları.");
        kronikSorunlar.put("kia:ceed", "DCT debriyaj, turbo dizel motorlarda enjektör arızası.");
        kronikSorunlar.put("kia:sportage", "DCT şanzıman ve dört çeker kaplin bakımı.");
        kronikSorunlar.put("kia:stonic", "Rio ile ortak motor/şanzıman sorunları.");
        kronikSorunlar.put("kia:picanto", "Basit yapı, elektrik kontak sorunları nadir.");
        kronikSorunlar.put("kia:niro", "Hibrit sistem genel güvenilir, batarya soğutma fanı gürültüsü.");

        // Ford
        kronikSorunlar.put("ford:focus", "PowerShift (çift kavramalı) şanzıman debriyaj ve titreşim sorunları.");
        kronikSorunlar.put("ford:fiesta", "PowerShift şanzıman sorunları, elektrik kontak arızaları.");
        kronikSorunlar.put("ford:kuga", "PowerShift şanzıman, EcoBoost motorlarda soğutma sistemi arızası.");
        kronikSorunlar.put("ford:puma", "Focus/Fiesta ile ortak şanzıman sorunları.");
        kronikSorunlar.put("ford:mondeo", "PowerShift şanzıman, dizel motorlarda DPF tıkanması.");
        kronikSorunlar.put("ford:courier", "Ticari kullanım yorgunluğu, süspansiyon aşınması.");
        kronikSorunlar.put("ford:escort", "Eski model, kaporta pası ve elektrik tesisatı yaşlanması.");

        // Opel
        kronikSorunlar.put("opel:corsa", "Zincir gerdirici (küçük hacimli motorlarda), elektrik kontak sorunları.");
        kronikSorunlar.put("opel:astra", "Zincir gerdirici, DPF tıkanması (dizel).");
        kronikSorunlar.put("opel:crossland", "PSA platformu (Peugeot/Citroen ortak), EGC şanzıman sorunları.");
        kronikSorunlar.put("opel:grandland", "PSA platformu, DPF ve turbo sorunları.");
        kronikSorunlar.put("opel:insignia", "Elektronik donanım karmaşıklığı, otomatik şanzıman adaptasyon sorunları.");
        kronikSorunlar.put("opel:vectra", "Eski model, zincir/kayış gerginlik sorunları.");

        // Citroen
        kronikSorunlar.put("citroen:c3", "EGC şanzıman adaptasyon sorunları, elektronik el freni.");
        kronikSorunlar.put("citroen:c4", "EGC şanzıman, DPF tıkanması (dizel).");
        kronikSorunlar.put("citroen:c-elysee", "Basit mekanik yapı, klima ve elektrik kontak sorunları.");
        kronikSorunlar.put("citroen:c5 aircross", "Hidropnömatik konfor süspansiyonu (varsa) bakım maliyeti, DPF sorunları.");

        // Dacia
        kronikSorunlar.put("dacia:sandero", "Basit ve dayanıklı; klima kompresörü ve elektrik kontak sorunları nadiren.");
        kronikSorunlar.put("dacia:duster", "Dayanıklı motor, dört çeker versiyonlarda kaplin bakımı.");
        kronikSorunlar.put("dacia:logan", "Sandero ile ortak, düşük arıza oranı.");
        kronikSorunlar.put("dacia:jogger", "Yeni model; erken dönemde küçük donanım şikayetleri.");

        // Skoda
        kronikSorunlar.put("skoda:octavia", "DSG şanzıman mekatronik arızası, TSI motorlarda zincir gerdirici.");
        kronikSorunlar.put("skoda:fabia", "Küçük hacimli TSI motorlarda yağ tüketimi, DSG debriyaj.");
        kronikSorunlar.put("skoda:superb", "DSG şanzıman, hava süspansiyonlu versiyonlarda maliyetli arıza.");
        kronikSorunlar.put("skoda:kamiq", "Fabia ile ortak motor/şanzıman sorunları.");
        kronikSorunlar.put("skoda:karoq", "Octavia ile ortak DSG ve zincir sorunları.");

        // Seat
        kronikSorunlar.put("seat:ibiza", "TSI motor zincir gerdirici, DSG debriyaj.");
        kronikSorunlar.put("seat:leon", "DSG şanzıman mekatronik arızası, zincir gerdirici.");
        kronikSorunlar.put("seat:arona", "Ibiza ile ortak motor/şanzıman sorunları.");
        kronikSorunlar.put("seat:ateca", "Leon ile ortak DSG ve zincir sorunları.");

        // Nissan
        kronikSorunlar.put("nissan:qashqai", "CVT şanzıman kayış/kasnak aşınması, DCT versiyonlarda debriyaj.");
        kronikSorunlar.put("nissan:juke", "CVT/DCT şanzıman sorunları, turbo motorlarda soğutma arızası.");
        kronikSorunlar.put("nissan:micra", "Basit yapı, elektrik kontak sorunları nadir.");
        kronikSorunlar.put("nissan:x-trail", "CVT şanzıman ısınma şikayeti, dört çeker sistem bakımı.");

        // Mercedes-Benz
        kronikSorunlar.put("mercedes-benz:a 180", "DCT (7G-DCT) şanzıman debriyaj, elektronik donanım arıza kodları.");
        kronikSorunlar.put("mercedes-benz:c 200", "Hava süspansiyonu (varsa), turbo motor yağ tüketimi.");
        kronikSorunlar.put("mercedes-benz:e 200", "Hava süspansiyonu maliyetli arıza, elektronik donanım karmaşıklığı.");
        kronikSorunlar.put("mercedes-benz:gla 200", "A-Serisi ile ortak DCT sorunları.");
        kronikSorunlar.put("mercedes-benz:glc 200", "Hava süspansiyonu, dört çeker sistem bakım maliyeti.");
        kronikSorunlar.put("mercedes-benz:s 500", "Hava süspansiyonu, çok katmanlı elektronik donanım yüksek bakım gideri.");

        // Audi
        kronikSorunlar.put("audi:a3", "DSG mekatronik arızası, TSI zincir gerdirici.");
        kronikSorunlar.put("audi:a4", "DSG/S-tronic şanzıman, quattro sistem bakım maliyeti.");
        kronikSorunlar.put("audi:a6", "Hava süspansiyonu (varsa), elektronik donanım karmaşıklığı.");
        kronikSorunlar.put("audi:q3", "A3 ile ortak DSG ve zincir sorunları.");
        kronikSorunlar.put("audi:q5", "A4 ile ortak, quattro bakım maliyeti.");
        kronikSorunlar.put("audi:q7", "Hava süspansiyonu, yüksek bakım gideri.");

        // Mazda
        kronikSorunlar.put("mazda:mazda2", "Dayanıklı motor, elektrik kontak sorunları nadir.");
        kronikSorunlar.put("mazda:mazda3", "SkyActiv motor genel güvenilir, klasik otomatik daha sorunsuz.");
        kronikSorunlar.put("mazda:cx-30", "Mazda3 ile ortak, dayanıklı yapı.");
        kronikSorunlar.put("mazda:cx-5", "Genel güvenilir, dizel modellerde DPF tıkanması.");

        // Mitsubishi
        kronikSorunlar.put("mitsubishi:space star", "Basit yapı, elektrik kontak sorunları nadir.");
        kronikSorunlar.put("mitsubishi:asx", "Dayanıklı motor, CVT şanzıman versiyonlarında ısınma şikayeti.");
        kronikSorunlar.put("mitsubishi:outlander", "CVT şanzıman, dört çeker sistem bakım maliyeti.");

        // Suzuki
        kronikSorunlar.put("suzuki:swift", "Dayanıklı ve düşük arıza oranlı, klima kompresörü nadiren.");
        kronikSorunlar.put("suzuki:vitara", "Dört çeker kaplin bakımı, turbo motorlarda soğutma sistemi.");
        kronikSorunlar.put("suzuki:s-cross", "Vitara ile ortak motor/şanzıman sorunları.");

        // Chevrolet
        kronikSorunlar.put("chevrolet:spark", "Basit yapı, elektrik kontak sorunları.");
        kronikSorunlar.put("chevrolet:cruze", "Otomatik şanzıman adaptasyon sorunları, turbo motor yağ tüketimi.");

        // Volvo
        kronikSorunlar.put("volvo:xc40", "Otomatik şanzıman yazılım güncelleme ihtiyacı, elektronik donanım karmaşıklığı.");
        kronikSorunlar.put("volvo:xc60", "Hava süspansiyonu (varsa), turbo motor yağ tüketimi.");
        kronikSorunlar.put("volvo:s60", "XC60 ile ortak motor/şanzıman sorunları.");
        kronikSorunlar.put("volvo:s90", "Hava süspansiyonu, elektronik donanım yüksek bakım gideri.");

        // Jeep
        kronikSorunlar.put("jeep:renegade", "DCT şanzıman debriyaj sorunları, elektrik kontak arızaları.");
        kronikSorunlar.put("jeep:compass", "DCT şanzıman, dört çeker sistem bakım maliyeti.");
        kronikSorunlar.put("jeep:grand cherokee", "Hava süspansiyonu maliyetli arıza, elektronik donanım karmaşıklığı.");

        // Land Rover
        kronikSorunlar.put("land rover:discovery sport", "Hava süspansiyonu, elektrik aksamı yüksek arıza oranı.");
        kronikSorunlar.put("land rover:range rover evoque", "Hava süspansiyonu, dokuz ileri otomatik şanzıman sorunları.");
        kronikSorunlar.put("land rover:defender", "Hava süspansiyonu, yeni nesil elektronik donanımda yazılım arızaları.");

        // Mini
        kronikSorunlar.put("mini:cooper", "BMW ile ortak zincir gerdirici sorunları, DCT şanzıman.");
        kronikSorunlar.put("mini:countryman", "Cooper ile ortak motor/şanzıman sorunları.");

        // Alfa Romeo
        kronikSorunlar.put("alfa romeo:giulia", "Elektronik donanım karmaşıklığı, otomatik şanzıman adaptasyon sorunları.");
        kronikSorunlar.put("alfa romeo:stelvio", "Giulia ile ortak, dört çeker sistem bakım maliyeti.");

        // Lexus
        kronikSorunlar.put("lexus:nx", "Genel güvenilir (Toyota kökenli), hibrit batarya uzun vadede takip gerektirir.");
        kronikSorunlar.put("lexus:rx", "Hibrit sistem güvenilir, hava süspansiyonlu versiyonlarda bakım maliyeti.");
        kronikSorunlar.put("lexus:es", "Genel güvenilir, düşük arıza oranı.");

        // Porsche
        kronikSorunlar.put("porsche:macan", "Hava süspansiyonu, PDK şanzıman bakım maliyeti.");
        kronikSorunlar.put("porsche:cayenne", "Hava süspansiyonu, hava yastığı/elektronik donanım yüksek bakım gideri.");
        kronikSorunlar.put("porsche:911", "IMS rulman sorunu (eski nesil motorlar), yüksek bakım maliyeti.");

        // Tesla
        kronikSorunlar.put("tesla:model 3", "Kaporta pres kalitesi (panel aralıkları), motor/batarya soğutma pompası arızaları.");
        kronikSorunlar.put("tesla:model y", "Model 3 ile ortak üretim kalitesi şikayetleri.");

        // MG
        kronikSorunlar.put("mg:zs", "Yeni marka, erken dönem elektronik/multimedya yazılım şikayetleri.");
        kronikSorunlar.put("mg:hs", "ZS ile ortak, DCT şanzıman debriyaj bildirimleri.");
        kronikSorunlar.put("mg:mg5", "Erken dönem yazılım ve elektrik kontak şikayetleri.");

        // Togg
        kronikSorunlar.put("togg:t10x", "Yeni model; yazılım güncellemeleri ve batarya yönetim sistemi erken dönem şikayetleri.");

        // Chery
        kronikSorunlar.put("chery:tiggo 7 pro", "Yeni marka, DCT şanzıman ve elektronik donanım erken dönem şikayetleri.");
        kronikSorunlar.put("chery:tiggo 8 pro", "Tiggo 7 ile ortak sorunlar.");

        // Subaru
        kronikSorunlar.put("subaru:xv", "Boxer motor genel güvenilir, CVT şanzıman ısınma şikayeti.");
        kronikSorunlar.put("subaru:forester", "XV ile ortak CVT sorunları.");

        // DS
        kronikSorunlar.put("ds:ds3", "PSA platformu, EGC şanzıman ve elektronik donanım sorunları.");
        kronikSorunlar.put("ds:ds7", "Hava süspansiyonu (varsa), elektronik donanım karmaşıklığı.");

        // Cupra
        kronikSorunlar.put("cupra:formentor", "VW grubu DSG mekatronik arızası, zincir gerdirici.");
        kronikSorunlar.put("cupra:leon", "Formentor ile ortak DSG ve zincir sorunları.");

        // Jaguar
        kronikSorunlar.put("jaguar:xe", "Elektronik donanım karmaşıklığı, otomatik şanzıman adaptasyon sorunları.");
        kronikSorunlar.put("jaguar:f-pace", "XE ile ortak, hava süspansiyonlu versiyonlarda bakım maliyeti.");

        // Maserati
        kronikSorunlar.put("maserati:ghibli", "Yüksek bakım maliyeti, elektronik donanım ve turbo sistem arızaları.");

        // Isuzu
        kronikSorunlar.put("isuzu:d-max", "Ticari kullanım yorgunluğu, turbo dizel motorlarda enjektör arızaları.");

        // SsangYong
        kronikSorunlar.put("ssangyong:tivoli", "Elektronik donanım ve otomatik şanzıman adaptasyon şikayetleri.");
        kronikSorunlar.put("ssangyong:korando", "Tivoli ile ortak sorunlar.");
    }

    public SonucPenceresi(JFrame parent, String marka, String model, int yil, double hesaplananFiyat) {
        super(parent, "Araç Değerleme Sonucu", true);
        setSize(480, 480); // Pencere boyutu kronik sorun kutusu için büyütüldü
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        Color arkaPlan = new Color(24, 27, 38);
        Color kartArkaPlan = new Color(30, 34, 48);
        Color morRenk = new Color(108, 92, 231);
        Color yesilFiyat = new Color(46, 213, 115);
        Color sarıSariBaslik = new Color(241, 196, 15);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBackground(arkaPlan);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // 1. Üst Bilgi Başlığı
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 3, 3));
        headerPanel.setOpaque(false);

        JLabel lblBaslik = new JLabel("HESAPLANAN ARAÇ DEĞERİ", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBaslik.setForeground(new Color(160, 174, 192));

        JLabel lblArac = new JLabel(marka + " " + model + " (" + yil + ")", SwingConstants.CENTER);
        lblArac.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblArac.setForeground(Color.WHITE);

        headerPanel.add(lblBaslik);
        headerPanel.add(lblArac);

        // 2. Orta İçerik (Fiyat Kartı + Kronik Sorunlar Kartı)
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        // Fiyat Kartı
        JPanel priceCard = new JPanel(new GridBagLayout());
        priceCard.setBackground(kartArkaPlan);
        priceCard.setBorder(new LineBorder(new Color(50, 56, 75), 1, true));
        priceCard.setPreferredSize(new Dimension(0, 85));

        JPanel priceContent = new JPanel(new GridLayout(2, 1, 2, 2));
        priceContent.setOpaque(false);

        JLabel lblMetin = new JLabel("Tahmini Piyasa Değeri", SwingConstants.CENTER);
        lblMetin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMetin.setForeground(new Color(200, 205, 215));

        JLabel lblFiyat = new JLabel(String.format("%,.0f TL", hesaplananFiyat), SwingConstants.CENTER);
        lblFiyat.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblFiyat.setForeground(yesilFiyat);

        priceContent.add(lblMetin);
        priceContent.add(lblFiyat);
        priceCard.add(priceContent);

        // Kronik Sorunlar Kartı
        String sorunMetni = getKronikSorunMetni(marka, model);

        JPanel kronikCard = new JPanel(new BorderLayout(8, 8));
        kronikCard.setBackground(kartArkaPlan);

        TitledBorder kronikBorder = BorderFactory.createTitledBorder(
            new LineBorder(new Color(60, 68, 90), 1), 
            "⚠️ Bilinen Kronik Sorunlar & Dikkat Edilecekler"
        );
        kronikBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        kronikBorder.setTitleColor(sarıSariBaslik);
        kronikCard.setBorder(BorderFactory.createCompoundBorder(
            kronikBorder,
            BorderFactory.createEmptyBorder(6, 10, 8, 10)
        ));

        JTextArea txtSorun = new JTextArea(sorunMetni);
        txtSorun.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSorun.setForeground(new Color(220, 225, 235));
        txtSorun.setBackground(kartArkaPlan);
        txtSorun.setLineWrap(true);
        txtSorun.setWrapStyleWord(true);
        txtSorun.setEditable(false);
        txtSorun.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(txtSorun);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        kronikCard.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(priceCard, BorderLayout.NORTH);
        centerPanel.add(kronikCard, BorderLayout.CENTER);

        // 3. Alt Kapat Butonu
        JButton btnKapat = new JButton("Tamam");
        btnKapat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKapat.setBackground(morRenk);
        btnKapat.setForeground(Color.WHITE);
        btnKapat.setFocusPainted(false);
        btnKapat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKapat.setPreferredSize(new Dimension(0, 42));
        btnKapat.addActionListener(e -> dispose());

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(btnKapat, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private String getKronikSorunMetni(String marka, String model) {
        if (marka == null || model == null) {
            return "Bu araç için spesifik bir kronik arıza kaydı bulunmamaktadır.";
        }

        String searchKey = (marka.trim() + ":" + model.trim()).toLowerCase();

        // 1. Birebir marka + model eşleşmesi
        if (kronikSorunlar.containsKey(searchKey)) {
            return kronikSorunlar.get(searchKey);
        }

        // 2. Karakter veya Türkçe harf uyumluluğu kontrolü
        for (Map.Entry<String, String> entry : kronikSorunlar.entrySet()) {
            String key = entry.getKey();
            if (key.equalsIgnoreCase(searchKey) || key.contains(":" + model.toLowerCase().trim())) {
                return entry.getValue();
            }
        }

        return "Bu model için genel servis kayıtlarında belirgin bir kronik arıza bildirimine rastlanmamıştır.";
    }
}