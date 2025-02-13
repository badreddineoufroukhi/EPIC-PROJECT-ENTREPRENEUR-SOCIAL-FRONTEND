package ma.epicproject.backend.criteria;

import ma.epicproject.backend.common.bean.BaseCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProposeCriteria extends BaseCriteria {
    private String name;
    private String nameLike;

    private Integer rating;

    private Double prix;

    private String avatar;

    private String description;
    private String descriptionLike;
    private Long ouvrierId;
}
