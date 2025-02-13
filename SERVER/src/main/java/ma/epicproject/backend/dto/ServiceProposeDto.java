package ma.epicproject.backend.dto;

import ma.epicproject.backend.common.bean.AuditBaseDto;
import ma.epicproject.backend.common.util.Utils;
import ma.epicproject.backend.entity.ServicePropose;
import ma.epicproject.backend.entity.Ouvrier;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceProposeDto extends AuditBaseDto {

    private Long id;
    private String name;
    private Integer rating;
    private Double prix;
    private String avatar;
    private String description;
    private Ouvrier ouvrier;  // Remplacé par Ouvrier

    public ServiceProposeDto(ServicePropose servicePropose) {
        super();
        convertToDto(this, servicePropose, false, 0);
    }

    public ServiceProposeDto(ServicePropose servicePropose, boolean collections, int level) {
        super();
        convertToDto(this, servicePropose, collections, level);
    }

    public ServiceProposeDto convertIdToDto(ServiceProposeDto serviceProposeDto, ServicePropose servicePropose) {
        serviceProposeDto.setId(servicePropose.getId());
        return serviceProposeDto;
    }

    public ServicePropose convertIdToEntity(ServicePropose servicePropose, ServiceProposeDto serviceProposeDto) {
        servicePropose.setId(serviceProposeDto.getId());
        return servicePropose;
    }

    public ServiceProposeDto convertToDto(ServiceProposeDto serviceProposeDto, ServicePropose servicePropose, boolean collections, int level) {
        level++;
        if (serviceProposeDto != null && level <= maxLevel) {
            serviceProposeDto = convertIdToDto(serviceProposeDto, servicePropose);
            serviceProposeDto.setName(servicePropose.getName());
            serviceProposeDto.setRating(servicePropose.getRating());
            serviceProposeDto.setPrix(servicePropose.getPrix());
            serviceProposeDto.setAvatar(servicePropose.getAvatar());
            serviceProposeDto.setDescription(servicePropose.getDescription());
            serviceProposeDto.setOuvrier(servicePropose.getOuvrier());  // Référence à l'ouvrier

            serviceProposeDto.setCreatedBy(servicePropose.getCreatedBy());
            serviceProposeDto.setCreatedOn(Utils.dateTimeToString(servicePropose.getCreatedOn()));
            serviceProposeDto.setUpdatedBy(servicePropose.getUpdatedBy());
            serviceProposeDto.setUpdatedOn(Utils.dateTimeToString(servicePropose.getUpdatedOn()));

            if (collections) {
                // Optionnellement, gérez les collections ici
            }
        }
        return serviceProposeDto;
    }

    public ServicePropose convertToEntity(ServicePropose servicePropose, ServiceProposeDto serviceProposeDto) {
        if (servicePropose != null) {
            servicePropose = convertIdToEntity(servicePropose, serviceProposeDto);
            servicePropose.setName(serviceProposeDto.getName());
            servicePropose.setRating(serviceProposeDto.getRating());
            servicePropose.setPrix(serviceProposeDto.getPrix());
            servicePropose.setAvatar(serviceProposeDto.getAvatar());
            servicePropose.setDescription(serviceProposeDto.getDescription());
            servicePropose.setOuvrier(serviceProposeDto.getOuvrier());  // Assurez-vous d'attribuer l'ouvrier
            if (serviceProposeDto.getOuvrier().getId() != null) {
                Ouvrier ouvrier = new Ouvrier();
                ouvrier.setId(serviceProposeDto.getOuvrier().getId());
                servicePropose.setOuvrier(ouvrier);
            }
        }
        return servicePropose;
    }
}
