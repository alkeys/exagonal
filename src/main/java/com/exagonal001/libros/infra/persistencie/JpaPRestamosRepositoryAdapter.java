package com.exagonal001.libros.infra.persistencie;

import org.springframework.stereotype.Repository;

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

  

    public JpaPRestamosRepositoryAdapter(SpringDataPrestamoRepository springDataPrestamoRepository,
            SpringDataUserRepository springDataUserRepository) {
        this.springDataPrestamoRepository = springDataPrestamoRepository;
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
     public PrestamosLibros save(PrestamosLibros prestamosLibros) {
        LibroEntity libroEntity = new LibroEntity();
        UserEntity userEntity = springDataUserRepository.findById(prestamosLibros.idUsuario()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        libroEntity.setId(prestamosLibros.idLibro());
        
        PrestamosEntity prestamosEntity = new PrestamosEntity(
            prestamosLibros.idPrestamo(),
            userEntity,
            libroEntity,
            prestamosLibros.fechaPrestamo(),
            prestamosLibros.fechaDevolucion()
        );
        PrestamosEntity savedEntity = springDataPrestamoRepository.save(prestamosEntity);
        return new PrestamosLibros(
            savedEntity.getIdPrestamo(),
            savedEntity.getIdUsuario().getId(),
            savedEntity.getIdLibro().getId(),
            savedEntity.getFechaPrestamo(),
            savedEntity.getFechaDevolucion()

        );        
        
    }
    
}
