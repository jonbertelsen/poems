package dat.entities;

import dat.dtos.PoemDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="title", length = 100, nullable = false)
    private String title;
    @Column(name="poem", length = 1000, nullable = false)
    private String poem;
    @Column(name="style", length = 30, nullable = false)
    private String style;

    // make a constructor that takes a PoemDTO object as parameter and initializes the fields.
    public Poem(PoemDTO poemDTO){
        this.id = poemDTO.getId();
        this.title = poemDTO.getTitle();
        this.poem = poemDTO.getPoem();
        this.style = poemDTO.getStyle();
    }


}
