package kursimerlion.fullstack_test_backend.service.impl;

import kursimerlion.fullstack_test_backend.entity.db.organization.GetOrganization;
import kursimerlion.fullstack_test_backend.entity.db.organization.PostOrganization;
import kursimerlion.fullstack_test_backend.entity.page_request.OrganizationPageRequest;
import kursimerlion.fullstack_test_backend.entity.paging.Page;
import kursimerlion.fullstack_test_backend.entity.tree.Node;
import kursimerlion.fullstack_test_backend.entity.tree.Tree;
import kursimerlion.fullstack_test_backend.service.OrganizationsService;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.psu.pro_it_test.tables.Organizations;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.jooq.impl.DSL.*;
import static ru.psu.pro_it_test.tables.Workers.WORKERS;
import static ru.psu.pro_it_test.tables.Organizations.ORGANIZATIONS;

@Service
public class OrganizationsServiceImpl implements OrganizationsService {

    private final DSLContext dsl;

    @Autowired
    public OrganizationsServiceImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Integer getPageCount(Integer pageSize) {

        Integer orgCount = dsl.selectCount().from(ORGANIZATIONS).fetchAny().value1();
        return (orgCount < pageSize) ? 1
                : (orgCount % pageSize == 0) ? orgCount / pageSize
                : orgCount / pageSize + 1;
    }

    @Override
    public Page<GetOrganization> getPage(Integer pageSize, Integer pageNumber) {

        Organizations o = ORGANIZATIONS.as("o"), c = ORGANIZATIONS.as("c");

        Table<?> withWorkerCount = dsl.select(o.ID, o.NAME, o.ID_PARENT_ORG, count().as("count_worker"))
                .from(o)
                .rightJoin(WORKERS).on(o.ID.eq(WORKERS.ID_ORG))
                .groupBy(o.ID).asTable("with_worker_count");
        Table<?> withChildCount = dsl.select(o.ID, o.NAME, o.ID_PARENT_ORG, count().as("child_count"))
                .from(o)
                .rightJoin(c).on(o.ID.eq(c.ID_PARENT_ORG))
                .groupBy(o.ID).asTable("with_child_count");

        Table<?> withChildWorker = dsl.select(
                    withWorkerCount.field(0, UUID.class).as("c_id"),
                    withWorkerCount.field(1, String.class).as("c_name"),
                    withWorkerCount.field(2, UUID.class).as("c_p_org"),
                    withWorkerCount.field("count_worker", Integer.class),
                    withChildCount.field("child_count", Integer.class))
                .from(withWorkerCount)
                .fullJoin(withChildCount).on(withWorkerCount.field(0, UUID.class).eq(withChildCount.field(0, UUID.class)))
                .where(withWorkerCount.field(0).isNotNull())
                .union(dsl.select(o.ID, o.NAME, o.ID_PARENT_ORG, inline(0), inline(0)).from(o)).asTable("with_c_w");

        SelectLimitPercentAfterOffsetStep<Record4<UUID, String, BigDecimal, BigDecimal>> limitStep = dsl.select(
                    withChildWorker.field(0, UUID.class).as("c_id"),
                    withChildWorker.field(1, String.class).as("c_name"),
                    sum(withChildWorker.field("count_worker", Integer.class)),
                    sum(withChildWorker.field("child_count", Integer.class)))
                .from(withChildWorker)
                .groupBy(withChildWorker.field(0), withChildWorker.field(1))
                .offset(pageSize * (pageNumber - 1))
                .limit(pageSize);

        return new Page<>(limitStep.fetch()
                .map(res -> {
                    GetOrganization organization = new GetOrganization();
                    organization.setId(res.get(res.field1(), UUID.class));
                    organization.setName(res.get(res.field2(), String.class));
                    organization.setWorkersCount(res.get(res.field3(), Integer.class));
                    Boolean flagLeaf = res.get(res.field4(), Integer.class) > 0;
                    return new Node<>(organization, flagLeaf);
                }));
    }

