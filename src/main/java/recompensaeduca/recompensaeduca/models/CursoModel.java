package recompensaeduca.recompensaeduca.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;

@Data
@Entity
@Table(name = "curso")
public class CursoModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_ensenanza")
    private Integer tipoEnsenanza;

    @Column(name = "grado")
    private Integer grado;

    @Column(name = "letra")
    private String letra;

    @Column(name = "nombre_curso")
    private String nombreCurso;

    @Column(name = "activo")
    private Integer activo;

    @CreationTimestamp
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @OneToMany(mappedBy = "curso")
    private Set<EstudianteEntity> estudiantes;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "colegio_id")
    private ColegioModel colegio;
    
}
