package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import src.Database.*;

import static src.UserCenter.MainFrame;

/**
 * @author 32808
 */
public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginForm(Statement stmt, Connection conn) {
        // 设置窗口标题
        setTitle("请先登录");
        // 设置窗口大小和位置
        setSize(300, 200);
        Font font = new Font("微软雅黑", Font.PLAIN, 16);

        setLocationRelativeTo(null);
        // 居中显示

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 点击关闭按钮时退出程序


        // 创建组件
        JLabel usernameLabel = new JLabel("用户UID:");
        usernameField = new JTextField(20);
        usernameLabel.setFont(font);
        usernameField.setFont(font);

        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);
        passwordLabel.setFont(font);
        passwordField.setFont(font);

        // 创建登录和注册按钮
        loginButton = new JButton("登录");
        registerButton = new JButton("注册");
        loginButton.setFont(font);
        registerButton.setFont(font);

        // 设置登录按钮的点击事件
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int username = Integer.parseInt(usernameField.getText());
                String password = new String(passwordField.getPassword());

                if (Database.checkPassword(username,password,stmt,conn)) {
                    // 登陆成功
                    MainFrame mainFrame = new MainFrame(username, stmt,conn);
                    mainFrame.setVisible(true);
                    LoginForm.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "密码错误！");
                }

            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterForm registerForm = new RegisterForm(stmt, conn);
                registerForm.setVisible(true);
            }
        });

        // 布局组件
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(3, 2, 10, 10));
        // 使用网格布局

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);


        panel.add(loginButton);
        panel.add(registerButton);

        // 添加面板到窗口
        add(panel);

        // 设置窗口可见
        setVisible(true);
    }


}