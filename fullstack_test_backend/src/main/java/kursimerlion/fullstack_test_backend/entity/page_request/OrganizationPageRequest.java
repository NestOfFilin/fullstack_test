package kursimerlion.fullstack_test_backend.entity.page_request;

import kursimerlion.fullstack_test_backend.entity.search_param.OrganizationSearchParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationPageRequest extends PageRequest {

    private OrganizationSearchParams organizationSearchParams;
}
