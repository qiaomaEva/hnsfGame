package com.tedu.manager;

import com.tedu.element.ElementObj;
import com.tedu.element.MapObj;
import com.tedu.element.Play;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.swing.ImageIcon;

/**
 * @说明  假的加载器
 * @author renjj
 *
 */
public class GameLoad {
    private static final ElementManager em = ElementManager.getManager();
    //	图片集合  使用map来进行存储     枚举类型配合移动(扩展)
    public static Map<String,ImageIcon> imgMap = new HashMap<>();
    public static Map<String, List<ImageIcon>> imgMaps;
    private static Properties pro = new Properties();

    public static void MapLoad(int mapId){
        String mapName = "com/tedu/text/" + mapId + ".map";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        if(maps == null){
            System.out.println("配置文件读取异常，请重新安装。");
            return;
        }
        pro.clear();
        try {
            pro.load(maps);
            Enumeration<?> names = pro.propertyNames();
            while(names.hasMoreElements()){
                String key = names.nextElement().toString();
//                System.out.println(pro.getProperty(key));
                String[] arrs = pro.getProperty(key).split(";");
                for(int i = 0; i<arrs.length; i++) {
                    ElementObj element = new MapObj().createElement(mapId + "," + key + "," + arrs[i]);
                    em.addElement(element, GameElement.MAPS);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadImg(){
        String texturl="com/tedu/text/GameData.pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set){
               String url = pro.getProperty(o.toString());
               imgMap.put(o.toString(), new ImageIcon(url));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadPlay(){
        loadObj();
        String playStr = "430,540,play1Up";
        ElementObj obj = getObj("play");
        assert obj != null;
        ElementObj play = obj.createElement(playStr);
        em.addElement(play, GameElement.PLAY);
    }

    public static void loadEnemy(int mapID){
        loadObj();
        String enemyStr = "";
        switch (mapID){
            case 1: enemyStr = "1;200,300,enemyRight;600,300,enemyLeft;400,400,enemyRight;200,100,enemyLeft;500,50,enemyRight";break;
            case 2: enemyStr = "2;200,300,enemyRight;600,300,enemyLeft;400,400,enemyRight;200,100,enemyLeft;500,50,enemyRight;" +
                    "100,200,enemyUp;400,300,enemyDown;500,100,enemyUp;300,400,enemyDown;175,369,enemyUp";break;
        }
        String[] arrs = enemyStr.split(";");
        for(int i=1; i<arrs.length; i++){
            ElementObj obj = getObj("enemy");
            ElementObj enemy = obj.createElement(arrs[0] + "," + (arrs.length - 1) + "," + arrs[i]);
            em.addElement(enemy, GameElement.ENEMY);
        }
    }

    public static ElementObj getObj(String str){
        try {
            Class<?> class1 = objMap.get(str);
            Object newInstance = class1.newInstance();
            if(newInstance instanceof ElementObj){
                return (ElementObj)newInstance;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static final Map<String, Class<?>> objMap = new HashMap<>();

    public static void loadObj(){
        String texturl="com/tedu/text/obj.pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);

        try {
            pro.clear();
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set){
                String classUrl = pro.getProperty(o.toString());
                Class<?> forName = Class.forName(classUrl);
                objMap.put(o.toString(), forName);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
