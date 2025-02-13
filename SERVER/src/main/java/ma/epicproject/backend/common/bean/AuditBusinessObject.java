package ma.epicproject.backend.common.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

/**
 * Classe mère abstraite de tous les Objets métier.
 *
 * @author JAF
 * @version 1.2
 */
@MappedSuperclass
@EntityListeners(EntityListener.class)
public class AuditBusinessObject extends BusinessObject {

    /** created on */
    @JsonProperty(access = Access.READ_ONLY)
    @Column(name="createdon")
    private LocalDateTime createdOn;

    /** updated on */
    @JsonProperty(access = Access.READ_ONLY)
    @Column(name="updatedon")
    private LocalDateTime updatedOn;

    /** created by */
    @JsonProperty(access = Access.READ_ONLY)
    @Column(name="createdby")
    private String createdBy;

    /** updated by */
    @JsonProperty(access = Access.READ_ONLY)
    @Column(name="updatedby")
    private String updatedBy;

    /**
     * Constructeur par défaut.
     */
    public AuditBusinessObject() {
        super();
    }

    public AuditBusinessObject(Long id) {
        super(id);
    }

    /**
     * created date
     *
     * @return LocalDateTime
     */
    @Column(name="createdon")
    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    /**
     * created date
     *
     * @param createdOn
     */
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * updated date
     *
     * @return LocalDateTime
     */
    @Column(name = "updatedon")
    public LocalDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    /**
     * updated date
     *
     * @param updatedOn
     */
    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * created user
     *
     * @return String
     */
    @Column(name="createdby") // Mise à jour ici
    public String getCreatedBy() {
        return this.createdBy != null ? this.createdBy : "";
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
    @Column(name = "updatedby")
    public String getUpdatedBy() {
        return this.updatedBy != null ? this.updatedBy : "";
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
