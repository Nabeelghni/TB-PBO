import java.util.ArrayList; // Untuk ArrayList
import java.util.List; // Untuk List
import java.util.Scanner; // Untuk Scanner
import java.util.Random; // Untuk Random
import java.util.Date; // Untuk Date
import java.text.SimpleDateFormat; // Untuk SimpleDateFormat
import java.text.ParseException; // Untuk ParseException

public class Main {
        private static List<Player> players;
        private static PlayerManager playerManager;
        private static MatchManager matchManager;
        private static StatisticManager statisticManager;
        private static Scanner scanner;
        private static Random random;
        private static double initialCapital;
        private static double totalExpenditure;
    
        public static void main(String[] args) {
            // Inisialisasi
            initializeApplication();
    
            // Loop utama aplikasi
            while (true) {
                System.out.println("------------------------------------------------------------------");
                System.out.println("                         Golden Chief Team                        ");
                System.out.println("------------------------------------------------------------------");
                System.out.println("Menu Utama:");
                System.out.println("1. Tambah Pemain");
                System.out.println("2. Lihat Pemain");
                System.out.println("3. Update Pemain");
                System.out.println("4. Hapus Pemain");
                System.out.println("5. Pertandingan");
                System.out.println("6. Lihat Statistik Pemain");
                System.out.println("7. Keluar");
                System.out.println("8. ResetData");
                System.out.print("Pilih opsi: ");
            
                int choice;
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer
                } catch (Exception e) {
                    System.out.println("Input tidak valid. Silakan coba lagi.");
                    scanner.nextLine(); // Clear the invalid input
                    continue; // Kembali ke awal loop
                }
        
            switch (choice) {
                case 1:
                String backToMenu1;
                    // Menambahkan pemain
                    while (true) {
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("                         Golden Chief Team                        ");
                        System.out.println("------------------------------------------------------------------");
                        System.out.print("Masukkan nama pemain: ");
                        String name = scanner.nextLine(); // Menggunakan nextLine untuk nama pemain
                        System.out.print("Masukkan tinggi pemain: ");
                        double height = scanner.nextDouble();
                        scanner.nextLine(); // Membersihkan buffer setelah nextDouble
                        System.out.print("Masukkan posisi pemain: ");
                        String position = scanner.nextLine(); // Menggunakan nextLine untuk posisi pemain
                        System.out.print("Masukkan level keterampilan pemain (1-10): ");
                        int skillLevel = scanner.nextInt();
                        
                        // Validasi skill level
                        if (skillLevel < 1 || skillLevel > 10) {
                            System.out.println("Keterampilan harus antara 1 dan 10.");
                            continue; // Kembali ke awal loop
                        }
                    
                        // Gaji berdasarkan keterampilan
                        double salaryPerMatch;
                        if (skillLevel <= 7) {
                            salaryPerMatch = 2_000_000; // Rookie
                        } else {
                            salaryPerMatch = 6_000_000; // Veteran
                        }
                    
                        // Menghitung total gaji
                        double annualSalary = SalaryCalculator.calculateAnnualSalary(salaryPerMatch); // Menghitung gaji tahunan
                    
                        // Memeriksa apakah modal mencukupi
                        if (initialCapital < salaryPerMatch) {
                            System.out.println("Modal tidak mencukupi untuk menambahkan pemain.");
                            break; // Keluar dari loop jika modal tidak mencukupi
                        }
                    
                        // Menghitung total pengeluaran
                        totalExpenditure += salaryPerMatch; // Gaji tanpa bonus
                        initialCapital -= salaryPerMatch; // Mengurangi modal awal
                    
                        int randomId = 1000 + random.nextInt(1999); 
                        Player newPlayer = new Player(randomId, name, height, position, skillLevel, new Date());
                        playerManager.create(newPlayer);
                        players.add(newPlayer); // Menambahkan pemain ke daftar
                        System.out.println("Pemain ditambahkan: " + newPlayer.getName() + " dengan ID: " + randomId);
                        
                        // Menampilkan sisa modal dan total pengeluaran
                        System.out.printf("Total pengeluaran saat ini: %.2f%n", totalExpenditure);
                        System.out.printf("Sisa modal setelah menambahkan pemain: %.2f%n", initialCapital);
                        System.out.printf("Gaji tahunan pemain: %.2f%n", annualSalary);

                        // Opsi untuk kembali ke menu utama
                        System.out.print("Kembali ke menu utama? (y/n): ");
                        backToMenu1 = scanner.next();
                        scanner.nextLine(); // Membersihkan buffer setelah next
                        if (backToMenu1.equalsIgnoreCase("y")) {
                            break; // Kembali ke menu utama
                      } else if (backToMenu1.equalsIgnoreCase("n")) {
                            continue; // Kembali ke awal loop

                       } else {
                         System.out.println("Input tidak valid. Silakan coba lagi.");
                       }
                    }
                    break;    
                    

                case 2:
                while (true) {
                    String backToMenu2;
                    // Menampilkan daftar pemain
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("                          Daftar Pemain                           ");
                    System.out.println("------------------------------------------------------------------");
                    List<Player> allPlayers = playerManager.getAllPlayers(); // Pastikan ada metode ini di PlayerManager
                    for (Player player : allPlayers) {
                        System.out.printf("Nomor: %d, Nama: %s, Posisi: %s%n", player.getId(), player.getName(), player.getPosition());
                    }
                    System.out.println("-----------------------------------------------------------------------");
                    
                    // Opsi untuk kembali ke menu utama
                        System.out.print("Kembali ke menu utama? (y/n): ");
                        backToMenu2 = scanner.next();
                        scanner.nextLine(); // Membersihkan buffer setelah next
                        if (backToMenu2.equalsIgnoreCase("y")) {
                            break; // Kembali ke menu utama
                      } else if (backToMenu2.equalsIgnoreCase("n")) {
                            continue; // Kembali ke awal loop

                       } else {
                         System.out.println("Input tidak valid. Silakan coba lagi.");
                       }
                    }
                    break;

            case 3:
            //update pemain
                while (true) {
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("                           Update Pemain                          ");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("Daftar Pemain:");
                    List<Player> allPlayers = playerManager.getAllPlayers(); // Ambil semua pemain
                    for (Player player : allPlayers) {
                        System.out.printf("Nomor: %d, Nama: %s, Posisi: %s%n", player.getId(), player.getName(), player.getPosition());
                    }
                    System.out.println("-----------------------------------------------------------------------");
                    
                    System.out.print("Masukkan Nomor pemain yang ingin diupdate: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer

                    Player playerToUpdate = playerManager.read(updateId);

                    if (playerToUpdate != null) {
                        System.out.print("Masukkan nama baru (kosongkan jika tidak ingin mengubah): ");
                        String newName = scanner.nextLine();
                        if (!newName.isEmpty()) {
                            playerToUpdate.setName(newName);
                        }
                        System.out.print("Masukkan tinggi baru (kosongkan jika tidak ingin mengubah): ");
                        String newHeight = scanner.nextLine();
                        if (!newHeight.isEmpty()) {
                            playerToUpdate.setHeight(Double.parseDouble(newHeight));
                        }
                        System.out.print("Masukkan posisi baru (kosongkan jika tidak ingin mengubah): ");
                        String newPosition = scanner.nextLine();
                        if (!newPosition.isEmpty()) {
                            playerToUpdate.setPosition(newPosition);
                        }
                        System.out.print("Masukkan level keterampilan baru (kosongkan jika tidak ingin mengubah): ");
                        String newSkillLevel = scanner.nextLine();
                        if (!newSkillLevel.isEmpty()) {
                            playerToUpdate.setSkillLevel(Integer.parseInt(newSkillLevel));
                        }

                        // Update di database
                        playerManager.update(playerToUpdate);

                        // Refresh daftar pemain
                        updatePlayerList();

                        refreshPlayersList(); // Refresh daftar pemain setelah update
                        // Perbarui di list lokal
                        for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getId() == updateId) {
                        players.set(i, playerToUpdate);
                        break;
                        }    
                    }

                        System.out.println("Pemain berhasil diupdate.");
                    } else {
                        System.out.println("Pemain tidak ditemukan.");
                    }
                    
                    // Opsi untuk kembali ke menu utama
                    String backToMenu3;
                    System.out.print("Kembali ke menu utama? (y/n): ");
                    backToMenu3 = scanner.next();
                    scanner.nextLine(); // Membersihkan buffer setelah next
                    if (backToMenu3.equalsIgnoreCase("y")) {
                        break; // Kembali ke menu utama
                  } else if (backToMenu3.equalsIgnoreCase("n")) {
                        continue; // Kembali ke awal loop

                   } else {
                     System.out.println("Input tidak valid. Silakan coba lagi.");
                   }
                }
                break;

                case 4:
                while (true) {
                // Hapus Pemain
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("                           Delete Player                          ");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("Daftar Pemain:");
                    List<Player> allPlayers = playerManager.getAllPlayers(); // Pastikan ada metode ini di PlayerManager
                    for (Player player : allPlayers) {
                        System.out.printf("Nomor: %d, Nama: %s, Posisi: %s%n", player.getId(), player.getName(), player.getPosition());
                    }
                    System.out.println("-----------------------------------------------------------------------");

                    System.out.print("Masukkan Nomor pemain yang ingin dihapus: ");
                    int deleteId = scanner.nextInt();

                     // Cari pemain yang akan dihapus
                     Player playerToDelete = playerManager.read(deleteId);
        
                     if (playerToDelete != null) {
                     // Konfirmasi penghapusan
                     System.out.println("Anda akan menghapus pemain:");
                     System.out.printf("Nama: %s, Posisi: %s%n", 
                     
                     playerToDelete.getName(), playerToDelete.getPosition());
            
            // Variabel untuk menyimpan konfirmasi
            String confirm = "";
            
            // Loop untuk memastikan input valid
            System.out.print("Apakah Anda yakin? (y/n): ");
            while (true) {
                confirm = scanner.nextLine().trim().toLowerCase();
                
                if (confirm.equals("y") || confirm.equals("n")) {
                    break; // Keluar dari loop jika input valid
                
                }
            }
            
            if (confirm.equals("y")) {
                // Hapus pemain dari database
                playerManager.delete(deleteId);
                
                // Hapus dari list lokal
                players.remove(playerToDelete);
 
                        System.out.println("Pemain berhasil dihapus.");
                    } else {
                        System.out.println("Penghapusan dibatalkan.");
                    }
                    
                    // Opsi untuk kembali ke menu utama
                    System.out.print("Kembali ke menu utama? (y/n): ");
                    String backToMenu4 = scanner.nextLine();
                    
                    if (backToMenu4.equalsIgnoreCase("y")) {
                        break; // Kembali ke menu utama
                    } else if (backToMenu4.equalsIgnoreCase("n")) {
                        continue; // Kembali ke awal loop
                    } else {
                        System.out.println("Input tidak valid. Silakan coba lagi.");
                    }
                }
            }
            break;
           
        case 5:
        refreshPlayersList();
        while (true) {
            if (players.size() < 6) {
                System.out.println("Jumlah pemain tidak mencukupi untuk menjadwalkan pertandingan.");
                break;
            }
            
            System.out.println("------------------------------------------------------------------");
            System.out.println("                              Match                               ");
            System.out.println("------------------------------------------------------------------");
            
            // Input tanggal pertandingan
            Date matchDate = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
            while (matchDate == null) {
            try {
            System.out.print("Masukkan tanggal pertandingan (dd/MM/yyyy): ");
            String matchDateString = scanner.nextLine();
            matchDate = dateFormat.parse(matchDateString);
            } catch (ParseException e) {
            System.out.println("Format tanggal salah. Gunakan format dd/MM/yyyy");
        }
    }
            System.out.print("Masukkan nama tim A: ");
            String teamAName = scanner.nextLine();
            System.out.print("Masukkan nama tim B: ");
            String teamBName = scanner.nextLine();

            // Tampilkan daftar pemain dengan detail
            System.out.println("Daftar Pemain:");
            for (Player player : players) {
                System.out.printf("ID: %d, Nama: %s, Posisi: %s, Level: %d%n", 
                    player.getId(), player.getName(), player.getPosition(), player.getSkillLevel());
            }
            
            System.out.print("Masukkan ID pemain yang akan dimainkan (pisahkan dengan koma): ");
            String[] selectedPlayerIds = scanner.nextLine().split(",");
            List<Player> selectedPlayers = new ArrayList<>();
            
            // Validasi dan pemilihan pemain
            for (String idStr : selectedPlayerIds) {
                try {
                    int playerId = Integer.parseInt(idStr.trim());
                    Player player = players.stream()
                        .filter(p -> p.getId() == playerId)
                        .findFirst()
                        .orElse(null);
                    
                    if (player != null) {
                        selectedPlayers.add(player);
                    } else {
                        System.out.println("Pemain dengan ID " + playerId + " tidak ditemukan.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID pemain tidak valid: " + idStr);
                }
            }
            
            // Hitung point pemain
            List<Integer> playerPoints = new ArrayList<>();
            int totalPointA = 0;
            System.out.println("Rincian Point Pemain:");
            for (Player player : selectedPlayers) {
                // Point acak antara 1-10
                int points = random.nextInt(10) + 1;
                playerPoints.add(points);
                totalPointA += points;
                double bonus = points * 100_000;
                System.out.printf("%s = %d, bonus: %.2f%n", 
                    player.getName(), points, bonus);
            }
            
            // Skor tim B dibuat berbeda
            int totalPointB = 0;
            while (totalPointB == totalPointA || totalPointB == 0) {
                totalPointB = random.nextInt(50) + 1;
            }
            
           // Tentukan pemenang
        boolean isTeamAWin = totalPointA > totalPointB;
        
        System.out.printf("Skor akhir: %s %d - %d %s%n", 
            teamAName, totalPointA, totalPointB, teamBName);
        
        // Hitung total gaji
        double totalSalary = selectedPlayers.stream()
            .mapToDouble(Player::calculateSalary)
            .sum();
        
        // Hitung bonus dan modal tambahan
        double additionalCapital = 0;
        double totalBonus = 0;

        if (isTeamAWin && teamAName.equalsIgnoreCase("GoldenChief")) {
            // Tim GoldenChief menang di tim A
            additionalCapital = 50_000_000;
            for (int i = 0; i < selectedPlayers.size(); i++) {
                double bonus = playerPoints.get(i) * 100_000;
                totalBonus += bonus;
                statisticManager.recordGoal(selectedPlayers.get(i), playerPoints.get(i));
            }
            
            // Modal = Modal saat ini + 50 juta - (total gaji + total bonus)
            initialCapital += additionalCapital - totalSalary - totalBonus;
        } 
        else if (!isTeamAWin && teamBName.equalsIgnoreCase("GoldenChief")) {
            // Tim GoldenChief menang di tim B
            additionalCapital = 50_000_000;
            for (int i = 0; i < selectedPlayers.size(); i++) {
                double bonus = playerPoints.get(i) * 100_000;
                totalBonus += bonus;
                statisticManager.recordGoal(selectedPlayers.get(i), playerPoints.get(i));
            }
            
            // Modal = Modal saat ini + 50 juta - (total gaji + total bonus)
            initialCapital += additionalCapital - totalSalary - totalBonus;
        } 
        else {
            // Tim GoldenChief kalah
            additionalCapital = 20_000_000;
            
            // Modal = Modal saat ini + 20 juta - total gaji
            initialCapital += additionalCapital - totalSalary;
            
            System.out.println("Tim GoldenChief Kalah. Tidak ada bonus untuk pemain.");
            totalBonus = 0;
        }
        
        // Update modal di database
        playerManager.updateCapital(initialCapital);
        
        System.out.printf("Total gaji pemain yang dimainkan: %.2f%n", totalSalary);
        System.out.printf("Total bonus yang didapat: %.2f%n", totalBonus);
        System.out.printf("Modal setelah pertandingan: %.2f%n", initialCapital);

        statisticManager.saveMatchResult(
            teamAName, 
            teamBName, 
            totalPointA, 
            totalPointB, 
            matchDate, 
            selectedPlayers, 
            playerPoints
        );




            // Opsi untuk kembali ke menu utama
            String backToMenu5;
            System.out.print("Kembali ke menu utama? (y/n): ");
            backToMenu5 = scanner.next();
            scanner.nextLine(); // Membersihkan buffer setelah next
            if (backToMenu5.equalsIgnoreCase("y")) {
                break; // Kembali ke menu utama
          } else if (backToMenu5.equalsIgnoreCase("n")) {
                continue; // Kembali ke awal loop

           } else {
             System.out.println("Input tidak valid. Silakan coba lagi.");
           }
        }
        break;

         

        case 6:
    while (true) {
        System.out.println("------------------------------------------------------------------");
        System.out.println("                         Golden Chief Team                        ");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Daftar Pemain:");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.printf("%d. %s%n", i + 1, player.getName()); // Menampilkan nomor dan nama pemain
        }
    
        System.out.print("Pilih nomor pemain untuk melihat statistik: ");
        int playerNumber = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        if (playerNumber < 1 || playerNumber > players.size()) {
            System.out.println("Nomor pemain tidak valid.");
            continue;
        }

        Player selectedPlayer = players.get(playerNumber - 1); // Mendapatkan pemain berdasarkan nomor yang dipilih
        int selectedPlayerId = selectedPlayer.getId(); // Ambil ID pemain yang dipilih

        List<StatisticManager.PlayerMatchStat> playerStats = 
        statisticManager.getPlayerMatchStats(selectedPlayerId);

        if (playerStats.isEmpty()) {
            System.out.println("Tidak ada statistik pertandingan untuk pemain ini.");
        } else {
            System.out.println("Statistik Pertandingan:");
            for (StatisticManager.PlayerMatchStat stat : playerStats) {
                System.out.printf("Pertandingan: %s vs %s%n", stat.getTeamA(), stat.getTeamB());
                System.out.printf("Skor: %d - %d%n", stat.getScoreA(), stat.getScoreB());
                System.out.printf("Tanggal: %s%n", stat.getMatchDate());
                System.out.printf("Points: %d, Bonus: %.2f%n", stat.getPoints(), stat.getBonus());
                System.out.println("-------------------");
            }
        }
        
        int goals = statisticManager.getGoals(selectedPlayer); // Mengambil statistik gol dari StatisticManager
        System.out.printf("Statistik untuk Pemain: %s%n", selectedPlayer.getName());
        System.out.printf("Jumlah Point: %d%n", goals);
    
        // Opsi untuk kembali ke menu utama
        String backToMenu6;
        System.out.print("Kembali ke menu utama? (y/n): ");
        backToMenu6 = scanner.next();
        scanner.nextLine(); // Membersihkan buffer setelah next
        if (backToMenu6.equalsIgnoreCase("y")) {
            break; // Kembali ke menu utama
        } else if (backToMenu6.equalsIgnoreCase("n")) {
            continue; // Kembali ke awal loop
        } else {
            System.out.println("Input tidak valid. Silakan coba lagi.");
        }
    }
    break;

                case 7:
                    // Keluar dari program
                    System.out.println("Terima kasih telah menggunakan program ini.");
                    scanner.close();
                    System.exit(0);
                    break;

                case 8: // Reset Data
                    System.out.println("Anda yakin ingin mereset semua data? (y/n)");
                    String confirmReset = scanner.nextLine();
                    if (confirmReset.equalsIgnoreCase("y")) {
                        statisticManager.resetStatistics(); // Hapus statistik TERLEBIH DAHULU
                        matchManager.resetMatches(); // Tambahkan reset matches
                        playerManager.resetPlayers(); // Baru hapus pemain
                        playerManager.updateCapital(100_000_000); // Mengatur modal kembali ke 100.000.000
                        System.out.println("Data telah direset dan modal diatur kembali ke 100.000.000.");
                    } else {
                        System.out.println("Reset dibatalkan.");
                    }
                    break;


                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi.");
                    break;
            }
        }
    }
        // Method inisialisasi aplikasi
        private static void initializeApplication() {
            // Inisialisasi objek
            players = new ArrayList<>();
            playerManager = new PlayerManager();
            matchManager = new MatchManager();
            statisticManager = new StatisticManager();
            scanner = new Scanner(System.in);
            random = new Random();
    
            // Memuat data awal
            matchManager.loadMatches(); // Memuat semua pertandingan dari database
            refreshPlayersList(); // Muat daftar pemain
            playerManager.loadPlayers();
            
            // Memuat modal
            initialCapital = playerManager.loadInitialCapital();
            
            // Inisialisasi total pengeluaran
            totalExpenditure = 0;
        }
    
        // Method untuk me-refresh daftar pemain
        private static void refreshPlayersList() {
            players.clear(); // Kosongkan list saat ini
            players.addAll(playerManager.getAllPlayers()); // Muat ulang dari database
        }
    
        // Method untuk update daftar pemain (opsional, bisa digunakan di beberapa case)
        private static void updatePlayerList() {
            refreshPlayersList();
        }
}