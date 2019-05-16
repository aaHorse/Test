package main.project_1.ui;

/**
 * java类简单作用描述
 *
 * @ProjectName: Test
 * @Description: java类作用描述
 * @Author: horse
 * @CreateDate: 2019/5/5 18:53
 * @UpdateUser: horse
 * @UpdateDate: 2019/5/5 18:53
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */

import main.project_1.System_m;
import main.project_1.System_p;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 借书页面
 * */
public class Borrow {
    /*
    * 全局读者
    * */
    public static System_m.Reader reader=new System_m.Reader();
    public Borrow() {
        //JTable jTable=new JTable();
        JF_Reader jf_reader=new JF_Reader();
    }
}

/*
* 获取读者信息
* */
class JF_Reader extends JFrame implements ActionListener{
    private static Container container;
    private JTextField jTextField_1=new JTextField("",10);
    private JTextField jTextField_2=new JTextField(10);
    private JTextField jTextField_3=new JTextField(10);
    private JTextField jTextField_4=new JTextField(10);
    private JTextField jTextField_5=new JTextField(10);

    public JF_Reader(){
        super("读者信息");
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container bg_container=getContentPane();
        bg_container.setLayout(new FlowLayout(FlowLayout.CENTER));

        container=new Container();
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

        bg_container.add(container);

        JTextField [] num={new JTextField("姓名", 10), jTextField_1,
                new JTextField("学号", 10), jTextField_2,
                new JTextField("学院", 10),jTextField_3,
                new JTextField("专业", 10), jTextField_4,
                new JTextField("年级", 10), jTextField_5};

        for(int i=0;i<10;i++){
            if(i%2==0){
                num[i].setEnabled(false);
            }
        }

        for(int i=0;i<10;i++){
            Container container_2=new Container();
            container_2.setLayout(new FlowLayout(FlowLayout.LEFT));
            container_2.add(num[i]);
            i++;
            container_2.add(num[i]);
            container.add(container_2);
        }

        JButton jButton=new JButton("确定");
        container.add(jButton);
        jButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("确定")){
            Borrow.reader.setNum(jTextField_2.getText());
            Borrow.reader.setName(jTextField_1.getText());
            Borrow.reader.setXueyuan(jTextField_3.getText());
            Borrow.reader.setMajor(jTextField_4.getText());
            Borrow.reader.setGrade(jTextField_5.getText());
            /*
            * 跳转到借书的表格显示
            * */
            new JTable();
        }
    }
}


/*
 * 借书时显示的表格
 * */
class JTable extends JFrame implements ActionListener{
    private static Container container;
    private static javax.swing.JTable table;
    /*
    * 全局的借阅信息
    * */
    public static List<System_m.Reader> lists=new ArrayList<>();

    public JTable(){
        super("借书");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        intiComponent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("确定")){
            lists.clear();
            for(int i=0;i<table.getRowCount();i++){
                if(table.getValueAt(i,3).toString().equals("true")){
                    System_m.Book book=new System_m.Book();
                    String name=table.getValueAt(i,0).toString();
                    String author=table.getValueAt(i,1).toString();
                    String publishing_name=table.getValueAt(i,2).toString();

                    book.setBook_name(name);
                    book.setBook_author(author);
                    book.setPublishing_name(publishing_name);

                    System_m.Reader reader=new System_m.Reader();
                    reader.setNum(Borrow.reader.getNum());
                    reader.setName(Borrow.reader.getName());
                    reader.setGrade(Borrow.reader.getGrade());
                    reader.setMajor(Borrow.reader.getMajor());
                    reader.setXueyuan(Borrow.reader.getXueyuan());
                    reader.setBook(book);
                    reader.setDate(getStringDate(new Date()));

                    lists.add(reader);
                }
            }
            /*
            * 通知，进数据库
            * */
            System_p.func_1(lists);
        }
    }

    public String getStringDate(Date now){
        SimpleDateFormat sdfd =new SimpleDateFormat("yyy年MM月dd日HH时mm分ss秒");
        String str=sdfd.format(now);
        return str;
    }

    public void intiComponent() {
        container=getContentPane();
        container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));
        add_button();
        table= new javax.swing.JTable(new MyTableModel());
        JScrollPane scroll = new JScrollPane(table);
        container.add(scroll);
    }

    private void add_button(){
        Container container_2=new Container();
        container_2.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton jButton=new JButton("确定");
        jButton.addActionListener(this);
        container_2.add(jButton);
        container.add(container_2);
    }

    private class MyTableModel extends AbstractTableModel {
        String[] columnNames =
                {"书名", "作者", "出版社", "我要借阅"};
        Object[][] data = new Object[100][4];
        public MyTableModel() {
            for (int i = 0; i < 100; i++) {
                data[i][0]="书"+i;
                data[i][1]="作者"+i;
                data[i][2]="出版社"+i;
                data[i][3]=new Boolean(false);
            }
        }


        /**
         * 得到列名
         */
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        /**
         * 重写方法，得到表格列数
         */
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        /**
         * 得到表格行数
         */
        @Override
        public int getRowCount() {
            return data.length;
        }

        /**
         * 得到数据所对应对象
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        /**
         * 得到指定列的数据类型
         */
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return data[0][columnIndex].getClass();
        }

        /**
         * 指定设置数据单元是否可编辑.这里设置"姓名","学号"不可编辑
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex < 3)
                return false;
            else
                return true;
        }

        /**
         * 如果数据单元为可编辑，则将编辑后的值替换原来的值
         */
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
            /*通知监听器数据单元数据已经改变*/
            fireTableCellUpdated(rowIndex, columnIndex);
        }

    }
}