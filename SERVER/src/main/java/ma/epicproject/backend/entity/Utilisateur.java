package ma.epicproject.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import ma.epicproject.backend.common.bean.AuditBusinessObject;
import ma.epicproject.backend.dto.UtilisateurDto;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;



@Getter
@Setter
@Entity
@Table(name = Utilisateur.Constants.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@GenericGenerator(name = "userSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
        @Parameter(name = "sequence_name", value = "user_seq"),
        @Parameter(name = "increment_size", value = "1") })
public class Utilisateur extends AuditBusinessObject{

    @Id
    @Column(name = Constants.COLUMN_ID_NAME)
    @GeneratedValue(generator = "userSequenceGenerator")
    private Long id;

    @Column(name = Constants.COLUMN_NOM_NAME, nullable = false)
    private String nom;

    @Column(name = Constants.COLUMN_PRENOM_NAME, nullable = false)
    private String prenom;

    @Column(name = Constants.COLUMN_EMAIL_NAME, nullable = false, unique = true)
    private String email;

    @Column(name = Constants.COLUMN_MOTDEPASSE_NAME, nullable = false)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(name = Constants.COLUMN_ROLE_NAME, nullable = false)
    private Role role;

    @Column(name = Constants.COLUMN_AVATAR)
    private String avatar;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static class Constants {
        public static final String TABLE_NAME = "utilisateur";
        public static final String COLUMN_ID_NAME = "id_utilisateur";
        public static final String COLUMN_NOM_NAME = "nom";
        public static final String COLUMN_PRENOM_NAME = "prenom";
        public static final String COLUMN_EMAIL_NAME = "email";
        public static final String COLUMN_MOTDEPASSE_NAME = "mot_de_passe";
        public static final String COLUMN_ROLE_NAME = "role";
        public static final String COLUMN_AVATAR = "avatar";
    }

    public enum Role {
        ADMIN, CLIENT, OUVRIER
    }
}
