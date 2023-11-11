package tingeso_mingeso.backendestudiantesservice.controller;

import tingeso_mingeso.backendestudiantesservice.entity.StudentEntity;
import tingeso_mingeso.backendestudiantesservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentEntity>> getAll() {
        List<StudentEntity> students = studentService.getAll();
        if(students.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEntity> getById(@PathVariable("id") Long id) {
        StudentEntity student = studentService.getStudentById(id);
        if(student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @PostMapping()
    public ResponseEntity<StudentEntity> save(@RequestBody StudentEntity student) {
        StudentEntity studentNew = studentService.save(student);
        return ResponseEntity.ok(studentNew);
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        studentService.eliminarUsuario(id);
        return ResponseEntity.ok("Eliminado").toString();
    }
    
}
