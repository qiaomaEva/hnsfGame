package com.tedu.controller;

import java.util.List;
import java.util.Map;

import javax.swing.*;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.MapObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * @说明 游戏的主线程，用于控制游戏加载，游戏关卡，游戏运行时自动化
 * 		游戏判定；游戏地图切换 资源释放和重新读取。。。
 * @author renjj
 * @继承 使用继承的方式实现多线程(一般建议使用接口实现)
 */
public class GameThread extends Thread{
    private ElementManager em;

    public GameThread() {
        em=ElementManager.getManager();
    }
    @Override
    public void run() {//游戏的run方法  主线程
        while(true) { //扩展,可以讲true变为一个变量用于控制结束
//		游戏开始前   读进度条，加载游戏资源(场景资源)
            gameLoad();
//		游戏进行时   游戏过程中
            gameRun();
//		游戏场景结束  游戏资源回收(场景资源)
            gameOver();
            try {
                sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 游戏的加载
     */
    private static int mapID = 3;
    private static int score = 0;
    private void gameLoad() {
        GameLoad.loadImg();         //加载图片
        GameLoad.MapLoad(mapID); //加载地图
        GameLoad.loadPlay();        //加载主角
        GameLoad.loadEnemy(mapID);       //加载敌人
    }
    /**
     * @说明  游戏进行时
     * @任务说明  游戏过程中需要做的事情：1.自动化玩家的移动，碰撞，死亡
     *                                 2.新元素的增加(NPC死亡后出现道具)
     *                                 3.暂停等等。。。。。
     * 先实现主角的移动
     * */

    private void gameRun() {
        long gameTime = 0L;
        while(isRun(mapID, score)) {// 预留扩展   true可以变为变量，用于控制管关卡结束等
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            List<ElementObj> files = em.getElementsByKey(GameElement.PLAYFILE);
            List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> plays = em.getElementsByKey(GameElement.PLAY);
            score = maps.get(0).getScore();
            moveAndUpdate(all, gameTime);
            ElementPK(files, enemys);
            ElementPK(files, maps);
            MapPK(plays, maps);
            gameTime++;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
//            JOptionPane.showMessageDialog(null, "玩家得分总结报告：\n关卡ID: " + mapID + "\n得分: " + score);
            em.getElementsByKey(GameElement.PLAY).clear();
            em.getElementsByKey(GameElement.PLAYFILE).clear();
            em.getElementsByKey(GameElement.ENEMY).clear();
            sleep(5000);
            em.getElementsByKey(GameElement.MAPS).clear();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRun(int mapID, int score){
        switch (mapID){
            case 1: if(score == 5){
                return false;
            }
            case 2: if(score == 10){
                return false;
            }
        }
        return true;
    }

    public void ElementPK(List<ElementObj> listA, List<ElementObj> listB){
        for(int i=0; i<listA.size(); i++){
            ElementObj A=listA.get(i);
            for(int j = 0; j < listB.size(); j++){
                ElementObj B=listB.get(j);
                if(A.pk(B)){
                    A.setLive(false);
                    B.setLive(false);
                    break;
                }
            }
        }
    }

    public void MapPK(List<ElementObj> listA, List<ElementObj> listB) {
        for (ElementObj obj1 : listA) {
            if (obj1 instanceof Play) {
                Play player = (Play) obj1;  // 获取玩家类
                boolean isInGrass = false;  // 记录玩家是否在草丛中，默认为false
                for (ElementObj obj2 : listB) {
                    if (obj2 instanceof MapObj) {
                        MapObj mapObj = (MapObj) obj2;  // 获取地图元素
                        if (player.pk(mapObj)) {  // 如果玩家与地图元素碰撞
                            if ("GRASS".equals(mapObj.getName())) {
                                isInGrass = true;  // 玩家在草丛中
                            } else {
                                switch (player.getFx()) {
                                    case "play1Up":
                                        player.setY(player.getY() + 2);
                                        break;
                                    case "play1Left":
                                        player.setX(player.getX() + 2);
                                        break;
                                    case "play1Right":
                                        player.setX(player.getX() - 2);
                                        break;
                                    case "play1Down":
                                        player.setY(player.getY() - 2);
                                        break;
                                }
                            }
                        }
                    }
                }
                // 判断玩家是否在草丛中
                if (isInGrass) {
                    player.setInvisible(true);
                } else {
                    player.setInvisible(false);
                }
            }
        }
    }


    public void moveAndUpdate(Map<GameElement, List<ElementObj>> all, long gameTime){
        //			GameElement.values();//隐藏方法  返回值是一个数组,数组的顺序就是定义枚举的顺序
        for(GameElement ge:GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for(int i=list.size()-1;i>=0;i--) {
                ElementObj obj=list.get(i);//读取为基类
                if(!obj.isLive()){
                    list.remove(i);
                    obj.die();
                    continue;
                }
                obj.model(gameTime);//调用的模板方法 不是move
            }
        }
    }
    /**游戏切换关卡*/
    private void gameOver() {
        mapID++;
        score = 0;
        if(mapID <= 2) {
            gameLoad();
            gameRun();
            gameOver();
        }
        System.exit(0);
    }
}






