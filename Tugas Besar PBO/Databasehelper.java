import java.sql.Connection; // Mengimpor kelas Connection dari paket java.sql
import java.sql.DriverManager; // Mengimpor kelas DriverManager dari paket java.sql
import java.sql.SQLException; // Mengimpor kelas SQLException dari paket java.sql

public class Databasehelper { // Mendefinisikan kelas Databasehelper
    private static Databasehelper instance; // Menyimpan instance tunggal dari Databasehelper
    private String url = "jdbc:postgresql://localhost:5432/GoldenChief"; // URL untuk koneksi ke database PostgreSQL
    private String user = "postgres"; // Nama pengguna untuk koneksi database
    private String password = "Nabeel1818"; // Kata sandi untuk koneksi database

    private Databasehelper() { // Constructor privat untuk mencegah instansiasi langsung
        // Constructor privat
    }

    public static Databasehelper getInstance() { // Metode statis untuk mendapatkan instance tunggal dari Databasehelper
        if (instance == null) { // Memeriksa apakah instance belum ada
            instance = new Databasehelper(); // Membuat instance baru jika belum ada
        }
        return instance; // Mengembalikan instance yang ada
    }

    public Connection connect() throws SQLException { // Metode untuk membuat koneksi ke database
        return DriverManager.getConnection(url, user, password); // Mengembalikan koneksi ke database menggunakan DriverManager
    }
}
