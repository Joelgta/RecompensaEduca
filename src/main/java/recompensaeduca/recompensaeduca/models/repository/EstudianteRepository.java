package recompensaeduca.recompensaeduca.models.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;

@Repository
public interface EstudianteRepository extends CrudRepository<EstudianteEntity, Integer> {
    
    public EstudianteEntity findByNombreUsuarioAndActivo(String nombreUsuario, int e);

    public EstudianteEntity findByRutAndActivo(String rut, int e);

    public List<EstudianteEntity> findAllByActivoOrderByCursoId_NombreCurso(int e);

    public EstudianteEntity findByIdAndActivo(int id, int e);
}
