package cc.coopersoft.house.repair;

public class TestKnow {

    public static void main(String[] args){
        String v = "   66      55 77  ";
        System.out.println("--------");
        for(String s: v.split("\\s+")){
            System.out.println(s);
        }
        System.out.println("--------");


        String v2="201809090911";

        System.out.println(v2.substring(v2.length()- 4));
        System.out.println(v2.substring(0,10));
    }

}
