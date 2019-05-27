package main;

import main.project_1.System_v;
import main.project_2.server.System_v_server;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

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
        InitGlobalFont(new Font("alias", Font.PLAIN,20));
        new Menu();
    }

    public Menu(){
        super("上机作业");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        container=getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.setBackground(Color.LIGHT_GRAY);
        JMenuBar jmb = new JMenuBar();
        this.setJMenuBar(jmb);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JMenu menu1 = new JMenu("项目");
        JMenu menu2 = new JMenu("关于");
        JMenuItem item1 = new JMenuItem("图书馆");
        JMenuItem item2 = new JMenuItem("在线聊天");
        JMenuItem item3 = new JMenuItem("关于开发者");
        menu1.add(item1);
        menu1.add(item2);
        menu2.add(item3);
        jmb.add(menu1);
        jmb.add(menu2);
        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        JTextArea textArea=new JTextArea();
        textArea.setText("\n\n\n欢  迎  使  用  !");
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setEnabled(false);
        textArea.setDisabledTextColor(Color.RED);
        textArea.setFont(new Font("宋体", Font.PLAIN, 50));
        container.add(textArea);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e){
        String str = e.getActionCommand();
        System.out.println(str);
        if("图书馆".equals(str)) {
            new System_v();
        }
        else if("在线聊天".equals(str)){
            new System_v_server();
        }
        else if("关于开发者".equals(str)){
            //
        }
    }



    /**
     * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
     */
    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys();
             keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
