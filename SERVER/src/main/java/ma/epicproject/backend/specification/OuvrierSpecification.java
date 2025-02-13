package ma.epicproject.backend.specification;

import ma.epicproject.backend.criteria.OuvrierCriteria;
import ma.epicproject.backend.entity.Ouvrier;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OuvrierSpecification implements Specification<Ouvrier> {
    private OuvrierCriteria ouvrierCriteria;
    private boolean distinct;

    public OuvrierSpecification(OuvrierCriteria criteria) {
        this.ouvrierCriteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Ouvrier> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (ouvrierCriteria != null) {
            if (ouvrierCriteria.getId() != null && ouvrierCriteria.getId() > 0) {
                predicates.add(builder.equal(root.<String>get("id"), ouvrierCriteria.getId()));
            }
            if (ouvrierCriteria.getIdsIn() != null && !ouvrierCriteria.getIdsIn().isEmpty()) {
                predicates.add(root.<Long>get("id").in(ouvrierCriteria.getIdsIn()));
            }
            if (ouvrierCriteria.getIdsNotIn() != null && !ouvrierCriteria.getIdsNotIn().isEmpty()) {
                predicates.add(builder.not(root.<Long>get("id").in(ouvrierCriteria.getIdsNotIn())));
            }
            if (ouvrierCriteria.getNotId() != null && ouvrierCriteria.getNotId() > 0) {
                predicates.add(builder.notEqual(root.<Boolean>get("id"), ouvrierCriteria.getNotId()));
            }
            if (ouvrierCriteria.getNom() != null && !ouvrierCriteria.getNom().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("nom"), ouvrierCriteria.getNom()));
            }
            if (ouvrierCriteria.getNomLike() != null && !ouvrierCriteria.getNomLike().isEmpty()) {
                Expression<String> path = root.<String>get("nom");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + ouvrierCriteria.getNomLike().toLowerCase() + "%"));
            }
            if (ouvrierCriteria.getPrenom() != null && !ouvrierCriteria.getPrenom().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("prenom"), ouvrierCriteria.getPrenom()));
            }
            if (ouvrierCriteria.getPrenomLike() != null && !ouvrierCriteria.getPrenomLike().isEmpty()) {
                Expression<String> path = root.<String>get("prenom");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + ouvrierCriteria.getPrenomLike().toLowerCase() + "%"));
            }
            if (ouvrierCriteria.getEmail() != null && !ouvrierCriteria.getEmail().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("email"), ouvrierCriteria.getEmail()));
            }
            if (ouvrierCriteria.getEmailLike() != null && !ouvrierCriteria.getEmailLike().isEmpty()) {
                Expression<String> path = root.<String>get("email");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + ouvrierCriteria.getEmailLike().toLowerCase() + "%"));
            }

            if (ouvrierCriteria.getProfession() != null && !ouvrierCriteria.getProfession().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("profession"), ouvrierCriteria.getProfession()));
            }
            if (ouvrierCriteria.getProfessionLike() != null && !ouvrierCriteria.getProfessionLike().isEmpty()) {
                Expression<String> path = root.<String>get("profession");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + ouvrierCriteria.getProfessionLike().toLowerCase() + "%"));
            }

            if (ouvrierCriteria.getDescription() != null && !ouvrierCriteria.getDescription().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("description"), ouvrierCriteria.getDescription()));
            }
            if (ouvrierCriteria.getDescriptionLike() != null && !ouvrierCriteria.getDescriptionLike().isEmpty()) {
                Expression<String> path = root.<String>get("description");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + ouvrierCriteria.getDescriptionLike().toLowerCase() + "%"));
            }

            if (ouvrierCriteria.getNumeroTelephone() != null && !ouvrierCriteria.getNumeroTelephone().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("numeroTelephone"), ouvrierCriteria.getNumeroTelephone()));
            }
            if (ouvrierCriteria.getNumeroTelephoneLike() != null && !ouvrierCriteria.getNumeroTelephoneLike().isEmpty()) {
                Expression<String> path = root.<String>get("numeroTelephone");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + ouvrierCriteria.getNumeroTelephoneLike().toLowerCase() + "%"));
            }

            if (ouvrierCriteria.getVille() != null && !ouvrierCriteria.getVille().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("ville"), ouvrierCriteria.getVille()));
            }
            if (ouvrierCriteria.getVilleLike() != null && !ouvrierCriteria.getVilleLike().isEmpty()) {
                Expression<String> path = root.<String>get("ville");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + ouvrierCriteria.getVilleLike().toLowerCase() + "%"));
            }

            if (ouvrierCriteria.getLocalisation() != null && !ouvrierCriteria.getLocalisation().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("localisation"), ouvrierCriteria.getLocalisation()));
            }
            if (ouvrierCriteria.getLocalisationLike() != null && !ouvrierCriteria.getLocalisationLike().isEmpty()) {
                Expression<String> path = root.<String>get("localisation");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + ouvrierCriteria.getLocalisationLike().toLowerCase() + "%"));
            }

            if (ouvrierCriteria.getRating() != null) {
                predicates.add(builder.equal(root.<Integer>get("rating"), ouvrierCriteria.getRating()));
            }
            if (ouvrierCriteria.getDisponible() != null && !ouvrierCriteria.getDisponible().isEmpty()) {
                predicates.add(builder.equal(root.<Boolean>get("disponible"),Boolean.valueOf(ouvrierCriteria.getDisponible())));
            }

            if (ouvrierCriteria.getFilterName() != null
                    && !ouvrierCriteria.getFilterName().isEmpty()
                    && ouvrierCriteria.getFilterWord() != null
                    && !ouvrierCriteria.getFilterWord().isEmpty()) {
                Expression<String> path = root.<String>get(ouvrierCriteria.getFilterName());
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + ouvrierCriteria.getFilterWord().toLowerCase() + "%"));
            }

            // A comprendre !!!!!!!!!
            if (ouvrierCriteria.getOrderByAsc() != null && ouvrierCriteria.getOrderByAsc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < ouvrierCriteria.getOrderByAsc().length; i++) {
                    orderList.add(builder.asc(root.get(ouvrierCriteria.getOrderByAsc()[i])));
                }
                query.orderBy(orderList);
            }
            if (ouvrierCriteria.getOrderByDesc() != null && ouvrierCriteria.getOrderByDesc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < ouvrierCriteria.getOrderByDesc().length; i++) {
                    orderList.add(builder.desc(root.get(ouvrierCriteria.getOrderByDesc()[i])));
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
