package kursimerlion.fullstack_test_backend.entity.page_request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PageRequest {

    private Integer pageSize, pageNumber;
}
