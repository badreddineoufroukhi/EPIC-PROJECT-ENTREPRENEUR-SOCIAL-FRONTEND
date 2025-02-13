package ma.epicproject.backend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ma.epicproject.backend.common.bean.AuditBaseDto;
import ma.epicproject.backend.common.util.Utils;
import ma.epicproject.backend.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DemandeDto extends AuditBaseDto {

    private Long id;
    private Client client;
    private ServicePropose servicePropose;
    private Integer rating;

    @Enumerated(EnumType.STRING)
    private Agreement agreement;

    private String description;

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public DemandeDto(Demande demande) {
        super();
        convertToDto(this, demande, false, 0);
    }

    public DemandeDto(Demande demande, boolean collections, int level) {
        super();
        convertToDto(this, demande, collections, level);
    }

    public DemandeDto convertIdToDto(DemandeDto demandeDto, Demande demande) {
        demandeDto.setId(demande.getId());
        return demandeDto;
    }

    public Demande convertIdToEntity(Demande demande, DemandeDto demandeDto) {
        demande.setId(demandeDto.getId());
        return demande;
    }

    public DemandeDto convertToDto(DemandeDto demandeDto, Demande demande, boolean collections, int level) {
        level++;
        if (demandeDto != null && level <= maxLevel) {
            demandeDto = convertIdToDto(demandeDto, demande);
            demandeDto.setClient(demande.getClient());
            demandeDto.setServicePropose(demande.getServicePropose());
            demandeDto.setRating(demande.getRating());
            demandeDto.setAgreement(DemandeDto.Agreement.valueOf(demande.getAgreement().name()));
            demandeDto.setDescription(demande.getDescription());

            demandeDto.setCreatedBy(demande.getCreatedBy());
            demandeDto.setCreatedOn(Utils.dateTimeToString(demande.getCreatedOn()));
            demandeDto.setUpdatedBy(demande.getUpdatedBy());
            demandeDto.setUpdatedOn(Utils.dateTimeToString(demande.getUpdatedOn()));

            if (collections) {
                // Vous pouvez ajouter des collections supplémentaires ici si nécessaire
            }
        }
        return demandeDto;
    }

    public Demande convertToEntity(Demande demande, DemandeDto demandeDto) {
        if (demande != null) {
            demande = convertIdToEntity(demande, demandeDto);
            if (demandeDto.getClient().getId() != null) {
                Client client = new Client();
                client.setId(demandeDto.getClient().getId());
                demande.setClient(client);
            }
            if (demandeDto.getServicePropose().getId() != null) {
                ServicePropose servicePropose = new ServicePropose();
                servicePropose.setId(demandeDto.getServicePropose().getId());
                demande.setServicePropose(servicePropose);
            }
            demande.setRating(demandeDto.getRating());
            demande.setAgreement(Demande.Agreement.valueOf(demandeDto.getAgreement().name()));
            demande.setDescription(demandeDto.getDescription());
        }
        return demande;
    }

    public enum Agreement {
        EVALUATED, NOTEVALYATED, NOAGREEMENT
    }
}
