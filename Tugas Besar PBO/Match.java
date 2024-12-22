import java.util.ArrayList; // Mengimpor kelas ArrayList dari paket java.util
import java.util.List; // Mengimpor kelas List dari paket java.util
import java.util.Date; // Mengimpor kelas Date dari paket java.util

public class Match { // Mendefinisikan kelas Match
    private Team teamA; // Menyimpan tim A yang berpartisipasi dalam pertandingan
    private Team teamB; // Menyimpan tim B yang berpartisipasi dalam pertandingan
    private Date matchDate; // Menyimpan tanggal pertandingan
    private int scoreA; // Skor untuk tim A
    private int scoreB; // Skor untuk tim B
    private List<Player> players = new ArrayList<>(); // Daftar pemain yang terlibat dalam pertandingan

    public Match(Team teamA, Team teamB, Date matchDate) { // Constructor untuk menginisialisasi objek Match
        this.teamA = teamA; // Mengatur tim A
        this.teamB = teamB; // Mengatur tim B
        this.matchDate = matchDate; // Mengatur tanggal pertandingan
    }

    public void startMatch() { // Metode untuk memulai pertandingan
        // Logika untuk memulai pertandingan
        System.out.println("Pertandingan antara " + teamA.getName() + " dan " + teamB.getName() + " dimulai pada " + matchDate); // Menampilkan informasi pertandingan
    }

    public void setScore(int scoreA, int scoreB) { // Metode untuk mengatur skor pertandingan
        this.scoreA = scoreA; // Mengatur skor tim A
        this.scoreB = scoreB; // Mengatur skor tim B
    }

    public int getScoreA() { // Metode untuk mendapatkan skor tim A
        return scoreA; // Mengembalikan skor tim A
    }

    public int getScoreB() { // Metode untuk mendapatkan skor tim B
        return scoreB; // Mengembalikan skor tim B
    }

    public void addPlayer(Player player) { // Metode untuk menambahkan pemain ke dalam daftar pemain
        players.add(player); // Menambahkan pemain ke dalam daftar
    }

    public List<Player> getPlayers() { // Metode untuk mendapatkan daftar pemain
        return players; // Mengembalikan daftar pemain
    }

    public void calculateBonus(StatisticManager statisticManager, double[] modal) { // Metode untuk menghitung bonus untuk pemain yang mencetak poin
        if (scoreA > scoreB) { // Jika tim A menang
            // Tim A menang
            if (teamA.getName().equalsIgnoreCase("GoldenChief")) { // Memeriksa apakah tim A adalah GoldenChief
                // Hanya jika Tim A adalah GoldenChief
                for (Player player : teamA.getPlayers()) { // Iterasi melalui pemain di tim A
                    int goals = statisticManager.getGoals(player); // Mendapatkan jumlah gol yang dicetak oleh pemain
                    double bonus = goals * 100000; // Menghitung bonus berdasarkan jumlah gol
                    player.addPoints((int)bonus); // Menambahkan bonus ke pemain
                    modal[0] -= bonus; // Mengurangi modal tim dengan bonus yang diberikan
                }
            }
        } else if (scoreB > scoreA) { // Jika tim B menang
            // Tim B menang
            if (teamB.getName().equalsIgnoreCase("GoldenChief")) { // Memeriksa apakah tim B adalah GoldenChief
                // Hanya jika Tim B adalah GoldenChief
                for (Player player : teamB.getPlayers()) { // Iterasi melalui pemain di tim B
                    int goals = statisticManager.getGoals(player); // Mendapatkan jumlah gol yang dicetak oleh pemain
                    double bonus = goals * 100000; // Menghitung bonus berdasarkan jumlah gol
                    player.addPoints((int)bonus); // Menambahkan bonus ke pemain
                    modal[0] -= bonus; // Mengurangi modal tim dengan bonus yang diberikan
                }
            }
        } else {    // Jika hasilnya seri
                    // Jika hasilnya seri, tidak ada bonus yang diberikan
        }
    }
}