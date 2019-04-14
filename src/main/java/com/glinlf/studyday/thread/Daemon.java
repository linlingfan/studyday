package com.glinlf.studyday.thread;

/**
 * @author glinlf
 * @date 2019-04-13
 * @Description TODO
 **/
public class Daemon {

    public static void main(String[] args){

        Thread thread = new Thread(new DaemonRunner());
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("finally!");
            }
        }
    }
}
