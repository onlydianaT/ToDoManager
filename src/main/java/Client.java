import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Client {
    private static final int PORT = 8989;
    private static final String HOST = null;

    public static void main(String[] args) throws ParseException, IOException {
        Map<String, String> object = new HashMap<>();
        //Открываем клиентский socket, используем try,catch, т.к. socket требует закрытия
        try (Socket clientSocket = new Socket(HOST, PORT);
             //Создаем поток вывода, flush - очистка буфера
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             //Создаем поток ввода
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String smth = in.readLine();
            System.out.println("Input action: add or remove and name of task");
            Scanner scanner = new Scanner(System.in);
            String[] word = scanner.nextLine().split(" ");
            object.put("type", word[0]);
            if (word.length > 1) {
                object.put("task", word[1]);
            }
            String json = new Gson().toJson(object);
            File fileOut = new File("reply.json");
            try (FileWriter files = new FileWriter(fileOut)) {
                files.write(json.toString());
                files.flush();
                out.println(fileOut);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String reply = in.readLine();
            File file = new File(reply);
            try (InputStreamReader inr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                System.out.println("To-do list:");
                while (inr.ready()) {
                    char read = (char) inr.read();
                    System.out.print(read);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