    /*
     select c_id, c_name, sum(w_c), sum(c_c)
        from (
            select a.id c_id, a.name c_name, a.id_parent_org c_p_org, a.w_c w_c, case when b.c_c is null then 0 else b.c_c end c_c
                from
                    (select o.id, o.name, o.id_parent_org, count(*) w_c
                        from public.organizations o
                        right join public.workers on o.id = workers.id_org
                        group by o.id) a
                full join
                    (select o.id, o.name, o.id_parent_org, count(*) c_c
                        from public.organizations o
                        right join public.organizations c on o.id = c.id_parent_org
                        group by o.id) b
                    on a.id = b.id
                where not a.id is null
            union
            select o.id, o.name, o.id_parent_org, 0, 0
                from public.organizations o
        ) as f
        where c_p_org is null
        group by c_id, c_name;
     */
    @Override
    public Tree<GetOrganization> getTreeLevel(Object id) {

        Organizations o = ORGANIZATIONS.as("o"), c = ORGANIZATIONS.as("c");

        Table<?> withWorkerCount = dsl.select(o.ID, o.NAME, o.ID_PARENT_ORG, count().as("count_worker"))
                .from(o)
                .rightJoin(WORKERS).on(o.ID.eq(WORKERS.ID_ORG))
                .groupBy(o.ID).asTable("with_worker_count");
        Table<?> withChildCount = dsl.select(o.ID, o.NAME, o.ID_PARENT_ORG, count().as("child_count"))
                .from(o)
                .rightJoin(c).on(o.ID.eq(c.ID_PARENT_ORG))
                .groupBy(o.ID).asTable("with_child_count");

        Table<?> withChildWorker = dsl.select(
                                        withWorkerCount.field(0, UUID.class).as("c_id"),
                                        withWorkerCount.field(1, String.class).as("c_name"),
                                        withWorkerCount.field(2, UUID.class).as("c_p_org"),
                                        withWorkerCount.field("count_worker", Integer.class),
                                        withChildCount.field("child_count", Integer.class))
                .from(withWorkerCount)
                .fullJoin(withChildCount).on(withWorkerCount.field(0, UUID.class).eq(withChildCount.field(0, UUID.class)))
                .where(withWorkerCount.field(0).isNotNull())
                .union(dsl.select(o.ID, o.NAME, o.ID_PARENT_ORG, inline(0), inline(0)).from(o)).asTable("with_c_w");

        SelectJoinStep<Record4<UUID, String, BigDecimal, BigDecimal>> joinStep = dsl.select(
                withChildWorker.field(0, UUID.class).as("c_id"),
                withChildWorker.field(1, String.class).as("c_name"),
                sum(withChildWorker.field("count_worker", Integer.class)),
                sum(withChildWorker.field("child_count", Integer.class)))
                .from(withChildWorker);

        SelectConditionStep<Record4<UUID, String, BigDecimal, BigDecimal>> conditionStep = (Objects.equals((String)id, "root"))
                ? joinStep.where(withChildWorker.field(2, UUID.class).isNull())
                : joinStep.where(withChildWorker.field(2, UUID.class).eq(UUID.fromString((String)id)));

        SelectHavingStep<Record4<UUID, String, BigDecimal, BigDecimal>> havingStep =
                conditionStep.groupBy(withChildWorker.field(0), withChildWorker.field(1));

        return new Tree<>(havingStep.fetch()
                .map(res -> {
                    GetOrganization organization = new GetOrganization();
                    organization.setId(res.get(res.field1(), UUID.class));
                    organization.setName(res.get(res.field2(), String.class));
                    organization.setWorkersCount(res.get(res.field3(), Integer.class));
                    Boolean flagLeaf = res.get(res.field4(), Integer.class) > 0;
                    return new Node<>(organization, flagLeaf);
                }));
    }

    @Override
    public GetOrganization findById(Object id) {
        UUID uuid = UUID.fromString((String)id);

        return dsl.select(ORGANIZATIONS.ID, ORGANIZATIONS.NAME, count(WORKERS.ID))
                .from(ORGANIZATIONS)
                .leftJoin(WORKERS).on(ORGANIZATIONS.ID.eq(WORKERS.ID_ORG))
                .where(ORGANIZATIONS.ID.eq(uuid))
                .fetchOneInto(GetOrganization.class);
    }

    @Override
    public void create(PostOrganization organization) {
        while (true) {
            try {
                organization.setId(UUID.randomUUID());
                dsl.insertInto(ORGANIZATIONS)
                        .set(ORGANIZATIONS.ID, organization.getId())
                        .set(ORGANIZATIONS.NAME, organization.getName())
                        .set(ORGANIZATIONS.ID_PARENT_ORG, organization.getIdParentOrg())
                        .execute();
                break;
            } catch (DataAccessException e) { }
        }
    }

    @Override
    public void update(PostOrganization organization) {
        dsl.update(ORGANIZATIONS)
                .set(ORGANIZATIONS.ID, organization.getId())
                .set(ORGANIZATIONS.NAME, organization.getName())
                .set(ORGANIZATIONS.ID_PARENT_ORG, organization.getIdParentOrg())
                .where(ORGANIZATIONS.ID.eq(organization.getId()))
                .execute();
    }

    @Override
    public void delete(Object id) {
        UUID uuid = UUID.fromString((String)id);

        dsl.delete(ORGANIZATIONS).where(ORGANIZATIONS.ID.eq(uuid)).execute();
    }
}
