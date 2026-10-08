import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;


public class Main {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        
        GradeTracker tracker = new GradeTracker();
        tracker.loadSampleData();

        System.out.println(tracker.generateSummaryReport());

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

            server.createContext("/api", new ApiHandler(tracker));

            String webDir = new File("web").exists() ? "web" : "../web";
            server.createContext("/", new StaticFileHandler(webDir));

            server.setExecutor(Executors.newFixedThreadPool(4));
            server.start();

            String url = "http://localhost:" + PORT;
            System.out.println("========================================================================");
            System.out.println("  Web GUI is running at: " + url);
            System.out.println("  (Opening browser automatically...)");
            System.out.println("  Press Ctrl+C to stop the server.");
            System.out.println("========================================================================");

            openBrowser(url);

        } catch (Exception e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }

    private static void openBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", url});
            }
        } catch (Exception ignored) {
            System.out.println("Please open your browser and go to: " + url);
        }
    }

    static class ApiHandler implements HttpHandler {
        private final GradeTracker tracker;

        public ApiHandler(GradeTracker tracker) {
            this.tracker = tracker;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            String method = exchange.getRequestMethod().toUpperCase();
            if (method.equals("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            URI uri = exchange.getRequestURI();
            String path = uri.getPath();
            Map<String, String> query = parseQuery(uri.getQuery());

            try {
                if (path.equals("/api/students")) {
                    handleStudents(exchange, method, query);
                } else if (path.equals("/api/summary")) {
                    handleSummary(exchange);
                } else if (path.equals("/api/report")) {
                    handleReport(exchange);
                } else if (path.equals("/api/reset")) {
                    tracker.loadSampleData();
                    sendJson(exchange, 200, "{\"success\":true}");
                } else {
                    sendJson(exchange, 404, "{\"error\":\"Not found\"}");
                }
            } catch (Exception e) {
                sendJson(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }

        private void handleStudents(HttpExchange exchange, String method, Map<String, String> query) throws IOException {
            switch (method) {
                case "GET":
                    StringBuilder sb = new StringBuilder("[");
                    List<Student> list = tracker.getAllStudents();
                    for (int i = 0; i < list.size(); i++) {
                        Student s = list.get(i);
                        sb.append(String.format("{\"index\":%d,\"name\":\"%s\",\"score\":%.2f,\"grade\":\"%s\",\"status\":\"%s\"}",
                                i, escape(s.getName()), s.getScore(), s.getLetterGrade(), s.getStatus()));
                        if (i < list.size() - 1) sb.append(",");
                    }
                    sb.append("]");
                    sendJson(exchange, 200, sb.toString());
                    break;

                case "POST":
                    String postBody = readBody(exchange);
                    String name = extractString(postBody, "name");
                    double score = extractDouble(postBody, "score");
                    if (name.isEmpty() || score < 0 || score > 100) {
                        sendJson(exchange, 400, "{\"error\":\"Invalid student name or grade\"}");
                        return;
                    }
                    tracker.addStudent(name, score);
                    sendJson(exchange, 201, "{\"success\":true}");
                    break;

                case "PUT":
                    int editIdx = Integer.parseInt(query.getOrDefault("index", "-1"));
                    String putBody = readBody(exchange);
                    String uName = extractString(putBody, "name");
                    double uScore = extractDouble(putBody, "score");
                    boolean updated = tracker.updateStudent(editIdx, uName, uScore);
                    if (updated) {
                        sendJson(exchange, 200, "{\"success\":true}");
                    } else {
                        sendJson(exchange, 404, "{\"error\":\"Student index not found\"}");
                    }
                    break;

                case "DELETE":
                    int delIdx = Integer.parseInt(query.getOrDefault("index", "-1"));
                    boolean deleted = tracker.deleteStudent(delIdx);
                    if (deleted) {
                        sendJson(exchange, 200, "{\"success\":true}");
                    } else {
                        sendJson(exchange, 404, "{\"error\":\"Student index not found\"}");
                    }
                    break;

                default:
                    sendJson(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        }

        private void handleSummary(HttpExchange exchange) throws IOException {
            Student top = tracker.getHighestStudent();
            Student low = tracker.getLowestStudent();

            String json = String.format(
                    "{\"total\":%d,\"average\":%.2f,\"highest\":%.2f,\"topStudent\":\"%s\",\"lowest\":%.2f,\"lowestStudent\":\"%s\"}",
                    tracker.getStudentCount(),
                    tracker.calculateAverage(),
                    tracker.calculateHighest(),
                    top != null ? escape(top.getName()) : "None",
                    tracker.calculateLowest(),
                    low != null ? escape(low.getName()) : "None"
            );
            sendJson(exchange, 200, json);
        }

        private void handleReport(HttpExchange exchange) throws IOException {
            String report = tracker.generateSummaryReport();
            byte[] bytes = report.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }

        private void sendJson(HttpExchange exchange, int status, String json) throws IOException {
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }

        private String readBody(HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n;
            while ((n = is.read(buf)) != -1) baos.write(buf, 0, n);
            return baos.toString(StandardCharsets.UTF_8);
        }

        private Map<String, String> parseQuery(String query) {
            Map<String, String> map = new HashMap<>();
            if (query == null || query.isEmpty()) return map;
            for (String pair : query.split("&")) {
                String[] parts = pair.split("=", 2);
                if (parts.length == 2) {
                    try {
                        map.put(URLDecoder.decode(parts[0], StandardCharsets.UTF_8.name()),
                                URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name()));
                    } catch (Exception ignored) {}
                }
            }
            return map;
        }

        private String extractString(String json, String key) {
            String target = "\"" + key + "\":\"";
            int idx = json.indexOf(target);
            if (idx == -1) return "";
            idx += target.length();
            int end = json.indexOf("\"", idx);
            if (end == -1) return "";
            return json.substring(idx, end).replace("\\\"", "\"").trim();
        }

        private double extractDouble(String json, String key) {
            String target = "\"" + key + "\":";
            int idx = json.indexOf(target);
            if (idx == -1) return -1;
            idx += target.length();
            int end = idx;
            while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '.')) {
                end++;
            }
            try {
                return Double.parseDouble(json.substring(idx, end));
            } catch (Exception e) {
                return -1;
            }
        }

        private String escape(String s) {
            return s == null ? "" : s.replace("\"", "\\\"");
        }
    }
    
    static class StaticFileHandler implements HttpHandler {
        private final Path webRoot;

        public StaticFileHandler(String dir) {
            this.webRoot = Paths.get(dir).toAbsolutePath().normalize();
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/") || path.isEmpty()) path = "/index.html";

            Path file = webRoot.resolve(path.substring(1)).normalize();
            if (!file.startsWith(webRoot) || !file.toFile().exists() || file.toFile().isDirectory()) {
                byte[] notFound = "404 Not Found".getBytes();
                exchange.sendResponseHeaders(404, notFound.length);
                try (OutputStream os = exchange.getResponseBody()) { os.write(notFound); }
                return;
            }

            String mime = "text/plain";
            String fn = file.getFileName().toString().toLowerCase();
            if (fn.endsWith(".html")) mime = "text/html; charset=UTF-8";
            else if (fn.endsWith(".css")) mime = "text/css; charset=UTF-8";
            else if (fn.endsWith(".js")) mime = "application/javascript; charset=UTF-8";

            byte[] bytes = java.nio.file.Files.readAllBytes(file);
            exchange.getResponseHeaders().set("Content-Type", mime);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
        }
    }
}
