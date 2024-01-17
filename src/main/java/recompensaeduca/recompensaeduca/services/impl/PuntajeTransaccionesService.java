package recompensaeduca.recompensaeduca.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.PuntajeTransaccionesModel;
import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;
import recompensaeduca.recompensaeduca.models.repository.PuntajeTransaccionesRepository;
import recompensaeduca.recompensaeduca.services.IPuntajeTransaccionesService;

@Service
public class PuntajeTransaccionesService implements IPuntajeTransaccionesService {

    @Autowired
    private PuntajeTransaccionesRepository puntajeTransaccionesRepository;

    @Override
    public List<Object[]> buscarTodosPorOrdenadoPorValidadoAscPersonal_ApellidoPaternoAsc() {
        return this.puntajeTransaccionesRepository.findTransacciones();
    }

    @Override
    public List<Object[]> buscarTodosPorPersonalOrdenadoPorValidadoAscPersonal_ApellidoPaternoAsc(PersonalEntity personal) {
        return this.puntajeTransaccionesRepository.findTransaccionesPorPersonal(personal);
    }

    @Override
    public PuntajeTransaccionesModel buscarPorEstudianteIdYValidado(int id, int v) {
        return this.puntajeTransaccionesRepository.findByEstudianteIdAndValidado(id, v);
    }
    
    @Override
    public PuntajeTransaccionesModel buscarPorId(int id) {
        return this.puntajeTransaccionesRepository.findById(id);
    }

    @Override
    public Object buscarMetricaGlobal(){
        return this.puntajeTransaccionesRepository.findMetricaGlobal();
    }
    
    @Override
    public boolean guardar(PuntajeTransaccionesModel puntajeTransacciones) 
    {
        try {
            if(puntajeTransacciones != null){
                this.puntajeTransaccionesRepository.save(puntajeTransacciones);
            }else{
                throw new Exception("Clase CursoModel null");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
