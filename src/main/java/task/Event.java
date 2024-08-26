package task;

import java.util.*;

public class Event extends Task {
    private String from;
    private String to;

    public Event(String line) throws InvalidTaskException {
        super(line);

        Map<String, String> flags = parseFlags(line);
        if (!flags.containsKey("from")) {
            throw new InvalidTaskException("Missing /from flag.");
        }
        if (!flags.containsKey("to")) {
            throw new InvalidTaskException("Missing /to flag.");
        }
        this.from = flags.get("from");
        this.to = flags.get("to");
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s, to: %s)", super.toString(), from, to);
    }

    @Override
    public String toJsonString() {
        List<String> keyValuePairs = new ArrayList<>();
        keyValuePairs.add("\"taskType\": \"event\"");
        keyValuePairs.add(String.format("\"description\": \"%s\"", description));
        keyValuePairs.add(String.format("\"isCompleted\": \"%s\"", isCompleted));
        keyValuePairs.add(String.format("\"from\": \"%s\"", from));
        keyValuePairs.add(String.format("\"to\": \"%s\"", to));
        return String.format("{%s}", String.join(", ", keyValuePairs));
    }

    public static Task fromJsonString(String jsonString) throws InvalidTaskException {
        Map<String, String> arguments = parseJsonString(jsonString);
        String inputLine = String.format(
            "event %s /from %s /to %s",
            arguments.get("description"),
            arguments.get("from"),
            arguments.get("to")
        );
        Event event = new Event(inputLine);
        event.isCompleted = arguments.get("isCompleted").compareTo("true") == 0;
        return event;
    }
}
