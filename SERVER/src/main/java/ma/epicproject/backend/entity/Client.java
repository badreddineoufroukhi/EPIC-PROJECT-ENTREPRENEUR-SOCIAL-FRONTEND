package ma.epicproject.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = Client.Constants.TABLE_NAME)
@DiscriminatorValue("CLIENT")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Client extends Utilisateur {


    public static class Constants {
        public static final String TABLE_NAME = "client";
    }
}