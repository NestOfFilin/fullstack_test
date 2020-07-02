package kursimerlion.fullstack_test_backend.service.impl;

import kursimerlion.fullstack_test_backend.entity.page_request.WorkerPageRequest;
import kursimerlion.fullstack_test_backend.entity.paging.Page;
import kursimerlion.fullstack_test_backend.entity.tree.Node;
import kursimerlion.fullstack_test_backend.entity.tree.Tree;
import kursimerlion.fullstack_test_backend.entity.db.worker.GetWorker;
import kursimerlion.fullstack_test_backend.entity.db.worker.PostWorker;
import kursimerlion.fullstack_test_backend.service.WorkersService;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.psu.pro_it_test.tables.Organizations;
import ru.psu.pro_it_test.tables.Workers;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.jooq.impl.DSL.*;
import static ru.psu.pro_it_test.tables.Workers.WORKERS;
import static ru.psu.pro_it_test.tables.Organizations.ORGANIZATIONS;

@Service
public class WorkersServiceImpl implements WorkersService {

    private final DSLContext dsl;

    @Autowired
    public WorkersServiceImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Integer getPageCount(Integer pageSize) {
        Integer workCount = dsl.selectCount().from(WORKERS).fetchAny().value1();
        return (workCount < pageSize) ? 1
                : (workCount % pageSize == 0) ? workCount / pageSize
                : workCount / pageSize + 1;
    }


    /*
    select w_id, w_name, w_manager, w.name, w_id_org, o.name, sum(c_w_c)
        from (
            select w.id w_id, w.name w_name, w.id_manager w_manager, w.id_org w_id_org, count(*) c_w_c
                from workers w
                right join workers c_w on w.id = c_w.id_manager
                where not w.id is null
                group by w.id
            union
            select w.id, w.name, w.id_manager, w.id_org, 0
                from workers w
        ) w_c
        left join workers w on w_manager = w.id
        left join organizations o on w_id_org = o.id
        group by w_id, w_name, w_manager, w.name, w_id_org, o.name
    */
    @Override
    public Page<GetWorker> getPage(WorkerPageRequest pageRequest) {

        Workers w = WORKERS.as("w");
        Organizations o = ORGANIZATIONS.as("o");

        Table<?> w_c = dsl.select(w.ID, w.NAME, w.ID_MANAGER, w.ID_ORG, count())
                .from(w)
                .rightJoin(WORKERS).on(w.ID.eq(WORKERS.ID_MANAGER))
                .where(w.ID.isNotNull())
                .groupBy(w.ID)
                .union(dsl.select(w.ID, w.NAME, w.ID_MANAGER, w.ID_ORG, inline(0)).from(w))
                .asTable("w_c");

        SelectLimitPercentAfterOffsetStep<Record5<UUID, String, String, String, BigDecimal>> limitStep = dsl.select(
                    w_c.field(0, UUID.class),
                    w_c.field(1, String.class),
                    w.NAME,
                    o.NAME,
                    sum(w_c.field(4, Integer.class)))
                .from(w_c)
                .leftJoin(w).on(w_c.field(2, UUID.class).eq(w.ID))
                .leftJoin(o).on(w_c.field(3, UUID.class).eq(o.ID))
                .groupBy(w_c.field(0, UUID.class), w_c.field(1, String.class),
                        w.NAME, o.NAME)
                .offset(pageRequest.getPageSize() * (pageRequest.getPageNumber() - 1))
                .limit(pageRequest.getPageSize());

        return new Page<>(limitStep.fetch()
                .map(res -> {
                    GetWorker worker = new GetWorker();
                    worker.setId(res.get(res.field1(), UUID.class));
                    worker.setName(res.get(res.field2(), String.class));
                    worker.setManagerName(res.get(res.field3(), String.class));
                    worker.setOrganizationName(res.get(res.field4(), String.class));
                    Boolean flagLeaf = res.get(res.field5(), Integer.class) > 0;
                    return new Node<>(worker, flagLeaf);
                }));
    }

    @Override
    public Tree<GetWorker> getTreeLevel(Object id) {

        Workers w = WORKERS.as("w");
        Organizations o = ORGANIZATIONS.as("o");

        Table<?> w_c = dsl.select(w.ID, w.NAME, w.ID_MANAGER, w.ID_ORG, count())
                .from(w)
                .rightJoin(WORKERS).on(w.ID.eq(WORKERS.ID_MANAGER))
                .where(w.ID.isNotNull())
                .groupBy(w.ID)
                .union(dsl.select(w.ID, w.NAME, w.ID_MANAGER, w.ID_ORG, inline(0)).from(w))
                .asTable("w_c");

        SelectJoinStep<Record5<UUID, String, String, String, BigDecimal>> joinStep = dsl.select(
                    w_c.field(0, UUID.class),
                    w_c.field(1, String.class),
                    w.NAME,
                    o.NAME,
                sum(w_c.field(4, Integer.class)))
                .from(w_c)
                .leftJoin(w).on(w_c.field(2, UUID.class).eq(w.ID))
                .leftJoin(o).on(w_c.field(3, UUID.class).eq(o.ID));

        SelectConditionStep<Record5<UUID, String, String, String, BigDecimal>> conditionStep =
                (Objects.equals((String)id, "root"))
                ? joinStep.where(w_c.field(2, UUID.class).isNull())
                : joinStep.where(w_c.field(2, UUID.class).eq(UUID.fromString((String)id)));

        SelectHavingStep<Record5<UUID, String, String, String, BigDecimal>> havingStep = conditionStep
                .groupBy(w_c.field(0, UUID.class), w_c.field(1, String.class),
                        w.NAME, o.NAME);

        return new Tree<>(havingStep.fetch()
                .map(res -> {
                    GetWorker worker = new GetWorker();
                    worker.setId(res.get(res.field1(), UUID.class));
                    worker.setName(res.get(res.field2(), String.class));
                    worker.setManagerName(res.get(res.field3(), String.class));
                    worker.setOrganizationName(res.get(res.field4(), String.class));
                    Boolean flagLeaf = res.get(res.field5(), Integer.class) > 0;
                    return new Node<>(worker, flagLeaf);
                }));
    }

    @Override
    public GetWorker findById(Object id) {
        return null;
    }

    @Override
    public void create(PostWorker worker) {
        while (true) {
            try {
                worker.setId(UUID.randomUUID());
                dsl.insertInto(ORGANIZATIONS)
                        .set(WORKERS.ID, worker.getId())
                        .set(WORKERS.NAME, worker.getName())
                        .set(WORKERS.ID_MANAGER, worker.getIdManager())
                        .set(WORKERS.ID_ORG, worker.getIdOrganization())
                        .execute();
                break;
            } catch (DataAccessException e) { }
        }
    }

    @Override
    public void update(PostWorker worker) {
        dsl.update(WORKERS)
                .set(WORKERS.ID, worker.getId())
                .set(WORKERS.NAME, worker.getName())
                .set(WORKERS.ID_MANAGER, worker.getIdManager())
                .set(WORKERS.ID_ORG, worker.getIdOrganization())
                .where(WORKERS.ID.eq(worker.getId()))
                .execute();
    }

    @Override
    public void delete(Object id) {
        UUID uuid = UUID.fromString((String)id);

        dsl.delete(WORKERS).where(WORKERS.ID.eq(uuid)).execute();
    }
}
