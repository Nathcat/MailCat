import com.nathcat.mailcat.smtp.SMTPInputStream;
import com.nathcat.mailcat.smtp.commands.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 1234);
        SMTPInputStream sIn = new SMTPInputStream(s.getInputStream());
        OutputStream sOut = s.getOutputStream();

        Scanner in = new Scanner(System.in);

        System.out.println("\033[1;3;36m" + sIn.readResponse() + "\033[0m");

        while (true) {
            System.out.print("\033[3;4;35mEnter command >\033[0m ");
            String command = in.nextLine() + "\r\n";
            //System.out.println("\033[3;4;35mSending command \"" + command + "\"...");

            sOut.write(command.getBytes(StandardCharsets.US_ASCII));
            sOut.flush();

            //System.out.println("Waiting...\033[0m");
            Response r = sIn.readResponse();

            System.out.println("\033[1;3;36m" + r + "\033[0m");

            if (r.code == 221) {
                System.exit(0);
            }
        }
    }
}
