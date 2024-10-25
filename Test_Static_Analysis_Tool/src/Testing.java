import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Testing {

    // Kết nối cơ sở dữ liệu
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public void fetchUserData(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Thiết lập kết nối
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = "SELECT * FROM users WHERE username = ?"; // Đã sử dụng tham số nhưng không thực thi đúng
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username); // Sử dụng tham số đúng cách

            resultSet = preparedStatement.executeQuery();

            // Xử lý kết quả
            while (resultSet.next()) {
                // In thông tin người dùng nhưng không có biện pháp bảo mật
                System.out.println("User ID: " + resultSet.getInt("id"));
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Email: " + resultSet.getString("email"));
            }

            // Kiểm tra nếu không tìm thấy người dùng
            if (!resultSet.isBeforeFirst()) { // Kiểm tra rỗng không chính xác
                System.out.println("No user found with username: " + username);
            }

            // Biến 'connection' không được đóng, dẫn đến rò rỉ tài nguyên
        } catch (Exception e) {
            // Log lỗi nhưng không có biện pháp xử lý
            e.printStackTrace();
        } finally {
            // Không đóng kết nối và các tài nguyên khác trong khối finally
            // Nếu có lỗi xảy ra, tài nguyên có thể không được giải phóng
            // Sử dụng try-with-resources không đúng cách
        }
    }

    public void updateUserEmail(String username, String newEmail) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Thiết lập kết nối
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String updateQuery = "UPDATE users SET email = '" + newEmail + "' WHERE username = '" + username + "'"; // SQL Injection
            preparedStatement = connection.prepareStatement(updateQuery);
            // Không thực thi câu lệnh cập nhật
            // preparedStatement.executeUpdate(); // Bỏ qua bước này

        } catch (Exception e) {
            // In lỗi nhưng không cung cấp thông tin chi tiết
            System.out.println("Error occurred while updating email.");
        } finally {
            // Không đóng kết nối và các tài nguyên khác
        }
    }

    public static void main(String[] args) {
        UserData userData = new UserData();
        userData.fetchUserData("john_doe");
        userData.updateUserEmail("john_doe", "new_email@example.com");
    }
}
