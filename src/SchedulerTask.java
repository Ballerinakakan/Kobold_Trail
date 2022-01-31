import java.util.Date;
import java.util.TimerTask;

public class SchedulerTask extends TimerTask {
    Date now;

    @Override
    public void run() {
        now = new Date();
        System.out.print("Made backup at: " + now);

    }
}
