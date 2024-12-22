import java.util.List; // Mengimpor kelas List dari paket java.util

public class ReportGenerator { // Mendefinisikan kelas ReportGenerator
    public static void generatePlayerReport(List<Player> players) { // Metode statis untuk menghasilkan laporan pemain
        System.out.println("Laporan Pemain:"); // Menampilkan judul laporan
        for (Player player : players) { // Iterasi melalui setiap pemain dalam daftar
            System.out.println("Nama: " + player.getName() + ", Posisi: " + player.getPosition() + ", Gaji: " + player.calculateSalary()); // Menampilkan nama, posisi, dan gaji pemain
        }
    }
}