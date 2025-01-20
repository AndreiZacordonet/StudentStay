package dev.studentstay.Documente.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleBasedLinksService {

    private final Map<String, Map<String, String>> roleAccessMap = new HashMap<>() {{
        // cereri
//        put("/student-stay/cereri", new HashMap<>() {{
//            put("GET", "ADMIN; PROFESSOR");    // get all cereri
//            put("POST", "STUDENT; PROFESSOR");  // create a new cerere
//        }});
//        put("/student-stay/cereri/{id}", new HashMap<>() {{
//            put("GET", "ADMIN; PROFESSOR; STUDENT");    // get cerere by id
//            put("DELETE", "ADMIN");                     // delete a cerere
//            put("PUT", "ADMIN; PROFESSOR");                      // update a cerere
//        }});
//        put("/student-stay/cereri/user/{id}", new HashMap<>() {{
//            put("GET", "ADMIN; PROFESSOR; STUDENT");    // get cerere by user id
//        }});


//        // clasament
//        put("/clasament", new HashMap<>() {{
//            put("GET", "ADMIN; PROFESSOR; STUDENT");    // get all clasament
//            put("POST", "PROFESSOR");    // populate clasament
//        }});
//        put("/clasament/{id}", new HashMap<>() {{
//            put("POST", "PROFESSOR");    // modify one clasament entry
//        }});


//        // documente
//        put("/documente", new HashMap<>() {{
//            put("GET", "PROFESSOR");    // get all documente
//            put("POST", "STUDENT; PROFESSOR");   // add document
//        }});
//        put("/documente/{id}", new HashMap<>() {{
//            put("GET", "STUDENT; PROFESSOR");   // get one document
//        }});
//        put("/documente/extract-text", new HashMap<>() {{
//            put("POST", "PROFESSOR");       // extract text from document
//        }});
//        put("/documente/process-text", new HashMap<>() {{
//            put("POST", "PROFESSOR");       // process text from document
//        }});
//        put("/documente/update-text/{id}", new HashMap<>() {{
//            put("PATCH", "PROFESSOR");       // process text from document
//        }});


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

//        // rezervari
//        put("/rezervari", new HashMap<>() {{
//            put("POST", "STUDENT; PROFESSOR");      // create rezervation
//        }});
//        put("/rezervari/{email}", new HashMap<>() {{
//            put("GET", "STUDENT; PROFESSOR");      // get rezervation
//        }});

//        put("/links", new HashMap<>() {{
//            put("GET", "ADMIN; STUDENT; PROFESSOR");    // allow to get links uri
//        }});
    }};

    public List<Map<String, Object>> getLinksForRole(String role) {
        List<Map<String, Object>> links = new ArrayList<>();

        for (Map.Entry<String, Map<String, String>> entry : roleAccessMap.entrySet()) {
            String endpoint = entry.getKey();
            Map<String, String> methodMap = entry.getValue();

            methodMap.forEach((method, allowedRoles) -> {
                if (Arrays.asList(allowedRoles.split("; ")).contains(role)) {
                    Map<String, Object> link = new HashMap<>();
                    link.put("url", endpoint);
                    link.put("method", method);
                    links.add(link);
                }
            });
        }

        return links;
    }
}
