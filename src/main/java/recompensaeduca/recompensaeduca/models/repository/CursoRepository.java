package recompensaeduca.recompensaeduca.models.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.CursoModel;

@Repository
public interface CursoRepository extends CrudRepository<CursoModel, Integer> {
    
    public List<CursoModel> findAllByActivoOrderByNombreCurso(int e);

    public CursoModel findByTipoEnsenanzaAndGradoAndLetraAndActivo(int tipoEnsenanza, int grado, String letra, int e);

    public CursoModel findById(int id);
}
