package com.erich.management.Controller;

import com.erich.management.Dto.ClientOrderDto;
import com.erich.management.Services.Impl.ClientOrderServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClientOrderController {

    private final ClientOrderServiceImpl clientService;

    @GetMapping("/all")
    public List<ClientOrderDto> all() {
        return clientService.findAll();
    }

    @PostMapping("/clientorder/save")
    public ResponseEntity<?> save(@RequestBody ClientOrderDto clientOrderDto) {
        return new ResponseEntity<>(clientService.save(clientOrderDto), HttpStatus.CREATED);
    }

    @GetMapping("/clientorder/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/clientorder/{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code) {
        return new ResponseEntity<>(clientService.findClientByCode(code), HttpStatus.OK);
    }

    @DeleteMapping("/clientorder/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
