package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;
import src.Database.User;
import src.Database.Post;

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

            Post[] post = new Post[postAmount];
            post = db.fetchPost(uid,stmt,conn);

            // 顶部面板
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel userLabel = new JLabel("用户名:"+user.username);
            JLabel uidLabel = new JLabel("UID:"+user.uid);
            JLabel levelLabel = new JLabel("贴子数："+postAmount);
            JLabel balanceLabel = new JLabel("余额: "+user.balance);
            JLabel signatureLabel = new JLabel("个性签名: "+user.shows);
            Font font = new Font("微软雅黑", Font.PLAIN, 16);

            userLabel.setFont(font);
            uidLabel.setFont(font);
            levelLabel.setFont(font);
            balanceLabel.setFont(font);
            signatureLabel.setFont(font);

            topPanel.add(userLabel);
            topPanel.add(uidLabel);
            topPanel.add(levelLabel);
            topPanel.add(balanceLabel);


            // 添加个性签名
            JPanel signaturePanel = new JPanel();
            signaturePanel.add(signatureLabel);

            // 帖子列表
            listModel = new DefaultListModel<>();
            for (int i = 0; i < postAmount; i++) {
                listModel.addElement("Post " + (i + 1)+"\t标题：\t"+post[i].title);
            }
            postList = new JList<>(listModel);
            postList.setFont(font);
            Post[] finalPost = post;
            postList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // 检查是否是双击
                        int index = postList.locationToIndex(e.getPoint()); // 获取被点击的项的索引
                        Posts po = new Posts(2, finalPost[index].tid, finalPost[index].title, finalPost[index].content, stmt, conn);
                        po.setVisible(true);
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(postList);

            // 添加到底部面板的按钮
            JButton billButton = new JButton("新建账单");
            billButton.setFont(font);
            JButton postButton = new JButton("新建Post");
            postButton.setFont(font);
            JButton changeButton = new JButton("修改签名");
            changeButton.setFont(font);
            JButton fresh = new JButton("刷新");
            fresh.setFont(font);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1, 4, 10, 10));

            panel.add(billButton);
            panel.add(postButton);
            panel.add(changeButton);
            panel.add(fresh);

            billButton.addActionListener(e -> {
                // 这里应该创建并显示BillFrame
                // BillFrame billFrame = new BillFrame();
                // billFrame.setVisible(true);
            });

            postButton.addActionListener(e -> {
                new Posts(1, uid, "", "", stmt, conn);
            });

            changeButton.addActionListener(e -> {
                // 这里应该创建并显示BillFrame
                // BillFrame billFrame = new BillFrame();
                // billFrame.setVisible(true);
            });

            fresh.addActionListener(e -> {
                User user1 = db.getUserInfo(uid,stmt,conn);
                Post[] post1 = db.fetchPost(uid,stmt,conn);
                listModel.clear();
                for (int i = 0; i < user1.post; i++) {
                    listModel.addElement("Post " + (i + 1)+"\t标题：\t"+post1[i].title);
                }
            });

            Panel allTop = new Panel();
            allTop.add(topPanel,BorderLayout.NORTH);
            allTop.add(signaturePanel,BorderLayout.SOUTH);

            // 组装布局
            add(allTop,BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            add(panel, BorderLayout.SOUTH);

            setVisible(true);
        }
    }

    // PostDetailFrame 和 BillFrame 的实现省略，它们将是类似的JFrame子类



}