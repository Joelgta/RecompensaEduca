package recompensaeduca.recompensaeduca.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;
import recompensaeduca.recompensaeduca.models.repository.PersonalRepository;
import recompensaeduca.recompensaeduca.services.IPersonalService;

@Service
public class PersonalService implements IPersonalService {

    @Autowired
    private PersonalRepository personalRepository;

    @Override
    public PersonalEntity buscarPorNombreUsuarioYActivo(String nombreUsuario, int e) {
        return this.personalRepository.findByNombreUsuarioAndActivo(nombreUsuario,e);
    }
    
    @Override
    public List<PersonalEntity> buscarTodosPorActivo(int e) {
        return this.personalRepository.findAllByActivo(e);
    }
    
    @Override
    public PersonalEntity buscarPorIdYActivo(int id, int e) {
        return this.personalRepository.findByIdAndActivo(id, e);
    }
    
    @Override
    public PersonalEntity buscarPorRutYActivo(String rut, int e) {
        return this.personalRepository.findByRutAndActivo(rut, e);
    }
    
    @Override
    public PersonalEntity buscarPorRutYNombreUsuarioYActivo(String rut, String nombreUsuario, int e) {
        return this.personalRepository.findByRutAndNombreUsuarioAndActivo(rut, nombreUsuario, e);
    }

    @Override
    public void reiniciarPuntajes() {
        List<PersonalEntity> docentes = this.personalRepository.buscarPorNombrePerfil("docente");
        for (PersonalEntity docente : docentes) {
            docente.setPuntaje(30000); // Asumiendo que 'puntaje' es el campo a reiniciar
        }
        this.guardarTodos(docentes);
    }
    
    @Override
    public boolean guardar(PersonalEntity personal) {

        try {
            if(personal != null){
                this.personalRepository.save(personal);    
            }else{
                throw new Exception("Clase CursoModel null");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean guardarTodos(List<PersonalEntity> personal) {
        
        try {
            if(personal != null){
                this.personalRepository.saveAll(personal);    
            }else{
                throw new Exception("Clase CursoModel null");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
