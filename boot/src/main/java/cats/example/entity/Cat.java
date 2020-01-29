package cats.example.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cat")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Long id;

    @Column(name = "catname")
    private String name;

    @Column(name = "weight")
    private Float weight;

    public Cat() {
    }

    public Cat(String name, Float weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(name, cat.name) &&
                Objects.equals(weight, cat.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight);
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
}
