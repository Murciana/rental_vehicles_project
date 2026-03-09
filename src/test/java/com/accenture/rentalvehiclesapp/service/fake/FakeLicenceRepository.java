package com.accenture.rentalvehiclesapp.service.fake;

import com.accenture.rentalvehiclesapp.repository.LicenceRepository;
import com.accenture.rentalvehiclesapp.repository.entity.licence.Licence;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class FakeLicenceRepository implements LicenceRepository {
    public final Map<UUID, Licence> store = new HashMap<>();

    // Pré-rempli avec tes 2 vraies licences
    public FakeLicenceRepository() {
        Licence b = new Licence();
        b.setId(UUID.fromString("149c9842-e944-4597-be59-b8c6dd7a530d"));
        b.setName("B");
        store.put(b.getId(), b);

        Licence a1 = new Licence();
        a1.setId(UUID.fromString("41b0a512-69d3-49d1-ab8c-6672308bb1a2"));
        a1.setName("A1");
        store.put(a1.getId(), a1);
    }

    @Override
    public <S extends Licence> S save(S entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Licence> findById(UUID uuid) {
        return Optional.ofNullable(store.get(uuid));
    }

    @Override
    public Licence getById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends Licence> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<Licence> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Licence> findAllById(Iterable<UUID> uuids) {
        List<Licence> res = new ArrayList<>();
        for (UUID id : uuids) {
            Licence licence = store.get(id);
            if (licence != null) res.add(licence);
        }
        return res;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean existsById(UUID uuid) {
        return store.containsKey(uuid);
    }

    @Override
    public void deleteById(UUID uuid) {
        store.remove(uuid);
    }

    @Override
    public void delete(Licence entity) {
        throw new UnsupportedOperationException("Not implemented on purpose in FakeLicenceRepository");
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        throw new UnsupportedOperationException("Not implemented on purpose in FakeLicenceRepository");
    }

    @Override
    public void deleteAll(Iterable<? extends Licence> entities) {
        throw new UnsupportedOperationException("Not implemented on purpose in FakeLicenceRepository");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented on purpose in FakeLicenceRepository");
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not implemented on purpose in FakeLicenceRepository");
    }

    @Override
    public <S extends Licence> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Licence> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Licence> entities) {
        throw new UnsupportedOperationException("Not implemented on purpose in FakeLicenceRepository");
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {
        throw new UnsupportedOperationException("Not implemented on purpose in FakeLicenceRepository");
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException("Not implemented on purpose in FakeLicenceRepository");
    }

    @Override
    public Licence getOne(UUID uuid) {
        return null;
    }



    @Override
    public Licence getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends Licence> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Licence> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Licence> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Licence> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Licence> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Licence> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Licence, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }


    @Override
    public List<Licence> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Licence> findAll(Pageable pageable) {
        return null;
    }
}
