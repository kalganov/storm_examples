package cats.example.dto;

import java.util.Objects;

public class CatDTO {

    private String name;
    private Float weight;

    @Override
    public String toString() {
        return "CatDTO{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatDTO catDTO = (CatDTO) o;
        return Objects.equals(name, catDTO.name) &&
                Objects.equals(weight, catDTO.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight);
    }
}
