package com.tedu.show;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/*
    游戏窗体，主要实现的功能：关闭，显示，最大最小化
    功能说明：需要嵌入面板，启动主线程等
    窗体说明：swing awt 注意：窗体大小（记录用户上次使用软件的窗体样式），

    分析：1.面板绑定到窗体
         2.监听绑定
         3.游戏主线程启动
         4.显示窗体
 */
public class GameJFrame extends JFrame {
    public static int GameX = 778;
    public static int GameY = 627;
    private JPanel jPanel = null;    //正在实现的面板
    private KeyListener keyListener = null;  //键盘的监听
    private Thread thread = null;    //游戏主线程
    private MouseMotionListener mouseMotionListener = null;  //鼠标监听
    private MouseListener mouseListener = null;  //鼠标监听

    public GameJFrame(){
        init();
    }
    public void init(){
        this.setSize(GameX, GameY);
        this.setTitle("测试游戏-泡泡堂");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置退出并且关闭
        this.setLocationRelativeTo(null);
    }


    //窗体布局：可以将存档、读档等button在这里进行布局。
    public void addButton(){
//        this.setLayout(manager);  布局格式，可以添加控件
    }


    //启动方法：
    public void start(){
        if(jPanel != null){
            this.add(jPanel);
        }
        if(keyListener != null){
            this.addKeyListener(keyListener);
        }
        if(thread != null){
            thread.start();   //启动线程
        }
        //界面的刷新
        this.setVisible(true);  //显示界面
        if(this.jPanel instanceof Runnable){    //如果jp是runnable的子类实体对象
            new Thread((Runnable)this.jPanel).start();
        }
    }

    //set注入：学习ssm，通过set方法注入配置文件中读取的数据，
    //将配置文件中的数据赋值为类的属性。
    //构造注入:需要配合构造方法
    //这两种方法源于spring中ioc进行对象的自动生成，管理。
    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }
}
