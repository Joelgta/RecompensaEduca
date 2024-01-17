package recompensaeduca.recompensaeduca.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;

@Data
@Entity
@Table(name = "colegio")
public class ColegioModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "comuna_id")
    private Integer comuna_id;

    @Column(name = "activo")
    private Integer activo;

    @CreationTimestamp
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @JsonBackReference
    @OneToMany(mappedBy = "colegio")
    private Set<CursoModel> cursos;

    @JsonBackReference
    @OneToMany(mappedBy = "colegio")
    private Set<PersonalEntity> personales;

}
