package recompensaeduca.recompensaeduca.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;

@Repository
public interface PersonalRepository extends CrudRepository<PersonalEntity, Integer> {
    
    public PersonalEntity findByNombreUsuarioAndActivo(String nombreUsuario, int e);

    public List<PersonalEntity> findAllByActivo(int e);

    public PersonalEntity findByIdAndActivo(int id, int e);

    public PersonalEntity findByRutAndActivo(String rut, int e);

    public PersonalEntity findByRutAndNombreUsuarioAndActivo(String rut, String nombreUsuario, int e);

    @Query("SELECT personal, perfil FROM PersonalEntity as personal JOIN PerfilModel as perfil ON ( perfil.nombre = :nombrePerfil AND personal.perfilId = perfil.id )")
    public List<PersonalEntity> buscarPorNombrePerfil(String nombrePerfil);
}
