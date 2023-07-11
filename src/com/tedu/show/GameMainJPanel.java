package com.tedu.show;

/*
    说明：游戏的主要面板
    功能说明：主要进行元素的显示和界面的刷新（多线程）
 */

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameMainJPanel extends JPanel implements Runnable {
    //联动管理器
    private ElementManager em;

    public GameMainJPanel(){
        init();
    }

    public void init(){
        em = ElementManager.getManager();   //得到元素管理器对象
    }

    @Override       //用于绘画
    public void paint(Graphics g) {
        super.paint(g);
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        for(GameElement ge:GameElement.values()){    //迭代器
            List<ElementObj> list = all.get(ge);
            for(int i = 0; i<list.size(); i++)
            {
                ElementObj obj = list.get(i);
                obj.showElement(g);
            }
        }
    }

    @Override
    public void run() { //接口实现
        while(true){
            this.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
