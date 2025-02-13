package ma.epicproject.backend.dto;

import ma.epicproject.backend.common.util.Utils;
import ma.epicproject.backend.entity.Client;
import ma.epicproject.backend.entity.Ouvrier;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OuvrierDto extends UtilisateurDto {

    private String profession;
    private String localisation;
    private String numeroTelephone;
    private Integer rating;
    private String ville;
    private String Description;
    private Boolean disponible;


    public OuvrierDto(Ouvrier ouvrier) {
        super(ouvrier);
        convertToDto(this, ouvrier, false, 0);
    }

    public OuvrierDto(Ouvrier ouvrier, boolean collections, int level) {
        super(ouvrier, collections, level);
        convertToDto(this, ouvrier, collections, level);
    }

    public OuvrierDto convertIdToDto(OuvrierDto ouvrierDto, Ouvrier ouvrier) {
        super.convertIdToDto(ouvrierDto, ouvrier); // Appel au convertisseur de UtilisateurDto
        return ouvrierDto;
    }

    public Ouvrier convertIdToEntity(Ouvrier ouvrier, OuvrierDto ouvrierDto) {
        super.convertIdToEntity(ouvrier, ouvrierDto); // Appel au convertisseur de UtilisateurDto
        return ouvrier;
    }

    public OuvrierDto convertToDto(OuvrierDto ouvrierDto, Ouvrier ouvrier, boolean collections, int level) {
        ouvrierDto = (OuvrierDto) super.convertToDto(ouvrierDto, ouvrier, collections, level);
        level++;
        if (ouvrierDto != null && level <= maxLevel) {
            ouvrierDto = convertIdToDto(ouvrierDto, ouvrier);
            ouvrierDto.setProfession(ouvrier.getProfession());
            ouvrierDto.setLocalisation(ouvrier.getLocalisation());
            ouvrierDto.setNumeroTelephone(ouvrier.getNumeroTelephone());
            ouvrierDto.setDescription(ouvrier.getDescription());
            ouvrierDto.setRating(ouvrier.getRating());
            ouvrierDto.setVille(ouvrier.getVille());
            ouvrierDto.setDisponible(ouvrier.getDisponible());

            ouvrierDto.setCreatedBy(ouvrier.getCreatedBy());
            ouvrierDto.setCreatedOn(Utils.dateTimeToString(ouvrier.getCreatedOn()));
            ouvrierDto.setUpdatedBy(ouvrier.getUpdatedBy());
            ouvrierDto.setUpdatedOn(Utils.dateTimeToString(ouvrier.getUpdatedOn()));

            if (collections) {
                // Collection handling, if needed
            }
        }
        return ouvrierDto;
    }

    public Ouvrier convertToEntity(Ouvrier ouvrier, OuvrierDto ouvrierDto) {
        if (ouvrier != null) {
            ouvrier = (Ouvrier) super.convertToEntity(ouvrier, ouvrierDto);
            ouvrier = convertIdToEntity(ouvrier, ouvrierDto);
            ouvrier.setProfession(ouvrierDto.getProfession());
            ouvrier.setLocalisation(ouvrierDto.getLocalisation());
            ouvrier.setDescription(ouvrierDto.getDescription());
            ouvrier.setNumeroTelephone(ouvrierDto.getNumeroTelephone());
            ouvrier.setRating(ouvrierDto.getRating());
            ouvrier.setVille(ouvrierDto.getVille());
            ouvrier.setDisponible(ouvrierDto.getDisponible());

        }
        return ouvrier;
    }
}
