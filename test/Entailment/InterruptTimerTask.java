package Entailment;

import java.util.TimerTask;

public class InterruptTimerTask extends TimerTask {
    private Thread theTread;

    public InterruptTimerTask(Thread theTread) {
        this.theTread = theTread;
    }

    @Override
    public void run() {
        theTread.interrupt();
    }
}
