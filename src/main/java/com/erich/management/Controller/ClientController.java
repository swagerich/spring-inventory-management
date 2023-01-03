package com.erich.management.Controller;

import com.erich.management.Dto.ClientDto;
import com.erich.management.Services.Impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClientController {
    private final ClientServiceImpl clientService;

    @GetMapping("/client/all")
    public ResponseEntity<List<ClientDto>> all() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @PostMapping("/client/save")
    public ResponseEntity<?> save(@RequestBody ClientDto clientDto) {
        return new ResponseEntity<>(clientService.save(clientDto), HttpStatus.CREATED);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
