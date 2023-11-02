package tingeso_mingeso.backendestudiantesservice.repository;

import tingeso_mingeso.backendestudiantesservice.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
