package com.ruoyi.system.components;

import java.util.List;
import java.util.Random;

public class RandomListPicker {

    public static Integer pickRandom(List list){
        if (list == null || list.isEmpty()){
            return null;
        }
        Random random = new Random();
        int index =random.nextInt(list.size());
        return index;
    }

    public static Integer numRandom(){
        Random random = new Random();
        int index = random.nextInt(20);
        return index+10;
    }
}
