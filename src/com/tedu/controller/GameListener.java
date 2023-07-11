package com.tedu.controller;

/*
    监听类，用于监听用户的操作 KeyListener
 */

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameListener implements KeyListener {
    private ElementManager em = ElementManager.getManager();

    private Set<Integer> set = new HashSet<Integer>();
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {    //按下：左上右下：37 38 39 40
//        System.out.println("KeyPressed"+e.getKeyCode());
        int key = e.getKeyCode();
        if(set.contains(key)){  //判定集合中是否存在该对象，若包含这个对象就直接结束方法
            return;
        }
        set.add(key);
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);  //拿到玩家集合
        for(ElementObj obj:play){
            obj.keyClick(true, e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {   //松开
//        System.out.println("keyReleased"+e.getKeyCode());
        if(!set.contains(e.getKeyCode())){
            return;
        }
        set.remove(e.getKeyCode());     //移除数据
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);  //拿到玩家集合
        for(ElementObj obj:play){
            obj.keyClick(false, e.getKeyCode());
        }
    }
}
