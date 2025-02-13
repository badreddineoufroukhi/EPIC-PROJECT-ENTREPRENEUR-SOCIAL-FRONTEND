package ma.epicproject.backend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ma.epicproject.backend.common.util.Utils;
import ma.epicproject.backend.entity.Client;
import ma.epicproject.backend.entity.Utilisateur;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto extends UtilisateurDto {



    public ClientDto(Client client) {
        super(client);
    }

    public ClientDto(Client client, boolean collections, int level) {
        super(client, collections, level);
    }

    public Client convertToEntity(Client client, ClientDto clientDto) {
        if (client != null) {
            client = (Client) super.convertToEntity(client, clientDto);
        }
        return client;
    }


    public ClientDto convertToDto(ClientDto clientDto, Client client, boolean collections, int level) {
        clientDto = (ClientDto) super.convertToDto(clientDto, client, collections, level); // Appelle la méthode de la classe parente

        return clientDto;
    }


}
