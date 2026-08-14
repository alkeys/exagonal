package com.exagonal001.libros.infra.persistencie;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exagonal001.libros.application.port.out.PrestamosRepositoryPort;
import com.exagonal001.libros.domain.models.PrestamosLibros;
import com.exagonal001.libros.infra.models.LibroEntity;
import com.exagonal001.libros.infra.models.PrestamosEntity;
import com.exagonal001.user.infra.models.UserEntity;
import com.exagonal001.user.infra.persistence.SpringDataUserRepository;


@Repository
public class JpaPRestamosRepositoryAdapter implements PrestamosRepositoryPort {

    private final SpringDataPrestamoRepository springDataPrestamoRepository;
    
    private final SpringDataUserRepository springDataUserRepository;

    private final SpringDataLibroRepository SpringDataLibroRepository;  

  

    public JpaPRestamosRepositoryAdapter(SpringDataPrestamoRepository springDataPrestamoRepository,
            SpringDataUserRepository springDataUserRepository, SpringDataLibroRepository springDataLibroRepository) {
        this.springDataPrestamoRepository = springDataPrestamoRepository;
        this.springDataUserRepository = springDataUserRepository;
        this.SpringDataLibroRepository = springDataLibroRepository;
    }

    @Override
    public List<PrestamosLibros> findAll() {
        return springDataPrestamoRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public PrestamosLibros findById(String id) {
        PrestamosEntity prestamosEntity = springDataPrestamoRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con id: " + id));
        return toDomain(prestamosEntity);
    }

    @Override
    public PrestamosLibros update(String id, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        PrestamosEntity prestamosEntity = springDataPrestamoRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con id: " + id));
        prestamosEntity.setFechaPrestamo(fechaPrestamo);
        prestamosEntity.setFechaDevolucion(fechaDevolucion);
        PrestamosEntity updatedEntity = springDataPrestamoRepository.save(prestamosEntity);
        return toDomain(updatedEntity);
    }

    @Override
    public void delete(String id) {
        PrestamosEntity prestamosEntity = springDataPrestamoRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con id: " + id));
        springDataPrestamoRepository.delete(prestamosEntity);
    }

    @Override
     public PrestamosLibros save(PrestamosLibros prestamosLibros) {
        LibroEntity libroEntity = new LibroEntity();
        UserEntity userEntity = springDataUserRepository.findById(prestamosLibros.idUsuario()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        libroEntity=SpringDataLibroRepository.findById(prestamosLibros.idLibro()).orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        libroEntity.setCantidadDisponible(libroEntity.getCantidadDisponible() - 1);
        SpringDataLibroRepository.save(libroEntity);
                
        PrestamosEntity prestamosEntity = new PrestamosEntity(
            prestamosLibros.idPrestamo(),
            userEntity,
            libroEntity,
            prestamosLibros.estadoPrestamo(),
            prestamosLibros.fechaPrestamo(),
            prestamosLibros.fechaDevolucion()
        );
        PrestamosEntity savedEntity = springDataPrestamoRepository.save(prestamosEntity);
        return toDomain(savedEntity);
    }


    /***
     * Devolver un libro prestado, actualizando la cantidad disponible del libro y eliminando el préstamo correspondiente.
     * @param id
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public void devolverLibro(String id,String idUsuario) {
        PrestamosEntity prestamosEntity = springDataPrestamoRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con id: " + id));
        if (!prestamosEntity.isEstadoPrestamo()) {
            throw new RuntimeException("El préstamo ya fue devuelto");
        }
        if (!prestamosEntity.getIdUsuario().getId().equals(UUID.fromString(idUsuario))) {
            throw new RuntimeException("El préstamo no pertenece al usuario indicado");
        }
        LibroEntity libroEntity = prestamosEntity.getIdLibro();
        libroEntity.setCantidadDisponible(libroEntity.getCantidadDisponible() + 1);
        prestamosEntity.setEstadoPrestamo(false);
    }




    private PrestamosLibros toDomain(PrestamosEntity entity) {
        return new PrestamosLibros(
            entity.getIdPrestamo(),
            entity.getIdUsuario().getId(),
            entity.getIdLibro().getId(),
            entity.isEstadoPrestamo(),
            entity.getFechaPrestamo(),
            entity.getFechaDevolucion()
        );
    }
    
}
