package tingeso_mingeso.backendestudiantesservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentEntity {
    @Id  //define que es una id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //se inclemente automatica
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private String lastnames;
    private String names;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_of_birth;
    private String type_school;
    private String school_name;
    private int year_of_graduation;
}
