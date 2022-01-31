import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Scanner;
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



    public static void main(String[] args) {
        String token;
        try{
            File tokenFile = new File("src/token.txt");
            Scanner reader = new Scanner(tokenFile);
            token = reader.nextLine();
            System.out.print("Token successfully read!\n");
        }
        catch (FileNotFoundException e){
            System.out.print("Couldn't find the token text file, please make sure you have it placed" +
                    "in the src folder and that it's named 'token.txt' \n");
            token = "nope";
        }

        try {
            JDABuilder jda = JDABuilder.createDefault(token);
            jda.setStatus(OnlineStatus.ONLINE);
            jda.setActivity(Activity.playing("Kobold Trail"));
            jda.addEventListeners(new Commands());
            jda.build();
        }
        catch (LoginException e){
            System.out.print("Token was invalid, make sure you have a valid token in the " +
                    "'token.txt' file\n");
        }

        //timer.schedule(task, 0);
    }
}
