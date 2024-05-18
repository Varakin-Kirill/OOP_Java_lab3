import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends HttpServlet {
    private static final String FILE_PATH = "C:\\Users\\Kirill\\IdeaProjects\\lab3\\src\\main\\java\\user.json";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String usersJson = readUsersFromFile();
        response.getWriter().write(usersJson);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        System.out.println("wrk");
        response.setCharacterEncoding("UTF-8");
        System.out.println("wrk");
        StringBuilder jsonRequest = new StringBuilder();
        System.out.println("wrk");

        String line;
        try (BufferedReader reader = request.getReader()) {
            System.out.println("wrk");

            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
        } catch (IOException e) {
            System.out.println("wrk");

            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка при чтении запроса");
            return;
        }
        System.out.println("wrk");

        // Преобразование JSON-строки в JSONObject
        JSONObject newUserJson = new JSONObject(jsonRequest.toString());
        System.out.println("wrk");

        // Добавление нового автомобиля в список
        JSONArray usersJsonArray = new JSONArray(readUsersFromFile());
        usersJsonArray.put(newUserJson);

        System.out.println("wrk");
        // Запись обновленного списка автомобилей в файл
        writeUsersToFile(usersJsonArray.toString());

        // Отправка обновленного списка автомобилей
        response.getWriter().write(usersJsonArray.toString());
    }

    private String readUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    private void writeUsersToFile(String usersJson) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(usersJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}