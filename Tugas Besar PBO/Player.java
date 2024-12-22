import java.util.Date; // Mengimpor kelas Date dari paket java.util

public class Player extends Person { // Mendefinisikan kelas Player yang merupakan turunan dari kelas Person
    private int id; // Menyimpan ID pemain
    private String position; // Menyimpan posisi pemain
    private int skillLevel; // Menyimpan level keterampilan pemain
    private Date joinDate; // Menyimpan tanggal bergabung pemain
    private int points; // Menyimpan jumlah poin yang diperoleh pemain

    public Player(int id, String name, double height, String position, int skillLevel, Date joinDate) { // Constructor untuk menginisialisasi objek Player
        super(name, height); // Memanggil constructor dari kelas induk (Person)
        this.id = id; // Mengatur ID pemain
        this.position = position; // Mengatur posisi pemain
        this.skillLevel = skillLevel; // Mengatur level keterampilan pemain
        this.joinDate = joinDate; // Mengatur tanggal bergabung pemain
        this.points = 0; // Menginisialisasi poin pemain dengan 0
    }

    // Tambahkan method setId
    public void setId(int id) {
    this.id = id;
    
    }

    @Override
    public String toString() { // Metode untuk mengembalikan representasi string dari objek Player
        return getName() + " = " + getId(); // Mengembalikan nama dan ID pemain
    }

    public void setPosition(String position) { // Metode untuk mengatur posisi pemain
        this.position = position; // Mengatur posisi pemain
    }

    public void setSkillLevel(int skillLevel) { // Metode untuk mengatur level keterampilan pemain
        this.skillLevel = skillLevel; // Mengatur level keterampilan pemain
    }
    
    public int getId() { // Getter untuk ID
        return id; // Mengembalikan ID pemain
    }
    
    public String getPosition() { // Getter untuk posisi
        return position; // Mengembalikan posisi pemain
    }

    public int getSkillLevel() { // Getter untuk level keterampilan
        return skillLevel; // Mengembalikan level keterampilan pemain
    }

    public int getPoints() { // Getter untuk jumlah poin
        return points; // Mengembalikan jumlah poin pemain
    }

    public void addPoints(int points) { // Metode untuk menambahkan poin
        this.points += points; // Menambahkan poin yang dicetak
    }

    public double calculateSalary() { // Metode untuk menghitung gaji pemain
        if (skillLevel >= 8){
            return 6_000_000;
            
        } else {
            return 2_000_000;
        }   
    }

    public Date getJoinDate() { // Getter untuk tanggal bergabung
        return joinDate; // Mengembalikan tanggal bergabung pemain
    }
}