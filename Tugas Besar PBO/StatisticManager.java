import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date; 
import java.util.List; 

public class StatisticManager {
    private Databasehelper dbHelper;

    public StatisticManager() {
        dbHelper = Databasehelper.getInstance();
    }

public void saveMatchResult(String teamA, String teamB, int scoreA, int scoreB, 
                             Date matchDate, List<Player> players, List<Integer> playerPoints) {
    String matchSql = "INSERT INTO matches (team_a, team_b, score_a, score_b, match_date, winner) VALUES (?, ?, ?, ?, ?, ?)";
    String statSql = "INSERT INTO statistic (match_id, player_id, points, bonus, match_date) VALUES (?, ?, ?, ?, ?)";
    
    Connection conn = null;
    try {
        conn = dbHelper.connect();
        conn.setAutoCommit(false); // Mulai transaksi

        int matchId = -1;
        // Simpan hasil pertandingan
        try (PreparedStatement matchStmt = conn.prepareStatement(matchSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            matchStmt.setString(1, teamA);
            matchStmt.setString(2, teamB);
            matchStmt.setInt(3, scoreA);
            matchStmt.setInt(4, scoreB);
            matchStmt.setDate(5, new java.sql.Date(matchDate.getTime()));
            
            // Tentukan pemenang
            String winner = scoreA > scoreB ? teamA : teamB;
            matchStmt.setString(6, winner);
            
            matchStmt.executeUpdate();
            
            // Ambil ID pertandingan yang baru saja dibuat
            try (ResultSet generatedKeys = matchStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    matchId = generatedKeys.getInt(1);
                }
            }
        }

        // Simpan statistik pemain
        if (matchId != -1) {
            try (PreparedStatement statStmt = conn.prepareStatement(statSql)) {
                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    int points = playerPoints.get(i);
                    double bonus = points * 100_000;
                    
                    statStmt.setInt(1, matchId);  // Tambahkan match_id
                    statStmt.setInt(2, player.getId());
                    statStmt.setInt(3, points);
                    statStmt.setDouble(4, bonus);
                    statStmt.setDate(5, new java.sql.Date(matchDate.getTime())); // Tambahkan match_date
                    statStmt.addBatch();
                }
                
                statStmt.executeBatch();
            }
        }

        conn.commit(); // Commit transaksi
    } catch (SQLException e) {
        // Rollback jika terjadi error
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
    } finally {
        // Pastikan koneksi ditutup
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


public List<PlayerMatchStat> getPlayerMatchStats(int playerId) {
    List<PlayerMatchStat> stats = new ArrayList<>();
    String sql = "SELECT m.team_a, m.team_b, m.score_a, m.score_b, m.match_date, " +
                 "s.points, s.bonus " +
                 "FROM matches m " +
                 "JOIN statistic s ON m.id = s.match_id " +
                 "WHERE s.player_id = ?";
    
    try (Connection conn = dbHelper.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, playerId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            PlayerMatchStat stat = new PlayerMatchStat(
                rs.getString("team_a"),
                rs.getString("team_b"),
                rs.getInt("score_a"),
                rs.getInt("score_b"),
                rs.getDate("match_date"),
                rs.getInt("points"),
                rs.getDouble("bonus")
            );
            stats.add(stat);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return stats;
}


public static class PlayerMatchStat {
    private String teamA;
    private String teamB;
    private int scoreA;
    private int scoreB;
    private Date matchDate;
    private int points;
    private double bonus;

    public PlayerMatchStat(String teamA, String teamB, int scoreA, int scoreB, 
                            Date matchDate, int points, double bonus) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.matchDate = matchDate;
        this.points = points;
        this.bonus = bonus;
    }

    // Getter methods
    public String getTeamA() { return teamA; }
    public String getTeamB() { return teamB; }
    public int getScoreA() { return scoreA; }
    public int getScoreB() { return scoreB; }
    public Date getMatchDate() { return matchDate; }
    public int getPoints() { return points; }
    public double getBonus() { return bonus; }
}

public void recordGoal(Player player, int points) {
    String sql = "INSERT INTO statistic (player_id, points, bonus) VALUES (?, ?, ?)";
    try (Connection conn = dbHelper.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, player.getId());
        stmt.setInt(2, points);
        stmt.setDouble(3, points * 100_000);
        
        stmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Error di recordGoal: " + e.getMessage());
        e.printStackTrace();
    }
}

    public int getGoals(Player player) {
        // Gunakan kolom points sebagai pengganti goals_scored
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

    public void displayStatisticsByMatch(int matchNumber) {
        // Implementasi untuk menampilkan statistik dari database berdasarkan matchNumber
        // Misalnya, ambil data dari database dan tampilkan ID pemain, nama, dan skor
        String sql = "SELECT player_id, goals_scored FROM statistic WHERE match_number = ?";
        try (Connection conn = dbHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matchNumber);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Statistik untuk Match ke-" + matchNumber + ":");
            while (rs.next()) {
                int playerId = rs.getInt("player_id");
                int goals = rs.getInt("goals_scored");
                System.out.println("ID Pemain: " + playerId + ", Gol: " + goals);
            }
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