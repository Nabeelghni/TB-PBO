import java.sql.Connection; // Mengimpor kelas Connection dari paket java.sql
import java.sql.PreparedStatement; // Mengimpor kelas PreparedStatement dari paket java.sql
import java.sql.ResultSet; // Mengimpor kelas ResultSet dari paket java.sql
import java.sql.SQLException; // Mengimpor kelas SQLException dari paket java.sql
import java.util.ArrayList; // Mengimpor kelas ArrayList dari paket java.util
import java.util.Date; // Mengimpor kelas Date dari paket java.util
import java.util.List; // Mengimpor kelas List dari paket java.util

public class MatchManager { // Mendefinisikan kelas MatchManager
    private Databasehelper dbHelper; // Menyimpan instance dari Databasehelper untuk koneksi database
    private List<Match> matches; // Menyimpan daftar pertandingan yang telah dijadwalkan
    private int matchCount; // Menyimpan jumlah pertandingan yang telah dijadwalkan

    public MatchManager() { // Constructor untuk menginisialisasi objek MatchManager
        dbHelper = Databasehelper.getInstance(); // Inisialisasi dbHelper
        matches = new ArrayList<>(); // Menginisialisasi daftar pertandingan
        matchCount = 0; // Inisialisasi matchCount
    }

    public void loadMatches() { // Metode untuk memuat semua pertandingan dari database
        String sql = "SELECT * FROM matches"; // Query SQL untuk mengambil semua data pertandingan
        try (Connection conn = dbHelper.connect(); // Membuat koneksi ke database
             PreparedStatement stmt = conn.prepareStatement(sql); // Menyiapkan statement SQL
             ResultSet rs = stmt.executeQuery()) { // Menjalankan query dan mendapatkan hasil
            while (rs.next()) { // Iterasi melalui hasil query
                String teamA = rs.getString("team_a"); // Mengambil nama tim A dari hasil
                String teamB = rs.getString("team_b"); // Mengambil nama tim B dari hasil
                Date matchDate = rs.getDate("match_date"); // Mengambil tanggal pertandingan dari hasil
                Team team1 = new Team(teamA); // Membuat objek Team untuk tim A
                Team team2 = new Team(teamB); // Membuat objek Team untuk tim B
                Match match = new Match(team1, team2, matchDate); // Membuat objek Match
                matches.add(match); // Menambahkan pertandingan ke daftar
            }
        } catch (SQLException e) { // Menangani exception jika terjadi kesalahan SQL
            e.printStackTrace(); // Mencetak stack trace untuk debugging
        }
    }

    public void scheduleMatch(Team teamA, Team teamB, Date matchDate) { // Metode untuk menjadwalkan pertandingan
        Match match = new Match(teamA, teamB, matchDate); // Membuat objek Match baru
        matches.add(match); // Menambahkan pertandingan ke daftar
        matchCount++; // Increment match count
        match.startMatch(); // Memulai pertandingan
        saveMatch(teamA.getName(), teamB.getName(), matchDate); // Menyimpan pertandingan ke database
    }

    public int getMatchCount() { // Metode untuk mendapatkan jumlah pertandingan
        return matchCount; // Mengembalikan jumlah pertandingan
    }

    private void saveMatch(String teamA, String teamB, Date matchDate) { // Metode untuk menyimpan pertandingan ke database
        String sql = "INSERT INTO matches (team_a, team_b, match_date) VALUES (?, ?, ?)"; // Query SQL untuk menyimpan pertandingan
        try (Connection conn = dbHelper.connect(); // Membuat koneksi ke database
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Menyiapkan statement SQL
            stmt.setString(1, teamA); // Mengatur parameter untuk tim A
            stmt.setString(2, teamB); // Mengatur parameter untuk tim B
            stmt.setDate(3, new java.sql.Date(matchDate.getTime())); // Mengatur parameter untuk tanggal pertandingan
            stmt.executeUpdate(); // Menjalankan perintah update untuk menyimpan data
        } catch (SQLException e) { // Menangani exception jika terjadi kesalahan SQL
            e.printStackTrace(); // Mencetak stack trace untuk debugging
        }
    }
    public void resetMatches() {
        String sql = "DELETE FROM matches CASCADE";
        try (Connection conn = dbHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}