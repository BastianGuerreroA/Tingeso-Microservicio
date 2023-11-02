package tingeso_mingeso.backendcuotasservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentEntity {
    private String rut;
    private String lastnames;
    private String names;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_of_birth;
    private String type_school;
    private String school_name;
    private int year_of_graduation;
}
