package com.example.studente.impiccato;

public class Timer extends Thread{
    private  GiocoActivity activity;

    public Timer(GiocoActivity giocoActivity){
        this.start();
        activity=giocoActivity;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            activity.setTimer(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
