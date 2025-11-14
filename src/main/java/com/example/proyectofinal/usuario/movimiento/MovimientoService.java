package com.example.proyectofinal.usuario.movimiento;

import com.example.proyectofinal.usuario.movimiento.dto.MovimientoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;

    public MovimientoService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    public List<MovimientoDto> obtenerMovimientosPorCuenta(Integer idCuenta) {
        return movimientoRepository.findByCuenta_IdCuenta(idCuenta)
                .stream()
                .map(mov -> {
                    MovimientoDto dto = new MovimientoDto();
                    dto.setIdMovimiento(mov.getIdMovimiento());
                    dto.setTipo(mov.getTipo());
                    dto.setMonto(mov.getMonto());
                    dto.setFecha(mov.getFecha());
                    dto.setIdCuenta(mov.getCuenta().getIdCuenta());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
