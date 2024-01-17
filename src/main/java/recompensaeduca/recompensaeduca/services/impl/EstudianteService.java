package recompensaeduca.recompensaeduca.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;
import recompensaeduca.recompensaeduca.models.repository.EstudianteRepository;
import recompensaeduca.recompensaeduca.services.IEstudianteService;

@Service
public class EstudianteService implements IEstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    public EstudianteEntity buscarPorNombreUsuarioYActivo(String nombreUsuario, int e) {
        return this.estudianteRepository.findByNombreUsuarioAndActivo(nombreUsuario, e);
    }

    @Override
    public List<EstudianteEntity> buscarTodosPorActivoOrdenarPorCurso_NombreCurso(int e) {
        return this.estudianteRepository.findAllByActivoOrderByCursoId_NombreCurso(e);
    }

    @Override
    public EstudianteEntity buscarPorRutYActivo(String rut, int e) {
        return this.estudianteRepository.findByRutAndActivo(rut, e);
    }
    
    @Override
    public EstudianteEntity buscarPorIdYActivo(int id, int e) {
        return this.estudianteRepository.findByIdAndActivo(id, e);
    }
    
    @Override
    public boolean guardar(EstudianteEntity estudiante) {

        try {
            if(estudiante != null){
                this.estudianteRepository.save(estudiante);
            }else{
                throw new Exception("Clase CursoModel null");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
