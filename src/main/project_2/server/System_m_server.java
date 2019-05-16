package main.project_2.server;

import main.project_2.User;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

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

    // 执行消息发送
    public void send() {
        if (!isStart) {
            JOptionPane.showMessageDialog(frame, "服务器还未启动,不能发送消息！", "错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (clients.size() == 0) {
            JOptionPane.showMessageDialog(frame, "没有用户在线,不能发送消息！", "错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String message = txt_message.getText().trim();
        if (message == null || message.equals("")) {
            JOptionPane.showMessageDialog(frame, "消息不能为空！", "错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        sendServerMessage(message);// 群发服务器消息
        contentArea.append("服务器说：" + txt_message.getText() + "\r\n");
        txt_message.setText(null);
    }

    // 启动服务器
    public void serverStart() throws java.net.BindException {
        try {
            clients = new ArrayList<ClientThread>();
            serverSocket = new ServerSocket(1234);
            serverThread = new ServerThread(serverSocket, 23);
            serverThread.start();
        }catch (Exception e1) {
            e1.printStackTrace();
            throw new BindException("启动服务器异常！");
        }
    }

    // 服务器线程
    class ServerThread extends Thread {
        private ServerSocket serverSocket;

        // 服务器线程的构造方法
        public ServerThread(ServerSocket serverSocket, int max) {
            this.serverSocket = serverSocket;
        }

        public void run() {
            while (true) {// 不停的等待客户端的链接
                try {
                    Socket socket = serverSocket.accept();
                    ClientThread client = new ClientThread(socket);
                    client.start();// 开启对此客户端服务的线程
                    clients.add(client);
                    User user=new User();
                    //往user中添加数据
                    System_v_server.view.func(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 为一个客户端服务的线程
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

        // 客户端线程的构造方法
        public ClientThread(Socket socket) {
            try {
                this.socket = socket;
                reader = new BufferedReader(new InputStreamReader(socket
                        .getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());
                // 接收客户端的基本用户信息
                String inf = reader.readLine();
                StringTokenizer st = new StringTokenizer(inf, "@");
                user = new User(st.nextToken(), st.nextToken());
                // 反馈连接成功信息
                writer.println(user.getName() + user.getIp() + "与服务器连接成功!");
                writer.flush();
                // 反馈当前在线用户信息
                if (clients.size() > 0) {
                    String temp = "";
                    for (int i = clients.size() - 1; i >= 0; i--) {
                        temp += (clients.get(i).getUser().getName() + "/" + clients
                                .get(i).getUser().getIp())
                                + "@";
                    }
                    writer.println("USERLIST@" + clients.size() + "@" + temp);
                    writer.flush();
                }
                // 向所有在线用户发送该用户上线命令
                for (int i = clients.size() - 1; i >= 0; i--) {
                    clients.get(i).getWriter().println(
                            "ADD@" + user.getName() + user.getIp());
                    clients.get(i).getWriter().flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        public void run() {// 不断接收客户端的消息，进行处理。
            String message = null;
            while (true) {
                try {
                    message = reader.readLine();// 接收客户端消息
                    if (message.equals("CLOSE"))// 下线命令
                    {
                        contentArea.append(this.getUser().getName()
                                + this.getUser().getIp() + "下线!\r\n");
                        // 断开连接释放资源
                        reader.close();
                        writer.close();
                        socket.close();

                        // 向所有在线用户发送该用户的下线命令
                        for (int i = clients.size() - 1; i >= 0; i--) {
                            clients.get(i).getWriter().println(
                                    "DELETE@" + user.getName());
                            clients.get(i).getWriter().flush();
                        }

                        listModel.removeElement(user.getName());// 更新在线列表

                        // 删除此条客户端服务线程
                        for (int i = clients.size() - 1; i >= 0; i--) {
                            if (clients.get(i).getUser() == user) {
                                ClientThread temp = clients.get(i);
                                clients.remove(i);// 删除此用户的服务线程
                                temp.stop();// 停止这条服务线程
                                return;
                            }
                        }
                    } else {
                        dispatcherMessage(message);// 转发消息
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 转发消息
        public void dispatcherMessage(String message) {
            StringTokenizer stringTokenizer = new StringTokenizer(message, "@");
            String source = stringTokenizer.nextToken();
            String owner = stringTokenizer.nextToken();
            String content = stringTokenizer.nextToken();
            message = source + "说：" + content;
            contentArea.append(message + "\r\n");
            if (owner.equals("ALL")) {// 群发
                for (int i = clients.size() - 1; i >= 0; i--) {
                    clients.get(i).getWriter().println(message + "(多人发送)");
                    clients.get(i).getWriter().flush();
                }
            }
        }
    }
}
