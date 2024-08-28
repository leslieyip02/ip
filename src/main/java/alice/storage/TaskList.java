package alice.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alice.task.InvalidTaskException;
import alice.task.Task;

public class TaskList {
    private final Storage storage;
    private List<Task> tasks;

    public TaskList(Storage storage) {
        this.storage = storage;
        try {
            this.tasks = loadTasks();
        } catch (IOException | InvalidTaskException e) {
            this.tasks = new ArrayList<>();
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) throws IOException {
        tasks.add(task);
        saveTasks();
    }

    public Task deleteTask(int index) throws IOException {
        Task removedTask = tasks.remove(index);
        saveTasks();
        return removedTask;
    }

    public Task markTask(int index) throws IOException {
        tasks.get(index).setCompletion(true);
        saveTasks();
        return tasks.get(index);
    }

    public Task unmarkTask(int index) throws IOException {
        tasks.get(index).setCompletion(false);
        saveTasks();
        return tasks.get(index);
    }


    /**
     * Finds tasks which contain a given keyword.
     * 
     * @param  keyword the keyword to search for
     * @return         the tasks which contain the keyword
     */
    public List<Task> findTasks(String keyword) {
        List<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            if (task.containsKeyword(keyword)) {
                results.add(task);
            }
        }
        return results;
    }

    private List<Task> loadTasks() throws IOException, InvalidTaskException {
        return storage.loadTasks();
    }

    private void saveTasks() throws IOException {
        storage.saveTasks(tasks);
    }
}