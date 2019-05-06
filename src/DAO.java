import java.io.*;
import java.util.*;
public class DAO {
    //通过学生姓名找到学生学号
    private String findSnumBySname(String Sname)
    {
        String result = "";
        try
        {
            //相对路径创建文件字符输入流：..\\..\\名字
            FileReader file = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\Student.txt");
            //创建缓冲的字符输入流对象
            BufferedReader brea = new BufferedReader(file);
            //读取第一行数据
            String str = brea.readLine();
            while(str!=null)
            {
                //用逗号将一行数据切割为字符串数组
                String[] strs = str.split(",");
                if(strs[1].equals(Sname))
                {
                    result = strs[0];
                    break;
                }
                //未找到读取下一行数据
                str = brea.readLine();
            }
            file.close();
        }catch(Exception ex)
        {
            System.out.println("未找到");
        }
        return result;
    }

    //通过学生学号找到课程号,返回值为多个，用ArrayList(容器)
    private ArrayList<String> findCnumsBySnum(String Snum)
    {
        ArrayList<String> result = new ArrayList<String>();
        try
        {
            FileReader file1 = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\SC.txt");
            BufferedReader bre = new BufferedReader(file1);
            String tmp = bre.readLine();
            while(tmp != null)
            {
                String[] strs = tmp.split(",");
                if(strs[0].equals(Snum))
                {
                    result.add(strs[1]);
                }
                tmp = bre.readLine();
            }
            file1.close();
        }
        catch(Exception ex)
        {
            System.out.print("");
        }
        return result;
    }
    //通过课程号找学号
    private ArrayList<String> findSnumByCnum(String cnum)
    {
        ArrayList<String> result = new ArrayList<String>();
        try
        {
            FileReader file = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\SC.txt");
            BufferedReader rea = new BufferedReader(file);
            String str = rea.readLine();
            while(str!=null)
            {
                String[] tmp = str.split(",");
                if(tmp[1].equals(cnum))
                {
                    result.add(tmp[0]);
                }
                str = rea.readLine();
            }
            file.close();
        }catch(Exception ex)
        {
            System.out.println("");
        }
        return result;
    }
    //通过学号找姓名
    private String findSnameBySnum(String Snum)
    {
        String result = "";
        try
        {
            FileReader file = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\Student.txt");
            BufferedReader bre = new BufferedReader(file);
            String str = bre.readLine();
            while(str!=null)
            {
                String[] tmp = str.split(",");
                if(tmp[0].equals(Snum))
                {
                    result = tmp[1];
                }
                str = bre.readLine();
            }
            file.close();
        }
        catch(Exception ex)
        {
            System.out.println("");
        }
        return result;
    }
    //通过课程号找到课程名称
    private ArrayList<String> findCnamesByCnum(String Cnum)
    {
        ArrayList<String> list=new ArrayList<String>();
        try
        {
            FileReader  file2 = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\Course.txt");
            BufferedReader brea2 = new BufferedReader(file2);
            String tmp = brea2.readLine();
            while(tmp!=null)
            {
                String[] strs = tmp.split(",");
                for(int i = 0;i<list.size();i++)
                {
                    if(strs[0].equals(Cnum))//?????
                    {
                        list.add(strs[1]);
                        //break;
                    }
                }
                tmp = brea2.readLine();
            }
            file2.close();
        }
        catch(Exception ex)
        {
            System.out.println("");
        }
        return list;
    }
    //通过课程号找课程名
    private String findCnameByCnum(String cno)
    {
        String result = "";
        try
        {
            FileReader  file2 = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\Course.txt");
            BufferedReader brea2 = new BufferedReader(file2);
            String tmp = brea2.readLine();
            while(tmp!=null)
            {
                String[] strs = tmp.split(",");
                if(strs[0].equals(cno))
                {
                    result = strs[1];
                    break;
                }
                tmp = brea2.readLine();
            }
            file2.close();
        }
        catch(Exception ex)
        {
            System.out.println("");
        }
        return result;
    }
    //通过学生姓名找到课程名称
    public ArrayList<String> findCnamesBySname(String sname)
    {
        //找学号
        String snum = findSnumBySname(sname);
        //找课程号
        ArrayList<String> cnums = findCnumsBySnum(snum);
        if(cnums.size()!=0)
        {
            ArrayList<String> list = new ArrayList<String>();
            for(int i = 0;i<cnums.size();i++)
            {
                list.add(findCnameByCnum(cnums.get(i)));
            }
            return list;
        }
        else
        {
            return null;
        }
    }
    //通过课程名找课程号
    private String findCnumByCname(String Cname)
    {
        String result = "";
        //ArrayList<String> list=new ArrayList<String>();
        try
        {
            FileReader  file2 = new FileReader("d:\\Workspaces\\MyEclipse 8.5\\Course.txt");
            BufferedReader brea2 = new BufferedReader(file2);
            String tmp = brea2.readLine();
            while(tmp!=null)
            {
                String[] strs = tmp.split(",");
                if(strs[1].equals(Cname))
                {
                    result = strs[0];
                    break;
                }
                tmp = brea2.readLine();
            }
            file2.close();
        }
        catch(Exception ex)
        {
            System.out.println("");
        }
        return result;
    }
    //通过课程名找到该门课的所有成绩
    private ArrayList<String> findGradeByCname(String Cname)
    {
        ArrayList<String> result = new ArrayList<String>();
        String cnum = findCnumByCname(Cname);
        try
        {
            FileReader  file = new FileReader("d:\\Workspaces\\MyEclipse 8.5\\SC.txt");
            BufferedReader brea = new BufferedReader(file);
            String str = brea.readLine();
            while(str!=null)
            {
                String[] tmp = str.split(",");
                if(tmp[1].equals(cnum))
                {
                    result.add(tmp[2]);
                }
                str = brea.readLine();
            }
            file.close();
        }catch(Exception ex)
        {
            System.out.println("");
        }
        return result;
    }
    //通过课程名找到选修该课程的学生
    public ArrayList<String> findSnameByCname(String Cname)
    {
        ArrayList<String> result = new ArrayList<String>();
        String cnum = findCnumByCname(Cname);
        ArrayList<String> Snum = findSnumByCnum(cnum);
        try
        {
            FileReader file4 = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\Student.txt");
            BufferedReader bre = new BufferedReader(file4);
            String str = bre.readLine();
            while(str!=null)
            {
                String[] tmp = str.split(",");
                for(int i = 0;i<Snum.size();i++)
                {
                    if(tmp[0].equals(Snum.get(i)))
                    {
                        result.add(tmp[1]);
                    }
                }
                str = bre.readLine();
            }
            file4.close();
        }catch(Exception ex)
        {
            System.out.println("未找到");
        }
        return result;
    }
    //根据所给课程名返回该课程的平均成绩
    public float AverageGrade(String Cname)
    {
        float result = -1.0f;
        ArrayList<String> list = findGradeByCname(Cname);
        for(int i = 0;i<list.size();i++)
        {
            result+=Integer.parseInt(list.get(i));
        }
        result = result/(float)(list.size());
        return result;
    }
    //通过学生姓名和课程名找到成绩
    public int findGradeBySnameCname(String sname,String cname)
    {
        int result =-1;
        String snum = findSnumBySname(sname);
        String cnum = findCnumByCname(cname);
        try
        {
            FileReader file3 = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\SC.txt");

            BufferedReader bre = new BufferedReader(file3);
            String str = bre.readLine();
            while(str!=null)
            {
                String[] tmp = str.split(",");
                if((tmp[0].equals(snum))&&(tmp[1].equals(cnum)))
                {
                    result =Integer.parseInt(tmp[2]);
                }
                str = bre.readLine();
            }
            file3.close();
        }
        catch(Exception ex)
        {
            System.out.println("");
        }
        return result;
    }
}
