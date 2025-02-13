package ma.epicproject.backend.specification;

import ma.epicproject.backend.criteria.ClientCriteria;
import ma.epicproject.backend.entity.Client;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ClientSpecification implements Specification<Client> {
    private ClientCriteria clientCriteria;
    private boolean distinct;

    public ClientSpecification(ClientCriteria criteria) {
        this.clientCriteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (clientCriteria != null) {
            if (clientCriteria.getId() != null && clientCriteria.getId() > 0) {
                predicates.add(builder.equal(root.<String>get("id"), clientCriteria.getId()));
            }
            if (clientCriteria.getIdsIn() != null && !clientCriteria.getIdsIn().isEmpty()) {
                predicates.add(root.<Long>get("id").in(clientCriteria.getIdsIn()));
            }
            if (clientCriteria.getIdsNotIn() != null && !clientCriteria.getIdsNotIn().isEmpty()) {
                predicates.add(builder.not(root.<Long>get("id").in(clientCriteria.getIdsNotIn())));
            }
            if (clientCriteria.getNotId() != null && clientCriteria.getNotId() > 0) {
                predicates.add(builder.notEqual(root.<Boolean>get("id"), clientCriteria.getNotId()));
            }
            if (clientCriteria.getNom() != null && !clientCriteria.getNom().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("nom"), clientCriteria.getNom()));
            }
            if (clientCriteria.getNomLike() != null && !clientCriteria.getNomLike().isEmpty()) {
                Expression<String> path = root.<String>get("nom");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + clientCriteria.getNomLike().toLowerCase() + "%"));
            }
            if (clientCriteria.getPrenom() != null && !clientCriteria.getPrenom().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("prenom"), clientCriteria.getPrenom()));
            }
            if (clientCriteria.getPrenomLike() != null && !clientCriteria.getPrenomLike().isEmpty()) {
                Expression<String> path = root.<String>get("prenom");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + clientCriteria.getPrenomLike().toLowerCase() + "%"));
            }
            if (clientCriteria.getEmail() != null && !clientCriteria.getEmail().isEmpty()) {
                predicates.add(builder.equal(root.<String>get("email"), clientCriteria.getEmail()));
            }
            if (clientCriteria.getEmailLike() != null && !clientCriteria.getEmailLike().isEmpty()) {
                Expression<String> path = root.<String>get("email");
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + clientCriteria.getEmailLike().toLowerCase() + "%"));
            }
//            if (clientCriteria.getSalary() != null) {
//                predicates.add(builder.equal(root.<Double>get("salary"), clientCriteria.getSalary()));
//            }
            if (clientCriteria.getFilterName() != null
                    && !clientCriteria.getFilterName().isEmpty()
                    && clientCriteria.getFilterWord() != null
                    && !clientCriteria.getFilterWord().isEmpty()) {
                Expression<String> path = root.<String>get(clientCriteria.getFilterName());
                Expression<String> lower = builder.lower(path);
                predicates.add(builder.like(lower, "%" + clientCriteria.getFilterWord().toLowerCase() + "%"));
            }

            // A comprendre !!!!!!!!!
            if (clientCriteria.getOrderByAsc() != null && clientCriteria.getOrderByAsc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < clientCriteria.getOrderByAsc().length; i++) {
                    orderList.add(builder.asc(root.get(clientCriteria.getOrderByAsc()[i])));
                }
                query.orderBy(orderList);
            }
            if (clientCriteria.getOrderByDesc() != null && clientCriteria.getOrderByDesc().length > 0) {
                List<Order> orderList = new ArrayList<Order>();
                for (int i = 0; i < clientCriteria.getOrderByDesc().length; i++) {
                    orderList.add(builder.desc(root.get(clientCriteria.getOrderByDesc()[i])));
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
