package kursimerlion.fullstack_test_backend.controller;

import kursimerlion.fullstack_test_backend.entity.page_request.OrganizationPageRequest;
import kursimerlion.fullstack_test_backend.entity.paging.Page;
import kursimerlion.fullstack_test_backend.entity.tree.Tree;
import kursimerlion.fullstack_test_backend.entity.db.organization.GetOrganization;
import kursimerlion.fullstack_test_backend.entity.db.organization.PostOrganization;
import kursimerlion.fullstack_test_backend.service.OrganizationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/organizations")
@CrossOrigin(origins = "http://localhost:3000")
public class OrganizationsController {

    private OrganizationsService organizationsService;

    @Autowired
    public OrganizationsController(OrganizationsService organizationsService) {
        this.organizationsService = organizationsService;
    }

    @GetMapping("/pageCount")
    public Integer getPageCount(@RequestParam Integer pageSize) {
        return organizationsService.getPageCount(pageSize);
    }

    @GetMapping
    public Page<GetOrganization> getPage(@RequestParam Integer pageSize, @RequestParam Integer pageNumber) {
        return organizationsService.getPage(pageSize, pageNumber);
    }

    @GetMapping("/tree")
    public Tree<GetOrganization> getTreeLevel(@RequestParam Object id) {
        return organizationsService.getTreeLevel(id);
    }

    @GetMapping("/{id}")
    public GetOrganization findById(@PathVariable Object id) {
        return organizationsService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody PostOrganization postOrganization) {
        organizationsService.create(postOrganization);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody PostOrganization postOrganization) {
        organizationsService.update(postOrganization);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Object id) {
        organizationsService.delete(id);
    }
}
