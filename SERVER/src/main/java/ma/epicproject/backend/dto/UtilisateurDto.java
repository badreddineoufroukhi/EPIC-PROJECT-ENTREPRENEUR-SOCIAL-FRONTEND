package ma.epicproject.backend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ma.epicproject.backend.common.bean.AuditBaseDto;
import ma.epicproject.backend.common.util.Utils;
import ma.epicproject.backend.entity.Utilisateur;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UtilisateurDto extends AuditBaseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UtilisateurDto(Utilisateur utilisateur) {
        super();
        convertToDto(this, utilisateur, false, 0);
    }

    public UtilisateurDto(Utilisateur utilisateur, boolean collections, int level) {
        super();
        convertToDto(this, utilisateur, collections, level);
    }

    public UtilisateurDto convertIdToDto(UtilisateurDto utilisateurDto, Utilisateur utilisateur) {
        utilisateurDto.setId(utilisateur.getId());
        return utilisateurDto;
    }

    public Utilisateur convertIdToEntity(Utilisateur utilisateur, UtilisateurDto utilisateurDto) {
        utilisateur.setId(utilisateurDto.getId());
        return utilisateur;
    }

    public UtilisateurDto convertToDto(UtilisateurDto utilisateurDto, Utilisateur utilisateur, boolean collections, int level) {
        level++;
        if (utilisateurDto != null && level <= maxLevel) {
            utilisateurDto = convertIdToDto(utilisateurDto, utilisateur);
            utilisateurDto.setNom(utilisateur.getNom());
            utilisateurDto.setPrenom(utilisateur.getPrenom());
            utilisateurDto.setEmail(utilisateur.getEmail());
            utilisateurDto.setAvatar(utilisateur.getAvatar());
            utilisateurDto.setRole(UtilisateurDto.Role.valueOf(utilisateur.getRole().name()));

            utilisateurDto.setCreatedBy(utilisateur.getCreatedBy());
            utilisateurDto.setCreatedOn(Utils.dateTimeToString(utilisateur.getCreatedOn()));
            utilisateurDto.setUpdatedBy(utilisateur.getUpdatedBy());
            utilisateurDto.setUpdatedOn(Utils.dateTimeToString(utilisateur.getUpdatedOn()));

            if (collections) {
                // Ajouter les relations si nécessaire
            }
        }
        return utilisateurDto;
    }

    public Utilisateur convertToEntity(Utilisateur utilisateur, UtilisateurDto utilisateurDto) {
        if (utilisateur != null) {
            utilisateur = convertIdToEntity(utilisateur, utilisateurDto);
            utilisateur.setNom(utilisateurDto.getNom());
            utilisateur.setPrenom(utilisateurDto.getPrenom());
            utilisateur.setEmail(utilisateurDto.getEmail());
            utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
            utilisateur.setAvatar(utilisateurDto.getAvatar());

            // Gérer l'enum Role
            utilisateur.setRole(Utilisateur.Role.valueOf(utilisateurDto.getRole().name()));
        }
        return utilisateur;
    }

    public enum Role {
        ADMIN, CLIENT, OUVRIER
    }
}
