package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import src.Database.*;
/**
 * @author 32808
 */
public class UserCenter {


    // ... 假设的帖子和账单列表数据

    public static void userCenter(int uid, Statement stmt, Connection conn) {
        Database db = new Database();
        User user = db.getUserInfo(uid, stmt, conn);
        final String username = user.username;
        final String signature = user.shows;
        final int post_count = user.post;
        final double balance = user.balance;
        // 创建并设置用户中心界面
        JFrame userCenterFrame = new JFrame("User Center");
        userCenterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userCenterFrame.setSize(800, 600);
        userCenterFrame.setLayout(new BorderLayout());

        // 添加用户信息组件（JLabel等）
        // ...

        // 假设的帖子列表
        DefaultListModel<String> postListModel = new DefaultListModel<>();
        for (int i = 0; i < post_count; i++) {
            postListModel.addElement("Post " + (i + 1));
        }
        JList<String> postList = new JList<>(postListModel);
        JScrollPane postScrollPane = new JScrollPane(postList);
        userCenterFrame.add(postScrollPane, BorderLayout.CENTER);

        // 账单按钮和监听器
        JButton billButton = new JButton("Bill");
        billButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开账单窗口
                showBillWindow();
            }
        });
        userCenterFrame.add(billButton, BorderLayout.PAGE_END);

        userCenterFrame.setVisible(true);
    }

    private static void showBillWindow() {
        // 创建并设置账单窗口
        JFrame billFrame = new JFrame("Bill");
        billFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        billFrame.setSize(400, 300);
        billFrame.setLocationRelativeTo(null);


        billFrame.setVisible(true);
    }

    // ... 其他代码，如设置组件属性、布局等
}