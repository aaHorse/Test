package main.project_1.ui;

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

/**
 * java类简单作用描述
 *
 * @ProjectName: Test
 * @Description: java类作用描述
 * @Author: horse
 * @CreateDate: 2019/5/5 18:56
 * @UpdateUser: horse
 * @UpdateDate: 2019/5/5 18:56
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
public class Search_All extends JFrame implements ActionListener {
    private static Container bg_container;
    private static JTextField jTextField_2;

    public Search_All() {
        super("读者借阅信息");
        this.setBounds(600, 150, 500, 500);
        this.setVisible(true);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bg_container = getContentPane();
        bg_container.setLayout(new FlowLayout(FlowLayout.CENTER));
        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        JTextField jTextField = new JTextField("学号 :");
        jTextField_2 = new JTextField(10);
        jTextField.setEnabled(false);
        jTextField_2.setEnabled(true);
        container.add(jTextField);
        container.add(jTextField_2);
        JButton jButton = new JButton("确定");
        jButton.addActionListener(this);
        container.add(jButton);
        bg_container.add(container);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("确定")) {
            System_p.func_2(jTextField_2.getText(),"Search_All");
        }
    }

    public static void update(List<System_m.Reader> lists) {
        new JTable_2(lists);
    }
}


/*
 * 借阅信息显示的表格
 * */
class JTable_2 extends JFrame{
    private static Container container;
    private static javax.swing.JTable table;
    private static List<System_m.Reader> reader_lists;
    /*
     * 全局的借阅信息
     * */
    public static List<System_m.Reader> lists=new ArrayList<>();

    public JTable_2(List<System_m.Reader> mylists){
        super("借阅信息");
        this.setBounds(600, 150, 500, 500);
        this.setVisible(true);
        this.reader_lists=mylists;
        System.out.println("长度为"+lists.size());
        intiComponent();
    }


    public void intiComponent() {
        container=getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        table= new javax.swing.JTable(new MyTableModel());
        JScrollPane scroll = new JScrollPane(table);

        this.add(scroll);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }


    private class MyTableModel extends AbstractTableModel {
        //(num,name,xueyuan,major,grade,date,book_name,book_author,publishing_name)
        String[] columnNames =
                {"学号","姓名","学院","专业","年级","借书时间","书名","作者","出版社"};
        int length=reader_lists.size();
        Object[][] data = new Object[length][9];
        public MyTableModel() {
            for (int i = 0; i < length; i++) {
                System_m.Reader reader=reader_lists.get(i);
                data[i][0]=reader.getNum();
                data[i][1]=reader.getName();
                data[i][2]=reader.getXueyuan();
                data[i][3]=reader.getMajor();
                data[i][4]=reader.getGrade();
                data[i][5]=reader.getDate();
                data[i][6]=reader.getBook().getBook_name();
                data[i][7]=reader.getBook().getBook_author();
                data[i][8]=reader.getBook().getPublishing_name();
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
         * 指定设置数据单元是否可编辑
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex < 9)
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
