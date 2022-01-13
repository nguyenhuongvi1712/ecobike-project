package ecoBike.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.net.http.HttpRequest;



public class API {
    public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

    public static String get(String url, String token) throws Exception {
        LOGGER.info("Request URL: " + url + "\n");
        URL line_api_url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder respone = new StringBuilder(); // ising StringBuilder for the sake of memory and performance
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        respone.append(inputLine + "\n");
        in.close();
        LOGGER.info("Respone Info: " + respone.substring(0, respone.length() - 1).toString());
        return respone.substring(0, respone.length() - 1).toString();
    }

    int var;

    public static String post(String url, String data) throws IOException, URISyntaxException, InterruptedException {
        LOGGER.info("Request Info:\nRequest URL: " + url + "\n" + "Payload Data: " + data + "\n");
        String responseInfo =  patch(url,data);
        LOGGER.info("Respone Info: " + responseInfo);
        return responseInfo;

//        allowMethods("PATCH");
//        URL line_api_url = new URL(url);
//        String payload = data;
//        HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//        conn.setRequestMethod("PATCH");
//        System.out.println("POst method");
//        conn.setRequestProperty("Content-Type", "application/json");
//        Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//        writer.write(payload);
//        writer.close();
//        BufferedReader in;
//        String inputLine;
//        if (conn.getResponseCode() / 100 == 2) {
//            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//        StringBuilder response = new StringBuilder();
//        while ((inputLine = in.readLine()) != null)
//            response.append(inputLine);
//        in.close();
    }

    private static void allowMethods(String... methods) {
        try {
            System.out.println("method: "+ methods);
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
            methodsField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/* static field */, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    public static String patch(String url, String data) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(data))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }




}