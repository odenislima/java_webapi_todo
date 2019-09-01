import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class TodoServlet extends HttpServlet {

    private final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {

        String uri = httpServletRequest.getRequestURI();
        Long id  = Long.parseLong(uri.substring("/todos/".length()));

        httpServletResponse.setStatus(200);
        httpServletResponse.setHeader("Content-Type", "application/json");

        String json = GSON.toJson(Todos.todos.get(id));
        httpServletResponse.getOutputStream().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        //get the id from the URL
        String uri = httpServletRequest.getRequestURI();
        Long id  = Long.parseLong(uri.substring("/todos/".length()));

        if(Todos.todos.containsKey(id)){
            httpServletResponse.setStatus(422);
            httpServletResponse.getOutputStream().println("You cannot create todo with id " + id + "it already exists");

        }

        //get the todo from the body
        String jsonBody = Util.readInputStream(httpServletRequest.getInputStream());
        Todo todo = GSON.fromJson(jsonBody, Todo.class);
        todo.setId(id);

        //insert new todo
        Todos.todos.put(todo.getId(),todo);


        String json = GSON.toJson(Todos.todos.get(id));
        httpServletResponse.setStatus(201);
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.getOutputStream().println(json);
    }

    @Override
    protected void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        //get the id from the URL
        String uri = httpServletRequest.getRequestURI();
        Long id  = Long.parseLong(uri.substring("/todos/".length()));

        if(!Todos.todos.containsKey(id)){
            httpServletResponse.setStatus(422);
            httpServletResponse.getOutputStream().println("You cannot up todo with id " + id + "it does not exists");

        }

        //get the todo from the body
        String jsonBody = Util.readInputStream(httpServletRequest.getInputStream());
        Todo todo = GSON.fromJson(jsonBody, Todo.class);
        todo.setId(id);

        //insert new todo
        Todos.todos.put(todo.getId(),todo);


        String json = GSON.toJson(Todos.todos.get(id));
        httpServletResponse.setStatus(200);
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.getOutputStream().println(json);
    }

    @Override
    protected void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String uri = httpServletRequest.getRequestURI();
        Long id  = Long.parseLong(uri.substring("/todos/".length()));

        Todo todo = Todos.todos.remove(id);
        httpServletResponse.setStatus(200);
        httpServletResponse.setHeader("Content-Type", "application/json");

        String json = GSON.toJson(todo);
        httpServletResponse.getOutputStream().println(json);
    }
}
