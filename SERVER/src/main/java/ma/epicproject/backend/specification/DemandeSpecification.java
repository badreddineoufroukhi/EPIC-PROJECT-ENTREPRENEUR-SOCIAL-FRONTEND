package ma.epicproject.backend.specification;

import ma.epicproject.backend.criteria.DemandeCriteria;
import ma.epicproject.backend.entity.Client;
import ma.epicproject.backend.entity.Demande;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.epicproject.backend.entity.Ouvrier;
import ma.epicproject.backend.entity.ServicePropose;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class DemandeSpecification implements Specification<Demande> {
    private DemandeCriteria demandeCriteria;
    private boolean distinct;

    public DemandeSpecification(DemandeCriteria criteria) {
        this.demandeCriteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Demande> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (demandeCriteria != null) {
            if (demandeCriteria.getId() != null && demandeCriteria.getId() > 0) {
                predicates.add(builder.equal(root.<String>get("id"), demandeCriteria.getId()));
            }
            if (demandeCriteria.getIdsIn() != null && !demandeCriteria.getIdsIn().isEmpty()) {
                predicates.add(root.<Long>get("id").in(demandeCriteria.getIdsIn()));
            }
            if (demandeCriteria.getIdsNotIn() != null && !demandeCriteria.getIdsNotIn().isEmpty()) {
                predicates.add(builder.not(root.<Long>get("id").in(demandeCriteria.getIdsNotIn())));
            }
            if (demandeCriteria.getNotId() != null && demandeCriteria.getNotId() > 0) {
                predicates.add(builder.notEqual(root.<Boolean>get("id"), demandeCriteria.getNotId()));
            }


            if (demandeCriteria.getDescription() != null && !demandeCriteria.getDescription().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("description"), demandeCriteria.getDescription()));
            }
            if (demandeCriteria.getDescriptionLike() != null && !demandeCriteria.getDescriptionLike().isEmpty()) {
                Expression<String> path = root.<String>get("description");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + demandeCriteria.getDescriptionLike().toLowerCase() + "%"));
            }


            if (demandeCriteria.getRating() != null) {
                predicates.add(builder.equal(root.<Integer>get("rating"), demandeCriteria.getRating()));
            }
            if (demandeCriteria.getClientId() != null && demandeCriteria.getClientId() > 0) {
                predicates.add(builder.equal(root.<Client>get("utilisateur").get("id"), demandeCriteria.getClientId()));
            }
            if (demandeCriteria.getServiceProposeId() != null && demandeCriteria.getServiceProposeId() > 0) {
                predicates.add(builder.equal(root.<ServicePropose>get("service_propose").get("id"), demandeCriteria.getServiceProposeId()));
            }
            if (demandeCriteria.getAgreement() != null && !demandeCriteria.getAgreement().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("agreement"),demandeCriteria.getAgreement()));
            }
            if (demandeCriteria.getFilterName() != null
                    && !demandeCriteria.getFilterName().isEmpty()
                    && demandeCriteria.getFilterWord() != null
                    && !demandeCriteria.getFilterWord().isEmpty()) {
                Expression<String> path = root.<String>get(demandeCriteria.getFilterName());
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + demandeCriteria.getFilterWord().toLowerCase() + "%"));
            }

            // A comprendre !!!!!!!!!
            if (demandeCriteria.getOrderByAsc() != null && demandeCriteria.getOrderByAsc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < demandeCriteria.getOrderByAsc().length; i++) {
                    orderList.add(builder.asc(root.get(demandeCriteria.getOrderByAsc()[i])));
                }
                query.orderBy(orderList);
            }
            if (demandeCriteria.getOrderByDesc() != null && demandeCriteria.getOrderByDesc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < demandeCriteria.getOrderByDesc().length; i++) {
                    orderList.add(builder.desc(root.get(demandeCriteria.getOrderByDesc()[i])));
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
