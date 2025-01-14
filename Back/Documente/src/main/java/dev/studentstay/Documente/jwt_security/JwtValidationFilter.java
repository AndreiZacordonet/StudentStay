package dev.studentstay.Documente.jwt_security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class JwtValidationFilter implements Filter {

    private final Map<String, Map<String, String>> roleAccessMap = new HashMap<>() {{
        // cereri
        put("/student-stay/cereri", new HashMap<>() {{
            put("GET", "ADMIN; PROFESSOR");    // get all cereri
            put("POST", "STUDENT; PROFESSOR");  // create a new cerere
        }});
        put("/student-stay/cereri/{id}", new HashMap<>() {{
            put("GET", "ADMIN; PROFESSOR; STUDENT");    // get cerere by id
            put("DELETE", "ADMIN");                     // delete a cerere
            put("PUT", "ADMIN; PROFESSOR");                      // update a cerere
        }});
        put("/student-stay/cereri/user/{id}", new HashMap<>() {{
            put("GET", "ADMIN; PROFESSOR; STUDENT");    // get cerere by user id
        }});


        // clasament
        put("/clasament", new HashMap<>() {{
            put("GET", "ADMIN; PROFESSOR; STUDENT");    // get all clasament
            put("POST", "PROFESSOR");    // populate clasament
        }});
        put("/clasament/{id}", new HashMap<>() {{
            put("POST", "PROFESSOR");    // modify one clasament entry
        }});


        // documente
        put("/documente", new HashMap<>() {{
            put("GET", "PROFESSOR");    // get all documente
            put("POST", "STUDENT; PROFESSOR");   // add document
        }});
        put("/documente/{id}", new HashMap<>() {{
            put("GET", "STUDENT; PROFESSOR");   // get one document
        }});
        put("/documente/extract-text", new HashMap<>() {{
            put("POST", "PROFESSOR");       // extract text from document
        }});
        put("/documente/process-text", new HashMap<>() {{
            put("POST", "PROFESSOR");       // process text from document
        }});


        // repartizari
        put("/repartizari", new HashMap<>() {{
            put("PATCH", "PROFESSOR");      // edit repartition by student's email
        }});
        put("/repartizari/populate", new HashMap<>() {{
            put("POST", "PROFESSOR");      // populate repartition
        }});
        put("/repartizari/{email}", new HashMap<>() {{
            put("GET", "STUDENT");      // get repartition by student id
        }});

        // rezervari
        put("/rezervari", new HashMap<>() {{
            put("POST", "STUDENT; PROFESSOR");      // create rezervation
        }});
        put("/rezervari/{email}", new HashMap<>() {{
            put("GET", "STUDENT; PROFESSOR");      // get rezervation
        }});
    }};

    private final GrpcClientService grpcClientService;

    public JwtValidationFilter(GrpcClientService grpcClientService) {
        this.grpcClientService = grpcClientService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Filter invoked");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authorizationHeader = httpRequest.getHeader("Authorization");

        System.out.println("\nauth header: " + authorizationHeader + "\n");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // FIXME: should i raise an exception an catch it later?
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = authorizationHeader.substring(7);

        System.out.println("\nToken: " + token + "\n");

        try {
            String role = grpcClientService.validateTokenAndGetRole(token);

            if (!isRoleAllowed(httpRequest.getRequestURI(), httpRequest.getMethod(), role)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied for role: " + role);
                return;
            }

            httpRequest.setAttribute("userRole", role);

            chain.doFilter(request, response);
        } catch (Exception e) {
            // TODO: take care of error message
            System.out.println("\nError msg: " + e.getMessage() + "\n");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token: " + e.getMessage());
        }
    }

    public boolean isRoleAllowed(String uri, String method, String role) {

        String baseUri = uri.split("\\?")[0].split("api")[1];   // split dupa 'api'

        for (Map.Entry<String, Map<String, String>> entry : roleAccessMap.entrySet()) {
            String key = entry.getKey();
            Map<String, String> methodMap = entry.getValue();

            String regex = key.replace("{id}", "\\d+").replace("{code}", "[a-zA-Z0-9]+");;

            if (baseUri.matches(regex)) {
                String allowedRoles = methodMap.get(method);

                System.out.println("\nallowedRoles: " + allowedRoles + "\ncontains role: " + Arrays.asList(allowedRoles.split("; ")).contains(role) + "\n");

                return allowedRoles != null && Arrays.asList(allowedRoles.split("; ")).contains(role);
            }
        }

        return false;
    }


//    public boolean isRoleAllowed(String uri, String method, String role) {
//
//        Map<String, String> methodMap = roleAccessMap.get(uri.split("\\?")[0].split("academia")[1]);
//
//        if (methodMap == null) {
//            return false;
//        }
//
//        String allowedRoles = methodMap.get(method);
//
//        return allowedRoles != null && Arrays.asList(allowedRoles.split("; ")).contains(role);
//    }

}
