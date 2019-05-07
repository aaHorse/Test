package main.project_1;

import main.project_1.inter.Back;
import main.project_1.ui.Return;
import main.project_1.ui.Search_All;

import java.awt.*;
import java.util.List;

/**
 * java类简单作用描述
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
public class System_p {

    /*
    * 将借阅信息写入数据库
    * */
    public static void func_1(List<System_m.Reader>lists){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<lists.size();i++){
                    DBUtils.func_1(lists.get(i));
                }
            }
        }).start();
    }

    /*
    * 查询读者的借阅信息
    * */
    public static void func_2(String num,String flag){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBUtils.func_2(num, new Back() {
                    @Override
                    public void success(List<System_m.Reader>lists) {
                        try{
                            EventQueue.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    /*
                                     * 回调ui线程，更新界面
                                     * */
                                    if(flag.equals("Search_All")){
                                        Search_All.update(lists);
                                    }else if(flag.equals("Return")){
                                        Return.update(lists);
                                    }else {
                                        System.out.println("System_p出错啦");
                                    }

                                }
                            });
                        }catch (InternalError e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error() {
                        System.out.println("查询读者借阅信息出错啦");
                    }
                });
            }
        }).start();
    }

    /*
    * 归还图书
    * */
    public static void func_3(List<System_m.Reader>lists){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<lists.size();i++){
                    DBUtils.func_3(lists.get(i));
                }
            }
        }).start();
    }
}
