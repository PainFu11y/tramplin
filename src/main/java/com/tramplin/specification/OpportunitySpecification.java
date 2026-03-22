package com.tramplin.specification;

import com.tramplin.entity.Opportunity;
import com.tramplin.entity.Tag;
import com.tramplin.enums.OpportunityStatus;
import com.tramplin.enums.WorkFormat;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class OpportunitySpecification {

    public static Specification<Opportunity> hasWorkFormat(WorkFormat workFormat) {
        return (root, query, cb) ->
                workFormat == null ? null : cb.equal(root.get("workFormat"), workFormat);
    }

    public static Specification<Opportunity> hasTags(List<String> tagNames) {
        return (root, query, cb) -> {
            if (tagNames == null || tagNames.isEmpty()) return null;
            Join<Opportunity, Tag> tags = root.join("tags", JoinType.INNER);
            query.distinct(true);
            return tags.get("name").in(tagNames);
        };
    }

    public static Specification<Opportunity> salaryFrom(Integer salaryFrom) {
        return (root, query, cb) ->
                salaryFrom == null ? null : cb.greaterThanOrEqualTo(root.get("salary"), salaryFrom);
    }

    public static Specification<Opportunity> salaryTo(Integer salaryTo) {
        return (root, query, cb) ->
                salaryTo == null ? null : cb.lessThanOrEqualTo(root.get("salary"), salaryTo);
    }

    public static Specification<Opportunity> isActive() {
        return (root, query, cb) ->
                cb.equal(root.get("status"), OpportunityStatus.ACTIVE);
    }
}