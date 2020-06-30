package kursimerlion.fullstack_test_backend.service;

import kursimerlion.fullstack_test_backend.entity.page_request.WorkerPageRequest;
import kursimerlion.fullstack_test_backend.entity.paging.Page;
import kursimerlion.fullstack_test_backend.entity.tree.Tree;
import kursimerlion.fullstack_test_backend.entity.db.worker.GetWorker;
import kursimerlion.fullstack_test_backend.entity.db.worker.PostWorker;

import java.util.List;
import java.util.UUID;

public interface WorkersService {

    List<GetWorker> findAll();

    Page<GetWorker> getPage(WorkerPageRequest pageRequest);

    Tree<GetWorker> getTreeLevel(UUID id);

    GetWorker findById(Object id);

    void create(PostWorker product);

    void update(PostWorker product);

    void delete(Object id);
}
