package kursimerlion.fullstack_test_backend.entity.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tree<T> {

    private List<Node<T>> nodes;
}
