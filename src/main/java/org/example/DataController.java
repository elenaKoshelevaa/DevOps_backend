package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class DataController {

    private static final Path FILE = Path.of("data.txt");

    @PostMapping(value = "/api/data", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> save(@RequestBody String text) throws IOException {
        Files.writeString(FILE, text + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        return ResponseEntity.ok("Данные сохранены");
    }
    @GetMapping(value = "/api/data", produces = "text/plain;charset=UTF-8")
    public String read(@RequestParam(required = false, defaultValue = "") String filter) throws IOException {
        if (!Files.exists(FILE)) return "";
        return Files.readAllLines(FILE).stream()
                .filter(line -> line.contains(filter))
                .collect(Collectors.joining("\n"));
    }
}
