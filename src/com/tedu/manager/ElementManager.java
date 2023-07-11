package com.tedu.manager;

//本类是元素管理器，专门存储所有的元素，同时提供方法
//给予视图和控制获取数据

import com.tedu.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementManager {
    /*
    string 作为key 匹配所有的元素   play ->List<object> listPlay
                                enemy ->List<Object> listEnemy
    枚举类型，当做map的key用水区分不一样的资源，用于获取资源
     */
    private Map<GameElement, List<ElementObj>> gameElements;

    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }
    //添加元素（多半由加载器调用）
    public void addElement(ElementObj obj, GameElement ge){
//        List<ElementObj> list = gameElements.get(ge);
//        list.add(obj);
        gameElements.get(ge).add(obj);  //添加对象到集合中，将key值进行存储
    }
    //依据key返回list集合，取出某一类元素
    public List<ElementObj> getElementsByKey(GameElement ge){
        return gameElements.get(ge);
    }

    /*
        单例模式：内存中有且只有一个实例
        饿汉模式-启动就自动加载实例
        饱汉模式-是需要使用时才加载实例

        编写方式：
        1.需要一个静态的属性（定义一个常量） 单例的引用
        2.提供一个静态的方法（返回这个实例） return单例的引用
        3.一般为防止其他人自己使用（类是可以实例化），所以会私有化构造方法
     */
    private static ElementManager EM=null;   //引用
    //synchronized线程锁-》保证本方法执行中只有一个线程
    public static synchronized ElementManager getManager(){
        if(EM == null){     //空值判定
            EM=new ElementManager();
        }
        return EM;
    }
    private ElementManager(){   //私有化构造方法
        init();
    }

    public void init(){         //实例化在这里完成
        //hasMap hash散列
        gameElements=new HashMap<GameElement, List<ElementObj>>();
        //将每种元素集合都放入map中
//        gameElements.put(GameElement.PLAY, new ArrayList<ElementObj>());
//        gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
//        gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
//        gameElements.put(GameElement.BOSS, new ArrayList<ElementObj>());
        for(GameElement ge:GameElement.values()){
            gameElements.put(ge, new ArrayList<ElementObj>());
        }
    }
}
