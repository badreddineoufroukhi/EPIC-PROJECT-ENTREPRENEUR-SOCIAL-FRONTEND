package ma.epicproject.backend.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OuvrierCriteria extends UtilisateurCriteria {
    private String profession;
    private String professionLike;

    private Integer rating;

    private String description;
    private String descriptionLike;

    private String numeroTelephone;
    private String numeroTelephoneLike;

    private String ville;
    private String villeLike;

    private String localisation;
    private String localisationLike;
    private String disponible;

}
