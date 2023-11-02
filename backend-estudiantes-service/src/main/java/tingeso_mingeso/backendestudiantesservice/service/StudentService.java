package tingeso_mingeso.backendestudiantesservice.service;

import tingeso_mingeso.backendestudiantesservice.entity.StudentEntity;
import tingeso_mingeso.backendestudiantesservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<StudentEntity> getAll() {
        return studentRepository.findAll();
    }

    public StudentEntity getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public StudentEntity save(StudentEntity student) {
        StudentEntity studentNew = studentRepository.save(student);
        return studentNew;
    }

    public void eliminarUsuario(Long id) {
        studentRepository.deleteById(id);
    }




}
