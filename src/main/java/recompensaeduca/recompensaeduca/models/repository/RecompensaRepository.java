package recompensaeduca.recompensaeduca.models.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.RecompensaModel;

@Repository
public interface RecompensaRepository extends CrudRepository<RecompensaModel, Integer> {
    
    public List<RecompensaModel> findAllByActivo(int e);

    public RecompensaModel findByNombreAndActivo(String nombre, int e);

    public RecompensaModel findByIdAndActivo(int id, int e);
}
