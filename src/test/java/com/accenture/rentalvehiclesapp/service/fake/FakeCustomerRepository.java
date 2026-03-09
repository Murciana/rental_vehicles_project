package com.accenture.rentalvehiclesapp.service.fake;

import com.accenture.rentalvehiclesapp.repository.CustomerRepository;
import com.accenture.rentalvehiclesapp.repository.entity.loggedinuser.Customer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class FakeCustomerRepository implements CustomerRepository {
    public final Map<UUID, Customer> store = new HashMap<>();

    @Override
    public <S extends Customer> S save(S entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Customer> findById(UUID uuid) {
        return Optional.ofNullable(store.get(uuid));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(store.values());
    }


    @Override
    public boolean existsByEmail(String email) {

        return store.values().stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
//        return store.containsKey(email);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
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
    public void delete(Customer entity) {

    }
    @Override
    public List<Customer> findAllById(Iterable<UUID> uuids) {
        return List.of();
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Customer> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return null;
    }


    @Override
    public void flush() {

    }

    @Override
    public <S extends Customer> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Customer> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Customer> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Customer getOne(UUID uuid) {
        return null;
    }

    @Override
    public Customer getById(UUID uuid) {
        return null;
    }

    @Override
    public Customer getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends Customer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Customer> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Customer> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Customer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Customer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Customer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Customer> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }




    @Override
    public long count() {
        return 0;
    }
}
