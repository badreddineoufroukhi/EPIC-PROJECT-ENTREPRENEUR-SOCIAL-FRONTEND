package ma.epicproject.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import ma.epicproject.backend.common.bean.AuditBusinessObject;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Getter
@Setter
@Entity
@Table(name = ServicePropose.Constants.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@GenericGenerator(name = "serviceProposeSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
        @Parameter(name = "sequence_name", value = "service_propose_seq"),
        @Parameter(name = "increment_size", value = "1") })
public class ServicePropose extends AuditBusinessObject {

    @Id
    @Column(name = Constants.COLUMN_ID_NAME)
    @GeneratedValue(generator = "serviceProposeSequenceGenerator")
    private Long id;

    @Column(name = Constants.COLUMN_NAME)
    private String name;

    @Column(name = Constants.COLUMN_RATING)
    private Integer rating;

    @Column(name = Constants.COLUMN_PRIX)
    private Double prix;

    @Column(name = Constants.COLUMN_AVATAR)
    private String avatar;

    @ManyToOne
    @JoinColumn(name = Constants.COLUMN_OUVRIER_ID)
    private Ouvrier ouvrier;

    @Column(name = Constants.COLUMN_DESCRIPTION)
    private String Description;

    public static class Constants {
        public static final String TABLE_NAME = "service_propose";
        public static final String COLUMN_ID_NAME = "id_service_propose";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_PRIX = "prix";
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_OUVRIER_ID = "ouvrier_id";
    }
}