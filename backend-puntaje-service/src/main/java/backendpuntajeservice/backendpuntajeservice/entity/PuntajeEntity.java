package backendpuntajeservice.backendpuntajeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "puntaje")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PuntajeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaExamen;
    private int puntaje;
}
