package recompensaeduca.recompensaeduca.services;

import java.util.List;

import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;

public interface IPersonalService {
    
    public PersonalEntity buscarPorNombreUsuarioYActivo(String nombreUsuario, int e);

    public List<PersonalEntity> buscarTodosPorActivo(int e);

    public PersonalEntity buscarPorIdYActivo(int id, int e);

    public PersonalEntity buscarPorRutYActivo(String rut, int e);

    public PersonalEntity buscarPorRutYNombreUsuarioYActivo(String rut, String nombreUsuario, int e);

    public void reiniciarPuntajes();

    public boolean guardar(PersonalEntity personal);

    public boolean guardarTodos(List<PersonalEntity> personal);
}
