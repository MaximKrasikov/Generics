package com.javarush.task.task35.task3512;

public class Generator<T> {
     Class <T> newId;
    public Generator(Class<T> newId){
        this.newId=  newId;
    }
    T newInstance()throws IllegalAccessException, InstantiationException {
        return  (T) newId.newInstance();
    }
}
