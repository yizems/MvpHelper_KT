package cn.yzl.mvp;

import java.io.File;
import java.net.URL;

/**
 * Created by YZL on 2017/9/11.
 */
public class A {
    public static void main(String[] args) {
//        MyWriter my = new MyWriter();
//        File parentFile = my.getParentFile();
        A a = new A();
        URL resource = a.getClass().getResource("");
        String file1 = resource.getFile();
        File file = new File("", "template");
        System.out.print(file.getAbsolutePath());
    }
}
