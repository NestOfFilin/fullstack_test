package kursimerlion.fullstack_test_backend.service.impl;

import kursimerlion.fullstack_test_backend.entity.page_request.WorkerPageRequest;
import kursimerlion.fullstack_test_backend.entity.paging.Page;
import kursimerlion.fullstack_test_backend.entity.tree.Tree;
import kursimerlion.fullstack_test_backend.entity.db.worker.GetWorker;
import kursimerlion.fullstack_test_backend.entity.db.worker.PostWorker;
import kursimerlion.fullstack_test_backend.service.WorkersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkersServiceImpl implements WorkersService {

    @Override
    public List<GetWorker> findAll() {
        return null;
    }

    @Override
    public Page<GetWorker> getPage(WorkerPageRequest pageRequest) {
        return null;
    }

    @Override
    public Tree<GetWorker> getTreeLevel(UUID id) {
        return null;
    }

    @Override
    public GetWorker findById(Object id) {
        return null;
    }

    @Override
    public void create(PostWorker product) {

    }

    @Override
    public void update(PostWorker product) {

    }

    @Override
    public void delete(Object id) {

    }
}
