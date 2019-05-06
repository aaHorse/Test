package main.project_1;

import java.util.Date;

/**
 * java类简单作用描述
 *
 * @ProjectName: Test
 * @Description: java类作用描述
 * @Author: horse
 * @CreateDate: 2019/5/5 1:11
 * @UpdateUser: horse
 * @UpdateDate: 2019/5/5 1:11
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
public class System_m {
    public static class Book{
        private String book_name;
        private String book_author;
        private String publishing_name;
        ///////////////////////////////////////////////////////////////////
        public String getBook_name() {
            return book_name;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public String getBook_author() {
            return book_author;
        }

        public void setBook_author(String book_author) {
            this.book_author = book_author;
        }

        public String getPublishing_name() {
            return publishing_name;
        }

        public void setPublishing_name(String publishing_name) {
            this.publishing_name = publishing_name;
        }
    }

    public static class Reader{
        private String num;
        private String name;
        //学院
        private String xueyuan;
        //专业
        private String major;
        //年级
        private String grade;
        //借阅时间
        private String date;
        //借阅的课本
        private Book book;
        ///////////////////////////////////////////////////////////////////
        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getXueyuan() {
            return xueyuan;
        }

        public void setXueyuan(String xueyuan) {
            this.xueyuan = xueyuan;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }
    }
}

