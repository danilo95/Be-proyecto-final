package com.example.proyectofinal.usuario.cuenta;

import com.example.proyectofinal.usuario.cuenta.dto.CuentaDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    private final CuentaBancariaRepository cuentaRepo;

    public CuentaService(CuentaBancariaRepository cuentaRepo) {
        this.cuentaRepo = cuentaRepo;
    }

    public List<CuentaDto> obtenerCuentasPorUsuario(Integer idUsuario) {
        return cuentaRepo.findByCliente_IdUsuario(idUsuario)
                .stream()
                .map(cuenta -> {
                    CuentaDto dto = new CuentaDto();
                    dto.setIdCuenta(cuenta.getIdCuenta());
                    dto.setNumeroCuenta(cuenta.getNumeroCuenta());
                    dto.setSaldo(cuenta.getSaldo());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
