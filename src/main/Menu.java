package main;

import main.project_1.System_v;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * java类简单作用描述
 *
 * @ProjectName: Test
 * @Description: java类作用描述
 * @Author: horse
 * @CreateDate: 2019/5/5 1:09
 * @UpdateUser: horse
 * @UpdateDate: 2019/5/5 1:09
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
public class Menu extends JFrame implements ActionListener {
    private static Container container;

    public static void main(String[] args){
        Menu menu = new Menu();

        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-300,
                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-300,600,600);
        menu.setVisible(true);
    }

    public Menu(){
        setting();
        setting_menu();
        setting_content();
    }

    private void setting(){
        container=getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.setBackground(Color.LIGHT_GRAY);
        this.setTitle("上机作业");
    }

    public void setting_menu(){
        //创建菜单
        JMenuBar jmb = new JMenuBar();
        //不能设定位置，会自动放在最上部
        this.setJMenuBar(jmb);
        //添加菜单
        JMenu menu1 = new JMenu("项目");
        JMenu menu2 = new JMenu("关于");
        JMenuItem item1 = new JMenuItem("图书馆");
        JMenuItem item2 = new JMenuItem("在线聊天");
        JMenuItem item3 = new JMenuItem("关于开发者");
        //添加菜单项至菜单上
        menu1.add(item1);
        menu1.add(item2);
        menu2.add(item3);
        //将菜单加入至菜单条
        jmb.add(menu1);
        jmb.add(menu2);
        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
    }

    private void setting_content(){
        JTextArea textArea=new JTextArea();
        textArea.setText("欢迎使用");
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setFont(new Font("宋体", Font.PLAIN, 30));
        container.add(textArea);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String str = e.getActionCommand();
        System.out.println(str);
        if("图书馆".equals(str)) {
            new System_v();
        }
        else if("在线聊天".equals(str)){
        }
        else if("关于开发者".equals(str)){
        }
    }
}
