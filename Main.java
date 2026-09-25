package Project_Araba;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        try {
            // Bulut sunucuları (Render, Railway vb.) portu otomatik atar.
            // Yerelde çalışırken 8085 portunu varsayılan alır.
            String portEnv = System.getenv("PORT");
            int port = (portEnv != null) ? Integer.parseInt(portEnv) : 8085;

            // "0.0.0.0" adresi dış ağlardan gelen internet trafiğini kabul eder.
            HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

            server.createContext("/", new MainHandler());
            server.createContext("/api/hesapla", new HesaplaHandler());
            server.createContext("/api/markalar", new MarkalarHandler());
            server.createContext("/api/modeller", new ModellerHandler());

            server.setExecutor(null);
            System.out.println("====================================================");
            System.out.println("🚀 OTO DEĞER AI WEB SUNUCUSU BAŞLATILDI!");
            System.out.println("🌐 Port: " + port);
            System.out.println("💻 Yerel Erişim: http://localhost:" + port);
            System.out.println("====================================================");
            server.start();
        } catch (Exception e) {
            System.err.println("❌ Sunucu başlatılırken hata oluştu:");
            e.printStackTrace();
        }
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

    static class MarkalarHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = "[\"Fiat\", \"Renault\", \"Volkswagen\", \"BMW\", \"Mercedes\", \"Audi\", \"Ford\", \"Toyota\", \"Hyundai\"]"; 
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    static class ModellerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String json = "[\"Model 1\", \"Model 2\"]";
            
            if (query != null && query.contains("Fiat")) {
                json = "[\"Egea\", \"Linea\", \"Panda\", \"500\", \"Doblo\", \"Fiorino\"]";
            } else if (query != null && query.contains("Renault")) {
                json = "[\"Clio\", \"Megane\", \"Fluence\", \"Symbol\", \"Taliant\"]";
            } else if (query != null && query.contains("Volkswagen")) {
                json = "[\"Golf\", \"Passat\", \"Polo\", \"Tiguan\", \"Jetta\"]";
            }

            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
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
                
                String jsonResponse = "{\"fiyat\": \"850.000 TL\", \"skor\": 88, \"durum\": \"Başarılı\"}";
                
                byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            }
        }
    }

    private static String getHTMLContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"tr\">\n");
        sb.append("<head>\n");
        sb.append("    <meta charset=\"UTF-8\">\n");
        sb.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\">\n");
        sb.append("    <title>OTO DEĞER AI - Akıllı Araç Değerleme</title>\n");
        sb.append("    <style>\n");
        sb.append("        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }\n");
        sb.append("        body { background-color: #0f172a; color: #f8fafc; padding: 15px; }\n");
        sb.append("        .header { text-align: center; padding: 15px 0; font-size: 20px; font-weight: bold; color: #38bdf8; border-bottom: 1px solid #1e293b; margin-bottom: 20px; }\n");
        sb.append("        .container { display: flex; flex-direction: column; gap: 20px; max-width: 900px; margin: 0 auto; }\n");
        sb.append("        @media (min-width: 768px) { .container { display: grid; grid-template-columns: 1fr 1fr; } }\n");
        sb.append("        .card { background: #1e293b; padding: 20px; border-radius: 12px; border: 1px solid #334155; }\n");
        sb.append("        .card h3 { color: #94a3b8; font-size: 14px; margin-bottom: 15px; text-transform: uppercase; letter-spacing: 1px; }\n");
        sb.append("        .form-group { margin-bottom: 12px; }\n");
        sb.append("        .form-group label { display: block; font-size: 12px; color: #cbd5e1; margin-bottom: 5px; }\n");
        sb.append("        .form-group input, .form-group select { width: 100%; padding: 10px; background: #0f172a; border: 1px solid #334155; color: #fff; border-radius: 6px; font-size: 14px; outline: none; }\n");
        sb.append("        .form-group input:focus, .form-group select:focus { border-color: #38bdf8; }\n");
        sb.append("        .schema-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 8px; }\n");
        sb.append("        .part-btn { background: #334155; color: #fff; border: 1px solid #475569; padding: 12px 5px; border-radius: 6px; text-align: center; font-size: 11px; cursor: pointer; user-select: none; transition: 0.2s; }\n");
        sb.append("        .part-btn:active { transform: scale(0.96); }\n");
        sb.append("        .part-btn.O { background: #334155; color: #cbd5e1; }\n");
        sb.append("        .part-btn.LB { background: #d97706; color: #fff; }\n");
        sb.append("        .part-btn.B { background: #dc2626; color: #fff; }\n");
        sb.append("        .part-btn.D { background: #7c3aed; color: #fff; }\n");
        sb.append("        .legend { display: flex; justify-content: space-around; margin-bottom: 15px; font-size: 10px; }\n");
        sb.append("        .legend span { padding: 3px 6px; border-radius: 4px; }\n");
        sb.append("        .btn-submit { width: 100%; background: #6366f1; color: white; border: none; padding: 15px; font-size: 16px; font-weight: bold; border-radius: 8px; cursor: pointer; margin-top: 15px; transition: 0.3s; }\n");
        sb.append("        .btn-submit:hover { background: #4f46e5; }\n");
        sb.append("        .modal { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.8); justify-content: center; align-items: center; padding: 20px; }\n");
        sb.append("        .modal-content { background: #1e293b; padding: 25px; border-radius: 12px; text-align: center; max-width: 400px; width: 100%; border: 1px solid #38bdf8; }\n");
        sb.append("        .modal-price { font-size: 28px; font-weight: bold; color: #4ade80; margin: 15px 0; }\n");
        sb.append("        .modal-close { background: #334155; color: white; border: none; padding: 10px 20px; border-radius: 6px; cursor: pointer; margin-top: 10px; }\n");
        sb.append("    </style>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("    <div class=\"header\">🚘 OTO DEĞER AI</div>\n");
        sb.append("    <div class=\"container\">\n");
        sb.append("        <div class=\"card\">\n");
        sb.append("            <h3>Araç Bilgileri</h3>\n");
        sb.append("            <div class=\"form-group\"><label>Marka</label><select id=\"marka\" onchange=\"modelYukle()\"></select></div>\n");
        sb.append("            <div class=\"form-group\"><label>Model</label><select id=\"model\"></select></div>\n");
        sb.append("            <div class=\"form-group\"><label>Yıl</label><input type=\"number\" id=\"yil\" value=\"2024\"></div>\n");
        sb.append("            <div class=\"form-group\"><label>Kilometre</label><input type=\"number\" id=\"km\" value=\"100000\"></div>\n");
        sb.append("            <div class=\"form-group\"><label>Yakıt Tipi</label><select id=\"yakit\"><option>Benzin</option><option>Dizel</option><option>LPG</option><option>Hibrit</option><option>Elektrik</option></select></div>\n");
        sb.append("            <div class=\"form-group\"><label>Vites Tipi</label><select id=\"vites\"><option>Manuel</option><option>Otomatik</option></select></div>\n");
        sb.append("            <div class=\"form-group\"><label>Tramer Kaydı (TL)</label><input type=\"number\" id=\"tramer\" value=\"0\"></div>\n");
        sb.append("        </div>\n");
        sb.append("        <div class=\"card\">\n");
        sb.append("            <h3>Araç Ekspertiz Şeması</h3>\n");
        sb.append("            <div class=\"legend\">\n");
        sb.append("                <span style=\"background:#334155;\">O: Orijinal</span>\n");
        sb.append("                <span style=\"background:#d97706;\">LB: Lokal Boya</span>\n");
        sb.append("                <span style=\"background:#dc2626;\">B: Boyalı</span>\n");
        sb.append("                <span style=\"background:#7c3aed;\">D: Değişen</span>\n");
        sb.append("            </div>\n");
        sb.append("            <div class=\"schema-grid\">\n");
        sb.append("                <div></div><div class=\"part-btn O\" onclick=\"toggleState(this)\">Ön Tampon<br>[ O ]</div><div></div>\n");
        sb.append("                <div class=\"part-btn O\" onclick=\"toggleState(this)\">Sol Ön Çam.<br>[ O ]</div><div class=\"part-btn O\" onclick=\"toggleState(this)\">Kaput<br>[ O ]</div><div class=\"part-btn O\" onclick=\"toggleState(this)\">Sağ Ön Çam.<br>[ O ]</div>\n");
        sb.append("                <div class=\"part-btn O\" onclick=\"toggleState(this)\">Sol Ön Kapı<br>[ O ]</div><div class=\"part-btn O\" onclick=\"toggleState(this)\">Tavan<br>[ O ]</div><div class=\"part-btn O\" onclick=\"toggleState(this)\">Sağ Ön Kapı<br>[ O ]</div>\n");
        sb.append("                <div class=\"part-btn O\" onclick=\"toggleState(this)\">Sol Arka Kapı<br>[ O ]</div><div></div><div class=\"part-btn O\" onclick=\"toggleState(this)\">Sağ Arka Kapı<br>[ O ]</div>\n");
        sb.append("                <div class=\"part-btn O\" onclick=\"toggleState(this)\">Sol Arka Çam.<br>[ O ]</div><div class=\"part-btn O\" onclick=\"toggleState(this)\">Bagaj<br>[ O ]</div><div class=\"part-btn O\" onclick=\"toggleState(this)\">Sağ Arka Çam.<br>[ O ]</div>\n");
        sb.append("                <div></div><div class=\"part-btn O\" onclick=\"toggleState(this)\">Arka Tampon<br>[ O ]</div><div></div>\n");
        sb.append("            </div>\n");
        sb.append("            <button class=\"btn-submit\" onclick=\"hesapla()\">DEĞERİ HESAPLA</button>\n");
        sb.append("        </div>\n");
        sb.append("    </div>\n");
        sb.append("    <div id=\"resultModal\" class=\"modal\">\n");
        sb.append("        <div class=\"modal-content\">\n");
        sb.append("            <h3>Tahmini Piyasa Değeri</h3>\n");
        sb.append("            <div id=\"modalPrice\" class=\"modal-price\">0 TL</div>\n");
        sb.append("            <p style=\"font-size: 12px; color: #94a3b8;\">Kondisyon Skoru: %<span id=\"modalScore\">0</span></p>\n");
        sb.append("            <button class=\"modal-close\" onclick=\"closeModal()\">Kapat</button>\n");
        sb.append("        </div>\n");
        sb.append("    </div>\n");
        sb.append("    <script>\n");
        sb.append("        const states = ['O', 'LB', 'B', 'D'];\n");
        sb.append("        window.onload = function() {\n");
        sb.append("            fetch('/api/markalar')\n");
        sb.append("            .then(res => res.json())\n");
        sb.append("            .then(markalar => {\n");
        sb.append("                let mSelect = document.getElementById('marka');\n");
        sb.append("                mSelect.innerHTML = '';\n");
        sb.append("                markalar.forEach(m => { mSelect.innerHTML += `<option value=\"${m}\">${m}</option>`; });\n");
        sb.append("                modelYukle();\n");
        sb.append("            });\n");
        sb.append("        };\n");
        sb.append("        function modelYukle() {\n");
        sb.append("            let secilenMarka = document.getElementById('marka').value;\n");
        sb.append("            fetch('/api/modeller?marka=' + encodeURIComponent(secilenMarka))\n");
        sb.append("            .then(res => res.json())\n");
        sb.append("            .then(modeller => {\n");
        sb.append("                let modSelect = document.getElementById('model');\n");
        sb.append("                modSelect.innerHTML = '';\n");
        sb.append("                modeller.forEach(m => { modSelect.innerHTML += `<option value=\"${m}\">${m}</option>`; });\n");
        sb.append("            });\n");
        sb.append("        }\n");
        sb.append("        function toggleState(btn) {\n");
        sb.append("            let currentState = states.find(s => btn.classList.contains(s)) || 'O';\n");
        sb.append("            let nextIndex = (states.indexOf(currentState) + 1) % states.length;\n");
        sb.append("            let nextState = states[nextIndex];\n");
        sb.append("            btn.classList.remove('O', 'LB', 'B', 'D');\n");
        sb.append("            btn.classList.add(nextState);\n");
        sb.append("            let title = btn.innerHTML.split('<br>')[0];\n");
        sb.append("            btn.innerHTML = `${title}<br>[ ${nextState} ]`;\n");
        sb.append("        }\n");
        sb.append("        function hesapla() {\n");
        sb.append("            fetch('/api/hesapla', { method: 'POST', body: JSON.stringify({ test: 1 }) })\n");
        sb.append("            .then(res => res.json())\n");
        sb.append("            .then(data => {\n");
        sb.append("                document.getElementById('modalPrice').innerText = data.fiyat;\n");
        sb.append("                document.getElementById('modalScore').innerText = data.skor;\n");
        sb.append("                document.getElementById('resultModal').style.display = 'flex';\n");
        sb.append("            });\n");
        sb.append("        }\n");
        sb.append("        function closeModal() {\n");
        sb.append("            document.getElementById('resultModal').style.display = 'none';\n");
        sb.append("        }\n");
        sb.append("    </script>\n");
        sb.append("</body>\n");
        sb.append("</html>\n");
        return sb.toString();
    }
}