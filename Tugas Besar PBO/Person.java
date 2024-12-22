public class Person { // Mendefinisikan kelas Person
    private String name; // Menyimpan nama orang
    private double height; // Menyimpan tinggi orang

    public Person(String name, double height) { // Constructor untuk menginisialisasi objek Person
        this.name = name; // Mengatur nama
        this.height = height; // Mengatur tinggi
    }

    public String getName() { // Metode untuk mendapatkan nama
        return name; // Mengembalikan nama
    }

    public void setName(String name) { // Metode untuk mengatur nama
        this.name = name; // Mengatur nama
    }

    public double getHeight() { // Metode untuk mendapatkan tinggi
        return height; // Mengembalikan tinggi
    }

    public void setHeight(double height) { // Metode untuk mengatur tinggi
        this.height = height; // Mengatur tinggi
    }
}