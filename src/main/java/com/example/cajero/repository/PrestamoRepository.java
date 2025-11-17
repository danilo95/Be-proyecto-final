package com.example.cajero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cajero.entity.EstadoPrestamo;
import com.example.cajero.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    // MÉTODO EXISTENTE (ACTUALIZADO)
    List<Prestamo> findByEstado(EstadoPrestamo estado);

    // MÉTODOS NUEVOS AGREGADOS
    List<Prestamo> findByCliente_IdUsuario(Integer idCliente);
    List<Prestamo> findByCajero_IdUsuario(Integer idCajero);
    Optional<Prestamo> findByIdPrestamo(Integer idPrestamo);
    List<Prestamo> findByEstadoOrderByFechaSolicitudDesc(EstadoPrestamo estado);
}