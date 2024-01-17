package recompensaeduca.recompensaeduca.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.RecompensaModel;
import recompensaeduca.recompensaeduca.models.repository.RecompensaRepository;
import recompensaeduca.recompensaeduca.services.IRecompensaService;

@Service
public class RecompensaService implements IRecompensaService {

    @Autowired
    private RecompensaRepository recompensaRepository;

    @Override
    public List<RecompensaModel> buscarTodosPorActivo(int e) {
        return this.recompensaRepository.findAllByActivo(e);
    }

    @Override
    public RecompensaModel buscarPorNombreYActivo(String nombre, int e) {
        return this.recompensaRepository.findByNombreAndActivo(nombre, e);
    }
    
    @Override
    public RecompensaModel buscarPorIdYActivo(int id, int e) {
        return this.recompensaRepository.findByIdAndActivo(id, e);
    }
    
    @Override
    public boolean guardar(RecompensaModel recompensa) {

        try {
            if(recompensa != null){
                this.recompensaRepository.save(recompensa);
            }else{
                throw new Exception("Clase CursoModel null");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
}
