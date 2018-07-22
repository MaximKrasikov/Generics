package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    /*Только для классов, которые наследуются от Animal и имеют публичный конструктор без параметров, - создать по одному объекту.
        Добавить созданные объекты в результирующий сет и вернуть.*/
    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {// принимает путь к классу
        Set<Animal> set = new HashSet<>();// общий сет
        File[] list = new File(pathToAnimals).listFiles(); // массив для файлов
        for(File file:list){ // просмотриваем массив
            if(file.isFile()&& file.getName().endsWith(".class")){// объект является файлом и имя файла имеет окончание
                String packageName= Solution.class.getPackage().getName()+".data";// формирование имени пакета для класса
                Class clazz= new ClassFromPath().load(file.toPath(),packageName);// загрузка класса из переданного пути

                int score =0;
                //find interface Animal
                Class[] interfaces= clazz.getInterfaces();
                for(Class interf:interfaces)
                    if(interf.getSimpleName().toString().equals("Animal")){
                        score++;
                        break;
                    }
                    //find default constructor
                    Constructor[] constructors= clazz.getConstructors();
                    for(Constructor constructor:constructors)
                        if(constructor.getParameterCount()==0){// если у конструктора нет параметров
                            score++;
                        }
                        if(score ==2)
                            try {
                                Animal animal= (Animal) clazz.newInstance();
                                set.add(animal);
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
            }
        }
        return set;
    }
    public static class ClassFromPath extends ClassLoader{
        public Class <?> load (Path path, String packageName){// метод принимает путь к классу и имя класса
            try {
                String className= packageName+ "." +path.getFileName().toString().replace(".class","");
                byte [] b= Files.readAllBytes(path);
                return defineClass(className,b,0,b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
        }
    }
}
