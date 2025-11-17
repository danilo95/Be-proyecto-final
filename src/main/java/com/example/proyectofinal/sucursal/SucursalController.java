package com.example.proyectofinal.sucursal;

import com.example.proyectofinal.sucursal.EXCEPTIONS.NotFoundException;
import com.example.proyectofinal.sucursal.dto.SucursalDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final SucursalService service;

    public SucursalController(SucursalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SucursalDto> crear(@Valid @RequestBody CrearSucursalRequest req) {
        SucursalDto created = service.crear(req);
        return ResponseEntity.created(URI.create("/api/sucursales/" + created.getIdSucursal())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalDto> obtener(@PathVariable Integer id) {
        SucursalDto dto = service.obtenerPorId(id);
        if (dto == null) throw new NotFoundException("Sucursal no encontrada: " + id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<SucursalDto> listar() {
        return service.listarTodas();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDto> actualizar(@PathVariable Integer id,
                                                  @Valid @RequestBody CrearSucursalRequest req) {
        SucursalDto updated = service.actualizar(id, req); // lanzará NotFoundException si no existe
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id); // lanzará NotFoundException si no existe
        return ResponseEntity.noContent().build();
    }
}