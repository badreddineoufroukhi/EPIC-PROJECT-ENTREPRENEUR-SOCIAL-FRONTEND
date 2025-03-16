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
@Table(name = Demande.Constants.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@GenericGenerator(name = "demandeSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
        @Parameter(name = "sequence_name", value = "demande_seq"),
        @Parameter(name = "increment_size", value = "1") })
public class Demande extends AuditBusinessObject {

    @Id
    @Column(name = Constants.COLUMN_ID_NAME)
    @GeneratedValue(generator = "demandeSequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = Constants.COLUMN_CLIENT_ID, nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = Constants.COLUMN_SERVICEPROPOSE_ID, nullable = false)
    private ServicePropose servicePropose;

    @Column(name = Constants.COLUMN_RATING)
    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(name = Constants.COLUMN_AGREEMENT)
    private Agreement agreement;

    @Column(name = Constants.COLUMN_DESCRIPTION)
    private String description;

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public static class Constants {
        public static final String TABLE_NAME = "demande";
        public static final String COLUMN_ID_NAME = "id_demande";
        public static final String COLUMN_CLIENT_ID = "client_id";
        public static final String COLUMN_SERVICEPROPOSE_ID = "service_propose_id";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AGREEMENT = "agreement";
    }

    public enum Agreement {
        EVALUATED, NOTEVALYATED, NOAGREEMENT
    }
}
