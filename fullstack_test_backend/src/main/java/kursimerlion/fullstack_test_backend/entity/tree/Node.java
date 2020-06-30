package kursimerlion.fullstack_test_backend.entity.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Node<T> {

    private T data;
    private Boolean flagLeaf;
}
