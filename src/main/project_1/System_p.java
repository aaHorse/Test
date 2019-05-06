package main.project_1;

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

    public static void main(String[]args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBUtils.func_2();
            }
        }).start();
    }
}
