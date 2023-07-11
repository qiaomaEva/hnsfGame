package com.tedu.element;

import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj{

    private boolean enemyLeft=false; //左
    private boolean enemyUp=false;   //上
    private boolean enemyRight=false;//右
    private boolean enemyDown=false; //下
    private boolean live;
    private String fx="enemyLeft";
    private int x = 0;
    private int y = 0;
    private int num = 0;
    private int mapID;

    @Override
    public int getScore() {
        return score;
    }


    public void setFx(String fx) {
        this.fx = fx;
    }

    //	变量专门用来记录当前主角面向的方向,默认为是up


    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        mapID = new Integer(split[0]);
        num = new Integer(split[1]);
        if(score == 5){
            score = 0;
        }
        x = new Integer(split[2]);
        y = new Integer(split[3]);
        this.setX(new Integer(split[2]));
        this.setY(new Integer(split[3]));
        this.setFx(split[4]);
        ImageIcon icon2 = GameLoad.imgMap.get(split[4]);
        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());
        this.setIcon(icon2);
        return this;
    }

    protected void updateImage(long gameTime) {
        this.setIcon(GameLoad.imgMap.get(fx));
    }

    public void move(){
            switch (fx){
                case "enemyUp":
                    this.setY(this.getY() - 2);
                    if(y - this.getY() >= 100){
                        this.enemyUp = false;
                        this.enemyDown = true;
                        this.fx = "enemyDown";
                    }
                    break;
                case "enemyDown":
                    this.setY(this.getY() + 2);
                    if(this.getY() - y >= 100){
                        this.enemyDown = false;
                        this.enemyUp = true;
                        this.fx = "enemyUp";
                    }
                    break;
                case "enemyRight":
                    this.setX(this.getX() + 2);
                    if(this.getX() - x >= 100){
                        this.enemyRight = false;
                        this.enemyLeft = true;
                        this.fx = "enemyLeft";
                    }
                    break;
                case "enemyLeft":
                    this.setX(this.getX() - 2);
                    if(x - this.getX() >= 100){
                        this.enemyLeft = false;
                        this.enemyRight = true;
                        this.fx = "enemyRight";
                    }
                    break;
            }
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getH(),this.getW(), null);
        Font font = new Font("微软雅黑", Font.BOLD, 16);
        g.setFont(font);
        g.setColor(Color.RED);
    }

    @Override
    public void setLive(boolean live) {
        ImageIcon icon2 = GameLoad.imgMap.get("boom");
        this.setIcon(icon2);
        if(score < num) {
            score++;
        }
        else
            score++;
        super.setLive(live);
    }

//    @Override
//    public void die() {
//        super.die();
//        ImageIcon icon2 = GameLoad.imgMap.get("boom");
//        this.setIcon(icon2);
//        if(score < num) {
//            score++;
//        }
//        else
//            score++;
//    }
}
