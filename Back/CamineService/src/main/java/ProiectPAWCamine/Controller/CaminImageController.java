package ProiectPAWCamine.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CaminImageController {

    @GetMapping("/api/camine/images")
    public ResponseEntity<List<String>> getImages(@RequestParam String folderPath) {
        try {
            File folder = new ClassPathResource("static/" + folderPath).getFile();
            System.out.println("Folder primit: " + folder.getAbsolutePath());

            File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".webp"));

            List<String> imagePaths = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    imagePaths.add("http://localhost:8080/static/" + folderPath + "/" + file.getName());
                }
            }
            System.out.println(imagePaths);
            return ResponseEntity.ok(imagePaths);
        } catch (Exception e) {
            System.err.println("Eroare la accesarea folderului: " + e.getMessage());
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }
}
