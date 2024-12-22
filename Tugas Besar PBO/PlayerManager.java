import java.sql.Connection; // Mengimpor kelas Connection dari paket java.sql
import java.sql.PreparedStatement; // Mengimpor kelas PreparedStatement dari paket java.sql
import java.sql.ResultSet; // Mengimpor kelas ResultSet dari paket java.sql
import java.sql.SQLException; // Mengimpor kelas SQLException dari paket java.sql
import java.util.ArrayList; // Mengimpor kelas ArrayList dari paket java.util
import java.util.List; // Mengimpor kelas List dari paket java.util

public class PlayerManager implements CRUDOperations<Player> { 
    private Databasehelper dbHelper; // Menyimpan instance dari Databasehelper
    private List<Player> players; // Menyimpan daftar pemain

    public PlayerManager() { // Constructor untuk menginisialisasi objek PlayerManager
        dbHelper = Databasehelper.getInstance(); // Menggunakan instance dari Databasehelper
        players = new ArrayList<>(); // Menginisialisasi daftar pemain
    }
    

public List<Player> getAllPlayers() { // Metode untuk mendapatkan semua pemain
        List<Player> players = new ArrayList<>(); // Membuat daftar baru untuk menyimpan pemain
        String sql = "SELECT * FROM players"; // Query SQL untuk mengambil semua data pemain
        try (Connection conn = dbHelper.connect(); // Membuat koneksi ke database
             PreparedStatement stmt = conn.prepareStatement(sql); // Menyiapkan statement SQL
             ResultSet rs = stmt.executeQuery()) { // Menjalankan query dan mendapatkan hasil
            while (rs.next()) { // Iterasi melalui hasil query
                Player player = new Player( // Membuat objek Player dari hasil query
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("height"),
                    rs.getString("position"),
                    rs.getInt("skill_level"),
                    rs.getDate("join_date")
                );
                players.add(player); // Menambahkan pemain ke daftar
            }
        } catch (SQLException e) { // Menangani exception jika terjadi kesalahan SQL
            e.printStackTrace(); // Mencetak stack trace untuk debugging
        }
        return players; // Mengembalikan daftar pemain
    }

    public int getPlayerCount() { // Metode untuk mendapatkan jumlah pemain
        return getAllPlayers().size(); // Mengembalikan jumlah pemain dari daftar
    }

    @Override
    public void create(Player player) { // Metode untuk menambahkan pemain baru
        players.add(player); // Menambahkan pemain ke daftar
        String sql = "INSERT INTO players (name, height, position, skill_level, salary, join_date) VALUES (?, ?, ?, ?, ?, ?)"; // Query SQL untuk menyimpan pemain
        try (Connection conn = dbHelper.connect(); // Membuat koneksi ke database
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Menyiapkan statement SQL
            stmt.setString(1, player.getName()); // Mengatur parameter untuk nama pemain
            stmt.setDouble(2, player.getHeight()); // Mengatur parameter untuk tinggi pemain
            stmt.setString(3, player.getPosition()); // Mengatur parameter untuk posisi pemain
            stmt.setInt(4, player.getSkillLevel()); // Mengatur parameter untuk level keterampilan pemain
            stmt.setDouble(5, player.calculateSalary()); // Mengatur parameter untuk gaji pemain
            stmt.setDate(6, new java.sql.Date(player.getJoinDate().getTime())); // Mengatur parameter untuk tanggal bergabung
            stmt.executeUpdate(); // Menjalankan perintah update untuk menyimpan data
        } catch (SQLException e) { // Menangani exception jika terjadi kesalahan SQL
            e.printStackTrace(); // Mencetak stack trace untuk debugging
        }
    }

    @Override
    public Player read(int id) {
        String sql = "SELECT * FROM players WHERE id = ?";
        try (Connection conn = dbHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Player(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("height"),
                    rs.getString("position"),
                    rs.getInt("skill_level"),
                    rs.getDate("join_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Mengembalikan null jika pemain tidak ditemukan
    }



       // Implementasi metode update untuk single Player
       @Override
       public void update(Player player) {
           String sql = "UPDATE players SET " +
                        "name = ?, " +
                        "height = ?, " +
                        "position = ?, " +
                        "skill_level = ? " +
                        "WHERE id = ?";
           
           try (Connection conn = dbHelper.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
               
               stmt.setString(1, player.getName());
               stmt.setDouble(2, player.getHeight());
               stmt.setString(3, player.getPosition());
               stmt.setInt(4, player.getSkillLevel());
               stmt.setInt(5, player.getId());
               
               int rowsAffected = stmt.executeUpdate();
               
               if (rowsAffected > 0) {
                // Ambil data terbaru dari database
                Player updatedPlayer = read(player.getId());

                  if (players != null) {
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getId() == player.getId()) {
                        players.set(i, updatedPlayer);
                        break;
                       }
                   }
                   System.out.println("Pemain berhasil diupdate: " + player.getName());
               } else {
                   System.out.println("Tidak ada pemain yang diupdate dengan ID: " + player.getId());
               }
            }
            
           } catch (SQLException e) {
               System.err.println("Error saat mengupdate pemain: " + e.getMessage());
               e.printStackTrace();
           }
           
       }
   
       // Implementasi metode update dengan ID dan Player
       @Override
       public void update(int id, Player player) {
          
           player.setId(id);  
           update(player);
       }
       
    @Override
    public void delete(int id) { // Metode untuk menghapus pemain berdasarkan ID
        String sql = "DELETE FROM players WHERE id = ?"; // Query SQL untuk menghapus pemain
        try (Connection conn = dbHelper.connect(); // Membuat koneksi ke database
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Menyiapkan statement SQL
            stmt.setInt(1, id); // Mengatur parameter untuk ID pemain
            stmt.executeUpdate(); // Menjalankan perintah update untuk menghapus data
        } catch (SQLException e) { // Menangani exception jika terjadi kesalahan SQL
            e.printStackTrace(); // Mencetak stack trace untuk debugging
        }
        
    }

    public void loadPlayers() {
        players = getAllPlayers(); // Memuat semua pemain dari database
    }

    public double loadInitialCapital() {
        String sql = "SELECT amount FROM capital WHERE id = 1"; // Asumsi hanya ada satu record modal
        try (Connection conn = dbHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return initializeCapital(); // Jika tidak ditemukan, inisialisasi modal
    }

    private double initializeCapital() {
        double initialAmount = 100000000; // Modal awal
        String sql = "INSERT INTO capital (amount) VALUES (?)";
        try (Connection conn = dbHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, initialAmount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return initialAmount; // Kembalikan modal awal
    }

    public void updateCapital(double newCapital) {
        String sql = "UPDATE capital SET amount = ? WHERE id = 1"; // Asumsi hanya ada satu record modal
        try (Connection conn = dbHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newCapital);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }
    public void resetPlayers() {
        String sql = "DELETE FROM players";
        try (Connection conn = dbHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void resetStatistics() {
        String sql = "DELETE FROM statistic";
        try (Connection conn = dbHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}