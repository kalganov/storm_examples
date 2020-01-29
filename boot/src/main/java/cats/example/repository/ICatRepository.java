package cats.example.repository;

import cats.example.entity.Cat;
import org.springframework.data.repository.CrudRepository;

public interface ICatRepository extends CrudRepository<Cat, Long> {
}
