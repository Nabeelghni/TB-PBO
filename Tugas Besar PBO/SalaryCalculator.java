public class SalaryCalculator { // Mendefinisikan kelas SalaryCalculator
    public static double calculateAnnualSalary(double salaryPerMatch) { // Metode statis untuk menghitung gaji tahunan
        int matchesPerYear = 28; // Jumlah pertandingan dalam setahun
        return salaryPerMatch * matchesPerYear; // Menghitung gaji tahunan
    }
}