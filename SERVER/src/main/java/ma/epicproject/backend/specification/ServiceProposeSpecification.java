package ma.epicproject.backend.specification;

import ma.epicproject.backend.criteria.ServiceProposeCriteria;
import ma.epicproject.backend.entity.Ouvrier;
import ma.epicproject.backend.entity.ServicePropose;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ServiceProposeSpecification implements Specification<ServicePropose> {
    private ServiceProposeCriteria serviceProposeCriteria;
    private boolean distinct;

    public ServiceProposeSpecification(ServiceProposeCriteria criteria) {
        this.serviceProposeCriteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<ServicePropose> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (serviceProposeCriteria != null) {
            if (serviceProposeCriteria.getId() != null && serviceProposeCriteria.getId() > 0) {
                predicates.add(builder.equal(root.<String>get("id"), serviceProposeCriteria.getId()));
            }
            if (serviceProposeCriteria.getIdsIn() != null && !serviceProposeCriteria.getIdsIn().isEmpty()) {
                predicates.add(root.<Long>get("id").in(serviceProposeCriteria.getIdsIn()));
            }
            if (serviceProposeCriteria.getIdsNotIn() != null && !serviceProposeCriteria.getIdsNotIn().isEmpty()) {
                predicates.add(builder.not(root.<Long>get("id").in(serviceProposeCriteria.getIdsNotIn())));
            }
            if (serviceProposeCriteria.getNotId() != null && serviceProposeCriteria.getNotId() > 0) {
                predicates.add(builder.notEqual(root.<Boolean>get("id"), serviceProposeCriteria.getNotId()));
            }
            if (serviceProposeCriteria.getName() != null && !serviceProposeCriteria.getName().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("name"), serviceProposeCriteria.getName()));
            }
            if (serviceProposeCriteria.getNameLike() != null && !serviceProposeCriteria.getNameLike().isEmpty()) {
                Expression<String> path = root.<String>get("name");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + serviceProposeCriteria.getNameLike().toLowerCase() + "%"));
            }

            if (serviceProposeCriteria.getRating() != null) {
                predicates.add(builder.equal(root.<Integer>get("rating"), serviceProposeCriteria.getRating()));
            }
            if (serviceProposeCriteria.getPrix() != null) {
                predicates.add(builder.equal(root.<Double>get("prix"), serviceProposeCriteria.getPrix()));
            }
            if (serviceProposeCriteria.getOuvrierId() != null && serviceProposeCriteria.getOuvrierId() > 0) {
                predicates.add(builder.equal(root.<Ouvrier>get("ouvrier").get("id"), serviceProposeCriteria.getOuvrierId()));
            }
            if (serviceProposeCriteria.getDescription() != null && !serviceProposeCriteria.getDescription().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("description"), serviceProposeCriteria.getDescription()));
            }
            if (serviceProposeCriteria.getDescriptionLike() != null && !serviceProposeCriteria.getDescriptionLike().isEmpty()) {
                Expression<String> path = root.<String>get("description");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + serviceProposeCriteria.getDescriptionLike().toLowerCase() + "%"));
            }
            if (serviceProposeCriteria.getFilterName() != null
                    && !serviceProposeCriteria.getFilterName().isEmpty()
                    && serviceProposeCriteria.getFilterWord() != null
                    && !serviceProposeCriteria.getFilterWord().isEmpty()) {
                Expression<String> path = root.<String>get(serviceProposeCriteria.getFilterName());
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + serviceProposeCriteria.getFilterWord().toLowerCase() + "%"));
            }

            // A comprendre !!!!!!!!!
            if (serviceProposeCriteria.getOrderByAsc() != null && serviceProposeCriteria.getOrderByAsc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < serviceProposeCriteria.getOrderByAsc().length; i++) {
                    orderList.add(builder.asc(root.get(serviceProposeCriteria.getOrderByAsc()[i])));
                }
                query.orderBy(orderList);
            }
            if (serviceProposeCriteria.getOrderByDesc() != null && serviceProposeCriteria.getOrderByDesc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < serviceProposeCriteria.getOrderByDesc().length; i++) {
                    orderList.add(builder.desc(root.get(serviceProposeCriteria.getOrderByDesc()[i])));
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
