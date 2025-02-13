package ma.epicproject.backend.criteria;

import ma.epicproject.backend.common.bean.BaseCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurCriteria extends BaseCriteria {
    private String nom;
    private String nomLike;
    private String prenom;
    private String prenomLike;
    private String email;
    private String emailLike;
    private String role;
}
