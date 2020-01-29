package cats.example.service;

import cats.example.entity.Cat;

public interface ICatService {
    Long getCatsCount();

    Cat saveCat(Cat cat);
}
