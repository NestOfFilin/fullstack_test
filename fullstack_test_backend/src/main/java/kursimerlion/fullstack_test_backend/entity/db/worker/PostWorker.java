package kursimerlion.fullstack_test_backend.entity.db.worker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostWorker extends Worker {

    private UUID idOrganization;
    private UUID idManager;
}
