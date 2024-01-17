package recompensaeduca.recompensaeduca.models.entities;

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
import recompensaeduca.recompensaeduca.models.ColegioModel;
import recompensaeduca.recompensaeduca.models.PuntajeTransaccionesModel;

@Data
@Entity
@Table(name = "personal")
public class PersonalEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "rut")
    private String rut;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "perfil_id")
    private Integer perfilId;

    @Column(name = "puntaje")
    private Integer puntaje;
    
    @Column(name = "activo")
    private Integer activo;
    
    @CreationTimestamp
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;
    
    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "colegio_id")
    private ColegioModel colegio;

    @OneToMany(mappedBy = "personal")
    private Set<PuntajeTransaccionesModel> puntajesTransacciones;
}
