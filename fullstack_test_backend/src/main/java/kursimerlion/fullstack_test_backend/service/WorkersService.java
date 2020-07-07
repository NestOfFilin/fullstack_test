package kursimerlion.fullstack_test_backend.service;

import kursimerlion.fullstack_test_backend.entity.page_request.WorkerPageRequest;
import kursimerlion.fullstack_test_backend.entity.paging.Page;
import kursimerlion.fullstack_test_backend.entity.tree.Tree;
import kursimerlion.fullstack_test_backend.entity.db.worker.GetWorker;
import kursimerlion.fullstack_test_backend.entity.db.worker.PostWorker;


public interface WorkersService {

    Integer getPageCount(Integer pageSize);

    Page<GetWorker> getPage(Integer pageSize, Integer pageNumber);

    Tree<GetWorker> getTreeLevel(Object id);

    GetWorker findById(Object id);

    void create(PostWorker worker);

    void update(PostWorker worker);

    void delete(Object id);
}
