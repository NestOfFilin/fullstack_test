package kursimerlion.fullstack_test_backend.entity.search_param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerSeachParams extends SearchParams {

    private String organizationName;
}
