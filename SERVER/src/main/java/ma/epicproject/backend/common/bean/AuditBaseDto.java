package ma.epicproject.backend.common.bean;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;


@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class AuditBaseDto extends BaseDto {


    /** created on */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String createdOn;

    /** updated on */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String updatedOn;

    /** created by */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String createdBy;

    /** updated by */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String updatedBy;

    @JsonIgnore
    protected int maxLevel = 2;

    /**
     * Constructeur par défaut.
     */
    public AuditBaseDto() {
    }

    public AuditBaseDto(Long id) {
        super(id);
    }

    /**
     * created date
     *
     * @return String
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * created date
     *
     * @param createOn
     */
    public void setCreatedOn(String createOn) {
        this.createdOn = createOn;
    }

    /**
     * updated date
     *
     * @return String
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getUpdatedOn() {
        return updatedOn;
    }

    /**
     * updated date
     *
     * @param updatedOn
     */
    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * created user
     *
     * @return String
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getCreatedBy() {
        return createdBy != null ? createdBy : "";
    }

    /**
     * created user
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * updated user
     *
     * @return String
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getUpdatedBy() {
        return updatedBy != null ? updatedBy : "";
    }

    /**
     * updated user
     *
     * @param updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


}
