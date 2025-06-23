import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrugPreventionApp {
    // Tạo cửa sổ chính của ứng dụng
    public static void main(String[] args) {
        JFrame frame = new JFrame("Phòng Ngừa Sử Dụng Ma Túy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null); // Đặt cửa sổ vào giữa màn hình
        
        // Tạo một JPanel chứa các phần tử giao diện
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Đặt các phần tử theo chiều dọc
        
        // Thêm một tiêu đề
        JLabel titleLabel = new JLabel("Thông Tin Phòng Ngừa Ma Túy", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);
        
        // Thêm một đoạn mô tả về tác hại của ma túy
        JTextArea infoArea = new JTextArea(
                "Ma túy gây ra rất nhiều tác hại nghiêm trọng đến sức khỏe như: \n" +
                "- Tổn hại đến hệ thần kinh, làm suy yếu trí nhớ.\n" +
                "- Tăng nguy cơ mắc các bệnh tim mạch và ung thư.\n" +
                "- Gây nghiện, ảnh hưởng đến tinh thần và cuộc sống gia đình.\n\n" +
                "Phòng ngừa sử dụng ma túy bằng cách:\n" +
                "- Từ chối các lời mời sử dụng ma túy.\n" +
                "- Tìm kiếm sự giúp đỡ từ gia đình, bạn bè hoặc chuyên gia.\n" +
                "- Tham gia các hoạt động thể dục thể thao để giảm căng thẳng.\n",
                10, 40);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoArea.setEditable(false);
        panel.add(infoArea);
        
        // Thêm một nút thoát
        JButton exitButton = new JButton("Thoát");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Đóng ứng dụng khi nhấn nút thoát
            }
        });
        panel.add(exitButton);
        
        // Thêm panel vào frame
        frame.add(panel);
        
        // Hiển thị cửa sổ
        frame.setVisible(true);
    }
}
