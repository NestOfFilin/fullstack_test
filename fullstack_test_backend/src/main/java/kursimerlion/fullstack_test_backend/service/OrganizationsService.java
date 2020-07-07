package kursimerlion.fullstack_test_backend.service;

import kursimerlion.fullstack_test_backend.entity.db.organization.GetOrganization;
import kursimerlion.fullstack_test_backend.entity.db.organization.PostOrganization;
import kursimerlion.fullstack_test_backend.entity.page_request.OrganizationPageRequest;
import kursimerlion.fullstack_test_backend.entity.paging.Page;
import kursimerlion.fullstack_test_backend.entity.tree.Tree;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface OrganizationsService {

    Integer getPageCount(Integer pageSize);

    Page<GetOrganization> getPage(Integer pageSize, Integer pageNumber);

    Tree<GetOrganization> getTreeLevel(Object id);

    GetOrganization findById(Object id);

    void create(PostOrganization organization);

    void update(PostOrganization organization);

    void delete(Object id);
}
