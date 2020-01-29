package cats.example.controller;

import cats.example.dto.CatDTO;
import cats.example.entity.Cat;
import cats.example.service.ICatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cat")
public class CatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatController.class);

    private ICatService catService;

    public CatController(ICatService catService) {
        this.catService = catService;
    }

    @GetMapping("/count")
    public Long countCats() {
        return catService.getCatsCount();
    }

    @PostMapping("/save")
    @ResponseBody
    public Cat saveCat(@RequestBody CatDTO cat) {
        LOGGER.info("Creating a cat: [{}]", cat);
        return catService.saveCat(new Cat(cat.getName(), cat.getWeight()));
    }
}
