package com.tedu.element;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj{
    private int hp = 1;
    private static int mapID = 0;

    private String scoreSummary = "";

    private String name="";


    public int getMapID() {
        return mapID;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void showElement(Graphics g) {
        if(isShow(mapID, score)) {
            g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
            Font font = new Font("微软雅黑", Font.BOLD, 16);
            g.setFont(font);
            g.setColor(Color.RED);
            g.drawString("地图：" + this.getMapID(), 10, 20);
            g.drawString("得分：" + this.getScore(), 10, 40);
        }
        else {// 绘制得分总结报告
            g.setColor(Color.BLACK);
            Font summaryFont = new Font("微软雅黑", Font.BOLD, 36);
            g.setFont(summaryFont);
            if(this.getScore() == 10){
                g.drawString("恭喜您已经通关！", 245, 150);
            }
            g.drawString("玩家得分总结报告：", 230, 200);
            Font infoFont = new Font("微软雅黑", Font.PLAIN, 26);
            g.setFont(infoFont);
            g.drawString("关卡: " + mapID, 330, 270);
            g.drawString("得分: " + this.getScore(), 330, 300);

        }
    }

    public boolean isShow(int mapID, int score){
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

    @Override
    public ElementObj createElement(String str) {
        String []arr = str.split(",");
        ImageIcon icon = null;
        this.setMapID(new Integer(arr[0]));
        switch (arr[1]){
            case "GRASS":icon = new ImageIcon("image/wall/grass.png");
                name="GRASS";
                break;
            case "BRICK":icon = new ImageIcon("image/wall/brick.png");break;
            case "RIVER":icon = new ImageIcon("image/wall/river.png");break;
            case "IRON":icon = new ImageIcon("image/wall/iron.png");
                this.hp=4;
                name="IRON";
                break;
        }
        this.setX(Integer.parseInt(arr[2]));
        this.setY(Integer.parseInt(arr[3]));
        if (icon != null) {
            this.setW(icon.getIconWidth());
        }
        if (icon != null) {
            this.setH(icon.getIconHeight());
        }
        this.setIcon(icon);
        return this;
    }

    // 辅助方法：获取地图上的"GRASS"元素


    @Override
    public void setLive(boolean live) {
        if("IRON".equals(name)){
            this.hp--;
            if(this.hp > 0){
                return;
            }
        }
        super.setLive(live);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
