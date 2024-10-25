import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Testing {

    // Lưu mật khẩu người dùng thô mà không mã hóa (lỗi bảo mật)
    private static Map<String, String> userPasswords = new HashMap<String, String>() {{
        put("admin", "password123");
        put("user", "123456");
        put("guest", "guest123");
    }};
    
    // Hàm đăng nhập với nhiều lỗi bảo mật và logic
    public static boolean login(String username, String password) {
        try {
            // Lỗi: Sử dụng MD5 không an toàn và kiểm tra mật khẩu thô
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            String hashedPassword = new String(md.digest());

            if (userPasswords.containsKey(username) && userPasswords.get(username).equals(password)) {
                System.out.println("Login successful!");
                // Lỗi: In mật khẩu ra màn hình
                System.out.println("Welcome, " + username + "! Your password is " + password);
                return true;
            } else {
                System.out.println("Login failed!");
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm đọc file mà không có kiểm tra quyền truy cập
    public static void readFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("File not found: " + filename);
        }
    }

    // Hàm tạo mật khẩu ngẫu nhiên với độ an toàn thấp
    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {  // Lỗi: Mật khẩu ngắn và dễ đoán
            password.append((char) ('a' + random.nextInt(26)));
        }
        return password.toString();
    }

    // Hàm tính chiết khấu với lỗi logic
    public static double calculateDiscount(double price, double discountPercentage) {
        if (price < 0 || discountPercentage < 0) {
            return -1;  // Lỗi: Trả về giá trị âm cho đầu vào không hợp lệ
        }
        double discount = price * (discountPercentage / 100);
        double finalPrice = price - discount;
        if (finalPrice < 0) {
            System.out.println("Error: final price is negative!");
            finalPrice = 0;  // Lỗi: Đặt giá trị trực tiếp không hợp lý
        }
        return finalPrice;
    }

    // Hàm lưu thông tin người dùng mà không mã hóa
    public static void saveUserInfo(String username, String password) {
        try (FileWriter writer = new FileWriter("user_info.txt", true)) {
            // Lỗi bảo mật: Lưu thông tin không mã hóa
            writer.write(username + ":" + password + "\n");
            System.out.println("User information saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm tính tổng số lớn không tối ưu và dễ gặp lỗi overflow
    public static long calculateSum(int[] numbers) {
        long total = 0;
        for (int num : numbers) {
            total += num;
        }
        if (total > 1000000) {
            System.out.println("Warning: total exceeds limit!");
        }
        return total;
    }

    // Hàm xử lý đầu vào không chuẩn và dễ gây lỗi
    public static void processInput(String input) {
        if (input == null || input.isEmpty()) {
            System.out.println("Input is empty!");
            return;
        }
        // Lỗi: Không xử lý đầu vào an toàn
        String result = input.substring(0, input.length() + 1); // Lỗi: Lỗi ArrayIndexOutOfBounds
        System.out.println("Processed input: " + result);
    }

    // Hàm tính tổng hợp với logic không hợp lệ
    public static double calculateAverage(int[] numbers) {
        if (numbers.length == 0) return 0; // Lỗi: Chia cho 0
        double total = calculateSum(numbers);
        return total / numbers.length;
    }

    // Hàm xử lý chiết khấu với điều kiện không hợp lệ
    public static double applyDiscount(double price, double discount) {
        if (price < 0) {
            System.out.println("Price cannot be negative!"); // Lỗi: Phải ném ngoại lệ thay vì in thông báo
            return price;
        }
        if (discount < 0 || discount > 100) { // Lỗi: không xử lý đúng giá trị chiết khấu
            System.out.println("Invalid discount value!");
            return price;
        }
        return price - (price * (discount / 100));
    }

    // Các hàm không được sử dụng (code smell)
    public static void unusedFunction() {
        System.out.println("This function is not used.");
    }

    public static int anotherUnusedFunction(int x, int y) {
        return x + y;
    }

    public static void main(String[] args) {
        // Gọi các hàm để tạo thêm lỗi logic
        login("admin", "wrongpassword");
        System.out.println("Discount: " + calculateDiscount(100, -20));  // Test với chiết khấu âm
        saveUserInfo("new_user", "new_password");

        // Đọc file không tồn tại để gây lỗi IO
        readFile("nonexistent_file.txt");

        // Xử lý đầu vào không hợp lệ
        processInput(null);
        processInput("");

        // Tính tổng số lớn
        int[] numbers = {1, 2, 3, 4, 5, 1000000};
        System.out.println("Total sum: " + calculateSum(numbers));

        // Tính giá trung bình
        System.out.println("Average: " + calculateAverage(numbers));

        // Áp dụng chiết khấu không hợp lệ
        double price = applyDiscount(-50, 30);
        System.out.println("Price after discount: " + price);
    }
}
