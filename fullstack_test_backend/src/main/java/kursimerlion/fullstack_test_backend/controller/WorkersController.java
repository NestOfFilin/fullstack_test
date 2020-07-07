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

@RestController
@RequestMapping("api/workers")
@CrossOrigin(origins = "http://localhost:3000")
public class WorkersController {

    private WorkersService workersService;

    @Autowired
    public WorkersController(WorkersService workersService) {
        this.workersService = workersService;
    }

    @GetMapping("/pageCount")
    public Integer getPageCount(@RequestParam Integer pageSize) {
        return workersService.getPageCount(pageSize);
    }

    @GetMapping
    public Page<GetWorker> getPage(@RequestParam Integer pageSize, @RequestParam Integer pageNumber) {
        return workersService.getPage(pageSize, pageNumber);
    }

    @GetMapping("/tree")
    public Tree<GetWorker> getTreeLevel(@RequestParam Object id) {
        return workersService.getTreeLevel(id);
    }

    @GetMapping("/{id}")
    public GetWorker findById(@PathVariable Object id) {
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
