package cats.example.service;

import cats.example.entity.Cat;
import cats.example.repository.ICatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatService implements ICatService {

    private ICatRepository catRepository;

    public CatService(ICatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Transactional
    public Long getCatsCount() {
        return catRepository.count();
    }

    @Transactional
    public Cat saveCat(Cat cat) {
        return catRepository.save(cat);
    }
}
