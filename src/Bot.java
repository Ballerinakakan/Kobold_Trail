import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import javax.security.auth.login.LoginException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Bot{
    public static Kobold siul = new Kobold("Siul", 10, 10, 10, 10, 10, 10, true, Aspects.BROWN, Aspects.BROWN);

    /*
    static Timer timer = new Timer();
    Calendar cal = Calendar.getInstance();
    static TimerTask task = new TimerTask() {
        @Override
        public void run() {
            System.out.print("Timer done!");
        }
    };
     */



    public static void main(String[] args) throws LoginException{
        JDABuilder jda = JDABuilder.createDefault("OTI5MTA1NDA4NjU4OTExMjgz.Ydie1w.5j7biWT3ylUat_QnO_7YP77g6Zs");
        jda.setStatus(OnlineStatus.ONLINE);
        jda.setActivity(Activity.playing("Kobold Trail"));
        jda.addEventListeners(new Commands());
        jda.build();

        //timer.schedule(task, 0);



    }
}
