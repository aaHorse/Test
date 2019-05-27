package main.project_2.server;

import main.project_2.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
public class System_m_server {
    private ServerSocket serverSocket;
    private ServerThread serverThread;
    private ArrayList<ClientThread> clients;
    private System_v_server.View view;

    public void serverStart(System_v_server.View view){
        try {
            this.view=view;
            clients = new ArrayList<ClientThread>();
            serverSocket = new ServerSocket(1234);
            serverThread = new ServerThread(serverSocket, 23);
            serverThread.start();
            view.func_2("系统信息","服务器成功启动");
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public boolean send(String message) {
        if (clients.size() == 0) {
            return false;
        }
        for (int i = clients.size() - 1; i >= 0; i--) {
            clients.get(i).getWriter().println(pack(5,"服务器","服务器",message));
            clients.get(i).getWriter().flush();
        }
        return true;
    }


    class ServerThread extends Thread {
        private ServerSocket serverSocket;
        public ServerThread(ServerSocket serverSocket, int max) {
            this.serverSocket = serverSocket;
        }

        public void run() {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ClientThread client = new ClientThread(socket);
                    client.start();
                    clients.add(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    class ClientThread extends Thread {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;
        private User user;

        public BufferedReader getReader() {
            return reader;
        }

        public PrintWriter getWriter() {
            return writer;
        }

        public User getUser() {
            return user;
        }

        public ClientThread(Socket socket) {
            try {
                this.socket = socket;
                reader = new BufferedReader(new InputStreamReader(socket
                        .getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());
                String inf = reader.readLine();
                String [] num=inf.split("%");
                user = new User();
                user.setName(num[1]);
                user.setIp(num[2]);
                writer.println(pack(0,user.getName(),user.getIp(),"成功连接服务器"));
                writer.flush();
                view.func_1(user.getName());
                view.func_2("系统信息",user.getName()+"成功连接服务器");
                if (clients.size() > 0) {
                    String temp = "";
                    for (int i = clients.size() - 1; i >0; i--) {
                        temp+=pack(1,clients.get(i).getUser().getName(),clients.get(i).getUser().getIp(),"")+"%";
                    }
                    /*
                    * 分出来写是为了避免最后面多出一个 %
                    * */
                    temp+=pack(1,clients.get(0).getUser().getName(),clients.get(0).getUser().getIp(),"");
                    writer.println(temp);
                    writer.flush();
                }
                for (int i = clients.size() - 1; i >= 0; i--) {
                    clients.get(i).getWriter().println(pack(2,clients.get(i).getUser().getName(),clients.get(i).getUser().getIp(),",上线"));
                    clients.get(i).getWriter().flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            String message = null;
            while (true) {
                try {
                    message = reader.readLine();
                    String num[]=message.split("%");
                    /*
                    * 还没区分单人发送还是群发的 3,4
                    * */
                    view.func_2(num[1],num[3]);
                    for (int i = clients.size() - 1; i >= 0; i--) {
                        clients.get(i).getWriter().println(pack(4,num[1],num[2],num[3]));
                        clients.get(i).getWriter().flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
    *  0:成功连接服务器  ，服务器to客户端
    *  1：给客户端反馈目前的在线人员信息  ，服务器to客户端
    *  2:给所有客户端发送新用户的上线信息 ， 服务器to客户端
    *  3:服务器转发单人发送的信息 ， 服务器to客户端
    *  4：服务器转发群发的信息 ， 服务器to客户端
    *  5:服务器进行群发信息 ， 服务器to客户端
    *
    *  6:客户端给服务器发信息 ， 客户端to服务器
    *  7:客户端给服务器发送连接的基本信息 , 客户端to服务器
    * */
    private String pack(int code,String name,String ip,String content){
        return code+"%"+name+"%"+ip+"%"+content;
    }
}
