package com.glinlf.studyday.interview.magima;

/**
 * @author glinlf
 * @date 2019-04-08
 * @Description TODO
 **/
public class Son extends Parent {

    int j = 1;

    Son() {
        j = 2;
    }

    {
        j = 3;
    }

    @Override
    protected int getValue() {
        return j;
    }

    public static void main(String[] args){
//        Son son = new Son();
//        System.out.println(son.getValue());
        Parent parent = new Parent();
        System.out.println(parent.getValue());
    }
}
