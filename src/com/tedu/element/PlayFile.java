package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

public class PlayFile extends ElementObj{
    private int attack = 1;
    private int moveNum = 4;
    private String fx;

    public PlayFile(){}

    public ElementObj createElement(String str){
        String[] split = str.split(",");
        ImageIcon icon1 = GameLoad.imgMap.get("file2");
        for(String str1:split){
            String[] split2 = str1.split(":");
            switch (split2[0]){
                case"x":this.setX(Integer.parseInt(split2[1]) - icon1.getIconWidth()/2 -5);break;
                case"y":this.setY(Integer.parseInt(split2[1]) - icon1.getIconHeight()/2);break;
                case"f":this.fx=split2[1];break;
            }
        }
        this.setW(icon1.getIconWidth());
        this.setH(icon1.getIconHeight());
        this.setIcon(icon1);
        return this;
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }

    @Override
    protected void move() {
        if(this.getX() < 0 || this.getY() > 1000 || this.getX() > 1000 || this.getY() < 0){
            this.setLive(false);
            return;
        }

        switch (this.fx){
            case"play1Up":this.setY(this.getY() - this.moveNum);break;
            case"play1Left":this.setX(this.getX() - this.moveNum);break;
            case"play1Right":this.setX(this.getX() + this.moveNum);break;
            case"play1Down":this.setY(this.getY() + this.moveNum);break;
        }
    }

//    public void die(){
//        ElementManager em = ElementManager.getManager();
//        ImageIcon icon1 = new ImageIcon("image/boom/boom.png");
//        ElementObj obj=new Play(this.getX(),this.getY(),50,50,icon1);//实例化对象
//        obj.setIcon(icon1);
////		讲对象放入到 元素管理器中
//        em.addElement(obj, GameElement.DIE);//直接添加
//    }

    private long time = 0;

//    @Override
//    protected void updateImage(long gameTime) {
//        if(gameTime- time > 5){
//            time = gameTime;
//            this.setW(getW() + 2);
//            this.setH(getH() + 2);
//        }
//    }
}
