package com.javarush.task.task35.task3505;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertableUtil implements Convertable<String> {

    public static<T extends Convertable> Map convert(List<T> list) {
        Map result = new HashMap();
        for(T items:list)
            result.put(items.getKey(),items);
        return result;
    }

    /*ключами являются объекты, полученные вызовом интерфейсного метода getKey*/
    @Override
    public String getKey() {
        return null;
    }
}
