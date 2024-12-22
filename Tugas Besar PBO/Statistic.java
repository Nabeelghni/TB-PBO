import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap; // Mengimpor kelas HashMap dari paket java.util
import java.util.Map; // Mengimpor kelas Map dari paket java.util

public class Statistic { // Mendefinisikan kelas Statistic
    private Map<Player, Integer> goalsScored; // Menyimpan jumlah gol yang dicetak oleh pemain
    private Databasehelper dbHelper; // Tambahkan field untuk Databasehelper

    public Statistic() { // Constructor untuk menginisialisasi objek Statistic
        goalsScored = new HashMap<>(); // Menginisialisasi HashMap untuk menyimpan statistik gol
            dbHelper = Databasehelper.getInstance(); // Inisialisasi dbHelper

    }

    // Metode untuk mencatat gol yang dicetak oleh pemain
    public void recordGoal(Player player, int points) {
    // Gunakan query yang sesuai dengan struktur tabel statistic
    String sql = "INSERT INTO statistic (player_id, points, bonus) VALUES (?, ?, ?)";
    try (Connection conn = dbHelper.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, player.getId());
        stmt.setInt(2, points);
        stmt.setDouble(3, points * 100_000);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public int getGoals(Player player) {
    String sql = "SELECT COALESCE(SUM(points), 0) as total_goals FROM statistic WHERE player_id = ?";
    try (Connection conn = dbHelper.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, player.getId());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("total_goals");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
    // Metode untuk menampilkan semua statistik pemain
    public void displayAllStatistics() {
        System.out.println("Statistik Pemain:"); // Menampilkan judul statistik
        for (Map.Entry<Player, Integer> entry : goalsScored.entrySet()) { // Iterasi melalui setiap entri dalam statistik
            Player player = entry.getKey(); // Mendapatkan pemain dari entri
            int goals = entry.getValue(); // Mendapatkan jumlah gol dari entri
            double bonus = calculateBonus(goals); // Menghitung bonus berdasarkan jumlah gol
            System.out.println("Nama: " + player.getName() + ", Point: " + goals + ", Bonus: " + bonus); // Menampilkan nama, gol, dan bonus pemain
        }
    }

    // Metode untuk menghitung bonus berdasarkan jumlah gol
    private double calculateBonus(int goals) {
        return goals * 1000_000; // Misalnya, bonus 100.000 per gol
    }
}