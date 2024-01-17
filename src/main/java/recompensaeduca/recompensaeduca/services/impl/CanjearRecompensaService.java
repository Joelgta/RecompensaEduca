package recompensaeduca.recompensaeduca.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.CanjearRecompensaModel;
import recompensaeduca.recompensaeduca.models.repository.CanjearRecompensaRepository;
import recompensaeduca.recompensaeduca.services.ICanjearRecompensaService;

@Service
public class CanjearRecompensaService implements ICanjearRecompensaService{

    @Autowired
    private CanjearRecompensaRepository canjearRecompensaRepository;

    @Override
    public List<CanjearRecompensaModel> buscarTodosPorEstudianteIdOrdenadoPorCanjeadoAscRecompensa_IdAsc(int e) {
        return this.canjearRecompensaRepository.findAllByEstudianteIdOrderByCanjeadoAscRecompensa_IdAsc(e);
    }
    
    @Override
    public List<Object[]> buscarTodosPorOrdenadoPorCanjeadoDescRecompensa_IdAscActualizadoEnAsc() {
        return this.canjearRecompensaRepository.findCanjearRecompensas();
    }
        
    @Override
    public CanjearRecompensaModel buscarPorId(int id) {
        return this.canjearRecompensaRepository.findById(id);
    }
        
    @Override
    public boolean guardar(CanjearRecompensaModel canjearRecompensa) {
        try {
            if(canjearRecompensa != null){
                this.canjearRecompensaRepository.save(canjearRecompensa);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
