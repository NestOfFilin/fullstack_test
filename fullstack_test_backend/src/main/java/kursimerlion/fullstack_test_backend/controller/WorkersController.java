package kursimerlion.fullstack_test_backend.controller;

import kursimerlion.fullstack_test_backend.entity.page_request.WorkerPageRequest;
import kursimerlion.fullstack_test_backend.entity.paging.Page;
import kursimerlion.fullstack_test_backend.entity.tree.Tree;
import kursimerlion.fullstack_test_backend.entity.db.worker.GetWorker;
import kursimerlion.fullstack_test_backend.entity.db.worker.PostWorker;
import kursimerlion.fullstack_test_backend.service.WorkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/workers")
public class WorkersController {

    private WorkersService workersService;

    @Autowired
    public WorkersController(WorkersService workersService) {
        this.workersService = workersService;
    }

    @GetMapping
    public Page<GetWorker> getPage(@RequestBody WorkerPageRequest pageRequest) {
        return null;
    }

    @GetMapping("/tree")
    public Tree<GetWorker> getTreeLevel(@RequestBody UUID id) {
        return null;
    }

    @GetMapping("/{id}")
    public GetWorker findById(@PathVariable UUID id) {
        return workersService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody PostWorker postWorker) {
        workersService.create(postWorker);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody PostWorker postWorker) {
        workersService.update(postWorker);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Object id) {
        workersService.delete(id);
    }
}
