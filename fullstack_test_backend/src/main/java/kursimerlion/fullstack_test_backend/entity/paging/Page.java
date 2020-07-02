package kursimerlion.fullstack_test_backend.entity.paging;

import kursimerlion.fullstack_test_backend.entity.tree.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

    private List<Node<T>> pageBody;
    //private Integer countPages;
}
