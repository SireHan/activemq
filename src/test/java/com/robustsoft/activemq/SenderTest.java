package com.robustsoft.activemq;
/**
 * Created by JoinHan on 2017/3/24.
 */
class SenderTest {

        public static void main(String[] args){
            Sender sender = new Sender();
            sender.init();
            SenderMq testMq = new SenderMq(sender);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Thread 1
            new Thread(new SenderMq(sender)).start();
            //Thread 2
            new Thread(new SenderMq(sender)).start();
            //Thread 3
            new Thread(new SenderMq(sender)).start();
            //Thread 4
            new Thread(new SenderMq(sender)).start();
            //Thread 5
            new Thread(new SenderMq(sender)).start();

            sender.distory();
        }
        private static class SenderMq implements Runnable{
            Sender sender;
            public SenderMq(Sender sender){
                this.sender = sender;
            }

            @Override
            public void run() {
                while(true){
                    try {
                        sender.sendMessage("Test-MQ");
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
