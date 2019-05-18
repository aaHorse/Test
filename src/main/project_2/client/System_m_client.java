package main.project_2.client;

import main.project_2.User;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
public class System_m_client {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private MessageThread messageThread;// 负责接收消息的线程
    private Map<String, User> onLineUsers = new HashMap<String, User>();// 所有在线用户
    private User user;
    private System_v_client.View view;


    public boolean connectServer(User user, System_v_client.View view) {
        try {
            this.user=user;
            this.view=view;

            socket = new Socket("127.0.0.1", 1234);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 发送客户端用户基本信息(用户名和ip地址)
            writer.println(pack(7,user.getName(),user.getIp(),""));
            writer.flush();
            // 开启接收消息的线程
            messageThread = new MessageThread(reader);
            messageThread.start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void send(String message) {
        writer.println(pack(6,user.getName(),user.getIp(),message));
        writer.flush();
    }



    /*
     *  0:成功连接服务器
     *  1：给客户端反馈目前的在线人员信息
     *  2:给所有客户端发送新用户的上线信息
     *  3:服务器转发单人发送的信息
     *  4：服务器转发群发的信息
     *  5:服务器进行群发信息
     *  6:客户端给服务器发信息
     *  7:客户端给服务器发送连接的基本信息
     * */
    private String pack(int code,String name,String ip,String content){
        return code+"%"+name+"%"+ip+"%"+content;
    }

    /*
     * 监听服务器，接收服务器返回的数据
     * */
    class MessageThread extends Thread {
        private BufferedReader reader;

        // 接收消息线程的构造方法
        public MessageThread(BufferedReader reader) {
            this.reader = reader;
        }


        public void run() {
            String message = "";
            while (true) {
                try {
                    message = reader.readLine();
                    String num[]=message.split("%");
                    //分别对界面做修改
                    switch (Integer.parseInt(num[0])){
                        case 0:
                            //0:成功连接服务器
                            view.func_1(num[1]);
                            view.func_2("系统信息",num[3]);
                            break;
                        case 1:
                            //1：给客户端反馈目前的在线人员信息
                            view.func_1(num[1]);
                            break;
                        case 2:
                            //2:给所有客户端发送新用户的上线信息
                            view.func_2("系统信息",num[1]+"上线啦");
                            break;
                        case 3:
                            //3:服务器转发单人发送的信息
                        case 4:
                            //4：服务器转发群发的信息
                        case 5:
                            //5:服务器进行群发信息
                            view.func_2(num[1],num[3]);
                        default:
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}