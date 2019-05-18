package main.project_2.client;


import main.project_2.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;


/**
 * java类简单作用描述
 *
 * @ProjectName: Test
 * @Description: java类作用描述
 * @Author: horse
 * @CreateDate: 2019/5/16 19:11
 * @UpdateUser: horse
 * @UpdateDate: 2019/5/16 19:11
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
public class System_v_client{
    public static void main(String[]args){
        InitGlobalFont(new Font("alias", Font.PLAIN,20));
        view=new View();
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

    private static View view;

    static class View extends JFrame implements ActionListener {
        //
        private JScrollPane west;
        private DefaultListModel defaultListModel;//在线用户
        private JList jList;
        //
        private JScrollPane east;
        private JTextArea east_area;//聊天信息
        //
        private JPanel south;
        private JTextField south_area;//发送内容
        private JButton south_bt;
        //
        private JPanel north;
        private JTextField jTextField_2;
        //
        private System_m_client system_m_client;
        private boolean isConnected = false;

        public View(){
            super("在线聊天");
            int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
            this.setSize(screen_width/2,screen_height/2);
            this.setLocation((screen_width - this.getWidth()) / 2,
                    (screen_height - this.getHeight()) / 2);

            //north
            north=new JPanel();
            north.setLayout(new BorderLayout());
            JPanel jPanel_north=new JPanel();
            jPanel_north.setLayout(new GridLayout(1,3));

            JTextArea jTextArea_1=new JTextArea("          客户端");
            jTextArea_1.setEnabled(false);
            jTextArea_1.setFont(new Font("宋体", Font.PLAIN, 50));

            JTextField jTextField=new JTextField("用户名");
            jTextField.setFont(new Font("宋体", Font.PLAIN, 30));
            jTextField.setEnabled(false);
            jTextField_2=new JTextField(10);
            jTextField_2.setFont(new Font("宋体", Font.PLAIN, 30));
            jTextField_2.setEnabled(true);
            JButton jButton_north=new JButton("连接");
            jButton_north.setFont(new Font("宋体", Font.PLAIN, 30));
            jButton_north.addActionListener(this);

            jPanel_north.add(jTextField);
            jPanel_north.add(jTextField_2);
            jPanel_north.add(jButton_north);

            north.add(jTextArea_1,BorderLayout.CENTER);
            north.add(jPanel_north,BorderLayout.EAST);

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
            south_bt.addActionListener(this);
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
            String str=e.getActionCommand();
            switch (str){
                case "发送":
                    String message=south_area.getText().trim();
                    if(isConnected){
                        system_m_client.send(message);
                        south_area.setText("");
                    }else{
                        show_error("服务器尚未连接");
                    }
                    break;
                case "连接":
                    String name=jTextField_2.getText().trim();
                    String ip="";
                    try {
                        ip= InetAddress.getLocalHost().getHostAddress();
                    } catch (UnknownHostException ex) {
                        ex.printStackTrace();
                    }
                    system_m_client=new System_m_client();
                    User user=new User();
                    user.setName(name);
                    user.setIp(ip);
                    if(system_m_client.connectServer(user,view)){
                        //连接成功
                        isConnected=true;
                    }else{
                        //连接失败
                        show_error("连接失败");
                    }
                    break;
                default:
                    System.out.println("出错啦");
            }
        }

        /*
         * 修改在线人数
         * */
        public void func_1(String str){
            defaultListModel.addElement(str+"       \n");
        }
        /*
         * 修改聊天内容
         * */
        public void func_2(String name,String content){
            Date now=new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String time=ft.format(now);
            east_area.append(name+"   "+time+"\n"+content+"\n");
        }

        private void show_error(String str){
            JOptionPane.showMessageDialog(this, str, "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
