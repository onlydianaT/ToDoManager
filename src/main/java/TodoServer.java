import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class TodoServer {
    private int port = 8989;
    private Todos todos = new Todos();
    private Map<String, String> objectT = new HashMap<>();
    //private List action=new ArrayList<>();
    // private List task=new ArrayList<>();


    public TodoServer(int port, Todos todos) {
        this.port = port;
        this.todos = todos;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Starting server at " + port + "...");
            while (true) {
                //Ожидаем подключения
                //После открытия серверный сокет откроет клиентский сокет
                try (Socket clientSocket = serverSocket.accept();
                     //Создаем поток вывода
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     //Создаем поток ввода
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    out.println("Server started");
                    File fileAction = new File("actions.txt");
                    File fileTasks = new File("tasks.txt");

                    String fileIn = in.readLine();
                    JSONParser parser = new JSONParser();
                    try {
                        Object object = parser.parse(new FileReader(fileIn));
                        String jsonText = JSONValue.toJSONString(object).toLowerCase();
                        Map<String, String> result =
                                new ObjectMapper().readValue(jsonText, HashMap.class);
                        if (!result.get("type").equals("restore")) {
                            try (FileWriter files = new FileWriter(fileAction, true)) {
                                files.write((result.get("type") + " ").toString());
                                files.flush();
                            }
                        }
                        if (fileAction.exists()) {
                            List action = new ArrayList<>();
                            try (BufferedReader inr = new BufferedReader(new FileReader(fileAction))) {
                                String line = inr.readLine();
                                String[] read = line.split(" ");
                                for (int i = 0; i < read.length; i++) {
                                    action.add(read[i]);

                                }
                            }
                            if (result.get("task") != (null)) {
                                try (FileWriter files = new FileWriter(fileTasks, true)) {
                                    files.write((result.get("task") + " ").toString());
                                    files.flush();
                                }
                            }
                            if (fileTasks.exists()) {
                                List task = new ArrayList<>();
                                try (BufferedReader inr = new BufferedReader(new FileReader(fileTasks))) {
                                    String line = inr.readLine();
                                    String[] read = line.split(" ");
                                    for (int i = 0; i < read.length; i++) {
                                        task.add(read[i]);
                                    }
                                }
                                if (result.get("type").equals("add")) {
                                    todos.addTask(result.get("task"));
                                } else if (result.get("type").equals("remove")) {
                                    todos.removeTask(result.get("task"));
                                } else if (result.get("type").equals("restore")) {
                                    todos.restoreTask(action, task);
                                }
                            }
                        }
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }

                    List tasks = todos.getAllTasks();
                    File fileOut = new File("reply.txt");
                    try (FileWriter files = new FileWriter(fileOut)) {
                        files.write(tasks.toString());
                        files.flush();
                        out.println(fileOut);
                    }
                } catch (IOException e) {
                    System.out.println("Не могу стартовать сервер");
                    e.printStackTrace();
                }
            }
        }
    }
}
