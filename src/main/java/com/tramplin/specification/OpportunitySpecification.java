package com.tramplin.specification;

import com.tramplin.entity.Opportunity;
import com.tramplin.entity.Tag;
import com.tramplin.enums.OpportunityStatus;
import com.tramplin.enums.WorkFormat;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

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

    public static Specification<Opportunity> searchByKeyword(String keyword) {
        return (root, query, cb) -> {

            if (keyword == null || keyword.isBlank()) return null;

            String pattern = "%" + keyword.toLowerCase() + "%";

            Join<Object, Object> companyJoin = root.join("company", JoinType.LEFT);
            Join<Object, Object> tagsJoin = root.join("tags", JoinType.LEFT);

            query.distinct(true);

            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern),
                    cb.like(cb.lower(root.get("city")), pattern),
                    cb.like(cb.lower(companyJoin.get("name")), pattern),
                    cb.like(cb.lower(tagsJoin.get("name")), pattern)
            );
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

    public static Specification<Opportunity> belongsToCompany(UUID companyId) {
        return (root, query, cb) ->
                companyId == null ? null : cb.equal(root.get("company").get("id"), companyId);
    }

}