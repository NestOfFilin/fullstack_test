package kursimerlion.fullstack_test_backend.entity.db.worker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetWorker extends Worker {

    private String managerName, organizationName;
}
