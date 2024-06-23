package src;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Statement;

public class Bills extends JFrame {

        public Bills(int type, int id, String title, String content, double amount, Statement stmt, Connection conn) {
            if (type == 1) {
                // 1修改 2创建
                setTitle("新建账单");
            } else {
                setTitle("修改账单");
            }
            // 设置窗口标题和大小
            setSize(400, 600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            Font font = new Font("微软雅黑", Font.PLAIN, 16);

            // 创建大编辑框
            JTextArea largeTextArea = new JTextArea(5, 20);
            largeTextArea.setLineWrap(true);
            largeTextArea.setText(content);
            // 5行，20列
            JScrollPane largeScrollPane = new JScrollPane(largeTextArea);
            // 使用滚动面板包装大编辑框

            // 创建小编辑框
            JTextField smallTextField = new JTextField(20);
            smallTextField.setText(title);
            // 20个字符宽度
            largeTextArea.setFont(font);
            smallTextField.setFont(font);
            // 创建按钮
            JButton button = new JButton("确定");
            button.setFont(font);

            JRadioButton radioButton1 = new JRadioButton("收入账单请勾选。金额填写在下面");
            radioButton1.setFont(font);
            JTextField smallTextField1 = new JTextField(20);
            smallTextField1.setText(String.valueOf(amount));

            if (type == 2){
                smallTextField1.setText("金额不支持修改");
                smallTextField1.setEditable(false);
            }

            // 将单选框添加到面板中



            button.addActionListener(e -> {
                if (type == 1) {
                    if (radioButton1.isSelected()){
                        Database.createBill(smallTextField.getText(), largeTextArea.getText(), id, 0.0-Double.parseDouble(smallTextField1.getText()), stmt, conn);
                    } else {
                        Database.createBill(smallTextField.getText(), largeTextArea.getText(), id, Double.parseDouble(smallTextField1.getText()), stmt, conn);
                    }

                    JOptionPane.showMessageDialog(null, "创建成功！");
                    dispose();
                } else {
                    Database.updateBill(id, smallTextField.getText(), largeTextArea.getText(), stmt, conn);
                    JOptionPane.showMessageDialog(null, "修改成功！");
                    dispose();
                }
            });

            // 创建面板以组织组件
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            // 使用BorderLayout来组织组件
            JPanel panel1 = new JPanel();
            panel1.setLayout(new BorderLayout());

            panel1.add(radioButton1, BorderLayout.NORTH);

            panel1.add(smallTextField1, BorderLayout.CENTER);
            // 添加组件到面板
            panel.add(largeScrollPane, BorderLayout.CENTER);
            // 大编辑框放在中央
            panel.add(smallTextField, BorderLayout.NORTH);
            // 小编辑框放在北部
            panel1.add(button, BorderLayout.SOUTH);
            // 按钮放在南部
            panel.add(panel1, BorderLayout.SOUTH);

            // 添加面板到窗口
            add(panel);

            // 设置窗口可见
            setVisible(true);
        }

    }

