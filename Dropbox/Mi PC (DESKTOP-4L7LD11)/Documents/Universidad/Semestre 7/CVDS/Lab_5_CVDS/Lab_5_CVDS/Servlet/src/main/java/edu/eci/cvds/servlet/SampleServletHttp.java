package edu.eci.cvds.servlet;

import edu.eci.cvds.servlet.model.Todo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Optional;

@WebServlet(
        urlPatterns = "/QuesoConBocadillo"
)

public class SampleServletHttp extends HttpServlet{
    static final long serialVersionUID = 35L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id;
        Writer respWriter = resp.getWriter();
        ArrayList<Todo> todos = new ArrayList();
        try {
            Optional<String> optiName = Optional.ofNullable(req.getParameter("id"));
            if (optiName.isPresent() && !optiName.get().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                id = optiName.get();
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                id = "";
            }
            Todo todo = Service.getTodo(Integer.parseInt(id));
            todos.add(todo);
            respWriter.write(Service.todosToHTMLTable(todos));
        } catch (FileNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            //responseWriter.write("requerimiento no encontrado");
        }catch (NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            //responseWriter.write("requerimiento invalido");
        }catch (MalformedURLException e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //responseWriter.write("Url incorrecto");
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            //responseWriter.write("requerimiento invalido");
        }
        respWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id;
        Writer respWriter = resp.getWriter();
        ArrayList<Todo> todos = new ArrayList();
        try {
            Optional<String> optiName = Optional.ofNullable(req.getParameter("id"));
            if (optiName.isPresent() && !optiName.get().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                id = optiName.get();
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                id = "";
            }
            Todo todo = Service.getTodo(Integer.parseInt(id));
            todos.add(todo);
            respWriter.write(Service.todosToHTMLTable(todos));
        } catch (FileNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            //responseWriter.write("requerimiento no encontrado");
        }catch (NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            //responseWriter.write("requerimiento invalido");
        }catch (MalformedURLException e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //responseWriter.write("Url incorrecto");
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            //responseWriter.write("requerimiento invalido");
        }
        respWriter.flush();
    }
}
