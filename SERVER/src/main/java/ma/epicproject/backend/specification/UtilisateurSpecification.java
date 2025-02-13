package ma.epicproject.backend.specification;

import ma.epicproject.backend.criteria.UtilisateurCriteria;
import ma.epicproject.backend.entity.Utilisateur;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurSpecification implements Specification<Utilisateur> {
    private UtilisateurCriteria utilisateurCriteria;
    private boolean distinct;

    public UtilisateurSpecification(UtilisateurCriteria criteria) {
        this.utilisateurCriteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Utilisateur> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (utilisateurCriteria != null) {
            if (utilisateurCriteria.getId() != null && utilisateurCriteria.getId() > 0) {
                predicates.add(builder.equal(root.<String>get("id"), utilisateurCriteria.getId()));
            }
            if (utilisateurCriteria.getIdsIn() != null && !utilisateurCriteria.getIdsIn().isEmpty()) {
                predicates.add(root.<Long>get("id").in(utilisateurCriteria.getIdsIn()));
            }
            if (utilisateurCriteria.getIdsNotIn() != null && !utilisateurCriteria.getIdsNotIn().isEmpty()) {
                predicates.add(builder.not(root.<Long>get("id").in(utilisateurCriteria.getIdsNotIn())));
            }
            if (utilisateurCriteria.getNotId() != null && utilisateurCriteria.getNotId() > 0) {
                predicates.add(builder.notEqual(root.<Boolean>get("id"), utilisateurCriteria.getNotId()));
            }
            if (utilisateurCriteria.getNom() != null && !utilisateurCriteria.getNom().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("nom"), utilisateurCriteria.getNom()));
            }
            if (utilisateurCriteria.getNomLike() != null && !utilisateurCriteria.getNomLike().isEmpty()) {
                Expression<String> path = root.<String>get("nom");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + utilisateurCriteria.getNomLike().toLowerCase() + "%"));
            }
            if (utilisateurCriteria.getPrenom() != null && !utilisateurCriteria.getPrenom().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("prenom"), utilisateurCriteria.getPrenom()));
            }
            if (utilisateurCriteria.getPrenomLike() != null && !utilisateurCriteria.getPrenomLike().isEmpty()) {
                Expression<String> path = root.<String>get("prenom");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + utilisateurCriteria.getPrenomLike().toLowerCase() + "%"));
            }
            if (utilisateurCriteria.getEmail() != null && !utilisateurCriteria.getEmail().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("email"), utilisateurCriteria.getEmail()));
            }
            if (utilisateurCriteria.getEmailLike() != null && !utilisateurCriteria.getEmailLike().isEmpty()) {
                Expression<String> path = root.<String>get("email");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + utilisateurCriteria.getEmailLike().toLowerCase() + "%"));
            }
            if (utilisateurCriteria.getFilterName() != null
                    && !utilisateurCriteria.getFilterName().isEmpty()
                    && utilisateurCriteria.getFilterWord() != null
                    && !utilisateurCriteria.getFilterWord().isEmpty()) {
                Expression<String> path = root.<String>get(utilisateurCriteria.getFilterName());
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + utilisateurCriteria.getFilterWord().toLowerCase() + "%"));
            }

            if (utilisateurCriteria.getOrderByAsc() != null && utilisateurCriteria.getOrderByAsc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < utilisateurCriteria.getOrderByAsc().length; i++) {
                    orderList.add(builder.asc(root.get(utilisateurCriteria.getOrderByAsc()[i])));
                }
                query.orderBy(orderList);
            }
            if (utilisateurCriteria.getOrderByDesc() != null && utilisateurCriteria.getOrderByDesc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < utilisateurCriteria.getOrderByDesc().length; i++) {
                    orderList.add(builder.desc(root.get(utilisateurCriteria.getOrderByDesc()[i])));
                }
                query.orderBy(orderList);
            }
        }
        if (distinct)
            query.distinct(true);
        return andTogether(predicates, builder);
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
