package main.project_1;

import main.project_1.ui.Search_All;
import main.project_1.ui.Borrow;
import main.project_1.ui.Return;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * java类简单作用描述
 *
 * 图书馆系统对应的界面
 *
 * @ProjectName: Test
 * @Description: java类作用描述
 * @Author: horse
 * @CreateDate: 2019/5/5 1:12
 * @UpdateUser: horse
 * @UpdateDate: 2019/5/5 1:12
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
public class System_v extends JFrame implements ActionListener {
    public System_v(){
        super("图书馆系统");
        this.setBackground(Color.LIGHT_GRAY);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel=new JPanel();
        JButton jButton_1=new JButton("借书");
        JButton jButton_2=new JButton("还书");
        JButton jButton_3=new JButton("查看所有图书馆借出去的书");
        jPanel.add(jButton_1);
        jPanel.add(jButton_2);
        jPanel.add(jButton_3);
        jButton_1.addActionListener(this);
        jButton_2.addActionListener(this);
        jButton_3.addActionListener(this);

        jButton_1.setLocation(200,200);
        jPanel.setLocation(500,500);
        this.setContentPane(jPanel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String str=e.getActionCommand();
        System.out.println(str);
        switch(str){
            case "借书":
                new Borrow();
                break;
            case "还书":
                new Return();
                break;
            case "查看所有图书馆借出去的书":
                new Search_All();
                break;
            default:
                System.out.println("System_v没有找到匹配");
                break;
        }
    }
}
