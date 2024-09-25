package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.entities.Poem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoemDTO {
    private Integer id;
    private String title;
    private String poem;
    private String style;

    public PoemDTO(Poem poem){
        this.id = poem.getId();
        this.title = poem.getTitle();
        this.poem = poem.getPoem();
        this.style = poem.getStyle();
    }

    public PoemDTO(String title, String poem, String style){
        this.title = title;
        this.poem = poem;
        this.style = style;
    }

    public static List<PoemDTO> toDTOList(List<Poem> poems){
        return poems.stream().map(PoemDTO::new).toList();
    }
}
