package com.robustsoft.activemq;
/**
 * Created by JoinHan on 2017/3/24.
 */
class ReceiverTest {

    public static void main(String[] args){
        Receiver receiver = new Receiver();
        receiver.init();
        ReceiverMq testMq = new ReceiverMq(receiver);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Thread 1
        new Thread(new ReceiverMq(receiver)).start();
        //Thread 2
        new Thread(new ReceiverMq(receiver)).start();
        //Thread 3
        new Thread(new ReceiverMq(receiver)).start();
        //Thread 4
        new Thread(new ReceiverMq(receiver)).start();
        //Thread 5
        new Thread(new ReceiverMq(receiver)).start();
    }
    private static class ReceiverMq implements Runnable{
        Receiver receiver;
        public ReceiverMq(Receiver receiver){
            this.receiver = receiver;
        }

        @Override
        public void run() {
            while(true){
                try {
                    receiver.getMessage("Test-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
