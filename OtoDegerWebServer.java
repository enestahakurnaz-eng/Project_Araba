package Project_Araba;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Locale;

public class OtoDegerWebServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        String portEnv = System.getenv("PORT");
        if (portEnv != null && !portEnv.isEmpty()) {
            port = Integer.parseInt(portEnv);
        }

        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

        server.createContext("/", new MainHandler());
        server.createContext("/api/hesapla", new HesaplaHandler());

        server.setExecutor(null);
        System.out.println("====================================================");
        System.out.println("🚀 OTO DEĞER AI WEB SUNUCUSU BAŞLATILDI!");
        System.out.println("Port: " + port);
        System.out.println("====================================================");
        server.start();
    }

    static class MainHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String htmlResponse = getHTMLContent();
            byte[] bytes = htmlResponse.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    static class HesaplaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                // Gelen JSON parametrelerini basitçe ayrıştırıyoruz
                String marka = parseJsonVal(body, "marka", "Fiat");
                String model = parseJsonVal(body, "model", "Egea");
                int yil = parseJsonInt(body, "yil", 2024);
                int km = parseJsonInt(body, "km", 100000);
                double tramer = parseJsonDouble(body, "tramer", 0);

                // AracKatalogu üzerinden dinamik taban fiyat çekimi
                double tabanFiyat = AracKatalogu.getTabanFiyat(marka, model, yil);

                // KM ve Tramer Düşüşü
                double kmEtkisi = Math.max(0, (km / 10000.0) * 0.015); 
                double nihaiFiyat = tabanFiyat * (1.0 - kmEtkisi) - tramer;
                if (nihaiFiyat < 50000) nihaiFiyat = 50000;

                int kondisyonSkoru = Math.max(20, Math.min(100, (int)(100 - (km / 5000.0) - (tramer / 10000.0))));

                NumberFormat formatter = NumberFormat.getInstance(new Locale("tr", "TR"));
                String formatliFiyat = formatter.format((long) nihaiFiyat) + " TL";

                String jsonResponse = String.format("{\"fiyat\": \"%s\", \"skor\": %d, \"durum\": \"Başarılı\"}", formatliFiyat, kondisyonSkoru);

                byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            }
        }

        private String parseJsonVal(String json, String key, String def) {
            try {
                String pattern = "\"" + key + "\":\"";
                int start = json.indexOf(pattern);
                if (start == -1) return def;
                start += pattern.length();
                int end = json.indexOf("\"", start);
                return json.substring(start, end);
            } catch (Exception e) { return def; }
        }

        private int parseJsonInt(String json, String key, int def) {
            try {
                String pattern = "\"" + key + "\":";
                int start = json.indexOf(pattern);
                if (start == -1) return def;
                start += pattern.length();
                int end = json.indexOf(",", start);
                if (end == -1) end = json.indexOf("}", start);
                return Integer.parseInt(json.substring(start, end).trim().replaceAll("[^0-9]", ""));
            } catch (Exception e) { return def; }
        }

        private double parseJsonDouble(String json, String key, double def) {
            try {
                String pattern = "\"" + key + "\":";
                int start = json.indexOf(pattern);
                if (start == -1) return def;
                start += pattern.length();
                int end = json.indexOf(",", start);
                if (end == -1) end = json.indexOf("}", start);
                return Double.parseDouble(json.substring(start, end).trim().replaceAll("[^0-9.]", ""));
            } catch (Exception e) { return def; }
        }
    }

    private static String getHTMLContent() {
        return """
        <!DOCTYPE html>
        <html lang="tr">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
            <title>OTO DEĞER AI - Akıllı Araç Değerleme</title>
            <style>
                * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
                body { background-color: #0f172a; color: #f8fafc; padding: 15px; }
                .header { text-align: center; padding: 15px 0; font-size: 20px; font-weight: bold; color: #38bdf8; border-bottom: 1px solid #1e293b; margin-bottom: 20px; }
                .container { display: flex; flex-direction: column; gap: 20px; max-width: 900px; margin: 0 auto; }
                @media (min-width: 768px) { .container { display: grid; grid-template-columns: 1fr 1fr; } }
                .card { background: #1e293b; padding: 20px; border-radius: 12px; border: 1px solid #334155; }
                .card h3 { color: #94a3b8; font-size: 14px; margin-bottom: 15px; text-transform: uppercase; letter-spacing: 1px; }
                .form-group { margin-bottom: 12px; }
                .form-group label { display: block; font-size: 12px; color: #cbd5e1; margin-bottom: 5px; }
                .form-group input, .form-group select { width: 100%; padding: 10px; background: #0f172a; border: 1px solid #334155; color: #fff; border-radius: 6px; font-size: 14px; outline: none; }
                .form-group input:focus, .form-group select:focus { border-color: #38bdf8; }
                
                .schema-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 8px; }
                .part-btn { background: #334155; color: #fff; border: 1px solid #475569; padding: 12px 5px; border-radius: 6px; text-align: center; font-size: 11px; cursor: pointer; user-select: none; transition: 0.2s; }
                .part-btn:active { transform: scale(0.96); }
                .part-btn.O { background: #334155; color: #cbd5e1; }
                .part-btn.LB { background: #d97706; color: #fff; }
                .part-btn.B { background: #dc2626; color: #fff; }
                .part-btn.D { background: #7c3aed; color: #fff; }
                
                .legend { display: flex; justify-content: space-around; margin-bottom: 15px; font-size: 10px; }
                .legend span { padding: 3px 6px; border-radius: 4px; }
                
                .btn-submit { width: 100%; background: #6366f1; color: white; border: none; padding: 15px; font-size: 16px; font-weight: bold; border-radius: 8px; cursor: pointer; margin-top: 15px; transition: 0.3s; }
                .btn-submit:hover { background: #4f46e5; }
                
                .modal { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.8); justify-content: center; align-items: center; padding: 20px; }
                .modal-content { background: #1e293b; padding: 25px; border-radius: 12px; text-align: center; max-width: 400px; width: 100%; border: 1px solid #38bdf8; }
                .modal-price { font-size: 28px; font-weight: bold; color: #4ade80; margin: 15px 0; }
                .modal-close { background: #334155; color: white; border: none; padding: 10px 20px; border-radius: 6px; cursor: pointer; margin-top: 10px; }
            </style>
        </head>
        <body>

            <div class="header">🚘 OTO DEĞER AI</div>

            <div class="container">
                <div class="card">
                    <h3>Araç Bilgileri</h3>
                    <div class="form-group">
                        <label>Marka</label>
                        <select id="marka" onchange="updateModels()"></select>
                    </div>
                    <div class="form-group">
                        <label>Model</label>
                        <select id="model"></select>
                    </div>
                    <div class="form-group">
                        <label>Yıl</label>
                        <input type="number" id="yil" value="2024">
                    </div>
                    <div class="form-group">
                        <label>Kilometre</label>
                        <input type="number" id="km" value="100000">
                    </div>
                    <div class="form-group">
                        <label>Yakıt Tipi</label>
                        <select id="yakit"><option>Benzin</option><option>Dizel</option><option>LPG</option><option>Hibrit</option><option>Elektrik</option></select>
                    </div>
                    <div class="form-group">
                        <label>Vites Tipi</label>
                        <select id="vites"><option>Manuel</option><option>Otomatik</option></select>
                    </div>
                    <div class="form-group">
                        <label>Tramer Kaydı (TL)</label>
                        <input type="number" id="tramer" value="0">
                    </div>
                </div>

                <div class="card">
                    <h3>Araç Ekspertiz Şeması</h3>
                    <div class="legend">
                        <span style="background:#334155;">O: Orijinal</span>
                        <span style="background:#d97706;">LB: Lokal Boya</span>
                        <span style="background:#dc2626;">B: Boyalı</span>
                        <span style="background:#7c3aed;">D: Değişen</span>
                    </div>

                    <div class="schema-grid">
                        <div></div>
                        <div class="part-btn O" onclick="toggleState(this)">Ön Tampon<br>[ O ]</div>
                        <div></div>

                        <div class="part-btn O" onclick="toggleState(this)">Sol Ön Çam.<br>[ O ]</div>
                        <div class="part-btn O" onclick="toggleState(this)">Kaput<br>[ O ]</div>
                        <div class="part-btn O" onclick="toggleState(this)">Sağ Ön Çam.<br>[ O ]</div>

                        <div class="part-btn O" onclick="toggleState(this)">Sol Ön Kapı<br>[ O ]</div>
                        <div class="part-btn O" onclick="toggleState(this)">Tavan<br>[ O ]</div>
                        <div class="part-btn O" onclick="toggleState(this)">Sağ Ön Kapı<br>[ O ]</div>

                        <div class="part-btn O" onclick="toggleState(this)">Sol Arka Kapı<br>[ O ]</div>
                        <div></div>
                        <div class="part-btn O" onclick="toggleState(this)">Sağ Arka Kapı<br>[ O ]</div>

                        <div class="part-btn O" onclick="toggleState(this)">Sol Arka Çam.<br>[ O ]</div>
                        <div class="part-btn O" onclick="toggleState(this)">Bagaj<br>[ O ]</div>
                        <div class="part-btn O" onclick="toggleState(this)">Sağ Arka Çam.<br>[ O ]</div>

                        <div></div>
                        <div class="part-btn O" onclick="toggleState(this)">Arka Tampon<br>[ O ]</div>
                        <div></div>
                    </div>

                    <button class="btn-submit" onclick="hesapla()">DEĞERİ HESAPLA</button>
                </div>
            </div>

            <div id="resultModal" class="modal">
                <div class="modal-content">
                    <h3>Tahmini Piyasa Değeri</h3>
                    <div id="modalPrice" class="modal-price">0 TL</div>
                    <p style="font-size: 12px; color: #94a3b8;">Kondisyon Skoru: %<span id="modalScore">0</span></p>
                    <button class="modal-close" onclick="closeModal()">Kapat</button>
                </div>
            </div>

            <script>
                const katalog = {
                    "Fiat": ["Egea", "Egea Cross", "500", "Panda", "Tipo", "Doblo", "Linea", "Marea", "Palio", "Albea"],
                    "Renault": ["Megane", "Clio", "Captur", "Kadjar", "Talisman", "Symbol", "Taliant", "Austral", "19", "Fluence", "Toros"],
                    "Tofas": ["Sahin", "Dogan", "Kartal", "Murat 131"],
                    "Volkswagen": ["Golf", "Passat", "Polo", "Tiguan", "T-Cross", "Jetta", "Arteon", "T-Roc", "Bora"],
                    "BMW": ["320i", "118i", "218i Gran Coupe", "520i", "730i", "X1", "X3", "X5", "316i"],
                    "Peugeot": ["208", "301", "2008", "3008", "508", "306", "405"],
                    "Honda": ["Civic", "City", "CR-V", "HR-V", "Accord"],
                    "Toyota": ["Corolla", "Corolla Cross", "C-HR", "RAV4", "Camry", "Yaris", "Yaris Cross", "Avensis"],
                    "Hyundai": ["i20", "i10", "Elantra", "Tucson", "Bayon", "Kona", "Accent Blue", "Accent Era"],
                    "Kia": ["Rio", "Ceed", "Sportage", "Stonic", "Picanto", "Niro"],
                    "Ford": ["Focus", "Fiesta", "Kuga", "Puma", "Mondeo", "Courier", "Escort"],
                    "Opel": ["Corsa", "Astra", "Crossland", "Grandland", "Insignia", "Vectra"],
                    "Citroen": ["C3", "C4", "C-Elysee", "C5 Aircross"],
                    "Dacia": ["Sandero", "Duster", "Logan", "Jogger"],
                    "Skoda": ["Octavia", "Fabia", "Superb", "Kamiq", "Karoq"],
                    "Seat": ["Ibiza", "Leon", "Arona", "Ateca"],
                    "Nissan": ["Qashqai", "Juke", "Micra", "X-Trail"],
                    "Mercedes-Benz": ["A 180", "C 200", "E 200", "GLA 200", "GLC 200", "S 500"],
                    "Audi": ["A3", "A4", "A6", "Q3", "Q5", "Q7"],
                    "Mazda": ["Mazda2", "Mazda3", "CX-30", "CX-5"],
                    "Mitsubishi": ["Space Star", "ASX", "Outlander"],
                    "Suzuki": ["Swift", "Vitara", "S-Cross"],
                    "Chevrolet": ["Spark", "Cruze"],
                    "Volvo": ["XC40", "XC60", "S60", "S90"],
                    "Jeep": ["Renegade", "Compass", "Grand Cherokee"],
                    "Land Rover": ["Discovery Sport", "Range Rover Evoque", "Defender"],
                    "Mini": ["Cooper", "Countryman"],
                    "Alfa Romeo": ["Giulia", "Stelvio"],
                    "Lexus": ["NX", "RX", "ES"],
                    "Porsche": ["Macan", "Cayenne", "911"],
                    "Tesla": ["Model 3", "Model Y"],
                    "MG": ["ZS", "HS", "MG5"],
                    "Togg": ["T10X"],
                    "Chery": ["Tiggo 7 Pro", "Tiggo 8 Pro"],
                    "Subaru": ["XV", "Forester"],
                    "DS": ["DS3", "DS7"],
                    "Cupra": ["Formentor", "Leon"],
                    "Jaguar": ["XE", "F-Pace"],
                    "Maserati": ["Ghibli"],
                    "Isuzu": ["D-Max"],
                    "SsangYong": ["Tivoli", "Korando"]
                };

                function initKatalog() {
                    const markaSelect = document.getElementById('marka');
                    markaSelect.innerHTML = '';
                    for (let marka in katalog) {
                        let opt = document.createElement('option');
                        opt.value = marka;
                        opt.innerText = marka;
                        markaSelect.appendChild(opt);
                    }
                    updateModels();
                }

                function updateModels() {
                    const marka = document.getElementById('marka').value;
                    const modelSelect = document.getElementById('model');
                    modelSelect.innerHTML = '';
                    if (katalog[marka]) {
                        katalog[marka].forEach(model => {
                            let opt = document.createElement('option');
                            opt.value = model;
                            opt.innerText = model;
                            modelSelect.appendChild(opt);
                        });
                    }
                }

                window.onload = initKatalog;

                const states = ['O', 'LB', 'B', 'D'];
                function toggleState(btn) {
                    let currentState = states.find(s => btn.classList.contains(s)) || 'O';
                    let nextIndex = (states.indexOf(currentState) + 1) % states.length;
                    let nextState = states[nextIndex];

                    btn.classList.remove('O', 'LB', 'B', 'D');
                    btn.classList.add(nextState);

                    let title = btn.innerHTML.split('<br>')[0];
                    btn.innerHTML = `${title}<br>[ ${nextState} ]`;
                }

                function hesapla() {
                    const payload = {
                        marka: document.getElementById('marka').value,
                        model: document.getElementById('model').value,
                        yil: parseInt(document.getElementById('yil').value) || 2024,
                        km: parseInt(document.getElementById('km').value) || 0,
                        tramer: parseFloat(document.getElementById('tramer').value) || 0
                    };

                    fetch('/api/hesapla', { 
                        method: 'POST', 
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(payload) 
                    })
                    .then(res => res.json())
                    .then(data => {
                        document.getElementById('modalPrice').innerText = data.fiyat;
                        document.getElementById('modalScore').innerText = data.skor;
                        document.getElementById('resultModal').style.display = 'flex';
                    });
                }

                function closeModal() {
                    document.getElementById('resultModal').style.display = 'none';
                }
            </script>
        </body>
        </html>
        """;
    }
}