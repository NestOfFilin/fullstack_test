package kursimerlion.fullstack_test_backend.entity.db.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostOrganization extends Organization  {

    private UUID idParentOrg;
}
