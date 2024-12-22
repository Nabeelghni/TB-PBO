public class InputValidator { // Mendefinisikan kelas InputValidator
    public static boolean isValidName(String name) { // Metode statis untuk memvalidasi nama
        return name != null && !name.trim().isEmpty(); // Mengembalikan true jika nama tidak null dan tidak kosong
    }

    public static boolean isValidHeight(double height) { // Metode statis untuk memvalidasi tinggi
        return height > 0; // Mengembalikan true jika tinggi lebih besar dari 0
    }

    public static boolean isValidSalary(double salary) { // Metode statis untuk memvalidasi gaji
        return salary >= 0; // Mengembalikan true jika gaji tidak negatif
    }
}