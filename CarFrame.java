package Project_Araba;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CarFrame extends JFrame {

    // Koyu Tema Renk Paleti
    private static final Color COLOR_BG = new Color(24, 27, 38);
    private static final Color COLOR_CARD = new Color(30, 34, 48);
    private static final Color COLOR_FIELD = new Color(38, 44, 60);
    private static final Color COLOR_BORDER = new Color(60, 68, 90);
    private static final Color COLOR_TEXT_LIGHT = Color.WHITE;
    private static final Color COLOR_PURPLE = new Color(108, 92, 231);

    // Bileşenler
    private JComboBox<String> cmbMarka;
    private JComboBox<String> cmbModel;
    private JComboBox<Integer> cmbYil;
    private JTextField txtKm;
    private JComboBox<String> cmbYakit;
    private JComboBox<String> cmbVites;
    private JTextField txtTramer;

    // Ekspertiz Şeması
    private final Map<String, JButton> parcaButonlari = new HashMap<>();
    private final Map<String, String> parcaDurumlari = new HashMap<>();

    // AI Sekmesi Bileşenleri
    private File selectedImageFile;
    private JLabel imagePreviewLabel;
    private JLabel aiResultLabel;
    private JButton analyzeBtn;

    // Pencere Sürükleme Konum Noktası
    private Point initialClick;

    public CarFrame() {
        setUndecorated(true);
        setTitle("OTO DEĞER AI - Akıllı Araç Değerleme");
        setIconImage(createLogoImage());
        setSize(980, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);

        initUI();
    }

    private Image createLogoImage() {
        BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(41, 128, 185));
        g2.fillRect(0, 0, 64, 64);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
        g2.drawString("OTO", 12, 28);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.drawString("DEĞER", 6, 50);
        g2.dispose();
        return img;
    }

    private void initUI() {
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);

        UIManager.put("TabbedPane.selected", COLOR_CARD);
        UIManager.put("TabbedPane.contentAreaColor", COLOR_BG);
        UIManager.put("TabbedPane.unselectedBackground", new Color(20, 24, 34));
        UIManager.put("TabbedPane.background", COLOR_BG);
        UIManager.put("TabbedPane.focus", COLOR_BG);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(COLOR_CARD);
        tabbedPane.setForeground(Color.WHITE);

        tabbedPane.addTab("Manuel Değerleme", createManuelDeğerlemePanel());
        tabbedPane.addTab("Yapay Zeka (Fotoğraftan Tanıma)", createAiPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18, 20, 29));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        leftPanel.setOpaque(false);

        JPanel logoBox = new JPanel(new GridLayout(2, 1));
        logoBox.setBackground(new Color(41, 128, 185));
        logoBox.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));

        JLabel lblOto = new JLabel("OTO", SwingConstants.CENTER);
        lblOto.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblOto.setForeground(Color.WHITE);

        JLabel lblDeger = new JLabel("DEĞER", SwingConstants.CENTER);
        lblDeger.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblDeger.setForeground(Color.WHITE);

        logoBox.add(lblOto);
        logoBox.add(lblDeger);

        JLabel lblTitle = new JLabel("OTO DEĞER AI - Akıllı Araç Değerleme");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(Color.WHITE);

        leftPanel.add(logoBox);
        leftPanel.add(lblTitle);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 8));
        rightPanel.setOpaque(false);

        JButton btnMin = new JButton("-");
        styleWindowControlBtn(btnMin, new Color(180, 190, 200), Color.BLACK);
        btnMin.addActionListener(e -> setState(JFrame.ICONIFIED));

        JButton btnMax = new JButton("□");
        styleWindowControlBtn(btnMax, new Color(130, 180, 220), Color.BLACK);
        btnMax.addActionListener(e -> {
            if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.NORMAL);
            } else {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        JButton btnClose = new JButton("X");
        styleWindowControlBtn(btnClose, new Color(231, 76, 60), Color.WHITE);
        btnClose.addActionListener(e -> System.exit(0));

        rightPanel.add(btnMin);
        rightPanel.add(btnMax);
        rightPanel.add(btnClose);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                setLocation(thisX + xMoved, thisY + yMoved);
            }
        });

        return panel;
    }

    private void styleWindowControlBtn(JButton btn, Color bgColor, Color fgColor) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createManuelDeğerlemePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COLOR_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        contentPanel.setOpaque(false);

        contentPanel.add(createAracBilgileriPanel());
        contentPanel.add(createEkspertizPanel());

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setOpaque(false);

        JButton btnHesapla = new JButton("DEĞERİ HESAPLA");
        btnHesapla.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnHesapla.setBackground(COLOR_PURPLE);
        btnHesapla.setForeground(Color.WHITE);
        btnHesapla.setFocusPainted(false);
        btnHesapla.setPreferredSize(new Dimension(0, 50));
        btnHesapla.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblInfo = new JLabel("Bilgileri girip şemadan araç durumunu seçtikten sonra 'Hesapla' butonuna basın.", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInfo.setForeground(new Color(180, 190, 205));

        bottomPanel.add(btnHesapla, BorderLayout.NORTH);
        bottomPanel.add(lblInfo, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        btnHesapla.addActionListener(e -> hesaplaAndGoster());

        return mainPanel;
    }

    private JPanel createAracBilgileriPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 12));
        panel.setBackground(COLOR_CARD);
        panel.setBorder(createCustomTitledBorder("Araç Bilgileri"));

        JLabel lblMarka = new JLabel("Marka:");
        styleLabel(lblMarka);
        cmbMarka = new JComboBox<>(new String[]{
            "Fiat", "Renault", "Volkswagen", "BMW", "Peugeot", 
            "Toyota", "Honda", "Ford", "Opel", "Mercedes-Benz", 
            "Audi", "Citroen", "Hyundai", "Kia", "Nissan", 
            "Skoda", "Seat", "Volvo", "Togg", "Tesla", "Chery"
        });
        cmbMarka.setMaximumRowCount(6);
        styleComboBox(cmbMarka);

        JLabel lblModel = new JLabel("Model:");
        styleLabel(lblModel);
        cmbModel = new JComboBox<>();
        cmbModel.setMaximumRowCount(6);
        styleComboBox(cmbModel);
        guncelleModelListesi();

        JLabel lblYil = new JLabel("Yıl:");
        styleLabel(lblYil);
        Integer[] yillar = new Integer[]{2024, 2021, 2018, 2015, 2012, 2009, 2006, 2003, 2000};
        cmbYil = new JComboBox<>(yillar);
        cmbYil.setMaximumRowCount(6);
        styleComboBox(cmbYil);

        JLabel lblKm = new JLabel("Kilometre:");
        styleLabel(lblKm);
        txtKm = new JTextField("100.000");
        styleTextField(txtKm);

        JLabel lblYakit = new JLabel("Yakıt Tipi:");
        styleLabel(lblYakit);
        cmbYakit = new JComboBox<>(new String[]{"Benzin", "Dizel", "LPG", "Hibrit", "Elektrik"});
        styleComboBox(cmbYakit);

        JLabel lblVites = new JLabel("Vites Tipi:");
        styleLabel(lblVites);
        cmbVites = new JComboBox<>(new String[]{"Manuel", "Otomatik"});
        styleComboBox(cmbVites);

        JLabel lblTramer = new JLabel("Tramer Kaydı (TL):");
        styleLabel(lblTramer);
        txtTramer = new JTextField("0");
        styleTextField(txtTramer);

        panel.add(lblMarka);
        panel.add(cmbMarka);
        panel.add(lblModel);
        panel.add(cmbModel);
        panel.add(lblYil);
        panel.add(cmbYil);
        panel.add(lblKm);
        panel.add(txtKm);
        panel.add(lblYakit);
        panel.add(cmbYakit);
        panel.add(lblVites);
        panel.add(cmbVites);
        panel.add(lblTramer);
        panel.add(txtTramer);

        cmbMarka.addActionListener(e -> guncelleModelListesi());

        return panel;
    }

    private JPanel createEkspertizPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(COLOR_CARD);
        panel.setBorder(createCustomTitledBorder("Araç Ekspertiz Şeması (Parçalara Tıklayarak Durum Değiştirin)"));

        // Üst Kısım: Durum Kısaltmaları Bilgilendirme Paneli (Legend)
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        legendPanel.setOpaque(false);

        JLabel lblO = new JLabel("O: Orijinal");
        lblO.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblO.setForeground(new Color(180, 190, 205));

        JLabel lblLB = new JLabel("LB: Lokal Boya");
        lblLB.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblLB.setForeground(new Color(241, 196, 15));

        JLabel lblB = new JLabel("B: Boyalı");
        lblB.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblB.setForeground(new Color(211, 84, 0));

        JLabel lblD = new JLabel("D: Değişen");
        lblD.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblD.setForeground(new Color(231, 76, 60));

        JLabel sep1 = new JLabel("•"); sep1.setForeground(COLOR_BORDER);
        JLabel sep2 = new JLabel("•"); sep2.setForeground(COLOR_BORDER);
        JLabel sep3 = new JLabel("•"); sep3.setForeground(COLOR_BORDER);

        legendPanel.add(lblO);
        legendPanel.add(sep1);
        legendPanel.add(lblLB);
        legendPanel.add(sep2);
        legendPanel.add(lblB);
        legendPanel.add(sep3);
        legendPanel.add(lblD);

        panel.add(legendPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(6, 3, 6, 6));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10));

        gridPanel.add(new JLabel());
        gridPanel.add(createParcaButon("Ön Tampon"));
        gridPanel.add(new JLabel());

        gridPanel.add(createParcaButon("Sol Ön Çamurluk"));
        gridPanel.add(createParcaButon("Kaput"));
        gridPanel.add(createParcaButon("Sağ Ön Çamurluk"));

        gridPanel.add(createParcaButon("Sol Ön Kapı"));
        gridPanel.add(createParcaButon("Tavan"));
        gridPanel.add(createParcaButon("Sağ Ön Kapı"));

        gridPanel.add(createParcaButon("Sol Arka Kapı"));
        gridPanel.add(new JLabel());
        gridPanel.add(createParcaButon("Sağ Arka Kapı"));

        gridPanel.add(createParcaButon("Sol Arka Çamurluk"));
        gridPanel.add(createParcaButon("Bagaj"));
        gridPanel.add(createParcaButon("Sağ Arka Çamurluk"));

        gridPanel.add(new JLabel());
        gridPanel.add(createParcaButon("Arka Tampon"));
        gridPanel.add(new JLabel());

        panel.add(gridPanel, BorderLayout.CENTER);
        return panel;
    }

    private JButton createParcaButon(String parcaAdi) {
        JButton btn = new JButton(parcaAdi + " [ O ]");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(100, 110, 125));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        parcaDurumlari.put(parcaAdi, "O");
        parcaButonlari.put(parcaAdi, btn);

        btn.addActionListener(e -> {
            String mevcut = parcaDurumlari.get(parcaAdi);
            if ("O".equals(mevcut)) {
                parcaDurumlari.put(parcaAdi, "LB");
                btn.setText(parcaAdi + " [ LB ]");
                btn.setBackground(new Color(241, 196, 15));
                btn.setForeground(Color.BLACK);
            } else if ("LB".equals(mevcut)) {
                parcaDurumlari.put(parcaAdi, "B");
                btn.setText(parcaAdi + " [ B ]");
                btn.setBackground(new Color(211, 84, 0));
                btn.setForeground(Color.WHITE);
            } else if ("B".equals(mevcut)) {
                parcaDurumlari.put(parcaAdi, "D");
                btn.setText(parcaAdi + " [ D ]");
                btn.setBackground(new Color(192, 57, 43));
                btn.setForeground(Color.WHITE);
            } else {
                parcaDurumlari.put(parcaAdi, "O");
                btn.setText(parcaAdi + " [ O ]");
                btn.setBackground(new Color(100, 110, 125));
                btn.setForeground(Color.WHITE);
            }
        });

        return btn;
    }

    private JPanel createAiPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COLOR_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        imagePreviewLabel = new JLabel("Görsel Yüklenmedi", SwingConstants.CENTER);
        imagePreviewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        imagePreviewLabel.setForeground(COLOR_TEXT_LIGHT);
        imagePreviewLabel.setBorder(new LineBorder(COLOR_BORDER, 2, true));
        imagePreviewLabel.setPreferredSize(new Dimension(500, 300));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton uploadBtn = new JButton("Fotoğraf Yükle");
        styleAiButton(uploadBtn, COLOR_PURPLE);

        analyzeBtn = new JButton("Yapay Zeka İle Analiz Et");
        styleAiButton(analyzeBtn, new Color(46, 204, 113));
        analyzeBtn.setEnabled(false);

        buttonPanel.add(uploadBtn);
        buttonPanel.add(analyzeBtn);

        aiResultLabel = new JLabel("<html><center>Fotoğraf yüklendikten sonra AI aracı otomatik tanıyacaktır.</center></html>", SwingConstants.CENTER);
        aiResultLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        aiResultLabel.setForeground(COLOR_TEXT_LIGHT);

        mainPanel.add(imagePreviewLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(aiResultLabel, BorderLayout.SOUTH);

        uploadBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = chooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(new ImageIcon(selectedImageFile.getAbsolutePath())
                        .getImage().getScaledInstance(440, 260, Image.SCALE_SMOOTH));
                imagePreviewLabel.setIcon(icon);
                imagePreviewLabel.setText("");
                analyzeBtn.setEnabled(true);
            }
        });

        analyzeBtn.addActionListener(e -> {
            if (selectedImageFile != null) {
                String marka = (String) cmbMarka.getSelectedItem();
                String model = (String) cmbModel.getSelectedItem();
                int yil = (Integer) cmbYil.getSelectedItem();

                double hesaplananFiyat = AracKatalogu.getTabanFiyat(marka, model, yil);

                aiResultLabel.setText("<html><center>AI Tespiti Tamamlandı: " + marka + " " + model + " (" + yil + ")</center></html>");

                SonucPenceresi dialog = new SonucPenceresi(this, marka, model, yil, hesaplananFiyat);
                dialog.setVisible(true);
            }
        });

        return mainPanel;
    }

    private void hesaplaAndGoster() {
        String marka = (String) cmbMarka.getSelectedItem();
        String model = (String) cmbModel.getSelectedItem();
        int yil = (Integer) cmbYil.getSelectedItem();
        String yakit = (String) cmbYakit.getSelectedItem();
        String vites = (String) cmbVites.getSelectedItem();

        double km = 100000;
        try {
            String rawKm = txtKm.getText().trim().replaceAll("[^0-9]", "");
            km = Double.parseDouble(rawKm);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir kilometre değeri giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double tramer = 0;
        try {
            String rawTramer = txtTramer.getText().trim().replaceAll("[^0-9]", "");
            if (!rawTramer.isEmpty()) {
                tramer = Double.parseDouble(rawTramer);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir Tramer tutarı giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double tabanFiyat = AracKatalogu.getTabanFiyat(marka, model, yil);

        double carpan = 1.0;
        if ("Otomatik".equals(vites)) carpan += 0.04;
        if ("Elektrik".equals(yakit)) carpan += 0.08;
        else if ("Hibrit".equals(yakit)) carpan += 0.05;
        else if ("Dizel".equals(yakit)) carpan += 0.02;
        else if ("LPG".equals(yakit)) carpan -= 0.02;

        tabanFiyat *= carpan;

        double kmDuselisi = (km / 10000.0) * 2500.0;
        double tramerDuselisi = tramer * 0.50;

        double ekspertizDuselisi = 0;
        for (Map.Entry<String, String> entry : parcaDurumlari.entrySet()) {
            String parca = entry.getKey();
            String durum = entry.getValue();

            if ("O".equals(durum)) continue;

            boolean tamponVeyaCamurluk = parca.contains("Tampon") || parca.contains("Çamurluk");

            double orani = 0;
            if (tamponVeyaCamurluk) {
                if ("LB".equals(durum)) orani = 0.001;
                else if ("B".equals(durum)) orani = 0.0025;
                else if ("D".equals(durum)) orani = 0.005;
            } else {
                if ("LB".equals(durum)) orani = 0.003;
                else if ("B".equals(durum)) orani = 0.008;
                else if ("D".equals(durum)) orani = 0.018;

                if ("Tavan".equals(parca) || "Kaput".equals(parca)) {
                    orani *= 1.3;
                }
            }

            ekspertizDuselisi += tabanFiyat * orani;
        }

        double hesaplananFiyat = Math.max(tabanFiyat - kmDuselisi - ekspertizDuselisi - tramerDuselisi, 50000.0);

        SonucPenceresi dialog = new SonucPenceresi(this, marka, model, yil, hesaplananFiyat);
        dialog.setVisible(true);
    }

    private void guncelleModelListesi() {
        String secilenMarka = (String) cmbMarka.getSelectedItem();
        cmbModel.removeAllItems();

        if (secilenMarka == null) return;

        switch (secilenMarka) {
            case "Fiat":
                cmbModel.addItem("Egea"); cmbModel.addItem("Egea Cross"); cmbModel.addItem("500"); cmbModel.addItem("Panda"); cmbModel.addItem("Doblo");
                break;
            case "Renault":
                cmbModel.addItem("Megane"); cmbModel.addItem("Clio"); cmbModel.addItem("Captur"); cmbModel.addItem("Fluence");
                break;
            case "Volkswagen":
                cmbModel.addItem("Golf"); cmbModel.addItem("Passat"); cmbModel.addItem("Polo"); cmbModel.addItem("Tiguan");
                break;
            case "BMW":
                cmbModel.addItem("320i"); cmbModel.addItem("118i"); cmbModel.addItem("520i"); cmbModel.addItem("X1");
                break;
            case "Peugeot":
                cmbModel.addItem("208"); cmbModel.addItem("301"); cmbModel.addItem("2008"); cmbModel.addItem("3008");
                break;
            case "Toyota":
                cmbModel.addItem("Corolla"); cmbModel.addItem("Yaris"); cmbModel.addItem("C-HR");
                break;
            case "Honda":
                cmbModel.addItem("Civic"); cmbModel.addItem("City"); cmbModel.addItem("CR-V");
                break;
            default:
                cmbModel.addItem("Standart Model");
                break;
        }
    }

    private TitledBorder createCustomTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(new LineBorder(COLOR_BORDER, 1), title);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));
        border.setTitleColor(COLOR_TEXT_LIGHT);
        return border;
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(COLOR_TEXT_LIGHT);
    }

    private void styleComboBox(JComboBox<?> box) {
        box.setFont(new Font("Segoe UI", Font.BOLD, 13));
        box.setBackground(COLOR_FIELD);
        box.setForeground(Color.WHITE);

        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(true);
                if (isSelected && index >= 0) {
                    label.setBackground(COLOR_PURPLE);
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(COLOR_FIELD);
                    label.setForeground(Color.WHITE);
                }
                return label;
            }
        });
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.BOLD, 14));
        field.setBackground(COLOR_FIELD);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private void styleAiButton(JButton btn, Color bgColor) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}