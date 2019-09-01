import java.util.HashMap;
import java.util.Map;

public class Todos {
    public static Map<Long, Todo> todos = new HashMap<>();

    static {
        todos.put(1L, new Todo(1L, "fisrt task"));
        todos.put(2L, new Todo(2L, "segund task"));
    }

    public static Long nextId(){
        return todos.keySet().stream().reduce(Math::max).orElse(0L)+1L;
    }
}
