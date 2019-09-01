import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TodosServlet extends HttpServlet {
    private final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //httpServletResponse.setStatus(200);
        httpServletResponse.setHeader("Content-Type", "application/json");

        String json = GSON.toJson(Todos.todos.values());
        httpServletResponse.getOutputStream().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String jsonBody = Util.readInputStream(httpServletRequest.getInputStream());

        Todo todo = GSON.fromJson(jsonBody, Todo.class);
        todo.setId(Todos.nextId());
        Todos.todos.put(todo.getId(), todo);

        httpServletResponse.setStatus(201);
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.getOutputStream().println(GSON.toJson(todo));
    }
}
