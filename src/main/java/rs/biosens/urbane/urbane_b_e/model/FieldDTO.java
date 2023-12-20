package rs.biosens.urbane.urbane_b_e.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FieldDTO {

    private Integer id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String lat;

    @Size(max = 255)
    private String longg;

    @Size(max = 255)
    private String cropType;

    @Size(max = 255)
    private String hbm;

    private LocalDate createdAt;

    private Integer users;

}
