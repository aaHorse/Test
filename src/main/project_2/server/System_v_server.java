package main.project_2.server;

import main.project_2.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * java类简单作用描述
 *
 * @ProjectName: Test
 * @Description: java类作用描述
 * @Author: horse
 * @CreateDate: 2019/5/16 19:14
 * @UpdateUser: horse
 * @UpdateDate: 2019/5/16 19:14
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
public class System_v_server {
    private static View view;
    private System_m_server system_m_server;

    public System_v_server(){
        view=new View();
        system_m_server=new System_m_server();
        system_m_server.serverStart(view);
    }

    class View extends JFrame implements ActionListener{
        //
        private JScrollPane west;
        private DefaultListModel defaultListModel;
        private JList jList;
        //
        private JScrollPane east;
        private JTextArea east_area;
        //
        private JPanel south;
        private JTextField south_area;
        private JButton south_bt;
        //
        private JPanel north;
        //

        public View(){
            super("在线聊天");
            int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
            this.setSize(screen_width/2,screen_height/2);
            this.setLocation((screen_width - this.getWidth()) / 2,
                    (screen_height - this.getHeight()) / 2);

            //north
            north=new JPanel();
            JTextArea jTextArea_1=new JTextArea("服务器端");
            jTextArea_1.setEnabled(false);
            jTextArea_1.setFont(new Font("宋体", Font.PLAIN, 50));
            north.add(jTextArea_1);

            //west
            defaultListModel=new DefaultListModel();
            jList=new JList(defaultListModel);
            west = new JScrollPane(jList);
            west.setBorder(new TitledBorder("在线用户"));

            //east
            east_area=new JTextArea();
            east_area.setEnabled(false);
            east=new JScrollPane(east_area);
            east.setBorder(new TitledBorder("聊天记录"));

            JSplitPane jSplitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, west, east);
            jSplitPane.setDividerLocation(5);

            //south
            south=new JPanel(new BorderLayout());
            south.setBorder(new TitledBorder("发信息"));
            south_area=new JTextField();
            south_bt=new JButton("发送");
            south.add(south_area,BorderLayout.CENTER);
            south.add(south_bt,BorderLayout.EAST);

            //合起來
            this.setLayout(new BorderLayout());
            this.add(north,BorderLayout.NORTH);
            this.add(west,BorderLayout.WEST);
            this.add(east,BorderLayout.CENTER);
            this.add(south,BorderLayout.SOUTH);
            this.setVisible(true);


        }



        @Override
        public void actionPerformed(ActionEvent e) {

        }

        //修改在线人数
        public void func_1(String str){
            defaultListModel.addElement(str);
        }

        //修改聊天记录
        public void func_2(String str){
            east_area.append(str);
        }
    }
}
