import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodosTest {

        private List<String> list = new ArrayList<>();
        private Todos todos = new Todos();

        @ParameterizedTest
        @CsvSource({"run"})
        void addTask(String task) {
            list.add(task);
            list.add(task);
            list.add(task);
            list.add(task);
            list.add(task);
            list.add(task);
            list.add(task);
            todos.addTask(task);
            todos.addTask(task);
            todos.addTask(task);
            todos.addTask(task);
            todos.addTask(task);
            todos.addTask(task);
            todos.addTask(task);
            todos.addTask(task);
            assertEquals(list, todos.getAllTasks());
        }

        @Test
        void removeTask() {
            list.add("task");
            list.add("task2");
            todos.addTask("task");
            todos.addTask("task2");
            todos.addTask("task3");
            todos.removeTask("task3");
            assertEquals(list, todos.getAllTasks());
        }

        @org.junit.jupiter.api.Test
        void getAllTasks() {
            list.add("ask");
            list.add("tell");
            todos.addTask("tell");
            todos.addTask("ask");
            assertEquals(list, todos.getAllTasks());
        }
    }


