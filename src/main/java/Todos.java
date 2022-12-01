import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Todos {
    private List<String> list = new ArrayList<>();
    private int maxTasks = 7;
    private File fileAction = new File("actions.txt");
    private File fileTasks = new File("tasks.txt");


    public void addTask(String task) {
        if (list.size() < maxTasks) {
            list.add(task);
        }
    }

    public void removeTask(String task) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(task)) {
                list.remove(i);
            }
        }
    }

    public void restoreTask(List action, List task) throws IOException {
        String act = (String) action.get(action.size() - 1);
        if (act.equals("add")) {
            int sizeAction = action.size();
            action.remove(sizeAction - 1);
            String forRemove = (String) task.get(task.size() - 1);
            list.remove(forRemove);
            int sizeTask = task.size();
            task.remove(sizeTask - 1);
            task.remove(forRemove);
            try (FileWriter files = new FileWriter(fileAction)) {
                for (int i = 0; i < action.size(); i++) {
                    files.write(action.get(i) + " ");
                    files.flush();
                }
            }
            try (FileWriter files = new FileWriter(fileTasks)) {
                for (int i = 0; i < task.size(); i++) {
                    files.write(task.get(i) + " ");
                    files.flush();
                }
            }
        } else if (act.equals("remove")) {
            int sizeAction = action.size();
            action.remove(sizeAction - 1);
            String forAdd = (String) task.get(task.size() - 1);
            list.add(forAdd);
            int sizeTask = task.size();
            task.remove(sizeTask - 1);
            try (FileWriter files = new FileWriter(fileAction)) {
                for (int i = 0; i < action.size(); i++) {
                    files.write(action.get(i) + " ");
                    files.flush();
                }
            }
            try (FileWriter files = new FileWriter(fileTasks)) {
                for (int i = 0; i < task.size(); i++) {
                    files.write(task.get(i) + " ");
                    files.flush();
                }
            }
        }
    }

    public List<String> getAllTasks() {
        Collections.sort(list);
        return list;
    }
}
