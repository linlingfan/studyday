package com.glinlf.studyday.jvm;

/**
 * @author glinlf
 * @date 2019-04-02
 * @Description TODO
 **/
public class StudyJVM {

    public Object instance = null;

    public static void main(String[] args){
        StudyJVM a = new StudyJVM();
        StudyJVM b = new StudyJVM();

        a.instance = b;
        b.instance = a;

    }
}
