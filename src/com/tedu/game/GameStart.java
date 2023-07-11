package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

/*
    程序的唯一入口
 */
public class GameStart {
    public static void main(String[] args) {
        GameJFrame gj = new GameJFrame();
        //实例化面板，注入到jframe中
        GameMainJPanel jp = new GameMainJPanel();
        GameListener listener = new GameListener(); //实例化监听
        GameThread th = new GameThread();   //实例化主线程
        gj.setjPanel(jp);   //注入
        gj.setKeyListener(listener);    //注入
        gj.setThread(th);       //注入
        gj.start();    //显示窗体
    }
}
