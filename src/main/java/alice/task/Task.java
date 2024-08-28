package alice.task;

import java.util.Map;

import alice.parser.TaskParser;

/** Represents tasks. */
public abstract class Task {
    protected String description;
    protected boolean isCompleted;

    /** Types of tasks. */
    public enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }

    public Task(String line) throws InvalidTaskException {
        this.description = TaskParser.parseDescription(line);
        this.isCompleted = false;
    }

    /**
     * Sets the completion status of a task.
     * 
     * @param isCompleted whether the task should be set as complete or incomplete
     */
    public void setCompletion(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * Parses a JSON string into a task.
     * 
     * @param  jsonString the JSON string representing a task
     * @return            the parsed task
     */
    public static Task fromJsonString(String jsonString) throws InvalidTaskException {
        Map<String, String> arguments = TaskParser.parseJsonString(jsonString);        
        switch (TaskType.valueOf(arguments.get("taskType").toUpperCase())) {
        case TODO:
            return ToDo.fromJsonString(jsonString);
        case DEADLINE:
            return Deadline.fromJsonString(jsonString);
        case EVENT:
            return Event.fromJsonString(jsonString);
        default:
            throw new InvalidTaskException();
        }
    }

    /**
     * Checks if the task description contains a given keyword.
     * 
     * @param  keyword the keyword to search for
     * @return         whether the description contains the keyword
     */
    public boolean containsKeyword(String keyword) {
        return description.contains(keyword);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isCompleted ? "X" : " ", description);
    }

    /**
     * Converts a task into a JSON string for storage.
     * The implementation is handled in the subclasses.
     * 
     * @param  jsonString the JSON string representing a task
     * @return            the parsed task
     */
    public abstract String toJsonString();
}
