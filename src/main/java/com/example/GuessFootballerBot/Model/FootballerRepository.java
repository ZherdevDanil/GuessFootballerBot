package com.example.GuessFootballerBot.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository клас що дозволяє робити деякі запити з бд
 */
@Repository
public interface FootballerRepository extends CrudRepository<Footballer, Integer> {

    /**
     * Метод повертає обьєк класс Footballer, знайдений за випадково згенерованим id
     */
    @Override
    Optional<Footballer> findById(Integer randomId);
}
