package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;


/**
 * @author 32808
 */
public class RegisterForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;

    public RegisterForm(Statement stmt, Connection conn) {
        setTitle("Register Form");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);

        Font font = new Font("微软雅黑", Font.PLAIN, 16);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // 使用DISPOSE_ON_CLOSE来释放资源

        // 创建组件
        JLabel usernameLabel = new JLabel("用户昵称:");
        usernameField = new JTextField(20);
        usernameLabel.setFont(font);
        usernameField.setFont(font);

        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);
        passwordLabel.setFont(font);
        passwordField.setFont(font);

        JLabel confirmPasswordLabel = new JLabel("确认密码:");
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordLabel.setFont(font);
        confirmPasswordField.setFont(font);

        registerButton = new JButton("注册");
        registerButton.setFont(font);


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String password2 = new String(confirmPasswordField.getPassword());
                int uid = -1;

                if (password2.equals(password)) {
                    // 注册
                    uid = Database.createUser(username,password,stmt,conn);
                    JOptionPane.showMessageDialog(null, "注册成功！您的UID是"+uid+"。请牢记，登陆时使用uid");
                    RegisterForm.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "密码不一致！请仔细检查");
                }

            }
        });

        // 布局组件
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);

        panel.add(new JLabel());
        // 添加一个空标签以保持布局对称

        panel.add(registerButton);

        // 添加面板到窗口
        add(panel);

        // 设置窗口可见
        setVisible(true);
    }

}