package recompensaeduca.recompensaeduca.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;

@Data
@Entity
@Table(name = "puntaje_transacciones")
public class PuntajeTransaccionesModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    @Column(name = "estudiante_id")
    private Integer estudianteId;

    @Column(name = "puntaje_traspaso")
    private Integer puntajeTraspaso;

    @Column(name = "validado")
    private Integer validado;

    @Column(name = "detalle")
    private String detalle;

    @CreationTimestamp
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "personal_id")
    private PersonalEntity personal;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "concepto_id")
    private ConceptoModel concepto;
}
