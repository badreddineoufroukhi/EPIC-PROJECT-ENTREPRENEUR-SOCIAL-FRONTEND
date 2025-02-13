package ma.epicproject.backend.common.bean;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.Jwt;


public class EntityListener implements Serializable {

	@PrePersist
	public void prePersist(AuditBusinessObject auditBusinessObject) {
		if (auditBusinessObject.getCreatedOn() == null) {
			auditBusinessObject.setCreatedOn(LocalDateTime.now());
			auditBusinessObject.setCreatedBy(getCurrentUser());
		}
	}

	@PreUpdate
	public void preUpdate(AuditBusinessObject auditBusinessObject) {
		auditBusinessObject.setUpdatedOn(LocalDateTime.now());
		auditBusinessObject.setUpdatedBy(getCurrentUser());
	}

	public String getCurrentUser() {
//		if (SecurityContextHolder.getContext().getAuthentication() != null) {
//			Jwt subject = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			return subject.getSubject();
//		}
		return "unknown";

	}
}
