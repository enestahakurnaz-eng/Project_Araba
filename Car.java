package Project_Araba;

public class Car {
    private String brand;
    private String model;
    private int year;
    private int kilometer;
    private int paintedParts;
    private int replacedParts;
    private String imagePath;

    public Car() {}

    public Car(String brand, String model, int year, int kilometer, int paintedParts, int replacedParts) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.kilometer = kilometer;
        this.paintedParts = paintedParts;
        this.replacedParts = replacedParts;
    }

    // Getter ve Setter Metotları
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getKilometer() { return kilometer; }
    public void setKilometer(int kilometer) { this.kilometer = kilometer; }

    public int getPaintedParts() { return paintedParts; }
    public void setPaintedParts(int paintedParts) { this.paintedParts = paintedParts; }

    public int getReplacedParts() { return replacedParts; }
    public void setReplacedParts(int replacedParts) { this.replacedParts = replacedParts; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}