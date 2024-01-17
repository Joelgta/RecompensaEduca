package recompensaeduca.recompensaeduca.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.CursoModel;
import recompensaeduca.recompensaeduca.models.repository.CursoRepository;
import recompensaeduca.recompensaeduca.services.ICursoService;

@Service
public class CursoService implements ICursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public List<CursoModel> buscarCursosPorActivoOrdenadoPorNombreCurso(int e) 
    {
        return this.cursoRepository.findAllByActivoOrderByNombreCurso(e);
    }
    
    @Override
    public CursoModel buscarCurso(int id) 
    {
        return this.cursoRepository.findById(id);
    }

    @Override
    public CursoModel buscarCursoPorTipoYGradoYLetraPorActivo(int tipoEnsenanza, int grado, String letra, int e) 
    {
        return this.cursoRepository.findByTipoEnsenanzaAndGradoAndLetraAndActivo(tipoEnsenanza, grado, letra, e);
    }

    @Override
    public boolean guardarCurso(CursoModel curso) 
    {
        try {
            if(curso != null){
                this.cursoRepository.save(curso);    
            }else{
                throw new Exception("Clase CursoModel null");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }    
}
