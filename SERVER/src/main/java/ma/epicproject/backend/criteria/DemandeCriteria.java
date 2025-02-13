package ma.epicproject.backend.criteria;

import ma.epicproject.backend.common.bean.BaseCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeCriteria extends BaseCriteria {
    private Long clientId;
    private Long serviceProposeId;
    private Integer rating;
    private String agreement;
    private String description;
    private String descriptionLike;
}
