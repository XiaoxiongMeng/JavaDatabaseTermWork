package src;

import src.Database.Bill;
import src.Database.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;

public class BillCenter {

    public static class MainFrame extends JFrame {

        private JList<String> postList;
        private DefaultListModel<String> listModel;


        public MainFrame(int uid, Statement stmt, Connection conn) {
            setTitle("账单中心");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            Database db = new Database();

            User user = db.getUserInfo(uid,stmt,conn);

            int billAmount = Database.getBillAmount(uid,stmt,conn);

            Bill[] bill = new Bill[billAmount];
            bill = db.fetchBill(uid,stmt,conn);

            // 顶部面板
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel userLabel = new JLabel("用户名:"+user.username);
            JLabel uidLabel = new JLabel("UID:"+user.uid);
            JLabel levelLabel = new JLabel("贴子数："+billAmount);
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
            for (int i = 0; i < billAmount; i++) {
                listModel.addElement("Bill " + (i + 1)+"\t标题：\t"+bill[i].title);
            }
            postList = new JList<>(listModel);
            postList.setFont(font);
            final Bill[][] finalPost = {bill};
            postList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // 检查是否是双击
                        int index = postList.locationToIndex(e.getPoint()); // 获取被点击的项的索引
                        Bills po = new Bills(2, finalPost[0][index].bid, finalPost[0][index].title, finalPost[0][index].content, finalPost[0][index].amount, stmt, conn);
                        po.setVisible(true);
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(postList);

            // 添加到底部面板的按钮
            JButton postButton = new JButton("新建Bill");
            postButton.setFont(font);
            JButton changeButton = new JButton("修改签名");
            changeButton.setFont(font);
            JButton fresh = new JButton("刷新");
            fresh.setFont(font);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1, 4, 10, 10));

            panel.add(postButton);
            panel.add(changeButton);
            panel.add(fresh);


            postButton.addActionListener(e -> {
                new Bills(1, uid, "", "", 0, stmt, conn);
            });

            changeButton.addActionListener(e -> {
                // 这里应该创建并显示BillFrame
                // BillFrame billFrame = new BillFrame();
                // billFrame.setVisible(true);
            });

            fresh.addActionListener(e -> {
                int amount1 = Database.getBillAmount(uid, stmt, conn);
                Bill[] bill1 = db.fetchBill(uid,stmt,conn);
                listModel.clear();
                for (int i = 0; i < amount1; i++) {
                    listModel.addElement("Bill " + (i + 1)+"\t标题：\t"+bill1[i].title);
                }
                finalPost[0] = bill1;
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