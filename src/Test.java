import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 1234);
        Scanner sIn = new Scanner(s.getInputStream());
        OutputStream sOut = s.getOutputStream();

        Scanner in = new Scanner(System.in);

        while (true) {
            String command = in.nextLine() + "\r\n";
            System.out.println("\033[3;4;35mSending command \"" + command + "\"...");

            sOut.write(command.getBytes(StandardCharsets.US_ASCII));
            sOut.flush();

            System.out.println("Waiting...\033[0m");
            do {
                System.out.println("\033[1;3;36m" + sIn.nextLine() + "\033[0m");
            }
            while (sIn.hasNextLine());
        }
    }
}
