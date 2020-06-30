package kursimerlion.fullstack_test_backend.entity.db.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganization extends Organization {

    private Integer workersCount;
}
