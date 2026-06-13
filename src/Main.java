import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        HttpClient client = HttpClient.newHttpClient();

        Scanner sc = new Scanner(System.in);
        System.out.println("use '.exit' to exit the app");
        System.out.print("github-activity> ");
        while (sc.hasNextLine()) {
            String command = sc.nextLine();
            if (command.equals(".exit")) {
                break;
            } else if (command.isEmpty()) {
                System.out.println("Please provide a GitHub username.");
            } else {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://api.github.com/users/" + command + "/events"))
                        .build();
                HttpResponse<String> response;
                try {
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (response.statusCode() != 200) {
                    if (response.statusCode() == 404) {
                        System.out.println("User not found. Please check the username.");
                    } else {
                        System.out.println("Error occurred, " + "status code: " + response.statusCode());
                    }
                }
                System.out.println(response.body());
            }
            System.out.print("github-activity> ");
        }
    }
}