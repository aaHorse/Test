package main.project_1.inter;

import main.project_1.System_m;

import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: Test
 * @Description: java类作用描述
 * @Author: horse
 * @CreateDate: 2019/5/7 15:19
 * @UpdateUser: horse
 * @UpdateDate: 2019/5/7 15:19
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */

/*
* 访问网络的回调接口
* */
public interface Back {
    void success(List<System_m.Reader>lists);
    void error();
}
