package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;
import src.Database.User;

public class UserCenter {

    public static class MainFrame extends JFrame {

        private JList<String> postList;
        private DefaultListModel<String> listModel;

        public MainFrame(int uid, Statement stmt, Connection conn) {
            setTitle("用户界面示例");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            Database db = new Database();

            User user = db.getUserInfo(uid,stmt,conn);

            int postAmount = user.post;

            // 顶部面板
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel userLabel = new JLabel("用户名:"+user.username);
            JLabel uidLabel = new JLabel("UID:"+user.uid);
            JLabel levelLabel = new JLabel("贴子数："+postAmount);
            JLabel balanceLabel = new JLabel("余额: "+user.balance);
            JLabel signatureLabel = new JLabel("个性签名: "+user.shows);
            topPanel.add(userLabel);
            topPanel.add(uidLabel);
            topPanel.add(levelLabel);
            topPanel.add(balanceLabel);

            JPanel topSeparator = new JPanel(); // 可以用来添加分隔线或空间
            topSeparator.setPreferredSize(new Dimension(0, 10)); // 高度为10像素

            // 添加个性签名
            JPanel signaturePanel = new JPanel();
            signaturePanel.add(signatureLabel);

            // 帖子列表
            listModel = new DefaultListModel<>();
            for (int i = 0; i < postAmount; i++) {
                listModel.addElement("Post " + (i + 1));
            }
            postList = new JList<>(listModel);
            postList.addMouseListener(new MouseAdapter() {
                public void mouseDoubleClicked(MouseEvent e) {
                    System.out.println("双击了帖子: " + postList.getSelectedValue());
                }
            });
            JScrollPane scrollPane = new JScrollPane(postList);

            // 添加到底部面板的按钮
            JButton billButton = new JButton("账单");
            billButton.addActionListener(e -> {
                // 这里应该创建并显示BillFrame
                // BillFrame billFrame = new BillFrame();
                // billFrame.setVisible(true);
                System.out.println("点击了账单按钮");
            });

            // 组装布局
            add(topPanel, BorderLayout.NORTH);
            add(topSeparator, BorderLayout.NORTH);
            add(signaturePanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            add(billButton, BorderLayout.SOUTH);

            setVisible(true);
        }
    }

    // PostDetailFrame 和 BillFrame 的实现省略，它们将是类似的JFrame子类


}