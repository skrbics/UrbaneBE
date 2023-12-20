package rs.biosens.urbane.urbane_b_e.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Integer id;

    @Size(max = 255)
    private String phone;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String role;

    @Size(max = 255)
    private String country;

    @Size(max = 255)
    private String climateZone;

    private LocalDate createdAt;

}
