package cn.element.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeValueAsString(Object obj, HttpServletResponse response) throws IOException {
        String json = objectMapper.writeValueAsString(obj);

        response.setContentType("application/json;charset=utf-8");

        PrintWriter writer = response.getWriter();

        writer.println(json);
        writer.close();
    }
}
