package ma.epicproject.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = Ouvrier.Constants.TABLE_NAME)
@DiscriminatorValue("OUVRIER")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Ouvrier extends Utilisateur {


    @Column(name = Constants.COLUMN_PROFESSION)
    private String profession;

    @Column(name = Constants.COLUMN_RATING)
    private Integer rating;

    @Column(name = Constants.COLUMN_DESCRIPTION)
    private String description;

    @Column(name = Constants.COLUMN_NUMEROTELEPHONE_NAME)
    private String numeroTelephone;

    @Column(name = Constants.COLUMN_VILLE)
    private String ville;

    @Column(name = Constants.COLUMN_LOCALISATION)
    private String localisation;

    @Column(name = Constants.COLUMN_DESPONIBLE)
    private Boolean disponible;

    public static class Constants {
        public static final String TABLE_NAME = "ouvrier";
        public static final String COLUMN_PROFESSION = "profession";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_VILLE = "ville";
        public static final String COLUMN_NUMEROTELEPHONE_NAME = "numero_telephone";
        public static final String COLUMN_LOCALISATION = "localisation";
        public static final String COLUMN_DESPONIBLE = "disponible";

    }
}